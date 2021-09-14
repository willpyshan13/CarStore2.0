//
//  SelectCityViewController.m
//  CarOwnerProject
//
//  Created by apple on 2021/1/27.
//

#import "SelectCityViewController.h"
#import "ZYPinYinSearch.h"
#import "ButtonGroupView.h"
#import "PinYinForObjc.h"
#define KSectionIndexBackgroundColor  [UIColor clearColor] //索引试图未选中时的背景颜色
#define kSectionIndexTrackingBackgroundColor [UIColor lightGrayColor]//索引试图选中时的背景
#define kSectionIndexColor [UIColor grayColor]//索引试图字体颜色
#define HotBtnColumns 3 //每行显示的热门城市数
#define BGCOLOR [UIColor colorWithRed:237/255.0 green:245/255.0 blue:251/255.0 alpha:1]

@interface SelectCityViewController ()<UIGestureRecognizerDelegate,UISearchBarDelegate,UITextFieldDelegate,ButtonGroupViewDelegate>
{
    UIImageView   *_bgImageView;
    UIView        *_tipsView;
    UILabel       *_tipsLab;
    NSTimer       *_timer;
}
@property (strong, nonatomic) UITextField *searchText;

@property (strong, nonatomic) NSMutableDictionary *searchResultDic;

@property (strong, nonatomic) ButtonGroupView *locatingCityGroupView;//定位城市试图

@property (strong, nonatomic) ButtonGroupView *hotCityGroupView;//热门城市

@property (strong, nonatomic) ButtonGroupView *historicalCityGroupView; //历史使用城市/常用城市

@property (strong, nonatomic) UIView *tableHeaderView;

@property (strong, nonatomic) NSMutableArray *arrayCitys;   //城市数据

@property (strong, nonatomic) NSMutableDictionary *cities;

@property (strong, nonatomic) NSMutableArray *keys; //城市首字母

@property (strong, nonatomic) NSMutableArray *sectionTitles;

//@property (strong, nonatomic) NSMutableArray *addressArray;
@property (strong, nonatomic) NSMutableArray *dataArray;

@end

@implementation SelectCityViewController

