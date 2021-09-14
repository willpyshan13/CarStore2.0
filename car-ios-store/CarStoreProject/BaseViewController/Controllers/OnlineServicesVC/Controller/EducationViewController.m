//
//  EducationViewController.m
//  CarStoreProject
//
//  Created by Qin on 2021/2/15.
//

#import "EducationViewController.h"
#import "EduCollectionViewCell.h"
#import "StoreHeadView.h"
#import "CourseParentModel.h"
//最新课程为空
#import "NewEduEmpCollectionViewCell.h"


@interface EducationViewController ()<UICollectionViewDataSource,UICollectionViewDelegate,UICollectionViewDelegateFlowLayout>
@property (nonatomic, strong) UICollectionView *collectionView;

//@property (nonatomic,strong)NSMutableArray *allListArray;

//最新培训课程
@property (nonatomic,strong)NSMutableArray *firstListArray;
@property(nonatomic,strong)NSMutableArray *productIdArray;

//全部培训课程
@property (nonatomic,strong)NSMutableArray *secondListArray;
@property(nonatomic,strong)NSMutableArray *secondProductIdArray;



@property (nonatomic,strong)EmptyView *empView;

@end

@implementation EducationViewController
{
    int pageSize;
}
static NSString * const EduCollectionViewCelliIdentify = @"EduCollectionViewCell";//最新培训教育

static NSString * const NewEduEmpCollectionViewCelliIdentify = @"NewEduEmpCollectionViewCell";//最新培训教育空

static NSString * const SupplementaryViewFooterIdentify = @"SupplementaryViewFooterIdentify";

static NSString * const SupplementaryViewHeaderIdentify = @"SupplementaryViewHeaderIdentify";



- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    [self.view addSubview:self.collectionView];
    self.view.backgroundColor = NumberEDF5FBColor;

    pageSize=10;
    
    //最新培训课程
    [self requestQueryCaseForTechnicianListNewestWithPage:1];
    
    //全部培训课程
    [self requestQueryCaseForTechnicianListWithPage:1];
    
    _collectionView.mj_header = [MJRefreshNormalHeader headerWithRefreshingBlock:^{ //下拉刷新
        [self requestGoodHeaderList];
        
    }];
    _collectionView.mj_footer = [MJRefreshAutoNormalFooter footerWithRefreshingTarget:self refreshingAction:@selector(requestGoodFooterList)];
}
#pragma mark == 最新培训课程
-(void)requestQueryCaseForTechnicianListNewestWithPage:(NSInteger)page
{
    NSDictionary *pageBoDict=@{
        @"courseTitle":@"",
        @"pageNum":@(page),
        @"pageSize":@(pageSize),
    };
    NSDictionary *headerDict =@{
        @"token":[UserInfo getUserInfo].token,
    };
    NSString *urlStr = [NSString stringWithFormat:@"%@",courseParentListNewestApi];
    [HTTPManager postRequestWithUrl:urlStr parameters:pageBoDict header:headerDict progress:^(NSProgress * _Nonnull uploadProgress) {
        
    } success:^(NSDictionary * _Nonnull responseObject)
    {
        DLog(@"最新培训教育responseObject===%@",responseObject);
        
        NSArray *dataArr = responseObject[@"data"];
        NSArray *commendArr = [CourseParentModel mj_objectArrayWithKeyValuesArray:dataArr];
        for (int i=0; i<commendArr.count; i++) {
            CourseParentModel *caseModel = commendArr[i];
            NSString *mainIDString = [NSString stringWithFormat:@"%@",caseModel.uuid];
            if (![self.productIdArray containsObject:mainIDString])
            {
                [self.firstListArray addObject:caseModel];
                [self.productIdArray addObject:mainIDString];
            }
        }
        
        [self.collectionView reloadData];
        [self.collectionView.mj_footer endRefreshing];
        [self.collectionView.mj_header endRefreshing];
        
       
        
    } other:^(NSDictionary * _Nonnull responseObject) {
        [AppUtils showInfoMessage:responseObject[@"msg"]];

    } failure:^(NSError * _Nonnull error) {
        
    }];
}

#pragma mark == 下拉刷新
-(void)requestGoodHeaderList
{
    [self.secondListArray removeAllObjects];
    [self.secondProductIdArray removeAllObjects];
    [self requestQueryCaseForTechnicianListWithPage:1];
    
    [self.firstListArray removeAllObjects];
    [self.productIdArray removeAllObjects];
    [self requestQueryCaseForTechnicianListNewestWithPage:1];
}

