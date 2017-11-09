package com.nevex.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nevex.model.OktaUser;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

/**
 * Created by Mark Cunningham on 11/8/2017.
 */
@RequestMapping
@Controller
public class HomeController {

    @GetMapping
    public ModelAndView home() {
        return new ModelAndView("home.html");
    }

    @GetMapping(value = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dto> test(OktaUser oktaUser) {
        return ResponseEntity.ok(new Dto("Hello "+oktaUser.getEmail()));
    }

    public static class Dto {
        @JsonProperty("message")
        private String message;

        Dto(String message) {
            this.message = message;
        }
        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

}
