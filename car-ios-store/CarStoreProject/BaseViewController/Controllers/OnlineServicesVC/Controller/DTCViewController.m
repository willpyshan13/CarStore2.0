//
//  DTCViewController.m
//  CarStoreProject
//
//  Created by Qin on 2021/2/15.
//

#import "DTCViewController.h"

#import "DTCCell.h"
#import "DTCListModel.h"

static NSString * const OnLineDTCTableViewCelliIdentify = @"OnLineDTCTableViewCell";

@interface DTCViewController ()<UITableViewDelegate,UITableViewDataSource,UITextFieldDelegate>
@property (nonatomic,strong)UITableView *listTable;
@property (strong, nonatomic)UIView *searchBgView;
@property (strong, nonatomic) UITextField *searchText;
@property (nonatomic,strong)EmptyView *empView;
@property (nonatomic,strong)NSMutableArray *listArray;
@property(nonatomic,strong)NSMutableArray *productIdArray;
@property (nonatomic,strong)UIButton *buluBtn;
@property (nonatomic,strong)NSString *inputStr;//输入故障车代码

@end

@implementation DTCViewController
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
    self.view.backgroundColor=HEXColor(@"#EDF5FB");
    
    [self createSearchView];
    [self.view addSubview:self.listTable];
    
    [self.view addSubview:self.buluBtn];
    self.inputStr = @"";
    pageSize = 10;

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
-(void)createSearchView{
    UIView *searchBgView=[[UIView alloc]initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 66)];
    searchBgView.backgroundColor=[UIColor whiteColor];
    [self.view addSubview:searchBgView];
    self.searchBgView=searchBgView;
    
    UIView *searchView=[[UIView alloc]initWithFrame:CGRectMake(15, 13, SCREEN_WIDTH-15*2, 40)];
    searchView.backgroundColor=HEXColor(@"#F6F7F8");
    searchView.clipsToBounds=YES;
    searchView.layer.cornerRadius=6;
    [searchBgView addSubview:searchView];
    
    UIImageView *searchBg = [[UIImageView alloc]initWithImage:kImageNamed(@"city_search_icon")];
    searchBg.frame = CGRectMake(15, (40-15)/2, 15, 15);
    [searchView addSubview:searchBg];
    
    _searchText = [[UITextField alloc]initWithFrame:CGRectMake(searchBg.right+15, 5,searchView.width-30 , 30)];
    _searchText.backgroundColor = [UIColor clearColor];
    _searchText.font = [UIFont systemFontOfSize:14];
    _searchText.placeholder  = @"请输入故障车代码";
    _searchText.returnKeyType = UIReturnKeySearch;
    _searchText.clearButtonMode = UITextFieldViewModeWhileEditing;
    _searchText.textColor    = [UIColor colorWithRed:58/255.0 green:58/255.0 blue:58/255.0 alpha:1];
    _searchText.delegate     = self;
    [_searchText addTarget:self action:@selector(textChange:) forControlEvents:UIControlEventEditingChanged];
    [searchView addSubview:_searchText];
}

