//
//  MaintenanceViewController.m
//  CarOwnerProject
//
//  Created by apple on 2021/3/18.
//

#import "MaintenanceViewController.h"
#import "EduCollectionViewCell.h"
#import "StoreHeadView.h"
#import "MallBannerView.h"
#import "SelectRidersAlertView.h"
#import "SelectRidersTModel.h"//筛选品牌、系统
#import "SelectVehicleConfigModel.h"//车型
#import "SelectRidersCarAlertView.h"
#import "MaintainInfoModel.h"//养护信心

@interface MaintenanceViewController ()<UICollectionViewDataSource,UICollectionViewDelegate,UICollectionViewDelegateFlowLayout,SelectRidersAlertViewDelegate,SelectRidersCarAlertViewDelegate>
@property (nonatomic, strong) UICollectionView *collectionView;
@property (nonatomic,strong)SelectRidersCarAlertView*selCarView;//选择车型

//品牌、
@property (nonatomic,strong)NSMutableArray *pinpaiListArray;
//车型
@property (nonatomic,strong)NSMutableArray *selCarListArray;
//系统
@property (nonatomic,strong)NSMutableArray *selListArray;


@property (nonatomic,strong)EmptyView *empView;
@property (nonatomic,strong)MallBannerView *bannerView;
@property (nonatomic,copy)NSString *systemStr;//系统
@property (nonatomic,copy)NSString *brandStr;//品牌
@property (nonatomic,copy)NSString *modelStr;//车型

@property (nonatomic,assign)NSInteger type;//1、品牌 2、车型 3、系统
@property (nonatomic,copy)NSString *attachSys;//系统
@property (nonatomic,copy)NSString *brandUuid;//品牌
@property (nonatomic,copy)NSString *carmodel;//车型
@property (nonatomic,strong)StoreHeadView *supplementaryView;

@property (nonatomic,strong)NSMutableArray *listArray;


@end

@implementation MaintenanceViewController
{
    int pageSize;
}

static NSString * const EduCollectionViewCelliIdentify = @"EduCollectionViewCell";//回答
static NSString * const SupplementaryViewFooterIdentify = @"SupplementaryViewFooterIdentify";
static NSString * const HeadBannerIdentify = @"HeadBannerIdentify";//轮播图
static NSString * const SupplementaryViewHeaderIdentify = @"SupplementaryViewHeaderIdentify";

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    [self.view addSubview:self.collectionView];
//    self.view.backgroundColor = NumberEDF5FBColor;
    
    self.attachSys=@"";
    self.brandUuid=@"";
    self.carmodel=@"";
    
    self.systemStr=@"全部系统 ";
    self.brandStr=@"全部品牌 ";
    self.modelStr=@"全部车型 ";

    self.type = 0;
    
    pageSize = 10;
    
    [self requestQueryCaseForTechnicianListWithPage:1 attachSys:self.attachSys brandUuid:self.brandUuid carModel:self.carmodel];

    
    _collectionView.mj_header = [MJRefreshNormalHeader headerWithRefreshingBlock:^{ //下拉刷新
        [self requestGoodHeaderList];
    }];
    
    _collectionView.mj_footer = [MJRefreshAutoNormalFooter footerWithRefreshingTarget:self refreshingAction:@selector(requestGoodFooterList)];
}

#pragma mark== 上拉加载更多
-(void)requestGoodFooterList{
    NSInteger count = _listArray.count;
    NSInteger tmp = (count%pageSize == 0)?count/pageSize:count/pageSize+1;
    tmp += 1;
    [self requestQueryCaseForTechnicianListWithPage:(int)tmp attachSys:self.attachSys brandUuid:self.brandUuid carModel:self.carmodel];
}
#pragma mark == 下拉刷新
-(void)requestGoodHeaderList{
    
    
    [self requestQueryCaseForTechnicianListWithPage:1 attachSys:self.attachSys brandUuid:self.brandUuid carModel:self.carmodel];
    [self.collectionView.mj_header endRefreshing];
}

