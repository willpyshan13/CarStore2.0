//
//  UpdateMaintenanceVController.m
//  CarOwnerProject
//
//  Created by apple on 2021/3/19.
//

#import "UpdateMaintenanceVController.h"
#import "WxbyTypeCollectionViewCell.h"
#import "MallBannerView.h"
#import "WxbySortCollectionReusableView.h"
#import "WxGoodsCollectionViewCell.h"
#import "ScrollSortBarView.h"
#import "QueryTreeListModel.h"
#import "QueryTreeListSubListModel.h"

#import "SelectRidersAlertView.h"
//推荐门店
#import "StoreQueryListWXModel.h"


@interface UpdateMaintenanceVController ()<UICollectionViewDataSource,UICollectionViewDelegate,UICollectionViewDelegateFlowLayout,SelectRidersAlertViewDelegate>
@property (nonatomic,strong)UIView *lineView;
@property (nonatomic, strong) UICollectionView *collectionView;
@property (nonatomic,strong)MallBannerView *bannerView;
@property(nonatomic,strong) NSMutableArray *headTittleDataArray;
@property (nonatomic,strong)NSMutableArray *selListArray;//例如太想洗车小分类
@property(nonatomic,strong) NSMutableArray *scrollSortDataArray;

@property(nonatomic,strong) NSMutableArray *storeDataArray;

@property (nonatomic,copy)NSString *levelOne;//店铺分类

@property (nonatomic,assign)NSInteger storeTypeTag;

@property (nonatomic,strong)ScrollSortBarView *scrollSortVIew;//店铺分类
@property (nonatomic,strong)ScrollSortBarView *barView;

@end

@implementation UpdateMaintenanceVController
{
    int pageSize;
}

static NSString * const WxbyTypeCollectionViewCelliIdentify = @"WxbyTypeCollectionViewCell";//回答
static NSString * const SupplementaryViewFooterIdentify = @"SupplementaryViewFooterIdentify";
static NSString * const HeadBannerIdentify = @"HeadBannerIdentify";//轮播图
static NSString * const SupplementaryViewHeaderIdentify = @"SupplementaryViewHeaderIdentify";
static NSString * const WxGoodsCollectionViewCellIdentify = @"WxGoodsCollectionViewCell";


- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    self.navigationItem.title = @"维修保养";
    [self.navigationController.navigationBar setTitleTextAttributes:
         @{NSFontAttributeName:[UIFont systemFontOfSize:18.0f],
           NSForegroundColorAttributeName:Number090909Color}];
    [self.view addSubview:self.lineView];
    [self.view addSubview:self.collectionView];
    pageSize = 10;
    
    self.storeTypeTag = 0;
    self.levelOne=@"";
    
    [self requestQueryTreeList];
    
    [self requestQueryStoreListWithPage:1];
    
  
    
    
    _collectionView.mj_header = [MJRefreshNormalHeader headerWithRefreshingBlock:^{ //下拉刷新
        [self requestGoodHeaderList];
    }];
    _collectionView.mj_footer = [MJRefreshAutoNormalFooter footerWithRefreshingTarget:self refreshingAction:@selector(requestGoodFooterList)];
}
#pragma mark== 上拉加载更多
-(void)requestGoodFooterList{
    NSInteger count = _storeDataArray.count;
    NSInteger tmp = (count%pageSize == 0)?count/pageSize:count/pageSize+1;
    tmp += 1;
    [self requestQueryStoreListWithPage:(int)tmp];
}

#pragma mark == 下拉刷新
-(void)requestGoodHeaderList{
    
    [self.storeDataArray removeAllObjects];
    [self requestQueryStoreListWithPage:1];
    
}



