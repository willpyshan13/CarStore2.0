//
//  CourseParentModel.h
//  CarStoreProject
//
//  Created by apple on 2021/3/9.
//

#import "BaseModel.h"

NS_ASSUME_NONNULL_BEGIN

@interface CourseParentModel : BaseModel
@property (nonatomic, copy) NSString *courseCover;//教程封面图片url
@property (nonatomic, copy) NSString *courseDesc;
@property (nonatomic, copy) NSString *courseTitle;//教程名称
@property (nonatomic, copy) NSString *createdTime;
@property (nonatomic, copy) NSString *newest;//是否最新数据
@property (nonatomic, copy) NSString *uuid;
@end

NS_ASSUME_NONNULL_END
