package com.ozomall.config;

import com.ozomall.interceptor.AdminInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class OzomallWebMvcConfig implements WebMvcConfigurer {
    @Resource
    private AdminInterceptor adminInterceptor;

    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludePathList = new ArrayList<>();
        excludePathList.add("/admin/verify/code");
        excludePathList.add("/admin/user/login");
        registry.addInterceptor(adminInterceptor).addPathPatterns("/admin/**").excludePathPatterns(excludePathList);
    }
}
