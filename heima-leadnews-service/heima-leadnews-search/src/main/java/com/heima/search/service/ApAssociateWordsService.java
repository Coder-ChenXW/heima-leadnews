package com.heima.search.service;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.search.dtos.UserSearchDto;

public interface ApAssociateWordsService {

    /** 
     * @Function: 功能描述 搜索联想词
     * @Author: ChenXW
     * @Date: 18:37 2022/7/29
     */
    public ResponseResult search(UserSearchDto dto);

}
