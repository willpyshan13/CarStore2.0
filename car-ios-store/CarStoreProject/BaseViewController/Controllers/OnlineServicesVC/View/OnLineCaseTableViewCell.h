//
//  OnLineCaseTableViewCell.h
//  CarStoreProject
//
//  Created by apple on 2021/1/27.
//

#import <UIKit/UIKit.h>
#import "OnlineCaseModel.h"

NS_ASSUME_NONNULL_BEGIN

@interface OnLineCaseTableViewCell : UITableViewCell
@property (weak, nonatomic) IBOutlet UIView *bgView;
@property (weak, nonatomic) IBOutlet UILabel *priceLab;//收益
@property (weak, nonatomic) IBOutlet UILabel *salesLab;//销量
@property (weak, nonatomic) IBOutlet UILabel *nameLab;
@property (nonatomic,strong)OnlineCaseModel *model;

@end

NS_ASSUME_NONNULL_END
