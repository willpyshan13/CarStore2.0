//
//  UIView+Frame.h
//  GoodBarberV2
//
//  Created by MPow on 31/10/13.
//  Copyright (c) 2013 DuoApps. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface UIView (Frame)

@property(nonatomic,readwrite) CGFloat x,y,width,height;

@property (nonatomic,readwrite) CGPoint origin;
@property (nonatomic,readwrite) CGSize size;

@property (nonatomic, readwrite) CGFloat centerX;
@property (nonatomic, readwrite) CGFloat centerY;

@property CGFloat bottom;
@property CGFloat right;

@property (readonly) CGPoint bottomLeft;
@property (readonly) CGPoint bottomRight;
@property (readonly) CGPoint topRight;
@property (readonly) CGPoint topLeft;


+ (UIView *)remindView:(NSArray *)aText;


- (instancetype)czh_cornerAllCornersWithCornerRadius:(CGFloat)cornerRadius;

@end


#pragma mark - 视图层次扩展 -
@interface UIView (ZOrder)

/// 当前视图在父视图中的索引
- (NSUInteger)getSubviewIndex;

/// 将视图置于父视图最上面
- (void)bringToFront;

/// 将视图置于父视图最下面
- (void)sendToBack;

/// 视图层次上移一层
- (void)bringOneLevelUp;

/// 视图层次下移一层
- (void)sendOneLevelDown;

/// 是否在最上面
- (BOOL)isInFront;

/// 是否在最下面
- (BOOL)isAtBack;

/// 视图交换层次
- (void)swapDepthsWithView:(UIView*)swapView;

/**
 获取view所在的viewController
 
 @return view所在的控制器
 */
- (UIViewController *)ll_viewController;


@end
