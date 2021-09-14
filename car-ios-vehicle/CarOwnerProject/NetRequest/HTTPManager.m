//
//  HTTPManager.m
//  600Rebate
//
//  Created by iMac on 2019/4/11.
//  Copyright © 2019年 iMac. All rights reserved.
//

#import "HTTPManager.h"

@implementation HTTPManager

+(NSString *)getUrlWithString:(NSString*)string{
    NSString *url=[NSString stringWithFormat:@"%@%@",HOST_URL,string];
    return url;
}

+(AFHTTPSessionManager*)getHTTPManager{
    static AFHTTPSessionManager *_manager = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        _manager = [AFHTTPSessionManager manager];
        //申明请求的数据是json类型 AFJSONRequestSerializer
        _manager.requestSerializer=[AFJSONRequestSerializer  serializer];
        //申明返回的结果是json类型
        _manager.responseSerializer = [AFJSONResponseSerializer serializer];
        //如果报接受类型不一致请替换一致text/html或别的 注意：设置超时时间要放在这句代码之后
        _manager.responseSerializer.acceptableContentTypes = [NSSet setWithObjects:@"application/json",@"text/json",@"text/javascript",@"text/plain",@"text/html",@"text/javascript",nil];
        //超时时间为10秒 注意：设置超时时间要写在设置acceptableContentTypes的后面
//        [_manager.requestSerializer willChangeValueForKey:@"timeoutInterval"];
        _manager.requestSerializer.timeoutInterval=60.0;
//        [_manager.requestSerializer didChangeValueForKey:@"timeoutInterval"];
        _manager.securityPolicy.allowInvalidCertificates = NO;
        
//        AFSecurityPolicy *securityPolicy = [AFSecurityPolicy defaultPolicy];
//
//        securityPolicy.allowInvalidCertificates = NO;
//
//        securityPolicy.validatesDomainName = YES;
//
//        _manager.securityPolicy  = securityPolicy;
    
    });
    
    return _manager;
}

//设置请求头
+(void)setRequestHTTPHeader{
}


//字典转json格式字符串：
+(NSString*)dictionaryToJsonString:(NSDictionary *)dic{
    NSError *parseError = nil;
    NSData *jsonData = [NSJSONSerialization dataWithJSONObject:dic options:NSJSONWritingPrettyPrinted error:&parseError];
    
    return [[NSString alloc] initWithData:jsonData encoding:NSUTF8StringEncoding];
}

//json格式字符串转字典：
+(NSDictionary *)jsonStringToDictionary:(NSString *)jsonString{
    if (jsonString == nil){
        return nil;
    }
    NSData *jsonData = [jsonString dataUsingEncoding:NSUTF8StringEncoding];
    NSError *err;
    NSDictionary *dic = [NSJSONSerialization JSONObjectWithData:jsonData options:NSJSONReadingMutableContainers error:&err];
    if(err){
        NSLog(@"json解析失败：%@",err);
        return nil;
    }
    return dic;
}

/**
 *  get请求
 */
