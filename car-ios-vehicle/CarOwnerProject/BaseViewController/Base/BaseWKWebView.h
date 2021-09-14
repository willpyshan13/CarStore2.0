//
//  BaseWKWebView.h
//  DaLongInsurance
//
//  Created by 申巧 on 16/9/8.
//  Copyright © 2016年 申巧. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "WKDelegateController.h"

@protocol JSBridgeDelegate <NSObject>

@optional
//加载完成后的代理方法
- (void)webViewloadFinish:(id)obj;

//跳转至登录页面
//- (void)presentLoginController:(id)obj;
////跳转至绑定手机号页面
//- (void)pushBindMobileController:(id)obj;

@end

@interface BaseWKWebView : UIView

@property (nonatomic, strong) WKWebView *wkWebView;
//webview的宿主视图控制器
@property (nonatomic, strong) UIViewController *controller;
//当前加载的url
@property (nonatomic, copy) NSString *currentUrl;
//是否清除缓存  默认不清除
@property (nonatomic) BOOL cleanCache;
//是否可滚动  默认可滚动
@property (nonatomic) BOOL scrollEnable;

@property (nonatomic,assign) BOOL isDrap;//是否拖动返回

//与js交互的代理
@property (nonatomic, assign) id<JSBridgeDelegate>bridgeDelegate;

- (instancetype)initWithFrame:(CGRect)frame controller:(UIViewController *)control methods:(NSArray *)array;

- (void)loadUrl:(NSString *)url;

- (void)configNavigatioBackBtn;

//是否由native设置title，默认为NO
@property (nonatomic) BOOL titleSetted;

- (void)loadHtml:(NSString *)html;
@property (nonatomic, strong) UIButton *backBtn;

@property (nonatomic, assign) int type;


@property (nonatomic,copy)NSString *buddleIDS;

@property (nonatomic,copy)NSString *callback;// 分享链接
@property (nonatomic,copy)NSString *typeStr;//
@property (nonatomic,copy)NSString *currentURL;//当前网页地址

@end
