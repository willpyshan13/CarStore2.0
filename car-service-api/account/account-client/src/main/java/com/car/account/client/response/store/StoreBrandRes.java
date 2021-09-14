package com.car.account.client.response.store;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="StoreAccountRes",description="店铺账号VO")
public class StoreBrandRes {


    @ApiModelProperty(value = "品牌ID",name = "uuid")
    private String uuid;

    @ApiModelProperty(value = "品牌名称",name = "configName")
    private String configName;
}
