//
//  AppDelegate.m
//  CarOwnerProject
//
//  Created by apple on 2021/1/25.
//

#import "AppDelegate.h"
#import "BaseTabViewController.h"
#import "LoginViewController.h"
#import "BaseNavViewController.h"
#import "IntroduceGuideView.h"
#import <CoreLocation/CoreLocation.h>
#import "SystemAlertView.h"
#import "WXApi.h"

@interface AppDelegate ()<CLLocationManagerDelegate,WXApiDelegate>
{

    CLLocationManager*locationmanager;//定位服务

    NSString*strlatitude;//经度

    NSString*strlongitude;//纬度

}
@property (assign, nonatomic)BOOL first;
@property (strong, nonatomic) IntroduceGuideView *guide;

@end

@implementation AppDelegate


- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {
    // Override point for customization after application launch.
    
    self.window = [[UIWindow alloc]initWithFrame:[UIScreen mainScreen].bounds];
    self.window.backgroundColor = [UIColor whiteColor];
    
    [self.window makeKeyAndVisible];
    
    //在register之前打开log, 后续可以根据log排查问题
    [WXApi startLogByLevel:WXLogLevelDetail logBlock:^(NSString *log) {
        NSLog(@"WeChatSDK: %@", log);
    }];

    [WXApi registerApp:WeChat_AppId universalLink:WeixinUniversalLink];
    
//    self.first=[AppUtils isFirstLaunchCurrentVersion];
//    if (self.first==YES)
//    {
        [self requestGetByCode];
//        IntroduceGuideView *guide = [[IntroduceGuideView alloc] initWithCoverIxmageNames:nil backgroundImageNames:nil];
//        [self.window addSubview:guide];
//        self.guide=guide;
//        WeakSelf(guide)
//        guide.didSelectedEnter = ^{
//            StrongSelf(guide)
//            [guide removeFromSuperview];
//        };
//    }
    
    [self configMainController];
    
    
    
    return YES;
}
-(void)configMainController
{
    UserModel *model =[UserInfo getUserInfo];
    
    DLog(@"token==%@",model.token);
    
    if ([SafeValue([NSString stringWithFormat:@"%@",model.token])isEqualToString:@""]) {
        LoginViewController *loginVC =[[LoginViewController alloc]init];
        BaseNavViewController *loginNav =[[BaseNavViewController alloc]initWithRootViewController:loginVC];
        self.window.rootViewController = loginNav;
    }else
    {
        BaseTabViewController *tabbarVC = [[BaseTabViewController alloc]init];
        [self.window setRootViewController:tabbarVC];
        [self startLocation];
    }
}
#pragma mark == 查询引导页
-(void)requestGetByCode
{
    NSDictionary *pageBoDict=@{
    };
    NSDictionary *headerDict =@{
    };
    NSString *urlStr = [NSString stringWithFormat:@"%@/guide_page_technician_store",settingGetByCodeApi];
    [HTTPManager getRequestWithUrl:urlStr parameters:pageBoDict header:headerDict progress:^(NSProgress * _Nonnull uploadProgress) {
        
    } success:^(NSDictionary * _Nonnull responseObject) {
        DLog(@"引导图responseObject===%@",responseObject);
        NSArray *dataArr = responseObject[@"data"];
        
        NSMutableArray *urlImgArr =[[NSMutableArray alloc]init];
        for (int i=0; i<dataArr.count; i++) {
            NSDictionary *imageDict = dataArr[i];
            NSString *imgStr = imageDict[@"imgUrl"];
    
            [urlImgArr addObject:imgStr];
        }
        
        DLog(@"引导图urlImgArr===%@",urlImgArr);

        IntroduceGuideView *guide = [[IntroduceGuideView alloc] initWithCoverImageNames:nil backgroundImageNames:urlImgArr];
        guide.backgroundImageNames = urlImgArr;
        [self.window addSubview:guide];
        self.guide=guide;
        WeakSelf(guide)
        guide.didSelectedEnter = ^{
            StrongSelf(guide)
            [guide removeFromSuperview];
        };
        
    } other:^(NSDictionary * _Nonnull responseObject) {
        
        DLog(@"账户信息其他responseObject===%@",responseObject);

        [AppUtils showErrorMessage:responseObject[@"msg"]];
    } failure:^(NSError * _Nonnull error) {
        
    }];
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
    DLog(@"定位失败，关闭定位");
    UserModel *userM =[UserInfo getUserInfo];
     if (userM==nil){
         userM = [UserModel user];
     }
     
     userM.longitude =@"";
     userM.latitude =@"";
     [UserInfo saveUserInfo:userM];
    //设置提示提醒用户打开定位服务
//    [[SystemAlertView shareInstance] showAlert:@"提示" message:@"定位服务当前可能尚未打开，请设置打开！" cancelTitle:nil titleArray:nil viewController:nil confirm:^(NSInteger buttonTag) {
//    }];
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
    
    [NSTimer scheduledTimerWithTimeInterval:60.0f target:self selector:@selector(uploadingLocationView) userInfo:nil repeats:YES];

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
    DLog(@"latiude===%@==%@",latiude,longitude);
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


//#pragma mark - UISceneSession lifecycle
/*
- (UISceneConfiguration *)application:(UIApplication *)application configurationForConnectingSceneSession:(UISceneSession *)connectingSceneSession options:(UISceneConnectionOptions *)options {
    // Called when a new scene session is being created.
    // Use this method to select a configuration to create the new scene with.
    return [[UISceneConfiguration alloc] initWithName:@"Default Configuration" sessionRole:connectingSceneSession.role];
}


- (void)application:(UIApplication *)application didDiscardSceneSessions:(NSSet<UISceneSession *> *)sceneSessions {
    // Called when the user discards a scene session.
    // If any sessions were discarded while the application was not running, this will be called shortly after application:didFinishLaunchingWithOptions.
    // Use this method to release any resources that were specific to the discarded scenes, as they will not return.
}*/

- (BOOL)application:(UIApplication *)application handleOpenURL:(NSURL *)url
{
    return  [WXApi handleOpenURL:url delegate:self];
}

- (BOOL)application:(UIApplication *)application openURL:(NSURL *)url sourceApplication:(NSString *)sourceApplication annotation:(id)annotation
{
    return [WXApi handleOpenURL:url delegate:self];
}

- (BOOL)application:(UIApplication *)application continueUserActivity:(NSUserActivity *)userActivity restorationHandler:(void(^)(NSArray<id<UIUserActivityRestoring>> * __nullable restorableObjects))restorationHandler
{
    return [WXApi handleOpenUniversalLink:userActivity delegate:self];
}

#pragma mark 微信
- (void)onReq:(BaseReq*)req{
    DLog(@" 分享想==req: %@",req);
}

@end
