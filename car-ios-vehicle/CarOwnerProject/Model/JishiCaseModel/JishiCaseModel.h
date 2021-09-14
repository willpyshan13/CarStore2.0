//
//  JishiCaseModel.h
//  CarOwnerProject
//
//  Created by apple on 2021/1/28.
//

#import "BaseModel.h"

NS_ASSUME_NONNULL_BEGIN

@interface JishiCaseModel : BaseModel
@property(nonatomic,copy)NSString *amt;//案例金额
@property(nonatomic,copy)NSString *imgUrl;//案例图片
@property(nonatomic,copy)NSString *technicianName;//技师名称
@property(nonatomic,copy)NSString *title;//案例唯名称
@property(nonatomic,copy)NSString *uuid;//案例唯一标识
@property(nonatomic,copy)NSString *workingYear;//工龄


@end

NS_ASSUME_NONNULL_END
