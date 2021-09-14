//
//  BaseTabViewController.m
//  CarOwnerProject
//
//  Created by apple on 2021/1/25.
//

#import "BaseTabViewController.h"
#import "DesignatedDriverVController.h"//代驾
#import "MaintenanceVController.h"//维修保养
#import "RidersViewController.h"//车友帮
#import "MyViewController.h"//我的
#import "BaseNavViewController.h"
#import "ShareCarStoreVController.h"//共享
#import "UpdateMaintenanceVController.h"

@interface BaseTabViewController ()
@property(nonatomic,strong)NSMutableArray *items;
@property (nonatomic, strong) DesignatedDriverVController *designatedDriverVC;//代驾
@property (nonatomic, strong) MaintenanceVController *maintenanceVC;//维修
@property (nonatomic, strong) UpdateMaintenanceVController *updatemaintenanceVC;//维修保养

@property (nonatomic, strong) ShareCarStoreVController *shareVC;//共享
@property (nonatomic, strong) RidersViewController *didersVC;//车友帮
@property (nonatomic, strong) MyViewController *myVC;//我的

@end

@implementation BaseTabViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    [self setUpChildVC];
//    self.delegate = self;
    [self removeTabBarTopLine];
}

// 添加所有的子控制器
- (void)setUpChildVC
{
    //代驾
    DesignatedDriverVController *designatedDriverVC = [[DesignatedDriverVController alloc] init];
    [self setUpOneChildVC:designatedDriverVC image:[UIImage imageNamed:@"daijia"] selectedImage:kImageNamed(@"daijiaxuanzhong") title:@"代驾"];
    self.designatedDriverVC = designatedDriverVC;
    
    //维修保养
//    MaintenanceVController *maintenanceVC = [[MaintenanceVController alloc] init];
//    [self setUpOneChildVC:maintenanceVC image:[UIImage imageNamed:@"weweixiubaoyang"] selectedImage:kImageNamed(@"weweixiubaoyangxuanzhong") title:@"维修保养"];
//    self.maintenanceVC = maintenanceVC;
    UpdateMaintenanceVController *maintenanceVC = [[UpdateMaintenanceVController alloc] init];
    [self setUpOneChildVC:maintenanceVC image:[UIImage imageNamed:@"weweixiubaoyang"] selectedImage:kImageNamed(@"weweixiubaoyangxuanzhong") title:@"维修保养"];
    self.updatemaintenanceVC = maintenanceVC;
    
    
    //共享
    ShareCarStoreVController *sharecarVC = [[ShareCarStoreVController alloc] init];
    [self setUpOneChildVC:sharecarVC image:[UIImage imageNamed:@"tab_share_nor"] selectedImage:kImageNamed(@"tab_share_sel") title:@"共享"];
    self.shareVC = sharecarVC;
    
    //车友帮
    RidersViewController *didersVC = [[RidersViewController alloc] init];
    [self setUpOneChildVC:didersVC image:[UIImage imageNamed:@"cheyoubang"] selectedImage:kImageNamed(@"cheyoubangxuanzhong") title:@"车友邦"];
    self.didersVC = didersVC;
    
    //车友帮
    MyViewController *myVC = [[MyViewController alloc] init];
    [self setUpOneChildVC:myVC image:[UIImage imageNamed:@"wode"] selectedImage:kImageNamed(@"wodexuanzhong") title:@"我的"];
    self.myVC = myVC;
    
    if (@available(iOS 13.0, *)) {
        self.overrideUserInterfaceStyle = UIUserInterfaceStyleLight;
    } else {
        // Fallback on earlier versions
    }
}

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
/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
