package com.ozomall.config;

import com.ozomall.interceptor.AdminInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class OzomallWebMvcConfig extends WebMvcConfigurerAdapter {
    @Resource
    private AdminInterceptor adminInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludePathList = new ArrayList<>();
        excludePathList.add("/admin/verify/code");
        excludePathList.add("/admin/user/login");
        excludePathList.add("/mall/goods/**");
        excludePathList.add("/mall/user/**");
        registry.addInterceptor(adminInterceptor).excludePathPatterns(excludePathList);
        super.addInterceptors(registry);
    }
}
