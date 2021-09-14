//
//  UIButton+YasinTimerCategory.m
//  GreenYears
//
//  Created by Yasin on 2017/11/3.
//  Copyright © 2017年 Yasin. All rights reserved.
//

//注意：如果按钮出现闪烁，将xib或者storyboard中Type属性设置为custom即可

#import "UIButton+YasinTimerCategory.h"

static NSString *yasinTempText;

@implementation UIButton (YasinTimerCategory)

- (void)startCountDownTime:(int)time withCountDownBlock:(void(^)(void))countDownBlock{
    
    [self initButtonData];
    
    [self startTime:time];
    
    if (countDownBlock) {
        countDownBlock();
    }
}

- (void)initButtonData{
    
    yasinTempText = [NSString stringWithFormat:@"%@",self.titleLabel.text];
  
}

- (void)startTime:(int)time{
    
    __block int timeout = time;
    
    dispatch_queue_t queue = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0);
    
    dispatch_source_t _timer = dispatch_source_create(DISPATCH_SOURCE_TYPE_TIMER, 0, 0,queue);
    
    dispatch_source_set_timer(_timer,dispatch_walltime(NULL, 0),1.0 * NSEC_PER_SEC, 0);
    
    dispatch_source_set_event_handler(_timer, ^{
        
        //倒计时结束
        if(timeout <= 0){
            
            dispatch_source_cancel(_timer);
            
            dispatch_async(dispatch_get_main_queue(), ^{
                
                [self setTitle:yasinTempText forState:UIControlStateNormal];
                self.userInteractionEnabled = YES;
            });
            
        }else{
            
            dispatch_async(dispatch_get_main_queue(), ^{
                
                NSString *text = [NSString stringWithFormat:@"%02ds",timeout];
                [self setTitle:text forState:UIControlStateNormal];
                self.userInteractionEnabled = NO;
                
            });
            
            timeout --;
            
        }
        
    });
    
    dispatch_resume(_timer);
    
}

@end