+(void)getRequestWithUrl:(NSString*)url parameters:(NSDictionary*)parameters header:(NSDictionary*)header  progress:(void(^)(NSProgress *downloadProgress))progress success:(void(^)(NSDictionary *responseObject))success other:(void(^)(NSDictionary *responseObject))other failure:(void(^)(NSError *error))failure
{
    NSLog(@"GET请求的URL===%@",[self getUrlWithString:url]);
    AFHTTPSessionManager *manager=[self getHTTPManager];
    [self setRequestHTTPHeader];
    
    NSString *urlString=[[self getUrlWithString:url] stringByAddingPercentEncodingWithAllowedCharacters:[NSCharacterSet characterSetWithCharactersInString:@"`#%^{}\"[]|\\<>"].invertedSet];
    if ([url containsString:@"itunes.apple.com"]) {
        urlString=[url stringByAddingPercentEncodingWithAllowedCharacters:[NSCharacterSet characterSetWithCharactersInString:@"`#%^{}\"[]|\\<>"].invertedSet];
    }
    [UIApplication sharedApplication].networkActivityIndicatorVisible = YES;
    [manager GET:urlString parameters:parameters headers:header progress:^(NSProgress * _Nonnull downloadProgress) {
        progress(downloadProgress);
    } success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
        NSDictionary *requestDict=[NSDictionary dictionary];
                if ([responseObject isKindOfClass:[NSData class]]) {
                    requestDict=[NSJSONSerialization JSONObjectWithData:(NSData*)responseObject options:kNilOptions error:nil];
                }else{
                    requestDict=(NSDictionary *)responseObject;
                }
                
                if ([url containsString:@"itunes.apple.com"]){
                    success(requestDict);
                }else{
                    NSString *code=[NSString stringWithFormat:@"%@",[responseObject objectForKey:@"code"]];
                    if ([code isEqualToString:CODE_SUCCESS]){
                        [UIApplication sharedApplication].networkActivityIndicatorVisible = NO;

                        success(requestDict);
                        
                    }else{
                        other(requestDict);
                        [ZJProgressHUD showStatus:requestDict[@"mesg"] andAutoHideAfterTime:2.0];
                        [UIApplication sharedApplication].networkActivityIndicatorVisible = NO;

                    }
                }
    } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
        failure(error);
        NSLog(@"GET请求失败===%@",error);
        [UIApplication sharedApplication].networkActivityIndicatorVisible = NO;
        if (error)
        {
            NSData *responseData = error.userInfo[AFNetworkingOperationFailingURLResponseDataErrorKey];
            NSString * receive = [[NSString alloc]initWithData:responseData encoding:NSUTF8StringEncoding];
        
            //字符串再生成NSData
            NSData *data = [receive dataUsingEncoding:NSUTF8StringEncoding];
        
            NSDictionary *dict = [NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableLeaves error:nil];
            
            DLog(@"请求错误啊啊啊啊啊啊dict======%@",dict);
            
            NSString *codeStr = dict[@"code"];
            
            if ([codeStr isEqualToString:CODE_Failure]||[codeStr isEqualToString:CODE_XTFailure]||[codeStr isEqualToString:CODE_Failure1]) {
                dispatch_async(dispatch_get_main_queue(), ^{
                    UIAlertController *alert = [UIAlertController alertControllerWithTitle:@"提示" message:TokenInvalidMsg preferredStyle:UIAlertControllerStyleAlert];
                    UIViewController *vc = [[UIApplication sharedApplication].delegate window].rootViewController;
                    UIAlertAction *confirmAction = [UIAlertAction actionWithTitle:@"确定" style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action)
                       {
                        LoginViewController *loginVC = [LoginViewController new];
                        BaseNavViewController *baseVC =[[BaseNavViewController alloc]initWithRootViewController:loginVC];
                        baseVC.modalPresentationStyle = UIModalPresentationFullScreen;
                        [vc presentViewController:baseVC animated:YES completion:nil];
                        UserModel *userM =[UserInfo getUserInfo];
                        if (userM==nil){
                            userM = [UserModel user];
                        }
                        userM.token =@"";
                        userM.phone =@"";
                        [UserInfo saveUserInfo:userM];
                    
//
                    }];
                       [alert addAction:confirmAction];
                       [vc presentViewController:alert animated:YES completion:nil];
                   });
            }
        }

    }];
    
}

/**
 *  post请求（不上传图片）
 */
