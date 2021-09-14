//
//  IntroduceGuideView.m
//  DaLongInsurance
//
//  Created by 申巧 on 16/9/2.
//  Copyright © 2016年 申巧. All rights reserved.
//

#import "IntroduceGuideView.h"
#import "UIImage+GifPlay.h"
#import "UIView+Frame.h"

@interface IntroduceGuideView ()<UIScrollViewDelegate>
@property (nonatomic, strong) NSArray *backgroundViews;
@property (nonatomic, strong) NSArray *scrollViewPages;
@property (nonatomic, strong) UIPageControl *pageControl;
@property (nonatomic, assign) NSInteger centerPageIndex;

@end

@implementation IntroduceGuideView

- (id)initWithCoverImageNames:(NSArray *)coverNames backgroundImageNames:(NSArray *)bgNames
{
    if (self = [super initWithFrame:[UIScreen mainScreen].bounds]) {
        NSArray *array = @[@"",@"",@""];
        [self initSelfWithCoverNames:array backgroundImageNames:bgNames];
    }
    return self;
}

- (id)initWithCoverImageNames:(NSArray *)coverNames backgroundImageNames:(NSArray *)bgNames button:(UIButton *)button
{
    if (self = [super initWithFrame:[UIScreen mainScreen].bounds]) {
        [self initSelfWithCoverNames:coverNames backgroundImageNames:bgNames];
        self.enterButton = button;
    }
    return self;
}

- (void)initSelfWithCoverNames:(NSArray *)coverNames backgroundImageNames:(NSArray *)bgNames{
    self.coverImageNames = coverNames;
    self.backgroundImageNames = bgNames;
    [self initUIView];
}

- (void)initUIView {
    self.frame = [UIScreen mainScreen].bounds;
    
    [self addBackgroundViews];
    
    self.pagingScrollView = [[UIScrollView alloc] initWithFrame:self.bounds];
    self.pagingScrollView.delegate = self;
    self.pagingScrollView.pagingEnabled = YES;
    self.pagingScrollView.showsHorizontalScrollIndicator = NO;
//    self.pagingScrollView.bounces = NO;
    [self addSubview:self.pagingScrollView];
    
    self.pageControl = [[UIPageControl alloc] initWithFrame:[self frameOfPageControl]];
    self.pageControl.pageIndicatorTintColor = NumberFFColor;
    self.pageControl.currentPageIndicatorTintColor = Number1691E3Color;
    [self addSubview:self.pageControl];
//    self.pageControl.hidden = YES;
    
    [self reloadPages];    
}

- (void)addBackgroundViews{
    CGRect frame = self.bounds;
    if ([self backgroundImageNames].count) {
        NSMutableArray *tmpArray = [NSMutableArray new];
        [[[[self backgroundImageNames] reverseObjectEnumerator] allObjects] enumerateObjectsUsingBlock:^(id obj, NSUInteger idx, BOOL *stop) {
//            UIImageView *imageView = [[UIImageView alloc] initWithImage:[UIImage imageNamed:obj]];
            DLog(@"引导图obj====%@",obj);
            UIImageView *imageView = [[UIImageView alloc] init];
            [imageView sd_setImageWithURL:obj];
            imageView.frame = frame;
            imageView.tag = idx + 1;
            [tmpArray addObject:imageView];
            [self addSubview:imageView];
        }];
        self.backgroundViews = [[tmpArray reverseObjectEnumerator] allObjects];
    }else{
        UIView *view = [[UIView alloc] initWithFrame:frame];
        view.backgroundColor = [UIColor whiteColor];
        [self addSubview:view];
    }
}

