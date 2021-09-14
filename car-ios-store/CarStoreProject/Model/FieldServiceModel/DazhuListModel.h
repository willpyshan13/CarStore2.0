//
//  DazhuListModel.h
//  CarOwnerProject
//
//  Created by apple on 2021/2/1.
//

#import "BaseModel.h"

NS_ASSUME_NONNULL_BEGIN
//付费咨询问题列表
@interface DazhuListModel : BaseModel

@property(nonatomic,copy)NSString *answerDesc;//回答描述 ,
@property(nonatomic,copy)NSString *carOwnerName;//车主姓名 ,
@property(nonatomic,copy)NSString *consultAmt;//咨询金额 ,
@property(nonatomic,copy)NSString *consultDesc;//咨询描述 ,
@property(nonatomic,copy)NSString *createdTime;//咨询时间 ,
@property(nonatomic,copy)NSString *workingYear;//工龄
@property(nonatomic,copy)NSString *technicianImgUrl;//技师头像 ,
@property(nonatomic,copy)NSString *technicianName;//案例唯一标识
@property(nonatomic,copy)NSString *title;// 咨询标题 ,
@property(nonatomic,copy)NSString *uuid;// 咨询uuid
@property(nonatomic,copy)NSString *orderUuid;
@end

NS_ASSUME_NONNULL_END
