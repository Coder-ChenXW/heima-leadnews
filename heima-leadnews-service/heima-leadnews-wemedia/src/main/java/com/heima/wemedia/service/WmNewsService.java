package com.heima.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmNewsPageReqDto;
import com.heima.model.wemedia.pojos.WmNews;

public interface WmNewsService extends IService<WmNews> {

    /** 
     * @Function: 功能描述 条件查询文章列表
     * @Author: ChenXW
     * @Date: 18:23 2022/7/25
     */
    public ResponseResult findList(WmNewsPageReqDto dto);

}