- (id)init
{
    self = [super init];
    if (self) {
        // Custom initialization
        self.arrayHotCity = [NSMutableArray array];
        
        self.arrayHistoricalCity = [NSMutableArray array];
        
        self.arrayLocatingCity   = [NSMutableArray array];
        self.keys = [NSMutableArray array];
        self.arrayCitys = [NSMutableArray array];
    }
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    self.navigationItem.title = @"选择城市";
    [self.navigationController.navigationBar setTitleTextAttributes:
         @{NSFontAttributeName:[UIFont systemFontOfSize:18.0f],
           NSForegroundColorAttributeName:Number090909Color}];
    [self creatNav];
    
    self.sectionTitles=[NSMutableArray array];
//    self.addressArray=[NSMutableArray array];
    self.dataArray=[NSMutableArray array];
    
    self.view.backgroundColor = [UIColor whiteColor];
 
    [self requestQueryListByTypeApi];//查询热门城市
    
    [self requestQueryAreaList];//查询所有区域
    //3自定义背景
    
    UIView *searchView = [[UIView alloc] initWithFrame:CGRectMake(0, NavigationBar_Height, self.view.frame.size.width, 60)];
    searchView.backgroundColor = [UIColor whiteColor];
    UIView *grayView =[[UIView alloc]initWithFrame:CGRectMake(15, 10, SCREEN_WIDTH-30, 40)];
    grayView.backgroundColor = NumberF6F7F8Color;
    grayView.clipsToBounds = YES;
    grayView.layer.cornerRadius = 6.0f;
    [searchView addSubview:grayView];
    
        
    UIImage *seaImg = kImageNamed(@"city_search_icon");
    UIImageView *searchBg = [[UIImageView alloc]initWithImage:seaImg];
    searchBg.frame = CGRectMake(14, (40-seaImg.size.height)/2, seaImg.size.width, seaImg.size.height);
    [grayView addSubview:searchBg];
    
    //搜索框
    CGFloat searbgW = CGRectGetWidth(grayView.frame);
    _searchText = [[UITextField alloc]initWithFrame:CGRectMake(CGRectGetMidX(searchBg.frame)+20, 0,searbgW-CGRectGetMidX(searchBg.frame)-30 , grayView.frame.size.height)];
    _searchText.backgroundColor = [UIColor clearColor];
    _searchText.font = [UIFont systemFontOfSize:13];
    _searchText.placeholder  = @"输入城市名称或字母查询";
    _searchText.returnKeyType = UIReturnKeySearch;
    _searchText.clearButtonMode = UITextFieldViewModeWhileEditing;
    _searchText.textColor    = [UIColor colorWithRed:58/255.0 green:58/255.0 blue:58/255.0 alpha:1];
    _searchText.delegate     = self;
    [_searchText addTarget:self action:@selector(textChange:) forControlEvents:UIControlEventEditingChanged];
    [grayView addSubview:_searchText];

    [self.view addSubview:searchView];

    
    // Do any additional setup after loading the view.
    _tableView = [[UITableView alloc] initWithFrame:self.view.bounds style:UITableViewStylePlain];
    _tableView.frame           = CGRectMake(0,searchView.frame.origin.y+searchView.frame.size.height, self.view.frame.size.width, self.view.frame.size.height-NavigationBar_Height-60);
    _tableView.backgroundColor = [UIColor clearColor];
    _tableView.delegate        = self;
    _tableView.dataSource      = self;
    [self.view addSubview:_tableView];
    
    
    
    //添加单击事件 取消键盘第一响应
    UITapGestureRecognizer *tap = [[UITapGestureRecognizer alloc]initWithTarget:self action:@selector(resignFirstResponder:)];
    tap.delegate = self;
    [self.view addGestureRecognizer:tap];
    
}
//查询热门城市
-(void)requestQueryListByTypeApi
{
    NSDictionary *pageBoDict=@{
    };
    NSDictionary *headerDict =@{
        @"token":[UserInfo getUserInfo].token,
    };
    NSString *urlStr = [NSString stringWithFormat:@"%@/hot_city",queryListByTypeApi];
    [HTTPManager getRequestWithUrl:urlStr parameters:pageBoDict header:headerDict progress:^(NSProgress * _Nonnull uploadProgress) {
        
    } success:^(NSDictionary * _Nonnull responseObject) {

        DLog(@"热门城市responseObject===%@",responseObject);
        NSArray *dataArr = responseObject[@"data"];
        [self.arrayHotCity addObjectsFromArray:dataArr];
        
        [self ininHeaderView];
        
        self.hotCityGroupView.items = [self GetCityDataSoucre:self.arrayHotCity];
        
        [self.tableView reloadData];
        
    } other:^(NSDictionary * _Nonnull responseObject) {
        [AppUtils showErrorMessage:responseObject[@"msg"]];
        
    } failure:^(NSError * _Nonnull error) {
        
    }];
}
#pragma mark== 查询所有区域
-(void)requestQueryAreaList
{
    NSDictionary *pageBoDict=@{
    };
    NSDictionary *headerDict =@{
    };
    NSString *urlStr = [NSString stringWithFormat:@"%@?areaType=2",queryAreaListApi];
    DLog(@"areaTypeareaTypeareaTypeurlStr======%@",urlStr);
    [HTTPManager getRequestWithUrl:urlStr parameters:pageBoDict header:headerDict progress:^(NSProgress * _Nonnull uploadProgress) {
        
    } success:^(NSDictionary * _Nonnull responseObject) {
        DLog(@"查询所有区域responseObject===%@",responseObject);
        NSArray *dataArray = responseObject[@"data"];
        if (dataArray.count>0) {
            self.dataArray=[NSMutableArray arrayWithArray:dataArray];
            NSMutableDictionary *addressDict=[self sortDataArray:dataArray.mutableCopy];
            self.cities=addressDict;
            [self getCityData];
        }
        
    } other:^(NSDictionary * _Nonnull responseObject) {
        [AppUtils showErrorMessage:responseObject[@"msg"]];
        
    } failure:^(NSError * _Nonnull error) {
        
    }];
}

