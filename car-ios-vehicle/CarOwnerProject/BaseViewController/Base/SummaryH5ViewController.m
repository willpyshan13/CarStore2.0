//
//  SummaryH5ViewController.m
//  InHoldingProject
//
//  Created by user on 2018/5/3.
//  Copyright © 2018年 wangshuping. All rights reserved.
//

#import "SummaryH5ViewController.h"
#import "BaseWKWebView.h"


@interface SummaryH5ViewController ()<JSBridgeDelegate>
@property (nonatomic, strong) BaseWKWebView *wkWebView;
@end

@implementation SummaryH5ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    self.automaticallyAdjustsScrollViewInsets = YES;
        _wkWebView = [[BaseWKWebView alloc] initWithFrame:(CGRect){0,0,SCREEN_WIDTH,SCREEN_HEIGHT} controller:self methods:nil];
    _wkWebView.bridgeDelegate = self;
    _wkWebView.cleanCache=YES;
    [self.view addSubview:_wkWebView];
    [_wkWebView loadUrl:_url];
      
    [self updateLeftNavBarBtnItemWithImage:@"" target:self selector:@selector(leftNavBarBtnPressed:)];
    
}
-(void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    self.navigationController.navigationBar.hidden = YES;
}
-(void)viewWillDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
    self.navigationController.navigationBar.hidden = NO;
}

#pragma mark ==关闭
-(void)closeBtnClick:(UIBarButtonItem *)sender
{
    [self.navigationController popToRootViewControllerAnimated:YES];
}

- (void)webViewloadFinish:(id)obj{
//    [_wkWebView configNavigatioBackBtn];
    _wkWebView.wkWebView.height = SCREEN_HEIGHT;
}

-(void)dealloc
{
    DLog(@"清除缓存");
}
- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
