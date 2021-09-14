//
//  HTTPManager.h
//  600Rebate
//
//  Created by iMac on 2019/4/11.
//  Copyright © 2019年 iMac. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "AppDelegate.h"
#import "LoginViewController.h"
#import "BaseNavViewController.h"


NS_ASSUME_NONNULL_BEGIN

@interface HTTPManager : NSObject

///**
// 网络状态
// */
//@property(nonatomic,assign)NSInteger networkStatus;
//
///**
// 是否有网络
// */
//@property(nonatomic,assign)BOOL isHasNetwork;


//json格式字符串转字典：
+(NSDictionary *)jsonStringToDictionary:(NSString *)jsonString;


/**
 取消所有网络请求
 */
+(void)cancelAllHttpRequests;
/**
 *  取消指定的url请求
 *
 *  @param requestType 该请求的请求类型
 *  @param requestUrl  该请求的完整url
 */
+(void)cancelHttpRequestWithRequestType:(NSString *)requestType requestUrl:(NSString *)requestUrl;
///**
// 监测网络状态
// */
//+(void)startMonitoringNetworkStatus;

/**
 *  get请求
 */
+(void)getRequestWithUrl:(NSString*)url parameters:(NSDictionary*)parameters header:(NSDictionary*)header  progress:(void(^)(NSProgress *downloadProgress))progress success:(void(^)(NSDictionary *responseObject))success other:(void(^)(NSDictionary *responseObject))other failure:(void(^)(NSError *error))failure;

/**
 *  post请求（不上传图片）
 */
+(void)postRequestWithUrl:(NSString*)url parameters:(NSDictionary*)parameters header:(NSDictionary*)header progress:(void(^)(NSProgress *uploadProgress))progress success:(void(^)(NSDictionary *responseObject))success other:(void(^)(NSDictionary *responseObject))other failure:(void(^)(NSError *error))failure;



/**
 *  post请求（上传图片）
 */
+(void)postRequestWithUrl:(NSString*)url parameters:(NSDictionary*)parameters header:(NSDictionary*)header constructingBodyWithBlock:(void(^)(id<AFMultipartFormData>  _Nonnull formData))constructingBodyWithBlock progress:(void(^)(NSProgress *uploadProgress))progress success:(void(^)(NSDictionary *responseObject))success other:(void(^)(NSDictionary *responseObject))other failure:(void(^)(NSError *error))failure;

/**
 *  put请求
 */
+(void)putRequestWithUrl:(NSString*)url parameters:(NSDictionary*)parameters success:(void(^)(NSDictionary *responseObject))success other:(void(^)(NSDictionary *responseObject))other failure:(void(^)(NSError *error))failure;





/**
form-data请求接口

@param url 请求地址
@param parameters 请求参数   传字典就行，所有的键值对都传进来
@param success 成功
@param failure 失败
*/
  + (void)p_postRequestUrl:(NSString *)url parameters:(NSDictionary *)parameters header:(NSDictionary *)header success:(void (^)(id responseObject))success other:(void(^)(NSDictionary *responseObject))other failure:(void (^)(id errorLog))failure;



























@end

NS_ASSUME_NONNULL_END
