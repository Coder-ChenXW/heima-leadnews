package com.heima.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.article.dtos.ArticleDto;
import com.heima.model.article.dtos.ArticleHomeDto;
import com.heima.model.article.pojos.ApArticle;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.mess.ArticleVisitStreamMess;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

public interface ApArticleService extends IService<ApArticle> {

    /**
     * @Function: 功能描述 加载文章列表 1更多 2最新
     * @Author: ChenXW
     * @Date: 20:30 2022/7/22
     */
    public ResponseResult load(ArticleHomeDto dto,Short type);


    /** 
     * @Function: 功能描述 加载文章列表首页
     * @Author: ChenXW
     * @Date: 15:32 2022/7/30
     */
    public ResponseResult load2(ArticleHomeDto dto,Short type,boolean firstPage);


    /**
     * @Function: 功能描述 保存app端相关文章
     * @Author: ChenXW
     * @Date: 10:38 2022/7/27
     */
    public ResponseResult saveArticle(ArticleDto dto);


    /** 
     * @Function: 功能描述 更新文章分值
     * @Author: ChenXW
     * @Date: 17:18 2022/7/30
     */
    public void updateScore(ArticleVisitStreamMess mess);

}