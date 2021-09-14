//
//  ShareTableViewCell.h
//  CarOwnerProject
//
//  Created by apple on 2021/1/26.
//

#import <UIKit/UIKit.h>

NS_ASSUME_NONNULL_BEGIN

@interface ShareTableViewCell : UITableViewCell
@property (weak, nonatomic) IBOutlet UIView *bgView;
@property (weak, nonatomic) IBOutlet UIButton *shareBtn;//分享按钮
@property (nonatomic, copy) void(^shareBlock)(id obj);
@end

NS_ASSUME_NONNULL_END
