package com.ozomall.controller;

import com.ozomall.entity.BannerDto;
import com.ozomall.entity.Result;
import com.ozomall.service.BannerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

@Controller
@ResponseBody
@RequestMapping("/banner")
public class BannerController {
    @Resource
    BannerService bannerService;

    @ApiOperation(value = "添加banner")
    @PostMapping("/add")
    public Result addBanner(@RequestBody BannerDto form) {
        return bannerService.addBanner(form);
    }

    @ApiOperation(value = "获取banner")
    @GetMapping("/get")
    public Result getBanner() {
        return bannerService.getBanner();
    }

    @ApiOperation(value = "上传banner图片")
    @PostMapping("/upload")
    public Result upload(@RequestBody MultipartFile file) throws IOException {
        return bannerService.upload(file);
    }

    @ApiOperation(value = "修改banner")
    @PostMapping("/put")
    public Result putBanner(@RequestBody BannerDto form) {
        return bannerService.putBanner(form);
    }

    @ApiOperation(value = "删除banner")
    @PostMapping("/del")
    public Result delBanner(@RequestBody BannerDto form) {
        return bannerService.delBanner(form);
    }

}
