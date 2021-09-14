//
//  BaseModel.h
//  PrayWithPureBody
//
//  Created by 申巧 on 2017/5/9.
//  Copyright © 2017年 申巧. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "MJExtension.h"

#define NSNullToString(value) \
({\
id  result = nil;\
result = [value isKindOfClass:[NSNull class]] ? @"" : value;\
(result);\
})\

@interface BaseModel : NSObject

+ (NSArray *)getVariableNameByObject:(id)object;

+ (NSArray *)getPropertyNameByObject:(id)object;

@end
