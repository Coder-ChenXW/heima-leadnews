package com.heima.search.service;

import com.heima.model.search.dtos.UserSearchDto;
import com.heima.model.common.dtos.ResponseResult;

import java.io.IOException;

public interface ArticleSearchService {

    /**
     * @Function: 功能描述 ES文章分页检索
     * @Author: ChenXW
     * @Date: 16:39 2022/7/29
     */
    ResponseResult search(UserSearchDto userSearchDto) throws IOException;
}