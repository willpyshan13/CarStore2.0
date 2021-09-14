//
//  DesignatedDriverVController.m
//  CarOwnerProject
//
//  Created by apple on 2021/1/25.
//

#import "DesignatedDriverVController.h"
#import "MallBannerView.h"
#import "SelectCityViewController.h"
#import <CoreLocation/CoreLocation.h>
#import "SystemAlertView.h"
#import "BaseWKWebView.h"
#import <IQKeyboardManager.h>

@interface DesignatedDriverVController ()<UITextFieldDelegate,CityListViewDelegate,CLLocationManagerDelegate,JSBridgeDelegate>
{

    CLLocationManager*locationmanager;//定位服务

    NSString*strlatitude;//经度

    NSString*strlongitude;//纬度

}
@property (nonatomic,strong)UIScrollView *bgScrollView;
@property (nonatomic,strong)MallBannerView *bannerView;

@property (nonatomic,strong)UIButton *nowBtn;//现在
@property (nonatomic,strong)UIView *nowLineView;//现在线
@property (nonatomic,strong)UIButton *yuyueBtn;//预约
@property (nonatomic,strong)UIView *yuyueLineView;//预约线
@property (nonatomic,strong)FSCustomButton *addressBtn;//地址

@property (nonatomic, strong) BaseWKWebView *wkWebView;

@property (nonatomic,strong) UIView *empView;

@end

@implementation DesignatedDriverVController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    self.navigationItem.title = @"嘟一家";
    [self.navigationController.navigationBar setTitleTextAttributes:
         @{NSFontAttributeName:[UIFont systemFontOfSize:18.0f],
           NSForegroundColorAttributeName:Number090909Color}];
    [self creatNav];
    [self creatUI];
}
-(void)creatNav{
    UIView *leftView=[[UIView alloc]initWithFrame:CGRectMake(0, 0, 100, 30)];
    UIImage *addressImg=kImageNamed(@"homepage_down_icon");
    FSCustomButton *addressBtn =[FSCustomButton buttonWithType:UIButtonTypeCustom];
    addressBtn.frame = CGRectMake(-10, 0, 80, 30);
    [addressBtn setImage:addressImg forState:UIControlStateNormal];
    [addressBtn addTarget:self action:@selector(addressBtnClick) forControlEvents:UIControlEventTouchUpInside];
    addressBtn.buttonImagePosition =FSCustomButtonImagePositionRight;
    NSString *cityName = [[NSUserDefaults standardUserDefaults]objectForKey:changeCityName];
    if ([SafeValue(cityName)isEqualToString:@""]) {
        [addressBtn setTitle:@"上海市 " forState:UIControlStateNormal];
    }else
    {
        [addressBtn setTitle:[NSString stringWithFormat:@"%@ ",cityName] forState:UIControlStateNormal];
    }
    
    addressBtn.titleLabel.font = FontSize(16);
    self.addressBtn = addressBtn;
    [leftView addSubview:addressBtn];
    
    UIBarButtonItem *leftItem = [[UIBarButtonItem alloc]initWithCustomView:leftView];
    UIBarButtonItem *spaceItem = [[UIBarButtonItem alloc]initWithBarButtonSystemItem:UIBarButtonSystemItemFixedSpace target:nil action:nil];
    spaceItem.width = -15;
    self.navigationItem.leftBarButtonItems = @[spaceItem,leftItem];
    
    UserModel *model =[UserInfo getUserInfo];

    if (![SafeValue([NSString stringWithFormat:@"%@",model.token])isEqualToString:@""]) {
        [self startLocation];
    }

    
}

