package com.nevex.filter;

import com.nevex.model.OktaUser;
import com.nevex.util.RequestUtils;
import org.springframework.core.MethodParameter;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Optional;

/**
 * Created by Mark Cunningham on 11/9/2017.
 */
public class OktaUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(OktaUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        Optional<OktaUser> oktaUserOpt = RequestUtils.getOktaUser();
        if ( oktaUserOpt.isPresent()) {
            return oktaUserOpt.get();
        } else {
            throw new AuthenticationServiceException("The request is not authenticated with a user");
        }

    }
}
