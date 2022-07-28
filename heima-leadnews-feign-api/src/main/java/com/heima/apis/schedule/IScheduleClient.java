package com.heima.apis.schedule;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.schedule.dtos.Task;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient("leadnews-schedule")
public interface IScheduleClient {

    /**
     * @Function: 功能描述 添加延迟任务
     * @Author: ChenXW
     */

    @PostMapping("/api/v1/task/add")
    public ResponseResult addTask(@RequestBody Task task);

    /**
     * @Function: 功能描述 取消任务
     * @Author: ChenXW
     */

    @GetMapping("/api/v1/task/{taskId}")
    public ResponseResult cancelTask(@PathVariable("taskId") long taskId);


    /**
     * @Function: 功能描述 安装类型和优先级拉取任务
     * @Author: ChenXW
     */

    @GetMapping("/api/v1/task/{taskId}/{type}/{priority}")
    public ResponseResult poll(@PathVariable("type") int type,@PathVariable("priority") int priority);

}