//根据人名的首字母进行排序，并把实体类进行排序
- (NSMutableDictionary*)sortDataArray:(NSMutableArray *)buddyList{
   NSMutableArray *contactsSource = [NSMutableArray array];
   contactsSource = buddyList;

   NSMutableArray *array = [NSMutableArray array];
   //建立索引的核心, 返回27，是a－z和＃
   UILocalizedIndexedCollation *indexCollation = [UILocalizedIndexedCollation currentCollation];
   [array addObjectsFromArray:[indexCollation sectionTitles]];
    
   self.sectionTitles = array;
    
   NSInteger highSection = [self.sectionTitles count];

   NSMutableArray *sortedArray = [NSMutableArray arrayWithCapacity:highSection];

   for (int i = 0; i < highSection; i++) {
      NSMutableArray *sectionArray = [NSMutableArray arrayWithCapacity:1];
      [sortedArray addObject:sectionArray];
   }

   //按首字母分组
   for (NSDictionary *dict in contactsSource) {
      if (dict) {
          NSString *firstLetter = [PinYinForObjc chineseConvertToPinYin:dict[@"areaName"]];
          NSInteger section = [indexCollation sectionForObject:[firstLetter substringToIndex:1] collationStringSelector:@selector(uppercaseString)];
          NSMutableArray *array = [sortedArray objectAtIndex:section];
          [array addObject:dict];
      }
   }

   //每个section内的数组排序
   for (int i = 0; i < [sortedArray count]; i++) {
       NSArray *array=[sortedArray[i] sortedArrayUsingComparator:^NSComparisonResult(id  _Nonnull obj1, id  _Nonnull obj2) {
           NSString *firstLetter1 = [PinYinForObjc chineseConvertToPinYin:obj1[@"areaName"]];
           firstLetter1 = [[firstLetter1 substringToIndex:1] uppercaseString];
           NSString *firstLetter2 = [PinYinForObjc chineseConvertToPinYin:obj2[@"areaName"]];
           firstLetter2 = [[firstLetter2 substringToIndex:1] uppercaseString];
           return [firstLetter1 caseInsensitiveCompare:firstLetter2];
       }];
      
      [sortedArray replaceObjectAtIndex:i withObject:[NSMutableArray arrayWithArray:array]];

   }

   

   //去掉空的section

   for (NSInteger i = [sortedArray count] - 1; i >= 0; i--) {

      NSArray *array = [sortedArray objectAtIndex:i];

      if ([array count] == 0) {

          [sortedArray removeObjectAtIndex:i];

          [self.sectionTitles removeObjectAtIndex:i];

      }

   }

    NSMutableDictionary *addressDict=[NSMutableDictionary dictionary];
    for (NSInteger i=0; i<sortedArray.count; i++) {
        NSArray *cityArray=sortedArray[i];
        NSString *letter=[[cityArray firstObject] objectForKey:@"letter"];
        [addressDict setObject:cityArray forKey:letter];
    }
    
//    NSLog(@"处理后的地区数据===%@",addressDict);
    return addressDict;;
}

