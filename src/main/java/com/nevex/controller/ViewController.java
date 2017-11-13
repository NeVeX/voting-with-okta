package com.nevex.controller;

import com.nevex.dao.entity.VotingInstanceInformationEntity;
import com.nevex.model.OktaUser;
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

    @Autowired
    public ViewController(VotingService votingService) {
        this.votingService = votingService;
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
                                           OktaUser oktaUser) {
        if (!isAdminUser(oktaUser)) {
            return new ModelAndView("error");
        }
        if (votingService.doesVotingResourceNameExist(votingResourceName)) {
            return new ModelAndView("voting-admin");
        }
        return new ModelAndView("error");
    }

    private boolean isAdminUser(OktaUser oktaUser) {
        return StringUtils.containsIgnoreCase(oktaUser.getUsername(), "mcunningham");
    }
}