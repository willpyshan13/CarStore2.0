package com.car.order.web.mapper.consult;

import com.car.order.web.model.consult.ConsultImages;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author zhouz
 * @date 2021/1/1
 */
@Repository
public interface ConsultImagesMapper extends Mapper<ConsultImages> {

    /**
     * 批量新增咨询相关图片
     * @param consultImagesList
     */
    void batchInsertConsultImages(@Param("insertList") List<ConsultImages> consultImagesList);

    /**
     * 批量根据咨询uuid获取照片
     */
    List<String> getImgList(@Param("consultUuid") String consultUuid);

    /**
     * 批量根据咨询uuid,type获取照片
     */
    List<String> getTypeImgList(@Param("consultUuid") String consultUuid,@Param("type") String type);

    /**
     *
     */
    Integer  accomplishConsult(@Param("consultUuid")String consultUuid,@Param("carOwnerUuid")String carOwnerUuid);
}
