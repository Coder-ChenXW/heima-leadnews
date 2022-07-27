package com.heima.model.article.dtos;

import com.heima.model.article.pojos.ApArticle;
import lombok.Data;

@Data
public class ArticleDto extends ApArticle {

    /**
     * @Function: 功能描述 文章内容
     * @Author: ChenXW
     * @Date: 18:08 2022/7/26
     */

    private String content;

}
