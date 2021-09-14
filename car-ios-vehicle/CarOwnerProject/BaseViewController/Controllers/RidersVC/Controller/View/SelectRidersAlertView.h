//
//  SelectRidersAlertView.h
//  CarOwnerProject
//
//  Created by apple on 2021/2/7.
//

#import <UIKit/UIKit.h>
#import "SelectRidersTModel.h"//筛选品牌、车型、系统
#import "QueryTreeListModel.h"//维修保养
NS_ASSUME_NONNULL_BEGIN

@protocol SelectRidersAlertViewDelegate <NSObject>
@optional
//选中的品牌车型
-(void)getSelectpinpaiOrXitong:(SelectRidersTModel *)model;
//维系保养
-(void)getSelectQueryTreeList:(QueryTreeListModel *)model;

@end
//选择品牌系统
@interface SelectRidersAlertView : UIView

@property (weak, nonatomic) IBOutlet UIView *bgView;
@property (weak, nonatomic) IBOutlet UITableView *listTab;

@property (nonatomic, weak)id<SelectRidersAlertViewDelegate> delegate;

@property (nonatomic,assign)NSInteger type;//1、品牌 2、系统 3、维系保养

+ (instancetype)selectRidersAlertViewAlertView;
+ (id)initSelectRidersAlertView;

- (void)showSelectRidersAlertViewOn:(id)host;

- (void)dismissSelectRidersAlertView;

- (void)configFrame:(CGRect)rect;

@property (nonatomic,strong)NSMutableArray *couponArr;



@end

NS_ASSUME_NONNULL_END
