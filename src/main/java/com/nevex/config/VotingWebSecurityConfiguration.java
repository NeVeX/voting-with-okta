package com.nevex.config;

import com.nevex.config.property.VotingWithOktaProperties;
import com.nevex.filter.OktaAuthenticatedUserExtractorFilter;
import org.apache.bcel.verifier.statics.LONG_Upper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.embedded.Ssl;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.saml.websso.WebSSOProfileConsumer;
import org.springframework.security.saml.websso.WebSSOProfileConsumerImpl;
import org.springframework.security.saml.websso.WebSSOProfileOptions;

import javax.annotation.PostConstruct;

import java.lang.reflect.Field;

import static org.springframework.security.extensions.saml2.config.SAMLConfigurer.saml;

/**
 * Created by Mark Cunningham on 11/9/2017.
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@Configuration
public class VotingWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final static Logger LOGGER = LoggerFactory.getLogger(VotingWebSecurityConfiguration.class);

    @Autowired
    private ServerProperties serverProperties;
    @Autowired
    private VotingWithOktaProperties votingWithOktaProperties;

    @Value("${security.saml2.metadata-url}")
    String metadataUrl;

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

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                // Spring Security should completely ignore URLs starting with /resources/
                .antMatchers("/resources/**");
        super.configure(web);
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        Ssl ssl = serverProperties.getSsl();

        http
            .authorizeRequests()
                .antMatchers("/saml*").permitAll()
                .anyRequest().authenticated()
            .and()
                .apply(saml())
                .serviceProvider()
                .keyStore()
                .storeFilePath(votingWithOktaProperties.getKeystoreResource())
                .password(ssl.getKeyStorePassword())
                .keyname(ssl.getKeyAlias())
                .keyPassword(ssl.getKeyStorePassword())
            .and()
                .protocol(votingWithOktaProperties.getProtocol())
                .hostname(votingWithOktaProperties.getSamlHostname())
                .basePath(serverProperties.getContextPath())
            .and()
                .identityProvider()
                .metadataFilePath(metadataUrl);

        LOGGER.info("\n\n\n\nUsing meta data url: "+metadataUrl);
    }

}
