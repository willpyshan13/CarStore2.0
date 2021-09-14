//
//  TechniciansCaseVController.m
//  CarOwnerProject
//
//  Created by apple on 2021/1/26.
//

#import "TechniciansCaseVController.h"
#import "TechnicianCaseTableViewCell.h"
//#import "MallBannerView.h"

#import "JishiCaseModel.h"
#import "SelectRidersAlertView.h"
#import "SelectRidersTModel.h"//筛选品牌、系统
#import "SelectVehicleConfigModel.h"//车型
#import "SelectRidersCarAlertView.h"


@interface TechniciansCaseVController ()<UITableViewDelegate,UITableViewDataSource,SelectRidersAlertViewDelegate,SelectRidersCarAlertViewDelegate>
@property (nonatomic,strong)UIView *bannerBGView;//轮播图
//@property (nonatomic,strong)MallBannerView *bannerView;
@property (nonatomic,strong)NSMutableArray *listArray;
@property (nonatomic,strong)EmptyView *empView;

@property (nonatomic,strong)SelectRidersCarAlertView*selCarView;//选择车型

//品牌、
@property (nonatomic,strong)NSMutableArray *pinpaiListArray;
//车型
@property (nonatomic,strong)NSMutableArray *selCarListArray;
//系统
@property (nonatomic,strong)NSMutableArray *selListArray;


@property (nonatomic,assign)NSInteger type;//1、品牌 2、车型 3、系统

@property (nonatomic,copy)NSString *attachSys;//系统
@property (nonatomic,copy)NSString *brandUuid;//品牌
@property (nonatomic,copy)NSString *carmodel;//车型

@property (nonatomic,strong)FSCustomButton*pinpanBtn;
@property (nonatomic,strong)FSCustomButton*chexingBtn;
@property (nonatomic,strong)FSCustomButton*xitongBtn;

@property (nonatomic,copy)NSString *systemStr;//系统
@property (nonatomic,copy)NSString *brandStr;//品牌
@property (nonatomic,copy)NSString *modelStr;//车型

@end

@implementation TechniciansCaseVController
{
    int pageSize;
}
static NSString * const TechnicianCaseTableViewCelliIdentify = @"TechnicianCaseTableViewCell";//技师案例
-(void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    self.navigationController.navigationBar.hidden = NO;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    self.title = @"技师案例";
    [self.navigationController.navigationBar setTitleTextAttributes:
         @{NSFontAttributeName:[UIFont systemFontOfSize:18.0f],
           NSForegroundColorAttributeName:Number090909Color}];
    
    [self.view addSubview:self.listTable];
    
    [self updateLeftNavBarBtnItemWithImage:@"back_bg1" target:self selector:@selector(leftNavBarBtnPressed:)];
    
    self.attachSys=@"";
    self.brandUuid=@"";
    self.carmodel=@"";
    
    self.systemStr=@"全部系统   ";
    self.brandStr=@"全部品牌   ";
    self.modelStr=@"全部车型   ";
    
    pageSize = 10;
    self.type = 0;
    
    [self requestQueryCaseForTechnicianListWithPage:1 attachSys:self.attachSys brandUuid:self.brandUuid carModel:self.carmodel];
    
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
    [self requestQueryCaseForTechnicianListWithPage:(int)tmp attachSys:self.attachSys brandUuid:self.brandUuid carModel:self.carmodel];
}
#pragma mark == 下拉刷新
-(void)requestGoodHeaderList{
    
    [self.listArray removeAllObjects];
    [self requestQueryCaseForTechnicianListWithPage:1 attachSys:self.attachSys brandUuid:self.brandUuid carModel:self.carmodel];
    [self.listTable.mj_header endRefreshing];
}