+(void)postRequestWithUrl:(NSString*)url parameters:(NSDictionary*)parameters header:(NSDictionary*)header  progress:(void(^)(NSProgress *uploadProgress))progress success:(void(^)(NSDictionary *responseObject))success other:(void(^)(NSDictionary *responseObject))other failure:(void(^)(NSError *error))failure
{
    NSLog(@"POST请求(无图片)的URL===%@",[self getUrlWithString:url]);
    AFHTTPSessionManager *manager=[self getHTTPManager];
    [self setRequestHTTPHeader];
    
    
    
    NSString *urlString=[[self getUrlWithString:url] stringByAddingPercentEncodingWithAllowedCharacters:[NSCharacterSet characterSetWithCharactersInString:@"`#%^{}\"[]|\\<> "].invertedSet];
        
    [UIApplication sharedApplication].networkActivityIndicatorVisible = YES;

    [manager POST:urlString parameters:parameters headers:header progress:^(NSProgress * _Nonnull uploadProgress) {
        progress(uploadProgress);
    } success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
        NSDictionary *requestDict=[NSDictionary dictionary];
        if ([responseObject isKindOfClass:[NSData class]]) {
            requestDict=[NSJSONSerialization JSONObjectWithData:(NSData*)responseObject options:kNilOptions error:nil];
        }else{
            requestDict=(NSDictionary *)responseObject;
        }
        NSString *code=[NSString stringWithFormat:@"%@",[responseObject objectForKey:@"code"]];
        DLog(@"请求成功code=====%@",code);
        if ([code isEqualToString:CODE_SUCCESS]){
            [UIApplication sharedApplication].networkActivityIndicatorVisible = NO;
            success(requestDict);
        }else{            
            [UIApplication sharedApplication].networkActivityIndicatorVisible = NO;

            //不是OSS 获取token  提示
            other(requestDict);
        }
    } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
        failure(error);
        [UIApplication sharedApplication].networkActivityIndicatorVisible = NO;
        NSLog(@"POST请求(无图片)失败===%@",error);
        
        if (error)
        {
            NSData *responseData = error.userInfo[AFNetworkingOperationFailingURLResponseDataErrorKey];
            NSString * receive = [[NSString alloc]initWithData:responseData encoding:NSUTF8StringEncoding];
        
            //字符串再生成NSData
            NSData *data = [receive dataUsingEncoding:NSUTF8StringEncoding];
        
            NSDictionary *dict = [NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableLeaves error:nil];
            
            DLog(@"请求错误啊啊啊啊啊啊dict======%@",dict);
            
            NSString *codeStr = dict[@"code"];
            
            if ([codeStr isEqualToString:CODE_Failure]||[codeStr isEqualToString:CODE_XTFailure]||[codeStr isEqualToString:CODE_Failure1]) {
                dispatch_async(dispatch_get_main_queue(), ^{
                    UIAlertController *alert = [UIAlertController alertControllerWithTitle:@"提示" message:TokenInvalidMsg preferredStyle:UIAlertControllerStyleAlert];
                    UIViewController *vc = [[UIApplication sharedApplication].delegate window].rootViewController;
                    UIAlertAction *confirmAction = [UIAlertAction actionWithTitle:@"确定" style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action)
                       {
                        LoginViewController *loginVC = [LoginViewController new];
                        BaseNavViewController *baseVC =[[BaseNavViewController alloc]initWithRootViewController:loginVC];
                        baseVC.modalPresentationStyle = UIModalPresentationFullScreen;
                        [vc presentViewController:baseVC animated:YES completion:nil];
                        UserModel *userM =[UserInfo getUserInfo];
                        if (userM==nil){
                            userM = [UserModel user];
                        }
                        userM.token =@"";
                        userM.phone =@"";
                        [UserInfo saveUserInfo:userM];
                    
//
                    }];
                       [alert addAction:confirmAction];
                       [vc presentViewController:alert animated:YES completion:nil];
                   });
            }
        }
        
        
        
    }];
    
}

/**
 *  post请求（上传图片）
 */