-(void)creatNav{
    UIView *leftView=[[UIView alloc]initWithFrame:CGRectMake(0, 0, 100, 30)];
    UIImage *closeImg=kImageNamed(@"city_close_icon");
    UIButton *closeBtn =[UIButton buttonWithType:UIButtonTypeCustom];
    closeBtn.frame = CGRectMake(-20, 0, 80, 30);
    [closeBtn setImage:closeImg forState:UIControlStateNormal];
    [closeBtn addTarget:self action:@selector(closeBtnClick) forControlEvents:UIControlEventTouchUpInside];
    closeBtn.titleLabel.font = FontSize(16);
    [leftView addSubview:closeBtn];
    UIBarButtonItem *leftItem = [[UIBarButtonItem alloc]initWithCustomView:leftView];
    UIBarButtonItem *spaceItem = [[UIBarButtonItem alloc]initWithBarButtonSystemItem:UIBarButtonSystemItemFixedSpace target:nil action:nil];
    spaceItem.width = -15;
    self.navigationItem.leftBarButtonItems = @[spaceItem,leftItem];
}
-(void)closeBtnClick
{
    DLog(@"关闭");
    [self.navigationController popViewControllerAnimated:NO];
}

- (void)resignFirstResponder:(UITapGestureRecognizer*)tap
{
    [_searchText resignFirstResponder];
    
}

-(BOOL)gestureRecognizer:(UIGestureRecognizer *)gestureRecognizer shouldReceiveTouch:(UITouch *)touch

{
    
    if ([NSStringFromClass([touch.view class]) isEqualToString:@"UITableViewCellContentView"]) {//如果当前是tableView
  
        return NO;
        
    }
    
    return YES;
    
}

- (void)textChange:(UITextField*)textField
{
//    [self filterContentForSearchText:textField.text];
}

- (void)textFieldDidBeginEditing:(UITextField *)textField
{
    
}

- (BOOL)textFieldShouldReturn:(UITextField *)textField
{
    [textField resignFirstResponder];
    return YES;
    
}
- (void)ininHeaderView
{
    
    
    _tableHeaderView = [[UIView alloc]initWithFrame:CGRectMake(0, 0, _tableView.frame.size.width, 250)];
    _tableHeaderView.backgroundColor = [UIColor whiteColor];
    
    //定位城市
    UILabel *title1 = [[UILabel alloc]initWithFrame:CGRectMake(15, 0, 160, 30)];
    title1.text = @"当前定位城市：上海";
    title1.font = [UIFont systemFontOfSize:14];
    title1.textColor = Number090909Color;
    [_tableHeaderView addSubview:title1];
    

    UIView *title3bgView =[[UIView alloc]initWithFrame:CGRectMake(0, CGRectGetMaxY(title1.frame), SCREEN_WIDTH, 44)];
    title3bgView.backgroundColor=NumberEDF5FBColor;
    [_tableHeaderView addSubview:title3bgView];

    
    //热门城市
    UILabel *title3 = [[UILabel alloc]initWithFrame:CGRectMake(15, 14, 160, 20)];
    title3.text = @"热门城市";
    title3.backgroundColor = NumberEDF5FBColor;
    title3.textColor = Number666666Color;
    title3.font = [UIFont systemFontOfSize:14];
    [title3bgView addSubview:title3];
    
    
    long row = _arrayHotCity.count/3;
    if (_arrayHotCity.count%3 > 0) {
        row += 1;
    }
    CGFloat hotViewHight = 45*row;
    _hotCityGroupView = [[ButtonGroupView alloc]initWithFrame:CGRectMake(0, CGRectGetMaxY(title3bgView.frame), _tableHeaderView.frame.size.width, hotViewHight)];
    _hotCityGroupView.backgroundColor = NumberEDF5FBColor;
    _hotCityGroupView.delegate = self;
    _hotCityGroupView.columns = 3;
//    _hotCityGroupView.items = [self GetCityDataSoucre:_arrayHotCity];
    [_tableHeaderView addSubview:_hotCityGroupView];
    
    
    _tableHeaderView.frame = CGRectMake(0, 0, _tableView.frame.size.width, _hotCityGroupView.frame.origin.y+_hotCityGroupView.frame.size.height);
    _tableView.tableHeaderView.frame = _tableHeaderView.frame;
    _tableView.tableHeaderView = _tableHeaderView;
    
}

