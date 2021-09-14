//
//  ShareCarStoreVController.m
//  CarOwnerProject
//
//  Created by apple on 2021/3/9.
//

#import "ShareCarStoreVController.h"
#import "BaseWKWebView.h"

@interface ShareCarStoreVController ()<JSBridgeDelegate>
@property (nonatomic, strong) BaseWKWebView *wkWebView;

@end

@implementation ShareCarStoreVController
-(void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    self.navigationController.navigationBar.hidden = YES;
}
- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    // Do any additional setup after loading the view.
    self.automaticallyAdjustsScrollViewInsets = YES;
       
    _wkWebView = [[BaseWKWebView alloc] initWithFrame:(CGRect){0,0,SCREEN_WIDTH,SCREEN_HEIGHT-SafeAreaBottom_Height-BottomTab_Height} controller:self methods:nil];
    _wkWebView.bridgeDelegate = self;
    _wkWebView.cleanCache=YES;
    [self.view addSubview:_wkWebView];
    [_wkWebView loadUrl:[NSString stringWithFormat:@"%@%@",HOSTH5_IP,shareH5url]];
      
//    _wkWebView.backgroundColor =[UIColor redColor];
    [self updateLeftNavBarBtnItemWithImage:@"" target:self selector:@selector(leftNavBarBtnPressed:)];
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
    _wkWebView.wkWebView.height = SCREEN_HEIGHT-SafeAreaBottom_Height-BottomTab_Height;
}

-(void)dealloc
{
    DLog(@"清除缓存");
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
