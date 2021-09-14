//
//  ShareAlertView.h
//  KeChiProduct
//
//  Created by apple on 2020/8/13.
//  Copyright © 2020 apple. All rights reserved.
//

#import <UIKit/UIKit.h>

NS_ASSUME_NONNULL_BEGIN

@interface ShareAlertView : UIView


+ (instancetype)showShareAlertView;

+ (id)initShareAlertView;

- (void)showShareAlertViewOn:(id)host;


- (void)configFrame:(CGRect)rect;

@property (nonatomic, strong) NSDictionary *paraDict;
- (void)dismissShareAlertView;


@property (weak, nonatomic) IBOutlet UIView *bgView;

@property (weak, nonatomic) IBOutlet FSCustomButton *weixinBtn;//微信好友
@property (weak, nonatomic) IBOutlet FSCustomButton *weixinFriendsBtn;//微信朋友圈

@property (nonatomic, copy) void(^shareBtnBlock)(id obj, NSInteger type);

@end

NS_ASSUME_NONNULL_END
