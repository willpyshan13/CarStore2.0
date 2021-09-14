//
//  CarCollectionReusableView.h
//  CarOwnerProject
//
//  Created by apple on 2021/1/29.
//

#import <UIKit/UIKit.h>

NS_ASSUME_NONNULL_BEGIN

@interface CarCollectionReusableView : UICollectionReusableView
@property(nonatomic,strong) UIView *lineView;
@property(nonatomic,strong) UIImageView *iconImage;
@property (nonatomic, strong) UILabel *headText;
@end

NS_ASSUME_NONNULL_END
