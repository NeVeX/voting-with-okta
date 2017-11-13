package com.nevex.dao.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;

/**
 * Created by Mark Cunningham on 11/10/2017.
 */
@Entity
@Table(name = "voting_instances_information", schema = "voting", uniqueConstraints = @UniqueConstraint(columnNames = {"voting_id", "team_name"}))
public class VotingInstanceInformationEntity implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    @Basic // TODO: use join columns
    @Column(name = "voting_id")
    private int votingId;
    @Basic
    @Column(name = "team_name")
    private String teamName;
    @Basic
    @Column(name = "short_description", length = 1000)
    private String shortDescription;
    @Basic
    @Column(name = "long_description", length = 2000)
    private String longDescription;
    @Basic
    @Column(name = "teamMembers", length = 2000)
    private String teamMembers; // TODO: don't save as JSON FFS

    public VotingInstanceInformationEntity() {
    }

    public VotingInstanceInformationEntity(int votingId, String teamName, String shortDescription, String longDescription, String teamMembers) {
        this.votingId = votingId;
        this.teamName = teamName;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.teamMembers = teamMembers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVotingId() {
        return votingId;
    }

    public void setVotingId(int votingId) {
        this.votingId = votingId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(String teamMembers) {
        this.teamMembers = teamMembers;
    }
}
