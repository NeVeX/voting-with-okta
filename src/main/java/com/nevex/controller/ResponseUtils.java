package com.nevex.controller;

import com.nevex.model.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Mark Cunningham on 11/9/2017.
 */
class ResponseUtils {

    static ResponseEntity<ErrorDto> to404() {
        return ResponseEntity.status(404).body(new ErrorDto("404", "That does not exist"));
    }

//    static ModelAndView toViewName(String viewName) {
//        return new ModelAndView(viewName);
//    }

}
