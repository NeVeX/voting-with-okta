package com.nevex.service;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Mark Cunningham on 11/9/2017.
 */
public class VotingService {

    private final Set<String> votingInstances = new HashSet<>();

    public VotingService() {
        votingInstances.add("ux-hackathon");
    }

    public Set<String> getAllInstances() {
        return votingInstances;
    }


}