-(void)addressBtnClick
{
    DLog(@"地址");
    SelectCityViewController *selectCityVC =[[SelectCityViewController alloc]init];
    selectCityVC.hidesBottomBarWhenPushed = YES;
    
    selectCityVC.delegate = self;
    //热门城市列表
//    selectCityVC.arrayHotCity = [NSMutableArray arrayWithObjects:@"北京市",@"深圳市",@"广州市",@"杭州市",@"上海市",@"成都市", nil];
    //定位城市列表
    selectCityVC.arrayLocatingCity   = [NSMutableArray arrayWithObjects:@"上海", nil];
    
    [self.navigationController pushViewController:selectCityVC animated:YES];
}

- (void)didClickedWithCityName:(NSString*)cityName
{
    DLog(@"cityName====%@",cityName);
    [self.addressBtn setTitle:[NSString stringWithFormat:@"%@ ",cityName] forState:UIControlStateNormal];
}

-(void)creatUI
{
    UIScrollView *bgScrollView=[[UIScrollView alloc]initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT)];
    bgScrollView.backgroundColor=NumberEDF5FBColor;
    bgScrollView.showsVerticalScrollIndicator = NO;
    bgScrollView.contentSize = CGSizeMake(0, SCREEN_HEIGHT+100);
    self.bgScrollView = bgScrollView;
    [self.view addSubview:bgScrollView];
    
    CGFloat bannerView_W = SCREEN_WIDTH;
    CGFloat bannerView_H = bannerView_W*0.23;
    CGFloat bannerView_Y = 0;
    
    MallBannerView *bannerView = [[MallBannerView alloc]initWithFrame:CGRectMake(0, bannerView_Y, bannerView_W, bannerView_H) imageArray:@[@"my-icon-pic",@"my-icon-pic",@"my-icon-pic"] barnnerType:BannerViewTypeUrlPicture];
    bannerView.backgroundColor =[UIColor blueColor];
    bannerView.clipsToBounds = YES;
    bannerView.layer.cornerRadius = 2.0f;
    self.bannerView = bannerView;
       
       /* 设置分页控件位置居中 */
    bannerView.cycleScrollView.pageControlStyle = SDCycleScrollViewPageContolStyleClassic;
    bannerView.cycleScrollView.pageControlAliment = SDCycleScrollViewPageContolAlimentCenter;

    [bgScrollView addSubview:bannerView];
    
    UIView *empView=[[UIView alloc]initWithFrame:CGRectMake(0, CGRectGetMaxY(bannerView.frame), SCREEN_WIDTH, 300)];
    empView.backgroundColor=[UIColor whiteColor];
    [bgScrollView addSubview:empView];
    self.empView=empView;
    
    
    _wkWebView = [[BaseWKWebView alloc] initWithFrame:(CGRect){0,0,SCREEN_WIDTH,300} controller:self methods:nil];
    _wkWebView.bridgeDelegate = self;
    _wkWebView.cleanCache=YES;
    [_wkWebView loadUrl:[NSString stringWithFormat:@"%@%@",HOSTH5_IP,daijiaMapH5url]];
    [empView addSubview:_wkWebView];

    
