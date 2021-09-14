//
//  UIColor+Additions.m
//  KeChiProduct
//
//  Created by apple on 2020/5/19.
//  Copyright © 2020 apple. All rights reserved.
//

#import "UIColor+Additions.h"
#define DEFAULT_VOID_COLOR [UIColor whiteColor]


@implementation UIColor (Additions)
//stringToConvert 十六进制颜色透明度值  形如： #ffffff,0.5  表示白色，0.5透明度
+ (UIColor *)colorWithHexStringAndAlpha:(NSString *)stringToConvert{
    NSString *cString = [[stringToConvert stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceAndNewlineCharacterSet]] uppercaseString];
    
    if ([cString length] < 6)
        return DEFAULT_VOID_COLOR;
    if ([cString hasPrefix:@"#"])
        cString = [cString substringFromIndex:1];
    
    NSRange range;
    range.location = 0;
    range.length = 2;
    NSString *rString = [cString substringWithRange:range];
    
    range.location = 2;
    NSString *gString = [cString substringWithRange:range];
    
    range.location = 4;
    NSString *bString = [cString substringWithRange:range];
    
    unsigned int r, g, b;
    [[NSScanner scannerWithString:rString] scanHexInt:&r];
    [[NSScanner scannerWithString:gString] scanHexInt:&g];
    [[NSScanner scannerWithString:bString] scanHexInt:&b];
    
    float alpha = 1;
    if ([cString containsString:@","]) {
        NSString *alphaStr = [[cString componentsSeparatedByString:@","] lastObject];
        alpha = [alphaStr floatValue];
    }
    return [UIColor colorWithRed:((float) r / 255.0f)
                           green:((float) g / 255.0f)
                            blue:((float) b / 255.0f)
                           alpha:alpha];
}
@end