#pragma mark== 上拉加载更多
-(void)requestGoodFooterList{
    NSInteger count = _secondListArray.count;
    NSInteger tmp = (count%pageSize == 0)?count/pageSize:count/pageSize+1;
    tmp += 1;
    [self requestQueryCaseForTechnicianListWithPage:(int)tmp];
}
#pragma mark == 全部培训课程
-(void)requestQueryCaseForTechnicianListWithPage:(NSInteger)page
{
    NSDictionary *pageBoDict=@{
        @"courseTitle":@"",
        @"pageNum":@(page),
        @"pageSize":@(pageSize),
    };
    NSDictionary *headerDict =@{
        @"token":[UserInfo getUserInfo].token,
    };
    NSString *urlStr = [NSString stringWithFormat:@"%@",courseParentListApi];
    [HTTPManager postRequestWithUrl:urlStr parameters:pageBoDict header:headerDict progress:^(NSProgress * _Nonnull uploadProgress) {
        
    } success:^(NSDictionary * _Nonnull responseObject)
    {
        DLog(@"全部培训教育responseObject===%@",responseObject);
        
        NSArray *dataArr = responseObject[@"data"];
        NSArray *commendArr = [CourseParentModel mj_objectArrayWithKeyValuesArray:dataArr];
        for (int i=0; i<commendArr.count; i++) {
            CourseParentModel *caseModel = commendArr[i];
            NSString *mainIDString = [NSString stringWithFormat:@"%@",caseModel.uuid];
            if (![self.secondProductIdArray containsObject:mainIDString])
            {
                [self.secondListArray addObject:caseModel];
                [self.secondProductIdArray addObject:mainIDString];
            }
        }
        if (dataArr.count<10) {
            self.collectionView.mj_footer.hidden=YES;
        }else
        {
            self.collectionView.mj_footer.hidden=NO;
        }
        [self.collectionView reloadData];
        [self.collectionView.mj_footer endRefreshing];
        [self.collectionView.mj_header endRefreshing];
        
//        if (self.allListArray.count==0) {
//            [self.empView showEmptyViewOn:self];
//        }else
//        {
//            [self.empView dismissEmptyView];
//        }
        
    } other:^(NSDictionary * _Nonnull responseObject) {
        [AppUtils showInfoMessage:responseObject[@"msg"]];

    } failure:^(NSError * _Nonnull error) {
        
    }];
}



#pragma mark - UICollectionViewDataSource method
- (NSInteger)numberOfSectionsInCollectionView:(UICollectionView *)collectionView{
    return 2;
}

- (NSInteger)collectionView:(UICollectionView *)collectionView numberOfItemsInSection:(NSInteger)section
{
    if (section==0)
    {
        if (self.firstListArray.count==0) {
            return 1;
        }else
        {
            return self.firstListArray.count;
        }
        
    }
    return self.secondListArray.count;
}
- (__kindof UICollectionViewCell *)collectionView:(UICollectionView *)collectionView cellForItemAtIndexPath:(NSIndexPath *)indexPath{
   
    
    if (indexPath.section==0)
    {
        if (self.firstListArray.count==0) {
            NewEduEmpCollectionViewCell *cell = [collectionView dequeueReusableCellWithReuseIdentifier:NewEduEmpCollectionViewCelliIdentify forIndexPath:indexPath];
            return cell;
        }else
        {
            EduCollectionViewCell *cell = [collectionView dequeueReusableCellWithReuseIdentifier:EduCollectionViewCelliIdentify forIndexPath:indexPath];
            CourseParentModel *model = self.firstListArray[indexPath.row];
            [cell setModel:model];
            return cell;
        }
    }
    
    EduCollectionViewCell *cell = [collectionView dequeueReusableCellWithReuseIdentifier:EduCollectionViewCelliIdentify forIndexPath:indexPath];
    CourseParentModel *model = self.secondListArray[indexPath.row];
    [cell setModel:model];
    return cell;
}

