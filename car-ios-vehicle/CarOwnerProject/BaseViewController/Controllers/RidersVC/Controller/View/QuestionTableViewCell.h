//
//  QuestionTableViewCell.h
//  CarOwnerProject
//
//  Created by apple on 2021/1/26.
//

#import <UIKit/UIKit.h>
#import "DazhuListModel.h"//问题列表
NS_ASSUME_NONNULL_BEGIN

@interface QuestionTableViewCell : UITableViewCell
@property (weak, nonatomic) IBOutlet UIImageView *headImg;//头型
@property (weak, nonatomic) IBOutlet UILabel *titLab;//标题
@property (weak, nonatomic) IBOutlet UILabel *questLab;//问题
@property (weak, nonatomic) IBOutlet UILabel *timeLab;//时间
@property (weak, nonatomic) IBOutlet UILabel *answerLab;//答案

@property (weak, nonatomic) IBOutlet UILabel *priceLab;//价格
@property (weak, nonatomic) IBOutlet UIButton *ptBtn;//旁听
@property (nonatomic,strong)DazhuListModel *model;

@property (nonatomic, copy) void(^pangtingBlock)(id obj);

@end

NS_ASSUME_NONNULL_END
