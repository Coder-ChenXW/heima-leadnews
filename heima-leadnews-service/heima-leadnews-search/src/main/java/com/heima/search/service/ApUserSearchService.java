package com.heima.search.service;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.search.dtos.HistorySearchDto;
import org.springframework.web.bind.annotation.RequestBody;

public interface ApUserSearchService {

    
    /** 
     * @Function: 功能描述 保存用户历史搜索记录
     * @Author: ChenXW
     * @Date: 17:45 2022/7/29
     */
    public void insert(String keyword,Integer userId);

    /** 
     * @Function: 功能描述 查询搜索历史
     * @Author: ChenXW
     * @Date: 18:19 2022/7/29
     */
    public ResponseResult findUserSearch();


    /** 
     * @Function: 功能描述 删除历史记录
     * @Author: ChenXW
     * @Date: 18:27 2022/7/29
     */
    public ResponseResult delUserSearch(HistorySearchDto dto);

}