-(void)requestQueryTreeList
{
    NSDictionary *pageBoDict=@{
        
    };
    NSDictionary *headerDict =@{
        @"token":[UserInfo getUserInfo].token,
    };
    //查询父节点所有分组，根节点传-1
    NSString *urlStr = [NSString stringWithFormat:@"%@-1",queryTreeListApi];
    [HTTPManager getRequestWithUrl:urlStr parameters:pageBoDict header:headerDict progress:^(NSProgress * _Nonnull uploadProgress) {
        
    } success:^(NSDictionary * _Nonnull responseObject) {
        DLog(@"维修保养新版全部分类1responseObject===%@",responseObject);
        
        NSArray *dataArr = responseObject[@"data"];
                
        NSArray *commendArr = [QueryTreeListModel mj_objectArrayWithKeyValuesArray:dataArr];
        QueryTreeListModel *caseModel =[[QueryTreeListModel alloc]init];
        caseModel.groupName = @"推荐";
        caseModel.uuid = @"";
        [self.scrollSortDataArray addObject:caseModel];
        
        for (int i=0; i<commendArr.count; i++) {
            QueryTreeListModel *caseModel = commendArr[i];
            if (![caseModel.uuid isEqualToString:@"1006"]) {
                [self.headTittleDataArray addObject:caseModel];
                [self.scrollSortDataArray addObject:caseModel];
            }
        }
        [self.collectionView reloadData];
        
    } other:^(NSDictionary * _Nonnull responseObject) {
        
    } failure:^(NSError * _Nonnull error) {
        
    }];
}
#pragma mark == 对应店铺
-(void)requestQueryStoreListWithPage:(NSInteger)page
{
    DLog(@"levelOne====%@",self.levelOne);
    NSDictionary *pageBoDict=@{
        @"levelOne":self.levelOne,
        @"pageNum":@(page),
        @"pageSize":@(pageSize),
    };
    NSDictionary *headerDict =@{
        @"token":[UserInfo getUserInfo].token,
    };
    //查询父节点所有分组，根节点传-1
    NSString *urlStr = [NSString stringWithFormat:@"%@",storeQueryStoreListApi];
    [HTTPManager postRequestWithUrl:urlStr parameters:pageBoDict header:headerDict progress:^(NSProgress * _Nonnull uploadProgress) {
        
    } success:^(NSDictionary * _Nonnull responseObject) {
        DLog(@"维修保养新版全部分类1responseObject===%@",responseObject);
//        if (page==1) {
//
//        }
        
        NSArray *dataArr = responseObject[@"data"];
        NSArray *commendArr = [StoreQueryListWXModel mj_objectArrayWithKeyValuesArray:dataArr];
        
        for (int i=0; i<commendArr.count; i++) {
            StoreQueryListWXModel *caseModel = commendArr[i];
            [self.storeDataArray addObject:caseModel];
        }
        
        if (dataArr.count<10) {
            self.collectionView.mj_footer.hidden=YES;
        }else
        {
            self.collectionView.mj_footer.hidden=NO;
        }
        [self.collectionView reloadData];
                
        [self.collectionView.mj_header endRefreshing];
        [self.collectionView.mj_footer endRefreshing];
        
        
    } other:^(NSDictionary * _Nonnull responseObject) {
        
    } failure:^(NSError * _Nonnull error) {
        
    }];
}



#pragma mark - UICollectionViewDataSource method
- (NSInteger)numberOfSectionsInCollectionView:(UICollectionView *)collectionView{
    return 3;
}

