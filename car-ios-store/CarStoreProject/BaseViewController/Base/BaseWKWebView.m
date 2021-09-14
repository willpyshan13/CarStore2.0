//
//  BaseWKWebView.m
//  DaLongInsurance
//
//  Created by 申巧 on 16/9/8.
//  Copyright © 2016年 申巧. All rights reserved.
//

#import "BaseWKWebView.h"
#import "BaseTabViewController.h"

static NSString *WECHATPAYPROTOCOL = @"weixin";

@interface BaseWKWebView ()<WKUIDelegate,WKNavigationDelegate,WKScriptMessageHandler,WKDelegate>{
    NSString *currentTitle;
}
@property (nonatomic, strong) WKUserContentController *userContentController;
@property (nonatomic, strong) UIProgressView *progressView;

@property (nonatomic, strong) NSArray *methodArray;

@end

@implementation BaseWKWebView{
    //是否手动设为 WKNavigationActionPolicyCancel
    BOOL loadState;
}

- (instancetype)initWithFrame:(CGRect)frame controller:(UIViewController *)control methods:(NSArray *)array{
    if (self = [super initWithFrame:frame]) {
        if (control) {
            _controller = control;
        }
        //finishAll返回原生界面 finishPage一级一级返回
        _methodArray = @[@"finishAll",@"finishPage",@"getToken",@"setCheckSts"];
        [self initUIView];
    }
    return self;
}

- (void)initUIView{
    
   
    //项目的 deployment target ,即app愿意支持的最低版本
//#if __IPHONE_OS_VERSION_MIN_REQUIRED <= __IPHONE_8_0
//#else
//#endif
    self.backgroundColor = [UIColor whiteColor];
    WKWebViewConfiguration *configuration = [[WKWebViewConfiguration alloc] init];
    _userContentController = [WKUserContentController new];
    configuration.preferences.javaScriptEnabled = YES;
    configuration.preferences.javaScriptCanOpenWindowsAutomatically = true;
    configuration.userContentController = _userContentController;
    if (IOS_9_OR_LATER) {
        //允许视频播放
        configuration.allowsAirPlayForMediaPlayback = YES;
    }
    // 允许在线播放
    configuration.allowsInlineMediaPlayback = YES;
    // 允许可以与网页交互，选择视图
    configuration.selectionGranularity = YES;
    
    _wkWebView = [[WKWebView alloc] initWithFrame:(CGRect){0,0,self.width,self.height} configuration:configuration];
    _scrollEnable = YES;
//    //造成无法释放内存.
    WKDelegateController *delegateController = [[WKDelegateController alloc] init];
    delegateController.delegate = self;
    for (NSString *method in _methodArray) {
        [_userContentController addScriptMessageHandler:delegateController name:method];
    }
    _wkWebView.navigationDelegate = self;
    _wkWebView.UIDelegate = self;
    [self addSubview:_wkWebView];
    [_wkWebView addObserver:self forKeyPath:@"estimatedProgress" options:NSKeyValueObservingOptionNew context:nil];
    [_wkWebView addObserver:self forKeyPath:@"title" options:NSKeyValueObservingOptionNew context:nil];
    
//    if (@available(iOS 11.0, *)) {
//        self.wkWebView.scrollView.contentInsetAdjustmentBehavior=UIScrollViewContentInsetAdjustmentNever;
//    } else {
//        // Fallback on earlier versions
//    }
    
    _progressView = [[UIProgressView alloc] initWithFrame:(CGRect){0,0,self.frame.size.width,4}];
    _progressView.trackTintColor = [UIColor clearColor];
    _progressView.progressTintColor = [UIColor colorWithHexStringAndAlpha:@"#58C515"];
    [self addSubview:_progressView];
    
    DLog(@"currentUrlcurrentUrl====%@",self.currentUrl);
    
}

- (void)observeValueForKeyPath:(NSString *)keyPath ofObject:(id)object change:(NSDictionary<NSString *,id> *)change context:(void *)context{
    if ([keyPath isEqualToString:@"estimatedProgress"]) {
        [_progressView setProgress:_wkWebView.estimatedProgress animated:YES];
        if (_wkWebView.estimatedProgress >= 1) {
            [_progressView setProgress:0 animated:NO];
        }
    }
    if ([keyPath isEqualToString:@"title"]) {
        NSString *title = _wkWebView.title;
        if (title.length) {
            if (!_titleSetted) {
                if (!currentTitle) {
                    currentTitle = title;
                }
                _controller.title = currentTitle;
                currentTitle = nil;
            }
        }
    }
}


