package com.ozomall.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Encryption {
    public String encrypt(String pwd) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodePasswod = encoder.encode(pwd);
        return encodePasswod;
    }
}
