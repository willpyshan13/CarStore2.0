//
//  FieldServiceDesModel.h
//  CarStoreProject
//
//  Created by apple on 2021/3/10.
//

#import "BaseModel.h"

NS_ASSUME_NONNULL_BEGIN
//现场服务
@interface FieldServiceDesModel : BaseModel
@property (nonatomic, copy) NSString *uuid;
@property (nonatomic, copy) NSString *brandName;// 品牌名称 ,
@property (nonatomic, copy) NSString *buyerMobile;// 购买者手机号码 ,
@property (nonatomic, copy) NSString *buyerName;// 购买者姓名 ,
@property (nonatomic, copy) NSString *carModelName;// 车型名称 ,
@property (nonatomic, copy) NSString *createdTime;// 创建时间 ,
@property (nonatomic, copy) NSString *distance;//  距离(km) ,
@property (nonatomic, copy) NSString *faultDesc;//  故障描述 ,
@property (nonatomic, copy) NSString *grabbingOrdersSts;//   抢单状态 0未抢，1已抢 ,
@property (nonatomic, copy) NSString *isOneself;//是否是本人发布，true：是， false：否 ,
@property (nonatomic, copy) NSString *issuerMobile;// 发布者手机号码 ,
@property (nonatomic, copy) NSString *issuerName;// 发布者姓名 ,
@property (nonatomic, copy) NSString *issuerUuid;//发布者uuid ,
@property (nonatomic, copy) NSString *orderSts;//订单状态 0 待支付 1 已支付 2: 已取消 3:退款中 4:退款成功 5:退款失败 6：已完成 ,
@property (nonatomic, copy) NSString *payType;// 支付方式 0 微信支付 1 支付宝支付 ,
@property (nonatomic, copy) NSString *totalAmount;// 总支付费用 ,


@end

NS_ASSUME_NONNULL_END
