//
//  DesignatedDriverVController.m
//  CarStoreProject
//
//  Created by apple on 2021/1/25.
//

#import "DesignatedDriverVController.h"
#import "SubstituteTableViewCell.h"
#import <CoreLocation/CoreLocation.h>

@interface DesignatedDriverVController ()<UITableViewDelegate,UITableViewDataSource,CLLocationManagerDelegate>
{

    CLLocationManager*locationmanager;//定位服务

    NSString*strlatitude;//经度

    NSString*strlongitude;//纬度

}
@property (nonatomic,strong)UITableView *listTable;
@end

@implementation DesignatedDriverVController
static NSString * const SubstituteTableViewCelliIdentify = @"SubstituteTableViewCell";//答主

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    self.title = @"代驾详情";
    [self.navigationController.navigationBar setTitleTextAttributes:
         @{NSFontAttributeName:[UIFont systemFontOfSize:18.0f],
           NSForegroundColorAttributeName:Number090909Color}];
    [self.view addSubview:self.listTable];
    self.navigationController.navigationBar.barTintColor = NumberEDF5FBColor;

    self.view.backgroundColor =NumberEDF5FBColor;

    UserModel *model =[UserInfo getUserInfo];

    if (![SafeValue([NSString stringWithFormat:@"%@",model.token])isEqualToString:@""]) {
        [self startLocation];
    }
    
//    [self creatXuanfu];
}
#pragma mark == 悬浮按钮
-(void)creatXuanfu
{
    UIButton*btn=[[UIButton alloc]initWithFrame:CGRectMake(SCREEN_WIDTH-60,(SCREEN_HEIGHT-60)/2,60,60)];
    
    btn.backgroundColor=Number1691E3Color;
//    btn.layer.borderWidth = 1;
//    btn.layer.borderColor = [UIColor blackColor].CGColor;
    [btn setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    [btn setTitle:@"检" forState:UIControlStateNormal];
    btn.titleLabel.font = FontSize(18);
    
    btn.layer.cornerRadius=30;
    [btn addTarget:self action:@selector(btnClick) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:btn];
    
    UIPanGestureRecognizer *panGestureRecognizer = [[UIPanGestureRecognizer alloc]
                                                    
                                                    initWithTarget:self
                                                    
                                                    action:@selector(handlePan:)];
    
    [btn addGestureRecognizer:panGestureRecognizer];
}

-(void)btnClick
{
    DLog(@"点击浮窗");
    SummaryH5ViewController *shopCenterVC =[[SummaryH5ViewController alloc]init];
    
    shopCenterVC.url = [NSString stringWithFormat:@"%@%@",HOSTH5_IP,addDetectionH5url];
    DLog(@"添加检测shopCenterVC===%@",shopCenterVC.url);
 
    shopCenterVC.hidesBottomBarWhenPushed = YES;
    [self.navigationController pushViewController:shopCenterVC animated:YES];
}

- (void) handlePan:(UIPanGestureRecognizer*) recognizer

{
    
    CGPoint translation = [recognizer translationInView:self.view];
    
    CGFloat centerX=recognizer.view.center.x+ translation.x;
    
    CGFloat centerY=recognizer.view.center.y+ translation.y;

    CGFloat thecenter=0;
    
    CGFloat thecenterY=0;

    
    recognizer.view.center=CGPointMake(centerX,centerY);
    
    [recognizer setTranslation:CGPointZero inView:self.view];
    
    if(recognizer.state==UIGestureRecognizerStateEnded|| recognizer.state==UIGestureRecognizerStateCancelled) {
        
        if(centerX>SCREEN_WIDTH/2) {
            
            thecenter=SCREEN_WIDTH-60/2;
            
        }else{
            
            thecenter=60/2;
            
        }
        if (centerY<NavigationBar_Height) {
            thecenterY= NavigationBar_Height;
            
            [UIView animateWithDuration:0.3 animations:^{
                
                recognizer.view.center=CGPointMake(thecenter,NavigationBar_Height+30);
                
            }];
        }
        else if (centerY > (SCREEN_HEIGHT-BottomTab_Height-SafeAreaBottom_Height))
        {
            [UIView animateWithDuration:0.3 animations:^{
                
                recognizer.view.center=CGPointMake(thecenter,SCREEN_HEIGHT-BottomTab_Height-SafeAreaBottom_Height-30);
                
            }];
        }
        
        else
        {
            [UIView animateWithDuration:0.3 animations:^{
                
                recognizer.view.center=CGPointMake(thecenter,centerY);
                
            }];
        }
        
        
        
    }
}



- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    
    return 4;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    SubstituteTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:SubstituteTableViewCelliIdentify forIndexPath:indexPath];
    cell.selectionStyle = UITableViewCellSelectionStyleNone;
    [cell configSetTableViewCellIndex:indexPath];
    return cell;
    
}
-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return UITableViewAutomaticDimension;
}
-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    [AppUtils showInfoMessage:@"该功能暂未开通，敬请期待"];
}

