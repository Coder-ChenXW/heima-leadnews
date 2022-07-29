package com.heima.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.article.pojos.ApArticleConfig;

import java.util.Map;

public interface ApArticleConfigService extends IService<ApArticleConfig> {
    
    /** 
     * @Function: 功能描述 修改文章
     * @Author: ChenXW
     * @Date: 15:46 2022/7/29
     */
    void updateByMap(Map map);
}
