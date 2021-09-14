//
//  SubstituteTableViewCell.h
//  CarStoreProject
//
//  Created by apple on 2021/1/26.
//

#import <UIKit/UIKit.h>

NS_ASSUME_NONNULL_BEGIN

@interface SubstituteTableViewCell : UITableViewCell
//价格
@property (weak, nonatomic) IBOutlet UILabel *priceLab;
//出发时间
@property (weak, nonatomic) IBOutlet UILabel *startTimeLab;
//出发地
@property (weak, nonatomic) IBOutlet UILabel *startAddressLab;
//目的地
@property (weak, nonatomic) IBOutlet UILabel *bournLab;
//全程
@property (weak, nonatomic) IBOutlet UILabel *wholeCourseLab;
//距离
@property (weak, nonatomic) IBOutlet UILabel *distanceLab;
//直接抢单
@property (weak, nonatomic) IBOutlet UIButton *wordSnatchBtn;
@property (weak, nonatomic) IBOutlet UIView *bgView;


- (void)configSetTableViewCellIndex:(NSIndexPath *)indexPath;


@end

NS_ASSUME_NONNULL_END
