package com.heima.schedule.service.impl;

import com.alibaba.fastjson.JSON;
import com.heima.common.constants.ScheduleConstants;
import com.heima.common.redis.CacheService;
import com.heima.model.schedule.dtos.Task;
import com.heima.model.schedule.pojos.Taskinfo;
import com.heima.model.schedule.pojos.TaskinfoLogs;
import com.heima.schedule.mapper.TaskinfoLogsMapper;
import com.heima.schedule.mapper.TaskinfoMapper;
import com.heima.schedule.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;


@Service
@Transactional
@Slf4j
public class TaskServiceImpl implements TaskService {
    /**
     * @param task
     * @Function: 功能描述 添加延迟任务
     * @Author: ChenXW
     * @Date: 16:00 2022/7/28
     */
    @Override
    public long addTask(Task task) {
        //添加任务到数据库中
        boolean success = addTaskToDb(task);

        if (success) {
            //添加任务到redis
            addTaskToCache(task);
        }


        return task.getTaskId();
    }

    /**
     * @param taskId
     * @Function: 功能描述 取消任务
     * @Author: ChenXW
     * @Date: 16:26 2022/7/28
     */
    @Override
    public boolean cancelTask(long taskId) {

        boolean flag = false;

        //删除任务,更新任务日志
        Task task = updateDb(taskId, ScheduleConstants.CANCELLED);

        //删除redis的数据
        if (task != null) {
            removeTaskFromCache(task);
            flag = true;
        }

        return flag;
    }

    /**
     * @param type
     * @param priority
     * @Function: 功能描述 安装类型和优先级拉取任务
     * @Author: ChenXW
     * @Date: 16:41 2022/7/28
     */
    @Override
    public Task poll(int type, int priority) {

        Task task=null;

        try {
            String key = type + "_" + priority;

            //从redis中拉取数据
            String task_json = cacheService.lRightPop(ScheduleConstants.TOPIC + key);
            if (StringUtils.isNotBlank(task_json)){
                task = JSON.parseObject(task_json, Task.class);

                //修改数据库信息
                updateDb(task.getTaskId(),ScheduleConstants.EXECUTED);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("poll task exception");
        }

        return task;
    }

    /**
     * @Function: 功能描述 删除redis中的数据
     * @Author: ChenXW
     * @Date: 16:34 2022/7/28
     */
    private void removeTaskFromCache(Task task) {
        String key = task.getTaskId() + "_" + task.getPriority();
        if (task.getExecuteTime() <= System.currentTimeMillis()) {
            cacheService.lRemove(ScheduleConstants.TOPIC + key, 0, JSON.toJSONString(task));
        } else {
            cacheService.zRemove(ScheduleConstants.FUTURE + key, JSON.toJSONString(task));
        }
    }


    /**
     * @Function: 功能描述 更新任务日志
     * @Author: ChenXW
     * @Date: 16:28 2022/7/28
     */
    private Task updateDb(long taskId, int status) {

        Task task = null;

        try {
            //删除任务
            taskinfoMapper.deleteById(taskId);

            //更新任务日志
            TaskinfoLogs taskinfoLogs = taskinfoLogsMapper.selectById(taskId);
            taskinfoLogs.setStatus(status);
            taskinfoLogsMapper.updateById(taskinfoLogs);

            task = new Task();
            BeanUtils.copyProperties(taskinfoLogs, task);
            task.setExecuteTime(taskinfoLogs.getExecuteTime().getTime());
        } catch (Exception e) {
            log.error("task cancel exception taskId={}", taskId);
        }

        return task;
    }


    @Autowired
    private CacheService cacheService;

    /**
     * @Function: 功能描述 把任务添加到redis中
     * @Author: ChenXW
     * @Date: 16:10 2022/7/28
     */
    private void addTaskToCache(Task task) {

        String key = task.getTaskId() + "_" + task.getPriority();

        //获取5分钟之后的时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 5);
        long nextScheduleTime = calendar.getTimeInMillis();

        //如果任务的执行时间小于等于当前时间,存入list中
        if (task.getExecuteTime() <= System.currentTimeMillis()) {
            cacheService.lLeftPush(ScheduleConstants.TOPIC + key, JSON.toJSONString(task));
        } else if (task.getExecuteTime() <= nextScheduleTime) {
            //如果任务的执行时间大于当前时间 && 小于等于预设时间（未来5分钟） 存入zset中
            cacheService.zAdd(ScheduleConstants.FUTURE + key, JSON.toJSONString(task), task.getExecuteTime());

        }

    }


    @Autowired
    private TaskinfoMapper taskinfoMapper;

    @Autowired
    private TaskinfoLogsMapper taskinfoLogsMapper;

    /**
     * @Function: 功能描述 添加任务到数据库中
     * @Author: ChenXW
     * @Date: 16:03 2022/7/28
     */
    private boolean addTaskToDb(Task task) {

        boolean flag = false;

        try {
            //保存任务表
            Taskinfo taskinfo = new Taskinfo();
            BeanUtils.copyProperties(task, taskinfo);
            taskinfo.setExecuteTime(new Date(task.getExecuteTime()));
            taskinfoMapper.insert(taskinfo);

            //设置taskId
            task.setTaskId(taskinfo.getTaskId());

            //保存任务日志数据
            TaskinfoLogs taskinfoLogs = new TaskinfoLogs();
            BeanUtils.copyProperties(taskinfo, taskinfoLogs);
            taskinfoLogs.setVersion(1);
            taskinfoLogs.setStatus(ScheduleConstants.SCHEDULED);
            taskinfoLogsMapper.insert(taskinfoLogs);

            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return flag;
    }


    /**
     * @Function: 功能描述 未来数据定时刷新
     * @Author: ChenXW
     * @Date: 17:00 2022/7/28
     */
    @Scheduled(cron = "0 */1 * * * ?")
    public void refresh() {
        System.out.println(System.currentTimeMillis() / 1000 + "执行了定时任务");

        // 获取所有未来数据集合的key值
        Set<String> futureKeys = cacheService.scan(ScheduleConstants.FUTURE + "*");// future_*
        for (String futureKey : futureKeys) { // future_250_250

            String topicKey = ScheduleConstants.TOPIC + futureKey.split(ScheduleConstants.FUTURE)[1];
            //获取该组key下当前需要消费的任务数据
            Set<String> tasks = cacheService.zRangeByScore(futureKey, 0, System.currentTimeMillis());
            if (!tasks.isEmpty()) {
                //将这些任务数据添加到消费者队列中
                cacheService.refreshWithPipeline(futureKey, topicKey, tasks);
                System.out.println("成功的将" + futureKey + "下的当前需要执行的任务数据刷新到" + topicKey + "下");
            }
        }
    }

}
