package com.car.order.web.dto;

import lombok.Data;

import java.util.List;

/**
 * Created by Intellij IDEA.
 *
 * @author:  cjw
 * Date:  2021/3/7
 */
@Data
public class OrderSceneDto {
    /**
     * 城市
     */
    private String addressCity;
    /**
     * 品牌
     */
    private List<String> brands;
    /**
     * 维修类型
     */
    private String technologyType;

}
