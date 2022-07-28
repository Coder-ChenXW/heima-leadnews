package com.heima.wemedia.service;

import java.util.Date;

public interface WmNewsTaskService {

    /**
     * @Function: 功能描述 添加任务到延迟队列中
     * @Author: ChenXW
     * @Date: 17:58 2022/7/28
     */
    public void addNewsToTask(Integer id, Date publishTime);


    /**
     * @Function: 功能描述 消费任务审核文章
     * @Author: ChenXW
     * @Date: 18:11 2022/7/28
     */
    public void scanNewsByTask();

}