#pragma mark - WKScriptMessageHandler
- (void)userContentController:(WKUserContentController *)userContentController didReceiveScriptMessage:(WKScriptMessage *)message{
    DLog(@"接收到的消息message====%@",message);
    DLog(@"==message.name==:%@",message.name);
    DLog(@"==message.body==:%@",message.body);
    
    [_methodArray enumerateObjectsUsingBlock:^(id  _Nonnull obj, NSUInteger idx, BOOL * _Nonnull stop) {
        NSString *name = obj;
        
        if ([message.name isEqualToString:@"getToken"])
        {
            [self getAlertCall];
        }
        
        if ([message.name isEqualToString:name]) {
            if ([message.name isEqualToString:@"finishAll"]) {
                [self postFinishAllMessage:message.body name:name];
            }
        }
        if ([message.name isEqualToString:name]) {
            if ([message.name isEqualToString:@"finishPage"]) {
                [self postFinishPageMessage:message.body name:name];
            }
        }
        if ([message.name isEqualToString:name]) {
            if ([message.name isEqualToString:@"setCheckSts"]) {
                [self postSetCheckStsMessage:message.body name:name];
            }
        }
    }];
}

#pragma mark 或者token
- (void)getAlertCall {
    NSString *jsStr = [NSString stringWithFormat:@"forIphoneToken('%@')",[UserInfo getUserInfo].token];
    DLog(@"getToken======jsStr===%@",jsStr);
    [self.wkWebView evaluateJavaScript:jsStr completionHandler:^(id _Nullable result, NSError * _Nullable error) {
        NSLog(@"结果结果%@----%@",result, error);
    }];
}

#pragma mark - private method
- (void)getToken:(id)body name:(NSString *)name{
    if (body && ![body isKindOfClass:[NSNull class]]) {
        NSDictionary *json = [[NSDictionary alloc] initWithDictionary:body];
        DLog(@"====getToken.json====:%@ ,%@",json,NSStringFromClass([json class]));
        if ([json isKindOfClass:[NSDictionary class]]) {
//            NSString *callback = @"getToken";
            NSString *callback = json[@"url"];
            DLog(@"callback====%@",callback);
            if (callback.length) {
                [self callbackJSMethodWithMethodName:callback];
            }
        }
    }
}

//回调js方法
- (void)callbackJSMethodWithMethodName:(NSString *)methodName{
    //参数只传token
    NSString *string = [NSString stringWithFormat:@"alertCallback('%@')",[UserInfo getUserInfo].token];
    NSLog(@"string2:%@",string);
    
    DLog(@"getToken 回调函数: %@",string);
    [self.wkWebView evaluateJavaScript:string completionHandler:^(id _Nullable respons, NSError * _Nullable error)
     {
         DLog(@"respons====%@error====%@",respons,error);
     }];
    
    
}


#pragma mark - 全部返回原生界面
- (void)postFinishAllMessage:(id)body name:(NSString *)name
{
    if (body && ![body isKindOfClass:[NSNull class]])
    {
        NSDictionary *json = body;
        DLog(@"====全部返回原生postMessage.json====:%@ ,%@",json,NSStringFromClass([json class]));
        if ([json isKindOfClass:[NSDictionary class]])
        {
            [_controller.navigationController popToRootViewControllerAnimated:YES];
        }
    }
}
#pragma mark == 一级一级的返回
- (void)postFinishPageMessage:(id)body name:(NSString *)name
{
    if (body && ![body isKindOfClass:[NSNull class]])
    {
        NSDictionary *json = body;
        DLog(@"====一级一级返回原生postMessage.json====:%@ ,%@",json,NSStringFromClass([json class]));
        if ([json isKindOfClass:[NSDictionary class]])
        {
            if ([_wkWebView canGoBack]) {
                [_wkWebView goBack];
            }else{
                [_controller.navigationController popViewControllerAnimated:YES];
                _controller.navigationController.interactivePopGestureRecognizer.enabled = YES;
                if (self.isDrap == YES) {
                    [_wkWebView loadRequest:[NSURLRequest requestWithURL:[NSURL URLWithString:@"about:blank"]]]; //关闭语音和视频
                }
                
            }
        }
    }
}
#pragma mark == 刷新进入主页
- (void)postSetCheckStsMessage:(id)body name:(NSString *)name
{
    if (body && ![body isKindOfClass:[NSNull class]])
    {
        NSDictionary *json = body;
        DLog(@"====进入主页====:%@ ,%@",json,NSStringFromClass([json class]));
        if ([json isKindOfClass:[NSDictionary class]])
        {
            BaseTabViewController *tabbarVC = [[BaseTabViewController alloc]init];
            [_controller.view.window setRootViewController:tabbarVC];
        }
    }
}


