//
//  CaseViewController.m
//  CarStoreProject
//
//  Created by apple on 2021/1/27.
//

#import "CaseViewController.h"
#import "OnLineCaseTableViewCell.h"
#import "OnlineCaseModel.h"
#import "TechniciansCaseVController.h"//技师案例

@interface CaseViewController ()<UITableViewDelegate,UITableViewDataSource>
@property (nonatomic,strong)UITableView *listTable;
@property (nonatomic,strong)NSMutableArray *listArray;
@property(nonatomic,strong)NSMutableArray *productIdArray;
@property (nonatomic,strong)EmptyView *empView;
@property (nonatomic,strong)UIButton *publishCaseBtn;
@property (nonatomic,strong)UIButton *jishiCaseBtn;

@end

@implementation CaseViewController
{
    int pageSize;
}
static NSString * const OnLineCaseTableViewCelliIdentify =
@"OnLineCaseTableViewCell";//案例

-(void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    [self requestQueryCaseForTechnicianListWithPage:1];
}


- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    pageSize = 10;
    [self.view addSubview:self.listTable];
    self.view.backgroundColor = NumberEDF5FBColor;
        
    _listTable.mj_header = [MJRefreshNormalHeader headerWithRefreshingBlock:^{ //下拉刷新
        [self requestGoodHeaderList];
        
    }];
    _listTable.mj_footer = [MJRefreshAutoNormalFooter footerWithRefreshingTarget:self refreshingAction:@selector(requestGoodFooterList)];
    
    [self.view addSubview:self.publishCaseBtn];
    
    
    [self requestQueryByCode];
}

-(void)requestQueryByCode
{
    NSDictionary *pageBoDict=@{
    };
    NSDictionary *headerDict =@{
        @"token":[UserInfo getUserInfo].token,
    };
    NSString *urlStr = [NSString stringWithFormat:@"%@",queryByCodeApi];
    [HTTPManager getRequestWithUrl:urlStr parameters:pageBoDict header:headerDict progress:^(NSProgress * _Nonnull uploadProgress) {
        
    } success:^(NSDictionary * _Nonnull responseObject) {
        DLog(@"responseObject121212121=====%@",responseObject);
        NSDictionary *dataDict = responseObject[@"data"];
        NSString *lableValue=SafeValue([NSString stringWithFormat:@"%@",dataDict[@"lableValue"]]);
        
        if ([lableValue isEqualToString:@"1"])
        {
            self.jishiCaseBtn.hidden = YES;
            
        }else
        {
            self.jishiCaseBtn.hidden = NO;
            [self.view addSubview:self.jishiCaseBtn];
        }
        
        
    } other:^(NSDictionary * _Nonnull responseObject)
    {
        DLog(@"responseObject===%@",responseObject);
        [AppUtils showErrorMessage:responseObject[@"msg"]];
        
    } failure:^(NSError * _Nonnull error) {
        
    }];
}



-(UIButton *)publishCaseBtn
{
    if (!_publishCaseBtn) {
        
        _publishCaseBtn =[UIButton buttonWithType:UIButtonTypeCustom];
        _publishCaseBtn.frame =CGRectMake(15, 10, 90, 25);
        [_publishCaseBtn setTitle:@"发布新案例" forState:UIControlStateNormal];
        [_publishCaseBtn setTitleColor:Number1691E3Color forState:UIControlStateNormal];
        _publishCaseBtn.titleLabel.font = FontSize(16);
        [_publishCaseBtn addTarget:self action:@selector(publishtCaseBtnClick) forControlEvents:UIControlEventTouchUpInside];
    }
    return _publishCaseBtn;
}
-(UIButton *)jishiCaseBtn
{
    if (!_jishiCaseBtn) {
        
        _jishiCaseBtn =[UIButton buttonWithType:UIButtonTypeCustom];
        _jishiCaseBtn.frame =CGRectMake(_publishCaseBtn.right+15, 10, 90, 25);
        [_jishiCaseBtn setTitle:@"技师案例" forState:UIControlStateNormal];
        [_jishiCaseBtn setTitleColor:Number1691E3Color forState:UIControlStateNormal];
        _jishiCaseBtn.titleLabel.font = FontSize(16);
        [_jishiCaseBtn addTarget:self action:@selector(jishiCaseBtnClick) forControlEvents:UIControlEventTouchUpInside];
    }
    return _jishiCaseBtn;
}
#pragma mark  == 技师案例
-(void)jishiCaseBtnClick
{
    DLog(@"技师案例");
    TechniciansCaseVController *techVC =[[TechniciansCaseVController alloc]init];
    techVC.hidesBottomBarWhenPushed = YES;
    [self.navigationController pushViewController:techVC animated:YES];
    
}
-(void)publishtCaseBtnClick
{
    
    DLog(@"发布案例");
    SummaryH5ViewController *shopCenterVC =[[SummaryH5ViewController alloc]init];
    shopCenterVC.url = [NSString stringWithFormat:@"%@%@",HOSTH5_IP,fabuAnliH5Api];
    shopCenterVC.hidesBottomBarWhenPushed = YES;
    [self.navigationController pushViewController:shopCenterVC animated:YES];
}