- (NSArray*)GetCityDataSoucre:(NSArray*)ary
{
    NSMutableArray *cityAry = [[NSMutableArray alloc]init];
    for (NSDictionary *hostDict in ary) {
        NSString *dataString = hostDict[@"lableDesc"];
        [cityAry addObject: [CityItem initWithTitleName:dataString]];
        [self.tableView reloadData];
    }
    return cityAry;
}

#pragma mark - 获取城市数据
-(void)getCityData{
//    NSString *path=[[NSBundle mainBundle] pathForResource:@"citydict"
//                                                   ofType:@"plist"];
//    self.cities = [NSMutableDictionary dictionaryWithContentsOfFile:path];
//
    [_keys addObjectsFromArray:[[self.cities allKeys] sortedArrayUsingSelector:@selector(compare:)]];
//
//    DLog(@"keys===%@",self.keys);
//    //添加热门城市
//    NSString *strHot = @"#";
//    [self.keys insertObject:strHot atIndex:0];
//    [self.cities setObject:_arrayHotCity forKey:strHot];
    
    NSArray *allValuesAry = [self.cities allValues];
    for (NSArray*oneAry in allValuesAry) {
        for (NSString *cityName in oneAry) {
           [_arrayCitys addObject:cityName];
        }
    }
    [self.tableView reloadData];
}

#pragma mark - tableView
-(CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section
{
    return 30.0;
}

-(UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section
{
    UIView *bgView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, tableView.frame.size.width, 20)];
    
    bgView.backgroundColor = NumberEDF5FBColor;

    UILabel *titleLabel = [[UILabel alloc] initWithFrame:CGRectMake(13, 0, 250, 30)];
    titleLabel.backgroundColor = [UIColor clearColor];
    titleLabel.textColor = Number666666Color;
    titleLabel.font = [UIFont systemFontOfSize:14];
    
//    UIImageView *line = [[UIImageView alloc]initWithFrame:CGRectMake(0, 19, bgView.frame.size.width, 1)];
//    line.backgroundColor = [UIColor colorWithRed:220/255.0 green:220/255.0 blue:220/255.0 alpha:1];
    
    
    NSString *key = [_keys objectAtIndex:section];

    titleLabel.text = key;
//    [bgView addSubview:line];

    [bgView addSubview:titleLabel];
    
    return bgView;
}

- (NSArray *)sectionIndexTitlesForTableView:(UITableView *)tableView
{
    
    NSMutableArray *indexNumber = [NSMutableArray arrayWithArray:_keys];
//    NSString *strHot = @"#";
//    //添加搜索前的#号
//    [indexNumber insertObject:strHot atIndex:0];
    return indexNumber;
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    // Return the number of sections.
    return [_keys count];
}

- (NSInteger)tableView:(UITableView *)tableView sectionForSectionIndexTitle:(NSString *)title atIndex:(NSInteger)index
{
//    NSLog(@"title = %@",title);
    [self showTipsWithTitle:title];
    
    return index;
}