#pragma mark - WKNavigationDelegate
//是否允许加载网页
- (void)webView:(WKWebView *)webView decidePolicyForNavigationAction:(WKNavigationAction *)navigationAction decisionHandler:(void (^)(WKNavigationActionPolicy))decisionHandler{
    NSString *url = [[navigationAction.request URL] absoluteString];
    //解析url
    url = [url stringByRemovingPercentEncoding];
    NSString *hostname = [navigationAction.request URL].host.lowercaseString;
    DLog(@" 域名：%@; 地址：%@",hostname,url);
    self.currentURL = url;
    //截获电话请求
    if ([url hasPrefix:@"tel:"] || [url hasPrefix:@"sms:"]) {
        decisionHandler(WKNavigationActionPolicyCancel);
        
        NSRange range = [url rangeOfString:@":"];
        if (range.length) {
            NSString *phone = [url substringFromIndex:range.location+range.length];
            NSString *title = @"";
            NSString *string = @"";
            if ([url hasPrefix:@"tel"]) {
                title = @"是否拨打电话?";
                string = [NSString stringWithFormat:@"tel://%@",phone];
            }else {
                title = @"是否发送短信?";
                string = [NSString stringWithFormat:@"sms://%@",phone];
            }
            UIAlertController *alert = [UIAlertController alertControllerWithTitle:title message:phone preferredStyle:UIAlertControllerStyleAlert];
            UIAlertAction *action = [UIAlertAction actionWithTitle:@"确定" style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
                [[UIApplication sharedApplication] openURL:[NSURL URLWithString:string]];
            }];
            [alert addAction:action];
            UIAlertAction *cancelAction = [UIAlertAction actionWithTitle:@"取消" style:UIAlertActionStyleCancel handler:^(UIAlertAction * _Nonnull action) {
            }];
            [alert addAction:cancelAction];
            [_controller presentViewController:alert animated:YES completion:nil];
        }
        loadState = NO;
    }else{
        //请求协议头
        NSArray *urlComps = [url componentsSeparatedByString:@"://"];
        if (urlComps.count) {
            NSString *protocol = [urlComps firstObject];
            DLog(@" 协议头：%@",protocol);
            if ([protocol isEqualToString:WECHATPAYPROTOCOL]) {
                NSArray *array = [url componentsSeparatedByString:@"?"];
                if (array.count) {
                    NSString *para = [array lastObject];
                    para = [para stringByAddingPercentEscapesUsingEncoding:NSUnicodeStringEncoding];
                    NSString *str = [NSString stringWithFormat:@"%@?%@",[array firstObject],para];
                    //                    [[UIApplication sharedApplication] openURL:[NSURL URLWithString:str]];

                    [[UIApplication sharedApplication] openURL:[NSURL URLWithString:str] options:@{} completionHandler:^(BOOL success) {
                        
                    }];
                    decisionHandler(WKNavigationActionPolicyCancel);
                    loadState = YES;
                }
            }else{
                _currentUrl = url;
                decisionHandler(WKNavigationActionPolicyAllow);
                loadState = NO;
            }
        }
    }
    
    
    NSString* reqUrl = navigationAction.request.URL.absoluteString;
    if ([reqUrl hasPrefix:@"alipays://"] || [reqUrl hasPrefix:@"alipay://"]) {
    
//        [[UIApplication sharedApplication]openURL:navigationAction.request.URL];
        [[UIApplication sharedApplication]openURL:navigationAction.request.URL options:@{} completionHandler:^(BOOL success) {
            
        }];
        //bSucc是否成功调起支付宝
    }
    
    
    
    if ([reqUrl hasPrefix:@"weixin://"]) {

//        [[UIApplication sharedApplication]openURL:navigationAction.request.URL];
  
        [[UIApplication sharedApplication]openURL:navigationAction.request.URL options:@{} completionHandler:^(BOOL success) {
            
        }];
        //bSucc是否成功调起微信

        
    }
    
}
//开始加载时调用
- (void)webView:(WKWebView *)webView didStartProvisionalNavigation:(WKNavigation *)navigation{
}


