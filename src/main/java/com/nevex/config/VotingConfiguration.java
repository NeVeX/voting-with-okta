package com.nevex.config;

import com.nevex.service.VotingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Mark Cunningham on 11/9/2017.
 */
@Configuration
public class VotingConfiguration {

    @Bean
    VotingService votingService() {
        return new VotingService();
    }

}