+(void)postRequestWithUrl:(NSString*)url parameters:(NSDictionary*)parameters constructingBodyWithBlock:(void(^)(id<AFMultipartFormData>  _Nonnull formData))constructingBodyWithBlock progress:(void(^)(NSProgress *uploadProgress))progress success:(void(^)(NSDictionary *responseObject))success other:(void(^)(NSDictionary *responseObject))other failure:(void(^)(NSError *error))failure
{
    NSLog(@"POST请求(有图片)的URL===%@",[self getUrlWithString:url]);
    
    AFHTTPSessionManager *manager=[self getHTTPManager];
    [self setRequestHTTPHeader];
    
    NSString *urlString=[[self getUrlWithString:url] stringByAddingPercentEncodingWithAllowedCharacters:[NSCharacterSet characterSetWithCharactersInString:@"`#%^{}\"[]|\\<>"].invertedSet];
    
    [manager POST:urlString parameters:parameters headers:@{} constructingBodyWithBlock:^(id<AFMultipartFormData>  _Nonnull formData) {
        constructingBodyWithBlock(formData);
    } progress:^(NSProgress * _Nonnull uploadProgress) {
        progress(uploadProgress);
    } success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
        NSDictionary *requestDict=[NSDictionary dictionary];
        if ([responseObject isKindOfClass:[NSData class]]) {
            requestDict=[NSJSONSerialization JSONObjectWithData:(NSData*)responseObject options:kNilOptions error:nil];
        }else{
            requestDict=(NSDictionary *)responseObject;
        }
        NSString *code=[NSString stringWithFormat:@"%@",[responseObject objectForKey:@"code"]];
        if ([code isEqualToString:CODE_SUCCESS]){
            success(requestDict);
        }else{
            other(requestDict);
        }
        NSLog(@"POST请求(有图片)返回值===%@",requestDict);
    } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
        failure(error);
        NSLog(@"POST请求(有图片)失败===%@",error);
        [UIApplication sharedApplication].networkActivityIndicatorVisible = NO;
        if (error)
        {
            NSData *responseData = error.userInfo[AFNetworkingOperationFailingURLResponseDataErrorKey];
            NSString * receive = [[NSString alloc]initWithData:responseData encoding:NSUTF8StringEncoding];
        
            //字符串再生成NSData
            NSData *data = [receive dataUsingEncoding:NSUTF8StringEncoding];
        
            NSDictionary *dict = [NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableLeaves error:nil];
            
            DLog(@"请求错误啊啊啊啊啊啊dict======%@",dict);
            
            NSString *codeStr = dict[@"code"];
            
            if ([codeStr isEqualToString:CODE_Failure]||[codeStr isEqualToString:CODE_XTFailure]||[codeStr isEqualToString:CODE_Failure1]) {
                dispatch_async(dispatch_get_main_queue(), ^{
                    UIAlertController *alert = [UIAlertController alertControllerWithTitle:@"提示" message:TokenInvalidMsg preferredStyle:UIAlertControllerStyleAlert];
                    UIViewController *vc = [[UIApplication sharedApplication].delegate window].rootViewController;
                    UIAlertAction *confirmAction = [UIAlertAction actionWithTitle:@"确定" style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action)
                       {
                        LoginViewController *loginVC = [LoginViewController new];
                        BaseNavViewController *baseVC =[[BaseNavViewController alloc]initWithRootViewController:loginVC];
                        baseVC.modalPresentationStyle = UIModalPresentationFullScreen;
                        [vc presentViewController:baseVC animated:YES completion:nil];
                        UserModel *userM =[UserInfo getUserInfo];
                        if (userM==nil){
                            userM = [UserModel user];
                        }
                        userM.token =@"";
                        userM.phone =@"";
                        [UserInfo saveUserInfo:userM];
                    }];
                       [alert addAction:confirmAction];
                       [vc presentViewController:alert animated:YES completion:nil];
                   });
            }
        }
    }];
}




/**
 form-data请求接口

 @param url 请求地址
 @param parameters 请求参数   传字典就行，所有的键值对都传进来
 @param success 成功
 @param failure 失败
 */
  + (void)p_postRequestUrl:(NSString *)url parameters:(NSDictionary *)parameters header:(NSDictionary *)header success:(void (^)(id responseObject))success other:(void(^)(NSDictionary *responseObject))other failure:(void (^)(id errorLog))failure
{
    //配置AF
    AFHTTPSessionManager *manager=[self getHTTPManager];
       [self setRequestHTTPHeader];
    NSString *urlString=[[self getUrlWithString:url] stringByAddingPercentEncodingWithAllowedCharacters:[NSCharacterSet characterSetWithCharactersInString:@"`#%^{}\"[]|\\<> "].invertedSet];
    [manager POST:urlString parameters:nil headers:header constructingBodyWithBlock:^(id<AFMultipartFormData>  _Nonnull formData) {
        for (NSString *key in [parameters allKeys]) {
            [formData appendPartWithFormData:[[parameters objectForKey:key] dataUsingEncoding:NSUTF8StringEncoding] name:key];
        }        
    } progress:^(NSProgress * _Nonnull uploadProgress) {
        
    } success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
        
        NSDictionary *requestDict=[NSDictionary dictionary];
        if ([responseObject isKindOfClass:[NSData class]])
        {
            requestDict=[NSJSONSerialization JSONObjectWithData:(NSData*)responseObject options:kNilOptions error:nil];
        }else{
            requestDict=(NSDictionary *)responseObject;
        }
        NSString *code=[NSString stringWithFormat:@"%@",[responseObject objectForKey:@"code"]];
        if ([code isEqualToString:CODE_SUCCESS]){
            success(requestDict);
        }else{
            other(requestDict);
        }
        NSLog(@"POST请求(有图片)返回值===%@",requestDict);
    } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
        failure(error.description);
        [UIApplication sharedApplication].networkActivityIndicatorVisible = NO;
        if (error)
        {
            NSData *responseData = error.userInfo[AFNetworkingOperationFailingURLResponseDataErrorKey];
            NSString * receive = [[NSString alloc]initWithData:responseData encoding:NSUTF8StringEncoding];
        
            //字符串再生成NSData
            NSData *data = [receive dataUsingEncoding:NSUTF8StringEncoding];
        
            NSDictionary *dict = [NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableLeaves error:nil];
            
            DLog(@"请求错误啊啊啊啊啊啊dict======%@",dict);
            
            NSString *codeStr = dict[@"code"];
            
            if ([codeStr isEqualToString:CODE_Failure]||[codeStr isEqualToString:CODE_XTFailure]||[codeStr isEqualToString:CODE_Failure1]) {
                dispatch_async(dispatch_get_main_queue(), ^{
                    UIAlertController *alert = [UIAlertController alertControllerWithTitle:@"提示" message:TokenInvalidMsg preferredStyle:UIAlertControllerStyleAlert];
                    UIViewController *vc = [[UIApplication sharedApplication].delegate window].rootViewController;
                    UIAlertAction *confirmAction = [UIAlertAction actionWithTitle:@"确定" style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action)
                       {
                        LoginViewController *loginVC = [LoginViewController new];
                        BaseNavViewController *baseVC =[[BaseNavViewController alloc]initWithRootViewController:loginVC];
                        baseVC.modalPresentationStyle = UIModalPresentationFullScreen;
                        [vc presentViewController:baseVC animated:YES completion:nil];
                        UserModel *userM =[UserInfo getUserInfo];
                        if (userM==nil){
                            userM = [UserModel user];
                        }
                        userM.token =@"";
                        userM.phone =@"";
                        [UserInfo saveUserInfo:userM];
                    
                    }];
                       [alert addAction:confirmAction];
                       [vc presentViewController:alert animated:YES completion:nil];
                   });
            }
        }
    }];
    

}







