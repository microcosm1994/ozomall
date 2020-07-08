package com.ozomall.service;

import com.ozomall.dao.AdminUserMapper;
import com.ozomall.entity.UserDto;
import com.ozomall.utils.Encryption;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdminUserService {
    @Resource
    private AdminUserMapper adminUserMapper;

    public Boolean login(String userName, String passWord) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Encryption encrypt = new Encryption();
        UserDto userForm = new UserDto();
        userForm.setUserName(userName);
        UserDto userInfo = adminUserMapper.find(userForm.getUserName(), userForm.getNickName(), userForm.getRole(), userForm.getPhone());
        if (userInfo == null) {
            return false;
        }
        String enPass = encrypt.encrypt(passWord);
        System.out.println(userForm.toString());
        System.out.println(userInfo.toString());
        System.out.println(enPass);
        boolean flag = encoder.matches(passWord, userInfo.getPassWord());
        return flag;
    }
}
