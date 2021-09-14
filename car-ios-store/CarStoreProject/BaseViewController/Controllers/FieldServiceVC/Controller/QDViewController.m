//
//  QDViewController.m
//  CarStoreProject
//
//  Created by apple on 2021/3/9.
//

#import "QDViewController.h"
#import "FieldServiceTableViewCell.h"
#import "FieldServiceDesModel.h"

@interface QDViewController ()<UITableViewDelegate,UITableViewDataSource>
@property (nonatomic,strong)UITableView *listTable;
@property (nonatomic,strong)NSMutableArray *listArray;
@property(nonatomic,strong)NSMutableArray *productIdArray;
@property (nonatomic,strong)EmptyView *empView;
@end
static NSString * const FieldServiceTableViewCelliIdentify = @"FieldServiceTableViewCell";//抢单

@implementation QDViewController
{
    int pageSize;
}
- (void)viewDidLoad {
    [super viewDidLoad];
    
   
    // Do any additional setup after loading the view.
    pageSize = 10;
    [self.view addSubview:self.listTable];
    [self requestQueryCaseForTechnicianListWithPage:1];
    
    
    _listTable.mj_header = [MJRefreshNormalHeader headerWithRefreshingBlock:^{ //下拉刷新
        [self requestGoodHeaderList];
        
    }];
    _listTable.mj_footer = [MJRefreshAutoNormalFooter footerWithRefreshingTarget:self refreshingAction:@selector(requestGoodFooterList)];
    
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
        @"queryType":@(0),
    };
    NSDictionary *headerDict =@{
        @"token":[UserInfo getUserInfo].token,
    };
    NSString *urlStr = [NSString stringWithFormat:@"%@",querySceneOrderListApi];
    [HTTPManager postRequestWithUrl:urlStr parameters:pageBoDict header:headerDict progress:^(NSProgress * _Nonnull uploadProgress) {
        
    } success:^(NSDictionary * _Nonnull responseObject) {
        DLog(@"案例查询responseObject===%@",responseObject);
        
        NSArray *dataArr = responseObject[@"data"];
        NSArray *commendArr = [FieldServiceDesModel mj_objectArrayWithKeyValuesArray:dataArr];
        for (int i=0; i<commendArr.count; i++) {
            FieldServiceDesModel *caseModel = commendArr[i];
            NSString *mainIDString = [NSString stringWithFormat:@"%@",caseModel.uuid];
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
        DLog(@"抢单错误responseObject====%@",responseObject);
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
    FieldServiceTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:FieldServiceTableViewCelliIdentify forIndexPath:indexPath];
    cell.selectionStyle = UITableViewCellSelectionStyleNone;
    
    FieldServiceDesModel *model = self.listArray[indexPath.row];
    [cell setModel:model];
    
    
    cell.zjqdListActionBlock = ^(id  _Nonnull obj) {
        DLog(@"直接抢单");
        [self requestGrabbingOrders:model.uuid];
        
    };
    
    return cell;
    
}

-(void)requestGrabbingOrders:(NSString *)sceneOrderUuid
{
    NSDictionary *pageBoDict=@{
    };
    NSDictionary *headerDict =@{
        @"token":[UserInfo getUserInfo].token,
    };
    NSString *urlStr = [NSString stringWithFormat:@"%@/%@",sceneOrderGrabbingOrdersApi,sceneOrderUuid];
    [HTTPManager getRequestWithUrl:urlStr parameters:pageBoDict header:headerDict progress:^(NSProgress * _Nonnull uploadProgress) {
        
    } success:^(NSDictionary * _Nonnull responseObject) {
        
        [self requestGoodHeaderList];
        
        
    } other:^(NSDictionary * _Nonnull responseObject) {
        
        DLog(@"账户信息其他responseObject===%@",responseObject);

        [AppUtils showErrorMessage:responseObject[@"msg"]];
    } failure:^(NSError * _Nonnull error) {
        
    }];
}

-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return UITableViewAutomaticDimension;
}


-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    FieldServiceDesModel *model = self.listArray[indexPath.row];
    SummaryH5ViewController *shopCenterVC =[[SummaryH5ViewController alloc]init];
    shopCenterVC.url = [NSString stringWithFormat:@"%@%@?uuid=%@",HOSTH5_IP,scenceOrderDetailH5Api,model.uuid];
     DLog(@"shopCenterVC====%@",shopCenterVC.url);
    shopCenterVC.hidesBottomBarWhenPushed = YES;
    [self.navigationController pushViewController:shopCenterVC animated:YES];
    
}

#pragma mark == 懒加载
-(UITableView *)listTable
{
    if (!_listTable) {
        _listTable =[[UITableView alloc]initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, self.view.frame.size.height-BottomTab_Height-SafeAreaBottom_Height-NavigationBar_Height-30) style:UITableViewStylePlain];
        _listTable.delegate = self;
        _listTable.dataSource = self;
        _listTable.separatorStyle = UITableViewCellSeparatorStyleNone;
        _listTable.backgroundColor =[UIColor whiteColor];
        [_listTable registerNib:[UINib nibWithNibName:@"FieldServiceTableViewCell" bundle:nil] forCellReuseIdentifier:FieldServiceTableViewCelliIdentify];//抢单
        _listTable.estimatedRowHeight = 10.0f;
        _listTable.showsVerticalScrollIndicator = NO;
        _listTable.backgroundColor =NumberEDF5FBColor;
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


/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
