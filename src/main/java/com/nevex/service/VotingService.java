package com.nevex.service;

import com.nevex.model.PersonDto;
import com.nevex.model.VotingInformationRequestDto;
import com.nevex.model.VotingInformationResponseDto;
import com.nevex.model.VotingInstancesDto;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Mark Cunningham on 11/9/2017.
 */
public class VotingService {

    private final Set<VotingInstancesDto> votingInstances = new HashSet<>();
    private final Map<String, Set<VotingInformationResponseDto>> votingInstancesToTeams = new HashMap<>();

    public VotingService() {
        votingInstances.add(new VotingInstancesDto("ux-hackathon", "UX Hackathon"));

        Set<PersonDto> teamMembers = new HashSet<>();
        teamMembers.add(new PersonDto("John Doe", "john@prosper.com"));
        teamMembers.add(new PersonDto("Jane Smith", "marysmaith@prosper.com"));
        teamMembers.add(new PersonDto("Nancy Drew", "nancy@prosper.com"));
        teamMembers.add(new PersonDto("Mark Cunningham", "mcunningham@prosper.com"));

        Set<VotingInformationResponseDto> testTeams = IntStream.range(1, 20)
                .mapToObj(i -> new VotingInformationResponseDto(i, "ux-hackathon", "test-team-"+i, "This is a short description"+i,
                        "She sells sea shells on the sea shore said jack in the box with old Mcdonald had a farm eeeei eeeei ooohhh", teamMembers))
                .collect(Collectors.toSet());

        votingInstancesToTeams.put("ux-hackathon", testTeams);
    }

    public boolean doesVotingIdExist(String votingId) {
        return votingInstances.stream().anyMatch(v -> StringUtils.equalsIgnoreCase(v.getVotingId(), votingId));
    }

    public Set<VotingInstancesDto> getAllInstances() {
        return votingInstances;
    }

    public Set<VotingInformationResponseDto> getVotingInformationForVotingId(String votingId) {
        return votingInstancesToTeams.containsKey(votingId) ? votingInstancesToTeams.get(votingId) : new HashSet<>();
    }

}

