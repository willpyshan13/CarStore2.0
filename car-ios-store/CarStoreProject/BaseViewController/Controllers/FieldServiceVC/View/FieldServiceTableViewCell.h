//
//  FieldServiceTableViewCell.h
//  CarStoreProject
//
//  Created by Qin on 2021/2/15.
//

#import <UIKit/UIKit.h>
#import "FieldServiceDesModel.h"

NS_ASSUME_NONNULL_BEGIN

@interface FieldServiceTableViewCell : UITableViewCell
@property (weak, nonatomic) IBOutlet UIView *bgView;

@property (weak, nonatomic) IBOutlet UILabel *pinpaiLab;//品牌
@property (weak, nonatomic) IBOutlet UILabel *chexingLab;//车型
@property (weak, nonatomic) IBOutlet UILabel *miaoshuLab;//描述
@property (weak, nonatomic) IBOutlet UILabel *priceLab;//价格
@property (weak, nonatomic) IBOutlet UILabel *juliLab;//距离
@property (weak, nonatomic) IBOutlet UILabel *zjqdLab;//直接抢单

@property (nonatomic,strong)FieldServiceDesModel *model;

@property (nonatomic, copy) void(^zjqdListActionBlock)(id obj);
@end

NS_ASSUME_NONNULL_END
