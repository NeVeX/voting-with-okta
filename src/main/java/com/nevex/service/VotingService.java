package com.nevex.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nevex.dao.VoteRepository;
import com.nevex.dao.VotingInstanceInformationRepository;
import com.nevex.dao.VotingInstancesRepository;
import com.nevex.dao.entity.VoteEntity;
import com.nevex.dao.entity.VotingInstanceEntity;
import com.nevex.dao.entity.VotingInstanceInformationEntity;
import com.nevex.model.PersonDto;
import com.nevex.model.UserVoteResponseDto;
import com.nevex.model.VoteRequestDto;
import com.nevex.model.VotingInformationRequestDto;
import com.nevex.model.VotingInformationResponseDto;
import com.nevex.model.VotingInstancesDto;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Mark Cunningham on 11/9/2017.
 */
public class VotingService {

    private final static Logger LOGGER = LoggerFactory.getLogger(VotingService.class);
    private final VotingInstancesRepository votingInstancesRepository;
    private final VotingInstanceInformationRepository votingInstanceInformationRepository;
    private final VoteRepository voteRepository;
    private final ObjectMapper objectMapper;

    public VotingService(VotingInstancesRepository votingInstancesRepository,
                         VotingInstanceInformationRepository votingInstanceInformationRepository,
                         VoteRepository voteRepository,
                         ObjectMapper objectMapper) {
        this.votingInstancesRepository = votingInstancesRepository;
        this.votingInstanceInformationRepository = votingInstanceInformationRepository;
        this.voteRepository = voteRepository;
        this.objectMapper = objectMapper;
        createTestData();
    }

//    public void openVoting(String votingResourceName) {
//        changeVotingOpenOrClosed(votingResourceName, true);
//    }
//
//    public void closeVoting(String votingResourceName) {
//        changeVotingOpenOrClosed(votingResourceName, false);
//    }

    public void changeVotingOpenOrClosed(String votingResourceName, boolean votingIsOpen) {
        Optional<VotingInstanceEntity> votingInstanceOpt = getVotingInstanceForResourceName(votingResourceName);
        if ( votingInstanceOpt.isPresent()) {
            votingInstanceOpt.get().setOpenForVoting(votingIsOpen);
            votingInstancesRepository.save(votingInstanceOpt.get());
        }
    }

    public Optional<UserVoteResponseDto> getUserVotes(String votingResourceName, String username) {
        Optional<VotingInstanceEntity> votingInstanceOpt = getVotingInstanceForResourceName(votingResourceName);
        if ( !votingInstanceOpt.isPresent()) { return Optional.empty(); }
        VotingInstanceEntity votingInstanceEntity = votingInstanceOpt.get();

        Optional<VoteEntity> voteEntityOpt = voteRepository.findOneByVotingIdAndUsername(votingInstanceEntity.getId(), username);
        if ( !voteEntityOpt.isPresent()) { return Optional.empty(); }
        VoteEntity voteEntity = voteEntityOpt.get();
        Optional<VotingInformationResponseDto> grandPrizeOpt = getTeamInformation(votingResourceName, voteEntity.getGrandPrize(), voteEntity.getVotingId());
        Optional<VotingInformationResponseDto> mostCreativeOpt = getTeamInformation(votingResourceName, voteEntity.getMostCreative(), voteEntity.getVotingId());
        Optional<VotingInformationResponseDto> mostImpactfulOpt = getTeamInformation(votingResourceName, voteEntity.getMostImpactful(), voteEntity.getVotingId());

        UserVoteResponseDto userVote = new UserVoteResponseDto(
                username,
                grandPrizeOpt.isPresent() ? grandPrizeOpt.get().getTeamName() : null,
                mostCreativeOpt.isPresent() ? mostCreativeOpt.get().getTeamName() : null,
                mostImpactfulOpt.isPresent() ? mostImpactfulOpt.get().getTeamName() : null
        );
        return Optional.of(userVote);
    }

    private Optional<VotingInformationResponseDto> getTeamInformation(String votingResourceName, Integer teamId, int votingId) {
        if ( teamId == null) { return Optional.empty(); }
        Optional<VotingInstanceInformationEntity> teamInfoOpt = votingInstanceInformationRepository.findOneByIdAndVotingId(teamId, votingId);
        if ( !teamInfoOpt.isPresent()) { return Optional.empty(); }

        VotingInstanceInformationEntity teamInfo = teamInfoOpt.get();
        return Optional.ofNullable(createVotingInfoResponse(teamInfo, votingResourceName));
    }

    public boolean placeVote(String votingResource, int teamId, String userName, VoteRequestDto voteRequest) {

        Optional<VotingInstanceEntity> votingInstanceOpt = getVotingInstanceForResourceName(votingResource);

        if ( !votingInstanceOpt.isPresent()) { return false; }

        VotingInstanceEntity votingInstanceEntity = votingInstanceOpt.get();
        if ( !votingInstanceEntity.getOpenForVoting() ) { return false; } // voting not open
        Optional<VotingInstanceInformationEntity> votingInstanceTeamOpt =
                votingInstanceInformationRepository.findOneByIdAndVotingId(teamId, votingInstanceEntity.getId());

        if ( !votingInstanceTeamOpt.isPresent()) { return false; }

        Optional<VoteEntity> voteEntityOpt = voteRepository.findOneByVotingIdAndUsername(votingInstanceEntity.getId(), userName);
        VoteEntity vote;
        if ( voteEntityOpt.isPresent()) {
            vote = voteEntityOpt.get();
        } else {
            vote = new VoteEntity(votingInstanceEntity.getId(), userName);
        }
        vote.updateVote(teamId, voteRequest);
        voteRepository.save(vote);
        LOGGER.info("Save vote for [{}]", userName);
        return true;
    }