- (NSInteger)collectionView:(UICollectionView *)collectionView numberOfItemsInSection:(NSInteger)section
{
    if (section==0)
    {
        return self.headTittleDataArray.count;
    }
    if (section==1) {
        return 1;
    }
    return self.storeDataArray.count;
   
}
- (__kindof UICollectionViewCell *)collectionView:(UICollectionView *)collectionView cellForItemAtIndexPath:(NSIndexPath *)indexPath{
   
    if (indexPath.section==0) {
        WxbyTypeCollectionViewCell *cell = [collectionView dequeueReusableCellWithReuseIdentifier:WxbyTypeCollectionViewCelliIdentify forIndexPath:indexPath];
        QueryTreeListModel *model = self.headTittleDataArray[indexPath.row];
        [cell setModel:model];
        switch (indexPath.row) {
            case 0:
            {
                cell.typeImg.image = kImageNamed(@"wxby_wcmr_icon");
            }
                break;
            case 1:
            {
                cell.typeImg.image = kImageNamed(@"wxby_qcwx_icon");

            }
                break;
            case 2:
            {
                cell.typeImg.image = kImageNamed(@"wxby_qcby_icon");

            }
                break;
            case 3:
            {
                cell.typeImg.image = kImageNamed(@"wxby_txxc_icon");

            }
                break;
            case 4:
            {
                cell.typeImg.image = kImageNamed(@"wxby_smfw_icon");

            }
                break;
            case 5:
            {
                cell.typeImg.image = kImageNamed(@"wxby_bxyb_icon");

            }
                break;
//            case 6:
//            {
//                cell.typeImg.image = kImageNamed(@"wxby_qctm_icon");
//
//            }
//                break;
//            case 7:
//            {
//                cell.typeImg.image = kImageNamed(@"wxby_qcjx_icon");
//
//            }
//                break;
                
            default:
                break;
        }
        return cell;
    }
    else if (indexPath.section==1)
    {
        UICollectionViewCell *cell = [collectionView dequeueReusableCellWithReuseIdentifier:HeadBannerIdentify forIndexPath:indexPath];
        [cell.contentView.subviews enumerateObjectsUsingBlock:^(__kindof UIView * _Nonnull obj, NSUInteger idx, BOOL * _Nonnull stop) {
            UIView *view = (UIView *)obj;
            [view removeFromSuperview];
        }];
        [cell addSubview:self.bannerView];
        return cell;
    }
    WxGoodsCollectionViewCell *cell = [collectionView dequeueReusableCellWithReuseIdentifier:WxGoodsCollectionViewCellIdentify forIndexPath:indexPath];
    StoreQueryListWXModel *model = self.storeDataArray[indexPath.row];
    DLog(@"storeDataArray=====%@",self.storeDataArray);
    [cell setModel:model];
    return cell;
    
}

