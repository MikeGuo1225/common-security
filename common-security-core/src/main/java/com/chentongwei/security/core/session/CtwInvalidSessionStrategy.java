package com.chentongwei.security.core.session;

import org.springframework.security.web.session.InvalidSessionStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * session失效策略
 *
 * @author TongWei.Chen 2018-04-06 21:27:15
 */
public class CtwInvalidSessionStrategy extends AbstractSessionStrategy implements InvalidSessionStrategy {

    /**
     * @param invalidSessionUrl：session失效URL
     */
    public CtwInvalidSessionStrategy(String invalidSessionUrl) {
        super(invalidSessionUrl);
    }

    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        super.onSessionInvalid(request, response);
    }
}
