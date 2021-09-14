//
//  MaintainInfoModel.h
//  CarOwnerProject
//
//  Created by apple on 2021/3/18.
//

#import "BaseModel.h"

NS_ASSUME_NONNULL_BEGIN

@interface MaintainInfoModel : BaseModel
@property(nonatomic,copy)NSString *attachSys;
@property(nonatomic,copy)NSString *attachSysName;
@property(nonatomic,copy)NSString *brandName;
@property(nonatomic,copy)NSString *brandUuid;
@property(nonatomic,copy)NSString *carModelName;
@property(nonatomic,copy)NSString *carModelUuid;
@property(nonatomic,copy)NSString *createdTime;
@property(nonatomic,copy)NSString *maintainCheckSts;
@property(nonatomic,copy)NSString *maintainContent;
@property(nonatomic,copy)NSString *maintainCover;
@property(nonatomic,copy)NSString *maintainTitle;
@property(nonatomic,copy)NSString *uuid;

@end

NS_ASSUME_NONNULL_END
