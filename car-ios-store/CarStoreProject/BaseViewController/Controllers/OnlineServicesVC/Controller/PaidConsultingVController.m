//
//  PaidConsultingVController.m
//  CarOwnerProject
//
//  Created by apple on 2021/1/26.
//

#import "PaidConsultingVController.h"
#import "AnswererTableViewCell.h"//答主
#import "QuestionTableViewCell.h"//问题
#import "DazhuListModel.h"//问题列表
#import "DazhuSureModel.h"//答主列表

@interface PaidConsultingVController ()<UITableViewDelegate,UITableViewDataSource>
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
-(void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    self.navigationController.navigationBar.hidden = NO;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    self.title = @"技师咨询";
    [self.navigationController.navigationBar setTitleTextAttributes:
         @{NSFontAttributeName:[UIFont systemFontOfSize:18.0f],
           NSForegroundColorAttributeName:Number090909Color}];
    
    [self updateLeftNavBarBtnItemWithImage:@"back_bg1" target:self selector:@selector(leftNavBarBtnPressed:)];
    
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
    
    [self.listArray removeAllObjects];
    [self.productIdArray removeAllObjects];
    
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
//    [AppUtils showInfoMessage:@"该功能暂未开通，敬请期待"];
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
//    [AppUtils showInfoMessage:@"该功能暂未开通，敬请期待"];
  
     
    SummaryH5ViewController *shopCenterVC =[[SummaryH5ViewController alloc]init];
    if (self.type==1){
        DazhuSureModel *model = self.listArray[indexPath.row];
        shopCenterVC.url = [NSString stringWithFormat:@"%@%@?technicianUuid=%@&price=%@",HOSTH5_IP,askH5url,model.uuid,model.answerAmt];
    }else if (self.type==2)
    {
        DazhuListModel *model = self.listArray[indexPath.row];
        shopCenterVC.url = [NSString stringWithFormat:@"%@%@?uuid=%@&orderUuid=%@&price=%@",HOSTH5_IP,auditH5url,model.uuid,model.orderUuid,model.consultAmt];
    }
    shopCenterVC.hidesBottomBarWhenPushed = YES;
    [self.navigationController pushViewController:shopCenterVC animated:YES];
}


#pragma mark == 懒加载
-(UITableView *)listTable
{
    if (!_listTable) {
        _listTable =[[UITableView alloc]initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, self.view.frame.size.height) style:UITableViewStyleGrouped];
        _listTable.delegate = self;
        _listTable.dataSource = self;
        _listTable.separatorStyle = UITableViewCellSeparatorStyleNone;
        _listTable.backgroundColor =[UIColor whiteColor];
        [_listTable registerNib:[UINib nibWithNibName:@"AnswererTableViewCell" bundle:nil] forCellReuseIdentifier:AnswererTableViewCelliIdentify];//答主
        [_listTable registerNib:[UINib nibWithNibName:@"QuestionTableViewCell" bundle:nil] forCellReuseIdentifier:QuestionTableViewCelliIdentify];//可选店铺时间
        _listTable.estimatedRowHeight = 10.0f;
        _listTable.showsVerticalScrollIndicator = NO;


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
