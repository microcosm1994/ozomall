package com.ozomall.controller.admin;

import com.google.code.kaptcha.Constants;
import com.ozomall.entity.Result;
import com.ozomall.entity.UserDto;
import com.ozomall.service.admin.UserService;
import com.ozomall.utils.ResultGenerate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Api(tags = "用户相关接口")
@Controller
@RequestMapping("/admin/user")
@ResponseBody
public class UserController {
    @Resource
    private HttpSession session;
    @Resource
    private UserService userService;

    @ApiOperation(value = "登陆")
    @PostMapping(value = "/login")
    public Result login(@RequestBody Map<String, String> loginInfo) {
        // 获取session中的验证码
        Object kaptchaSessionCode = session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        // 比对验证码
        if (loginInfo.get("code").equals(kaptchaSessionCode)) {
            // 调用登陆方法
            return userService.login(loginInfo.get("userName"), loginInfo.get("passWord"));
        } else {
            // 验证码错误
            return ResultGenerate.genErroResult("验证码错误");
        }
    }

    @ApiOperation("获取用户信息")
    @GetMapping(value = "/info")
    public Result info(@RequestParam("token") String token) {
        if (!StringUtils.isEmpty(token)) {
            return userService.getUserInfo(token);
        } else {
            return ResultGenerate.genErroResult("参数不能为空");
        }
    }

    @ApiOperation("获取用户列表")
    @GetMapping(value = "/get")
    public Result getUserList(UserDto form) {
        return userService.getUserList(form);
    }

    @ApiOperation("添加新用户")
    @PostMapping(value = "/add")
    public Result addUser(@RequestBody UserDto form) {
        System.out.println(form.toString());
        return userService.addUser(form);
    }
}
