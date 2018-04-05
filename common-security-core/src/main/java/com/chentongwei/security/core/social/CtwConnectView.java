package com.chentongwei.security.core.social;

import com.alibaba.fastjson.JSON;
import com.chentongwei.security.core.entity.SimpleResponse;
import com.chentongwei.security.core.properties.SecurityProperties;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 *  绑定操作会触发一个请求：
 *  ConnectController.connectionStatus【{providerId}】()
 *  return connectView(providerId);
 *
 *  PS:connectedView是解绑操作
 *
 *  protected String connectView(String providerId) {return getViewPath() + providerId + "Connect";}
 *  getViewPath() + providerId + "Connect" ==> connect/providerIdConnect
 *
 *  此处不直接定义@Component(connect/{providerId}Connect的原因是因为QQ等其他第三方登录也会用此类，
 *  所以各自在各自的配置文件中@Bean定义。)
 *
 * @author TongWei.Chen 2018-04-04 23:47:25
 */
public class CtwConnectView extends AbstractView {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        if (null == model.get("connections")) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().print(JSON.toJSONString(new SimpleResponse(200, "解绑成功", null)));
        } else {
            response.setContentType("text/html;charset=UTF-8");
            if (StringUtils.isBlank(securityProperties.getBrowser().getBindSuccessPage())) {
                response.getWriter().write("<h3>绑定成功</h3>");
                return;
            } else {
                response.sendRedirect(securityProperties.getBrowser().getBindSuccessPage());
            }
        }
    }
}
