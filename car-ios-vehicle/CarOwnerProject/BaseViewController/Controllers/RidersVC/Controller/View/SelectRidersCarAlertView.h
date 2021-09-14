//
//  SelectRidersCarAlertView.h
//  CarOwnerProject
//
//  Created by apple on 2021/2/7.
//

#import <UIKit/UIKit.h>
#import "SelectRidersTModel.h"//车型

NS_ASSUME_NONNULL_BEGIN
@protocol SelectRidersCarAlertViewDelegate <NSObject>
@optional
//选中的车型
-(void)getSelectCarType:(SelectRidersTModel *)model;


@end
@interface SelectRidersCarAlertView : UIView
@property (weak, nonatomic) IBOutlet UIView *bgView;
@property (weak, nonatomic) IBOutlet UITableView *listTab;


//选择车型
+ (instancetype)selectRidersAlertViewCarAlertView;
+ (id)initSelectRidersCarAlertView;

- (void)showSelectRidersCarAlertViewOn:(id)host;

- (void)dismissSelectRidersCarAlertView;

- (void)configFrame:(CGRect)rect;

@property (nonatomic,strong)NSMutableArray *couponArr;

@property (nonatomic, weak)id<SelectRidersCarAlertViewDelegate> delegate;

@end

NS_ASSUME_NONNULL_END
