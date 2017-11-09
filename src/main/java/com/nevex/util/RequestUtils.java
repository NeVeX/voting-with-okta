package com.nevex.util;

import com.nevex.model.OktaUser;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.swing.text.html.Option;
import java.util.Optional;

/**
 * Created by Mark Cunningham on 11/9/2017.
 */
public final class RequestUtils {

    private final static String OKTA_USER = "OKTA_USER";

    public static void setOktaUser(OktaUser user) {
        set(OKTA_USER, user);
    }

    public static Optional<OktaUser> getOktaUser() {
        return get(OKTA_USER, OktaUser.class);
    }

    /**
     * Convenience method to set key value pair into the request scope context.
     * Returns T/F if the data was saved.
     */
    static boolean set(String key, Object data) {
        if ( RequestContextHolder.getRequestAttributes() != null ) {
            RequestContextHolder.getRequestAttributes().setAttribute(key, data, RequestAttributes.SCOPE_REQUEST);
            return true;
        }
        return false;
    }

    /**
     * Convenience method to get the value for the given key from the request scope context, if it exists
     */
    static <T> Optional<T> get(String key, Class<T> clazz) {
        if ( RequestContextHolder.getRequestAttributes() != null ) {
            Object obj = RequestContextHolder.getRequestAttributes().getAttribute(key, RequestAttributes.SCOPE_REQUEST);
            return Optional.ofNullable(clazz.cast(obj));
        }
        return Optional.empty();
    }

}
