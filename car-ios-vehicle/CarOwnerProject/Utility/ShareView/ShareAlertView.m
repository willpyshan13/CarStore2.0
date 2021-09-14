//
//  ShareAlertView.m
//  KeChiProduct
//
//  Created by apple on 2020/8/13.
//  Copyright © 2020 apple. All rights reserved.
//

#import "ShareAlertView.h"

@implementation ShareAlertView


+ (instancetype)showShareAlertView{
    static ShareAlertView *bind = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        bind = [ShareAlertView initShareAlertView];
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
    
    
    self.weixinBtn.showsTouchWhenHighlighted = NO;
    self.weixinBtn.buttonImagePosition = FSCustomButtonImagePositionTop;
    self.weixinBtn.contentHorizontalAlignment = UIControlContentHorizontalAlignmentCenter;
    
    self.weixinFriendsBtn.showsTouchWhenHighlighted = NO;
    self.weixinFriendsBtn.buttonImagePosition = FSCustomButtonImagePositionTop;
    self.weixinFriendsBtn.contentHorizontalAlignment = UIControlContentHorizontalAlignmentCenter;
  
}


+ (id)initShareAlertView{
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
- (IBAction)shareBtnClick:(id)sender {
    
    
    UIButton *btn = sender;
     if (self.shareBtnBlock) {
         self.shareBtnBlock(sender, btn.tag);
     }

    [self dismissShareAlertView];
    
}

- (void)showShareAlertViewOn:(id)host{
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
    
        [UIView animateWithDuration:0.3 animations:^{
    
            self.bgView.y = self.height - self.bgView.height;
            
           } completion:^(BOOL finished) {
    
           }];

    
       
}
- (IBAction)closeBtnClick:(id)sender {
    [self dismissShareAlertView];
}


- (void)dismissShareAlertView
{
        
    [UIView animateWithDuration:0.3 animations:^{
    
        self.bgView.y = self.height;
        
    } completion:^(BOOL finished) {
    
        [self removeFromSuperview];
        
    }];
}

- (void)touchesEnded:(NSSet<UITouch *> *)touches withEvent:(UIEvent *)event{
    UITouch *touch = [touches anyObject];
    UIView *view = [touch view];
    if (view != _bgView) {
        if (self.superview) {

            [self dismissShareAlertView];
        }
    }
}
/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
 
 
*/

@end
