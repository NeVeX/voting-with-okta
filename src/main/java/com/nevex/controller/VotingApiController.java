package com.nevex.controller;

import com.nevex.model.OktaUser;
import com.nevex.model.VoteRequestDto;
import com.nevex.service.VotingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            votingService.placeVote(votingResource, teamId, oktaUser.getEmail(), voteRequestDto);
        return ResponseEntity.ok().build();
    }

}
