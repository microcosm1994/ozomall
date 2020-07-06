package com.ozomall.controller.admin;

import com.alibaba.fastjson.JSON;
import com.ozomall.entity.Result;
import com.ozomall.utils.ResultGenerate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/admin")
@ResponseBody
public class UserController {

    @PostMapping(value = "/user/login")
    public Result login(@RequestBody Map user) {
        String userJson = JSON.toJSONString(user);
        System.out.println(user);
        System.out.println(userJson);
        Result result = ResultGenerate.genSuccessResult(user, 0, 0, 0);
        System.out.println(result.toString());
        return result;
    }
}
