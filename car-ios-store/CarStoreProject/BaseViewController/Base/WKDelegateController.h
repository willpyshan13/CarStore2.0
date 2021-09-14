//
//  WKDelegateController.h
//  DaLongInsurance
//
//  Created by 申巧 on 16/10/10.
//  Copyright © 2016年 申巧. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <WebKit/WebKit.h>

@protocol WKDelegate <NSObject>

- (void)userContentController:(WKUserContentController *)userContentController didReceiveScriptMessage:(WKScriptMessage *)message;

@end

@interface WKDelegateController : UIViewController<WKScriptMessageHandler>

@property (nonatomic, weak) id<WKDelegate> delegate;

@end