- (void)showTipsWithTitle:(NSString*)title
{
    
    //获取当前屏幕window
    UIWindow *window = [UIApplication sharedApplication].keyWindow;
    //添加黑色透明背景
//    if (!_bgImageView) {
//        _bgImageView = [[UIImageView alloc]initWithFrame:CGRectMake(0, 0, window.frame.size.width, window.frame.size.height)];
//        _bgImageView.backgroundColor = [UIColor blackColor];
//        _bgImageView.alpha = 0.1;
//        [window addSubview:_bgImageView];
//    }
    if (!_tipsView) {
        //添加字母提示框
        _tipsView = [[UIView alloc]initWithFrame:CGRectMake(0, 0, 80, 80)];
        _tipsView.center = window.center;
        _tipsView.backgroundColor = [UIColor colorWithRed:0.95 green:0.95 blue:0.95 alpha:0.8];
        //设置提示框圆角
        _tipsView.layer.masksToBounds = YES;
        _tipsView.layer.cornerRadius  = _tipsView.frame.size.width/20;
        _tipsView.layer.borderColor   = [UIColor whiteColor].CGColor;
        _tipsView.layer.borderWidth   = 2;
        [window addSubview:_tipsView];
    }
    if (!_tipsLab) {
        //添加提示字母lable
        _tipsLab = [[UILabel alloc]initWithFrame:CGRectMake(0, 0, _tipsView.frame.size.width, _tipsView.frame.size.height)];
        //设置背景为透明
        _tipsLab.backgroundColor = [UIColor clearColor];
        _tipsLab.font = [UIFont boldSystemFontOfSize:50];
        _tipsLab.textAlignment = NSTextAlignmentCenter;
        
        [_tipsView addSubview:_tipsLab];
    }
   _tipsLab.text = title;//设置当前显示字母
    
//    [self performSelector:@selector(hiddenTipsView:) withObject:nil afterDelay:0.3];
//    dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(0.3 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
//        [self hiddenTipsView];
//    });
    
    
    _timer = [NSTimer timerWithTimeInterval:1 target:self selector:@selector(hiddenTipsView) userInfo:nil repeats:NO];
    [[NSRunLoop mainRunLoop] addTimer:_timer forMode:NSRunLoopCommonModes];
    
}

- (void)hiddenTipsView
{
    
    [UIView animateWithDuration:0.2 animations:^{
        _bgImageView.alpha = 0;
        _tipsView.alpha = 0;
    } completion:^(BOOL finished) {
        [_bgImageView removeFromSuperview];
        [_tipsView removeFromSuperview];
         _bgImageView = nil;
         _tipsLab     = nil;
         _tipsView    = nil;
    }];
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    // Return the number of rows in the section.
    NSString *key = [_keys objectAtIndex:section];
    NSArray *citySection = [_cities objectForKey:key];
    return [citySection count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    
    if ([_tableView respondsToSelector:@selector(setSectionIndexColor:)]) {
        _tableView.sectionIndexBackgroundColor = KSectionIndexBackgroundColor;  //修改索引试图未选中时的背景颜色
        _tableView.sectionIndexTrackingBackgroundColor = kSectionIndexTrackingBackgroundColor;//修改索引试图选中时的背景颜色
        _tableView.sectionIndexColor = kSectionIndexColor;//修改索引试图字体颜色
    }
    
    
    static NSString *CellIdentifier = @"Cell";
    
    NSString *key = [_keys objectAtIndex:indexPath.section];
    
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    if (cell == nil) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:CellIdentifier] ;
        cell.backgroundColor = [UIColor clearColor];
        cell.contentView.backgroundColor = [UIColor clearColor];
        cell.selectionStyle = UITableViewCellSelectionStyleNone;
        [cell.textLabel setTextColor:[UIColor blackColor]];
        cell.textLabel.font = [UIFont systemFontOfSize:18];
    }
    cell.textLabel.text = [[[_cities objectForKey:key] objectAtIndex:indexPath.row] objectForKey:@"areaName"];
    cell.textLabel.textColor = Number090909Color;
    cell.textLabel.font = FontSize(16);
    return cell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(nonnull NSIndexPath *)indexPath
{
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
    
    NSString *key = [_keys objectAtIndex:indexPath.section];
    NSString *cityName = [[[_cities objectForKey:key] objectAtIndex:indexPath.row] objectForKey:@"areaName"];
    NSString *uuid = [[[_cities objectForKey:key] objectAtIndex:indexPath.row] objectForKey:@"uuid"];
    DLog(@"排序城市==%@",uuid);

    [[NSUserDefaults standardUserDefaults]setObject:cityName forKey:changeCityName];
    [[NSUserDefaults standardUserDefaults]setObject:uuid forKey:changeCityUuid];

    if (_delegate) {
        [_delegate didClickedWithCityName:cityName];
    }
    
    [self.navigationController popViewControllerAnimated:YES];
//    [self dismissViewControllerAnimated:YES completion:nil];
    
}