/**
 *  put请求
 */
+(void)putRequestWithUrl:(NSString*)url parameters:(NSDictionary*)parameters success:(void(^)(NSDictionary *responseObject))success other:(void(^)(NSDictionary *responseObject))other failure:(void(^)(NSError *error))failure
{
    NSLog(@"PUT请求的URL===%@",[self getUrlWithString:url]);
    
    AFHTTPSessionManager *manager=[self getHTTPManager];
    [self setRequestHTTPHeader];
    NSString *urlString=[[self getUrlWithString:url] stringByAddingPercentEncodingWithAllowedCharacters:[NSCharacterSet characterSetWithCharactersInString:@"`#%^{}\"[]|\\<>"].invertedSet];
    
    [manager PUT:urlString parameters:parameters headers:@{} success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
        NSDictionary *requestDict=[NSDictionary dictionary];
        if ([responseObject isKindOfClass:[NSData class]]) {
            requestDict=[NSJSONSerialization JSONObjectWithData:(NSData*)responseObject options:kNilOptions error:nil];
        }else{
            requestDict=(NSDictionary *)responseObject;
        }
        NSString *code=[NSString stringWithFormat:@"%@",[responseObject objectForKey:@"code"]];
        if ([code isEqualToString:CODE_SUCCESS]){
            success(requestDict);
        }else{
            
            other(requestDict);
        }
    } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
        failure(error);
        NSLog(@"PUT请求失败===%@",error);
    }];
}

/**
 取消所有网络请求
 */
+(void)cancelAllHttpRequests{
    [[self getHTTPManager].operationQueue cancelAllOperations];
}

/**
 *  取消指定的url请求
 *
 *  @param requestType 该请求的请求类型
 *  @param requestUrl  该请求的完整url
 */
+(void)cancelHttpRequestWithRequestType:(NSString *)requestType requestUrl:(NSString *)requestUrl{
    NSError * error;
    /**根据请求的类型 以及 请求的url创建一个NSMutableURLRequest---通过该url去匹配请求队列中是否有该url,如果有的话 那么就取消该请求*/
    NSString * urlToPeCanced = [[[[self getHTTPManager].requestSerializer requestWithMethod:requestType URLString:requestUrl parameters:nil error:&error] URL] path];
    
    for (NSOperation * operation in [self getHTTPManager].operationQueue.operations) {
        //如果是请求队列
        if ([operation isKindOfClass:[NSURLSessionTask class]]) {
            //请求的类型匹配
            BOOL hasMatchRequestType = [requestType isEqualToString:[[(NSURLSessionTask *)operation currentRequest] HTTPMethod]];
            //请求的url匹配
            BOOL hasMatchRequestUrlString = [urlToPeCanced isEqualToString:[[[(NSURLSessionTask *)operation currentRequest] URL] path]];
            //两项都匹配的话  取消该请求
            if (hasMatchRequestType&&hasMatchRequestUrlString) {
                [operation cancel];
            }
        }
    }
}




@end
