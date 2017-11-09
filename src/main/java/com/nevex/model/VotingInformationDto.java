package com.nevex.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Mark Cunningham on 11/9/2017.
 */
public final class VotingInformationDto {

    private final String VOTING_ID = "voting_id";

    @JsonProperty(VOTING_ID)
    private final String votingId;

    @JsonCreator
    public VotingInformationDto(
            @JsonProperty(value = VOTING_ID, required = true) String votingId) {
        this.votingId = votingId;
    }

    public String getVotingId() {
        return votingId;
    }
}
