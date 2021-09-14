//
//  SystemAlertView.m
//  PrayWithPureBody
//
//  Created by 申巧 on 2017/6/16.
//  Copyright © 2017年 申巧. All rights reserved.
//

#import "SystemAlertView.h"

#define RootVC  [[UIApplication sharedApplication] keyWindow].rootViewController

@implementation SystemAlertView

+ (SystemAlertView *)shareInstance {
    static SystemAlertView *tools = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        tools = [[self alloc] init];
    });
    return tools;
}

/**
 *  创建提示框
 *
 *  @param title        标题
 *  @param message      提示内容
 *  @param cancelTitle  取消按钮(无操作,为nil则只显示一个按钮)
 *  @param titleArray   标题字符串数组(为nil,默认为"确定")
 *  @param vc           VC
 *  @param confirm      点击确认按钮的回调
 */
- (void)showAlert:(id)title
          message:(id)message
      cancelTitle:(id)cancelTitle
       titleArray:(NSArray *)titleArray
   viewController:(UIViewController *)vc
          confirm:(AlertViewBlock)confirm {
    
    if (!vc) vc = RootVC;
    
    [self p_showAlertController:title message:message
                    cancelTitle:cancelTitle titleArray:titleArray
                 viewController:vc confirm:^(NSInteger buttonTag) {
                     if (confirm)confirm(buttonTag);
                 }];
}

/**
 *  创建提示框(可变参数版)
 *
 *  @param title        标题
 *  @param message      提示内容
 *  @param cancelTitle  取消按钮(无操作,为nil则只显示一个按钮)
 *  @param vc           VC
 *  @param confirm      点击按钮的回调
 *  @param buttonTitles 按钮(为nil,默认为"确定",传参数时必须以nil结尾，否则会崩溃)
 */
- (void)showAlert:(id)title
          message:(id)message
      cancelTitle:(NSString *)cancelTitle
   viewController:(UIViewController *)vc
          confirm:(AlertViewBlock)confirm
     buttonTitles:(NSString *)buttonTitles, ... NS_REQUIRES_NIL_TERMINATION {
    
    // 读取可变参数里面的titles数组
    NSMutableArray *titleArray = [[NSMutableArray alloc] initWithCapacity:0];
    va_list list;
    if(buttonTitles) {
        //1.取得第一个参数的值(即是buttonTitles)
        [titleArray addObject:buttonTitles];
        //2.从第2个参数开始，依此取得所有参数的值
        NSString *otherTitle;
        va_start(list, buttonTitles);
        while ((otherTitle = va_arg(list, NSString*))) {
            [titleArray addObject:otherTitle];
        }
        va_end(list);
    }
    
    if (!vc) vc = RootVC;
    
    [self p_showAlertController:title message:message
                    cancelTitle:cancelTitle titleArray:titleArray
                 viewController:vc confirm:^(NSInteger buttonTag) {
                     if (confirm)confirm(buttonTag);
                 }];
    
}

- (void)p_showAlertController:(id)title
                      message:(id)message
                  cancelTitle:(id)cancelTitle
                   titleArray:(NSArray *)titleArray
               viewController:(UIViewController *)vc
                      confirm:(AlertViewBlock)confirm {
    
    UIAlertController  *alert = [UIAlertController alertControllerWithTitle:@""
                                                                    message:@""
                                                             preferredStyle:UIAlertControllerStyleAlert];
    if ([title isKindOfClass:[NSAttributedString class]]) {
        NSAttributedString *attributeString = title;
        [alert setValue:attributeString forKey:@"attributedTitle"];
    }else if([title isKindOfClass:[NSString class]]){
        [alert setTitle:title];
    }
    
    if ([message isKindOfClass:[NSAttributedString class]]) {
        NSAttributedString *attributeString = message;
        [alert setValue:attributeString forKey:@"attributedMessage"];
    }else if([message isKindOfClass:[NSString class]]){
        [alert setMessage:message];
    }
    
    if (cancelTitle) {
        NSString *cancelTitle_ = @"";
        UIColor *cancelColor = nil;
        if ([cancelTitle isKindOfClass:[NSString class]]) {
            cancelTitle_ = cancelTitle;
        }else if ([cancelTitle isKindOfClass:[NSArray class]]){
            NSArray *tmp = cancelTitle;
            cancelTitle_ = tmp[0];
            cancelColor = tmp[1];
        }
        // 取消
        UIAlertAction  *cancelAction = [UIAlertAction actionWithTitle:cancelTitle_
                                                                style:UIAlertActionStyleCancel
                                                              handler:^(UIAlertAction * _Nonnull action) {
                                                                  if (confirm)confirm(cancelIndex);
                                                              }];
        if (cancelColor) {
            [cancelAction setValue:cancelColor forKey:@"titleTextColor"];
        }
        [alert addAction:cancelAction];
    }
    // 确定操作
    if (!titleArray || titleArray.count == 0) {
        UIAlertAction  *confirmAction = [UIAlertAction actionWithTitle:@"确定"
                                                                 style:UIAlertActionStyleDefault
                                                               handler:^(UIAlertAction * _Nonnull action) {
                                                                   if (confirm)confirm(0);
                                                               }];
        
        [alert addAction:confirmAction];
    } else {
        for (NSInteger i = 0; i<titleArray.count; i++) {
            NSString *btnTitle = @"";
            UIColor *btnTitleColor = nil;
            if ([titleArray[i] isKindOfClass:[NSString class]]) {
                btnTitle = titleArray[i];
            }else if ([titleArray[i] isKindOfClass:[NSArray class]]){
                NSArray *tmp = titleArray[i];
                btnTitle = tmp[0];
                btnTitleColor = tmp[1];
            }
            UIAlertAction  *action = [UIAlertAction actionWithTitle:btnTitle
                                                              style:UIAlertActionStyleDefault
                                                            handler:^(UIAlertAction * _Nonnull action) {
                                                                if (confirm)confirm(i);
                                                            }];
            if (btnTitleColor) {
                [action setValue:btnTitleColor forKey:@"titleTextColor"]; // 此代码 可以修改按钮颜色
            }
            [alert addAction:action];
        }
    }
    
    [vc presentViewController:alert animated:YES completion:nil];
}