-(void)requestQueryCaseForTechnicianListWithPage:(NSInteger)page attachSys:(NSString *)attachSys brandUuid:(NSString *)brandUuid carModel:(NSString *)carmodel
{
    NSDictionary *pageBoDict=@{
        @"pageNum":@(page),
        @"pageSize":@(pageSize),
        @"attachSys":attachSys,//所属系统 ,
        @"brandUuid":brandUuid,//品牌uuid ,
        @"model":carmodel,//车型 ,
        @"maintainCheckSts":@(1),
    };
    DLog(@"pageBoDict===%@",pageBoDict);
    NSDictionary *headerDict =@{
        @"token":[UserInfo getUserInfo].token,
    };
    NSString *urlStr = [NSString stringWithFormat:@"%@",maintainListApi];
    [HTTPManager postRequestWithUrl:urlStr parameters:pageBoDict header:headerDict progress:^(NSProgress * _Nonnull uploadProgress) {
        
    } success:^(NSDictionary * _Nonnull responseObject) {
        DLog(@"养护信息responseObject===%@",responseObject);
        
        if (page==1) {
            [self.listArray removeAllObjects];
        }
        NSArray *dataArr = responseObject[@"data"];
        NSArray *commendArr = [MaintainInfoModel mj_objectArrayWithKeyValuesArray:dataArr];
        DLog(@"listArra===%lucommendArr===%lu",(unsigned long)dataArr.count,(unsigned long)commendArr.count);

        
        for (int i=0; i<commendArr.count; i++) {
            MaintainInfoModel *caseModel = commendArr[i];

            [self.listArray addObject:caseModel];
        }
        DLog(@"listArra===%lu",(unsigned long)self.listArray.count);
        if (dataArr.count<10) {
            self.collectionView.mj_footer.hidden=YES;
        }else
        {
            self.collectionView.mj_footer.hidden=NO;
        }
        [self.collectionView reloadData];
        [self.collectionView.mj_footer endRefreshing];
        
    } other:^(NSDictionary * _Nonnull responseObject) {
        
    } failure:^(NSError * _Nonnull error) {
        
    }];
}


#pragma mark - UICollectionViewDataSource method
- (NSInteger)numberOfSectionsInCollectionView:(UICollectionView *)collectionView{
    return 2;
}

- (NSInteger)collectionView:(UICollectionView *)collectionView numberOfItemsInSection:(NSInteger)section
{
    if (section==0) {
        return 1;
    }
    return self.listArray.count;
}
- (__kindof UICollectionViewCell *)collectionView:(UICollectionView *)collectionView cellForItemAtIndexPath:(NSIndexPath *)indexPath{
   
    if (indexPath.section==0) {
        UICollectionViewCell *cell = [collectionView dequeueReusableCellWithReuseIdentifier:HeadBannerIdentify forIndexPath:indexPath];
        [cell.contentView.subviews enumerateObjectsUsingBlock:^(__kindof UIView * _Nonnull obj, NSUInteger idx, BOOL * _Nonnull stop) {
            UIView *view = (UIView *)obj;
            [view removeFromSuperview];
        }];
        [cell addSubview:self.bannerView];
        return cell;
    }
    
    EduCollectionViewCell *cell = [collectionView dequeueReusableCellWithReuseIdentifier:EduCollectionViewCelliIdentify forIndexPath:indexPath];
    MaintainInfoModel *model = self.listArray[indexPath.row];
    [cell setModel:model];
//    cell.backgroundColor=[UIColor blueColor];
    
    return cell;
}

-(void)collectionView:(UICollectionView *)collectionView didSelectItemAtIndexPath:(NSIndexPath *)indexPath
{
    if (indexPath.section==1) {
        SummaryH5ViewController *shopCenterVC =[[SummaryH5ViewController alloc]init];
        MaintainInfoModel *model = self.listArray[indexPath.row];
        shopCenterVC.url = [NSString stringWithFormat:@"%@%@?uuid=%@",HOSTH5_IP,huliH5url,model.uuid];
        DLog(@"caseH5urlcaseH5url====%@",shopCenterVC.url);
        shopCenterVC.hidesBottomBarWhenPushed = YES;
        [self.navigationController pushViewController:shopCenterVC animated:YES];
    }
    
}

