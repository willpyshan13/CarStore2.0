//
//  UIImage+Color.h
//  DaLongInsurance
//
//  Created by nuopx-02 on 16/8/10.
//  Copyright © 2016年 申巧. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface UIImage (Color)

+ (UIImage*)createImageWithColor:(UIColor*) color targetSize:(CGSize)size;


/**
 *  根据颜色生成一张图片
 *  @param color 提供的颜色
 */
+ (UIImage *)imageWithColor:(UIColor *)color;
@end
