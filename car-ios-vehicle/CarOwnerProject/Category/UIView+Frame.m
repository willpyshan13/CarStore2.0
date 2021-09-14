//
//  UIView+Frame.m
//  GoodBarberV2
//
//  Created by MPow on 31/10/13.
//  Copyright (c) 2013 DuoApps. All rights reserved.
//

#import "UIView+Frame.h"

@implementation UIView (Frame)

-(void)setX:(CGFloat)x{
    
    CGRect frame=self.frame;
    frame.origin.x=x;
    self.frame=frame;
    
}

-(void)setY:(CGFloat)y{
    
    CGRect frame=self.frame;
    frame.origin.y=y;
    self.frame=frame;
    
}

-(void)setWidth:(CGFloat)width{
    
    CGRect frame=self.frame;
    frame.size.width=width;
    self.frame=frame;
}

-(void)setHeight:(CGFloat)height{
    
    CGRect frame=self.frame;
    frame.size.height=height;
    self.frame=frame;
}

-(void)setCenterX:(CGFloat)centerX
{
    CGPoint center = self.center;
    center.x = centerX;
    self.center = center;
}

-(void)setCenterY:(CGFloat)centerY
{
    CGPoint center = self.center;
    center.y = centerY;
    self.center = center;
}

-(void)setSize:(CGSize)size
{
    CGRect frame = self.frame;
    frame.size = size;
    self.frame = frame;
}

-(void)setOrigin:(CGPoint)origin
{
    CGRect frame = self.frame;
    frame.origin = origin;
    self.frame = frame;
}

-(CGFloat)x{
    return self.frame.origin.x;
}

-(CGFloat)y{
    return self.frame.origin.y;
}

-(CGFloat)width{
    return self.frame.size.width;
}

-(CGFloat)height{
    return self.frame.size.height;
}

-(CGFloat)centerX
{
    return self.center.x;
}

-(CGFloat)centerY
{
    return self.center.y;
}

-(CGSize)size
{
    return self.frame.size;
}

-(CGPoint)origin
{
    return self.frame.origin;
}

- (CGFloat)bottom{
    return self.frame.origin.y + self.frame.size.height;
}
- (void)setBottom:(CGFloat)newBottom{
    CGRect newframe = self.frame;
    newframe.origin.y = newBottom - self.frame.size.height;
    self.frame = newframe;
}

- (CGFloat)right{
    return self.frame.origin.x + self.frame.size.width;
}
- (void)setRight:(CGFloat)newRight{
    CGFloat delta = newRight - (self.frame.origin.x + self.frame.size.width);
    CGRect newframe = self.frame;
    newframe.origin.x += delta;
    self.frame = newframe;
}

// Query other frame locations
- (CGPoint)bottomRight{
    CGFloat x = self.frame.origin.x + self.frame.size.width;
    CGFloat y = self.frame.origin.y + self.frame.size.height;
    return CGPointMake(x, y);
}

- (CGPoint)bottomLeft{
    CGFloat x = self.frame.origin.x;
    CGFloat y = self.frame.origin.y + self.frame.size.height;
    return CGPointMake(x, y);
}

- (CGPoint)topRight{
    CGFloat x = self.frame.origin.x + self.frame.size.width;
    CGFloat y = self.frame.origin.y;
    return CGPointMake(x, y);
}

- (CGPoint)topLeft{
    CGFloat x = self.frame.origin.x;
    CGFloat y = self.frame.origin.y;
    return CGPointMake(x, y);
}



