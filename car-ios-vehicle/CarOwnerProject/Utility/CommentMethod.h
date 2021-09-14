//
//  CommentMethod.h
//  InHoldingProject
//
//  Created by user on 2018/3/27.
//  Copyright © 2018年 wangshuping. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>
@interface CommentMethod : NSObject
/**
 * 过滤value中的空值 -----引入头文件后,在需要的地方直接这样写SafeValue(你需要判断过滤的值)
 */
NSString *SafeValue(id value);

NSString *decimalNumberWithDouble(double conversionValue);//精确小数点
@end
