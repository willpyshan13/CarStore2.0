//
//  PaidConsultingVController.h
//  CarOwnerProject
//
//  Created by apple on 2021/1/26.
//

#import "BaseViewController.h"

NS_ASSUME_NONNULL_BEGIN
//付费咨询
@interface PaidConsultingVController : BaseViewController
@property (nonatomic,strong)UITableView *listTable;

@property (nonatomic,copy) void(^DidScrollBlock)(CGFloat scrollY);
@end

NS_ASSUME_NONNULL_END
