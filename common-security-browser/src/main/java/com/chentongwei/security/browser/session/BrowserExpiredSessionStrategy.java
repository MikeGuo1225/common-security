package com.chentongwei.security.browser.session;

import com.chentongwei.security.browser.session.template.AbstractSessionStrategyTemplate;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * session过期（被T掉）的策略
 *
 * @author TongWei.Chen 2018-04-06 17:44:31
 */
public class BrowserExpiredSessionStrategy extends AbstractSessionStrategyTemplate implements SessionInformationExpiredStrategy {

    /**
     * @param invalidSessionUrl：session过期URL
     */
    public BrowserExpiredSessionStrategy(String invalidSessionUrl) {
        super(invalidSessionUrl);
    }

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        super.onSessionInvalid(event.getRequest(), event.getResponse());
    }

    @Override
    protected boolean isConcurrency() {
        return true;
    }
}
