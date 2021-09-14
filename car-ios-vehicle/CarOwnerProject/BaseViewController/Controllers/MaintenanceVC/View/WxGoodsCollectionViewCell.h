//
//  WxGoodsCollectionViewCell.h
//  CarOwnerProject
//
//  Created by apple on 2021/3/19.
//

#import <UIKit/UIKit.h>
#import "StoreQueryListWXModel.h"
NS_ASSUME_NONNULL_BEGIN

@interface WxGoodsCollectionViewCell : UICollectionViewCell
@property (weak, nonatomic) IBOutlet UIImageView *goodImg;
@property (weak, nonatomic) IBOutlet UILabel *storeNameLab;

@property (weak, nonatomic) IBOutlet UILabel *desLab;//分类
@property (weak, nonatomic) IBOutlet UILabel *brandLab;//品牌

@property (nonatomic,strong)StoreQueryListWXModel *model;
@end

NS_ASSUME_NONNULL_END
