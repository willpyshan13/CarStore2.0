//
//  SoldoutGoodsVController.m
//  CarStoreProject
//
//  Created by apple on 2021/1/27.
//

#import "SoldoutGoodsVController.h"
#import "GoodsManagementTableViewCell.h"
#import "UpGoodsListModel.h"

@interface SoldoutGoodsVController ()<UITableViewDelegate,UITableViewDataSource>
@property (nonatomic,strong)UITableView *listTable;
@property (nonatomic,strong)NSMutableArray *listArray;
@property(nonatomic,strong)NSMutableArray *productIdArray;
@property (nonatomic,strong)EmptyView *empView;

@end

@implementation SoldoutGoodsVController
{
    int pageSize;
}
static NSString * const DownGoodsManagementTableViewCelliIdentify = @"GoodsManagementTableViewCell";//下架商品

-(void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    [self requestqueryStoreGoodsListWithPage:1];
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    pageSize = 10;
    
    self.view.backgroundColor=[UIColor whiteColor];
    
    [self.view addSubview:self.listTable];

//    [self requestqueryStoreGoodsListWithPage:1];
    
    _listTable.mj_header = [MJRefreshNormalHeader headerWithRefreshingBlock:^{ //下拉刷新
        [self requestGoodHeaderList];
        [self.listTable.mj_header endRefreshing];
    }];
    _listTable.mj_footer = [MJRefreshAutoNormalFooter footerWithRefreshingTarget:self refreshingAction:@selector(requestGoodFooterList)];

}

#pragma mark == 下拉刷新
-(void)requestGoodHeaderList
{
    [self.listArray removeAllObjects];
    [self.productIdArray removeAllObjects];
    [self requestqueryStoreGoodsListWithPage:1];

}
#pragma mark== 上拉加载更多
-(void)requestGoodFooterList{
    NSInteger count = _listArray.count;
    NSInteger tmp = (count%pageSize == 0)?count/pageSize:count/pageSize+1;
    tmp += 1;
    [self requestqueryStoreGoodsListWithPage:(int)tmp];
}

-(void)requestqueryStoreGoodsListWithPage:(NSInteger)page
{
    NSDictionary *pageBoDict=@{
        @"pageNum":@(page),
        @"pageSize":@(pageSize),
        @"sellSts":@(0),
    };
    NSDictionary *headerDict =@{
        @"token":[UserInfo getUserInfo].token,
    };
    NSString *urlStr = [NSString stringWithFormat:@"%@",queryStoreGoodsListApi];
    [HTTPManager postRequestWithUrl:urlStr parameters:pageBoDict header:headerDict progress:^(NSProgress * _Nonnull uploadProgress) {
        
    } success:^(NSDictionary * _Nonnull responseObject) {
        DLog(@"下架商品responseObject===%@",responseObject);
        
        NSArray *dataArr = responseObject[@"data"];
        if (dataArr.count==0) {
            [self.empView showEmptyViewOn:self];
        }
        else
        {
            [self.empView dismissEmptyView];
        }
        NSArray *commendArr = [UpGoodsListModel mj_objectArrayWithKeyValuesArray:dataArr];
        for (int i=0; i<commendArr.count; i++) {
            UpGoodsListModel *listModel = commendArr[i];
            NSString *mainIDString = [NSString stringWithFormat:@"%@",listModel.uuid];
            if (![self.productIdArray containsObject:mainIDString])
            {
                [self.listArray addObject:listModel];
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
    } other:^(NSDictionary * _Nonnull responseObject) {
        
    } failure:^(NSError * _Nonnull error) {
        
    }];
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    
    return self.listArray.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    GoodsManagementTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:DownGoodsManagementTableViewCelliIdentify forIndexPath:indexPath];
    cell.selectionStyle = UITableViewCellSelectionStyleNone;
    cell.stateLab.text = @"已下架";
    UpGoodsListModel *model = self.listArray[indexPath.row];
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
    
    UpGoodsListModel *model = self.listArray[indexPath.row];
    shopCenterVC.url = [NSString stringWithFormat:@"%@%@?uuid=%@",HOSTH5_IP,putawayProductDetailH5Api,model.uuid];
 
    DLog(@"shopCenterVC====%@",shopCenterVC.url);
    shopCenterVC.hidesBottomBarWhenPushed = YES;
    [self.navigationController pushViewController:shopCenterVC animated:YES];
    
}

#pragma mark == 懒加载
-(UITableView *)listTable
{
    if (!_listTable) {
        _listTable =[[UITableView alloc]initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, self.view.frame.size.height-BottomTab_Height-SafeAreaBottom_Height-100) style:UITableViewStylePlain];
        _listTable.delegate = self;
        _listTable.dataSource = self;
        _listTable.separatorStyle = UITableViewCellSeparatorStyleNone;
        _listTable.backgroundColor =[UIColor whiteColor];
        [_listTable registerNib:[UINib nibWithNibName:@"GoodsManagementTableViewCell" bundle:nil] forCellReuseIdentifier:DownGoodsManagementTableViewCelliIdentify];
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