-(void)requestQueryCaseForTechnicianListWithPage:(NSInteger)page attachSys:(NSString *)attachSys brandUuid:(NSString *)brandUuid carModel:(NSString *)carmodel
{
    NSDictionary *pageBoDict=@{
        @"pageNum":@(page),
        @"pageSize":@(pageSize),
        @"attachSys":attachSys,//所属系统 ,
        @"brandUuid":brandUuid,//品牌uuid ,
        @"model":carmodel,//车型 ,
    };
    DLog(@"pageBoDict===%@",pageBoDict);
    NSDictionary *headerDict =@{
        @"token":[UserInfo getUserInfo].token,
    };
    NSString *urlStr = [NSString stringWithFormat:@"%@",queryCaseForVehicleListApi];
    [HTTPManager postRequestWithUrl:urlStr parameters:pageBoDict header:headerDict progress:^(NSProgress * _Nonnull uploadProgress) {
        
    } success:^(NSDictionary * _Nonnull responseObject) {
        DLog(@"车主端技师案例responseObject===%@",responseObject);
        NSArray *dataArr = responseObject[@"data"];
        NSArray *commendArr = [JishiCaseModel mj_objectArrayWithKeyValuesArray:dataArr];
        
        DLog(@"listArra===%ldcommendArr===%ld",dataArr.count,commendArr.count);

        [self.listArray removeAllObjects];
        for (int i=0; i<commendArr.count; i++) {
            JishiCaseModel *caseModel = commendArr[i];
            
            [self.listArray addObject:caseModel];
        }
        DLog(@"listArra===%ld",self.listArray.count);
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
    return 94.0f;
}

-(UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section
{
    UIView *bgView =[[UIView alloc]initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 94)];
    bgView.backgroundColor = [UIColor whiteColor];
    
    FSCustomButton*pinpanBtn =[FSCustomButton buttonWithType:UIButtonTypeCustom];
    pinpanBtn.frame = CGRectMake(15, 10, (SCREEN_WIDTH-15*4)/3, 32);
    [pinpanBtn setTitle:self.brandStr forState:UIControlStateNormal];
    pinpanBtn.buttonImagePosition =FSCustomButtonImagePositionRight;
    [pinpanBtn setTitleColor:Number090909Color forState:UIControlStateNormal];
    [pinpanBtn setImage:kImageNamed(@"js_arrowdown_icon") forState:UIControlStateNormal];
    
    [pinpanBtn addTarget:self action:@selector(pinpaiBtnClick:) forControlEvents:UIControlEventTouchUpInside];
    pinpanBtn.clipsToBounds = YES;
    pinpanBtn.layer.borderWidth = 1.0f;
    pinpanBtn.layer.borderColor = NumberF1F1F1Color.CGColor;
    pinpanBtn.layer.cornerRadius = 2.0f;
    pinpanBtn.titleLabel.font = FontSize(14);
    pinpanBtn.tag = 66;
    self.pinpanBtn = pinpanBtn;
    [bgView addSubview:pinpanBtn];
    
    
    FSCustomButton *chexingBtn =[FSCustomButton buttonWithType:UIButtonTypeCustom];
    chexingBtn.frame = CGRectMake(CGRectGetMaxX(pinpanBtn.frame)+15, 10, (SCREEN_WIDTH-15*4)/3, 32);
    [chexingBtn setTitle:self.modelStr forState:UIControlStateNormal];
    [chexingBtn setImage:kImageNamed(@"js_arrowdown_icon") forState:UIControlStateNormal];
    chexingBtn.buttonImagePosition =FSCustomButtonImagePositionRight;
    chexingBtn.tag = 67;
    [chexingBtn addTarget:self action:@selector(pinpaiBtnClick:) forControlEvents:UIControlEventTouchUpInside];

    [chexingBtn setTitleColor:Number090909Color forState:UIControlStateNormal];
    chexingBtn.clipsToBounds = YES;
    chexingBtn.layer.borderWidth = 1.0f;
    chexingBtn.layer.borderColor = NumberF1F1F1Color.CGColor;
    chexingBtn.layer.cornerRadius = 2.0f;
    chexingBtn.titleLabel.font = FontSize(14);

    self.chexingBtn = chexingBtn;
    [bgView addSubview:chexingBtn];
    
    FSCustomButton *xitongBtn =[FSCustomButton buttonWithType:UIButtonTypeCustom];
    xitongBtn.frame = CGRectMake(CGRectGetMaxX(chexingBtn.frame)+15, 10, (SCREEN_WIDTH-15*4)/3, 32);
    [xitongBtn setTitle:self.systemStr forState:UIControlStateNormal];
    [xitongBtn setTitleColor:Number090909Color forState:UIControlStateNormal];
    [xitongBtn setImage:kImageNamed(@"js_arrowdown_icon") forState:UIControlStateNormal];
    xitongBtn.buttonImagePosition =FSCustomButtonImagePositionRight;
    xitongBtn.tag = 68;
    [xitongBtn addTarget:self action:@selector(pinpaiBtnClick:) forControlEvents:UIControlEventTouchUpInside];
    xitongBtn.clipsToBounds = YES;
    xitongBtn.layer.borderWidth = 1.0f;
    xitongBtn.layer.borderColor = NumberF1F1F1Color.CGColor;
    xitongBtn.layer.cornerRadius = 2.0f;
    xitongBtn.titleLabel.font = FontSize(14);

    self.xitongBtn = xitongBtn;
    [bgView addSubview:xitongBtn];
    
    
    UILabel *anliLab=[[UILabel alloc]initWithFrame:CGRectMake(15, CGRectGetMaxY(pinpanBtn.frame)+30, SCREEN_WIDTH-30, 25)];
    anliLab.text = @"维修技师-优秀案例";
    anliLab.textColor = Number090909Color;
    anliLab.font = [UIFont boldSystemFontOfSize:16.0f];
    [bgView addSubview:anliLab];
    
    return bgView;
}
-(void)pinpaiBtnClick:(FSCustomButton *)btn
{
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
        
        [self.listTable reloadData];

    } other:^(NSDictionary * _Nonnull responseObject) {
        [AppUtils showErrorMessage:responseObject[@"msg"]];
        
    } failure:^(NSError * _Nonnull error) {
        
    }];
}
//系统
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
        [self.pinpanBtn setTitle:model.configName forState:UIControlStateNormal];
        self.brandStr=model.configName;
        self.modelStr =@"全部车型 ";
        
        [self requestVehicleConfigQueryAllList];
    }else if (self.type==3)
    {
        self.attachSys =SafeValue([NSString stringWithFormat:@"%@",model.uuid]);
        [self requestQueryCaseForTechnicianListWithPage:1 attachSys:self.attachSys brandUuid:self.brandUuid carModel:self.carmodel];
        [self.xitongBtn setTitle:model.lableDesc forState:UIControlStateNormal];
        self.systemStr=model.lableDesc;
    }
    
    
}

