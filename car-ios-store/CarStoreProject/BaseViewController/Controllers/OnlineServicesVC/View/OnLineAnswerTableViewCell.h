//
//  OnLineAnswerTableViewCell.h
//  CarStoreProject
//
//  Created by apple on 2021/1/27.
//

#import <UIKit/UIKit.h>
#import "OnlineAnswerModel.h"

NS_ASSUME_NONNULL_BEGIN
//线上回答
@interface OnLineAnswerTableViewCell : UITableViewCell
@property (weak, nonatomic) IBOutlet UIView *bgView;
@property (weak, nonatomic) IBOutlet UILabel *titleLab;//标题
@property (weak, nonatomic) IBOutlet UILabel *detailLab;//内容
@property (weak, nonatomic) IBOutlet UILabel *priceLab;
@property (weak, nonatomic) IBOutlet UIButton *answerBtn;
@property (nonatomic,strong)OnlineAnswerModel *model;
@end

NS_ASSUME_NONNULL_END
