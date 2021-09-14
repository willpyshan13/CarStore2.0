//
//  BaseTabViewController.m
//  CarStoreProject
//
//  Created by apple on 2021/1/25.
//

#import "BaseTabViewController.h"

#import "BaseNavViewController.h"
#import "DesignatedDriverVController.h"//代驾接单
#import "OnlineServicesVController.h"//线上服务
#import "FieldServiceViewController.h"//现场服务

#import "CommodityManagementVController.h"//商品管理
#import "MyViewController.h"//我的


@interface BaseTabViewController ()
@property(nonatomic,strong)NSMutableArray *items;
@property (nonatomic, strong) DesignatedDriverVController *designatedDriverVC;//代驾接单
@property (nonatomic, strong) OnlineServicesVController *onlineServicesVC;//线上服务
@property (nonatomic, strong) FieldServiceViewController *fieldServicesVC;//现场服务
@property (nonatomic, strong) CommodityManagementVController *CommodityManagementVC;//商品管理
@property (nonatomic, strong) MyViewController *myVC;//我的





/** 控制器数组  */
@property (nonatomic, strong) NSArray *ControllerMutableArry;


/** 标题数组*/
@property (nonatomic, strong) NSArray *TitleMutableArry;


/** item选中图标数组 */
@property (nonatomic, strong) NSArray *SelectImagerMutableArry;


/** item未选中图标数组 */
@property (nonatomic, strong) NSArray *NomalImageMutableArry;


/** 标签控制器子控制器数组 */
@property (nonatomic, strong) NSMutableArray *childControllerArray;


@end

@implementation BaseTabViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
//    [self setUpChildVC];
//    self.delegate = self;
    [self removeTabBarTopLine];
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(requestFindUserByPhoneNo) name:kUpdateUserInfo object:nil];
    [self addchildController];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    if ([self.view window] == nil) {
        self.view = nil;
    }
    
}

-(void)requestFindUserByPhoneNo
{
    DLog(@"切换身份333");
    UserModel *model =[UserInfo getUserInfo];
    DLog(@"切换身份userType222===%@",model.userType);
    [self addMorechildController];
}

#pragma mark -== 技师提价子控制器
// 添加标签子控制器
- (void)addchildController {

    for (int i = 0; i < self.ControllerMutableArry.count; i ++) {
        Class class = NSClassFromString(self.ControllerMutableArry[i]);
        UIViewController *VC = [[class alloc] init];
        UINavigationController *NaVC = [[UINavigationController alloc] initWithRootViewController:VC];
        NaVC.tabBarItem.title = self.TitleMutableArry[i];
        NaVC.tabBarItem.selectedImage = [UIImage imageNamed:self.SelectImagerMutableArry[i]];
        NaVC.tabBarItem.image = [UIImage imageNamed:self.NomalImageMutableArry[i]];
        
        // 存放子控制器
        [self.childControllerArray addObject:NaVC];
    }
    
    self.viewControllers = self.childControllerArray;
    self.tabBar.tintColor = Number1684E3Color;
}
// 在指定位置添加或删除一个标签子控制器
- (void)addMorechildController {
    
    if (4 == self.childControllerArray.count) {
        Class class = NSClassFromString(@"CommodityManagementVController");
        UIViewController *VC = [[class alloc] init];
        UINavigationController *NaVC = [[UINavigationController alloc] initWithRootViewController:VC];
        NaVC.tabBarItem.title = @"商品管理";
        NaVC.tabBarItem.selectedImage = [UIImage imageNamed:@"shangpinguanlixuanz"];
        NaVC.tabBarItem.image = [UIImage imageNamed:@"shangpinguanliweixuanz"];
        
        [self.childControllerArray insertObject:NaVC atIndex:3];
    }else {
        [self.childControllerArray removeObjectAtIndex:3];
    }
    self.viewControllers = self.childControllerArray;

}
#pragma mark- 懒加载//MyViewController
- (NSArray *)ControllerMutableArry {
    if (!_ControllerMutableArry)
    {
        UserModel *model =[UserInfo getUserInfo];
        if ([model.userType isEqualToString:@"2"]) {
            _ControllerMutableArry = @[@"DesignatedDriverVController",@"OnlineServicesVController",@"FieldServiceViewController",@"MyViewController"];
        }
        else if([model.userType isEqualToString:@"3"])
        {
            _ControllerMutableArry = @[@"DesignatedDriverVController",@"OnlineServicesVController",@"FieldServiceViewController",@"CommodityManagementVController",@"MyViewController"];

        }
    }
    
    return _ControllerMutableArry;
}