#pragma mark - UICollectionViewDelegateFlowLayout method
- (CGSize)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout*)collectionViewLayout sizeForItemAtIndexPath:(NSIndexPath *)indexPath{
    
    if (indexPath.section==0) {
        CGFloat banW=self.view.frame.size.width-20;

        return (CGSize){SCREEN_WIDTH,banW*0.42};
    }
    CGFloat margin = 7;
    CGFloat width = (SCREEN_WIDTH-margin*3)/3;
    CGFloat height = AdaptAllScreen(120);

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
            StoreHeadView *supplementaryView = [collectionView dequeueReusableSupplementaryViewOfKind:UICollectionElementKindSectionHeader withReuseIdentifier:SupplementaryViewHeaderIdentify forIndexPath:indexPath];
//            supplementaryView.titleStr  =@"最新培训课程";
            
            [supplementaryView.pinpanBtn setTitle:self.brandStr forState:UIControlStateNormal];
            [supplementaryView.chexingBtn setTitle:self.modelStr forState:UIControlStateNormal];
            [supplementaryView.xitongBtn setTitle:self.systemStr forState:UIControlStateNormal];

            supplementaryView.selectTypeListBlock = ^(id obj, NSInteger type)
            {
                UIButton *btn = obj;
                //品牌车型一个接口
                if (btn.tag==66)
                {
                    DLog(@"品牌");
                    self.type =1;
                    [self requestPinPai];
                }
                else if (btn.tag==67)
                {
                    DLog(@"车型");
                    if (![self.brandUuid isEqualToString:@""]) {
                        self.type =2;
                        
                        SelectRidersCarAlertView *selCarView =[SelectRidersCarAlertView initSelectRidersCarAlertView];
                        [selCarView configFrame:[UIScreen mainScreen].bounds];
                        selCarView.delegate = self;
                        selCarView.couponArr = self.selCarListArray;
                        [selCarView showSelectRidersCarAlertViewOn:self.view.window];
                    }
                }
                else if (btn.tag==68)
                {
                    DLog(@"系统");
                    self.type =3;
                    
                    [self requestQueryListByTypeApiWithName:@"attach_sys"];
                }
            };
            
            supplementaryView.backgroundColor=[UIColor whiteColor];
             
            self.supplementaryView = supplementaryView;
            return supplementaryView;
        }else if([kind isEqualToString:UICollectionElementKindSectionFooter]) {
            UICollectionReusableView *supplementaryView = [collectionView dequeueReusableSupplementaryViewOfKind:UICollectionElementKindSectionFooter withReuseIdentifier:SupplementaryViewFooterIdentify forIndexPath:indexPath];
            if (indexPath.section ==0) {
                supplementaryView.backgroundColor =[UIColor whiteColor] ;
            }
            else
            {
                supplementaryView.backgroundColor =RGBColor(237, 238, 239);//RGBColor(237, 238, 239)
            }
            return supplementaryView;
        }

    return nil;
}


#pragma mark == 筛选品牌
-(void)requestPinPai
{
    NSDictionary *pageBoDict=@{
    };
    NSDictionary *headerDict =@{
        @"token":[UserInfo getUserInfo].token,
    };
    NSString *urlStr = [NSString stringWithFormat:@"%@/0001",pinpaiApi];
    
    [HTTPManager getRequestWithUrl:urlStr parameters:pageBoDict header:headerDict progress:^(NSProgress * _Nonnull uploadProgress) {
        
    } success:^(NSDictionary * _Nonnull responseObject) {
        DLog(@"品牌responseObject===%@",responseObject);
        [self.pinpaiListArray removeAllObjects];
        NSArray *dataArr = responseObject[@"data"];
        NSArray *commendArr = [SelectRidersTModel mj_objectArrayWithKeyValuesArray:dataArr];
        SelectRidersTModel *model =[[SelectRidersTModel alloc]init];
        model.configName = @"全部品牌";
        model.uuid = @"";
        [self.pinpaiListArray addObject:model];
        
        for (int i=0; i<commendArr.count; i++)
        {
            SelectRidersTModel *caseModel = commendArr[i];
            [self.pinpaiListArray addObject:caseModel];
        }
        SelectRidersAlertView *pinpaiView =[SelectRidersAlertView initSelectRidersAlertView];
        pinpaiView.type=1;
        [pinpaiView configFrame:[UIScreen mainScreen].bounds];
        pinpaiView.couponArr = self.pinpaiListArray;
        pinpaiView.delegate = self;
        [pinpaiView showSelectRidersAlertViewOn:self.view.window];
        
    } other:^(NSDictionary * _Nonnull responseObject) {
        [AppUtils showErrorMessage:responseObject[@"msg"]];
        
    } failure:^(NSError * _Nonnull error) {
        
    }];
}

