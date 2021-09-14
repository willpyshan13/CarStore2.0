//
//  UpGoodsListModel.h
//  CarStoreProject
//
//  Created by apple on 2021/1/28.
//

#import "BaseModel.h"

NS_ASSUME_NONNULL_BEGIN
//上架商品、下架商品
@interface UpGoodsListModel : BaseModel
@property (nonatomic, copy) NSString *amt;//商品金额
@property (nonatomic, copy) NSString *commentNum;//商品评论数量
@property (nonatomic, copy) NSString *createdTime;
@property (nonatomic,strong)NSArray *detailList;//物料名称价格
@property (nonatomic, copy) NSString *goodsDescribe;
@property (nonatomic, copy) NSString *goodsName;
@property (nonatomic, copy) NSString *goodsType;
@property (nonatomic, copy) NSString *goodsTypeUuid;
@property (nonatomic,strong)NSArray *imgList;//图片取数组中第一个
@property (nonatomic, copy) NSString *levelOne;
@property (nonatomic, copy) NSString *levelOneUuid;
@property (nonatomic, copy) NSString *levelTwo;
@property (nonatomic, copy) NSString *levelTwoUuid;
@property (nonatomic, copy) NSString *manHourCost;
@property (nonatomic, copy) NSString *materialsExpenses;
@property (nonatomic, copy) NSString *salesNum;// 销量
@property (nonatomic, copy) NSString *score;// 商品评分
@property (nonatomic, copy) NSString *sellSts;
@property (nonatomic, copy) NSString *storeName;//店铺名称
@property (nonatomic, copy) NSString *storeUuid;
@property (nonatomic, copy) NSString *surplusNum;//: 库存
@property (nonatomic, copy) NSString *uuid;


@end

NS_ASSUME_NONNULL_END