//    UIImage *empImg =kImageNamed(@"homepage_emp_icon");
//    UIImageView *empImage=[[UIImageView alloc]initWithFrame:CGRectMake((SCREEN_WIDTH-empImg.size.width)/2, 102, empImg.size.width, empImg.size.height)];
//    empImage.image = empImg;
//    [empView addSubview:empImage];
//    UILabel *empLab=[[UILabel alloc]initWithFrame:CGRectMake(0, CGRectGetMaxY(empImage.frame)+14, SCREEN_WIDTH, 20)];
//    empLab.text=@"您所在的城市暂未开通，敬请期待~";
//    empLab.textColor = Number999999Color;
//    empLab.font = FontSize(12.f);
//    empLab.textAlignment = NSTextAlignmentCenter;
//    [empView addSubview:empLab];
    
    //司机相关 start
    UIView *sijiView=[[UIView alloc]initWithFrame:CGRectMake(0, CGRectGetMaxY(empView.frame), SCREEN_WIDTH, 40)];
    sijiView.clipsToBounds = YES;
    sijiView.layer.cornerRadius = 10.0f;
    [bgScrollView addSubview:sijiView];
    
    UIButton *sijiBtn =[UIButton buttonWithType:UIButtonTypeCustom];
    sijiBtn.frame =CGRectMake(15, 10, 50, 20);
    [sijiBtn setImage:kImageNamed(@"homepage_siji_icon") forState:UIControlStateNormal];
    [sijiBtn setTitle:@"代驾" forState:UIControlStateNormal];
    sijiBtn.titleLabel.font = FontSize(14);
    [sijiBtn setTitleColor:Number1684E3Color forState:UIControlStateNormal];
    [sijiView addSubview:sijiBtn];
    
    UILabel *sijiLab=[[UILabel alloc]initWithFrame:CGRectMake(CGRectGetMaxX(sijiBtn.frame)+5, 0, SCREEN_WIDTH-CGRectGetMaxX(sijiBtn.frame)-10, 40)];
    sijiLab.text=@"不辜负每一程相遇";
    sijiLab.textColor = Number999999Color;
    sijiLab.font = FontSize(12.f);
    sijiLab.textAlignment = NSTextAlignmentLeft;
    sijiLab.textColor = Number1684E3Color;
    [sijiView addSubview:sijiLab];
    
    //司机相关 end
    
    //预约切换 start
    UIView *bgView =[[UIView alloc]initWithFrame:CGRectMake(0, CGRectGetMaxY(sijiView.frame), SCREEN_WIDTH, 44)];
    bgView.backgroundColor = [UIColor clearColor];
    
    UIButton *nowBtn =[UIButton buttonWithType:UIButtonTypeCustom];
    [nowBtn setTitle:@"现在" forState:UIControlStateNormal];
    nowBtn.frame = CGRectMake(15, 8, 40, 25);
    nowBtn.titleLabel.font = [UIFont boldSystemFontOfSize:18.0f];
    [nowBtn setTitleColor:Number090909Color forState:UIControlStateNormal];
    self.nowBtn = nowBtn;
    [nowBtn addTarget:self action:@selector(nowBtnClick:) forControlEvents:UIControlEventTouchUpInside];
    [bgView addSubview:nowBtn];
    
    UIView *nowLineView =[[UIView alloc]initWithFrame:CGRectMake(26, CGRectGetMaxY(nowBtn.frame)+2, 14, 4)];
    nowLineView.backgroundColor =Number1691E3Color;
    self.nowLineView = nowLineView;
    [bgView addSubview:nowLineView];
    
    
    UIButton *yuyueBtn =[UIButton buttonWithType:UIButtonTypeCustom];
    [yuyueBtn setTitle:@"预约" forState:UIControlStateNormal];
    yuyueBtn.frame = CGRectMake(CGRectGetMaxX(nowBtn.frame)+31, 8, 40, 25);
    yuyueBtn.titleLabel.font =FontSize(16);
    [yuyueBtn setTitleColor:Number090909Color forState:UIControlStateNormal];
    self.yuyueBtn = yuyueBtn;
    [yuyueBtn addTarget:self action:@selector(yuyueClick:) forControlEvents:UIControlEventTouchUpInside];
    [bgView addSubview:yuyueBtn];
    
    UIView *yuyueLineView =[[UIView alloc]initWithFrame:CGRectMake(CGRectGetMinX(yuyueBtn.frame)+11, CGRectGetMaxY(yuyueBtn.frame)+2, 14, 4)];
    yuyueLineView.backgroundColor =Number1691E3Color;
    yuyueLineView.hidden = YES;
    self.yuyueLineView = yuyueLineView;
    [bgView addSubview:yuyueLineView];

    [bgScrollView addSubview:bgView];
    //预约切换 end
    
    //行程start
    UIView *tripView =[[UIView alloc]initWithFrame:CGRectMake(10, CGRectGetMaxY(bgView.frame)+4, SCREEN_WIDTH-20, 146)];
    tripView.backgroundColor=[UIColor whiteColor];
    tripView.clipsToBounds = YES;
    tripView.layer.cornerRadius = 8.0f;
    [bgScrollView addSubview:tripView];
    
    UIView *startView =[[UIView alloc]initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 67)];
    [tripView addSubview:startView];
    
    
    UIImage *startImg =kImageNamed(@"homepage_start_icon");
    UIImageView *startImageView=[[UIImageView alloc]initWithFrame:CGRectMake(28, (67-startImg.size.height)/2, startImg.size.width, startImg.size.height)];
    startImageView.image = startImg;
    [startView addSubview:startImageView];
    
    UILabel *startAddress =[[UILabel alloc]initWithFrame:CGRectMake(CGRectGetMaxX(startImageView.frame)+10, 0, 124, 67)];
    startAddress.font = FontSize(16);
    startAddress.textColor = Number1684E3Color;
    
    NSString *moneyStr=[NSString stringWithFormat:@"从 %@",@"欧阳图文快印"];
    NSRange colorRange = [moneyStr rangeOfString:@"从"];
    NSMutableAttributedString *str = [[NSMutableAttributedString alloc]initWithString:moneyStr];
    //设置：在传过来的的内容显示成红色
    [str addAttribute:NSForegroundColorAttributeName value:Number090909Color range:colorRange];
    startAddress.attributedText = str;
    [startView addSubview:startAddress];
    
    
    UIButton *chufaTitleBtn =[UIButton buttonWithType:UIButtonTypeCustom];
    chufaTitleBtn.frame = CGRectMake(CGRectGetMaxX(startAddress.frame), 0, 50, 67);
    [chufaTitleBtn setTitle:@" 出发" forState:UIControlStateNormal];
    [chufaTitleBtn setImage:kImageNamed(@"homepage_right_icon") forState:UIControlStateNormal];
    [chufaTitleBtn setTitleColor:Number090909Color forState:UIControlStateNormal];
    chufaTitleBtn.titleLabel.font = FontSize(16.0f);
    [startView addSubview:chufaTitleBtn];

    
    
    
    UIView *endView =[[UIView alloc]initWithFrame:CGRectMake(15, CGRectGetMaxY(startView.frame), CGRectGetWidth(tripView.frame)-30, 54)];
    endView.backgroundColor =NumberEDF5FBColor;
    endView.clipsToBounds = YES;
    endView.layer.cornerRadius = 8.0f;
    [tripView addSubview:endView];
    
    UIImage *endImg =kImageNamed(@"homepage_end_icon");
    UIImageView *endImageView=[[UIImageView alloc]initWithFrame:CGRectMake(13, (54-endImg.size.height)/2, endImg.size.width, endImg.size.height)];
    endImageView.image = endImg;
    [endView addSubview:endImageView];
    
    UITextField *endTF =[[UITextField alloc]initWithFrame:CGRectMake(CGRectGetMaxX(endImageView.frame)+10, 0, CGRectGetWidth(endView.frame)-CGRectGetMaxX(endImageView.frame)-20, 54)];

    NSString *endText = @"输入你的目的地";
    NSMutableAttributedString *placeholder = [[NSMutableAttributedString alloc] initWithString:endText];
    [placeholder addAttribute:NSForegroundColorAttributeName value:Number090909Color range:NSMakeRange(0, endText.length)];
    [placeholder addAttribute:NSFontAttributeName value:[UIFont boldSystemFontOfSize:20] range:NSMakeRange(0, endText.length)];
    endTF.delegate = self;
    endTF.attributedPlaceholder = placeholder;
    endTF.tag=50001;
    [endView addSubview:endTF];

    
    bgScrollView.contentSize = CGSizeMake(SCREEN_WIDTH, CGRectGetMaxY(tripView.frame)+NavigationBar_Height+20);
    //行程end
    
