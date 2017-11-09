package com.nevex.controller;

import com.nevex.service.VotingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView getVotingPage(@PathVariable(VOTING_RESOURCE) String votingId) {
//        if ( votingService.getAllInstances().contains(votingId)) {
//            return toViewName("voting.html");
//        }
        return new ModelAndView("voting");
    }

}