- (void)reloadPages{
    self.pageControl.numberOfPages = [[self coverImageNames] count];
    self.pagingScrollView.contentSize = [self contentSizeOfScrollView];
    
    __block CGFloat x = 0;
    [[self scrollViewPages] enumerateObjectsUsingBlock:^(UIView *obj, NSUInteger idx, BOOL *stop) {
        obj.frame = CGRectOffset(obj.frame, x, 0);
        [self.pagingScrollView addSubview:obj];
        x += obj.frame.size.width;
    }];
    
    // fix ScrollView can not scrolling if it have only one page
    if (self.pagingScrollView.contentSize.width == self.pagingScrollView.frame.size.width) {
        self.pagingScrollView.contentSize = CGSizeMake(self.pagingScrollView.contentSize.width + 1, self.pagingScrollView.contentSize.height);
    }
    if (!self.enterButton) {
        self.enterButton = [UIButton buttonWithType:UIButtonTypeCustom];
        [self.enterButton setTitle:@"立即体验" forState:UIControlStateNormal];
        [self.enterButton setTitleColor:Number1691E3Color forState:UIControlStateNormal];
        self.enterButton.titleLabel.font = [UIFont systemFontOfSize:18];
        [self.enterButton sizeToFit];
        self.enterButton.size = (CGSize){self.enterButton.width+30,self.enterButton.height+10};
        self.enterButton.backgroundColor =NumberFFColor;
        self.enterButton.clipsToBounds = YES;
        self.enterButton.layer.cornerRadius = 8.0f;
        self.enterButton.x = self.pagingScrollView.contentSize.width - self.frame.size.width / 2 - self.enterButton.size.width / 2;
        self.enterButton.centerY = self.frame.size.height*80/100;
    }
    [self.enterButton addTarget:self action:@selector(enter:) forControlEvents:UIControlEventTouchUpInside];
    self.enterButton.alpha = 0;
    [self.pagingScrollView addSubview:self.enterButton];
    
    // fix enterButton can not presenting if ScrollView have only one page
    if (self.pageControl.numberOfPages == 1) {
        self.enterButton.alpha = 1;
        self.pageControl.alpha = 0;
    }
}

- (CGRect)frameOfPageControl{
    return CGRectMake(0, self.bounds.size.height - 30 - 10, self.bounds.size.width, 30);
}

- (CGRect)frameOfEnterButton{
    CGSize size = self.enterButton.bounds.size;
    if (CGSizeEqualToSize(size, CGSizeZero)) {
        size = CGSizeMake(self.frame.size.width * 0.6, self.frame.size.width * 0.6 * 0.4);
    }
    return (CGRect){self.pagingScrollView.contentSize.width - self.frame.size.width / 2 - size.width / 2, self.pageControl.frame.origin.y - size.height - AdaptAllScreen(40), size.width, size.height};
}

#pragma mark - UIScrollViewDelegate

- (void)scrollViewDidScroll:(UIScrollView *)scrollView{
    NSInteger index = scrollView.contentOffset.x / self.frame.size.width;
    CGFloat alpha = 1 - ((scrollView.contentOffset.x - index * self.frame.size.width) / self.frame.size.width);
    
    if ([self.backgroundViews count] > index) {
        UIView *v = [self.backgroundViews objectAtIndex:index];
        if (v) {
            [v setAlpha:alpha];
        }
    }
    
    self.pageControl.currentPage = scrollView.contentOffset.x / (scrollView.contentSize.width / [self numberOfPagesInPagingScrollView]);
    
    [self pagingScrollViewDidChangePages:scrollView];
    
//    DLog(@"%@",NSStringFromCGPoint(scrollView.contentOffset));
    //划出屏幕1/4时,移除引导页
    if (scrollView.contentOffset.x >= SCREEN_WIDTH*(self.scrollViewPages.count-1)+SCREEN_WIDTH/4) {
        if (![self hasNext:self.pageControl]) {
            [self enter:nil];
            [UIView animateWithDuration:0.5 animations:^{
                [scrollView setContentOffset:(CGPoint){self.frame.size.width*[self numberOfPagesInPagingScrollView],0} animated:NO];
            } completion:^(BOOL finished) {
                if (scrollView.contentOffset.x == self.frame.size.width*[self numberOfPagesInPagingScrollView]) {
                    [self enter:nil];
                }
            }];
        }
    }
}

#pragma mark - UIScrollView & UIPageControl DataSource
- (BOOL)hasNext:(UIPageControl*)pageControl
{
    return pageControl.numberOfPages > pageControl.currentPage + 1;
}

- (BOOL)isLast:(UIPageControl*)pageControl
{
    return pageControl.numberOfPages == pageControl.currentPage + 1;
}

- (NSInteger)numberOfPagesInPagingScrollView
{
    return [[self coverImageNames] count];
}