    public boolean doesVotingResourceExistAndIsOpenForVotes(String votingResourceName) {
        return doesVotingResourceNameExist(votingResourceName) && isVotingOpenForVotingResource(votingResourceName);
    }

    public boolean doesVotingResourceNameExist(String resourceName) {
        return getVotingInstanceForResourceName(resourceName).isPresent();
    }

    public boolean isVotingOpenForVotingResource(String resourceName) {
        Optional<VotingInstanceEntity> votingInstance = getVotingInstanceForResourceName(resourceName);
        if ( votingInstance.isPresent()) {
            return votingInstance.get().getOpenForVoting();
        }
        return false;
    }

    public Optional<VotingInstanceEntity> getVotingInstanceForResourceName(String resourceName) {
        return votingInstancesRepository.findOneByResourceName(StringUtils.lowerCase(resourceName));
    }

    public Set<VotingInstancesDto> getAllInstances() {
        Set<VotingInstancesDto> instances = new HashSet<>();
        for ( VotingInstanceEntity instancesEntity : votingInstancesRepository.findAll()) {
            instances.add(new VotingInstancesDto(instancesEntity));
        }
        return instances;
    }

    public Set<VotingInformationResponseDto> getVotingInformationForVotingResourceName(String resourceName) {
        Set<VotingInformationResponseDto> votingInformation = new HashSet<>();
        Optional<VotingInstanceEntity> votingInstance = getVotingInstanceForResourceName(resourceName);
        if ( votingInstance.isPresent()) {
            for ( VotingInstanceInformationEntity votingInfo : votingInstanceInformationRepository.findAllByVotingId(votingInstance.get().getId())) {
                VotingInformationResponseDto votingResponseInfo = createVotingInfoResponse(votingInfo, resourceName);
                if ( votingResponseInfo != null ) { votingInformation.add(votingResponseInfo); }
            }
        }
        return votingInformation;
    }

    private VotingInformationResponseDto createVotingInfoResponse(VotingInstanceInformationEntity votingEntity, String votingResourceName) {
        try {
            Set<PersonDto> teamMembers = objectMapper.readValue(votingEntity.getTeamMembers(), new TypeReference<Set<PersonDto>>() {});
            return new VotingInformationResponseDto(votingEntity, votingResourceName, teamMembers);
        } catch (Exception e) {
            // well fuck
            LOGGER.error("Could not get certain voting information for voting resource [{}]", votingResourceName, e);
            return null;
        }
    }

    private void createTestData() {

        VotingInstanceEntity votingInstanceEntity = votingInstancesRepository.save(new VotingInstanceEntity("UX Hackathon Voting", "ux-hackathon", false));

        Set<PersonDto> teamMembers = new HashSet<>();
        teamMembers.add(new PersonDto("John Doe", "john@prosper.com"));
        teamMembers.add(new PersonDto("Jane Smith", "marysmaith@prosper.com"));
        teamMembers.add(new PersonDto("Nancy Drew", "nancy@prosper.com"));
        teamMembers.add(new PersonDto("Mark Cunningham", "mcunningham@prosper.com"));

        String teamMembersAsJson;
        try {
            teamMembersAsJson = objectMapper.writeValueAsString(teamMembers);
        } catch (Exception e) {
            throw new RuntimeException("Blah");
        }

        Set<VotingInstanceInformationEntity> testTeams = IntStream.range(1, 20)
                .mapToObj(i -> new VotingInstanceInformationEntity(
                        votingInstanceEntity.getId(),
                        "test-team-"+i,
                        "This is a short description"+i,
                        "She sells sea shells on the sea shore said jack in the box with old Mcdonald had a farm eeeei eeeei ooohhh",
                        teamMembersAsJson))
                .collect(Collectors.toSet());

        testTeams.forEach(t -> votingInstanceInformationRepository.save(t));
    }

    @Transactional
    public boolean setVotingInformation(String votingResource, List<VotingInformationRequestDto> votingInformation) throws Exception {
        Optional<VotingInstanceEntity> votingInstanceEntityOpt = getVotingInstanceForResourceName(votingResource);
        if ( !votingInstanceEntityOpt.isPresent()) {
            return false;
        }
        VotingInstanceEntity votingInstanceEntity = votingInstanceEntityOpt.get();

        Set<VotingInstanceInformationEntity> votingInfosToSave = new HashSet<>();
        for ( VotingInformationRequestDto newInfo : votingInformation) {
            votingInfosToSave.add(new VotingInstanceInformationEntity(
                    votingInstanceEntity.getId(),
                    newInfo.getTeamName(),
                    newInfo.getShortDescription(),
                    newInfo.getLongDescription(),
                    objectMapper.writeValueAsString(newInfo.getTeamMembers())
            ));
        }

        // delete anything we already have
        votingInstanceInformationRepository.deleteAllByVotingId(votingInstanceEntity.getId());

        votingInfosToSave.forEach(ent -> votingInstanceInformationRepository.save(ent));
        return true;
    }
}

