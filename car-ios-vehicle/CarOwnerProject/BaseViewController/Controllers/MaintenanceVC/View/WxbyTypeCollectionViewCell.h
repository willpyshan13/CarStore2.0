//
//  WxbyTypeCollectionViewCell.h
//  CarOwnerProject
//
//  Created by apple on 2021/3/19.
//

#import <UIKit/UIKit.h>
#import "QueryTreeListModel.h"
NS_ASSUME_NONNULL_BEGIN

@interface WxbyTypeCollectionViewCell : UICollectionViewCell
@property (weak, nonatomic) IBOutlet UIImageView *typeImg;
@property (weak, nonatomic) IBOutlet UILabel *nameLab;
@property (nonatomic,strong)QueryTreeListModel *model;
@end

NS_ASSUME_NONNULL_END