#pragma mark == 下拉刷新
-(void)requestGoodHeaderList
{
    [self.listArray removeAllObjects];
    [self.productIdArray removeAllObjects];
    [self requestQueryCaseForTechnicianListWithPage:1];

}

#pragma mark== 上拉加载更多
-(void)requestGoodFooterList{
    NSInteger count = _listArray.count;
    NSInteger tmp = (count%pageSize == 0)?count/pageSize:count/pageSize+1;
    tmp += 1;
    [self requestQueryCaseForTechnicianListWithPage:(int)tmp];
}

-(void)requestQueryCaseForTechnicianListWithPage:(NSInteger)page
{
    NSDictionary *pageBoDict=@{
        @"pageNum":@(page),
        @"pageSize":@(pageSize),
    };
    NSDictionary *headerDict =@{
        @"token":[UserInfo getUserInfo].token,
    };
    NSString *urlStr = [NSString stringWithFormat:@"%@",queryCaseForTechnicianListApi];
    [HTTPManager postRequestWithUrl:urlStr parameters:pageBoDict header:headerDict progress:^(NSProgress * _Nonnull uploadProgress) {
        
    } success:^(NSDictionary * _Nonnull responseObject) {        
        DLog(@"案例查询responseObject===%@",responseObject);
        
        NSArray *dataArr = responseObject[@"data"];
        NSArray *commendArr = [OnlineCaseModel mj_objectArrayWithKeyValuesArray:dataArr];
        for (int i=0; i<commendArr.count; i++) {
            OnlineCaseModel *caseModel = commendArr[i];
            NSString *mainIDString = [NSString stringWithFormat:@"%@",caseModel.caseUuid];
            if (![self.productIdArray containsObject:mainIDString])
            {
                [self.listArray addObject:caseModel];
                [self.productIdArray addObject:mainIDString];
            }
        }
        
        if (dataArr.count<10) {
            self.listTable.mj_footer.hidden=YES;
        }else
        {
            self.listTable.mj_footer.hidden=NO;
        }
        [self.listTable reloadData];
        [self.listTable.mj_footer endRefreshing];
        [self.listTable.mj_header endRefreshing];
        
        if (self.listArray.count==0) {
            [self.empView showEmptyViewOn:self];
        }else
        {
            [self.empView dismissEmptyView];
        }
        
    } other:^(NSDictionary * _Nonnull responseObject) {
        [AppUtils showInfoMessage:responseObject[@"msg"]];

    } failure:^(NSError * _Nonnull error) {
        
    }];
}



- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    
    return self.listArray.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    OnLineCaseTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:OnLineCaseTableViewCelliIdentify forIndexPath:indexPath];
    cell.selectionStyle = UITableViewCellSelectionStyleNone;
    OnlineCaseModel *model = self.listArray[indexPath.row];
    [cell setModel:model];
    return cell;
}
-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return UITableViewAutomaticDimension;
}

-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    
    SummaryH5ViewController *shopCenterVC =[[SummaryH5ViewController alloc]init];
    shopCenterVC.url = [NSString stringWithFormat:@"%@%@",HOSTH5_IP,anliDetailH5Api];
     DLog(@"shopCenterVC====%@",shopCenterVC.url);
    shopCenterVC.hidesBottomBarWhenPushed = YES;
    [self.navigationController pushViewController:shopCenterVC animated:YES];
    
}
#pragma mark == 懒加载
-(UITableView *)listTable
{
    if (!_listTable) {
        _listTable =[[UITableView alloc]initWithFrame:CGRectMake(0, 45, SCREEN_WIDTH, self.view.frame.size.height-BottomTab_Height-SafeAreaBottom_Height-84-45) style:UITableViewStylePlain];
        _listTable.delegate = self;
        _listTable.dataSource = self;
        _listTable.separatorStyle = UITableViewCellSeparatorStyleNone;
        _listTable.backgroundColor =[UIColor whiteColor];
        [_listTable registerNib:[UINib nibWithNibName:@"OnLineCaseTableViewCell" bundle:nil] forCellReuseIdentifier:OnLineCaseTableViewCelliIdentify];//答主
        _listTable.estimatedRowHeight = 10.0f;
        _listTable.showsVerticalScrollIndicator = NO;
        _listTable.backgroundColor = NumberEDF5FBColor;
    }
    return _listTable;
}

-(NSMutableArray *)listArray
{
    if (!_listArray) {
        _listArray =[[NSMutableArray alloc]init];
    }
    return _listArray;
}
-(NSMutableArray *)productIdArray
{
    if (!_productIdArray) {
        _productIdArray =[NSMutableArray array];
    }
    return _productIdArray;
}

-(EmptyView *)empView
{
    if (!_empView) {
        _empView =[EmptyView initEmptyView];
        [_empView configFrame:self.listTable.frame];
    }
    return _empView;
}



- (void)scrollViewDidScroll:(UIScrollView *)scrollView
{
    if (self.DidScrollBlock)
    {
        self.DidScrollBlock(scrollView.contentOffset.y);
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