//    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(keyboardComeout:) name:UIKeyboardWillShowNotification object:nil];
//    
//    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(keyboardComeout:) name:UIKeyboardWillHideNotification object:nil];
    
//    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(done:) name:@"doneAction" object:nil];
    
    [endTF addPreviousNextDoneOnKeyboardWithTarget:self previousAction:nil nextAction:nil doneAction:@selector(customDone:)];
    
//    [self creatXuanfu];
    
}

-(void)customDone:(UITextField *)testTF
{
    [AppUtils showInfoMessage:@"该功能暂未开通，敬请期待"];
    [self.view endEditing:YES];
}

//- (void)done:(NSNotification *)notification{
//    [AppUtils showInfoMessage:@"该功能暂未开通，敬请期待"];
//}

//-(void)keyboardComeout:(NSNotification *)notification
//{
//    [AppUtils showInfoMessage:@"该功能暂未开通，敬请期待"];
//}

-(void)dealloc
{
//    [[NSNotificationCenter defaultCenter] removeObserver:self name:@"doneAction" object:nil];
}

#pragma mark==现在
-(void)nowBtnClick:(UIButton *)nowBtn
{
    DLog(@"现在");
    self.yuyueBtn.titleLabel.font =FontSize(16);
    self.yuyueLineView.hidden = YES;
    
    self.nowBtn.titleLabel.font = [UIFont boldSystemFontOfSize:18.0f];
    self.nowLineView.hidden = NO;
}
#pragma mark == 预约
-(void)yuyueClick:(UIButton *)yuyueBtn
{
    DLog(@"预约");
    self.yuyueBtn.titleLabel.font = [UIFont boldSystemFontOfSize:18.0f];
    self.yuyueLineView.hidden = NO;
    
    self.nowBtn.titleLabel.font =FontSize(16);
    self.nowLineView.hidden = YES;
}

