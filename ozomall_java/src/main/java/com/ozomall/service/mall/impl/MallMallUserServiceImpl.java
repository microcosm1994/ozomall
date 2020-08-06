package com.ozomall.service.mall.impl;

import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ozomall.dao.mall.MallUserMapper;
import com.ozomall.entity.Result;
import com.ozomall.entity.mall.MallUserDto;
import com.ozomall.service.mall.MallUserService;
import com.ozomall.utils.AuthUtils;
import com.ozomall.utils.ResultGenerate;
import com.ozomall.utils.Sms;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class MallMallUserServiceImpl implements MallUserService {
    @Resource
    HttpServletRequest request;

    @Resource
    MallUserMapper mallUserMapper;

    @Resource
    RedisTemplate<String, String> redisTemplate;

    /**
     * 发送短信验证码
     *
     * @param phone
     */
    @Override
    public Result sendMessage(String phone) throws ClientException {
        String code = Sms.sendMessage(phone);
        if (!StringUtils.isEmpty(code)) {
            // 保存用户短信验证码到redis
            redisTemplate.opsForValue().set(phone, code, 10, TimeUnit.MINUTES);
            return ResultGenerate.genSuccessResult();
        } else {
            return ResultGenerate.genErroResult("发送短息失败");
        }
    }

    /**
     * 登录
     *
     * @param user
     */
    @Override
    public Result login(MallUserDto user) {
        Map<String, Object> data = new HashMap<>();
        Result result = this.getUser(user);
        Object userInfo = result.getData();
        String token = AuthUtils.genToken(user.getPhone());
        redisTemplate.opsForValue().set(token, user.getPhone());
        data.put("token", token);
        if (userInfo != null) {
            data.put("users", userInfo);
            return ResultGenerate.genSuccessResult(data);
        } else {
            int row = mallUserMapper.insert(user);
            if (row > 0) {
                data.put("users", user);
                return ResultGenerate.genSuccessResult(data);
            } else {
                return ResultGenerate.genErroResult("登陆失败");
            }
        }
    }

    /**
     * 查询用户
     *
     * @param user
     */
    @Override
    public Result getUser(MallUserDto user) {
        QueryWrapper<MallUserDto> wrapper = new QueryWrapper<>();
        wrapper.eq("phone", user.getPhone());
        MallUserDto row = mallUserMapper.selectOne(wrapper);
        if (row != null) {
            return ResultGenerate.genSuccessResult(row);
        } else {
            return ResultGenerate.genErroResult("获取失败");
        }
    }

}
