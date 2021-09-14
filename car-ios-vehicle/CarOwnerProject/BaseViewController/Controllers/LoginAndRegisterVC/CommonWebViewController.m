//
//  CommonWebViewController.m
//  CarStoreProject
//
//  Created by Qin on 2021/2/15.
//

#import "CommonWebViewController.h"

#import <WebKit/WebKit.h>

@interface CommonWebViewController ()<WKNavigationDelegate>

@property(strong,nonatomic)WKWebView *webView;

@end

@implementation CommonWebViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    self.title=self.titleString;
    [self createWebView];
    [self updateLeftNavBarBtnItemWithImage:kImageNamed(@"back_bg") target:self selector:@selector(leftNavBarBtnPressed:)];    
}

-(void)createWebView{
    // WKWebView初始化
    self.webView = [[WKWebView alloc] initWithFrame:CGRectMake(0,NavigationBar_Height, SCREEN_WIDTH, SCREEN_HEIGHT-NavigationBar_Height)];
    self.webView.opaque=NO;
    self.webView.navigationDelegate=self;
//    [self.webView sizeToFit];
    [self.view addSubview:self.webView];
    
    if (self.url!=nil) {
        [self.webView loadRequest:[NSURLRequest requestWithURL:self.url]];
    }else{
        [self.webView loadRequest:[NSURLRequest requestWithURL:[NSURL URLWithString:@""]]];
    }
    
    
}

// 页面加载完成之后调用
- (void)webView:(WKWebView *)webView didFinishNavigation:(null_unspecified WKNavigation *)navigation {
    NSLog(@"网页导航加载完毕");
    //    [UIApplication sharedApplication].networkActivityIndicatorVisible = NO;
    
    
}

//当web视图加载内容时发生错误时调用
- (void)webView:(WKWebView *)webView didFailProvisionalNavigation:(null_unspecified WKNavigation *)navigation withError:(NSError *)error {
    NSLog(@"网页加载发生错误:%@",[error description]);
    //    [UIApplication sharedApplication].networkActivityIndicatorVisible = NO;
    
}

//在导航过程中发生错误时调用
- (void)webView:(WKWebView *)webView didFailNavigation:(null_unspecified WKNavigation *)navigation withError:(NSError *)error {
    NSLog(@"导航过程中发生错误,错误原因:%@",[error description]);
    
}

// https 支持
- (void)webView:(WKWebView *)webView didReceiveAuthenticationChallenge:(NSURLAuthenticationChallenge *)challenge completionHandler:(void (^)(NSURLSessionAuthChallengeDisposition disposition, NSURLCredential * _Nullable credential))completionHandler{
    if ([challenge.protectionSpace.authenticationMethod isEqualToString:NSURLAuthenticationMethodServerTrust]) {
        NSURLCredential *card = [[NSURLCredential alloc]initWithTrust:challenge.protectionSpace.serverTrust];
        completionHandler(NSURLSessionAuthChallengeUseCredential,card);
    }
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
