//
//  SystemAlertView.h
//  PrayWithPureBody
//
//  Created by 申巧 on 2017/6/16.
//  Copyright © 2017年 申巧. All rights reserved.
//

#import <Foundation/Foundation.h>

#define cancelIndex    (-1)

typedef void(^AlertViewBlock)(NSInteger buttonTag);

@interface SystemAlertView : NSObject

+ (SystemAlertView *)shareInstance;

- (void)showAlert:(id)title
          message:(id)message
      cancelTitle:(id)cancelTitle
       titleArray:(NSArray *)titleArray
   viewController:(UIViewController *)vc
          confirm:(AlertViewBlock)confirm;


- (void)showAlert:(id)title
          message:(id)message
      cancelTitle:(NSString *)cancelTitle
   viewController:(UIViewController *)vc
          confirm:(AlertViewBlock)confirm
     buttonTitles:(NSString *)buttonTitles, ... NS_REQUIRES_NIL_TERMINATION;

- (void)showSheet:(id)title
          message:(id)message
      cancelTitle:(NSString *)cancelTitle
       titleArray:(NSArray *)titleArray
   viewController:(UIViewController *)vc
          confirm:(AlertViewBlock)confirm;

@end
