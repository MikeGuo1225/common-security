package com.chentongwei.security.core.social;

import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.social.connect.Connection;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 查看绑定状态功能SpringSocial已经帮我们做好了，
 * ConnectController.connectionStatus()
 *
 * 最后返回了一个视图：
 * return connectView();
 * protected String connectView() {
 *     return getViewPath() + "status";
 * }
 *
 * getViewPath()==》connect/
 * 因为没有这个视图（connect/status），所以报错。
 * 我们这里要写这个connect/status的类
 *
 * @author TongWei.Chen 2018-04-04 23:26:22
 */
@Component("connect/status")
public class CtwConnectionStatusView extends AbstractView {
    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        /**
         * ConnectController.connectionStatus()；已经将绑定关系connectionMap放到了model里。所以这里取出来，
         * 然后遍历看哪个服务绑定了。
         */

        Map<String, List<Connection<?>>> connectionMap = (Map<String, List<Connection<?>>>) model.get("connectionMap");

        Map<String, Boolean> bindMaps = new HashMap<>();
        if (null != connectionMap && connectionMap.size() > 0) {
            for (String key : connectionMap.keySet()) {
                bindMaps.put(key, CollectionUtils.isNotEmpty(connectionMap.get(key)));
            }
        }

        response.setContentType("application/json;charset=utf-8");
        response.getWriter().print(JSON.toJSONString(bindMaps));
    }
}
