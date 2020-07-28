package com.ozomall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ozomall.dao.BannerMapper;
import com.ozomall.entity.BannerDto;
import com.ozomall.entity.Result;
import com.ozomall.service.BannerService;
import com.ozomall.utils.Oss;
import com.ozomall.utils.ResultGenerate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, BannerDto> implements BannerService {
    /**
     * 添加banner
     */
    @Override
    public Result addBanner(BannerDto form) {
        Boolean row = this.save(form);
        if (row) {
            return ResultGenerate.genSuccessResult();
        } else {
            return ResultGenerate.genErroResult("添加失败");
        }
    }

    /**
     * 获取banner
     */
    @Override
    public Result getBanner() {
        List<BannerDto> rows = this.list();
        if (rows != null) {
            return ResultGenerate.genSuccessResult(rows);
        } else {
            return ResultGenerate.genErroResult("获取失败");
        }
    }

    /**
     * 添加banner
     */
    @Override
    public Result upload(MultipartFile file) throws IOException {
        Oss ossClient = new Oss();
        ossClient.init("ozomall-goods-pic", "banner/");
        String name = ossClient.uploadImg2Oss(file);
        String url = ossClient.getImgUrl(name);
        ossClient.destory();
        if (!StringUtils.isEmpty(url)) {
            Map<String, String> data = new HashMap<>();
            data.put("url", url);
            return ResultGenerate.genSuccessResult(data);
        } else {
            return ResultGenerate.genErroResult("上传失败");
        }
    }

    /**
     * 修改banner
     */
    @Override
    public Result putBanner(BannerDto form) {
        Boolean row = this.updateById(form);
        if (row) {
            return ResultGenerate.genSuccessResult();
        } else {
            return ResultGenerate.genErroResult("修改失败");
        }
    }

    /**
     * 删除banner
     */
    @Override
    public Result delBanner(BannerDto form) {
        Boolean row = this.removeById(form.getId());
        if (row) {
            return ResultGenerate.genSuccessResult();
        } else {
            return ResultGenerate.genErroResult("删除失败");
        }
    }
}
