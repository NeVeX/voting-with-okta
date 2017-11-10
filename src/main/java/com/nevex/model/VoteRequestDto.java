package com.nevex.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Mark Cunningham on 11/9/2017.
 */
public class VoteRequestDto {

    final String GRAND_PRIZE = "grand_prize";
    final String MOST_CREATIVE = "most_creative";
    final String MOST_IMPACTFUL = "most_impactful";

    @JsonProperty(GRAND_PRIZE)
    private final Boolean grandPrize;
    @JsonProperty(MOST_CREATIVE)
    private final Boolean mostCreative;
    @JsonProperty(MOST_IMPACTFUL)
    private final Boolean mostImpactful;

    @JsonCreator
    public VoteRequestDto(
            @JsonProperty(value = GRAND_PRIZE) Boolean grandPrize,
            @JsonProperty(value = MOST_CREATIVE) Boolean mostCreative,
            @JsonProperty(value = MOST_IMPACTFUL) Boolean mostImpactful) {
        this.grandPrize = grandPrize;
        this.mostCreative = mostCreative;
        this.mostImpactful = mostImpactful;
    }

    public Boolean getGrandPrize() {
        return grandPrize;
    }

    public Boolean getMostCreative() {
        return mostCreative;
    }

    public Boolean getMostImpactful() {
        return mostImpactful;
    }
}
