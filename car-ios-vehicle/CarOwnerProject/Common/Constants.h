//
//  Constants.h
//  CarOwnerProject
//
//  Created by apple on 2021/1/25.
//

#ifndef Constants_h
#define Constants_h
/*
 常用
 **/

#define SCREEN_WIDTH [UIScreen mainScreen].bounds.size.width
#define SCREEN_HEIGHT [UIScreen mainScreen].bounds.size.height




//rgb颜色
#define RGBAColor(_r, _g, _b, _a) [UIColor colorWithRed:(_r)/255.0 green:(_g)/255.0 blue:(_b)/255.0 alpha:_a]
#define RGBColor(_r, _g, _b) RGBAColor(_r, _g, _b, 1.0f)

#define FontSize(_size)   [UIFont systemFontOfSize:_size]
//UIImage
#define kImageNamed(name) [UIImage imageNamed:name]


//toast显示时间
#define ToastShowTimeInterval 1

//根据编译环境控制是否打印log
#ifdef DEBUG
#define DLog( s, ... ) NSLog( @"<%p %@:(%d)> %@", self, [[NSString stringWithUTF8String:__FILE__] lastPathComponent], __LINE__, [NSString stringWithFormat:(s), ##__VA_ARGS__] )
#else
#define DLog( s, ... )
#endif

#define LAST_RUN_VERSION_KEY @"isQuiteee"//第一次登陆

#define changeCityName   @"cityName"
#define changeCityUuid   @"uuid"

//底部tab高度
#define BottomTab_Height    49
#define iPhoneX_All ([UIScreen mainScreen].bounds.size.height == 812.0 || [UIScreen mainScreen].bounds.size.height == 896.0)
//导航条高度 iPhone X为88 其余为64
#define NavigationBar_Height (iPhoneX_All ? 88 : 64)
// 底部高度 iPhone X为34 其余机型为0
#define SafeAreaBottom_Height (iPhoneX_All ? 34 : 0)


#define IOS_9_OR_LATER  ([[[UIDevice currentDevice] systemVersion] floatValue] >= 9.0)

//弱引用
#define WeakSelf(type)  __weak typeof(type) weak##type = type;
//强引用
#define StrongSelf(type)  __strong typeof(type) type = weak##type;

//按比例获取视图尺寸.以iPhone6屏幕尺寸为标准，所有尺寸屏幕按比例缩放
#define AdaptAllScreen(value) \
({\
CGFloat  result = value;\
result = [UIScreen mainScreen].bounds.size.width*value/375;\
(result);\
})\


#define PathUrl(name,type) [NSURL fileURLWithPath:[[NSBundle mainBundle] pathForResource:name ofType:type]];



#define WeChat_AppId @"wxe7d526a5f123af21"

#define WeixinUniversalLink  @"https://vehicle.dlvehicle.com/apple-app-site-association"

/*
 类别
 **/
#import "UIColor+Additions.h"//颜色
#import "UIButton+YasinTimerCategory.h"//倒计时
#import "UIView+Frame.h"


/*
 第三方、常用类
 **/
#import "UserInfo.h"
#import "UserModel.h"
#import "CommentMethod.h"
#import "EmptyView.h"
#import "SummaryH5ViewController.h"

#import "FSCustomButton.h"

#import "AppUtils.h"

#import "JohnTopTitleView.h"

#import <SDCycleScrollView.h>

//数据请求
#import <AFNetworking.h>
//按钮 图片
#import <SDWebImage.h>
#import <NSButton+WebCache.h>
//数据模型转换
#import <MJExtension.h>
//数据刷新
#import <MJRefresh.h>
#import <SVProgressHUD.h>

#import "ZJProgressHUD.h"

#endif /* Constants_h */
