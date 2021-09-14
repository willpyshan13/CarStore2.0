//
//  WxbySortCollectionReusableView.h
//  CarOwnerProject
//
//  Created by apple on 2021/3/19.
//

#import <UIKit/UIKit.h>

NS_ASSUME_NONNULL_BEGIN

@interface WxbySortCollectionReusableView : UICollectionReusableView
@property (nonatomic,strong)NSMutableArray *sortArr;
@property (nonatomic,assign)NSInteger storeTypeTag;

@property (nonatomic, copy) void(^selectItemBlock)(id obj, NSInteger index);


@end

NS_ASSUME_NONNULL_END
