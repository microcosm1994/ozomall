package com.ozomall.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ozomall.dao.AdminUserMapper;
import com.ozomall.entity.Result;
import com.ozomall.entity.UserDto;
import com.ozomall.service.AdminUserService;
import com.ozomall.utils.AuthUtils;
import com.ozomall.utils.ResultGenerate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class AdminUserServiceImpl implements AdminUserService {
    @Resource
    private AdminUserMapper adminUserMapper;
    @Resource
    RedisTemplate<String, String> redisTemplate;

    /**
     * 登陆
     *
     * @param userName 账号
     * @param passWord 密码
     * @return 成功返回true, 失败返回false
     */
    @Override
    public Result login(String userName, String passWord) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Map resultData = new HashMap();
        UserDto userInfo = adminUserMapper.login(userName);
        if (userInfo == null) {
            return ResultGenerate.genErroResult("用户名或密码错误");
        }
        // 验证密码
        boolean flag = encoder.matches(passWord, userInfo.getPassWord());
        if (flag) {
            String token = AuthUtils.genToken(userName);
            resultData.put("token", token);
            String jsonTokenValue = AuthUtils.setToken(token, userInfo);
            redisTemplate.opsForValue().set(token, jsonTokenValue);
            return ResultGenerate.genSuccessResult(resultData);
        } else {
            return ResultGenerate.genErroResult("密码错误");
        }
    }

    /**
     * 登陆
     *
     * @param token 用户token
     * @return 成功返回true, 失败返回false
     */
    @Override
    public Result getUserInfo(String token) {
        String tokenValue = redisTemplate.opsForValue().get(token);
        String userName = JSONObject.parseObject(tokenValue).getString("userName");
        UserDto userInof = adminUserMapper.find(userName, null, null, null);
        return ResultGenerate.genSuccessResult(userInof);
    }
}
