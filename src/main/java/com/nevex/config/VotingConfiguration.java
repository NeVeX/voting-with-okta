package com.nevex.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nevex.dao.VoteRepository;
import com.nevex.dao.VotingInstanceInformationRepository;
import com.nevex.dao.VotingInstancesRepository;
import com.nevex.service.VotingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Mark Cunningham on 11/9/2017.
 */
@Configuration
public class VotingConfiguration {

    @Autowired
    private VotingInstancesRepository votingInstancesRepository;
    @Autowired
    private VotingInstanceInformationRepository votingInstanceInformationRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    VotingService votingService() {
        return new VotingService(votingInstancesRepository, votingInstanceInformationRepository, voteRepository, objectMapper);
    }

}
