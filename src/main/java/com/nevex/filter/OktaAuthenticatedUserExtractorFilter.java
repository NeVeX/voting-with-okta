package com.nevex.filter;

import com.nevex.model.OktaUser;
import com.nevex.util.RequestUtils;
import org.apache.commons.lang3.StringUtils;
import org.opensaml.saml2.core.impl.AbstractNameIDType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Mark Cunningham on 11/9/2017.
 */
public class OktaAuthenticatedUserExtractorFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if ( !tryOauthExtraction(authentication)) {
                // try saml
                trySamlExtraction(authentication);
            }

        } finally {
            chain.doFilter(request, response);
        }
    }

    private void trySamlExtraction(Authentication authentication) {
        if ( authentication.getPrincipal() instanceof AbstractNameIDType) {
            AbstractNameIDType nameId = (AbstractNameIDType) authentication.getPrincipal();
            if (StringUtils.isNotBlank(nameId.getValue())) {
                setOktaUserOnRequest(nameId.getValue());
            }
        }
    }

    private void extractAndSetOktaUserDetails(Map details) {
        if ( details.containsKey("email")) {
            String email = details.get("email").toString();
            setOktaUserOnRequest(email);
        }
    }

    private void setOktaUserOnRequest(String username) {
        RequestUtils.setOktaUser(new OktaUser(username));
    }

    private boolean tryOauthExtraction(Authentication authentication) {
//        // madness
//        if (authentication instanceof OAuth2Authentication) {
//            OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) authentication;
//            if (oAuth2Authentication.getUserAuthentication() instanceof UsernamePasswordAuthenticationToken) {
//                UsernamePasswordAuthenticationToken passToken = (UsernamePasswordAuthenticationToken) oAuth2Authentication.getUserAuthentication();
//                extractAndSetOktaUserDetails((Map) passToken.getDetails());
//            }
//        }
        return false;
    }

    @Override
    public void destroy() {

    }
}
