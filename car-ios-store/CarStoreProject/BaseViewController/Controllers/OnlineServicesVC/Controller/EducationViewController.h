//
//  EducationViewController.h
//  CarStoreProject
//
//  Created by Qin on 2021/2/15.
//

#import "BaseViewController.h"

NS_ASSUME_NONNULL_BEGIN
//培训教育
@interface EducationViewController : BaseViewController
@property (nonatomic,copy) void(^DidScrollBlock)(CGFloat scrollY);
@end

NS_ASSUME_NONNULL_END
