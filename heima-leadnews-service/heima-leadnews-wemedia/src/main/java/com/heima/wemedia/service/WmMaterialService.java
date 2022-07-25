package com.heima.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmMaterialDto;
import com.heima.model.wemedia.pojos.WmMaterial;
import org.springframework.web.multipart.MultipartFile;


public interface WmMaterialService extends IService<WmMaterial> {

    /**
     * @Function: 功能描述 图片上传
     * @Author: ChenXW
     * @Date: 20:32 2022/7/24
     */
    public ResponseResult uploadPicture(MultipartFile multipartFile);

    
    /** 
     * @Function: 功能描述 素材列表查询
     * @Author: ChenXW
     * @Date: 21:01 2022/7/24
     */
    public ResponseResult findList( WmMaterialDto dto);

}