package com.car.order.web.mapper.sharetechnicianorder;

import com.car.order.web.model.sharetechnicianorder.ShareTechnicianImages;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

@Repository
public interface ShareTechnicianImagesMapper extends BaseMapper<ShareTechnicianImages> {

    /**
     *  根据第三方uuid查询相关现场dtc相关图片
     * @param relationUuid
     * @param type
     * @return
     */
    List<String> queryList(@Param("relationUuid") String relationUuid, @Param("type") Integer type);
}