-(void)requestQueryCaseForTechnicianListWithPage:(NSInteger)page
{
    NSDictionary *pageBoDict=@{
        @"pageNum":@(page),
        @"pageSize":@(pageSize),
        @"dtcCode":self.inputStr,
        @"dtcCheckSts":@(1),
    };
    NSDictionary *headerDict =@{
        @"token":[UserInfo getUserInfo].token,
    };
    NSString *urlStr = [NSString stringWithFormat:@"%@",dtcListApi];
    [HTTPManager postRequestWithUrl:urlStr parameters:pageBoDict header:headerDict progress:^(NSProgress * _Nonnull uploadProgress) {
        
    } success:^(NSDictionary * _Nonnull responseObject) {
        DLog(@"dtc列表responseObject===%@",responseObject);
        NSArray *dataArr = responseObject[@"data"];
        NSArray *commendArr = [DTCListModel mj_objectArrayWithKeyValuesArray:dataArr];
        for (int i=0; i<commendArr.count; i++) {
            DTCListModel *caseModel = commendArr[i];
            NSString *mainIDString = [NSString stringWithFormat:@"%@",caseModel.uuid];
            if (![self.productIdArray containsObject:mainIDString])
            {
                [self.listArray addObject:caseModel];
                [self.productIdArray addObject:mainIDString];
            }
        }
//
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

-(void)bulubtnClick
{
    DLog(@"补录一个");
    SummaryH5ViewController *shopCenterVC =[[SummaryH5ViewController alloc]init];
    shopCenterVC.url = [NSString stringWithFormat:@"%@%@",HOSTH5_IP,dtcAddOrEditH5Api];
     DLog(@"shopCenterVC====%@",shopCenterVC.url);
    shopCenterVC.hidesBottomBarWhenPushed = YES;
    [self.navigationController pushViewController:shopCenterVC animated:YES];
}



-(UITableView *)listTable
{
    if (!_listTable) {
        _listTable =[[UITableView alloc]initWithFrame:CGRectMake(0, self.searchBgView.bottom+10, SCREEN_WIDTH, self.view.frame.size.height-BottomTab_Height-SafeAreaBottom_Height-84-76-85) style:UITableViewStylePlain];
        _listTable.delegate = self;
        _listTable.dataSource = self;
        _listTable.separatorStyle = UITableViewCellSeparatorStyleNone;
        _listTable.backgroundColor =[UIColor whiteColor];
        [_listTable registerNib:[UINib nibWithNibName:@"DTCCell" bundle:nil] forCellReuseIdentifier:OnLineDTCTableViewCelliIdentify];
        _listTable.estimatedRowHeight = 10.0f;
        _listTable.showsVerticalScrollIndicator = NO;
        _listTable.backgroundColor = NumberEDF5FBColor;
    }
    return _listTable;
}
-(UIButton *)buluBtn
{
    if (!_buluBtn) {
        _buluBtn =[UIButton buttonWithType:UIButtonTypeCustom];
        _buluBtn.frame = CGRectMake(15, self.listTable.bottom+20, SCREEN_WIDTH-30, 44);
        _buluBtn.backgroundColor = Number1691E3Color;
        _buluBtn.titleLabel.font = FontSize(16);
        [_buluBtn setTitle:@"补录一个" forState:UIControlStateNormal];
        [_buluBtn setTitleColor:NumberFFColor forState:UIControlStateNormal];
        _buluBtn.clipsToBounds = YES;
        _buluBtn.layer.cornerRadius = 8.0f;
        [_buluBtn addTarget:self action:@selector(bulubtnClick) forControlEvents:UIControlEventTouchUpInside];
    }
    return _buluBtn;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return self.listArray.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    DTCCell *cell = [tableView dequeueReusableCellWithIdentifier:OnLineDTCTableViewCelliIdentify forIndexPath:indexPath];
    cell.selectionStyle = UITableViewCellSelectionStyleNone;
    DTCListModel *model = self.listArray[indexPath.row];
    [cell setModel:model];
    return cell;
}
-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return 44;
}

-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
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
            [AppUtils showInfoMessage:@"该功能暂未开通，敬请期待"];
        }else
        {
            DTCListModel *model = self.listArray[indexPath.row];
            SummaryH5ViewController *shopCenterVC =[[SummaryH5ViewController alloc]init];
            shopCenterVC.url = [NSString stringWithFormat:@"%@%@?uuid=%@",HOSTH5_IP,dtcDetailH5Api,model.uuid];
             DLog(@"shopCenterVC====%@",shopCenterVC.url);
            shopCenterVC.hidesBottomBarWhenPushed = YES;
            [self.navigationController pushViewController:shopCenterVC animated:YES];
        }
        
        
    } other:^(NSDictionary * _Nonnull responseObject)
    {
        DLog(@"responseObject===%@",responseObject);
        [AppUtils showErrorMessage:responseObject[@"msg"]];
        
    } failure:^(NSError * _Nonnull error) {
        
    }];
    
    
    
}




-(UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section{
    UIView *headerView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, tableView.frame.size.width, 44)];
    headerView.backgroundColor = [UIColor whiteColor];

    UILabel *codeLabel = [[UILabel alloc] initWithFrame:CGRectMake(15, 7, 70, 30)];
    codeLabel.font = [UIFont systemFontOfSize:14];
    codeLabel.textAlignment=NSTextAlignmentCenter;
    codeLabel.text=@"故障代码";
    codeLabel.textColor = Number090909Color;
    [headerView addSubview:codeLabel];
    
    UIView *lineView = [[UIView alloc] initWithFrame:CGRectMake(codeLabel.right+1, 0, 1, 44)];
    lineView.backgroundColor = HEXColor(@"#F1F1F1");
    [headerView addSubview:lineView];
    
    UILabel *desLabel = [[UILabel alloc] initWithFrame:CGRectMake(lineView.right+1, 7, SCREEN_WIDTH-codeLabel.right-3-15-83, 30)];
    desLabel.font = [UIFont systemFontOfSize:14];
    desLabel.text=@"   故障说明";
    desLabel.textColor = Number090909Color;

    [headerView addSubview:desLabel];
    
    UIView *lineView1 = [[UIView alloc] initWithFrame:CGRectMake(SCREEN_WIDTH-83, 0, 1, 44)];
    lineView1.backgroundColor = HEXColor(@"#F1F1F1");
    [headerView addSubview:lineView1];
    
    
    UILabel *codeLabel1 = [[UILabel alloc] initWithFrame:CGRectMake(lineView1.right+6, 7, 70, 30)];
    codeLabel1.font = [UIFont systemFontOfSize:14];
    codeLabel1.textAlignment=NSTextAlignmentCenter;
    codeLabel1.text=@"品牌";
    codeLabel1.textColor = Number090909Color;
    [headerView addSubview:codeLabel1];
    
    return headerView;
}

-(CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section{
    return 44;
}

- (void)textChange:(UITextField*)textField{
    DLog(@"输入");
    [self.listArray removeAllObjects];
    [self.productIdArray removeAllObjects];
    self.inputStr = textField.text;
    [self requestQueryCaseForTechnicianListWithPage:1];
}

- (BOOL)textFieldShouldReturn:(UITextField *)textField{
    [textField resignFirstResponder];
    return YES;
}




-(EmptyView *)empView
{
    if (!_empView) {
        _empView =[EmptyView initEmptyView];
        [_empView configFrame:self.listTable.frame];
    }
    return _empView;
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





/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
