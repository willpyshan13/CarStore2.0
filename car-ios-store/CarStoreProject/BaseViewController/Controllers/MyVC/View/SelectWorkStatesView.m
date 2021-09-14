//
//  SelectWorkStatesView.m
//  CarStoreProject
//
//  Created by apple on 2021/3/11.
//

#import "SelectWorkStatesView.h"

@implementation SelectWorkStatesView

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/


+ (instancetype)selectWorkStatesViewAlertView{
    static SelectWorkStatesView *bind = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        bind = [SelectWorkStatesView initSelectWorkStatesView];
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
    _bgView.y = self.frame.size.height;
    self.bgView.backgroundColor = NumberFFColor;
    [self commonInit];
}

- (void)layoutSubviews{
    [super layoutSubviews];
}

- (void)commonInit
{
    
//    self.bgView.layer.cornerRadius = 10.0f;
    UIBezierPath *maskPath = [UIBezierPath bezierPathWithRoundedRect:self.bgView.bounds byRoundingCorners:UIRectCornerTopLeft | UIRectCornerTopRight cornerRadii:CGSizeMake(10, 10)];
    CAShapeLayer *maskLayer = [[CAShapeLayer alloc] init];
    maskLayer.frame = self.bgView.bounds;
    maskLayer.path = maskPath.CGPath;
    [self.bgView.layer addSublayer:maskLayer];
    self.bgView.layer.mask = maskLayer;
    self.bgView.layer.masksToBounds = YES;
    self.bgView.userInteractionEnabled = YES;
    
    
    //工作中
    self.workBgView.userInteractionEnabled = YES;
    [self.workBgView addGestureRecognizer:[[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(workDidCLick:)]];
    
    //已下班
    self.offworkBgView.userInteractionEnabled = YES;
    [self.offworkBgView addGestureRecognizer:[[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(offworkDidCLick:)]];
}

#pragma mark == 工作中
-(void)workDidCLick:(UITapGestureRecognizer *)sender
{
    self.workImg.hidden = NO;
    self.offworkImg.hidden = YES;
    
    UIView *view = [sender view];
    if (self.workStatesActionBlock) {
        self.workStatesActionBlock(view,@"工作中");
    }
    [self dismissClick];
}
#pragma mark == 关闭
- (IBAction)closeView:(id)sender {

    [self dismissClick];
}
#pragma mark == 同步工作状态
- (IBAction)workStateBtnClick:(id)sender
{
    DLog(@"同步工作状态");
}


#pragma mark == 已下班
-(void)offworkDidCLick:(UITapGestureRecognizer *)sender
{
    self.workImg.hidden = YES;
    self.offworkImg.hidden = NO;
    
    UIView *view = [sender view];
    if (self.workStatesActionBlock) {
        self.workStatesActionBlock(view,@"已下班");
    }
    [self dismissClick];
}


- (void)touchesEnded:(NSSet<UITouch *> *)touches withEvent:(UIEvent *)event{
    UITouch *touch = [touches anyObject];
    UIView *view = [touch view];
    if (view != _bgView) {
        [self dismissClick];
//        if (self.superview)
//        {
//            [self dismissClick];
//        }
    }
}




+ (id)initSelectWorkStatesView{
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

- (void)showSelectWorkStatesViewOn:(id)host{
    
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

-(void)dismissClick
{
    [self dismissSelectWorkStatesView];
}

- (void)dismissSelectWorkStatesView
{
    [UIView animateWithDuration:0.3 animations:^{
    
        self.bgView.y = self.height;
        
    } completion:^(BOOL finished) {
    
        [self removeFromSuperview];
        
    }];
}



@end
