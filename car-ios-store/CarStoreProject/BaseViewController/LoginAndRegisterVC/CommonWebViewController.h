//
//  CommonWebViewController.h
//  CarStoreProject
//
//  Created by Qin on 2021/2/15.
//

#import "BaseViewController.h"

NS_ASSUME_NONNULL_BEGIN

@interface CommonWebViewController : BaseViewController

@property(nonatomic,copy)NSString *titleString;
@property(nonatomic,strong)NSURL *url;

@end

NS_ASSUME_NONNULL_END
