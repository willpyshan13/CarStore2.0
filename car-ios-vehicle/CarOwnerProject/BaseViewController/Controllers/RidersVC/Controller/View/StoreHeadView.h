//
//  StoreHeadView.h
//  InHoldingProject
//
//  Created by user on 2018/6/21.
//  Copyright © 2018年 wangshuping. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface StoreHeadView : UICollectionReusableView
@property (nonatomic, strong)UILabel *titleLabel;
@property (nonatomic, copy) NSString *titleStr;//标题名字
@property (nonatomic,strong)FSCustomButton*pinpanBtn;
@property (nonatomic,strong)FSCustomButton*chexingBtn;
@property (nonatomic,strong)FSCustomButton*xitongBtn;

@property (nonatomic, copy) void(^selectTypeListBlock)(id obj, NSInteger type);
@end
