//
//  OnlineCaseModel.h
//  CarStoreProject
//
//  Created by apple on 2021/1/28.
//

#import "BaseModel.h"

NS_ASSUME_NONNULL_BEGIN

@interface OnlineCaseModel : BaseModel

@property (nonatomic, copy) NSString *amt;//案例收益
@property (nonatomic, copy) NSString *caseUuid;//案例唯一标识 ,
@property (nonatomic, copy) NSString *num;//案例销量 ,
@property (nonatomic, copy) NSString *profitType;// 收益类型1维修2案例3问答
@property (nonatomic, copy) NSString *title;//案例名称 ,
@property (nonatomic, copy) NSString *uuid;//案例收益唯一标识

@end

NS_ASSUME_NONNULL_END