#pragma mark == 选择车型
-(void)requestVehicleConfigQueryAllList
{
    NSDictionary *pageBoDict=@{
    };
    NSDictionary *headerDict =@{
        @"token":[UserInfo getUserInfo].token,
    };
    NSString *urlStr = [NSString stringWithFormat:@"%@/%@",pinpaiApi,self.brandUuid];
    [HTTPManager getRequestWithUrl:urlStr parameters:pageBoDict header:headerDict progress:^(NSProgress * _Nonnull uploadProgress) {
        
    } success:^(NSDictionary * _Nonnull responseObject) {
        DLog(@"筛选responseObject===%@",responseObject);

        [self.selCarListArray removeAllObjects];
        NSArray *dataArr = responseObject[@"data"];
        if ([dataArr isKindOfClass:[NSArray class]]) {
            NSArray *commendArr = [SelectRidersTModel mj_objectArrayWithKeyValuesArray:dataArr];
            
            SelectRidersTModel *model =[[SelectRidersTModel alloc]init];
            model.configName = @"全部车型";
            model.uuid = @"";
            [self.selCarListArray addObject:model];
            
            for (int i=0; i<commendArr.count; i++) {
                SelectRidersTModel *caseModel = commendArr[i];
                [self.selCarListArray addObject:caseModel];
            }
        }
        
        [self.collectionView reloadData];

    } other:^(NSDictionary * _Nonnull responseObject) {
        [AppUtils showErrorMessage:responseObject[@"msg"]];
        
    } failure:^(NSError * _Nonnull error) {
        
    }];
}

#pragma mark == 系统
-(void)requestQueryListByTypeApiWithName:(NSString *)testString
{
    NSDictionary *pageBoDict=@{
    };
    NSDictionary *headerDict =@{
        @"token":[UserInfo getUserInfo].token,
    };
    NSString *urlStr = [NSString stringWithFormat:@"%@/%@",queryListByTypeApi,testString];
    [HTTPManager getRequestWithUrl:urlStr parameters:pageBoDict header:headerDict progress:^(NSProgress * _Nonnull uploadProgress) {
        
    } success:^(NSDictionary * _Nonnull responseObject) {
        [self.selListArray removeAllObjects];
        NSArray *dataArr = responseObject[@"data"];
        NSArray *commendArr = [SelectRidersTModel mj_objectArrayWithKeyValuesArray:dataArr];
     
        SelectRidersTModel *model =[[SelectRidersTModel alloc]init];
        model.lableDesc = @"全部系统";
        model.uuid = @"";
        [self.selListArray addObject:model];
        for (int i=0; i<commendArr.count; i++)
        {
            SelectRidersTModel *caseModel = commendArr[i];
            [self.selListArray addObject:caseModel];
        }
        SelectRidersAlertView *xitongView =[SelectRidersAlertView initSelectRidersAlertView];
        xitongView.type=2;
        [xitongView configFrame:[UIScreen mainScreen].bounds];
        xitongView.couponArr = self.selListArray;
        xitongView.delegate = self;
        [xitongView showSelectRidersAlertViewOn:self.view.window];
        
    } other:^(NSDictionary * _Nonnull responseObject) {
        [AppUtils showErrorMessage:responseObject[@"msg"]];
        
    } failure:^(NSError * _Nonnull error) {
        
    }];
}
#pragma mark == 品牌或者系统
-(void)getSelectpinpaiOrXitong:(SelectRidersTModel *)model
{
    DLog(@"lableDesc====%@",model.lableDesc);
    
    if (self.type==1) {
        self.brandUuid =SafeValue([NSString stringWithFormat:@"%@",model.uuid]);
        [self requestQueryCaseForTechnicianListWithPage:1 attachSys:self.attachSys brandUuid:self.brandUuid carModel:self.carmodel];
        [self.supplementaryView.pinpanBtn setTitle:model.configName forState:UIControlStateNormal];
        self.brandStr=model.configName;
        self.modelStr =@"全部车型 ";
        [self requestVehicleConfigQueryAllList];
    }else if (self.type==3)
    {
        self.attachSys =SafeValue([NSString stringWithFormat:@"%@",model.uuid]);
        [self requestQueryCaseForTechnicianListWithPage:1 attachSys:self.attachSys brandUuid:self.brandUuid carModel:self.carmodel];
        [self.supplementaryView.xitongBtn setTitle:model.lableDesc forState:UIControlStateNormal];
        self.systemStr=model.lableDesc;
    }
}

