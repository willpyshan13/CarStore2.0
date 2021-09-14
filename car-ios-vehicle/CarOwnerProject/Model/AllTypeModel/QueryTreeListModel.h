//
//  QueryTreeListModel.h
//  CarOwnerProject
//
//  Created by apple on 2021/1/29.
//

#import "BaseModel.h"
#import "QueryTreeListSubListModel.h"

NS_ASSUME_NONNULL_BEGIN

@interface QueryTreeListModel : BaseModel
@property(nonatomic,copy)NSString *groupName;//商品分组名称
@property(nonatomic,copy)NSString *groupNameEn;//商品分组英文名称
@property(nonatomic,copy)NSString *orderNum;//排序
@property(nonatomic,copy)NSString *parentId;//父组id
@property(nonatomic,copy)NSString *uuid;//主键ID
@property (nonatomic, strong) NSMutableArray *subListArray;//规格
@property (nonatomic, strong) QueryTreeListSubListModel *subList;//规格列表

@end

NS_ASSUME_NONNULL_END
