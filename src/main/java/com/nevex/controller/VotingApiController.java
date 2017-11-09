package com.nevex.controller;

import com.nevex.model.VotingInformationDto;
import com.nevex.service.VotingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by Mark Cunningham on 11/8/2017.
 */
@RestController
@RequestMapping("/api")
public class VotingApiController {

    final static String VOTING_RESOURCE = "voting-id";
    private final VotingService votingService;

    @Autowired
    public VotingApiController(VotingService votingService) {
        this.votingService = votingService;
    }

    @GetMapping(path = "/{"+VOTING_RESOURCE+"}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VotingInformationDto> getVotingInfo(@PathVariable(VOTING_RESOURCE) String votingId) {
        return ResponseEntity.ok(new VotingInformationDto(votingId));
    }
}
