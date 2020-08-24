package com.ozomall.controller.mall;

import com.aliyuncs.exceptions.ClientException;
import com.ozomall.entity.Result;
import com.ozomall.entity.mall.MallUserDto;
import com.ozomall.entity.mall.MallUserSettingDto;
import com.ozomall.service.mall.MallUserService;
import com.ozomall.utils.ResultGenerate;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@ResponseBody
@RequestMapping("/mall/user")
public class MallUserController {
    @Resource
    MallUserService mallUserService;

    @Resource
    private JedisPool jedisPool;

    @ApiOperation(value = "发送短信验证码")
    @PostMapping(value = "/sendMessage")
    public Result sendMessage(@RequestBody Map<String, String> loginInfo) throws ClientException {
        return mallUserService.sendMessage(loginInfo.get("phone"));
    }

    @ApiOperation(value = "登陆")
    @PostMapping(value = "/login")
    public Result login(@RequestBody MallUserDto user) {
        Jedis jedis = jedisPool.getResource();
        jedis.select(0);
        String code = jedis.get(user.getPhone());
        if (user.getCode().equals(code)) {
            return mallUserService.login(user);
        } else {
            return ResultGenerate.genErroResult("验证码错误");
        }
    }

    @ApiOperation(value = "登出")
    @PostMapping(value = "/logout")
    public Result logout(HttpServletRequest request) {
        Jedis jedis = jedisPool.getResource();
        jedis.select(0);
        String token = request.getHeader("token");
        jedis.del(token);
        return ResultGenerate.genSuccessResult();
    }

    @ApiOperation(value = "获取用户设置")
    @GetMapping(value = "/getSettings")
    public Result getSettings(MallUserSettingDto form) {
        return mallUserService.getSettings(form);
    }

    @ApiOperation(value = "设置用户设置")
    @PostMapping(value = "/setSettings")
    public Result setSettings(@RequestBody MallUserSettingDto form) {
        return mallUserService.setSettings(form);
    }

}
