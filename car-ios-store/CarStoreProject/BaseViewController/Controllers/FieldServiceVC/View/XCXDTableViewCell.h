//
//  XCXDTableViewCell.h
//  CarStoreProject
//
//  Created by apple on 2021/3/9.
//

#import <UIKit/UIKit.h>
#import "FieldServiceDesModel.h"


NS_ASSUME_NONNULL_BEGIN
//现场支持下单
@interface XCXDTableViewCell : UITableViewCell
@property (weak, nonatomic) IBOutlet UIView *bgView;

@property (weak, nonatomic) IBOutlet UILabel *pinpaiLab;//品牌
@property (weak, nonatomic) IBOutlet UILabel *chexingLab;//车型
@property (weak, nonatomic) IBOutlet UILabel *miaoshuLab;//描述
@property (weak, nonatomic) IBOutlet UILabel *priceLab;//价格
@property (weak, nonatomic) IBOutlet UILabel *juliLab;//距离

@property (nonatomic,strong)FieldServiceDesModel *model;

@end

NS_ASSUME_NONNULL_END
