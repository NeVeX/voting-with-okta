package com.nevex.controller;

import com.nevex.config.property.VotingWithOktaProperties;
import com.nevex.model.ErrorDto;
import com.nevex.model.OktaUser;
import com.nevex.model.UserVoteResponseDto;
import com.nevex.model.VoteRequestDto;
import com.nevex.service.VotingService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * Created by Mark Cunningham on 11/8/2017.
 */
@RestController
@RequestMapping("/api")
public class VotingApiController {

    final static String VOTING_RESOURCE = "voting-resource";
    final static String TEAM_ID = "team-id";
    private final VotingService votingService;

    @Autowired
    public VotingApiController(VotingService votingService) {
        this.votingService = votingService;
    }

    @GetMapping(path = "/{"+VOTING_RESOURCE+"}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getVotingInfo(@PathVariable(VOTING_RESOURCE) String votingId) {
        if ( votingService.doesVotingResourceNameExist(votingId)) {
            return ResponseEntity.ok(votingService.getVotingInformationForVotingResourceName(votingId));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(path = "/{"+VOTING_RESOURCE+"}/{"+TEAM_ID+"}/votes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> placeNewVote(
            @PathVariable(VOTING_RESOURCE) String votingResource,
            @PathVariable(TEAM_ID) Integer teamId,
            @RequestBody VoteRequestDto voteRequestDto,
            OktaUser oktaUser) {
            boolean isVotePlaced = votingService.placeVote(votingResource, teamId, oktaUser.getUsername(), voteRequestDto);
            if (!isVotePlaced ) {
                return ResponseEntity.badRequest().body(new ErrorDto("vote_not_placed", "Could not place vote - is the voting over?"));
            }
        return getUserVotes(votingResource, oktaUser.getUsername()); // get the current vote for the user
    }

    @GetMapping(path = "/{"+VOTING_RESOURCE+"}/votes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserVotes(@PathVariable(VOTING_RESOURCE) String votingResource, OktaUser oktaUser) {
        return getUserVotes(votingResource, oktaUser.getUsername());
    }

    private ResponseEntity<?> getUserVotes(String votingResource, String username) {
        Optional<UserVoteResponseDto> userVotesOpt = votingService.getUserVotes(votingResource, username);
        if ( userVotesOpt.isPresent()) {
            return ResponseEntity.ok(userVotesOpt.get());
        } else {
//            return ResponseEntity.notFound().build();
            // Instead of 404 (even though it should be, let's default to an empty object since I'm running out of time building this)
            return ResponseEntity.ok(new UserVoteResponseDto(username, null, null, null));
        }
    }

}
