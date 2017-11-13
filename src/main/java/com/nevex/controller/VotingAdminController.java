package com.nevex.controller;

import com.nevex.config.property.VotingWithOktaProperties;
import com.nevex.model.OktaUser;
import com.nevex.model.UserVoteResponseDto;
import com.nevex.model.VoteRequestDto;
import com.nevex.model.VotingInformationRequestDto;
import com.nevex.model.VotingResultsDto;
import com.nevex.service.VotingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * Created by Mark Cunningham on 11/8/2017.
 */
@RestController
@RequestMapping
public class VotingAdminController {

    final static String VOTING_RESOURCE = "voting-resource";
    private final VotingService votingService;
    private final String adminKey;

    @Autowired
    public VotingAdminController(VotingService votingService,
                                 VotingWithOktaProperties properties) {
        this.votingService = votingService;
        this.adminKey = properties.getAdminKey();
    }

    @GetMapping(path = "/{"+VOTING_RESOURCE+"}/results", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getResults(@PathVariable(VOTING_RESOURCE) String votingResource) {
        Optional<VotingResultsDto> resultsDtoOpt = votingService.getScores(votingResource);
        if (resultsDtoOpt.isPresent()) {
            return ResponseEntity.ok(resultsDtoOpt.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(path = "/{"+VOTING_RESOURCE+"}/admin/openvoting", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> openVoting(@PathVariable(VOTING_RESOURCE) String votingResource) {
        votingService.changeVotingOpenOrClosed(votingResource, true);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/{"+VOTING_RESOURCE+"}/admin/closevoting", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> closeVoting(@PathVariable(VOTING_RESOURCE) String votingResource) {
        votingService.changeVotingOpenOrClosed(votingResource, false);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/{"+VOTING_RESOURCE+"}/admin/info", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> setVotingInfo(@PathVariable(VOTING_RESOURCE) String votingResource,
                                           @RequestBody List<VotingInformationRequestDto> votingInformation) throws Exception {

        boolean didSave = votingService.setVotingInformation(votingResource, votingInformation);
        if ( didSave) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }


//    private ResponseEntity<?> changeVotingForResource(String resourceName, String adminKey, boolean openVoting) {
//        if ( !isAdminRequestOk(adminKey)) {
//            return ResponseEntity.status(403).body(new ErrorDto("access_denied", "nope"));
//        }
//        votingService.changeVotingOpenOrClosed(resourceName, openVoting);
//        return ResponseEntity.ok().build();
//    }

//    private boolean isAdminRequestOk(String adminKey) {
//        return StringUtils.equalsIgnoreCase(adminKey, this.adminKey);
//    }
}
