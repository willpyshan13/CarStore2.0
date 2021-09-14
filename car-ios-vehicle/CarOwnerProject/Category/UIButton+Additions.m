//
//  UIButton+Additions.m
//  IntelligentInn
//
//  Created by 申巧 on 15/11/2.
//  Copyright © 2015年 shenqiao. All rights reserved.
//

#import "UIButton+Additions.h"
#import "UIColor+Additions.h"
#import "UIImage+Color.h"
#import <objc/runtime.h>

@implementation UIButton (Additions)

static const NSString *KEY_HIT_TEST_EDGE_INSETS = @"HitTestEdgeInsets";
@dynamic hitTestEdgeInsets;

+ (UIButton *)initButtonWithBakImage:(id)normal highlighted:(id)highlighted title:(NSString *)title frame:(CGRect)frame font:(UIFont *)font textColor:(UIColor *)color tag:(NSInteger)tag{
    UIButton *button = [UIButton buttonWithType:UIButtonTypeCustom];
    if (normal) {
        if ([normal isKindOfClass:[NSString class]]) {
            [button setBackgroundImage:[UIImage imageNamed:normal] forState:UIControlStateNormal];
        }else{
            [button setBackgroundImage:normal forState:UIControlStateNormal];
        }
    }
    if (highlighted) {
        if ([highlighted isKindOfClass:[NSString class]]) {
            [button setBackgroundImage:[UIImage imageNamed:highlighted] forState:UIControlStateHighlighted];
        }else{
            [button setBackgroundImage:highlighted forState:UIControlStateHighlighted];
        }
    }
    if (title) {
        [button setTitle:title forState:UIControlStateNormal];
    }
    if (CGRectEqualToRect(frame, CGRectZero)) {
        [button sizeThatFits:button.size];
        [button sizeToFit];
    }else{
        button.frame = frame;
    }
    
    button.titleLabel.font = font;
    [button setTitleColor:color forState:UIControlStateNormal];
    button.tag = tag;
    
    return button;
}

+ (UIButton *)initButtonWithImage:(id)normal highlighted:(id)highlighted selected:(id)selected frame:(CGRect)frame tag:(NSInteger)tag{
    UIButton *button = [UIButton buttonWithType:UIButtonTypeCustom];
    if (normal) {
        if ([normal isKindOfClass:[UIImage class]]) {
            [button setImage:normal forState:UIControlStateNormal];
        }else{
            [button setImage:[UIImage imageNamed:normal] forState:UIControlStateNormal];
        }
    }
    if (highlighted) {
        if ([highlighted isKindOfClass:[UIImage class]]) {
            [button setImage:highlighted forState:UIControlStateHighlighted];
        }else{
            [button setImage:[UIImage imageNamed:highlighted] forState:UIControlStateHighlighted];
        }
    }
    if (selected) {
        if ([selected isKindOfClass:[UIImage class]]) {
            [button setImage:selected forState:UIControlStateSelected];
        }else{
            [button setImage:[UIImage imageNamed:selected] forState:UIControlStateSelected];
        }
    }
    
    if (CGRectEqualToRect(frame, CGRectZero)) {
        [button sizeThatFits:button.size];
        [button sizeToFit];
    }else{
        button.frame = frame;
    }
    button.tag = tag;
    return button;
}

//纯文本按钮
+ (UIButton *)initTextButtonWithTitle:(NSString *)title font:(UIFont *)font titleColor:(UIColor *)color selectedColor:(UIColor *)selectedColor highlightColor:(UIColor *)highColor frame:(CGRect)frame tag:(NSInteger)tag{
    UIButton *button = [UIButton buttonWithType:UIButtonTypeCustom];
    button.frame = (CGRect){0,0,150,40};
    button.tag = tag;
    [button setTitle:title forState:UIControlStateNormal];
    [button.titleLabel setFont:font];
    if (color) {
        [button setTitleColor:color forState:UIControlStateNormal];
    }
    if (selectedColor) {
        [button setTitleColor:selectedColor forState:UIControlStateSelected];
    }
    if (highColor) {
        [button setTitleColor:highColor forState:UIControlStateHighlighted];
    }
    
    if (CGRectEqualToRect(frame, CGRectZero)) {
        [button sizeToFit];
    }else{
        button.frame = frame;
    }
    
    return button;
}

- (void)setHitTestEdgeInsets:(UIEdgeInsets)hitTestEdgeInsets{
    NSValue *value = [NSValue value:&hitTestEdgeInsets withObjCType:@encode(UIEdgeInsets)];
    objc_setAssociatedObject(self, &KEY_HIT_TEST_EDGE_INSETS, value, OBJC_ASSOCIATION_RETAIN_NONATOMIC);
}

- (UIEdgeInsets)hitTestEdgeInsets{
    NSValue *value = objc_getAssociatedObject(self, &KEY_HIT_TEST_EDGE_INSETS);
    if (value) {
        UIEdgeInsets edgInsets;
        [value getValue:&edgInsets];
        return edgInsets;
    }
    return UIEdgeInsetsZero;
}

//改变btn的点击范围
- (BOOL)pointInside:(CGPoint)point withEvent:(UIEvent *)event{
    if (UIEdgeInsetsEqualToEdgeInsets(self.hitTestEdgeInsets, UIEdgeInsetsZero) || !self.enabled || self.hidden) {
        return [super pointInside:point withEvent:event];
    }
    CGRect relativeFrame = self.bounds;
    CGRect hitFrame = UIEdgeInsetsInsetRect(relativeFrame, self.hitTestEdgeInsets);
    return CGRectContainsPoint(hitFrame, point);
}




@end
