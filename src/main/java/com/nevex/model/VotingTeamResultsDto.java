package com.nevex.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Mark Cunningham on 11/12/2017.
 */
public class VotingTeamResultsDto {

    private final static String TEAM_ID = "team_id";
    private final static String TEAM_NAME = "team_name";
    private final static String VOTES = "votes";

    @JsonProperty(TEAM_ID)
    private final Integer teamId;
    @JsonProperty(TEAM_NAME)
    private final String teamName;
    @JsonProperty(VOTES)
    private final Integer votes;

    @JsonCreator
    public VotingTeamResultsDto(
            @JsonProperty(value = TEAM_ID, required = true) Integer teamId,
            @JsonProperty(value = TEAM_NAME, required = true) String teamName,
            @JsonProperty(value = VOTES, required = true) Integer votes) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.votes = votes;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public Integer getVotes() {
        return votes;
    }
}
