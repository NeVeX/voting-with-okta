package com.nevex.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Mark Cunningham on 11/9/2017.
 */
public class ErrorDto {

    private final static String ERROR = "error";
    private final static String MESSAGE = "message";

    @JsonProperty(ERROR)
    private final String error;
    @JsonProperty(MESSAGE)
    private final String message;

    @JsonCreator
    public ErrorDto(
            @JsonProperty(ERROR) String error,
            @JsonProperty(MESSAGE) String message) {
        this.error = error;
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
