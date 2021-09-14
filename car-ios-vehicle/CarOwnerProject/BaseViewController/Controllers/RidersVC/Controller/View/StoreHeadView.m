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
 
//    UILabel *titleLabel =[[UILabel alloc]initWithFrame:CGRectMake(15, (56-25)/2, SCREEN_WIDTH-160, 25)];
//    titleLabel.font = [UIFont boldSystemFontOfSize:16.0f];
//    titleLabel.textColor = Number333333Color;
//    [self addSubview:titleLabel];
//    self.titleLabel = titleLabel;
    
    
    FSCustomButton*pinpanBtn =[FSCustomButton buttonWithType:UIButtonTypeCustom];
    pinpanBtn.frame = CGRectMake(15, 10, (SCREEN_WIDTH-15*4)/3, 32);
    [pinpanBtn setTitle:@"全部品牌 " forState:UIControlStateNormal];
    pinpanBtn.buttonImagePosition =FSCustomButtonImagePositionRight;
    [pinpanBtn setTitleColor:Number090909Color forState:UIControlStateNormal];
    [pinpanBtn setImage:kImageNamed(@"js_arrowdown_icon") forState:UIControlStateNormal];
    
    [pinpanBtn addTarget:self action:@selector(pinpaiBtnClick:) forControlEvents:UIControlEventTouchUpInside];
    pinpanBtn.clipsToBounds = YES;
    pinpanBtn.layer.borderWidth = 1.0f;
    pinpanBtn.layer.borderColor = NumberF1F1F1Color.CGColor;
    pinpanBtn.layer.cornerRadius = 2.0f;
    pinpanBtn.titleLabel.font = FontSize(14);
    pinpanBtn.tag = 66;
    self.pinpanBtn = pinpanBtn;
    [self addSubview:pinpanBtn];
    
    
    FSCustomButton *chexingBtn =[FSCustomButton buttonWithType:UIButtonTypeCustom];
    chexingBtn.frame = CGRectMake(CGRectGetMaxX(pinpanBtn.frame)+15, 10, (SCREEN_WIDTH-15*4)/3, 32);
    [chexingBtn setTitle:@"全部车型 " forState:UIControlStateNormal];
    [chexingBtn setImage:kImageNamed(@"js_arrowdown_icon") forState:UIControlStateNormal];
    chexingBtn.buttonImagePosition =FSCustomButtonImagePositionRight;
    chexingBtn.tag = 67;
    [chexingBtn addTarget:self action:@selector(pinpaiBtnClick:) forControlEvents:UIControlEventTouchUpInside];

    [chexingBtn setTitleColor:Number090909Color forState:UIControlStateNormal];
    chexingBtn.clipsToBounds = YES;
    chexingBtn.layer.borderWidth = 1.0f;
    chexingBtn.layer.borderColor = NumberF1F1F1Color.CGColor;
    chexingBtn.layer.cornerRadius = 2.0f;
    chexingBtn.titleLabel.font = FontSize(14);

    self.chexingBtn = chexingBtn;
    [self addSubview:chexingBtn];
    
    FSCustomButton *xitongBtn =[FSCustomButton buttonWithType:UIButtonTypeCustom];
    xitongBtn.frame = CGRectMake(CGRectGetMaxX(chexingBtn.frame)+15, 10, (SCREEN_WIDTH-15*4)/3, 32);
    [xitongBtn setTitle:@"全部系统 " forState:UIControlStateNormal];
    [xitongBtn setTitleColor:Number090909Color forState:UIControlStateNormal];
    [xitongBtn setImage:kImageNamed(@"js_arrowdown_icon") forState:UIControlStateNormal];
    xitongBtn.buttonImagePosition =FSCustomButtonImagePositionRight;
    xitongBtn.tag = 68;
    [xitongBtn addTarget:self action:@selector(pinpaiBtnClick:) forControlEvents:UIControlEventTouchUpInside];
    xitongBtn.clipsToBounds = YES;
    xitongBtn.layer.borderWidth = 1.0f;
    xitongBtn.layer.borderColor = NumberF1F1F1Color.CGColor;
    xitongBtn.layer.cornerRadius = 2.0f;
    xitongBtn.titleLabel.font = FontSize(14);

    self.xitongBtn = xitongBtn;
    [self addSubview:xitongBtn];
}
//标题名称
-(void)setTitleStr:(NSString *)titleStr
{
    if (titleStr) {
        _titleStr = titleStr;
        _titleLabel.text = _titleStr;
    }
}
-(void)pinpaiBtnClick:(FSCustomButton *)btn
{
    
//    UIButton *selbtn = btn;
     if (self.selectTypeListBlock) {
         self.selectTypeListBlock(btn, btn.tag);
     }
    
//    if (btn.tag==66)
//    {
//        DLog(@"品牌");
//        [AppUtils showInfoMessage:@"该功能暂未开通，敬请期待"];
//    }
//    else if (btn.tag==67)
//    {
//        DLog(@"车型");
//        [AppUtils showInfoMessage:@"该功能暂未开通，敬请期待"];
//
//    }
//    else if (btn.tag==68)
//    {
//        DLog(@"系统");
//        [AppUtils showInfoMessage:@"该功能暂未开通，敬请期待"];
//    }
}

@end
