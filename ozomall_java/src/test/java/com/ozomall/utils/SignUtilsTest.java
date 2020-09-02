package com.ozomall.utils;

import com.ozomall.entity.WxPayDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SignUtilsTest {
    // 测试
    @Test
    public void test() throws Exception {
        WxPayDto wxDto = new WxPayDto();
        System.out.println(wxDto.toString());
        String nonce_str = RandomUtil.getRandomStr(); // 随机字符串
        wxDto.setNonce_str(nonce_str);
        String sign = SignUtils.genSignStr(wxDto, "ss"); // 签名
        System.out.printf(sign);
        wxDto.setSign(sign);
    }
}
