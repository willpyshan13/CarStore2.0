//
//  JohnTopTitleView.m
//  PrayWithPureBody
//
//  Created by user on 2017/10/9.
//  Copyright © 2017年 申巧. All rights reserved.
//

#import "JohnTopTitleView.h"
#define ViewWidth self.frame.size.width
#define ViewHeight self.frame.size.height

#import "UIImage+Color.h"

@interface JohnTopTitleView ()<UIScrollViewDelegate>{
    //CGFloat _titleHeight;  //标题高度
    CGFloat _lineViewWidth;  //记录底部线长度
    CGFloat _lineViewHeight;  //记录底部线长度

    
    NSInteger _titleCount;  //title个数
}

@property (nonatomic,strong) UISegmentedControl *titleSegment;

@property (nonatomic,strong) UIScrollView *pageScrollView;

@property (nonatomic,strong) UIView *lineView;

@end
@implementation JohnTopTitleView




- (instancetype)initWithFrame:(CGRect)frame segWidth:(CGFloat)segWidth{
    if (self = [super initWithFrame:frame]) {
        _textColor = [UIColor grayColor];
        _selectedTextColor = [UIColor blueColor];
        
        _textFont = FontSize(12);
        _selectedTextFont = FontSize(12);
        _lineColor = [UIColor blueColor];
        self.segWidth = segWidth;
        [self setting];
        
    }
    return self;
}

#pragma mark - 初始化设置
- (void)setting{
    if (@available(iOS 13.0, *)) {
        self.overrideUserInterfaceStyle = UIUserInterfaceStyleLight;
    } else {
        // Fallback on earlier versions
    }
    self.backgroundColor = [UIColor whiteColor];
    //title
    _titleHeight = 30.f;
    self.titleSegment = [[UISegmentedControl alloc]initWithFrame:CGRectMake(0, 0, self.segWidth,_titleHeight)];
    self.titleSegment.tintColor = [UIColor clearColor];
    [self.titleSegment setBackgroundColor:[UIColor clearColor]];

       
    self.titleSegment.layer.borderWidth = 5;
    self.titleSegment.layer.borderColor = [UIColor clearColor].CGColor;

    
    
     // 正常的图片
//    NSDictionary *dicNormal = [NSDictionary dictionaryWithObjectsAndKeys:[UIColor whiteColor], NSForegroundColorAttributeName,FontSize(12),NSFontAttributeName,nil];
//    [self.titleSegment setTitleTextAttributes:dicNormal forState:UIControlStateNormal];
    //未选中的图片
    [self.titleSegment setBackgroundImage:[UIImage imageWithColor:[UIColor clearColor]] forState:UIControlStateNormal barMetrics:UIBarMetricsDefault];
            
    
//    NSDictionary *dicSelect = [NSDictionary dictionaryWithObjectsAndKeys:[UIColor whiteColor], NSForegroundColorAttributeName,FontSize(12),NSFontAttributeName,nil];
//    [self.titleSegment setTitleTextAttributes:dicSelect forState:UIControlStateSelected];
    
    // 设置UISegmentedControl选中的图片
    [self.titleSegment setBackgroundImage:[UIImage imageWithColor:[UIColor clearColor]] forState:UIControlStateSelected barMetrics:UIBarMetricsDefault];

    //去掉分割线
    [self.titleSegment setDividerImage:[UIImage imageWithColor:[UIColor clearColor]] forLeftSegmentState:UIControlStateNormal rightSegmentState:UIControlStateNormal barMetrics:UIBarMetricsDefault];

    
    
    
    NSDictionary* selectedTextAttributes = @{NSFontAttributeName:_selectedTextFont,
                                             NSForegroundColorAttributeName: _selectedTextColor};
    [self.titleSegment setTitleTextAttributes:selectedTextAttributes forState:UIControlStateSelected];//设置文字属性
    NSDictionary* unselectedTextAttributes = @{NSFontAttributeName:_textFont,
                                               NSForegroundColorAttributeName: _textColor};
    [self.titleSegment setTitleTextAttributes:unselectedTextAttributes forState:UIControlStateNormal];
    [self.titleSegment addTarget:self action:@selector(pageChange:) forControlEvents:UIControlEventValueChanged];
    [self addSubview:self.titleSegment];
    
    //滑动sc
    self.pageScrollView = [[UIScrollView alloc]initWithFrame:CGRectMake(0, CGRectGetMaxY(self.titleSegment.frame), ViewWidth, ViewHeight - _titleHeight)];
    self.pageScrollView.bounces = NO;
    self.pageScrollView.pagingEnabled = YES;
    self.pageScrollView.showsVerticalScrollIndicator = NO;
    self.pageScrollView.showsHorizontalScrollIndicator = NO;
    self.pageScrollView.delegate = self;
    [self addSubview:self.pageScrollView];
    
    //底部线
    self.lineView = [[UIView alloc]init];
    self.lineView.backgroundColor = _lineColor;
    [self addSubview:self.lineView];
    
   
}

#pragma mark - set
- (void)setTextColor:(UIColor *)textColor{
    _textColor = textColor;
    NSDictionary* unselectedTextAttributes = @{
                                               NSForegroundColorAttributeName: _textColor};
    [self.titleSegment setTitleTextAttributes:unselectedTextAttributes forState:UIControlStateNormal];
}

