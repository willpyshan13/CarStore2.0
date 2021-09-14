//
//  UIButton+Additions.h
//  IntelligentInn
//
//  Created by 申巧 on 15/11/2.
//  Copyright © 2015年 shenqiao. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface UIButton (Additions)

//背景图片按钮
+ (UIButton *)initButtonWithBakImage:(id)normal highlighted:(id)highlighted title:(NSString *)title frame:(CGRect)frame font:(UIFont *)font textColor:(UIColor *)color tag:(NSInteger)tag;

//图片按钮
+ (UIButton *)initButtonWithImage:(id)normal highlighted:(id)highlighted selected:(id)selected frame:(CGRect)frame tag:(NSInteger)tag;

//纯文本按钮
+ (UIButton *)initTextButtonWithTitle:(NSString *)title font:(UIFont *)font titleColor:(UIColor *)color selectedColor:(UIColor *)selectedColor highlightColor:(UIColor *)highColor frame:(CGRect)frame tag:(NSInteger)tag;

@property(nonatomic, assign) UIEdgeInsets hitTestEdgeInsets;



@end