#pragma mark == 懒加载
-(UITableView *)listTable
{
    if (!_listTable) {
        _listTable =[[UITableView alloc]initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, self.view.frame.size.height-BottomTab_Height-SafeAreaBottom_Height) style:UITableViewStylePlain];
        _listTable.delegate = self;
        _listTable.dataSource = self;
        _listTable.separatorStyle = UITableViewCellSeparatorStyleNone;
        _listTable.backgroundColor =[UIColor whiteColor];
        [_listTable registerNib:[UINib nibWithNibName:@"SubstituteTableViewCell" bundle:nil] forCellReuseIdentifier:SubstituteTableViewCelliIdentify];//答主
        _listTable.estimatedRowHeight = 10.0f;
        _listTable.showsVerticalScrollIndicator = NO;
        _listTable.backgroundColor =NumberEDF5FBColor;
    }
    return _listTable;
}

#pragma mark == 定位
//开始定位
-(void) startLocation
{
    //判断定位功能是否打开
    if ([CLLocationManager locationServicesEnabled]) {
        locationmanager = [[CLLocationManager alloc]init];
        locationmanager.delegate = self;
        [locationmanager requestAlwaysAuthorization];
        [locationmanager requestWhenInUseAuthorization];
        
        //设置寻址精度
        locationmanager.desiredAccuracy = kCLLocationAccuracyBest;
        locationmanager.distanceFilter = 5.0;
        [locationmanager startUpdatingLocation];
    }
}

#pragma mark CoreLocation delegate (定位失败)
//定位失败后调用此代理方法
-(void)locationManager:(CLLocationManager *)manager didFailWithError:(NSError *)error
{
    //设置提示提醒用户打开定位服务
    [[SystemAlertView shareInstance] showAlert:@"提示" message:@"定位服务当前可能尚未打开，请设置打开！" cancelTitle:nil titleArray:nil viewController:nil confirm:^(NSInteger buttonTag) {
    }];
}

#pragma mark 定位成功后则执行此代理方法
-(void)locationManager:(CLLocationManager *)manager didUpdateLocations:(NSArray<CLLocation *> *)locations
{
    [locationmanager stopUpdatingHeading];
    //旧址
    CLLocation *currentLocation = [locations lastObject];
    CLGeocoder *geoCoder = [[CLGeocoder alloc]init];
    //打印当前的经度与纬度
    NSLog(@"当前位置经度===%f,当前位置纬度===%f",currentLocation.coordinate.latitude,currentLocation.coordinate.longitude);
    
    
    strlatitude =[NSString stringWithFormat:@"%f",currentLocation.coordinate.latitude];
    strlongitude =[NSString stringWithFormat:@"%f",currentLocation.coordinate.longitude];

    [self requestPositionReportWithLatiude:strlatitude longitude:strlongitude];
    //反地理编码
    [geoCoder reverseGeocodeLocation:currentLocation completionHandler:^(NSArray<CLPlacemark *> * _Nullable placemarks, NSError * _Nullable error)
    {
        NSLog(@"反地理编码");
        NSLog(@"反地理编码%lu",(unsigned long)placemarks.count);
        if (placemarks.count > 0) {
            CLPlacemark *placeMark = placemarks[0];
//            self.label_city.text = placeMark.locality;
//            if (!self.label_city.text) {
//                self.label_city.text = @"无法定位当前城市";
//            }
            /*看需求定义一个全局变量来接收赋值*/
            NSLog(@"城市----%@",placeMark.country);//当前国家
//            NSLog(@"城市%@",self.label_city.text);//当前的城市
            NSLog(@"%@",placeMark.subLocality);//当前的位置
            NSLog(@"%@",placeMark.thoroughfare);//当前街道
            NSLog(@"%@",placeMark.name);//具体地址
            
        }
    }];
}
-(void)uploadingLocationView
{
    [self requestPositionReportWithLatiude:strlatitude longitude:strlongitude];
    
}

-(void)requestPositionReportWithLatiude:(NSString *)latiude longitude:(NSString *)longitude
{
    NSDictionary *dict= @{
        @"latitude":latiude,
        @"longitude":longitude,
    };
    NSDictionary *headerDict =@{
        @"token":[UserInfo getUserInfo].token,
    };
    NSString *urlStr = [NSString stringWithFormat:@"%@",positionReportApi];
    [HTTPManager postRequestWithUrl:urlStr parameters:dict header:headerDict progress:^(NSProgress * _Nonnull uploadProgress) {
    } success:^(NSDictionary * _Nonnull responseObject)
    {
        DLog(@"上传当前位置responseObject====%@",responseObject);
    } other:^(NSDictionary * _Nonnull responseObject)
    {
        DLog(@"responseObject===%@",responseObject);
        [ZJProgressHUD showStatus:responseObject[@"msg"] andAutoHideAfterTime:2.0];

    } failure:^(NSError * _Nonnull error)
     {
        DLog(@"上传当前位置出错error===%@",error);
    }];
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
