package com.nevex.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nevex.dao.entity.VotingInstanceEntity;

import java.util.Objects;

/**
 * Created by Mark Cunningham on 11/9/2017.
 */
public final class VotingInstancesDto {

//    private final String VOTING_RESOURCE = "voting_id";
    private final String RESOURCE_NAME = "resource_name";
    private final String NAME = "name";

//    @JsonProperty(VOTING_RESOURCE)
//    private final String votingId;
    @JsonProperty(RESOURCE_NAME)
    private final String resourceName;
    @JsonProperty(NAME)
    private final String name;

    @JsonCreator
    public VotingInstancesDto(
//            @JsonProperty(value = VOTING_RESOURCE, required = true) String votingId,
            @JsonProperty(value = NAME, required = true) String name,
            @JsonProperty(value = RESOURCE_NAME, required = true) String resourceName
            ) {
//        this.votingId = votingId;
        this.name = name;
        this.resourceName = resourceName;
    }

    public VotingInstancesDto(VotingInstanceEntity votingInstanceEntity) {
        this(votingInstanceEntity.getName(), votingInstanceEntity.getResourceName());
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getName() {
        return name;
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
