package com.nevex.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nevex.dao.entity.VoteEntity;

/**
 * Created by Mark Cunningham on 11/10/2017.
 */
public class UserVoteResponseDto {

    private final static String USERNAME = "username";
    private final static String GRAND_PRIZE_TEAM_NAME = "grand_prize_team_name";
    private final static String MOST_CREATIVE_TEAM_NAME = "most_creative_team_name";
    private final static String MOST_IMPACTFUL_TEAM_NAME = "most_impactful_team_name";

    @JsonProperty(USERNAME)
    private final String username;
    @JsonProperty(GRAND_PRIZE_TEAM_NAME)
    private final String grandPrizeTeamName;
    @JsonProperty(MOST_CREATIVE_TEAM_NAME)
    private final String mostCreativeTeamName;
    @JsonProperty(MOST_IMPACTFUL_TEAM_NAME)
    private final String mostImpactfulTeamName;

    @JsonCreator
    public UserVoteResponseDto(
            @JsonProperty(USERNAME) String username,
            @JsonProperty(GRAND_PRIZE_TEAM_NAME) String grandPrizeTeamName,
            @JsonProperty(MOST_CREATIVE_TEAM_NAME) String mostCreativeTeamName,
            @JsonProperty(MOST_IMPACTFUL_TEAM_NAME) String mostImpactfulTeamName) {
        this.username = username;
        this.grandPrizeTeamName = grandPrizeTeamName;
        this.mostCreativeTeamName = mostCreativeTeamName;
        this.mostImpactfulTeamName = mostImpactfulTeamName;
    }

    public String getUsername() {
        return username;
    }

    public String getGrandPrizeTeamName() {
        return grandPrizeTeamName;
    }

    public String getMostCreativeTeamName() {
        return mostCreativeTeamName;
    }

    public String getMostImpactfulTeamName() {
        return mostImpactfulTeamName;
    }
}
