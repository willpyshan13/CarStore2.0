//
//  CarAllTypeView.h
//  CarOwnerProject
//
//  Created by apple on 2021/1/26.
//

#import <UIKit/UIKit.h>

NS_ASSUME_NONNULL_BEGIN

@protocol CarAllTypeViewDelegate <NSObject>

@optional
- (void)didSelectedIndexPath:(NSIndexPath *)indexPath;
@end


//全部分类
@interface CarAllTypeView : UIView

@property (nonatomic,weak) id<CarAllTypeViewDelegate>delegete;
@end

NS_ASSUME_NONNULL_END
