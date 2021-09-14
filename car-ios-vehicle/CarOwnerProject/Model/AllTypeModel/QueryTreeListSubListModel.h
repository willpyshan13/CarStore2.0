//
//  QueryTreeListSubListModel.h
//  CarOwnerProject
//
//  Created by apple on 2021/1/29.
//

#import "BaseModel.h"

NS_ASSUME_NONNULL_BEGIN

@interface QueryTreeListSubListModel : BaseModel
@property(nonatomic,copy)NSString *groupName;//商品分组名称
@property(nonatomic,copy)NSString *groupNameEn;//商品分组英文名称
@property(nonatomic,copy)NSString *orderNum;//排序
@property(nonatomic,copy)NSString *parentId;//父组id
@property(nonatomic,copy)NSString *uuid;//主键ID
@end

NS_ASSUME_NONNULL_END
