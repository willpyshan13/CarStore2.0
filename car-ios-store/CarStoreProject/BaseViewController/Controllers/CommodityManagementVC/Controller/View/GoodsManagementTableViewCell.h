//
//  GoodsManagementTableViewCell.h
//  CarStoreProject
//
//  Created by apple on 2021/1/27.
//

#import <UIKit/UIKit.h>
#import "UpGoodsListModel.h"

NS_ASSUME_NONNULL_BEGIN
//商品管理
@interface GoodsManagementTableViewCell : UITableViewCell
@property (weak, nonatomic) IBOutlet UIImageView *goodsImg;//图片
@property (weak, nonatomic) IBOutlet UILabel *storeNameLab;//店名称
@property (weak, nonatomic) IBOutlet UILabel *stateLab;//状态 已上架 已下架
@property (weak, nonatomic) IBOutlet UILabel *startNumLab;//星级数
@property (weak, nonatomic) IBOutlet UILabel *comNumLab;//评论数
@property (weak, nonatomic) IBOutlet UILabel *saleNumLab;//销量 库存
@property (weak, nonatomic) IBOutlet UILabel *priceLab;

@property (nonatomic,strong)UpGoodsListModel *model;

@end

NS_ASSUME_NONNULL_END
