package com.ozomall.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@PropertySource(value = "classpath:config.properties")
public class SignUtilsTest {
    @Value("${wechat.appid}")
    private String appid; // 微信分配的小程序ID
    // 测试
    @Test
    public void test() throws Exception {
        System.out.println(appid);
    }
}
