package com.nevex.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Mark Cunningham on 11/12/2017.
 */
public class VotingResultsDto {

    private final static String VOTING_RESOURCE = "voting_resource";
    private final static String GRAND_PRIZE_SCORES = "grand_prize_scores";
    private final static String MOST_IMPACTFUL_SCORES = "most_impactful_scores";
    private final static String MOST_CREATIVE_SCORES = "most_creative_scores";

    @JsonProperty(VOTING_RESOURCE)
    private final String votingResource;
    @JsonProperty(GRAND_PRIZE_SCORES)
    private final List<VotingTeamResultsDto> grandPrizeScores;
    @JsonProperty(MOST_IMPACTFUL_SCORES)
    private final List<VotingTeamResultsDto> mostImpactfulScores;
    @JsonProperty(MOST_CREATIVE_SCORES)
    private final List<VotingTeamResultsDto> mostCreativeScores;

    @JsonCreator
    public VotingResultsDto(
            @JsonProperty(value = VOTING_RESOURCE, required = true) String votingResource,
            @JsonProperty(value = GRAND_PRIZE_SCORES, required = true) List<VotingTeamResultsDto> grandPrizeScores,
            @JsonProperty(value = MOST_IMPACTFUL_SCORES, required = true) List<VotingTeamResultsDto> mostImpactfulScores,
            @JsonProperty(value = MOST_CREATIVE_SCORES, required = true) List<VotingTeamResultsDto> mostCreativeScores
    ) {
        this.votingResource = votingResource;
        this.grandPrizeScores = grandPrizeScores.stream().sorted(Collections.reverseOrder()).collect(Collectors.toList());
        this.mostImpactfulScores = mostImpactfulScores.stream().sorted(Collections.reverseOrder()).collect(Collectors.toList());
        this.mostCreativeScores = mostCreativeScores.stream().sorted(Collections.reverseOrder()).collect(Collectors.toList());
    }

    public String getVotingResource() {
        return votingResource;
    }

    public List<VotingTeamResultsDto> getGrandPrizeScores() {
        return grandPrizeScores;
    }

    public List<VotingTeamResultsDto> getMostImpactfulScores() {
        return mostImpactfulScores;
    }

    public List<VotingTeamResultsDto> getMostCreativeScores() {
        return mostCreativeScores;
    }
}
