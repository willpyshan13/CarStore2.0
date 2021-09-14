//
//  HTTPHeader.h
//  CarStoreProject
//
//  Created by apple on 2021/1/25.
//

#ifndef HTTPHeader_h
#define HTTPHeader_h

#import "HTTPManager.h"

//#define HOST_IP @"https://api.dlvehicle.com"//  生产
#define HOST_IP @"https://dev.api.dlvehicle.com" //测试
#define HOST_URL [NSString stringWithFormat:@"%@",HOST_IP]


//#define HOSTH5_IP  @"https://store.dlvehicle.com"// 生产
#define HOSTH5_IP  @"https://dev.store.dlvehicle.com" //测试
//#define HOSTH5_IP  @"http://192.168.3.10:8080" //测试
//分享
#define shareDownAppH5             @"/sharePage"

//成功
#define CODE_SUCCESS @"0000"

//token失效
#define CODE_Failure   @"40002"
#define CODE_Failure1   @"4003"

#define CODE_XTFailure   @"401"

//token失效提示语
#define TokenInvalidMsg @"您的登录已失效,请重新登录"

//调用h5界面 ================开始
/*
 
 技师注册
 **/
#define jishiRegistApiH5   @"/jishiRgister"


/*
 店铺注册
 **/
#define shopRegisterH5     @"/shopRgister"


/*
店铺个人信息
 **/
#define shopCenterH5Api   @"/shopCenter"


/*
 商品详情
 **/
#define putawayProductDetailH5Api  @"/putawayProductDetail"

/*
 技师个人信息
 **/
#define gerenxinxiH5Api   @"/gerenxinxi"

/*
 发布商品
 **/
#define putawayProductH5Api   @"/putawayProductType"

/*
 我的钱包 
 **/
#define walletH5Api   @"/wallet"


/*
 店铺账户信息
 **/
#define accountInforH5Api   @"/accountInfor"

/*
 技师账户信息
 **/
#define accountInforJishiH5Api   @"/accountInforJishi"


/*
 我的订单
 **/
#define myOrderH5Api         @"/myOrder"

/*
 发布案例
 **/
#define fabuAnliH5Api         @"/fabuAnli"

/*
 案例详情
 **/
#define anliDetailH5Api         @"/anliDetail"

/*
 技师回答问题
 **/
#define questionAnsH5Api         @"/questionAns"


/*
店铺管理技师
 **/
#define jishiAdminH5Api       @"/jishiAdmin"

/*
技师关联店铺
 **/
#define relevancyShopH5Api       @"/relevancyShop"

/*
 DTC订单列表
 **/
#define dtcListH5Api             @"/dtcList"

/*
 DTC详情
 **/
#define dtcDetailH5Api            @"/dtcDetail"

/*
 补录dtc
 **/
#define dtcAddOrEditH5Api          @"/dtcAddOrEdit"

/*
 我的课程订单列表
 **/
#define myCourseH5Api             @"/myCourse"

/*
 课程列表
 **/
#define myjiaochengH5Api             @"/myjiaocheng"


/*
 现场订单详情
 **/
#define scenceOrderDetailH5Api         @"/scenceOrderDetail"


/*
 新增现场订单
 **/
#define scenceOrderH5Api             @"/scenceOrder"

/*
 案例详情购买
 **/
#define caseH5url    @"/caseDetail"

/*
 一元旁听详情购买
 **/
#define auditH5url   @"/audit"

/*
 技师问答
 **/
#define askH5url    @"/ask"

/*
 我的案例
 **/
#define myCaseH5Url     @"/myCase"

/*
 我的提问
 **/
#define myConsultH5url    @"/myConsult"

/*
 添加检测
 **/
#define addDetectionH5url         @"/addDetection"


//调用h5界面 ================结束

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
 查询案例列表 技师查询
 **/
#define queryCaseForTechnicianListApi   @"/order/case/queryCaseForTechnicianList"

/*
 POST /answer/preAnswerList
 技师待问答列表
 **/
#define answerPreAnswerListApi         @"/order/answer/preAnswerList"

/*
 查询商户商品列表
 **/
#define queryStoreGoodsListApi         @"/account/goods/queryStoreGoodsList"

/*
 切换角色
 **/
#define loginSwitchRoleApi            @"/account/login/switchRole"

/*
 账户信息
 **/
#define accountMsgApi                 @"/account/account/account"

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
 培训教育
 **/
#define courseParentListApi           @"/order/courseParent/list"

/*
 培训教育 最新培训课程
 **/
#define courseParentListNewestApi           @"/order/courseParent/listNewest"

/*
 培训教育 一般培训教育
 **/
#define courseParentListGeneralApi           @"/order/courseParent/listGeneral"

/*
 dtc列表
 **/
#define dtcListApi                      @"/order/dtc/list"

/*
 现场服务
 **/
#define querySceneOrderListApi            @"/order/sceneOrder/querySceneOrderList"


/*
 现场订单抢单
 **/
#define sceneOrderGrabbingOrdersApi        @"/order/sceneOrder/grabbingOrders"



/*
 上传当前位置
 **/
#define positionReportApi            @"/account/position/report"


/*
 品牌、车型
 **/
#define pinpaiApi         @"/account/vehicleConfig/queryList"

/*
 GET /dict/queryListByType/{type}
 根据字典类型查询字典集合
 **/
#define queryListByTypeApi   @"/manager/dict/queryListByType"

/*
 查询案例列表 车主查询   /order/case/queryCaseForVehicleList
 **/
#define queryCaseForVehicleListApi   @"/order/case/queryCaseForVehicleList"

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

#define queryByCodeApi          @"/manager/dict/queryByCode/on_off"

#endif /* HTTPHeader_h */
