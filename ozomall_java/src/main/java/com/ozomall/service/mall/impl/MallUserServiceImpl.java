package com.ozomall.service.mall.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ozomall.dao.mall.MallUserMapper;
import com.ozomall.dao.mall.MallUserSettingMapper;
import com.ozomall.dao.mall.MallUserSocialMapper;
import com.ozomall.entity.Result;
import com.ozomall.entity.mall.MallUserDto;
import com.ozomall.entity.mall.MallUserSettingDto;
import com.ozomall.entity.mall.MallUserSocialDto;
import com.ozomall.service.mall.MallUserService;
import com.ozomall.utils.AuthUtils;
import com.ozomall.utils.ResultGenerate;
import com.ozomall.utils.Sms;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class MallUserServiceImpl implements MallUserService {

    @Resource
    MallUserMapper mallUserMapper;

    @Resource
    MallUserSettingMapper mallUserSettingMapper;

    @Resource
    MallUserSocialMapper mallUserSocialMapper;

    @Resource
    private Sms sms;

    @Resource
    private JedisPool jedisPool;

    private String code2SessionUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=wxd2c7f2caf86e4478&secret=501f67083890471d14634c3196ba75d9&grant_type=authorization_code&js_code=";

    /**
     * 发送短信验证码
     *
     * @param phone
     */
    @Override
    public Result sendMessage(String phone) throws ClientException {
        String code = sms.sendMessage(phone);
        if (!StringUtils.isEmpty(code)) {
            // 保存用户短信验证码到redis
            Jedis jedis = jedisPool.getResource();
            jedis.select(0);
            jedis.setex(phone, 60 * 10, code);
            return ResultGenerate.genSuccessResult();
        } else {
            return ResultGenerate.genErroResult("发送短息失败");
        }
    }

    /**
     * 手机号登录
     *
     * @param user
     */
    @Override
    public Result phoneLogin(MallUserDto user) {
        Map<String, Object> data = new HashMap<>();
        Result result = this.getUser(user);
        Object userInfo = result.getData();
        String token = AuthUtils.genToken(user.getPhone());
        Jedis jedis = jedisPool.getResource();
        jedis.select(0);
        jedis.set(token, user.getPhone());
        data.put("token", token);
        // 有账号登录
        if (userInfo != null) {
            data.put("users", userInfo);
            return ResultGenerate.genSuccessResult(data);
        } else {
            // 无账号注册
            user.setNickName(user.getPhone());
            int row = mallUserMapper.insert(user);
            if (row > 0) {
                // 保存用户设置
                initUserSetting(user.getId());
                data.put("users", user);
                return ResultGenerate.genSuccessResult(data);
            } else {
                return ResultGenerate.genErroResult("登陆失败");
            }
        }
    }


    /**
     * 微信登录
     *
     * @param user
     */
    @Override
    public Result wxLogin(MallUserDto user) {
        Map<String, Object> data = new HashMap<>();
        Result result = this.getUser(user);
        Object userInfo = result.getData();
        String token = AuthUtils.genToken(user.getPhone());
        Jedis jedis = jedisPool.getResource();
        jedis.select(0);
        jedis.set(token, user.getPhone());
        data.put("token", token);
        // 有账号登录
        if (userInfo != null) {
            data.put("users", userInfo);
            return ResultGenerate.genSuccessResult(data);
        } else {
            // 无账号注册
            int row = mallUserMapper.insert(user);
            if (row > 0) {
                // 保存用户设置
                initUserSetting(user.getId());
                // 绑定微信号
                this.wxBinding(user);
                data.put("users", user);
                return ResultGenerate.genSuccessResult(data);
            } else {
                return ResultGenerate.genErroResult("登陆失败");
            }
        }
    }

    /**
     * 微信账号绑定
     *
     * @param user
     */
    public int wxBinding(MallUserDto user) {
        if (user.getWxCode().isEmpty()) {
            return 0;
        }
        // 获取wx openid
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(code2SessionUrl + user.getWxCode(), String.class);
        JSONObject body = JSON.parseObject(response.getBody());
        // 保存用户微信openid
        MallUserSocialDto socialData = new MallUserSocialDto();
        socialData.setOpenid((String) body.get("openid")); // 保存openid
        socialData.setUserId(user.getId()); // 保存用户id
        socialData.setSocialType(0); // 设置账号类型为0：微信
        int row = mallUserSocialMapper.insert(socialData);
        return row;
    }

    /**
     * 初始化用户设置
     *
     * @param id 新注册用户初始化账号设置数据
     */
    public int initUserSetting(int id) {
        MallUserSettingDto sData = new MallUserSettingDto();
        sData.setUserId(id);
        int row = mallUserSettingMapper.insert(sData);
        return row;
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

    /**
     * 获取用户设置
     */
    @Override
    public Result getSettings(MallUserSettingDto form) {
        QueryWrapper<MallUserSettingDto> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", form.getUserId());
        MallUserSettingDto row = mallUserSettingMapper.selectOne(wrapper);
        if (row != null) {
            return ResultGenerate.genSuccessResult(row);
        } else {
            return ResultGenerate.genErroResult("获取失败");
        }
    }

    /**
     * 设置用户设置
     */
    @Override
    public Result setSettings(MallUserSettingDto form) {
        int row = mallUserSettingMapper.updateById(form);
        if (row > 0) {
            return ResultGenerate.genSuccessResult();
        } else {
            return ResultGenerate.genErroResult("获取失败");
        }
    }

}