-(void)collectionView:(UICollectionView *)collectionView didSelectItemAtIndexPath:(NSIndexPath *)indexPath
{
    //选择小分类
    if (indexPath.section==0) {
//        QueryTreeListModel *model = self.headTittleDataArray[indexPath.row];
//        DLog(@"uuid====%@", model.uuid);
//        [self requestQueryListByTypeApiWithName:model.uuid];
        
        
        SummaryH5ViewController *shopCenterVC =[[SummaryH5ViewController alloc]init];
        NSString *areaUuid = [[NSUserDefaults standardUserDefaults]objectForKey:changeCityUuid];
        UserModel *userM =[UserInfo getUserInfo];
        NSString *latStr = SafeValue([NSString stringWithFormat:@"%@",userM.latitude]);
        NSString *longStr = SafeValue([NSString stringWithFormat:@"%@",userM.longitude]);
        DLog(@"latitude=====%@  longitude=====%@",userM.latitude,userM.longitude);
        if ([SafeValue(areaUuid)isEqualToString:@""])
        {
            if ([latStr isEqualToString:@""]&&[longStr isEqualToString:@""])
            {
                areaUuid=@"793";
                shopCenterVC.url = [NSString stringWithFormat:@"%@%@?areaUuid=%@",HOSTH5_IP,storeH5url,areaUuid];                
            }
            else
            {
                shopCenterVC.url = [NSString stringWithFormat:@"%@%@?lng=%@&lat=%@",HOSTH5_IP,storeH5url,longStr,latStr];
            }
            
        }else
        {
            shopCenterVC.url = [NSString stringWithFormat:@"%@%@?areaUuid=%@",HOSTH5_IP,storeH5url,areaUuid];
        }
        
        DLog(@"shopCenterVC====%@",shopCenterVC.url);
        shopCenterVC.hidesBottomBarWhenPushed = YES;
        [self.navigationController pushViewController:shopCenterVC animated:YES];
    }
    else if (indexPath.section==2)
    {
        StoreQueryListWXModel *model = self.storeDataArray[indexPath.row];
        
        SummaryH5ViewController *shopCenterVC =[[SummaryH5ViewController alloc]init];
        shopCenterVC.url = [NSString stringWithFormat:@"%@%@?uuid=%@",HOSTH5_IP,storeDetail,model.uuid];
        DLog(@"caseH5urlcaseH5url====%@",shopCenterVC.url);
     
        shopCenterVC.hidesBottomBarWhenPushed = YES;
        [self.navigationController pushViewController:shopCenterVC animated:YES];
    }
}
-(void)requestQueryListByTypeApiWithName:(NSString *)uuid
{
    NSDictionary *pageBoDict=@{
    };
    NSDictionary *headerDict =@{
        @"token":[UserInfo getUserInfo].token,
    };
    NSString *urlStr = [NSString stringWithFormat:@"%@/%@",queryListByParentApi,uuid];
    [HTTPManager getRequestWithUrl:urlStr parameters:pageBoDict header:headerDict progress:^(NSProgress * _Nonnull uploadProgress) {
        
    } success:^(NSDictionary * _Nonnull responseObject) {
        [self.selListArray removeAllObjects];
        DLog(@"小分类---responseObject====%@",responseObject);
        NSArray *dataArr = responseObject[@"data"];
        NSArray *commendArr = [QueryTreeListModel mj_objectArrayWithKeyValuesArray:dataArr];
     
        for (int i=0; i<commendArr.count; i++)
        {
            QueryTreeListModel *caseModel = commendArr[i];
            [self.selListArray addObject:caseModel];
        }
        SelectRidersAlertView *xitongView =[SelectRidersAlertView initSelectRidersAlertView];
        xitongView.type=3;
        [xitongView configFrame:[UIScreen mainScreen].bounds];
        xitongView.couponArr = self.selListArray;
        xitongView.delegate = self;
        [xitongView showSelectRidersAlertViewOn:self.view.window];
        
    } other:^(NSDictionary * _Nonnull responseObject) {
        [AppUtils showErrorMessage:responseObject[@"msg"]];
        
    } failure:^(NSError * _Nonnull error) {
        
    }];
}
#pragma mark == 进入h5
/*
 
-(void)getSelectQueryTreeList:(QueryTreeListModel *)model
{
    DLog(@"uuid======%@",model.uuid);
    SummaryH5ViewController *shopCenterVC =[[SummaryH5ViewController alloc]init];
    NSString *areaUuid = [[NSUserDefaults standardUserDefaults]objectForKey:changeCityUuid];
    if ([SafeValue(areaUuid)isEqualToString:@""]) {
        areaUuid=@"793";
    }
    DLog(@"areaUuid===%@",areaUuid);
    
    shopCenterVC.url = [NSString stringWithFormat:@"%@%@?areaUuid=%@&parentUuid=%@&currUuid=%@",HOSTH5_IP,storeH5url,areaUuid,model.parentId,model.uuid];
    DLog(@"shopCenterVC====%@",shopCenterVC.url);
    shopCenterVC.hidesBottomBarWhenPushed = YES;
    [self.navigationController pushViewController:shopCenterVC animated:YES];
}**/


- (CGSize)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout*)collectionViewLayout referenceSizeForHeaderInSection:(NSInteger)section
{
    if (section==2) {
        return CGSizeMake(0, 50);//需购买商品区头
    }
    return CGSizeMake(SCREEN_WIDTH, 0);//需购买商品区头
}
- (CGSize)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout*)collectionViewLayout referenceSizeForFooterInSection:(NSInteger)section{ //区脚高度
    
    if (section==1) {
        return CGSizeMake(0, 10);//需购买商品区头
    }
    return (CGSize){SCREEN_WIDTH,0};
//    return CGSizeZero;
}
//列间距
- (CGFloat)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout*)collectionViewLayout minimumInteritemSpacingForSectionAtIndex:(NSInteger)section{
    return 10;
}
//行间距
- (CGFloat)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout*)collectionViewLayout minimumLineSpacingForSectionAtIndex:(NSInteger)section{
    return 2;
}