-(void)ButtonGroupView:(ButtonGroupView *)buttonGroupView didClickedItem:(CityButton *)item
{
    if (_delegate) {
       
        [_delegate didClickedWithCityName:item.cityItem.titleName];
    }

    NSDictionary *hotDict = self.arrayHotCity[item.index];
    NSString *uuidStr=hotDict[@"lableCode"];
    NSString *cityName=hotDict[@"lableDesc"];

    [[NSUserDefaults standardUserDefaults]setObject:cityName forKey:changeCityName];
    [[NSUserDefaults standardUserDefaults]setObject:uuidStr forKey:changeCityUuid];
    [self.navigationController popViewControllerAnimated:YES];
}




NSInteger cityNameSort(id str1, id str2, void *context)
{
    NSString *string1 = (NSString*)str1;
    NSString *string2 = (NSString*)str2;
    
    return  [string1 localizedCompare:string2];
}
/**
 *  通过搜索条件过滤得到搜索结果
 *
 *  @param searchText 关键词
 *  @param scope      范围
 */
- (void)filterContentForSearchText:(NSString*)searchText {
    
//    NSLog(@"搜索结果111:%@",_arrayCitys) ;

    
    if (searchText.length > 0) {
        _searchResultDic = nil;
        _searchResultDic = [[NSMutableDictionary alloc]init];
        //搜索数组中是否含有关键字
        NSArray *resultAry  = [ZYPinYinSearch searchWithOriginalArray:_arrayCitys andSearchText:searchText andSearchByPropertyName:@""];
        
        NSLog(@"搜索结果:%@",resultAry) ;

        for (NSString*city in resultAry) {
            //获取字符串拼音首字母并转为大写
            NSString *pinYinHead = [PinYinForObjc chineseConvertToPinYinHead:city].uppercaseString;
            NSString *firstHeadPinYin = [pinYinHead substringToIndex:1]; //拿到字符串第一个字的首字母
            //        NSLog(@"pinYin = %@",firstHeadPinYin);
            
            
            NSMutableArray *cityAry = [NSMutableArray arrayWithArray:[_searchResultDic objectForKey:firstHeadPinYin]]; //取出首字母数组
            
            if (cityAry != nil) {
                
                [cityAry addObject:city];
                
                NSArray *sortCityArr = [cityAry sortedArrayUsingFunction:cityNameSort context:NULL];
                [_searchResultDic setObject:sortCityArr forKey:firstHeadPinYin];
                
            }else
            {
                cityAry= [[NSMutableArray alloc]init];
                [cityAry addObject:city];
                NSArray *sortCityArr = [cityAry sortedArrayUsingFunction:cityNameSort context:NULL];
                [_searchResultDic setObject:sortCityArr forKey:firstHeadPinYin];
            }
            
            
            
        }
        //    NSLog(@"dic = %@",dic);

        if (resultAry.count>0) {
            _cities = nil;
            _cities = _searchResultDic;
            [_keys removeAllObjects];
            //按字母升序排列
            [_keys addObjectsFromArray:[[self.cities allKeys] sortedArrayUsingSelector:@selector(compare:)]] ;
            _tableView.tableHeaderView = nil;
            [_tableView reloadData];
        }

    }else
    {
        //当字符串清空时 回到初始状态
        _cities = nil;
         [_keys removeAllObjects];
        [_arrayCitys removeAllObjects];
        [self getCityData];
        _tableView.tableHeaderView = _tableHeaderView;
        [_tableView reloadData];
    }
    
   
}


/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