+ (UIView *)remindView:(NSArray *)aText {
    
    //
    UIView *remindView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 50)];
    
    //
    UILabel *titleLabel = [[UILabel alloc] initWithFrame: CGRectMake(0, 0, SCREEN_WIDTH, 20)];
    [remindView addSubview:titleLabel];
    
    
    if (![aText[0] isEqualToString:@"未登录"]) {
        
        titleLabel.text = aText[0];
        titleLabel.textColor = [UIColor orangeColor];
        titleLabel.font = [UIFont systemFontOfSize:16];
        titleLabel.textAlignment = NSTextAlignmentCenter;
    }
    else {
        
    }
    if (aText.count == 1) {
        
        CGRect titleLabelFrame = CGRectMake(0, 0, SCREEN_WIDTH, 20);
        titleLabel.frame = titleLabelFrame;
        titleLabel.center = remindView.center;
        if ([aText[0] isEqualToString:@"未登录"]) {
            titleLabel.hidden = YES;
        }
        
        
    }
    else {
        
        CGRect titleLabelFrame = CGRectMake(0, 0, SCREEN_WIDTH, 20);
        titleLabel.frame = titleLabelFrame;
        
        //
        CGRect contentLabelFrame = CGRectMake(0, 30, SCREEN_WIDTH, 20);
        UILabel *contentLabel = [[UILabel alloc] initWithFrame:contentLabelFrame];
        [remindView addSubview:contentLabel];
        
        contentLabel.text = aText[1];
        contentLabel.textColor = [UIColor orangeColor];
        contentLabel.font = [UIFont systemFontOfSize:14];
        contentLabel.textAlignment = NSTextAlignmentCenter;
    }
    
    
    return remindView;
}

- (instancetype)czh_cornerAllCornersWithCornerRadius:(CGFloat)cornerRadius {
    
    CAShapeLayer *shapeLayer = [CAShapeLayer layer];
    UIBezierPath *path = [UIBezierPath bezierPathWithRoundedRect:self.bounds byRoundingCorners:UIRectCornerAllCorners cornerRadii:CGSizeMake(cornerRadius, cornerRadius)];
    shapeLayer.path = path.CGPath;
    self.layer.mask = shapeLayer;
    return self;
}

@end


#pragma mark - 视图层次扩展 -
@implementation UIView (ZOrder)

/// 当前视图在父视图中的索引
- (NSUInteger)getSubviewIndex {
    return [self.superview.subviews indexOfObject:self];
}

/// 将视图置于父视图最上面
- (void)bringToFront {
    [self.superview bringSubviewToFront:self];
}

/// 将视图置于父视图最下面
- (void)sendToBack {
    [self.superview sendSubviewToBack:self];
}

/// 视图层次上移一层
- (void)bringOneLevelUp
{
    NSInteger currentIndex = [self getSubviewIndex];
    [self.superview exchangeSubviewAtIndex:currentIndex withSubviewAtIndex:currentIndex+1];
}

/// 视图层次下移一层
- (void)sendOneLevelDown {
    NSInteger currentIndex = [self getSubviewIndex];
    [self.superview exchangeSubviewAtIndex:currentIndex withSubviewAtIndex:currentIndex-1];
}

/// 是否在最上面
- (BOOL)isInFront {
    return [self.superview.subviews lastObject] == self;
}

/// 是否在最下面
- (BOOL)isAtBack {
    return [self.superview.subviews objectAtIndex:0] == self;
}

/// 视图交换层次
- (void)swapDepthsWithView:(UIView*)swapView {
    [self.superview exchangeSubviewAtIndex:[self getSubviewIndex] withSubviewAtIndex:[swapView getSubviewIndex]];
}

/**
 获取view所在的viewController

 @return view所在的控制器
 */
- (UIViewController *)ll_viewController {
    for (UIWindow *window in [UIApplication sharedApplication].windows.reverseObjectEnumerator) {
        UIView *tempView = window.subviews.lastObject;
        for (UIView *subview in window.subviews.reverseObjectEnumerator) {
            if ([subview isKindOfClass:NSClassFromString(@"UILayoutContainerView")]) {
                tempView = subview;
                break;
            }
        }
        BOOL(^canNext)(UIResponder *) = ^(UIResponder *responder){
            if (![responder isKindOfClass:[UIViewController class]]) {
                return YES;
            } else if ([responder isKindOfClass:[UINavigationController class]]) {
                return YES;
            } else if ([responder isKindOfClass:[UITabBarController class]]) {
                return YES;
            } else if ([responder isKindOfClass:NSClassFromString(@"UIInputWindowController")]) {
                return YES;
            }
            return NO;
        };
        UIResponder *nextResponder = tempView.nextResponder;
        
        while (canNext(nextResponder)) {
            tempView = tempView.subviews.firstObject;
            if (!tempView) {
                return nil;
            }
            nextResponder = tempView.nextResponder;
        }
        UIViewController *currentVC = (UIViewController *)nextResponder;
        if (currentVC) {
            return currentVC;
        }
    }
    return nil;
}








@end