#pragma mark == 选中的车型
-(void)getSelectCarType:(SelectRidersTModel *)model
{
    self.carmodel =SafeValue([NSString stringWithFormat:@"%@",model.uuid]);
    [self requestQueryCaseForTechnicianListWithPage:1 attachSys:self.attachSys brandUuid:self.brandUuid carModel:self.carmodel];
    [self.supplementaryView.chexingBtn setTitle:model.configName forState:UIControlStateNormal];
    self.modelStr=model.configName;
}

- (CGSize)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout*)collectionViewLayout referenceSizeForHeaderInSection:(NSInteger)section
{
    if (section==0) {
        return CGSizeMake(0, 0);//需购买商品区头
    }
    return CGSizeMake(SCREEN_WIDTH, 60);//需购买商品区头
}
- (CGSize)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout*)collectionViewLayout referenceSizeForFooterInSection:(NSInteger)section{ //区脚高度
    return (CGSize){SCREEN_WIDTH,0};
//    return CGSizeZero;
}
//列间距
- (CGFloat)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout*)collectionViewLayout minimumInteritemSpacingForSectionAtIndex:(NSInteger)section{
    return 0;
}
//行间距
- (CGFloat)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout*)collectionViewLayout minimumLineSpacingForSectionAtIndex:(NSInteger)section{
    return 6;
   
}

-(UICollectionView *)collectionView
{
    if (!_collectionView) {
        UICollectionViewFlowLayout *layout = [[UICollectionViewFlowLayout alloc] init];
        self.collectionView = [[UICollectionView alloc] initWithFrame:(CGRect){0,0,self.view.width,SCREEN_HEIGHT-BottomTab_Height-80} collectionViewLayout:layout];
        self.collectionView.dataSource = self;
        self.collectionView.delegate = self;
        self.collectionView.backgroundColor = [UIColor whiteColor];
        self.collectionView.showsVerticalScrollIndicator = NO;
        [self.collectionView registerNib:[UINib nibWithNibName:@"EduCollectionViewCell" bundle:nil] forCellWithReuseIdentifier:EduCollectionViewCelliIdentify];
        [self.collectionView registerClass:[StoreHeadView class]  forSupplementaryViewOfKind:UICollectionElementKindSectionHeader withReuseIdentifier:SupplementaryViewHeaderIdentify];//产品区头
        [self.collectionView registerClass:[UICollectionReusableView class] forSupplementaryViewOfKind:UICollectionElementKindSectionFooter withReuseIdentifier:SupplementaryViewFooterIdentify];
        [self.collectionView registerClass:[UICollectionViewCell class] forCellWithReuseIdentifier:HeadBannerIdentify];

    }
    
    return _collectionView;
}
-(MallBannerView *)bannerView
{
    if (!_bannerView)
    {
        CGFloat banW=self.view.frame.size.width-20;
        _bannerView =[[MallBannerView alloc]initWithFrame:CGRectMake(10, 10,banW, banW*0.42) imageArray:@[@"my-icon-pic",@"my-icon-pic",@"my-icon-pic"] barnnerType:BannerViewTypeUrlPicture];
        _bannerView.clipsToBounds = YES;
        _bannerView.layer.cornerRadius = 10.0f;
    }
    return _bannerView;
}



-(NSMutableArray *)pinpaiListArray
{
    if (!_pinpaiListArray) {
        _pinpaiListArray =[[NSMutableArray alloc]init];
    }
    return _pinpaiListArray;
}
-(NSMutableArray *)selCarListArray
{
    if (!_selCarListArray) {
        _selCarListArray =[[NSMutableArray alloc]init];
    }
    return _selCarListArray;
}
-(NSMutableArray *)selListArray
{
    if (!_selListArray) {
        _selListArray =[[NSMutableArray alloc]init];
    }
    return _selListArray;
}
//选择车型
-(SelectRidersCarAlertView *)selCarView
{
    if (!_selCarView) {
        _selCarView =[SelectRidersCarAlertView initSelectRidersCarAlertView];
        [_selCarView configFrame:[UIScreen mainScreen].bounds];
        _selCarView.delegate = self;
    }
    return _selCarView;
}

-(NSMutableArray *)listArray
{
    if (!_listArray) {
        _listArray =[[NSMutableArray alloc]init];
    }
    return _listArray;
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
