//
//  FieldServiceViewController.m
//  CarStoreProject
//
//  Created by Qin on 2021/2/15.
//

#import "FieldServiceViewController.h"
//抢单
#import "QDViewController.h"
//我的抢单
#import "MyQDViewController.h"
//现场支持下单
#import "XCXDViewController.h"

@interface FieldServiceViewController ()<JohnTopTitleViewDelegate>
@property (nonatomic,strong)QDViewController *qdVC;//抢单
@property (nonatomic,strong)MyQDViewController *wdqdVC;//我的抢单
@property (nonatomic,strong)XCXDViewController *xczcVC;//现场支持下单
@property (nonatomic,strong) JohnTopTitleView *topTitleView;
@end

@implementation FieldServiceViewController

-(void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    self.navigationController.navigationBar.hidden = NO;
    self.navigationController.navigationBar.barTintColor = NumberEDF5FBColor;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    UILabel *label = [[UILabel alloc]init];
    label.frame = CGRectMake(0, 0, 0, 30);
    label.font = [UIFont systemFontOfSize:18];
    label.text = @"现场服务";
    label.textColor = Number090909Color;
    label.textAlignment = NSTextAlignmentCenter;
    self.navigationItem.titleView = label;
    
    [self.navigationController.navigationBar setTitleTextAttributes:
         @{NSFontAttributeName:[UIFont systemFontOfSize:18.0f],
           NSForegroundColorAttributeName:Number090909Color}];
    self.navigationController.navigationBar.barTintColor = NumberEDF5FBColor;

    self.view.backgroundColor =NumberEDF5FBColor;
    
    self.navigationController.navigationBar.barTintColor = NumberEDF5FBColor;
    [self.view addSubview:self.topTitleView];
}

#pragma mark - JohnTopTitleViewDelegate

- (void)didSelectedPage:(NSInteger)page{
    self.topTitleView.frame = CGRectMake(0, NavigationBar_Height, SCREEN_WIDTH, SCREEN_HEIGHT-NavigationBar_Height-SafeAreaBottom_Height);
    switch (page) {
        case 0:
        {
            
        }
            break;
        default:
        {
            
        }
            break;
    }
}

#pragma mark - Getter
- (JohnTopTitleView *)topTitleView{
    if (!_topTitleView)
    {
        
        _topTitleView = [[JohnTopTitleView alloc]initWithFrame:CGRectMake(0,NavigationBar_Height, SCREEN_WIDTH, SCREEN_HEIGHT-NavigationBar_Height) segWidth:SCREEN_WIDTH];
        _topTitleView.backgroundColor =NumberEDF5FBColor;
        _topTitleView.lineWidth = 12;
        _topTitleView.lineHeight = 3;
        _topTitleView.titles = @[@"抢单",@"我的抢单",@"现场支持下单"];
        [_topTitleView setupViewControllerWithFatherVC:self childVC:@[self.qdVC,self.wdqdVC,self.xczcVC]];
        _topTitleView.delegete = self;
        _topTitleView.lineColor =Number1691E3Color;
        _topTitleView.selectedTextColor =Number090909Color;
         _topTitleView.textColor = Number090909Color;
        _topTitleView.textFont = FontSize(16);
        _topTitleView.selectedTextFont =[UIFont boldSystemFontOfSize:18];
//        _topTitleView.canScroll = NO;
        
    }
    return _topTitleView;
}
- (QDViewController *)qdVC{//抢单
    if (!_qdVC) {
        _qdVC = [[QDViewController alloc]init];
        _qdVC.DidScrollBlock = ^(CGFloat scrollY) {
        };
    }
    return _qdVC;
}
- (MyQDViewController *)wdqdVC{//我的抢单
    if (!_wdqdVC) {
        _wdqdVC = [[MyQDViewController alloc]init];
        _wdqdVC.DidScrollBlock = ^(CGFloat scrollY) {
        };
    }
    return _wdqdVC;
}
- (XCXDViewController *)xczcVC{//现场支持下单
    if (!_xczcVC) {
        _xczcVC = [[XCXDViewController alloc]init];
        _xczcVC.DidScrollBlock = ^(CGFloat scrollY) {
        };
    }
    return _xczcVC;
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
