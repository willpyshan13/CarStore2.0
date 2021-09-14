package com.car.order.web.mapper.scene;

import com.car.order.client.request.scene.QuerySceneOrderListReq;
import com.car.order.client.response.scene.QuerySceneOrderListRes;
import com.car.order.client.response.scene.StatisticsSceneOrderRes;
import com.car.order.web.dto.LaAndLoDto;
import com.car.order.web.dto.scene.SceneOrderDto;
import com.car.order.web.model.scene.SceneOrder;
import com.car.order.web.model.scene.SceneOrderServices;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by Intellij IDEA.
 *
 * @author:  cjw
 * Date:  2021/1/30
 */
@Repository
public interface SceneOrderServicesMapper extends Mapper<SceneOrderServices> {
    /**
     * 查询统计数量
     *
     * @param year
     * @param month
     * @return
     */
    List<Map> sceneOrderStatisticsNum(@Param("year") Integer year, @Param("month") Integer month, @Param("uuId") String uuId);



    /**
     * 查询总单数
     * @param uuId
     * @return
     */
    Integer statisticsSceneOrder(@Param("uuId") String uuId);
}
