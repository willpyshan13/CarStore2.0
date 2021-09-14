//
//  RidersViewController.m
//  CarOwnerProject
//
//  Created by apple on 2021/1/25.
//

#import "RidersViewController.h"
#import "PaidConsultingVController.h"//付费咨询
#import "TechniciansCaseVController.h"//技师案例
#import "MaintenanceViewController.h"//养护信息


@interface RidersViewController ()<JohnTopTitleViewDelegate>
@property (nonatomic,strong)PaidConsultingVController *paidConsultingVC;//付费咨询
@property (nonatomic,strong)TechniciansCaseVController *techniciansCaseVC;//技师案例
@property (nonatomic,strong)MaintenanceViewController *maintenanceVC;//养护信息

@property (nonatomic,strong) JohnTopTitleView *topTitleView;
@end

@implementation RidersViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    self.navigationItem.title = @"车友邦";
    [self.navigationController.navigationBar setTitleTextAttributes:
         @{NSFontAttributeName:[UIFont systemFontOfSize:18.0f],
           NSForegroundColorAttributeName:Number090909Color}];
    
    [self.view addSubview:self.topTitleView];
}

#pragma mark - JohnTopTitleViewDelegate

- (void)didSelectedPage:(NSInteger)page{
    self.topTitleView.frame = CGRectMake(0, NavigationBar_Height, SCREEN_WIDTH, SCREEN_HEIGHT-NavigationBar_Height-SafeAreaBottom_Height);
    switch (page) {
        case 0:
        {
//            [self.offlineVC.onlineTable setContentOffset:CGPointMake(0, 0) animated:NO];
            
        }
            break;
        default:
        {
//            [self.streamingVC.streamingTable setContentOffset:CGPointMake(0, 0) animated:NO];
        }
            break;
    }
}

#pragma mark - Getter
- (JohnTopTitleView *)topTitleView{
    if (!_topTitleView)
    {
        
        _topTitleView = [[JohnTopTitleView alloc]initWithFrame:CGRectMake(0,NavigationBar_Height, SCREEN_WIDTH, self.view.frame.size.height-NavigationBar_Height)];
        _topTitleView.backgroundColor =[UIColor whiteColor];
        _topTitleView.lineWidth = 12;
        _topTitleView.lineHeight = 3;
        _topTitleView.titles = @[@"技师咨询",@"爱车讲堂",@"技师案例"];
        [_topTitleView setupViewControllerWithFatherVC:self childVC:@[self.paidConsultingVC,self.maintenanceVC,self.techniciansCaseVC]];
        _topTitleView.delegete = self;
        _topTitleView.lineColor =Number1691E3Color;
        _topTitleView.selectedTextColor =Number090909Color;
         _topTitleView.textColor = Number090909Color;
        _topTitleView.textFont = FontSize(14);
        _topTitleView.selectedTextFont =[UIFont boldSystemFontOfSize:14];
//        _topTitleView.canScroll = NO;
        
    }
    return _topTitleView;
}

- (PaidConsultingVController *)paidConsultingVC{//付费咨询
    if (!_paidConsultingVC) {
        _paidConsultingVC = [[PaidConsultingVController alloc]init];
        _paidConsultingVC.DidScrollBlock = ^(CGFloat scrollY) {
        };
    }
    return _paidConsultingVC;
}

- (TechniciansCaseVController *)techniciansCaseVC{//技师案例
    if (!_techniciansCaseVC) {
        _techniciansCaseVC = [[TechniciansCaseVController alloc]init];
        _techniciansCaseVC.DidScrollBlock = ^(CGFloat scrollY) {
        };
    }
    return _techniciansCaseVC;
}

- (MaintenanceViewController *)maintenanceVC{//技师案例
    if (!_maintenanceVC) {
        _maintenanceVC = [[MaintenanceViewController alloc]init];
        _maintenanceVC.DidScrollBlock = ^(CGFloat scrollY) {
        };
    }
    return _maintenanceVC;
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
