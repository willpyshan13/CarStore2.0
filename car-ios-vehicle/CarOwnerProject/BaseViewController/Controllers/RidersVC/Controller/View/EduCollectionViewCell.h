//
//  EduCollectionViewCell.h
//  CarStoreProject
//
//  Created by Qin on 2021/2/15.
//

#import <UIKit/UIKit.h>
#import "MaintainInfoModel.h"

NS_ASSUME_NONNULL_BEGIN

@interface EduCollectionViewCell : UICollectionViewCell
@property (weak, nonatomic) IBOutlet UIImageView *carImg;
@property (weak, nonatomic) IBOutlet UILabel *nameLab;
@property (nonatomic,strong)MaintainInfoModel *model;
@end

NS_ASSUME_NONNULL_END
