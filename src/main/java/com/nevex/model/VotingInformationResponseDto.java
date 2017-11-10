package com.nevex.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nevex.dao.entity.VotingInstanceInformationEntity;

import java.util.Objects;
import java.util.Set;

/**
 * Created by Mark Cunningham on 11/9/2017.
 */
public final class VotingInformationResponseDto extends VotingInformationRequestDto {

    private final String TEAM_ID = "team_id";

    private final int teamId;

    @JsonCreator
    public VotingInformationResponseDto(@JsonProperty(value = TEAM_ID, required = true) int teamId,
                                        @JsonProperty(value = VOTING_RESOURCE, required = true) String votingResource,
                                        @JsonProperty(value = TEAM_NAME, required = true) String teamName,
                                        @JsonProperty(value = SHORT_DESCRIPTION, required = true) String shortDescription,
                                        @JsonProperty(value = LONG_DESCRIPTION, required = true) String longDescription,
                                        @JsonProperty(value = TEAM_MEMBERS, required = true) Set<PersonDto> teamMembers) {
        super(votingResource, teamName, shortDescription, longDescription, teamMembers);
        this.teamId = teamId;
    }

    public VotingInformationResponseDto(VotingInstanceInformationEntity votingInfo, String votingResource, Set<PersonDto> teamMembers) {
        this(votingInfo.getId(), votingResource, votingInfo.getTeamName(), votingInfo.getShortDescription(), votingInfo.getLongDescription(), teamMembers);

    }

    public int getTeamId() {
        return teamId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VotingInformationResponseDto that = (VotingInformationResponseDto) o;
        return teamId == that.teamId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamId);
    }
}
