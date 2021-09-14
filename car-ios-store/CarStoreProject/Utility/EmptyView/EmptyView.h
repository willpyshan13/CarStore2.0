//
//  EmptyView.h
//  InHoldingProject
//
//  Created by user on 2018/3/19.
//  Copyright © 2018年 wangshuping. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface EmptyView : UIView

+ (instancetype)showEmptyView;

@property (weak, nonatomic) IBOutlet UIImageView *emptyImage;
@property (weak, nonatomic) IBOutlet UILabel *titleLabel;
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *imageTopCon;


+ (id)initEmptyView;

- (void)showEmptyViewOn:(id)host;


- (void)configFrame:(CGRect)rect;

@property (nonatomic, strong) NSDictionary *paraDict;
- (void)dismissEmptyView;

@property (nonatomic, copy) void(^detailNoMessageGoBlock)(id obj);

@end
