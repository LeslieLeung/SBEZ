package com.ameow.sbez.controller;

import com.alibaba.fastjson.JSONObject;
import com.ameow.sbez.constants.Roles;
import com.ameow.sbez.interceptor.RequiredPermission;
import com.ameow.sbez.service.TestService;
import com.ameow.sbez.utils.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Leslie Leung
 * @description 测试用的控制器
 * @date 2021/11/3
 */
@RestController
public class TestController {
    @Autowired
    private TestService testService;


    /**
     * 登录接口
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/login")
    public JsonResponse<Object> adminLogin(@RequestBody JSONObject jsonRequest) {
        String username = jsonRequest.getString("username");
        String password = jsonRequest.getString("password");

        int id = testService.checkPassword(username, password);
        if (id == 0) {
            return JsonResponse.error("用户名或密码错误");
        }

        int role = testService.getRole(id);
        String token = testService.grantToken(id, role);
        if (token.isEmpty()) {
            return JsonResponse.error("服务器出错，请重试");
        }
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("id", id);
        result.put("level", role);
        return JsonResponse.success(result);
    }

    /**
     * 获取当前用户信息
     * @return
     */
    @RequestMapping("/getCurrentUser")
    @RequiredPermission({Roles.ROLE_ADMIN, Roles.ROLE_USER})
    public JsonResponse<Object> testGetCurrentUser() {
        Map<String, Object> currentUser = getCurrentUser();
        Map<String, Object> result = new HashMap<>();
        result.put("id", currentUser.get("id"));
        result.put("role", currentUser.get("role"));
        return JsonResponse.success(result);
    }

    /**
     * 获取目前用户的id和role
     * @return id和role数组
     */
    private Map<String, Object> getCurrentUser() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        Map<String, Object> result = new HashMap<>();
        result.put("id", request.getAttribute("id"));
        result.put("role", request.getAttribute("role"));
        return result;
    }

    @RequestMapping("/userTest")
    @RequiredPermission({Roles.ROLE_USER})
    public JsonResponse<Object> userTest() {
        return JsonResponse.success("成功使用用户接口");
    }

    @RequestMapping("/adminTest")
    @RequiredPermission({Roles.ROLE_ADMIN})
    public JsonResponse<Object> adminTest() {
        return JsonResponse.success("成功使用管理员接口");
    }
}
