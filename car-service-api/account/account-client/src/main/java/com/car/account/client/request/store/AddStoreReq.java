package com.car.account.client.request.store;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author zhouz
 * @date 2020/12/19
 */
@Data
@ApiModel(value="AddStoreReq",description="新增店铺请求VO")
public class AddStoreReq {

    @NotBlank(message = "请输入店铺名称！")
    @ApiModelProperty(value = "店铺名称",name = "storeName")
    private String storeName;

    @NotBlank(message = "请输入店铺类型！")
    @ApiModelProperty(value = "店铺类型,对应字典表 uuid",name = "storeType")
    private String storeType;

    @ApiModelProperty(value = "汽车品牌列表 uuid",name = "brandUuidList")
    private List<String> brandUuidList;

    @ApiModelProperty(value = "管理员手机号")
    private String glyMobile;

    @NotBlank(message = "请输入公司名称！")
    @ApiModelProperty(value = "公司名称",name = "companyName")
    private String companyName;

    @NotBlank(message = "请输入公司地址-省 对应地区表uuid！")
    @ApiModelProperty(value = "公司地址-省",name = "companyAddressProvince")
    private String companyAddressProvince;

    @NotBlank(message = "请输入公司地址-市,对应地区表uuid！")
    @ApiModelProperty(value = "公司地址-市",name = "companyAddressCity")
    private String companyAddressCity;

    @ApiModelProperty(value = "公司地址-县镇",name = "companyAddressCounty")
    private String companyAddressCounty;

    @NotBlank(message = "请输入公司地址-详细信息！")
    @ApiModelProperty(value = "公司地址-详细信息",name = "companyAddressDetail")
    private String companyAddressDetail;

    @NotEmpty(message = "请上传营业执照图片")
    @ApiModelProperty(value = "营业执照图片",name = "businessImgList")
    private List<String> businessImgList;

    @NotEmpty(message = "请上传门店图片")
    @ApiModelProperty(value = "门店图片",name = "shopImgList")
    private List<String> shopImgList;

    @ApiModelProperty(value = "资质等其他图片",name = "otherImgList")
    private List<String> otherImgList;

    @ApiModelProperty(value = "店铺联系人",name = "storeUserReq")
    private List<StoreUserReq> storeUserReq;

    @ApiModelProperty(value = "店铺账号信息",name = "storeAccountReq")
    private StoreAccountReq storeAccountReq;

    @ApiModelProperty(value = "人员类型")
    private String renType;

    @ApiModelProperty(value = "法人身份证正面",name = "legal_person_front")
    private String legalPersonFront;

    @ApiModelProperty(value = "法人身份证反面",name = "legal_person_reverse")
    private String legalPersonReverse;

    @ApiModelProperty(value = "新增商品分类权限与平台服务费比例设置",name = "legal_person_reverse")
    List<StoreServiceRatesReq> storeServiceRatesReq;
}
