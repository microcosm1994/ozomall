package com.ozomall.service.admin.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ozomall.dao.admin.AdminUserMapper;
import com.ozomall.entity.Result;
import com.ozomall.entity.admin.AdminUserDto;
import com.ozomall.service.admin.AdminUserService;
import com.ozomall.utils.AuthUtils;
import com.ozomall.utils.ResultGenerate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
        wrapper.eq(AdminUserDto::getUserName, userName);
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
            redisTemplate.opsForValue().set(token, jsonTokenValue, 12, TimeUnit.HOURS);
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
        AdminUserDto userResult = adminUserMapper.getUsers(userName);
        if (userResult != null) {
            return ResultGenerate.genSuccessResult(userResult);
        } else {
            return ResultGenerate.genErroResult("失败");
        }
    }

    /**
     * 获取用户列表
     *
     * @param form
     * @return 返回用户列表
     */
    @Override
    public Result getUserList(AdminUserDto form) {
        Page page = new Page();
        page.setPages(form.getPage());
        page.setSize(form.getSize());
        IPage<Map> rows = adminUserMapper.userList(page, form);
        if (rows != null) {
            return ResultGenerate.genSuccessResult(rows);
        } else {
            return ResultGenerate.genErroResult("失败");
        }
    }

    /**
     * 添加新用户
     *
     * @param form
     */
    @Override
    public Result addUser(AdminUserDto form) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String pwd = encoder.encode(form.getPassWord());
        form.setPassWord(pwd);
        int row = adminUserMapper.insert(form);
        if (row > 0) {
            return ResultGenerate.genSuccessResult();
        } else {
            return ResultGenerate.genErroResult("失败");
        }
    }

    /**
     * 修改用户
     *
     * @param form
     */
    @Override
    public Result putUser(AdminUserDto form) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!StringUtils.isEmpty(form.getPassWord())) {
            String pwd = encoder.encode(form.getPassWord());
            form.setPassWord(pwd);
        }
        int row = adminUserMapper.updateById(form);
        if (row > 0) {
            return ResultGenerate.genSuccessResult();
        } else {
            return ResultGenerate.genErroResult("失败");
        }
    }

    /**
     * 删除用户
     *
     * @param form
     */
    @Override
    public Result delUser(AdminUserDto form) {
        AdminUserDto user = adminUserMapper.selectById(form.getId());
        if ("admin".equals(user.getUserName())) {
            return ResultGenerate.genErroResult("admin账号不能删除");
        }
        int row = adminUserMapper.deleteById(form.getId());
        if (row > 0) {
            return ResultGenerate.genSuccessResult();
        } else {
            return ResultGenerate.genErroResult("失败");
        }
    }
}
