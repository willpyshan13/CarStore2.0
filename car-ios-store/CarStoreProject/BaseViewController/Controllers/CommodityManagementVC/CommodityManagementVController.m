//
//  CommodityManagementVController.m
//  CarStoreProject
//
//  Created by apple on 2021/1/25.
//

#import "CommodityManagementVController.h"
#import "GoodsShelvesViewController.h"//上架商品
#import "SoldoutGoodsVController.h"//下架商品

@interface CommodityManagementVController ()<JohnTopTitleViewDelegate>
@property (nonatomic,strong)GoodsShelvesViewController *goodsShelvesVC;
@property (nonatomic,strong)SoldoutGoodsVController *soldoutGoodsVC;
@property (nonatomic,strong) JohnTopTitleView *topTitleView;
@property (nonatomic,strong)UIButton *publishCaseBtn;
@end

@implementation CommodityManagementVController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
//    self.title = @"案例详情";
    
    UILabel *label = [[UILabel alloc]init];
    label.frame = CGRectMake(0, 0, 0, 30);
    label.font = [UIFont systemFontOfSize:18];
    label.text = @"案例详情";
    label.textColor = Number090909Color;
    label.textAlignment = NSTextAlignmentCenter;
    self.navigationItem.titleView = label;
    
    [self.navigationController.navigationBar setTitleTextAttributes:
         @{NSFontAttributeName:[UIFont systemFontOfSize:18.0f],
           NSForegroundColorAttributeName:Number090909Color}];
    
    [self.view addSubview:self.topTitleView];
    [self.view addSubview:self.publishCaseBtn];
    self.view.backgroundColor =[UIColor whiteColor];

}

#pragma mark - JohnTopTitleViewDelegate

- (void)didSelectedPage:(NSInteger)page{
    self.topTitleView.frame = CGRectMake(0, NavigationBar_Height, SCREEN_WIDTH, SCREEN_HEIGHT-NavigationBar_Height-SafeAreaBottom_Height-BottomTab_Height);
    switch (page) {
        case 0:
        {
            self.publishCaseBtn.hidden = NO;
        }
            break;
        default:
        {
            self.publishCaseBtn.hidden = YES;
        }
            break;
    }
}


#pragma mark == 发布商品
-(void)publishBtnClick
{
    DLog(@"发布商品");
    SummaryH5ViewController *shopCenterVC =[[SummaryH5ViewController alloc]init];
    
    shopCenterVC.url = [NSString stringWithFormat:@"%@%@",HOSTH5_IP,putawayProductH5Api];;
    shopCenterVC.hidesBottomBarWhenPushed = YES;
    [self.navigationController pushViewController:shopCenterVC animated:YES];
}


#pragma mark - Getter
- (JohnTopTitleView *)topTitleView{
    if (!_topTitleView)
    {
        
        _topTitleView = [[JohnTopTitleView alloc]initWithFrame:CGRectMake(0,NavigationBar_Height, SCREEN_WIDTH, SCREEN_HEIGHT-NavigationBar_Height-SafeAreaBottom_Height-BottomTab_Height) segWidth:200];
        
        _topTitleView.lineWidth = 12;
        _topTitleView.lineHeight = 3;
        _topTitleView.titles = @[@"上架商品",@"下架商品"];
        [_topTitleView setupViewControllerWithFatherVC:self childVC:@[self.goodsShelvesVC,self.soldoutGoodsVC]];
        _topTitleView.delegete = self;
        _topTitleView.lineColor =Number1691E3Color;
        _topTitleView.selectedTextColor =Number090909Color;
         _topTitleView.textColor = Number090909Color;
        _topTitleView.textFont = FontSize(16);
        _topTitleView.selectedTextFont =[UIFont boldSystemFontOfSize:18];
        _topTitleView.backgroundColor =[UIColor whiteColor];
//        _topTitleView.canScroll = NO;
        
    }
    return _topTitleView;
}

- (GoodsShelvesViewController *)goodsShelvesVC{//案例
    if (!_goodsShelvesVC) {
        _goodsShelvesVC = [[GoodsShelvesViewController alloc]init];
        _goodsShelvesVC.DidScrollBlock = ^(CGFloat scrollY) {
        };
    }
    return _goodsShelvesVC;
}

- (SoldoutGoodsVController *)soldoutGoodsVC{//回答
    if (!_soldoutGoodsVC) {
        _soldoutGoodsVC = [[SoldoutGoodsVController alloc]init];
        _soldoutGoodsVC.DidScrollBlock = ^(CGFloat scrollY) {
        };
    }
    return _soldoutGoodsVC;
}

-(UIButton *)publishCaseBtn
{
    if (!_publishCaseBtn) {
        
        _publishCaseBtn =[UIButton buttonWithType:UIButtonTypeCustom];
        _publishCaseBtn.frame =CGRectMake(SCREEN_WIDTH-90, NavigationBar_Height, 80, 25);
        [_publishCaseBtn setTitle:@"发布商品" forState:UIControlStateNormal];
        [_publishCaseBtn setTitleColor:Number1691E3Color forState:UIControlStateNormal];
        _publishCaseBtn.titleLabel.font = FontSize(16);
        [_publishCaseBtn addTarget:self action:@selector(publishBtnClick) forControlEvents:UIControlEventTouchUpInside];
    }
    return _publishCaseBtn;
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
