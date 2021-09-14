//
//  DazhuSureModel.h
//  CarOwnerProject
//
//  Created by apple on 2021/2/1.
//

#import "BaseModel.h"

NS_ASSUME_NONNULL_BEGIN
//付费咨询 答主列表
@interface DazhuSureModel : BaseModel

@property(nonatomic,copy)NSString *answerAmt;//问答费用 ,
@property(nonatomic,strong)NSArray *brandResList;//品牌列表 ,
@property(nonatomic,copy)NSString *name;//技师名称 ,
@property(nonatomic,copy)NSString *photoImgUrl;//头像图片地址 ,
@property(nonatomic,copy)NSString *qaCount;//问答总数 ,
@property(nonatomic,copy)NSString *score;//评分 ,
@property(nonatomic,copy)NSString *technologyType;//技师类型 ,
@property(nonatomic,copy)NSString *uuid;//技师uuid
@property(nonatomic,copy)NSString *cybAuth;//技师uuid

@end

NS_ASSUME_NONNULL_END
