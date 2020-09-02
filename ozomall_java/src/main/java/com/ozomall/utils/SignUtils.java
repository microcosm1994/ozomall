package com.ozomall.utils;

import com.ozomall.entity.WxPayDto;
import org.springframework.util.DigestUtils;
import org.springframework.cglib.beans.BeanMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SignUtils {
    public static String genSignStr(WxPayDto form, String secretKey) {
        Map data = BeanMap.create(form);
        List keys = new ArrayList(data.keySet());
        Collections.sort(keys);
        String sign = "";
        for (Object key : keys) {
            Object value = data.get(key);
            if (null != value && !"".equals(value)) {
                sign += key + "=" + value + "&";
            }
        }
        sign = sign + "key=" + secretKey;
        String signMd5 = DigestUtils.md5DigestAsHex(sign.getBytes());
        return signMd5;
    }
}
