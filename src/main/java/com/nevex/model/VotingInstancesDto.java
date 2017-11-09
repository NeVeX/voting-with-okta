package com.nevex.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Mark Cunningham on 11/9/2017.
 */
public final class VotingInstancesDto {

    private final String VOTING_ID = "voting_id";
    private final String NAME = "name";

    @JsonProperty(VOTING_ID)
    private final String votingId;
    @JsonProperty(NAME)
    private final String name;

    @JsonCreator
    public VotingInstancesDto(
            @JsonProperty(value = VOTING_ID, required = true) String votingId,
            @JsonProperty(value = NAME, required = true) String name) {
        this.votingId = votingId;
        this.name = name;
    }

    public String getVotingId() {
        return votingId;
    }

    public String getName() {
        return name;
    }
}