- (NSArray *)TitleMutableArry {
    if (!_TitleMutableArry)
    {
        UserModel *model =[UserInfo getUserInfo];
        if ([model.userType isEqualToString:@"2"]) {
            _TitleMutableArry = @[@"代驾接单",@"线上服务",@"现场服务",@"我的"];
        }else
        {
            _TitleMutableArry = @[@"代驾接单",@"线上服务",@"现场服务",@"商品管理",@"我的"];
        }
        
    }
    
    return _TitleMutableArry;
}

- (NSArray *)SelectImagerMutableArry {
    if (!_SelectImagerMutableArry) {
        UserModel *model =[UserInfo getUserInfo];
        if ([model.userType isEqualToString:@"2"])
        {
            _SelectImagerMutableArry = @[@"daijiajiedanxuanzhopng",@"xianshangfuwusxuanzhong(1)",@"xianshangfuxuanzhong",@"wodexuanzhong"];
        }else if ([model.userType isEqualToString:@"3"])
        {
            _SelectImagerMutableArry = @[@"daijiajiedanxuanzhopng",@"xianshangfuwusxuanzhong(1)",@"xianshangfuxuanzhong",@"shangpinguanlixuanz",@"wodexuanzhong"];
        }
        
    }
    
    return _SelectImagerMutableArry;
}
- (NSArray *)NomalImageMutableArry {
    if (!_NomalImageMutableArry)
    {
        UserModel *model =[UserInfo getUserInfo];
        if ([model.userType isEqualToString:@"2"]) {
            _NomalImageMutableArry = @[@"daijiajiedanweixuanzhopng",@"xianshangfuwuweisxuanzhong",@"weixiubaoyangweixuanzhong",@"wodeweixuanz"];
        }else if([model.userType isEqualToString:@"3"])
        {
            _NomalImageMutableArry = @[@"daijiajiedanweixuanzhopng",@"xianshangfuwuweisxuanzhong",@"weixiubaoyangweixuanzhong",@"shangpinguanliweixuanz",@"wodeweixuanz"];
        }
        
    }
    
    return _NomalImageMutableArry;
}

- (NSMutableArray *)childControllerArray {
    if (!_childControllerArray) {
        _childControllerArray = [[NSMutableArray alloc] init];
    }
    
    return _childControllerArray;
}































// 添加所有的子控制器
- (void)setUpChildVC
{
    //代驾
    DesignatedDriverVController *designatedDriverVC = [[DesignatedDriverVController alloc] init];
    [self setUpOneChildVC:designatedDriverVC image:[UIImage imageNamed:@"daijiajiedanweixuanzhopng"] selectedImage:kImageNamed(@"daijiajiedanxuanzhopng") title:@"代驾接单"];
    self.designatedDriverVC = designatedDriverVC;
    
    //线上服务
    OnlineServicesVController *onlineServicesVC = [[OnlineServicesVController alloc] init];
    [self setUpOneChildVC:onlineServicesVC image:[UIImage imageNamed:@"xianshangfuwuweisxuanzhong"] selectedImage:kImageNamed(@"xianshangfuwusxuanzhong(1)") title:@"线上服务"];
    self.onlineServicesVC = onlineServicesVC;
    
    
    //现场服务
    FieldServiceViewController *fieldServicesVC = [[FieldServiceViewController alloc] init];
    [self setUpOneChildVC:fieldServicesVC image:[UIImage imageNamed:@"weixiubaoyangweixuanzhong"] selectedImage:kImageNamed(@"xianshangfuxuanzhong") title:@"现场服务"];
    self.fieldServicesVC = fieldServicesVC;
    UserModel *userM =[UserInfo getUserInfo];
    DLog(@"userType====%@",userM.userType);
    if ([userM.userType isEqualToString:@"3"]) {
        //商品管理
        CommodityManagementVController *CommodityManagementVC = [[CommodityManagementVController alloc] init];
        [self setUpOneChildVC:CommodityManagementVC image:[UIImage imageNamed:@"shangpinguanliweixuanz"] selectedImage:kImageNamed(@"shangpinguanlixuanz") title:@"商品管理"];
        self.CommodityManagementVC = CommodityManagementVC;
    }
    //车友帮
    MyViewController *myVC = [[MyViewController alloc] init];
    [self setUpOneChildVC:myVC image:[UIImage imageNamed:@"wodeweixuanz"] selectedImage:kImageNamed(@"wodexuanzhong") title:@"我的"];
    self.myVC = myVC;
    
    if (@available(iOS 13.0, *)) {
        self.overrideUserInterfaceStyle = UIUserInterfaceStyleLight;
    } else {
        // Fallback on earlier versions
    }
}

