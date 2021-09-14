//
//  CommentMethod.m
//  InHoldingProject
//
//  Created by user on 2018/3/27.
//  Copyright © 2018年 wangshuping. All rights reserved.
//

#import "CommentMethod.h"

@implementation CommentMethod

NSString *SafeValue(id value) {
    if(!value) {
        return @"";
    }else if ([value isKindOfClass:[NSString class]]) {
        if ([value isEqualToString:@"<null>"]) {
            return @"";
        }else if ([value isEqualToString:@"(null)"])
        {
            return @"";
        }
        else if ([value isEqualToString:@"null"])
        {
            return @"";
        }
        return value;
    }
    else {
        return [NSString stringWithFormat:@"%@",value];
    }
}


/** 直接传入精度丢失有问题的Double类型*/
NSString *decimalNumberWithDouble(double conversionValue){
    NSString *doubleString        = [NSString stringWithFormat:@"%lf", conversionValue];
    NSDecimalNumber *decNumber    = [NSDecimalNumber decimalNumberWithString:doubleString];
    return [decNumber stringValue];
}

@end
