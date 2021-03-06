package com.nevex.config.property;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotNull;

/**
 * Created by Mark Cunningham on 11/12/2017.
 */
@Configuration
@ConfigurationProperties(prefix = "voting-with-okta")
public class VotingWithOktaProperties {

    @NotBlank
    private String samlHostname;
    @NotBlank
    private String keystoreResource;
    @NotBlank
    private String adminKey;
    @NotBlank
    private String protocol;
    @NotNull
    private Boolean addTestData;

    public String getAdminKey() {
        return adminKey;
    }

    public void setAdminKey(String adminKey) {
        this.adminKey = adminKey;
    }

    public String getKeystoreResource() {
        return keystoreResource;
    }

    public void setKeystoreResource(String keystoreResource) {
        this.keystoreResource = keystoreResource;
    }

    public String getSamlHostname() {
        return samlHostname;
    }

    public void setSamlHostname(String samlHostname) {
        this.samlHostname = samlHostname;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public Boolean getAddTestData() {
        return addTestData;
    }

    public void setAddTestData(Boolean addTestData) {
        this.addTestData = addTestData;
    }
}
