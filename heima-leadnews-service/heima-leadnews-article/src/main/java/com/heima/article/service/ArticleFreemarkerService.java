package com.heima.article.service;

import com.heima.model.article.pojos.ApArticle;
import org.springframework.scheduling.annotation.Async;

public interface ArticleFreemarkerService {

    /** 
     * @Function: 功能描述 生成静态文件上传到minio中
     * @Author: ChenXW
     * @Date: 14:56 2022/7/27
     */
    public void buildArticleToMinIo(ApArticle apArticle,String content);

}
