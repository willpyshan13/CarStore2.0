//
//  UploadImageTypeViewController.h
//  DaLongInsurance
//
//  Created by 申巧 on 16/7/22.
//  Copyright © 2016年 申巧. All rights reserved.
//

#import <UIKit/UIKit.h>

@protocol SelectImageDelegate <NSObject>

@optional
- (void)selectImageComplete:(UIImage *)image;

@end

@interface UploadImageTypeViewController : UIViewController

- (void)showUploadImageView;

@property (nonatomic, copy) void (^selectImageBlock)(UIImage *image);

@property (nonatomic) BOOL allowImageEdit;

@property (nonatomic, assign) id<SelectImageDelegate>imageDelegate;


@property (nonatomic) BOOL isUpLoadHeadImage;


@end
