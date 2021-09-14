//
//  ScrollSortBarView.h
//  CarOwnerProject
//
//  Created by apple on 2021/3/19.
//

#import <UIKit/UIKit.h>
#import "QueryTreeListModel.h"
NS_ASSUME_NONNULL_BEGIN

@interface ScrollSortBarView : UIView
- (instancetype)initWithFrame:(CGRect)frame item:(NSArray *)array;
@property (nonatomic, strong) UIScrollView *scrollView;
@property (nonatomic, strong) NSArray *itemArray;

@property (nonatomic, copy) void(^selectItemBlock)(id obj, NSInteger index);

- (void)showIndex:(id)obj;

//是否在底部添加阴影效果
@property (nonatomic, assign) BOOL showShadow;
@end

NS_ASSUME_NONNULL_END
