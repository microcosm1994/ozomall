package com.ozomall.service.admin.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ozomall.dao.admin.AdminUserMapper;
import com.ozomall.entity.Result;
import com.ozomall.entity.admin.AdminUserDto;
import com.ozomall.service.admin.AdminUserService;
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
        LambdaQueryWrapper<AdminUserDto> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AdminUserDto::getUserName,userName);
        AdminUserDto userResult = adminUserMapper.selectOne(wrapper);
        if (userResult == null) {
            return ResultGenerate.genErroResult("用户名或密码错误");
        }
        // 验证密码
        boolean flag = encoder.matches(passWord, userResult.getPassWord());
        if (flag) {
            String token = AuthUtils.genToken(userName);
            resultData.put("token", token);
            String jsonTokenValue = AuthUtils.setToken(token, userResult);
            redisTemplate.opsForValue().set(token, jsonTokenValue);
            return ResultGenerate.genSuccessResult(resultData);
        } else {
            return ResultGenerate.genErroResult("密码错误");
        }
    }

    /**
     * 获取用户信息
     *
     * @param token 用户token
     * @return 返回用户信息
     */
    @Override
    public Result getUserInfo(String token) {
        String tokenValue = redisTemplate.opsForValue().get(token);
        String userName = JSONObject.parseObject(tokenValue).getString("userName");
        LambdaQueryWrapper<AdminUserDto> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AdminUserDto::getUserName,userName);
        AdminUserDto userResult = adminUserMapper.selectOne(wrapper);
        return ResultGenerate.genSuccessResult(userResult);
    }
}
