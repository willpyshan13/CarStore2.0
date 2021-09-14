//
//  MallBannerView.m
//  InHoldingProject
//
//  Created by user on 2018/3/8.
//  Copyright © 2018年 wangshuping. All rights reserved.
//

#import "MallBannerView.h"

@interface MallBannerView ()<SDCycleScrollViewDelegate>

@property(nonatomic,strong)NSMutableArray *arrURL;

@end

@implementation MallBannerView

- (instancetype)initWithFrame:(CGRect)frame imageArray:(NSArray*)imageArr barnnerType:(BannerViewType)type{
    
    if (self = [super initWithFrame:frame]) {
        
        self.userInteractionEnabled = YES;
        _imageArray = imageArr;
    
        SDCycleScrollView *cycleScrollView2 =[SDCycleScrollView cycleScrollViewWithFrame:CGRectMake(0, 0,self.frame.size.width, self.frame.size.height) delegate:self placeholderImage:kImageNamed(@"")];
        
        cycleScrollView2.backgroundColor = [UIColor clearColor];
        cycleScrollView2.pageControlAliment = SDCycleScrollViewPageContolAlimentCenter;
        cycleScrollView2.currentPageDotColor=Number1691E3Color;
        cycleScrollView2.pageDotColor = [UIColor whiteColor];
        cycleScrollView2.autoScrollTimeInterval = 3.0;
        [self addSubview:cycleScrollView2];
        self.cycleScrollView = cycleScrollView2;
        /*    BannerViewTypeUrlPicture, //网络图片
         BannerViewTypeLocationPicture, //本地图片*/
        
        // --- 模拟加载延迟
        dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(0.3 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
            
            switch (type) {
                case BannerViewTypeUrlPicture:/** 网络图片 url string 数组 */
                {
                    cycleScrollView2.imageURLStringsGroup = self->_imageArray;
                    
                }
                    break;
                case BannerViewTypeLocationPicture:      /** 本地图片数组 */
                {
                    cycleScrollView2.localizationImageNamesGroup = self->_imageArray;
                }
                    break;
                    
                default:
                    break;
            }
            
        });
        
    }
    
    return self;
}


#pragma mark - SDCycleScrollViewDelegate

- (void)cycleScrollView:(SDCycleScrollView *)cycleScrollView didSelectItemAtIndex:(NSInteger)index
{
    NSLog(@"---点击了第%ld张图片", (long)index);
    
    /* 创建通知 */
    [[NSNotificationCenter defaultCenter] postNotificationName:@"ZAGCHOMEBANNERCLICKIMGNOTIFICATION" object:@{@"index" : @(index)}];
}

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/

@end
