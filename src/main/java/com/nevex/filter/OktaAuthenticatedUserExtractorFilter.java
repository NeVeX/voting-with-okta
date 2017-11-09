package com.nevex.filter;

import com.nevex.model.OktaUser;
import com.nevex.util.RequestUtils;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

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
            // madness
            if (authentication instanceof OAuth2Authentication) {
                OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) authentication;
                if (oAuth2Authentication.getUserAuthentication() instanceof UsernamePasswordAuthenticationToken) {
                    UsernamePasswordAuthenticationToken passToken = (UsernamePasswordAuthenticationToken) oAuth2Authentication.getUserAuthentication();
                    extractOktaUserDetails((Map) passToken.getDetails());
                }
            }
        } finally {
            chain.doFilter(request, response);
        }
    }

    private void extractOktaUserDetails(Map details) {
        if ( details.containsKey("email")) {
            String email = details.get("email").toString();
            RequestUtils.setOktaUser(new OktaUser(email));
        }
    }

    @Override
    public void destroy() {

    }
}
