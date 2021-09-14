//
//  BaseNavViewController.m
//  CarStoreProject
//
//  Created by apple on 2021/1/25.
//

#import "BaseNavViewController.h"

@interface BaseNavViewController ()<UIGestureRecognizerDelegate>

@end

@implementation BaseNavViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    self.interactivePopGestureRecognizer.delegate = self;
}
- (BOOL)gestureRecognizerShouldBegin:(UIGestureRecognizer *)gestureRecognizer
{
   if (self.childViewControllers.count == 1)
   {
       return NO;
       
   }
   return YES;
   
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
