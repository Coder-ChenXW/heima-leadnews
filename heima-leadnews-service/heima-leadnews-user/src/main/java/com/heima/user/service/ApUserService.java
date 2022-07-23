package com.heima.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.user.dtos.LoginDto;
import com.heima.model.user.pojos.ApUser;


public interface ApUserService extends IService<ApUser> {

    /**
     * @Function: 功能描述 app端登录功能
     * @Author: ChenXW
     * @Date: 17:59 2022/7/22
     */
    public ResponseResult login(LoginDto dto);

}
