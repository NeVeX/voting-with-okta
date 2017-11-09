package com.nevex.service;

import com.nevex.model.VotingInstancesDto;

import java.util.HashSet;
import java.util.Set;

import static javafx.scene.input.KeyCode.V;

/**
 * Created by Mark Cunningham on 11/9/2017.
 */
public class VotingService {

    private final Set<VotingInstancesDto> votingInstances = new HashSet<>();

    public VotingService() {
        votingInstances.add(new VotingInstancesDto("ux-hackathon", "UX Hackathon"));
    }

    public Set<VotingInstancesDto> getAllInstances() {
        return votingInstances;
    }

}

