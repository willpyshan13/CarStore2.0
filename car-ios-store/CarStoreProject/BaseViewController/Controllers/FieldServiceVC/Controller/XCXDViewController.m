//
//  XCXDViewController.m
//  CarStoreProject
//
//  Created by apple on 2021/3/9.
//

#import "XCXDViewController.h"
#import "XCXDTableViewCell.h"
#import "FieldServiceDesModel.h"

@interface XCXDViewController ()<UITableViewDelegate,UITableViewDataSource>
@property (nonatomic,strong)UITableView *listTable;
@property (nonatomic,strong)NSMutableArray *listArray;
@property(nonatomic,strong)NSMutableArray *productIdArray;
@property (nonatomic,strong)EmptyView *empView;
@property (nonatomic,strong)UIButton *addOrderBtn;

@end

static NSString * const XCXDTableViewCelliIdentify = @"XCXDTableViewCell";//现场支持下单


@implementation XCXDViewController
{
    int pageSize;
}

-(void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    [self requestQueryCaseForTechnicianListWithPage:1];
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    
    
    [self.view addSubview:self.listTable];
    pageSize = 10;
    
    
    
    self.view.backgroundColor =NumberEDF5FBColor;
    
    _listTable.mj_header = [MJRefreshNormalHeader headerWithRefreshingBlock:^{ //下拉刷新
        [self requestGoodHeaderList];
        
    }];
    _listTable.mj_footer = [MJRefreshAutoNormalFooter footerWithRefreshingTarget:self refreshingAction:@selector(requestGoodFooterList)];
    
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
            self.addOrderBtn.hidden = YES;
        }else
        {
            self.addOrderBtn.hidden = NO;
            [self.view addSubview:self.addOrderBtn];
        }
        
        
    } other:^(NSDictionary * _Nonnull responseObject)
    {
        DLog(@"responseObject===%@",responseObject);
        [AppUtils showErrorMessage:responseObject[@"msg"]];
        
    } failure:^(NSError * _Nonnull error) {
        
    }];
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
        @"queryType":@(2),
    };
    NSDictionary *headerDict =@{
        @"token":[UserInfo getUserInfo].token,
    };
    NSString *urlStr = [NSString stringWithFormat:@"%@",querySceneOrderListApi];
    [HTTPManager postRequestWithUrl:urlStr parameters:pageBoDict header:headerDict progress:^(NSProgress * _Nonnull uploadProgress) {
        
    } success:^(NSDictionary * _Nonnull responseObject) {
        DLog(@"现场支持下单responseObject===%@",responseObject);
        
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
    XCXDTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:XCXDTableViewCelliIdentify forIndexPath:indexPath];
    cell.selectionStyle = UITableViewCellSelectionStyleNone;
    FieldServiceDesModel *model = self.listArray[indexPath.row];
    [cell setModel:model];
    return cell;
    
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

-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return UITableViewAutomaticDimension;
}

-(void)addOrderBtnClick
{
    DLog(@"新增现场订单");
    SummaryH5ViewController *shopCenterVC =[[SummaryH5ViewController alloc]init];
    shopCenterVC.url = [NSString stringWithFormat:@"%@%@",HOSTH5_IP,scenceOrderH5Api];
     DLog(@"shopCenterVC====%@",shopCenterVC.url);
    shopCenterVC.hidesBottomBarWhenPushed = YES;
    [self.navigationController pushViewController:shopCenterVC animated:YES];
}

-(UIButton *)addOrderBtn
{
    if (!_addOrderBtn) {
        _addOrderBtn =[UIButton buttonWithType:UIButtonTypeCustom];
        _addOrderBtn.frame = CGRectMake(25, 20, SCREEN_WIDTH-50, 44);
        _addOrderBtn.backgroundColor = Number1691E3Color;
        _addOrderBtn.titleLabel.font = FontSize(16);
        [_addOrderBtn setTitle:@"新增现场订单" forState:UIControlStateNormal];
        [_addOrderBtn setTitleColor:NumberFFColor forState:UIControlStateNormal];
        _addOrderBtn.clipsToBounds = YES;
        _addOrderBtn.layer.cornerRadius = 8.0f;
        [_addOrderBtn addTarget:self action:@selector(addOrderBtnClick) forControlEvents:UIControlEventTouchUpInside];
    }
    return _addOrderBtn;
}


#pragma mark == 懒加载
-(UITableView *)listTable
{
    if (!_listTable) {
        _listTable =[[UITableView alloc]initWithFrame:CGRectMake(0, self.addOrderBtn.bottom+10, SCREEN_WIDTH, self.view.frame.size.height-BottomTab_Height-SafeAreaBottom_Height-NavigationBar_Height-30-74) style:UITableViewStylePlain];
        _listTable.delegate = self;
        _listTable.dataSource = self;
        _listTable.separatorStyle = UITableViewCellSeparatorStyleNone;
        _listTable.backgroundColor =[UIColor whiteColor];
        [_listTable registerNib:[UINib nibWithNibName:@"XCXDTableViewCell" bundle:nil] forCellReuseIdentifier:XCXDTableViewCelliIdentify];//现场支持下单
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
