package com.heima.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmNewsDto;
import com.heima.model.wemedia.dtos.WmNewsPageReqDto;
import com.heima.model.wemedia.pojos.WmNews;
import org.springframework.web.bind.annotation.RequestBody;

public interface WmNewsService extends IService<WmNews> {

    /** 
     * @Function: 功能描述 条件查询文章列表
     * @Author: ChenXW
     * @Date: 18:23 2022/7/25
     */
    public ResponseResult findList(WmNewsPageReqDto dto);

    /** 
     * @Function: 功能描述发布修改文章或保存为草稿
     * @Author: ChenXW
     * @Date: 15:17 2022/7/26
     */
    public ResponseResult submitNews(WmNewsDto dto);


    /** 
     * @Function: 功能描述 文章的上下架
     * @Author: ChenXW
     * @Date: 15:25 2022/7/29
     */
    public ResponseResult downOrUp(WmNewsDto dto);

}
