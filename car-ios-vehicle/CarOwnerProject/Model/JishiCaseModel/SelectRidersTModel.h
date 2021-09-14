//
//  SelectRidersTModel.h
//  CarOwnerProject
//
//  Created by apple on 2021/2/7.
//

#import "BaseModel.h"

NS_ASSUME_NONNULL_BEGIN

@interface SelectRidersTModel : BaseModel
//系统相关
@property(nonatomic,copy)NSString *lableCode;//编号 ,
@property(nonatomic,copy)NSString *lableDesc;//描述(页面展示) ,
@property(nonatomic,copy)NSString *lableDescEn;// 描述英文 ,
@property(nonatomic,copy)NSString *lableType;//类型 ,
@property(nonatomic,copy)NSString *lableTypeDesc;//类型描述 ,
@property(nonatomic,copy)NSString *lableValue;// 数值 ,
@property(nonatomic,copy)NSString *sortNum;//排序(类型列表排序) ,

//品牌车型 相关
@property(nonatomic,copy)NSString *configName;// 品牌名称
@property(nonatomic,copy)NSString *parentCode;// 数据ID
@property(nonatomic,copy)NSString *configType;// 数据ID
@property(nonatomic,copy)NSString *uuid;// 数据ID


@end

NS_ASSUME_NONNULL_END