- (void)setSelectedTextColor:(UIColor *)selectedTextColor{
    _selectedTextColor = selectedTextColor;
    
    NSDictionary* selectedTextAttributes = @{                                             NSForegroundColorAttributeName: _selectedTextColor};
    [self.titleSegment setTitleTextAttributes:selectedTextAttributes forState:UIControlStateSelected];//设置文字属性
}


-(void)setTextFont:(UIFont *)textFont
{
    _textFont = textFont;
    
    NSDictionary* unselectedTextAttributes = @{NSFontAttributeName:_textFont,
                                               };
    [self.titleSegment setTitleTextAttributes:unselectedTextAttributes forState:UIControlStateNormal];
}
-(void)setSelectedTextFont:(UIFont *)selectedTextFont
{
    _selectedTextFont = selectedTextFont;
    NSDictionary* selectedTextAttributes = @{NSFontAttributeName:_selectedTextFont,
                                            };
    [self.titleSegment setTitleTextAttributes:selectedTextAttributes forState:UIControlStateSelected];//设置文字属性
}


- (void)setLineColor:(UIColor *)lineColor{
    _lineColor = lineColor;
    _lineView.backgroundColor = lineColor;
}

- (void)setTitles:(NSArray *)titles{
    if (titles.count > 0) {
        NSInteger page = titles.count;
        if (_lineViewWidth == 0 ||
            _lineViewWidth > self.segWidth/page) {  //限制线长度范围
            _lineViewWidth = self.segWidth / page;
        }
        
        if (_lineViewHeight == 0) {
            _lineViewHeight = 2;
        }
        
        self.lineView.frame = CGRectMake(0,0,_lineViewWidth, _lineViewHeight);
        self.lineView.center = CGPointMake(self.segWidth / page / 2, _titleHeight);
        self.lineView.clipsToBounds = YES;
        self.lineView.layer.cornerRadius =1.5;
        
        for (NSInteger i = 0; i < titles.count; i ++) {
            [self.titleSegment insertSegmentWithTitle:[titles objectAtIndex:i] atIndex:i animated:NO];
        }
    }
    self.titleSegment.selectedSegmentIndex = 0;
    _titleCount = titles.count;
}

- (void)setLineWidth:(CGFloat)lineWidth{
    _lineViewWidth = lineWidth;
}

-(void)setLineHeight:(CGFloat)lineHeight
{
    _lineViewHeight = lineHeight;
}

- (void)setCanScroll:(BOOL)canScroll{
    self.pageScrollView.scrollEnabled = canScroll;
}

- (void)setSelectedTitle:(NSInteger)selectedTitle{
    if (selectedTitle < _titleCount ) {
        self.titleSegment.selectedSegmentIndex = selectedTitle;
        [self.pageScrollView setContentOffset:CGPointMake(ViewWidth *self.titleSegment.selectedSegmentIndex,0) animated:NO];
        [self changeWithPage:self.titleSegment.selectedSegmentIndex];
    }
}

#pragma mark - 定制VC
- (void)setupViewControllerWithFatherVC:(UIViewController *)fatherVC childVC:(NSArray<UIViewController *>*)childVC{
    self.pageScrollView.contentSize = CGSizeMake(ViewWidth * childVC.count, 0);
    
    for (NSInteger i = 0; i < childVC.count; i ++) {
        UIViewController *vc = [childVC objectAtIndex:i];
        vc.view.frame = CGRectMake(ViewWidth * i, 0, ViewWidth, ViewHeight - _titleHeight);
        [fatherVC addChildViewController:vc];
        [self.pageScrollView addSubview:vc.view];
    }
}

- (void)setupViews:(NSArray<UIView *> *)views{
    self.pageScrollView.contentSize = CGSizeMake(ViewWidth * views.count, 0);
    
    for (NSInteger i = 0; i < views.count; i ++) {
        UIView *view = [views objectAtIndex:i];
        view.frame = CGRectMake(ViewWidth * i, 0, ViewWidth, ViewHeight - _titleHeight);
        [self.pageScrollView addSubview:view];
    }
    
}

#pragma mark - 联动设置
- (void)pageChange:(UISegmentedControl *)seg{
    [self changeWithPage:seg.selectedSegmentIndex];
    [self.pageScrollView setContentOffset:CGPointMake(ViewWidth *seg.selectedSegmentIndex,0) animated:NO];
}

- (void)scrollViewDidEndDecelerating:(UIScrollView *)scrollView{
    NSInteger page = scrollView.contentOffset.x / ViewWidth;
    self.titleSegment.selectedSegmentIndex = page;
    [self changeWithPage:page];
}

- (void)changeWithPage:(NSInteger)page{
    CGFloat lineViewCenterX = self.segWidth / _titleCount / 2 + page *(self.segWidth / _titleCount);
    [UIView transitionWithView:self.lineView duration:0.3 options:      UIViewAnimationOptionAllowUserInteraction  animations:^{
        self.lineView.center = CGPointMake(lineViewCenterX,self->_titleHeight);
    } completion:^(BOOL finished) {
    }];
    [self.delegete didSelectedPage:page];
}

- (NSInteger)selectedPage{
    return self.titleSegment.selectedSegmentIndex;
}

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/

@end
