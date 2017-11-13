package com.nevex.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nevex.dao.entity.VotingInstanceEntity;

import java.util.Objects;

/**
 * Created by Mark Cunningham on 11/9/2017.
 */
public final class VotingInstancesDto {

    private final String RESOURCE_NAME = "resource_name";
    private final String NAME = "name";
    private final String OPEN_FOR_VOTING = "open_for_voting";

    @JsonProperty(RESOURCE_NAME)
    private final String resourceName;
    @JsonProperty(NAME)
    private final String name;
    @JsonProperty(OPEN_FOR_VOTING)
    private final boolean openForVoting;

    @JsonCreator
    public VotingInstancesDto(
            @JsonProperty(value = NAME, required = true) String name,
            @JsonProperty(value = RESOURCE_NAME, required = true) String resourceName,
            @JsonProperty(value = OPEN_FOR_VOTING, required = true) boolean openForVoting
            ) {
        this.name = name;
        this.resourceName = resourceName;
        this.openForVoting = openForVoting;
    }

    public VotingInstancesDto(VotingInstanceEntity votingInstanceEntity) {
        this(votingInstanceEntity.getName(), votingInstanceEntity.getResourceName(), votingInstanceEntity.getOpenForVoting());
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getName() {
        return name;
    }

    public boolean isOpenForVoting() {
        return openForVoting;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VotingInstancesDto that = (VotingInstancesDto) o;
        return Objects.equals(resourceName, that.resourceName) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resourceName, name);
    }
}
