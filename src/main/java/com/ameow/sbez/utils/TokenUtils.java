package com.ameow.sbez.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.UUID;

/**
 * @author Leslie Leung
 * @description token工具类
 * @date 2021/11/3
 */
@Component
public class TokenUtils {
    @Autowired
    private RedisUtils redisUtils;

    /**
     * 发放token
     * @param id 用户id
     * @param role 权限
     * @return 获取到的token
     */
    public String grantToken(int id, int role) {
        HashMap<String, Integer> identity = new HashMap<>();
        identity.put("id", id);
        identity.put("role", role);

        String key = UUID.randomUUID().toString().replace("-", "");
        // token有效期为5周
        boolean result = redisUtils.set(key, identity, 3024000);
        // 删掉之前如果有效的token
        String formerToken = (String) redisUtils.get(String.format("user_%s", id));
        if (formerToken != null) {
            destroyToken(formerToken);
        }
        redisUtils.set(String.format("user_%s", id), key);
        if (result) {
            return key;
        } else {
            return "";
        }
    }

    /**
     * 获取token信息
     * @param key token
     * @return uid和role的map
     */
    private HashMap<String, Integer> retrieveToken(String key) {
        return (HashMap<String, Integer>) redisUtils.get(key);
    }

    /**
     * 获取token对应的权限
     * @param key token
     * @return 权限
     */
    public int getTokenRole(String key) {
        int role = -1;
        try {
            role = retrieveToken(key).get("role");
        } catch (Exception e) {}
        return role;
    }

    /**
     * 获取token对应的id
     * @param key token
     * @return id
     */
    public int getTokenId(String key) {
        return retrieveToken(key).get("id");
    }

    /**
     * 删除token
     * !! 仅限本类内使用 !!
     * @param key
     */
    private void destroyToken(String key) {
        redisUtils.del(key);
    }

    /**
     * 安全退出
     * @param key token
     */
    public void safeExit(String key) {
        int id = getTokenId(key);
        redisUtils.del(String.format("user_%s", id));
        destroyToken(key);
    }
}