- (BOOL)textFieldShouldReturn:(UITextField *)textField
{
    [self.view endEditing:YES];
    return YES;
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
    
    self.wkWebView.hidden = YES;
        UIImage *empImg =kImageNamed(@"homepage_emp_icon");
        UIImageView *empImage=[[UIImageView alloc]initWithFrame:CGRectMake((SCREEN_WIDTH-empImg.size.width)/2, 102, empImg.size.width, empImg.size.height)];
        empImage.image = empImg;
        [self.empView addSubview:empImage];
        UILabel *empLab=[[UILabel alloc]initWithFrame:CGRectMake(0, CGRectGetMaxY(empImage.frame)+14, SCREEN_WIDTH, 20)];
        empLab.text=@"您所在的城市暂未开通，敬请期待~";
        empLab.textColor = Number999999Color;
        empLab.font = FontSize(12.f);
        empLab.textAlignment = NSTextAlignmentCenter;
        [self.empView addSubview:empLab];
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

    UserModel *userM =[UserInfo getUserInfo];
    if (userM==nil){
        userM = [UserModel user];
    }
    userM.latitude = [NSString stringWithFormat:@"%@",SafeValue([NSString stringWithFormat:@"%@",strlatitude])];

    userM.longitude = [NSString stringWithFormat:@"%@",SafeValue([NSString stringWithFormat:@"%@",strlongitude])];
    
    [UserInfo saveUserInfo:userM];
    
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

#pragma mark ==JSBridgeDelegate
//
//-(void)dealloc
//{
//    //Removing notification observers on dealloc.
//    [[NSNotificationCenter defaultCenter] removeObserver:self];
//}

- (void)webViewloadFinish:(id)obj{
//    [_wkWebView configNavigatioBackBtn];
    _wkWebView.wkWebView.height = 300;
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
    
    shopCenterVC.url = [NSString stringWithFormat:@"%@%@",HOSTH5_IP,detectionDetailH5url];
    DLog(@"查看检测shopCenterVC===%@",shopCenterVC.url);
 
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

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
