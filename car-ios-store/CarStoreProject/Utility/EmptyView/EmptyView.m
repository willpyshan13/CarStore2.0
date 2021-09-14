//
//  EmptyView.m
//  InHoldingProject
//
//  Created by user on 2018/3/19.
//  Copyright © 2018年 wangshuping. All rights reserved.
//

#import "EmptyView.h"


@implementation EmptyView


+ (instancetype)showEmptyView{
    static EmptyView *bind = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        bind = [EmptyView initEmptyView];
        [bind configFrame:[UIScreen mainScreen].bounds];
    });
    return bind;
}
- (instancetype)initWithCoder:(NSCoder *)aDecoder{
    if (self = [super initWithCoder:aDecoder]) {
    }
    return self;
}

- (void)configFrame:(CGRect)rect
{
    self.frame = rect;
}

- (void)awakeFromNib{
    [super awakeFromNib];
    [self commonInit];
}

- (void)layoutSubviews{
    [super layoutSubviews];
}

- (void)commonInit
{
}


+ (id)initEmptyView{
    //loadNibNamed 方法调用了initWithCoder 方法。
    NSArray *array = [[NSBundle mainBundle] loadNibNamed:NSStringFromClass([self class]) owner:self options:nil];
    for (id obj in array) {
        Class classss = object_getClass(obj);
        if ([NSStringFromClass(classss) isEqualToString:NSStringFromClass(self)]) {
            return obj;
        }
    }
    return nil;
}

- (void)showEmptyViewOn:(id)host{
    if ([host isKindOfClass:[UIView class]])
    {
        [host addSubview:self];
        
    }else if ([host isKindOfClass:[UIViewController class]])
    {
        [((UIViewController *)host).view addSubview:self];
    }
    else
    {
        UIWindow *window = [UIApplication sharedApplication].keyWindow;
        [window addSubview:self];
    }
    
}
- (IBAction)goBtn:(id)sender
{
    if (self.detailNoMessageGoBlock) {
        self.detailNoMessageGoBlock(sender);
    }
}


- (void)dismissEmptyView
{
    [self removeFromSuperview];
}


/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/

@end
