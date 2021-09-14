//
//  DTCViewController.h
//  CarStoreProject
//
//  Created by Qin on 2021/2/15.
//

#import "BaseViewController.h"

NS_ASSUME_NONNULL_BEGIN

@interface DTCViewController : BaseViewController
@property (nonatomic,copy) void(^DidScrollBlock)(CGFloat scrollY);
@end

NS_ASSUME_NONNULL_END
