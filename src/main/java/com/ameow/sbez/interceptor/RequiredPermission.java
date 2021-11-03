package com.ameow.sbez.interceptor;

import java.lang.annotation.*;

/**
 * @author Leslie Leung
 * @description 权限注解，在controller中对方法添加该注解可以结合拦截器检验用户是否有权限访问该接口
 * @date 2021/11/3
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RequiredPermission {
    int[] value();
}