- (void)pagingScrollViewDidChangePages:(UIScrollView *)pagingScrollView
{
    if ([self isLast:self.pageControl]) {
        if (self.pageControl.alpha == 1) {
            self.enterButton.alpha = 0;
            
            [UIView animateWithDuration:0.4 animations:^{
                self.enterButton.alpha = 1;
                self.pageControl.alpha = 0;
            }];
        }
    } else {
        if (self.pageControl.alpha == 0) {
            [UIView animateWithDuration:0.4 animations:^{
                self.enterButton.alpha = 0;
                self.pageControl.alpha = 1;
            }];
        }
    }
}

- (BOOL)hasEnterButtonInView:(UIView*)page
{
    __block BOOL result = NO;
    [page.subviews enumerateObjectsUsingBlock:^(id obj, NSUInteger idx, BOOL *stop) {
        if (obj && obj == self.enterButton) {
            result = YES;
        }
    }];
    return result;
}

- (UIImageView*)scrollViewPage:(NSString*)imageName{
    UIImageView *imageView = [[UIImageView alloc] init];
    
    if ([imageName hasSuffix:@"gif"]) {
        NSRange range = [imageName rangeOfString:@"."];
        NSString *string = [imageName substringToIndex:range.location];
        NSArray *array = [UIImage testFunctionWithData:[[NSData alloc] initWithContentsOfFile:[[NSBundle mainBundle] pathForResource:string ofType:@"gif"]]];
        imageView.contentMode = UIViewContentModeScaleAspectFit;
        CGSize size = {[[UIScreen mainScreen] bounds].size.width, [[UIScreen mainScreen] bounds].size.height};
        imageView.frame = CGRectMake(imageView.frame.origin.x, imageView.frame.origin.y, size.width, size.height);
        [imageView setAnimationImages:array];
        [imageView setAnimationDuration:5];
        [imageView startAnimating];
    }else{
        imageView.image = [UIImage imageNamed:imageName];
//        imageView.contentMode = UIViewContentModeScaleAspectFit;
        CGSize size = {[[UIScreen mainScreen] bounds].size.width, [[UIScreen mainScreen] bounds].size.height};
        imageView.frame = CGRectMake(imageView.frame.origin.x, imageView.frame.origin.y, size.width, size.height);
    }
    
    return imageView;
}

- (NSArray*)scrollViewPages{
    if ([self numberOfPagesInPagingScrollView] == 0) {
        return nil;
    }
    
    if (_scrollViewPages) {
        return _scrollViewPages;
    }
    
    NSMutableArray *tmpArray = [NSMutableArray new];
    [self.coverImageNames enumerateObjectsUsingBlock:^(id obj, NSUInteger idx, BOOL *stop) {
        UIImageView *v = [self scrollViewPage:obj];
        v.userInteractionEnabled = YES;
        [tmpArray addObject:v];
        if (idx != [self numberOfPagesInPagingScrollView]-1) {
            UIButton *skip = [UIButton buttonWithType:UIButtonTypeCustom];
            [skip setTitle:@"跳过" forState:UIControlStateNormal];
            [skip setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
            
            skip.titleLabel.font = [UIFont systemFontOfSize:13];
            [skip setBackgroundColor:[UIColor colorWithHexStringAndAlpha:@"#e0e0e0"]];
            skip.size = (CGSize){AdaptAllScreen(55),AdaptAllScreen(25)};
//            skip.origin = (CGPoint){v.frame.size.width-skip.width-10,v.frame.size.height-skip.height-20};
            
            skip.origin = (CGPoint){v.frame.size.width-skip.width-10,40};

            [skip.layer setCornerRadius:3];
            [skip addTarget:self action:@selector(enter:) forControlEvents:UIControlEventTouchUpInside];
            [v addSubview:skip];
        }
    }];
    _scrollViewPages = tmpArray;
    
    return _scrollViewPages;
}

- (CGSize)contentSizeOfScrollView{
    UIView *view = [[self scrollViewPages] firstObject];
    return CGSizeMake(view.frame.size.width * self.scrollViewPages.count, view.frame.size.height);
}

#pragma mark - Action
- (void)enter:(id)object
{
    if (self.didSelectedEnter) {
        self.didSelectedEnter();
    }
}

@end
