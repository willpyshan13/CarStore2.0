//
//  MyQDViewController.h
//  CarStoreProject
//
//  Created by apple on 2021/3/9.
//

#import "BaseViewController.h"

NS_ASSUME_NONNULL_BEGIN
//我的抢单
@interface MyQDViewController : BaseViewController
@property (nonatomic,copy) void(^DidScrollBlock)(CGFloat scrollY);

@end

NS_ASSUME_NONNULL_END
