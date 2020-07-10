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
     * 新建分类
     */
    @PostMapping("/add")
    public Result addClassify(@RequestBody AdminClassifyDto form) {
        if (!StringUtils.isEmpty(form.getName())) {
            return adminClassifyService.addClassify(form);
        } else {
            return ResultGenerate.genErroResult("分类名称不能为空");
        }
    }

    /**
     * 查询分类
     */
    @GetMapping("/query")
    public Result queryClassify(@RequestParam("name") String name, @RequestParam("page") int page, @RequestParam("size") int size) {
        AdminClassifyDto form = new AdminClassifyDto();
        form.setName(name);
        form.setPage(page);
        form.setSize(size);
        return adminClassifyService.queryClassify(form);
    }
}
