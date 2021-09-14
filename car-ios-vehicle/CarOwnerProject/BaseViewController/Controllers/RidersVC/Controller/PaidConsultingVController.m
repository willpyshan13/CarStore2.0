//
//  PaidConsultingVController.m
//  CarOwnerProject
//
//  Created by apple on 2021/1/26.
//

#import "PaidConsultingVController.h"
#import "MallBannerView.h"
#import "AnswererTableViewCell.h"//答主
#import "QuestionTableViewCell.h"//问题
#import "DazhuListModel.h"//问题列表
#import "DazhuSureModel.h"//答主列表

@interface PaidConsultingVController ()<UITableViewDelegate,UITableViewDataSource>
@property (nonatomic,strong)UIView *bannerBGView;//轮播图
@property (nonatomic,strong)MallBannerView *bannerView;

@property (nonatomic,strong)UIButton *dazhuBtn;//答主
@property (nonatomic,strong)UIView *dzLineView;//答主线
@property (nonatomic,strong)UIButton *questionBtn;//问题
@property (nonatomic,strong)UIButton *allquestionBtn;//全国技师提问 问题广场
@property (nonatomic,strong)UIView *questionLineView;//问题线
@property (nonatomic,assign)NSInteger type;//1、答主 2、问题

@property (nonatomic,strong)EmptyView *empView;
@property (nonatomic,strong)NSMutableArray *listArray;
@property(nonatomic,strong)NSMutableArray *productIdArray;
@end

@implementation PaidConsultingVController
{
    int pageSize;
}
static NSString * const AnswererTableViewCelliIdentify = @"AnswererTableViewCell";//答主
static NSString * const QuestionTableViewCelliIdentify = @"QuestionTableViewCell";//问题


- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.    
    [self initBannerView];
    [self.view addSubview:self.listTable];
    
    pageSize = 10;
    self.type = 1;
    
    [self requestQueryTechnicianAnswerListWithPage:1];
    
    
    
    _listTable.mj_header = [MJRefreshNormalHeader headerWithRefreshingBlock:^{ //下拉刷新
        [self requestGoodHeaderList];
    }];
    
    _listTable.mj_footer = [MJRefreshAutoNormalFooter footerWithRefreshingTarget:self refreshingAction:@selector(requestGoodFooterList)];
    
    
}

#pragma mark== 上拉加载更多
-(void)requestGoodFooterList{
    NSInteger count = _listArray.count;
    NSInteger tmp = (count%pageSize == 0)?count/pageSize:count/pageSize+1;
    tmp += 1;
    if (self.type==1)
    {
        [self requestQueryTechnicianAnswerListWithPage:(int)tmp];
    }
    else
    {
        [self requestAnswerListWithPage:(int)tmp];
    }
}
#pragma mark == 下拉刷新
-(void)requestGoodHeaderList{
    
    
    
    if (self.type==1) {
        [self requestQueryTechnicianAnswerListWithPage:1];
        
    }else
    {
        
        [self requestAnswerListWithPage:1];
        
    }
//
}


