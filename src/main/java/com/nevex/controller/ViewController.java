package com.nevex.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nevex.dao.entity.VotingInstanceInformationEntity;
import com.nevex.model.OktaUser;
import com.nevex.model.PersonDto;
import com.nevex.model.VotingInformationRequestDto;
import com.nevex.model.VotingInformationResponseDto;
import com.nevex.model.VotingResultsDto;
import com.nevex.model.VotingTeamResultsDto;
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

import static com.nevex.controller.ResponseUtils.toErrorView;
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
        return toErrorView("The resource ["+votingResourceName+"] does not exist");
    }

    @GetMapping(path = "/{"+VOTING_RESOURCE+"}/admin")
    public ModelAndView getVotingAdminPage(@PathVariable(VOTING_RESOURCE) String votingResourceName,
                                           OktaUser oktaUser) throws Exception {
        if (!isAdminUser(oktaUser)) {
            return toErrorView("You are unauthorized to view this page");
        }
        if (votingService.doesVotingResourceNameExist(votingResourceName)) {
            ModelAndView modelAndView = new ModelAndView("voting-admin");
            modelAndView.addObject("example_json_data", getExampleJson());
            return modelAndView;
        }
        return toErrorView("The resource ["+votingResourceName+"] does not exist");
    }

    @GetMapping(path = "/{"+VOTING_RESOURCE+"}/results")
    public ModelAndView getResults(@PathVariable(VOTING_RESOURCE) String votingResourceName,
                                           OktaUser oktaUser) throws Exception {
        if (!isAdminUser(oktaUser)) {
            return toErrorView("You are unauthorized to see this page");
        }
        if (votingService.doesVotingResourceNameExist(votingResourceName)) {
            ModelAndView modelAndView = new ModelAndView("voting-results-select");
            modelAndView.addObject("results", objectMapper.writeValueAsString(votingService.getScores(votingResourceName).get()));
            return modelAndView;
        }
        return toErrorView("The resource ["+votingResourceName+"] does not exist");
    }

    @GetMapping(path = "/{"+VOTING_RESOURCE+"}/results/grandprize")
    public ModelAndView getGrandPrizeResults(@PathVariable(VOTING_RESOURCE) String votingResourceName,
                                   OktaUser oktaUser) throws Exception {
        return getSpecificResult(votingResourceName, oktaUser, ResultType.GrandPrize);
    }

    @GetMapping(path = "/{"+VOTING_RESOURCE+"}/results/mostcreative")
    public ModelAndView getMostCreativeResults(@PathVariable(VOTING_RESOURCE) String votingResourceName,
                                             OktaUser oktaUser) throws Exception {
        return getSpecificResult(votingResourceName, oktaUser, ResultType.MostCreative);
    }

    @GetMapping(path = "/{"+VOTING_RESOURCE+"}/results/mostimpactful")
    public ModelAndView getMostImpactfulResults(@PathVariable(VOTING_RESOURCE) String votingResourceName,
                                             OktaUser oktaUser) throws Exception {
        return getSpecificResult(votingResourceName, oktaUser, ResultType.MostImpactful);
    }

    private ModelAndView getSpecificResult(String resourceName, OktaUser oktaUser, ResultType type) throws Exception {
        if (!isAdminUser(oktaUser)) {
            return toErrorView("You are unauthorized to see this page");
        }
        if (votingService.doesVotingResourceNameExist(resourceName)) {
            ModelAndView modelAndView = new ModelAndView("voting-results");
            VotingResultsDto votingResultsDto = votingService.getScores(resourceName).get();
            List<VotingTeamResultsDto> teamResultsDto = null;
            switch (type) {
                case GrandPrize:
                    teamResultsDto = votingResultsDto.getGrandPrizeScores();
                    break;
                case MostCreative:
                    teamResultsDto = votingResultsDto.getMostCreativeScores();
                    break;
                case MostImpactful:
                    teamResultsDto = votingResultsDto.getMostImpactfulScores();
                    break;

            }
            modelAndView.addObject("results", teamResultsDto);
            modelAndView.addObject("results_size", teamResultsDto.size());
            modelAndView.addObject("header_title", type.title);
            return modelAndView;
        }
        return toErrorView("The resource ["+resourceName+"] does not exist");
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

    private enum ResultType {
        GrandPrize("Grand Prize"), MostCreative("Most Creative"), MostImpactful("Most Impactful");

        private String title;

        ResultType(String title) {
            this.title = title;
        }

    }
}