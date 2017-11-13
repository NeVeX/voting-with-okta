package com.nevex.dao.entity;

import com.nevex.dao.VotingInstancesRepository;

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
@Table(name = "voting_instances", schema = "voting", uniqueConstraints = @UniqueConstraint(columnNames = {"resource_name"}))
public class VotingInstanceEntity implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "resource_name")
    private String resourceName;
    @Basic
    @Column(name = "open_for_voting")
    private boolean openForVoting;

    public VotingInstanceEntity() { }

    public VotingInstanceEntity(String name, String resourceName, boolean openForVoting) {
        this.name = name;
        this.resourceName = resourceName;
        this.openForVoting = openForVoting;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public boolean getOpenForVoting() {
        return openForVoting;
    }

    public void setOpenForVoting(boolean openForVoting) {
        this.openForVoting = openForVoting;
    }
}
