//
//  DTCCell.h
//  CarStoreProject
//
//  Created by Qin on 2021/2/15.
//

#import <UIKit/UIKit.h>
#import "DTCListModel.h"

NS_ASSUME_NONNULL_BEGIN

@interface DTCCell : UITableViewCell

//编号
@property (weak, nonatomic) IBOutlet UILabel *codeLabel;
//描述
@property (weak, nonatomic) IBOutlet UILabel *desLabel;
//品牌
@property (weak, nonatomic) IBOutlet UILabel *pinpaiLab;

@property (nonatomic,strong)DTCListModel *model;

@end


NS_ASSUME_NONNULL_END
