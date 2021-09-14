//
//  SelectCityViewController.h
//  CarOwnerProject
//
//  Created by apple on 2021/1/27.
//

#import "BaseViewController.h"

NS_ASSUME_NONNULL_BEGIN
@protocol CityListViewDelegate <NSObject>

- (void)didClickedWithCityName:(NSString*)cityName;

@end

@interface SelectCityViewController : BaseViewController<UITableViewDataSource,UITableViewDelegate>
@property (strong, nonatomic) id<CityListViewDelegate>delegate;



@property (strong, nonatomic) NSMutableArray *arrayLocatingCity;//定位城市数据
@property (strong, nonatomic) NSMutableArray *arrayHotCity;//热门城市数据
@property (strong, nonatomic) NSMutableArray *arrayHistoricalCity;//常用城市数据

@property(nonatomic,strong)UITableView *tableView;
@end

NS_ASSUME_NONNULL_END
