//
//  StoreHeadView.m
//  InHoldingProject
//
//  Created by user on 2018/6/21.
//  Copyright © 2018年 wangshuping. All rights reserved.
//

#import "StoreHeadView.h"

@implementation StoreHeadView

- (instancetype)initWithFrame:(CGRect)frame{
    if (self = [super initWithFrame:frame])
    {
        if (self) {
            [self initUIView];
        }
        
    }
    return self;
}

- (void)initUIView{
    self.backgroundColor = NumberEDF5FBColor;
 
    UILabel *titleLabel =[[UILabel alloc]initWithFrame:CGRectMake(15, (56-25)/2, SCREEN_WIDTH-160, 25)];
    titleLabel.font = [UIFont boldSystemFontOfSize:16.0f];
    titleLabel.textColor = Number33Color;
    [self addSubview:titleLabel];
    self.titleLabel = titleLabel;
}
//标题名称
-(void)setTitleStr:(NSString *)titleStr
{
    if (titleStr) {
        _titleStr = titleStr;
        _titleLabel.text = _titleStr;
    }
}

@end
