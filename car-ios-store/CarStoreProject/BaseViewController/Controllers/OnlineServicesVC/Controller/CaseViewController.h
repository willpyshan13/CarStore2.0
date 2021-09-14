//
//  CaseViewController.h
//  CarStoreProject
//
//  Created by apple on 2021/1/27.
//

#import "BaseViewController.h"

NS_ASSUME_NONNULL_BEGIN
//案例
@interface CaseViewController : BaseViewController
@property (nonatomic,copy) void(^DidScrollBlock)(CGFloat scrollY);

@end

NS_ASSUME_NONNULL_END
