package com.nevex.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import java.util.Set;

/**
 * Created by Mark Cunningham on 11/9/2017.
 */
public class VotingInformationRequestDto {

    final String VOTING_ID = "voting_id";
    final String TEAM_NAME = "team_name";
    final String SHORT_DESCRIPTION = "short_description";
    final String LONG_DESCRIPTION = "long_description";
    final String TEAM_MEMBERS = "team_members";

    @NotBlank
    @JsonProperty(VOTING_ID)
    private final String votingId;
    @NotBlank
    @Max(value = 30)
    @JsonProperty(TEAM_NAME)
    private final String teamName;
    @NotBlank
    @Max(value = 50)
    @JsonProperty(SHORT_DESCRIPTION)
    private final String shortDescription;
    @NotBlank
    @Max(value = 300)
    @JsonProperty(LONG_DESCRIPTION)
    private final String longDescription;
    @NotEmpty
    @JsonProperty(TEAM_MEMBERS)
    private final Set<PersonDto> teamMembers;

    @JsonCreator
    public VotingInformationRequestDto(
            @JsonProperty(value = VOTING_ID, required = true) String votingId,
            @JsonProperty(value = TEAM_NAME, required = true) String teamName,
            @JsonProperty(value = SHORT_DESCRIPTION, required = true) String shortDescription,
            @JsonProperty(value = LONG_DESCRIPTION, required = true) String longDescription,
            @JsonProperty(value = TEAM_MEMBERS, required = true) Set<PersonDto> teamMembers) {
        this.votingId = votingId;
        this.teamName = teamName;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.teamMembers = teamMembers;
    }

    public String getVotingId() {
        return votingId;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public Set<PersonDto> getTeamMembers() {
        return teamMembers;
    }


}
