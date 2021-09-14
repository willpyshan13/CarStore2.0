//
//  DTCListModel.h
//  CarStoreProject
//
//  Created by apple on 2021/3/10.
//

#import "BaseModel.h"

NS_ASSUME_NONNULL_BEGIN

@interface DTCListModel : BaseModel
@property (nonatomic, copy) NSString *uuid;
@property (nonatomic, copy) NSString *configName;//品牌名称 ,
@property (nonatomic, copy) NSString *createdBy;// 创建人 ,
@property (nonatomic, copy) NSString *createdTime;//  创建时间 ,
@property (nonatomic, copy) NSString *dtcAmount;//   dtc购买金额 ,
@property (nonatomic, copy) NSString *dtcBrandUuid;//dtc发布关联品牌(对应车辆品牌uuid) ,
@property (nonatomic, copy) NSString *dtcCode;//dtc故障代码 ,
@property (nonatomic, copy) NSString *dtcDefinition;//dtc标题 ,
@property (nonatomic, copy) NSString *dtcType;//dtc类型 ,
@property (nonatomic, copy) NSString *dtcTypeName;//dtc类型名称 ,

@end

NS_ASSUME_NONNULL_END
