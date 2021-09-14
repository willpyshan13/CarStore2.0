//
//  MyOrderTableViewCell.h
//  CarOwnerProject
//
//  Created by apple on 2021/1/26.
//

#import <UIKit/UIKit.h>

NS_ASSUME_NONNULL_BEGIN

@interface MyOrderTableViewCell : UITableViewCell
@property (weak, nonatomic) IBOutlet UIView *bgView;
@property (weak, nonatomic) IBOutlet FSCustomButton *dfkBtn;//代付款
@property (weak, nonatomic) IBOutlet FSCustomButton *dfw;//待服务
@property (weak, nonatomic) IBOutlet FSCustomButton *dpj;//待评价
@property (weak, nonatomic) IBOutlet FSCustomButton *shtkBtn;//退款售后
@property (nonatomic, copy) void(^orderListBlock)(id obj, NSInteger type);
@end

NS_ASSUME_NONNULL_END
