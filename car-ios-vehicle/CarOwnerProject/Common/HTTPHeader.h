//
//  HTTPHeader.h
//  CarOwnerProject
//
//  Created by apple on 2021/1/25.
//

#ifndef HTTPHeader_h
#define HTTPHeader_h
/*
 数据请求
 **/

#import "HTTPManager.h"

//#define HOST_IP @"http://192.168.3.6:10010" //测试环境

//#define HOST_IP @"https://api.dlvehicle.com" //生产环境
#define HOST_IP @"https://dev.api.dlvehicle.com" //测试环境

#define HOST_URL [NSString stringWithFormat:@"%@",HOST_IP]


//成功
#define CODE_SUCCESS @"0000"

//token失效
#define CODE_Failure   @"40002"
#define CODE_Failure1   @"4003"

#define CODE_XTFailure   @"401"

//token失效提示语
#define TokenInvalidMsg @"您的登录已失效,请重新登录"



#pragma mark ================ h5开始 ================

//#define HOSTH5_IP  @"https://vehicle.dlvehicle.com" //生产环境
#define HOSTH5_IP  @"https://dev.vehicle.dlvehicle.com" //测试环境
//#define HOSTH5_IP  @"http://192.168.3.10:3000" //测试

//分享
#define shareDownAppH5             @"/sharePage"

/*
 商户汽车维修
 **/
#define storeH5url    @"/store"

/*
 技师问答
 **/
#define askH5url    @"/ask"

/*
 一元旁听详情购买
 **/
#define auditH5url   @"/audit"

/*
 案例详情购买
 **/
#define caseH5url    @"/case"

/*
 我的订单
 **/
#define orderH5url    @"/order"

/*
 已绑定车辆
 **/
#define carH5Url     @"/car"

/*
 我的提问
 **/
#define consultH5url    @"/my/consult"

/*
 我的案例
 **/
#define myCaseH5url     @"/my/case"

/*
 个人信息
 **/
#define infoH5url      @"/info"

/*
 /share
 **/
#define shareH5url      @"/share"

/*
 养护信息
 **/
#define huliH5url       @"/huli"

/*
 店铺详情
 **/
#define storeDetail    @"/store/detail"

/*
 地图 
 **/
#define daijiaMapH5url          @"/map"

/*
 查看检测
 **/
#define detectionDetailH5url         @"/detectionDetail"

#pragma mark ================ h5结束 ================


/*
 用户登录
 userType (integer, optional): 用户类型1车主2技师3店铺
 **/
#define loginUserLoginApi              @"/account/login/userLogin"


/*
 获取登录验证码
 **/
#define getLoginCodeApi               @"/account/login/getLoginCode/"

/*
 查询案例列表 车主查询   /order/case/queryCaseForVehicleList
 **/
#define queryCaseForVehicleListApi   @"/order/case/queryCaseForVehicleList"

/*
 养护信息
 **/
#define maintainListApi             @"/order/maintain/list"


/*
 GET /goodsParent/queryListByParent/{parentUuid}
 查询父节点所有分组，根节点传-1
 **/
#define queryListByParentApi   @"/account/goodsParent/queryListByParent/"

/*
 查询对应店铺
 /account/store/queryStoreList'
 **/
#define storeQueryStoreListApi     @"/account/store/queryStoreList"


/*
 GET /goodsParent/queryTreeList/{parentUuid}
 查询当前父节点下所有分组树结构列表[有层级结构
 **/

#define queryTreeListApi   @"/account/goodsParent/queryTreeList/"


/*
 POST /answer/answerList
 可被咨询的问答列表
 **/
#define answerListApi          @"/order/answer/answerList"


/*
 POST /technician/queryTechnicianAnswerList
 技师问答列表[车主端
 **/
#define queryTechnicianAnswerListApi                @"/account/technician/queryTechnicianAnswerList"

/*
 GET /account/queryQuizCaseCarCount
 查询我的提问、案例、车辆数量
 **/
#define queryQuizCaseCarCountApi   @"/account/account/queryQuizCaseCarCount"

/*
 GET /dict/queryListByType/{type}
 根据字典类型查询字典集合
 **/
#define queryListByTypeApi   @"/manager/dict/queryListByType"


/*
 品牌、车型
 **/
#define pinpaiApi         @"/account/vehicleConfig/queryList"

/*
 GET /vehicleConfig/queryAllList
 查询所有车辆配置信息
 **/
#define vehicleConfigQueryAllListApi  @"/account/vehicleConfig/queryAllList"

/*
 查询所有区域
 **/
#define queryAreaListApi          @"/manager/area/queryList"


/*
 上传图片 formData
 **/
#define uploadFileApi                  @"/utility/file/uploadFile"


/*
 上传头像 base64
 **/
#define uploadBase64Image             @"/utility/file/uploadBase64Image"

/*
 绑定用户头像
 **/
#define updateUserPhotoImageApi       @"/account/user/updateUserPhotoImg"

/*
 查询头像
 **/
#define queryUserPhotoImgUrlApi      @"/account/user/queryUserPhotoImgUrl"


/*
 查询引导页
 **/
#define settingGetByCodeApi           @"/manager/setting/getByCode"

/*
 上传当前位置
 **/
#define positionReportApi            @"/account/position/report"


#define queryByCodeApi          @"/manager/dict/queryByCode/on_off"

#endif /* HTTPHeader_h */
