package com.ameow.sbez.interceptor;

import com.alibaba.fastjson.JSON;
import com.ameow.sbez.utils.JsonResponse;
import com.ameow.sbez.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Leslie Leung
 * @description 权限拦截器，根据controller上的RequiredPermission注解检查token是否有需要的权限，并在request中把uid和role传给controller
 * @date 2021/11/3
 */
public class AuthInterceptor implements HandlerInterceptor {
    @Autowired
    private TokenUtils tokenUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        // 无token参数拒绝，放通的登录接口除外
        if (request.getHeader("token") == null || request.getHeader("Token").isEmpty()) {
            response.getWriter().write(JSON.toJSONString(JsonResponse.error("缺少token参数")));
            return false;
        }
        String token = request.getHeader("Token");
        int role = tokenUtils.getTokenRole(token);
        if (role == -1) {
            response.getWriter().write(JSON.toJSONString(JsonResponse.error("token无效")));
            return false;
        }
        if (this.hasPermission(handler, role)) {
            int id = tokenUtils.getTokenId(token);
            // 权限塞request里传给controller
            request.setAttribute("id", id);
            request.setAttribute("role", role);
            return true;
        }
        response.getWriter().write(JSON.toJSONString(JsonResponse.error("权限不足")));
        return false;
    }

    private boolean hasPermission(Object handler, int role) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            RequiredPermission requiredPermission = handlerMethod.getMethod().getAnnotation(RequiredPermission.class);
            if (requiredPermission == null) {
                requiredPermission = handlerMethod.getMethod().getDeclaringClass().getAnnotation(RequiredPermission.class);
            }
            if (requiredPermission != null) {
                for (int permission : requiredPermission.value()) {
                    if (role == permission) {
                        return true;
                    }
                }
            } else {
                return true;
            }
        }
        return false;
    }
}
