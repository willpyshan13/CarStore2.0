//
//  JohnTopTitleView.h
//  PrayWithPureBody
//
//  Created by user on 2017/10/9.
//  Copyright © 2017年 申巧. All rights reserved.
//

#import <UIKit/UIKit.h>
@protocol JohnTopTitleViewDelegate <NSObject>

@optional
- (void)didSelectedPage:(NSInteger)page;

@end
@interface JohnTopTitleView : UIView



@property (nonatomic,weak) id<JohnTopTitleViewDelegate>delegete;


- (instancetype)initWithFrame:(CGRect)frame segWidth:(CGFloat)segWidth;


//title数组
@property (nonatomic,strong) NSArray<NSString*> *titles;

//线宽（默认屏幕宽度除以标题数）
@property (nonatomic,assign) CGFloat lineWidth;

//线高
@property (nonatomic,assign) CGFloat lineHeight;

//线条宽度
@property (nonatomic,assign) CGFloat segWidth;

@property (nonatomic,assign,readonly) NSInteger selectedPage;
//选中字体颜色
@property (nonatomic,strong) UIColor* selectedTextColor;
//默认颜色
@property (nonatomic,strong) UIColor* textColor;


//默认字体大小
@property (nonatomic,strong) UIFont* textFont;
//选中字体大小
@property (nonatomic,strong) UIFont* selectedTextFont;

//线条颜色
@property (nonatomic,strong) UIColor* lineColor;

@property (nonatomic,assign,readonly) CGFloat titleHeight;  //标题栏高度

@property (nonatomic,assign,readwrite) NSInteger selectedTitle;//设置默认选中的title

@property (nonatomic,assign) BOOL canScroll;
/**
 *传入父控制器和子控制器数组即可
 **/
- (void)setupViewControllerWithFatherVC:(UIViewController *)fatherVC childVC:(NSArray<UIViewController *>*)childVC;

/**
 *传入view
 **/
- (void)setupViews:(NSArray<UIView *>*)views;
@end
