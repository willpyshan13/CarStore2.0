//
//  MallBannerView.h
//  InHoldingProject
//
//  Created by user on 2018/3/8.
//  Copyright © 2018年 wangshuping. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "SDCycleScrollView.h"
//轮播图有无图片
typedef NS_ENUM(NSUInteger,BannerViewType)
{
    BannerViewTypeUrlPicture, //网络图片
    BannerViewTypeLocationPicture, //本地图片
    
};

typedef void (^myblock)(NSMutableArray *netArr);

@interface MallBannerView : UIView<SDCycleScrollViewDelegate>

- (instancetype)initWithFrame:(CGRect)frame imageArray:(NSArray*)imageArr barnnerType:(BannerViewType)type;

@property (nonatomic,strong)NSArray *imageArray;

@property (nonatomic) SDCycleScrollView *cycleScrollView;

//- (instancetype)initWithFrame:(CGRect)frame imageArray:(NSArray*)imageArr;

@property (nonatomic,assign)BOOL isMain;

@property(nonatomic,copy)myblock block;
@end
