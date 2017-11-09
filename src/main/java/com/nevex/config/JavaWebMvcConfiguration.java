package com.nevex.config;

import com.nevex.filter.OktaUserArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * Created by Mark Cunningham on 11/9/2017.
 */
@Configuration
public class JavaWebMvcConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(oktaUserArgumentResolver());
    }

    @Bean
    OktaUserArgumentResolver oktaUserArgumentResolver() {
        return new OktaUserArgumentResolver();
    }

}
