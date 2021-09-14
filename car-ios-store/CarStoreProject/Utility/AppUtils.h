//
//  AppUtils.h
//  MeiRenJi
//
//  Created by 申巧 on 14/11/10.
//  Copyright (c) 2014年 XiaoQiQi. All rights reserved.
//

#import <Foundation/Foundation.h>
//#import "Reachability.h"
//#import "Reachability.h"


@interface AppUtils : NSObject

//关闭键盘
+ (void)closeKeyboard;

//生成二维码
+ (UIImage *)createQRCodeWithString:(NSString *)string size:(CGFloat)size;
//返回View覆盖多余的tableview cell线条
+ (UIView *)tableViewsFooterView;
//获取没有文字的导航栏返回按钮，本页面设置,在下一页面生效
+ (UIBarButtonItem *)navigationBackButtonWithNoTitle;


//弹出操作错误信息提示框
+ (void)showErrorMessage:(NSString *)message;
//弹出操作成功信息提示框
+ (void)showSuccessMessage:(NSString *)message;
//弹出加载提示框
+ (void)showProgressMessage:(NSString *) message;
//弹出加载提示框,此时屏幕不响应用户点击事件
+ (void)showProgressMessageWithNotAllowTouch:(NSString *)message;
//弹出信息提示框
+ (void)showInfoMessage:(NSString *)message;
//取消弹出框
+ (void)dismissHUD;

//获取Ip
+ (NSString *)getIPAddress:(BOOL)preferIPv4;


+ (BOOL)isFirstLoad;

//app当前的版本号
+ (NSString *)currentVersion;
//是否是首次使用当前版本,如果是则显示引导页
+ (BOOL)isFirstLaunchCurrentVersion;


//删除数据库
+ (void)deleteDataBase:(void (^)())success;


- (BOOL)isBlankString:(NSString *)string;

+(BOOL)isBlankString:(NSString *)string;//是否为空

+(BOOL)isContainsEmoji:(NSString *)string;


@end