//跳转失败时调用
- (void)webView:(WKWebView *)webView didFailNavigation:(WKNavigation *)navigation withError:(NSError *)error{
    [_progressView setProgress:0 animated:NO];
}
//内容加载失败时调用
- (void)webView:(WKWebView *)webView didFailProvisionalNavigation:(null_unspecified WKNavigation *)navigation withError:(NSError *)error{
    [_progressView setProgress:0 animated:NO];

    
}

//加载完成时调用
- (void)webView:(WKWebView *)webView didFinishNavigation:(WKNavigation *)navigation{
    if (_scrollEnable == NO) {
                
        
        [webView evaluateJavaScript:@"document.body.scrollHeight" completionHandler:^(id _Nullable result, NSError * _Nullable error) {
            //获取页面高度 , 并重置webview的frame
            double height = [result doubleValue];
            CGRect frame = webView.frame;
            frame.size.height = height;
            webView.frame = frame;
            self.height = height;
            if ([self.bridgeDelegate respondsToSelector:@selector(webViewloadFinish:)]) {
                [self.bridgeDelegate webViewloadFinish:@(height)];
            }
        }];
    }else{
        if ([self.bridgeDelegate respondsToSelector:@selector(webViewloadFinish:)]) {
            [self.bridgeDelegate webViewloadFinish:nil];
        }
    }
    
    
}

#pragma mark - WKUIDelegate
//提示框
- (void)webView:(WKWebView *)webView runJavaScriptAlertPanelWithMessage:(NSString *)message initiatedByFrame:(WKFrameInfo *)frame completionHandler:(void (^)(void))completionHandler{
    UIAlertController *alert = [UIAlertController alertControllerWithTitle:@"" message:message preferredStyle:UIAlertControllerStyleAlert];
    UIAlertAction *action = [UIAlertAction actionWithTitle:@"确定" style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
        completionHandler();
    }];
    [alert addAction:action];
    [_controller presentViewController:alert animated:YES completion:nil];
}
//取消，确定选择框
- (void)webView:(WKWebView *)webView runJavaScriptConfirmPanelWithMessage:(NSString *)message initiatedByFrame:(WKFrameInfo *)frame completionHandler:(void (^)(BOOL result))completionHandler{
    UIAlertController *alert = [UIAlertController alertControllerWithTitle:@"" message:message preferredStyle:UIAlertControllerStyleAlert];
    UIAlertAction *action = [UIAlertAction actionWithTitle:@"确定" style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
        completionHandler(YES);
    }];
    [alert addAction:action];
    UIAlertAction *cancelAction = [UIAlertAction actionWithTitle:@"取消" style:UIAlertActionStyleCancel handler:^(UIAlertAction * _Nonnull action) {
        completionHandler(NO);
    }];
    [alert addAction:cancelAction];
    [_controller presentViewController:alert animated:YES completion:nil];
}
// 交互，可输入的文本
-(void)webView:(WKWebView *)webView runJavaScriptTextInputPanelWithPrompt:(NSString *)prompt defaultText:(NSString *)defaultText initiatedByFrame:(WKFrameInfo *)frame completionHandler:(void (^)(NSString * _Nullable))completionHandler{
    UIAlertController *alert = [UIAlertController alertControllerWithTitle:@"t" message:prompt preferredStyle:UIAlertControllerStyleAlert];
    [alert addTextFieldWithConfigurationHandler:^(UITextField * _Nonnull textField) {
        textField.textColor = [UIColor redColor];
    }];
    [alert addAction:[UIAlertAction actionWithTitle:@"确定" style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
        completionHandler([[alert.textFields lastObject] text]);
    }]];
    [_controller presentViewController:alert animated:YES completion:NULL];
}