/**
 *  创建菜单(Sheet)
 *
 *  @param title        标题
 *  @param message      提示内容
 *  @param cancelTitle  取消按钮(无操作,为nil则只显示一个按钮)
 *  @param titleArray   标题字符串数组(为nil,默认为"确定")
 *  @param vc           VC
 *  @param confirm      点击确认按钮的回调
 */
- (void)showSheet:(id)title
          message:(id)message
      cancelTitle:(NSString *)cancelTitle
       titleArray:(NSArray *)titleArray
   viewController:(UIViewController *)vc
          confirm:(AlertViewBlock)confirm {
    
    if (!vc) vc = RootVC;
    
    [self p_showSheetAlertController:title message:message cancelTitle:cancelTitle
                          titleArray:titleArray viewController:vc confirm:^(NSInteger buttonTag) {
                              if (confirm)confirm(buttonTag);
                          }];
}

/**
 *  创建菜单(Sheet 可变参数版)
 *
 *  @param title        标题
 *  @param message      提示内容
 *  @param cancelTitle  取消按钮(无操作,为nil则只显示一个按钮)
 *  @param vc           VC iOS8及其以后会用到
 *  @param confirm      点击按钮的回调
 *  @param buttonTitles 按钮(为nil,默认为"确定",传参数时必须以nil结尾，否则会崩溃)
 */
- (void)showSheet:(NSString *)title
          message:(NSString *)message
      cancelTitle:(NSString *)cancelTitle
   viewController:(UIViewController *)vc
          confirm:(AlertViewBlock)confirm
     buttonTitles:(NSString *)buttonTitles, ... NS_REQUIRES_NIL_TERMINATION {
    // 读取可变参数里面的titles数组
    NSMutableArray *titleArray = [[NSMutableArray alloc] initWithCapacity:0];
    va_list list;
    if(buttonTitles) {
        //1.取得第一个参数的值(即是buttonTitles)
        [titleArray addObject:buttonTitles];
        //2.从第2个参数开始，依此取得所有参数的值
        NSString *otherTitle;
        va_start(list, buttonTitles);
        while ((otherTitle= va_arg(list, NSString*))) {
            [titleArray addObject:otherTitle];
        }
        va_end(list);
    }
    
    if (!vc) vc = RootVC;
    
    // 显示菜单提示框
    [self p_showSheetAlertController:title message:message cancelTitle:cancelTitle
                          titleArray:titleArray viewController:vc confirm:^(NSInteger buttonTag) {
                              if (confirm)confirm(buttonTag);
                          }];
    
}

// ActionSheet的封装
- (void)p_showSheetAlertController:(id)title
                           message:(id)message
                       cancelTitle:(NSString *)cancelTitle
                        titleArray:(NSArray *)titleArray
                    viewController:(UIViewController *)vc
                           confirm:(AlertViewBlock)confirm {
    
    UIAlertController *sheet = [UIAlertController alertControllerWithTitle:nil
                                                                   message:nil
                                                            preferredStyle:UIAlertControllerStyleActionSheet];
    if ([title isKindOfClass:[NSAttributedString class]]) {
        NSAttributedString *attributeString = title;
        [sheet setValue:attributeString forKey:@"attributedTitle"];
    }else if([title isKindOfClass:[NSString class]]){
        [sheet setTitle:title];
    }
    
    if ([message isKindOfClass:[NSAttributedString class]]) {
        NSAttributedString *attributeString = message;
        [sheet setValue:attributeString forKey:@"attributedMessage"];
    }else if([message isKindOfClass:[NSString class]]){
        [sheet setMessage:message];
    }
    
//    if (!cancelTitle) cancelTitle = @"取消";
    if (cancelTitle) {
        // 取消
        UIAlertAction  *cancelAction = [UIAlertAction actionWithTitle:cancelTitle
                                                                style:UIAlertActionStyleCancel
                                                              handler:^(UIAlertAction * _Nonnull action) {
                                                                  if (confirm)confirm(cancelIndex);
                                                              }];
        [cancelAction setValue:[UIColor blackColor] forKey:@"titleTextColor"];
        [sheet addAction:cancelAction];
    }
    
    
    if (titleArray.count > 0) {
        for (NSInteger i = 0; i<titleArray.count; i++) {
            UIAlertAction  *action = [UIAlertAction actionWithTitle:titleArray[i]
                                                              style:UIAlertActionStyleDefault
                                                            handler:^(UIAlertAction * _Nonnull action) {
                                                                if (confirm)confirm(i);
                                                            }];
            [action setValue:[UIColor blackColor] forKey:@"titleTextColor"];
            [sheet addAction:action];
        }
    }
    
    [vc presentViewController:sheet animated:YES completion:nil];
}

@end
