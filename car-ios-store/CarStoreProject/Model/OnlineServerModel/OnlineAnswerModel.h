//
//  OnlineAnswerModel.h
//  CarStoreProject
//
//  Created by apple on 2021/1/29.
//

#import "BaseModel.h"

NS_ASSUME_NONNULL_BEGIN
//线上回答
@interface OnlineAnswerModel : BaseModel
@property (nonatomic, copy) NSString *answerCheckSts;//回答审核状态 0 待审核 1 审核通过 2 审核驳回
@property (nonatomic, copy) NSString *answerDesc;//回答描述
@property (nonatomic,strong)NSArray *answerImgList;//回答图片列表
@property (nonatomic, copy) NSString *answerSts;//答复状态 0 未答复 1 已答复
@property (nonatomic, copy) NSString *answerTime;//答复时间
@property (nonatomic, copy) NSString *carOwnerImgUrl;//车主头像
@property (nonatomic, copy) NSString *carOwnerMobile;//车主手机号
@property (nonatomic, copy) NSString *carOwnerName;//车主姓名
@property (nonatomic, copy) NSString *carOwnerUuid;//车主uuid
@property (nonatomic, copy) NSString *consultCheckSts;//支咨询审核状态 0 待审核 1 审核通过 2 审核驳回
@property (nonatomic, copy) NSString *consultDesc;//咨询描述
@property (nonatomic,strong)NSArray *consultImgList;//咨询图片列表
@property (nonatomic, copy) NSString *createdTime;//咨询时间
@property (nonatomic, copy) NSString *technicianImgUrl;//技师头像
@property (nonatomic, copy) NSString *technicianMobile;//技师手机号
@property (nonatomic, copy) NSString *technicianName;//技师姓名
@property (nonatomic, copy) NSString *technicianUuid;//技师uuid
@property (nonatomic, copy) NSString *title;//咨询标题
@property (nonatomic, copy) NSString *uuid;//uuid
@property (nonatomic, copy) NSString *consultAmt;//案例金额


@end

NS_ASSUME_NONNULL_END
