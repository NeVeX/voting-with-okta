package com.nevex.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nevex.dao.entity.VotingInstanceInformationEntity;
import com.nevex.model.OktaUser;
import com.nevex.model.PersonDto;
import com.nevex.model.VotingInformationRequestDto;
import com.nevex.model.VotingInformationResponseDto;
import com.nevex.service.VotingService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.saml.websso.WebSSOProfileConsumer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.nevex.controller.VotingApiController.VOTING_RESOURCE;

/**
 * Created by Mark Cunningham on 11/9/2017.
 */
@Controller
@RequestMapping
public class ViewController {

    private final VotingService votingService;
    private final ObjectMapper objectMapper;

    @Autowired
    public ViewController(VotingService votingService, ObjectMapper objectMapper) {
        this.votingService = votingService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/")
    public ModelAndView home() {
        ModelAndView homeModelView = new ModelAndView("home");
        homeModelView.addObject("instances", votingService.getAllInstances());
        return homeModelView;
    }

    @GetMapping(path = "/{"+VOTING_RESOURCE+"}")
    public ModelAndView getVotingPage(@PathVariable(VOTING_RESOURCE) String votingResourceName) {
        if ( votingService.doesVotingResourceNameExist(votingResourceName)) {
            ModelAndView votingModelView = new ModelAndView("voting");
            Set<VotingInformationResponseDto> teams = votingService.getVotingInformationForVotingResourceName(votingResourceName);
            if ( teams == null ) { teams = new HashSet<>(); } // safety
            List<VotingInformationResponseDto> teamsOrdered = teams.stream().sorted(Comparator.comparing(VotingInformationRequestDto::getTeamName)).collect(Collectors.toList());;
            votingModelView.addObject("teams", teamsOrdered);
            votingModelView.addObject("voting_resource", votingResourceName);
            votingModelView.addObject("voting_open", votingService.isVotingOpenForVotingResource(votingResourceName));

            return votingModelView;
        }
        return new ModelAndView("error");
    }

    @GetMapping(path = "/{"+VOTING_RESOURCE+"}/admin")
    public ModelAndView getVotingAdminPage(@PathVariable(VOTING_RESOURCE) String votingResourceName,
                                           OktaUser oktaUser) throws Exception {
        if (!isAdminUser(oktaUser)) {
            return new ModelAndView("error");
        }
        if (votingService.doesVotingResourceNameExist(votingResourceName)) {
            ModelAndView modelAndView = new ModelAndView("voting-admin");
            modelAndView.addObject("example_json_data", getExampleJson());
            return modelAndView;
        }
        return new ModelAndView("error");
    }

    private boolean isAdminUser(OktaUser oktaUser) {
        return StringUtils.containsIgnoreCase(oktaUser.getUsername(), "mcunningham");
    }

    private String getExampleJson() throws Exception {
        List<VotingInformationRequestDto> votingWrapper = new ArrayList<>();
        Set<PersonDto> members = new HashSet<>();
        members.add(new PersonDto("Mark Cunningham", "mcunningham@example.com"));
        members.add(new PersonDto("Jane Doe", "janeDoe@example.com"));
        votingWrapper.add(new VotingInformationRequestDto("team-name-1",
                "short-description-1",
                "long-description-1",
                members));
        votingWrapper.add(new VotingInformationRequestDto("team-name-2",
                "short-description-2",
                "long-description-2",
                members));
        votingWrapper.add(new VotingInformationRequestDto("team-name-3",
                "short-description-3",
                "long-description-3",
                members));
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(votingWrapper);
    }

}