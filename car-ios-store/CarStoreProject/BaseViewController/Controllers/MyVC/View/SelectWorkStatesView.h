//
//  SelectWorkStatesView.h
//  CarStoreProject
//
//  Created by apple on 2021/3/11.
//

#import <UIKit/UIKit.h>

NS_ASSUME_NONNULL_BEGIN

@interface SelectWorkStatesView : UIView
@property (weak, nonatomic) IBOutlet UIView *bgView;

@property (weak, nonatomic) IBOutlet UIView *workBgView;//工作中
@property (weak, nonatomic) IBOutlet UIImageView *workImg;//工作中

@property (weak, nonatomic) IBOutlet UIView *offworkBgView;//已下班
@property (weak, nonatomic) IBOutlet UIImageView *offworkImg;//已下班


+ (instancetype)selectWorkStatesViewAlertView;
+ (id)initSelectWorkStatesView;

- (void)showSelectWorkStatesViewOn:(id)host;

- (void)dismissSelectWorkStatesView;

- (void)configFrame:(CGRect)rect;


//工作状态
@property (nonatomic, copy) void(^workStatesActionBlock)(id obj,NSString *workStateTitle);

@end

NS_ASSUME_NONNULL_END