#pragma mark == 答主列表
-(void)requestQueryTechnicianAnswerListWithPage:(NSInteger)page
{
    NSDictionary *pageBoDict=@{
        @"pageNum":@(page),
        @"pageSize":@(pageSize),
    };
    NSDictionary *headerDict =@{
        @"token":[UserInfo getUserInfo].token,
    };
    NSString *urlStr = [NSString stringWithFormat:@"%@",queryTechnicianAnswerListApi];
    [HTTPManager postRequestWithUrl:urlStr parameters:pageBoDict header:headerDict progress:^(NSProgress * _Nonnull uploadProgress) {
        
    } success:^(NSDictionary * _Nonnull responseObject) {
        DLog(@"付费咨询答主列表responseObject===%@",responseObject);
        if (page==1) {
            [self.listArray removeAllObjects];
            [self.productIdArray removeAllObjects];
        }
        NSArray *dataArr = responseObject[@"data"];
        NSArray *commendArr = [DazhuSureModel mj_objectArrayWithKeyValuesArray:dataArr];
        for (int i=0; i<commendArr.count; i++) {
            DazhuSureModel *caseModel = commendArr[i];
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
        [self.listTable.mj_header endRefreshing];
        [self.listTable.mj_footer endRefreshing];
        
        
//        if (self.listArray.count==0) {
//            [self.empView showEmptyViewOn:self];
//        }
        
    } other:^(NSDictionary * _Nonnull responseObject) {
        
    } failure:^(NSError * _Nonnull error) {
        
    }];
}


#pragma mark == 问题列表
-(void)requestAnswerListWithPage:(NSInteger)page
{
    NSDictionary *pageBoDict=@{
        @"pageNum":@(page),
        @"pageSize":@(pageSize),
    };
    NSDictionary *headerDict =@{
        @"token":[UserInfo getUserInfo].token,
    };
    NSString *urlStr = [NSString stringWithFormat:@"%@",answerListApi];
    [HTTPManager postRequestWithUrl:urlStr parameters:pageBoDict header:headerDict progress:^(NSProgress * _Nonnull uploadProgress) {
        
    } success:^(NSDictionary * _Nonnull responseObject) {
        DLog(@"付费咨询问题列表responseObject===%@",responseObject);
        
        if (page==1) {
            [self.listArray removeAllObjects];
            [self.productIdArray removeAllObjects];
        }
        
        NSArray *dataArr = responseObject[@"data"];
        NSArray *commendArr = [DazhuListModel mj_objectArrayWithKeyValuesArray:dataArr];
        for (int i=0; i<commendArr.count; i++) {
            DazhuListModel *caseModel = commendArr[i];
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
        [self.listTable.mj_header endRefreshing];
        [self.listTable.mj_footer endRefreshing];
        
        
//        if (self.listArray.count==0) {
//            [self.empView showEmptyViewOn:self];
//        }
        
    } other:^(NSDictionary * _Nonnull responseObject) {
        
    } failure:^(NSError * _Nonnull error) {
        
    }];
}




-(NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 1;
}
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    
    return self.listArray.count;
}
-(CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section
{
    if (self.type==2) {
        return 120.0f;
    }
    return 50.0f;
}

-(UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section
{
    UIView *bgView =[[UIView alloc]initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 44)];
    bgView.backgroundColor = [UIColor whiteColor];
    
    UIButton *dazhuBtn =[UIButton buttonWithType:UIButtonTypeCustom];
    [dazhuBtn setTitle:@"专业答主" forState:UIControlStateNormal];
    dazhuBtn.frame = CGRectMake(15, 12, 80, 25);
    dazhuBtn.titleLabel.font = [UIFont boldSystemFontOfSize:18.0f];
    [dazhuBtn setTitleColor:Number090909Color forState:UIControlStateNormal];
    self.dazhuBtn = dazhuBtn;
    [dazhuBtn addTarget:self action:@selector(dazhuBtnClick:) forControlEvents:UIControlEventTouchUpInside];
    [bgView addSubview:dazhuBtn];
    
    UIView *dzLineView =[[UIView alloc]initWithFrame:CGRectMake(46, CGRectGetMaxY(dazhuBtn.frame)+2, 14, 4)];
    dzLineView.backgroundColor =Number1691E3Color;
    self.dzLineView = dzLineView;
    [bgView addSubview:dzLineView];
    
    
    UIButton *questionBtn =[UIButton buttonWithType:UIButtonTypeCustom];
    [questionBtn setTitle:@"问题广场" forState:UIControlStateNormal];
    questionBtn.frame = CGRectMake(CGRectGetMaxX(dazhuBtn.frame)+20, 12, 80, 25);
    questionBtn.titleLabel.font =FontSize(16);
    [questionBtn setTitleColor:Number090909Color forState:UIControlStateNormal];
    self.questionBtn = questionBtn;
    [questionBtn addTarget:self action:@selector(questionClick:) forControlEvents:UIControlEventTouchUpInside];
    [bgView addSubview:questionBtn];
    
    UIView *questionLineView =[[UIView alloc]initWithFrame:CGRectMake(CGRectGetMinX(questionBtn.frame)+31, CGRectGetMaxY(questionBtn.frame)+2, 14, 4)];
    questionLineView.backgroundColor =Number1691E3Color;
    questionLineView.hidden = YES;
    self.questionLineView = questionLineView;
    [bgView addSubview:questionLineView];
    
    UIButton *allquestionBtn =[UIButton buttonWithType:UIButtonTypeCustom];
    [allquestionBtn setTitle:@"给全国技师提问" forState:UIControlStateNormal];
    allquestionBtn.frame = CGRectMake(15, CGRectGetMaxY(dazhuBtn.frame)+20, SCREEN_WIDTH-30, 44);
    allquestionBtn.titleLabel.font =FontSize(16);
    [allquestionBtn setTitleColor:NumberFFColor forState:UIControlStateNormal];
    [allquestionBtn addTarget:self action:@selector(allquestionBtnClick:) forControlEvents:UIControlEventTouchUpInside];
    allquestionBtn.clipsToBounds = YES;
    allquestionBtn.layer.cornerRadius = 22.0f;
    allquestionBtn.backgroundColor = Number1691E3Color;
    allquestionBtn.hidden = YES;
    self.allquestionBtn = allquestionBtn;
    [bgView addSubview:allquestionBtn];
    
    
    if (self.type==1) {
        self.questionBtn.titleLabel.font =FontSize(16);
        self.questionLineView.hidden = YES;
        
        self.dazhuBtn.titleLabel.font = [UIFont boldSystemFontOfSize:18.0f];
        self.dzLineView.hidden = NO;
        
        self.allquestionBtn.hidden = YES;
    }else
    {
        self.questionBtn.titleLabel.font = [UIFont boldSystemFontOfSize:18.0f];
        self.questionLineView.hidden = NO;
        
        self.dazhuBtn.titleLabel.font =FontSize(16);
        self.dzLineView.hidden = YES;
        self.type = 2;
        self.allquestionBtn.hidden = NO;
    }    
    return bgView;
}


-(void)dazhuBtnClick:(UIButton *)dzBtn
{
    DLog(@"点击答主");
    
    [self.listArray removeAllObjects];
    [self.productIdArray removeAllObjects];
//
    self.type = 1;
    self.questionBtn.titleLabel.font =FontSize(16);
    self.questionLineView.hidden = YES;
    
    self.dazhuBtn.titleLabel.font = [UIFont boldSystemFontOfSize:18.0f];
    self.dzLineView.hidden = NO;
    
    [self requestQueryTechnicianAnswerListWithPage:1];    
}

-(void)questionClick:(UIButton *)questionBtn
{
    DLog(@"点击问题");
//
    [self.listArray removeAllObjects];
    [self.productIdArray removeAllObjects];
    
    self.questionBtn.titleLabel.font = [UIFont boldSystemFontOfSize:18.0f];
    self.questionLineView.hidden = NO;
    
    self.dazhuBtn.titleLabel.font =FontSize(16);
    self.dzLineView.hidden = YES;
    self.type = 2;
    
    [self requestAnswerListWithPage:1];
    
}

-(void)allquestionBtnClick:(UIButton *)btn
{
    DLog(@"点击给全国技师提问");
    SummaryH5ViewController *shopCenterVC =[[SummaryH5ViewController alloc]init];
    shopCenterVC.url = [NSString stringWithFormat:@"%@%@",HOSTH5_IP,askH5url];
    shopCenterVC.hidesBottomBarWhenPushed = YES;
    [self.navigationController pushViewController:shopCenterVC animated:YES];
    
    
}
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (self.type==1) {
        AnswererTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:AnswererTableViewCelliIdentify forIndexPath:indexPath];
        cell.selectionStyle = UITableViewCellSelectionStyleNone;
        DazhuSureModel *model = self.listArray[indexPath.row];
        [cell setModel:model];
        
        return cell;
    }else
    {
        QuestionTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:QuestionTableViewCelliIdentify forIndexPath:indexPath];
        cell.selectionStyle = UITableViewCellSelectionStyleNone;
        
        
        
        DazhuListModel *model = self.listArray[indexPath.row];
        [cell setModel:model];
        
        cell.pangtingBlock = ^(id  _Nonnull obj)
        {
            SummaryH5ViewController *shopCenterVC =[[SummaryH5ViewController alloc]init];
            shopCenterVC.url = [NSString stringWithFormat:@"%@%@?uuid=%@&orderUuid=%@&price=%@",HOSTH5_IP,auditH5url,model.uuid,model.orderUuid,model.consultAmt];
            DLog(@"一元旁听url=====%@",shopCenterVC.url);
            shopCenterVC.hidesBottomBarWhenPushed = YES;
            [self.navigationController pushViewController:shopCenterVC animated:YES];
            
        };
        
        return cell;
    }
    
}
-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return UITableViewAutomaticDimension;
}

