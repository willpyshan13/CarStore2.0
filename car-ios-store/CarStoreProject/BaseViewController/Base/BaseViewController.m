//
//  BaseViewController.m
//  CarStoreProject
//
//  Created by apple on 2021/1/25.
//

#import "BaseViewController.h"

@interface BaseViewController ()
@property (nonatomic,strong)UIImageView *lineView;
@end

@implementation BaseViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    //这是状态栏字体颜色
    [self preferredStatusBarStyle];
    self.view.backgroundColor = [UIColor whiteColor];
    self.navigationController.navigationBar.barTintColor = [UIColor whiteColor];
//    [self.navigationController.navigationBar setBackgroundImage:[UIImage imageNamed:@"white_nav_bg"] forBarMetrics:0];
    _lineView =  [self findlineviw:self.navigationController.navigationBar];
    
    if (@available(iOS 13.0, *)) {
        self.overrideUserInterfaceStyle = UIUserInterfaceStyleLight;
    } else {
        // Fallback on earlier versions
    }
    
}

- (void)viewWillAppear:(BOOL)animated

{
    [super viewWillAppear:animated];
    _lineView.hidden = YES;
//    [self.navigationController.navigationBar setBackgroundImage:[UIImage imageNamed:@"white_nav_bg"] forBarMetrics:0];
}

- (UIImageView *)findlineviw:(UIView *)view
{
    if([view isKindOfClass:[UIImageView class]] &&view.bounds.size.height<=1.0){
        return (UIImageView *)view;
    }for (UIImageView *subview in view.subviews)
    {
        UIImageView *lineview = [self findlineviw:subview];
        if (lineview)
        {
            return lineview;
        }
    }
    return nil;
}

- (void)updateLeftNavBarBtnItemWithImage:(id)image target:(id)target selector:(SEL)selector{
    
    UIButton *btn = [[UIButton alloc] initWithFrame:(CGRect){0,0,44,44}];
    if ([image isKindOfClass:[UIImage class]]) {
        [btn setImage:(UIImage *)image forState:UIControlStateNormal];
    }else{
        [btn setImage:[UIImage imageNamed:image] forState:UIControlStateNormal];
    }
    [btn setImageEdgeInsets:UIEdgeInsetsMake(0, 0, 0, 22)];
//    [btn addTarget:target action:selector forControlEvents:UIControlEventTouchUpInside];
    [btn addTarget:self
       action:@selector(leftNavBarBtnPressed:)
     forControlEvents:UIControlEventTouchUpInside];
    self.navigationItem.leftBarButtonItem = [[UIBarButtonItem alloc] initWithCustomView:btn];
}
- (void)leftNavBarBtnPressed:(id)sender{
    [self.navigationController popViewControllerAnimated:YES];
}


#pragma mark 点击空白处叫回键盘
- (void)touchesBegan:(NSSet<UITouch *> *)touches withEvent:(UIEvent *)event {
    [self.view endEditing:YES];
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
