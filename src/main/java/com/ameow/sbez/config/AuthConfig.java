package com.ameow.sbez.config;

import com.ameow.sbez.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Leslie Leung
 * @description 拦截器配置
 * @date 2021/11/3
 */
@Configuration
public class AuthConfig implements WebMvcConfigurer {

    @Bean
    public AuthInterceptor authInterceptor() {
        return new AuthInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor())
                // 放通静态资源和所有登录接口
                .excludePathPatterns("/static/**")
                .excludePathPatterns("/login")
                .addPathPatterns("/**");

    }
}
