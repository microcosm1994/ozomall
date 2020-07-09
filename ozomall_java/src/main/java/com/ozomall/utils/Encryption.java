package com.ozomall.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Encryption {
    /**
     * 加密
     * @param pwd 密码
     * @return 加密后的密码
     */
    public String encrypt(String pwd) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(pwd);
    }
}
