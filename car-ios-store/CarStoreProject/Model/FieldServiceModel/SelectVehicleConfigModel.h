//
//  SelectVehicleConfigModel.h
//  CarOwnerProject
//
//  Created by apple on 2021/2/7.
//

#import "BaseModel.h"

NS_ASSUME_NONNULL_BEGIN

@interface SelectVehicleConfigModel : BaseModel
@property(nonatomic,copy)NSString *configName;//配置名称 ,
@property(nonatomic,copy)NSString *configType;// 配置类型 1：车辆类型 2：车辆品牌 3：车辆型号 ,
@property(nonatomic,copy)NSString *parentCode;// 父类编码，根节点默认：-1 ,
@property(nonatomic,copy)NSString *sortNum;//排序 ,
@property(nonatomic,copy)NSString *uuid;//主键ID
@end

NS_ASSUME_NONNULL_END
