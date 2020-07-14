package com.ozomall.controller.admin;

import com.ozomall.entity.AdminClassifyDto;
import com.ozomall.entity.Result;
import com.ozomall.service.AdminClassifyService;
import com.ozomall.utils.ResultGenerate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping("/admin/classify")
@ResponseBody
public class ClassifyController {
    @Resource
    private AdminClassifyService adminClassifyService;

    /**
     * 添加分类
     */
    @PostMapping("/add")
    public Result addClassify(@RequestBody AdminClassifyDto form) {
        if (!StringUtils.isEmpty(form.getName()) && !StringUtils.isEmpty(form.getClassifyLevel())) {
            return adminClassifyService.addClassify(form);
        } else {
            return ResultGenerate.genErroResult("分类等级与分类名称不能为空");
        }
    }

    /**
     * 查询类别分组
     */
    @GetMapping("/list")
    public Result queryClassify(AdminClassifyDto form) {
        return adminClassifyService.queryClassify(form);
    }
}
