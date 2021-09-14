//
//  IntroduceGuideView.h
//  DaLongInsurance
//
//  Created by 申巧 on 16/9/2.
//  Copyright © 2016年 申巧. All rights reserved.
//

#import <UIKit/UIKit.h>

typedef void (^DidSelectedEnter)();

@interface IntroduceGuideView : UIView

@property (nonatomic, strong) UIScrollView *pagingScrollView;
@property (nonatomic, strong) UIButton *enterButton;

@property (nonatomic, copy) DidSelectedEnter didSelectedEnter;

/**
 @[@"image1", @"image2"]
 */
@property (nonatomic, strong) NSArray *backgroundImageNames;

/**
 @[@"coverImage1", @"coverImage2"]
 */
@property (nonatomic, strong) NSArray *coverImageNames;


- (id)initWithCoverImageNames:(NSArray*)coverNames backgroundImageNames:(NSArray*)bgNames;


- (id)initWithCoverImageNames:(NSArray*)coverNames backgroundImageNames:(NSArray*)bgNames button:(UIButton*)button;


- (void)initSelfWithCoverNames:(NSArray *)coverNames backgroundImageNames:(NSArray *)bgNames;
@end