-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    
    SummaryH5ViewController *shopCenterVC =[[SummaryH5ViewController alloc]init];
    if (self.type==1){
        DazhuSureModel *model = self.listArray[indexPath.row];
        shopCenterVC.url = [NSString stringWithFormat:@"%@%@?technicianUuid=%@&price=%@",HOSTH5_IP,askH5url,model.uuid,model.answerAmt];
    }else if (self.type==2)
    {
        DazhuListModel *model = self.listArray[indexPath.row];
        shopCenterVC.url = [NSString stringWithFormat:@"%@%@?uuid=%@&orderUuid=%@&price=%@",HOSTH5_IP,auditH5url,model.uuid,model.orderUuid,model.consultAmt];
        DLog(@"shopCenterVC====%@",shopCenterVC.url);
        DLog(@"uuid====%@===orderUuid====%@",model.uuid,model.orderUuid);

    }
    DLog(@"shopCenterVC====%@",shopCenterVC.url);

    shopCenterVC.hidesBottomBarWhenPushed = YES;
    [self.navigationController pushViewController:shopCenterVC animated:YES];
}

- (void)scrollViewDidScroll:(UIScrollView *)scrollView
{
    if (self.DidScrollBlock)
    {
        self.DidScrollBlock(scrollView.contentOffset.y);
    }
}
-(void)initBannerView
{
       
    CGFloat bannerView_X = 15;
    CGFloat bannerView_W = SCREEN_WIDTH-30;
    CGFloat bannerView_H = bannerView_W*0.40;
    CGFloat bannerView_Y = 4;
    
    MallBannerView *bannerView = [[MallBannerView alloc]initWithFrame:CGRectMake(bannerView_X, bannerView_Y, bannerView_W, bannerView_H) imageArray:@[@"my-icon-pic",@"my-icon-pic",@"my-icon-pic"] barnnerType:BannerViewTypeUrlPicture];
    bannerView.backgroundColor =[UIColor blueColor];
    bannerView.clipsToBounds = YES;
    bannerView.layer.cornerRadius = 2.0f;
    self.bannerView = bannerView;
       
       /* 设置分页控件位置居中 */
    bannerView.cycleScrollView.pageControlStyle = SDCycleScrollViewPageContolStyleClassic;
    bannerView.cycleScrollView.pageControlAliment = SDCycleScrollViewPageContolAlimentCenter;
    [self.bannerBGView addSubview:bannerView];
}