//-(void)requestFindUserByPhoneNo
//{
//    DLog(@"切换身份333");
//    UserModel *model =[UserInfo getUserInfo];
//    DLog(@"切换身份userType222===%@",model.userType);
//    if ([model.userType isEqualToString:@"2"])
//    {
//        UITabBarController *tabBarController = (UITabBarController *)[UIApplication sharedApplication].delegate.window.rootViewController;
//
//        NSMutableArray *tabbarViewControllers = [NSMutableArray arrayWithArray:
//                                                 [tabBarController viewControllers]];
//
//        [tabbarViewControllers removeObjectAtIndex:3];
//        [tabBarController setViewControllers: tabbarViewControllers];
//    }else
//    {
//
//        NSMutableArray *ctrls = self.tabBarController.viewControllers.mutableCopy;
//
//        DLog(@"ctrls======%ld",ctrls.count);
//
//        CommodityManagementVController *second=[[CommodityManagementVController alloc] init];
//
//        BaseNavViewController *nav = [[BaseNavViewController alloc] initWithRootViewController:second];
//
//        nav.tabBarItem.selectedImage = [[UIImage imageNamed:@"shangpinguanlixuanz"]imageWithRenderingMode:UIImageRenderingModeAlwaysOriginal];
//
//        nav.tabBarItem.image = [[UIImage imageNamed:@"shangpinguanliweixuanz"]imageWithRenderingMode:UIImageRenderingModeAlwaysOriginal];
//
//        [ctrls insertObject:nav atIndex:3];
//
//        self.tabBarController.viewControllers = ctrls.copy;
//
//
////        UITabBarItem *item = self.tabBarController.tabBar.items[3];
////
////        item.title = @"商品管理";
//
////        //商品管理
////        CommodityManagementVController *CommodityManagementVC = [[CommodityManagementVController alloc] init];
////        [self setUpOneChildVC:CommodityManagementVC image:[UIImage imageNamed:@"shangpinguanliweixuanz"] selectedImage:kImageNamed(@"shangpinguanlixuanz") title:@"商品管理"];
////        self.CommodityManagementVC = CommodityManagementVC;
//    }
//
//}
    

// 封装创建控制器方法
- (void)setUpOneChildVC:(UIViewController *)vc image:(UIImage *)image selectedImage:(UIImage *)selectdeImage title:(NSString *)title
{
    vc.tabBarItem.title = title;
    vc.tabBarItem.image = image;
    vc.tabBarItem.selectedImage = selectdeImage;
    self.tabBar.tintColor = Number1684E3Color;
    [self.items addObject:vc.tabBarItem];
    //设置选中后标题颜色
    [[UITabBarItem appearance] setTitleTextAttributes:@{NSFontAttributeName : [UIFont fontWithName:@"HelveticaNeue-Bold" size:10.0F], NSForegroundColorAttributeName:Number1684E3Color} forState:UIControlStateSelected];
    
    //设置未选中标题颜色
    [[UITabBarItem appearance] setTitleTextAttributes:@{NSFontAttributeName : [UIFont systemFontOfSize:10.0F],  NSForegroundColorAttributeName:NumberB2BDCCColor} forState:UIControlStateNormal];
    BaseNavViewController *nav = [[BaseNavViewController alloc] initWithRootViewController:vc];
    [self addChildViewController:nav];
}


#pragma mark ==去掉tabbar底部线框
- (void)removeTabBarTopLine {

    CGRect rect= CGRectMake(0, 0, SCREEN_WIDTH, 0.5);
    UIGraphicsBeginImageContext(rect.size);
    CGContextRef context = UIGraphicsGetCurrentContext();
    CGContextSetFillColorWithColor(context, [UIColor colorWithHexStringAndAlpha:@"dcdcdc"].CGColor);
    CGContextFillRect(context, rect);
    UIImage *img = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    [self.tabBar setBackgroundImage:img];
    [self.tabBar setShadowImage:img];
    
    UIImage *tabbarImage = [self imageWithColor:NumberFFColor];
    self.tabBar.backgroundImage = tabbarImage;
    self.tabBar.shadowImage = tabbarImage;
    
}

- (UIImage *)imageWithColor:(UIColor *)color
{
    CGRect rect = CGRectMake(0, 0, 1, 1);
    UIGraphicsBeginImageContextWithOptions(rect.size, NO, 0);
    [color setFill];
    UIRectFill(rect);

    UIImage *image = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    return image;
}

#pragma mark - 1、懒加载控件、初始化控件------

- (NSMutableArray *)items {
    if (_items == nil) {
        _items = [NSMutableArray array];
    }
    return _items;
}

-(void)dealloc
{
    [[NSNotificationCenter defaultCenter] removeObserver:self name:kUpdateUserInfo object:nil];
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
