//
//  WxbySortCollectionReusableView.m
//  CarOwnerProject
//
//  Created by apple on 2021/3/19.
//

#import "WxbySortCollectionReusableView.h"
#import "ScrollSortBarView.h"
#import "QueryTreeListModel.h"
@implementation WxbySortCollectionReusableView

- (instancetype)initWithFrame:(CGRect)frame{
    if (self = [super initWithFrame:frame])
    {
        if (self)
        {
            self.storeTypeTag = 0;
        }
    }
    return self;
}

-(void)setSortArr:(NSMutableArray *)sortArr
{
    if (sortArr) {
        DLog(@"sortArr23243===%@",sortArr);
        for (NSInteger i=0; i<sortArr.count; i++)
        {
            ScrollSortBarView *label=self.sortArr[i];
            [label removeFromSuperview];
        }
        
        [self initUIViewWithFrame:sortArr];
    }
}
- (void)initUIViewWithFrame:(NSMutableArray*)sortArr
{
    for (ScrollSortBarView *view in self.subviews) {
        [view removeFromSuperview];
    }
    ScrollSortBarView *barView =[[ScrollSortBarView alloc]initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 50) item:sortArr];
    barView.backgroundColor =[UIColor redColor];
    [barView showIndex:@(self.storeTypeTag)];
    barView.selectItemBlock = ^(id obj, NSInteger tag){
        DLog(@"汽车维修tag===%ld",tag);
        self.storeTypeTag = tag;
        if (self.selectItemBlock) {
            self.selectItemBlock(obj,tag);
        }
    };
    
    [self addSubview:barView];
        
    }

//-(NSMutableArray *)sortArr{
//
//    if (!_sortArr) {
//        _sortArr = [[NSMutableArray alloc]init];
//
//    }
//    return _sortArr;
//}



@end
