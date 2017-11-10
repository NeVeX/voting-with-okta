package com.nevex.dao.entity;

import com.nevex.model.VoteRequestDto;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;

/**
 * Created by Mark Cunningham on 11/10/2017.
 */
@Entity
@Table(name = "votes", schema = "voting", uniqueConstraints = @UniqueConstraint(columnNames = {"voting_id", "username"}))
public class VoteEntity implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    @Basic // TODO: use join columns
    @Column(name = "voting_id")
    private int votingId;
    @Basic // TODO: use join columns
    @Column(name = "username")
    private String username;
    @Basic // TODO: use join columns
    @Column(name = "grand_prize")
    private Integer grandPrize;
    @Basic // TODO: use join columns
    @Column(name = "most_creative")
    private Integer mostCreative;
    @Basic // TODO: use join columns
    @Column(name = "most_impactful")
    private Integer mostImpactful;

    public VoteEntity() {}

    public VoteEntity(int votingId, String username) {
        this(votingId, username, null, null, null);
    }

    public VoteEntity(int votingId, String username, Integer grandPrize, Integer mostCreative, Integer mostImpactful) {
        this.votingId = votingId;
        this.username = username;
        this.grandPrize = grandPrize;
        this.mostCreative = mostCreative;
        this.mostImpactful = mostImpactful;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getGrandPrize() {
        return grandPrize;
    }

    public void setGrandPrize(Integer grandPrize) {
        this.grandPrize = grandPrize;
    }

    public Integer getMostCreative() {
        return mostCreative;
    }

    public void setMostCreative(Integer mostCreative) {
        this.mostCreative = mostCreative;
    }

    public Integer getMostImpactful() {
        return mostImpactful;
    }

    public void setMostImpactful(Integer mostImpactful) {
        this.mostImpactful = mostImpactful;
    }

    @Transient
    public void updateVote(int teamId, VoteRequestDto voteRequest) {
        if ( voteRequest.getGrandPrize() != null ) {
            this.grandPrize = teamId;
        }
        if ( voteRequest.getMostCreative() != null ) {
            this.mostCreative = teamId;
        }
        if ( voteRequest.getMostImpactful() != null ) {
            this.mostImpactful = teamId;
        }
    }
}