#pragma mark - UICollectionViewDelegateFlowLayout method
- (CGSize)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout*)collectionViewLayout sizeForItemAtIndexPath:(NSIndexPath *)indexPath{
    
    if (indexPath.section==0) {
        CGFloat margin = 25;
        CGFloat width = (SCREEN_WIDTH-margin*4)/3;
        CGFloat height = 96;
        return (CGSize){width,height};
    }
    if (indexPath.section==1) {
        CGFloat banW=self.view.frame.size.width-20;
        return (CGSize){SCREEN_WIDTH,banW*0.31};
    }
    
    CGFloat width = SCREEN_WIDTH;
    CGFloat height = 108;
    return (CGSize){width,height};
}
- (UIEdgeInsets)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout*)collectionViewLayout insetForSectionAtIndex:(NSInteger)section
{
    return UIEdgeInsetsMake(8, 8, 8, 6);
    
//        return UIEdgeInsetsZero;
}

- (UICollectionReusableView *)collectionView:(UICollectionView *)collectionView viewForSupplementaryElementOfKind:(NSString *)kind atIndexPath:(NSIndexPath *)indexPath{
    if([kind isEqualToString:UICollectionElementKindSectionHeader])
    {
        WxbySortCollectionReusableView *supplementaryView = [collectionView dequeueReusableSupplementaryViewOfKind:UICollectionElementKindSectionHeader withReuseIdentifier:SupplementaryViewHeaderIdentify forIndexPath:indexPath];
//        supplementaryView.backgroundColor=[UIColor blueColor];
//        NSArray *sortArr =@[@"推荐",@"太想洗车",@"汽车维修",@"汽车保养",@"汽车维修"];
//        NSArray *sortArr =self.scrollSortDataArray;
//        [supplementaryView.sortArr removeAllObjects];
        
//        for (WxbySortCollectionReusableView *view in self.collectionView.subviews) {
//            [view removeFromSuperview];
//        }
        supplementaryView.sortArr = self.scrollSortDataArray;
        supplementaryView.selectItemBlock = ^(id  _Nonnull obj, NSInteger index)
        {
            QueryTreeListModel *model= obj;
            self.levelOne = model.uuid;
            [self.storeDataArray removeAllObjects];            
            [self requestQueryStoreListWithPage:1];
        };
        
//        ScrollSortBarView *barView =[[ScrollSortBarView alloc]initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 50) item:sortArr];
//        barView.backgroundColor =[UIColor redColor];
//        [barView showIndex:@(self.storeTypeTag)];
//        barView.selectItemBlock = ^(id obj, NSInteger tag){
//            DLog(@"汽车维修tag===%ld",tag);
//            self.storeTypeTag = tag;
//            QueryTreeListModel *model= obj;
//            DLog(@"groupName====%@",model.groupName);
//            self.levelOne = model.uuid;
//            [self.storeDataArray removeAllObjects];
//            [self requestQueryStoreListWithPage:1];
//        };
//        [supplementaryView addSubview:barView];

        return supplementaryView;
    }
    else if([kind isEqualToString:UICollectionElementKindSectionFooter]) {
        UICollectionReusableView *supplementaryView = [collectionView dequeueReusableSupplementaryViewOfKind:UICollectionElementKindSectionFooter withReuseIdentifier:SupplementaryViewFooterIdentify forIndexPath:indexPath];
        if (indexPath.section ==1) {
            supplementaryView.backgroundColor =NumberF7F8F9Color;//RGBColor(237, 238, 239)
            
        }
        else
        {
            supplementaryView.backgroundColor =[UIColor whiteColor] ;
        }
        return supplementaryView;
    }
    return nil;
}



