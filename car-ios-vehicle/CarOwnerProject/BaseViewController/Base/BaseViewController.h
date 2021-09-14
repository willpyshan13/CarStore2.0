//
//  BaseViewController.h
//  CarOwnerProject
//
//  Created by apple on 2021/1/25.
//

#import <UIKit/UIKit.h>

NS_ASSUME_NONNULL_BEGIN

@interface BaseViewController : UIViewController
- (void)updateLeftNavBarBtnItemWithImage:(id)image target:(id)target selector:(SEL)selector;
- (void)leftNavBarBtnPressed:(id)sender;
@end

NS_ASSUME_NONNULL_END
