//
//  StoreQueryListWXModel.h
//  CarOwnerProject
//
//  Created by apple on 2021/3/19.
//

#import "BaseModel.h"

NS_ASSUME_NONNULL_BEGIN

@interface StoreQueryListWXModel : BaseModel

@property(nonatomic,copy)NSString *uuid;//主键ID
@property(nonatomic,copy)NSString *storeName;//店铺名称
@property(nonatomic,copy)NSArray *imgList;//店铺图片集合
@property(nonatomic,copy)NSArray *configNameList;//支持的品牌名称集合 ,
@property(nonatomic,copy)NSString *parentTypeName;
@end

NS_ASSUME_NONNULL_END
