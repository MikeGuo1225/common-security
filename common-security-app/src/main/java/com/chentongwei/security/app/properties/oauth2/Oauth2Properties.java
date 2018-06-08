package com.chentongwei.security.app.properties.oauth2;

import java.util.ArrayList;
import java.util.List;

/**
 * oauth2的基本配置
 *
 * @author chentongwei@bshf360.com 2018-06-07 19:52
 */
public class Oauth2Properties {

    /** jwt密签key */
    private String jwtSigningKey = "chentongwei";

    /** 客户端数组 */
    private List<ClientProperties> clients = new ArrayList();

    public String getJwtSigningKey() {
        return jwtSigningKey;
    }

    public void setJwtSigningKey(String jwtSigningKey) {
        this.jwtSigningKey = jwtSigningKey;
    }

    public List<ClientProperties> getClients() {
        return clients;
    }

    public void setClients(List<ClientProperties> clients) {
        this.clients = clients;
    }
}