-(void)collectionView:(UICollectionView *)collectionView didSelectItemAtIndexPath:(NSIndexPath *)indexPath
{
    if (indexPath.section==0)
    {
        CourseParentModel *model = self.firstListArray[indexPath.row];
        
        
        SummaryH5ViewController *remenJiaoyuVC =[[SummaryH5ViewController alloc]init];
//        shopCenterVC.url = [NSString stringWithFormat:@"%@%@?uuid=%@",HOSTH5_IP,myCourseH5Api,model.uuid];
        remenJiaoyuVC.url = [NSString stringWithFormat:@"%@%@?uuid=%@&courseName=%@&latestCourse=1",HOSTH5_IP,myCourseH5Api,model.uuid,model.courseTitle];
         DLog(@"shopCenterVC====%@",remenJiaoyuVC.url);
        remenJiaoyuVC.hidesBottomBarWhenPushed = YES;
        [self.navigationController pushViewController:remenJiaoyuVC animated:YES];
    }else
    {
        CourseParentModel *model = self.secondListArray[indexPath.row];
        SummaryH5ViewController *allJiaoyuCenterVC =[[SummaryH5ViewController alloc]init];
//        shopCenterVC.url = [NSString stringWithFormat:@"%@%@?uuid=%@",HOSTH5_IP,myCourseH5Api,model.uuid];
        allJiaoyuCenterVC.url = [NSString stringWithFormat:@"%@%@?uuid=%@&courseName=%@&latestCourse=0",HOSTH5_IP,myCourseH5Api,model.uuid,model.courseTitle];
         DLog(@"shopCenterVC====%@",allJiaoyuCenterVC.url);
        allJiaoyuCenterVC.hidesBottomBarWhenPushed = YES;
        [self.navigationController pushViewController:allJiaoyuCenterVC animated:YES];
    }
}

#pragma mark - UICollectionViewDelegateFlowLayout method
- (CGSize)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout*)collectionViewLayout sizeForItemAtIndexPath:(NSIndexPath *)indexPath
{
    if (indexPath.section==0) {
        if (self.firstListArray.count==0) {
            return (CGSize){SCREEN_WIDTH,164};
        }else
        {
            CGFloat margin = 7;
            CGFloat width = (SCREEN_WIDTH-margin*3)/2;
            CGFloat height = AdaptAllScreen(160);
            return (CGSize){width,height};
        }
    }
    CGFloat margin = 7;
    CGFloat width = (SCREEN_WIDTH-margin*3)/2;
    CGFloat height = AdaptAllScreen(160);
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
            if (indexPath.section== 0) {
                supplementaryView.titleStr  =@"最新培训课程";
            }
            else
            {
                supplementaryView.titleStr  =@"全部培训课程";
            }
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

- (CGSize)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout*)collectionViewLayout referenceSizeForHeaderInSection:(NSInteger)section
{
    return CGSizeMake(SCREEN_WIDTH, 56);//需购买商品区头
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
    return 0;
   
}

-(UICollectionView *)collectionView
{
    if (!_collectionView) {
        UICollectionViewFlowLayout *layout = [[UICollectionViewFlowLayout alloc] init];
        self.collectionView = [[UICollectionView alloc] initWithFrame:(CGRect){0,0,self.view.width,SCREEN_HEIGHT-BottomTab_Height-80} collectionViewLayout:layout];
        self.collectionView.dataSource = self;
        self.collectionView.delegate = self;
        self.collectionView.backgroundColor = NumberEDF5FBColor;
        self.collectionView.showsVerticalScrollIndicator = NO;
        [self.collectionView registerNib:[UINib nibWithNibName:@"EduCollectionViewCell" bundle:nil] forCellWithReuseIdentifier:EduCollectionViewCelliIdentify];
        
        [self.collectionView registerNib:[UINib nibWithNibName:@"NewEduEmpCollectionViewCell" bundle:nil] forCellWithReuseIdentifier:NewEduEmpCollectionViewCelliIdentify];

        [self.collectionView registerClass:[StoreHeadView class]  forSupplementaryViewOfKind:UICollectionElementKindSectionHeader withReuseIdentifier:SupplementaryViewHeaderIdentify];//产品区头
        [self.collectionView registerClass:[UICollectionReusableView class] forSupplementaryViewOfKind:UICollectionElementKindSectionFooter withReuseIdentifier:SupplementaryViewFooterIdentify];
    }
    
    return _collectionView;
}


//-(NSMutableArray *)allListArray
//{
//    if (!_allListArray) {
//        _allListArray =[[NSMutableArray alloc]init];
//    }
//    return _allListArray;
//}


//最新
-(NSMutableArray *)firstListArray
{
    if (!_firstListArray) {
        _firstListArray =[[NSMutableArray alloc]init];
    }
    return _firstListArray;
}
-(NSMutableArray *)productIdArray
{
    if (!_productIdArray) {
        _productIdArray =[NSMutableArray array];
    }
    return _productIdArray;
}
//全部
-(NSMutableArray *)secondListArray
{
    if (!_secondListArray) {
        _secondListArray =[[NSMutableArray alloc]init];
    }
    return _secondListArray;
}
-(NSMutableArray *)secondProductIdArray
{
    if (!_secondProductIdArray) {
        _secondProductIdArray =[NSMutableArray array];
    }
    return _secondProductIdArray;
}
-(EmptyView *)empView
{
    if (!_empView) {
        _empView =[EmptyView initEmptyView];
        [_empView configFrame:self.collectionView.frame];
    }
    return _empView;
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