#pragma mark == 选中的车型
-(void)getSelectCarType:(SelectRidersTModel *)model
{
    self.carmodel =SafeValue([NSString stringWithFormat:@"%@",model.uuid]);
    [self requestQueryCaseForTechnicianListWithPage:1 attachSys:self.attachSys brandUuid:self.brandUuid carModel:self.carmodel];
    [self.chexingBtn setTitle:model.configName forState:UIControlStateNormal];
    self.modelStr=model.configName;
}


-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{

     
    SummaryH5ViewController *shopCenterVC =[[SummaryH5ViewController alloc]init];
    JishiCaseModel *model = self.listArray[indexPath.row];
    shopCenterVC.url = [NSString stringWithFormat:@"%@%@?caseUuid=%@&price=%@",HOSTH5_IP,caseH5url,model.uuid,model.amt];
    DLog(@"caseH5urlcaseH5url====%@",shopCenterVC.url);
 
    shopCenterVC.hidesBottomBarWhenPushed = YES;
    [self.navigationController pushViewController:shopCenterVC animated:YES];

}


- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    TechnicianCaseTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:TechnicianCaseTableViewCelliIdentify forIndexPath:indexPath];
    cell.selectionStyle = UITableViewCellSelectionStyleNone;
    JishiCaseModel *model = self.listArray[indexPath.row];
    [cell setModel:model];
    return cell;
    
}
-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return UITableViewAutomaticDimension;
}





#pragma mark == 懒加载
-(UITableView *)listTable
{
    if (!_listTable) {
        _listTable =[[UITableView alloc]initWithFrame:CGRectMake(0, NavigationBar_Height, SCREEN_WIDTH, self.view.frame.size.height-NavigationBar_Height) style:UITableViewStyleGrouped];
        _listTable.delegate = self;
        _listTable.dataSource = self;
        _listTable.separatorStyle = UITableViewCellSeparatorStyleNone;
//        _listTable.tableHeaderView = self.bannerBGView;
        _listTable.backgroundColor =[UIColor whiteColor];
        [_listTable registerNib:[UINib nibWithNibName:@"TechnicianCaseTableViewCell" bundle:nil] forCellReuseIdentifier:TechnicianCaseTableViewCelliIdentify];//技师案例
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



-(EmptyView *)empView
{
    if (!_empView) {
        _empView =[EmptyView initEmptyView];
        [_empView configFrame:self.listTable.frame];
    }
    return _empView;
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





/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
