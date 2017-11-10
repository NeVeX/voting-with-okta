package com.nevex.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * Created by Mark Cunningham on 11/9/2017.
 */
public final class PersonDto {

    private final String FULL_NAME = "full_name";
    private final String EMAIL = "email";

    @JsonProperty(FULL_NAME)
    private final String fullName;
    @JsonProperty(EMAIL)
    private final String email;

    @JsonCreator
    public PersonDto(
            @JsonProperty(value = FULL_NAME, required = true) String fullName,
            @JsonProperty(value = EMAIL, required = true) String email) {
        this.email = email;
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonDto personDto = (PersonDto) o;
        return Objects.equals(email, personDto.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
