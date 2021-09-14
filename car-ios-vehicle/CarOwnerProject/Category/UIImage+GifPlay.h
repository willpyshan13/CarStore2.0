//
//  UIImage+GifPlay.h
//  DaLongInsurance
//
//  Created by 申巧 on 16/9/2.
//  Copyright © 2016年 申巧. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface UIImage (GifPlay)

+ (NSArray *)gifImageArrayWithData:(NSData *)data;

+ (UIImage *)My_sd_animatedGIFNamed:(NSString *)name;

+ (NSArray *)testFunctionWithData:(NSData *)data;

@end
