//
//  MaintenanceViewController.h
//  CarOwnerProject
//
//  Created by apple on 2021/3/18.
//

#import "BaseViewController.h"

NS_ASSUME_NONNULL_BEGIN
//养护信息
@interface MaintenanceViewController : BaseViewController
@property (nonatomic,copy) void(^DidScrollBlock)(CGFloat scrollY);
@end

NS_ASSUME_NONNULL_END
