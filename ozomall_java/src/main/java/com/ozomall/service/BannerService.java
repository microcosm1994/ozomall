package com.ozomall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ozomall.entity.BannerDto;
import com.ozomall.entity.Result;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface BannerService extends IService<BannerDto> {
    /**
     * 添加banner
     */
    Result addBanner(BannerDto form);

    /**
     * 获取banner
     */
    Result getBanner();

    /**
     * 上传banner图片
     */
    Result upload(MultipartFile file) throws IOException;

    /**
     * 修改banner
     */
    Result putBanner(BannerDto form);

    /**
     * 删除banner
     */
    Result delBanner(BannerDto form);
}