#pragma mark == 懒加载
-(UITableView *)listTable
{
    if (!_listTable) {
        _listTable =[[UITableView alloc]initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, self.view.frame.size.height-NavigationBar_Height-SafeAreaBottom_Height-BottomTab_Height) style:UITableViewStyleGrouped];
        _listTable.delegate = self;
        _listTable.dataSource = self;
        _listTable.separatorStyle = UITableViewCellSeparatorStyleNone;
        _listTable.tableHeaderView = self.bannerBGView;
        _listTable.backgroundColor =[UIColor whiteColor];
        [_listTable registerNib:[UINib nibWithNibName:@"AnswererTableViewCell" bundle:nil] forCellReuseIdentifier:AnswererTableViewCelliIdentify];//答主
        [_listTable registerNib:[UINib nibWithNibName:@"QuestionTableViewCell" bundle:nil] forCellReuseIdentifier:QuestionTableViewCelliIdentify];//可选店铺时间
        _listTable.estimatedRowHeight = 10.0f;
        _listTable.showsVerticalScrollIndicator = NO;


    }
    return _listTable;
}
-(UIView *)bannerBGView
{
    if (!_bannerBGView) {
        _bannerBGView =[[UIView alloc]initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, (SCREEN_WIDTH-30)*0.40)];
        _bannerBGView.backgroundColor = [UIColor whiteColor];
    }
    return _bannerBGView;
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
