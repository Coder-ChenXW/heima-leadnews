package com.heima.schedule.service;

import com.heima.model.schedule.dtos.Task;

public interface TaskService {

    /** 
     * @Function: 功能描述 添加延迟任务
     * @Author: ChenXW
     * @Date: 16:00 2022/7/28
     */
    public long addTask(Task task);

    /** 
     * @Function: 功能描述 取消任务
     * @Author: ChenXW
     * @Date: 16:26 2022/7/28
     */
    public boolean cancelTask(long taskId);


    /** 
     * @Function: 功能描述 安装类型和优先级拉取任务
     * @Author: ChenXW
     * @Date: 16:41 2022/7/28
     */
    public Task poll(int type,int priority);

}
