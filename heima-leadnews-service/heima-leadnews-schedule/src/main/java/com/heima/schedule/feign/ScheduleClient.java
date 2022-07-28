package com.heima.schedule.feign;

import com.heima.apis.schedule.IScheduleClient;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.schedule.dtos.Task;
import com.heima.schedule.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class ScheduleClient implements IScheduleClient {

    @Autowired
    private TaskService taskService;


    /**
     * @param task
     * @Function: 功能描述 添加延迟任务
     * @Author: ChenXW
     */
    @Override
    @PostMapping("/api/v1/task/add")
    public ResponseResult addTask(@RequestBody Task task) {
        return ResponseResult.okResult(taskService.addTask(task));
    }

    /**
     * @param taskId
     * @Function: 功能描述 取消任务
     * @Author: ChenXW
     */
    @Override
    @GetMapping("/api/v1/task/{taskId}")
    public ResponseResult cancelTask(@PathVariable("taskId") long taskId) {
        return ResponseResult.okResult(taskService.cancelTask(taskId));
    }

    /**
     * @param type
     * @param priority
     * @Function: 功能描述 安装类型和优先级拉取任务
     * @Author: ChenXW
     */
    @Override
    @GetMapping("/api/v1/task/{taskId}/{type}/{priority}")
    public ResponseResult poll(@PathVariable("type") int type, @PathVariable("priority") int priority) {
        return ResponseResult.okResult(taskService.poll(type, priority));
    }
}
