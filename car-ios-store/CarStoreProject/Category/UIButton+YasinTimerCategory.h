//
//  UIButton+YasinTimerCategory.h
//  GreenYears
//
//  Created by Yasin on 2017/11/3.
//  Copyright © 2017年 Yasin. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface UIButton (YasinTimerCategory)

- (void)startCountDownTime:(int)time withCountDownBlock:(void(^)(void))countDownBlock;

@end
