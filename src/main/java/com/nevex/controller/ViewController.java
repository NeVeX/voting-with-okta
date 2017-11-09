package com.nevex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static com.nevex.controller.ResponseUtils.toViewName;
import static com.nevex.controller.VotingApiController.VOTING_RESOURCE;

/**
 * Created by Mark Cunningham on 11/9/2017.
 */
@Controller
@RequestMapping
public class ViewController {

    @GetMapping("/")
    public ModelAndView home() {
        return toViewName("home");
    }

    @GetMapping(path = "/{"+VOTING_RESOURCE+"}")
    public ModelAndView getVotingPage(@PathVariable(VOTING_RESOURCE) String votingId) {
//        if ( votingService.getAllInstances().contains(votingId)) {
//            return toViewName("voting.html");
//        }
        return toViewName("voting");
    }

}