- (void)clearWebCache{
    if (IOS_9_OR_LATER) {
        NSSet *websiteDataTypes = [WKWebsiteDataStore allWebsiteDataTypes];
        NSDate *dateFrom = [NSDate dateWithTimeIntervalSince1970:0];
        [[WKWebsiteDataStore defaultDataStore] removeDataOfTypes:websiteDataTypes
                                                   modifiedSince:dateFrom completionHandler:^{}];
    }else{
        NSString *libraryDir = NSSearchPathForDirectoriesInDomains(NSLibraryDirectory,
                                                                   NSUserDomainMask, YES)[0];
        
        NSString *bundleId  =  [[[NSBundle mainBundle] infoDictionary] objectForKey:@"CFBundleIdentifier"];
        NSString *webkitFolderInLib = [NSString stringWithFormat:@"%@/WebKit",libraryDir];
        NSString *webKitFolderInCaches = [NSString stringWithFormat:@"%@/Caches/%@/WebKit",libraryDir,bundleId];
        NSString *webKitFolderInCachesfs = [NSString stringWithFormat:@"%@/Caches/%@/fsCachedData",libraryDir,bundleId];
        
        NSError *error;
        /* iOS8.0 WebView Cache的存放路径 */
        [[NSFileManager defaultManager] removeItemAtPath:webkitFolderInLib error:nil];
        [[NSFileManager defaultManager] removeItemAtPath:webKitFolderInCaches error:&error];
        
        /* iOS7.0 WebView Cache的存放路径 */
        [[NSFileManager defaultManager] removeItemAtPath:webKitFolderInCachesfs error:&error];
    }
    [[NSURLCache sharedURLCache] removeAllCachedResponses];
    NSURLCache * cache = [NSURLCache sharedURLCache];
    [cache removeAllCachedResponses];
    [cache setDiskCapacity:0];
    [cache setMemoryCapacity:0];
}

- (void)loadHtml:(NSString *)html{
    [_wkWebView loadHTMLString:html baseURL:nil];
}

- (void)loadUrl:(NSString *)url{
//    [[WebLoadFailedView sharedWebLoadFailedView] dismiss];
    
    if (self.cleanCache) {
        [self clearWebCache];
    }
    
    if (url.length) {
        _currentUrl = url;
        DLog(@"12321321currentUrl===%@",self.currentUrl);
        [self loadUrlRequest:_currentUrl];
    }else{
        //比较网络数据是否有变化,没有变化则使用缓存,否则重新请求
        [_wkWebView reloadFromOrigin];
    }
    
}

- (void)loadUrlRequest:(NSString *)url{
    if (url.length) {
        if ([NSString isChinese:url]) {
            url = [url stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding];
        }
        NSURLRequest *request = [[NSURLRequest alloc] initWithURL:[NSURL URLWithString:url]];
        [_wkWebView loadRequest:request];
    }
}

- (void)setController:(UIViewController *)controller{
    if (controller) {
        _controller = controller;
//        [self configNavigatioBackBtn];
    }
}

- (void)configNavigatioBackBtn{
    UIView *view = [[UIView alloc] initWithFrame:(CGRect){0,0,80,44}];
    
    UIButton *btn = [[UIButton alloc] initWithFrame:(CGRect){0,0,44,44}];
    [btn setImage:[UIImage imageNamed:@"back_bg"] forState:UIControlStateNormal];
    [btn setImageEdgeInsets:UIEdgeInsetsMake(0, 0, 0, 22)];
    [btn addTarget:self action:@selector(backPressed:) forControlEvents:UIControlEventTouchUpInside];
    self.backBtn = btn;
    [view addSubview:btn];

    UITapGestureRecognizer *tap = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(closeHtmlPage:)];
    tap.numberOfTapsRequired = 2;
    [btn addGestureRecognizer:tap];
    
    _controller.navigationItem.leftBarButtonItem = [[UIBarButtonItem alloc] initWithCustomView:view];

}

- (void)backPressed:(UIButton *)sender{
    if ([_wkWebView canGoBack]) {
        [_wkWebView goBack];
//        self.typeStr = @"";
    }else{
        [_controller.navigationController popViewControllerAnimated:YES];
        _controller.navigationController.interactivePopGestureRecognizer.enabled = YES;
        if (self.isDrap == YES) {
            [_wkWebView loadRequest:[NSURLRequest requestWithURL:[NSURL URLWithString:@"about:blank"]]]; //关闭语音和视频
        }
        
//        [self pausePlay];
    }
}

- (void)closeHtmlPage:(UIButton *)sender{
    [_controller.navigationController popViewControllerAnimated:YES];
}

- (void)setScrollEnable:(BOOL)scrollEnable{
    _scrollEnable = scrollEnable;
    self.wkWebView.scrollView.scrollEnabled = _scrollEnable;
}



- (void)dealloc{
    [self removeObserver:self forKeyPath:@"estimatedProgress"];
    [self removeObserver:self forKeyPath:@"title"];
    for (NSString *name in _methodArray) {
        //前面增加过得方法一定要remove掉
        [[_wkWebView configuration].userContentController removeScriptMessageHandlerForName:name];
    }
}







@end