#pragma mark == 横线
-(UIView *)lineView
{
    if (!_lineView) {
        _lineView =[[UIView alloc]initWithFrame:CGRectMake(0, NavigationBar_Height, SCREEN_WIDTH, 1)];
        _lineView.backgroundColor = NumberF1F1F1Color;
    }
    return _lineView;
}
-(MallBannerView *)bannerView
{
    if (!_bannerView)
    {
        CGFloat banW=self.view.frame.size.width-20;
        _bannerView =[[MallBannerView alloc]initWithFrame:CGRectMake(10, 10,banW, banW*0.31) imageArray:@[@"qichebaoyang_banner_icon",@"qichebaoyang_banner_icon",@"qichebaoyang_banner_icon"] barnnerType:BannerViewTypeUrlPicture];
        _bannerView.clipsToBounds = YES;
        _bannerView.layer.cornerRadius = 10.0f;
    }
    return _bannerView;
}
-(UICollectionView *)collectionView
{
    if (!_collectionView) {
        UICollectionViewFlowLayout *layout = [[UICollectionViewFlowLayout alloc] init];
        self.collectionView = [[UICollectionView alloc] initWithFrame:(CGRect){0,0,self.view.width,SCREEN_HEIGHT-BottomTab_Height} collectionViewLayout:layout];
        self.collectionView.dataSource = self;
        self.collectionView.delegate = self;
        self.collectionView.backgroundColor = [UIColor whiteColor];
        self.collectionView.showsVerticalScrollIndicator = NO;
        [self.collectionView registerNib:[UINib nibWithNibName:@"WxbyTypeCollectionViewCell" bundle:nil] forCellWithReuseIdentifier:WxbyTypeCollectionViewCelliIdentify];
        
        [self.collectionView registerNib:[UINib nibWithNibName:@"WxGoodsCollectionViewCell" bundle:nil] forCellWithReuseIdentifier:WxGoodsCollectionViewCellIdentify];

        
        [self.collectionView registerClass:[WxbySortCollectionReusableView class]  forSupplementaryViewOfKind:UICollectionElementKindSectionHeader withReuseIdentifier:SupplementaryViewHeaderIdentify];//产品区头
        [self.collectionView registerClass:[UICollectionReusableView class] forSupplementaryViewOfKind:UICollectionElementKindSectionFooter withReuseIdentifier:SupplementaryViewFooterIdentify];
        [self.collectionView registerClass:[UICollectionViewCell class] forCellWithReuseIdentifier:HeadBannerIdentify];

    }
    
    return _collectionView;
}

-(NSMutableArray *)headTittleDataArray{
    
    if (!_headTittleDataArray) {
        _headTittleDataArray = [[NSMutableArray alloc]init];
    }
    return _headTittleDataArray;
}
-(NSMutableArray *)selListArray{
    
    if (!_selListArray) {
        _selListArray = [[NSMutableArray alloc]init];
    }
    return _selListArray;
}
//scrollSortDataArray
-(NSMutableArray *)scrollSortDataArray{
    
    if (!_scrollSortDataArray) {
        _scrollSortDataArray = [[NSMutableArray alloc]init];
    }
    return _scrollSortDataArray;
}

-(NSMutableArray *)storeDataArray{
    
    if (!_storeDataArray) {
        _storeDataArray = [[NSMutableArray alloc]init];
    }
    return _storeDataArray;
}

- (ScrollSortBarView *)scrollSortVIew{
    if (!_scrollSortVIew)
    {
        _scrollSortVIew = [[ScrollSortBarView alloc] initWithFrame:(CGRect){0,0,SCREEN_WIDTH,50} item:self.scrollSortDataArray];
        [_scrollSortVIew showIndex:@3];
        WeakSelf(self)
        _scrollSortVIew.selectItemBlock = ^(id obj, NSInteger tag){
            StrongSelf(self)
            DLog(@"汽车维修tag===%ld",tag);
            QueryTreeListModel *model= obj;
            DLog(@"groupName====%@",model.groupName);
            
            self.levelOne = model.uuid;
            
            [self.storeDataArray removeAllObjects];
            
//            [self requestQueryStoreListWithPage:1];
            
        };
    }
    return _scrollSortVIew;
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
