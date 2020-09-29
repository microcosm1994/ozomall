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
        excludePathList.add("/banner/get");
        excludePathList.add("/classify/list");
        excludePathList.add("/classify/childrenList");
        excludePathList.add("/goods/get");
        excludePathList.add("/goods/getGoodsPic");
        excludePathList.add("/goods/getGoodsAttr");
        excludePathList.add("/goods/getGoodsSkuList");
        excludePathList.add("/goods/getGoodsParams");
        excludePathList.add("/goods/getGoodsBrandInfo");
        excludePathList.add("/mall/orders/getBuyList");
        excludePathList.add("/mall/goods/**");
        excludePathList.add("/mall/user/sendMessage");
        excludePathList.add("/mall/user/login");
        registry.addInterceptor(adminInterceptor).excludePathPatterns(excludePathList);
        super.addInterceptors(registry);
    }
}
