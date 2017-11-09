package com.nevex.config;

import com.nevex.filter.OktaAuthenticatedUserExtractorFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationProcessingFilter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * Created by Mark Cunningham on 11/9/2017.
 */
@Configuration
public class VotingWebSecurityConfiguration /*extends WebSecurityConfigurerAdapter*/ {

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .formLogin().disable()
//                .httpBasic().disable()
//                .anonymous().disable()
//                .csrf().disable()
//
//                .authorizeRequests().anyRequest().authenticated();
//        http.addFilterAfter(oktaAuthenticatedUserExtractorFilter(), AbstractPreAuthenticatedProcessingFilter.class);
//    }

    @Bean
    FilterRegistrationBean oktaAuthenticatedUserExtractorFilterBean(@Value("${security.filter-order}") Integer filterOrder) {
        FilterRegistrationBean reg = new FilterRegistrationBean(oktaAuthenticatedUserExtractorFilter());
        reg.setOrder(filterOrder+10);
        return reg;
    }

    @Bean
    OktaAuthenticatedUserExtractorFilter oktaAuthenticatedUserExtractorFilter() {
        return new OktaAuthenticatedUserExtractorFilter();
    }
}
