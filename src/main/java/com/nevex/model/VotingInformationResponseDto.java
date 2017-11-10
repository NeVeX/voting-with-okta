package com.nevex.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
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
                                        @JsonProperty(value = VOTING_ID, required = true) String votingId,
                                        @JsonProperty(value = TEAM_NAME, required = true) String teamName,
                                        @JsonProperty(value = SHORT_DESCRIPTION, required = true) String shortDescription,
                                        @JsonProperty(value = LONG_DESCRIPTION, required = true) String longDescription,
                                        @JsonProperty(value = TEAM_MEMBERS, required = true) Set<PersonDto> teamMembers) {
        super(votingId, teamName, shortDescription, longDescription, teamMembers);
        this.teamId = teamId;
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
