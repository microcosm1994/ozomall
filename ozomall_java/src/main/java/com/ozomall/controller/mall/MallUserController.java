package com.ozomall.controller.mall;

import com.aliyuncs.exceptions.ClientException;
import com.ozomall.entity.Result;
import com.ozomall.entity.mall.MallUserDto;
import com.ozomall.service.mall.MallUserService;
import com.ozomall.utils.ResultGenerate;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    private RedisTemplate<String, String> redisTemplate;

    @ApiOperation(value = "发送短信验证码")
    @PostMapping(value = "/sendMessage")
    public Result sendMessage(@RequestBody Map<String, String> loginInfo) throws ClientException {
        return mallUserService.sendMessage(loginInfo.get("phone"));
    }

    @ApiOperation(value = "登陆")
    @PostMapping(value = "/login")
    public Result login(@RequestBody MallUserDto user) {
        String code = redisTemplate.opsForValue().get(user.getPhone());
        if (user.getCode().equals(code)) {
            return mallUserService.login(user);
        } else {
            return ResultGenerate.genErroResult("验证码错误");
        }
    }

    @ApiOperation(value = "登陆")
    @PostMapping(value = "/logout")
    public Result logout(HttpServletRequest request) {
        String token = request.getHeader("token");
        redisTemplate.delete(token);
        return ResultGenerate.genSuccessResult();
    }

}
