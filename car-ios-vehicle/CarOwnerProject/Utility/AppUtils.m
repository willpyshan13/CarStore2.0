//
//  AppUtils.m
//  MeiRenJi
//
//  Created by 申巧 on 14/11/10.
//  Copyright (c) 2014年 XiaoQiQi. All rights reserved.
//

#import "AppUtils.h"
#import <SVProgressHUD/SVProgressHUD.h>
#import <CommonCrypto/CommonDigest.h>
#include <ifaddrs.h>
#include <arpa/inet.h>
#include <net/if.h>
//#import "DataBaseCenter.h"

#define IOS_CELLULAR    @"pdp_ip0"
#define IOS_WIFI        @"en0"
#define IOS_VPN         @"utun0"
#define IP_ADDR_IPv4    @"ipv4"
#define IP_ADDR_IPv6    @"ipv6"

@implementation AppUtils

+ (void)closeKeyboard{
    [[UIApplication sharedApplication].keyWindow endEditing:YES];
}

//生成二维码
+ (UIImage *)createQRCodeWithString:(NSString *)string size:(CGFloat)size{
    //创建滤镜
    CIFilter *filter = [CIFilter filterWithName:@"CIQRCodeGenerator"];
    //还原滤镜默认属性
    [filter setDefaults];
    NSData *data = [string dataUsingEncoding:NSUTF8StringEncoding];
    [filter setValue:data forKeyPath:@"inputMessage"];
    CIImage *outputImage = [filter outputImage];
    //生成的二维码模糊，所以通过该方法获得高清的二维码图片
    UIImage *image = [AppUtils createNonInterpolatedUIImageFromCIImage:outputImage size:size];
    return image;
}

//生成高清二维码
+ (UIImage *)createNonInterpolatedUIImageFromCIImage:(CIImage *)image size:(CGFloat)size{
    CGRect extent = CGRectIntegral(image.extent);
    CGFloat scale = MIN(size/CGRectGetWidth(extent), size/CGRectGetHeight(extent));
    
    // 1.创建bitmap;
    size_t width = CGRectGetWidth(extent) * scale;
    size_t height = CGRectGetHeight(extent) * scale;
    CGColorSpaceRef cs = CGColorSpaceCreateDeviceGray();
    CGContextRef bitmapRef = CGBitmapContextCreate(nil, width, height, 8, 0, cs, (CGBitmapInfo)kCGImageAlphaNone);
    CIContext *context = [CIContext contextWithOptions:nil];
    CGImageRef bitmapImage = [context createCGImage:image fromRect:extent];
    CGContextSetInterpolationQuality(bitmapRef, kCGInterpolationNone);
    CGContextScaleCTM(bitmapRef, scale, scale);
    CGContextDrawImage(bitmapRef, extent, bitmapImage);
    
    // 2.保存bitmap到图片
    CGImageRef scaledImage = CGBitmapContextCreateImage(bitmapRef);
    CGContextRelease(bitmapRef);
    CGImageRelease(bitmapImage);
    
    UIImage *result = [UIImage imageWithCGImage:scaledImage];
    return result;
}
void ProviderReleaseData (void *info, const void *data, size_t size){
    free((void*)data);
}

+ (UIImage*)imageBlackToTransparent:(UIImage*)image withRed:(CGFloat)red andGreen:(CGFloat)green andBlue:(CGFloat)blue{
    const int imageWidth = image.size.width;
    const int imageHeight = image.size.height;
    size_t      bytesPerRow = imageWidth * 4;
    uint32_t* rgbImageBuf = (uint32_t*)malloc(bytesPerRow * imageHeight);
    CGColorSpaceRef colorSpace = CGColorSpaceCreateDeviceRGB();
    CGContextRef context = CGBitmapContextCreate(rgbImageBuf, imageWidth, imageHeight, 8, bytesPerRow, colorSpace,
                                                 kCGBitmapByteOrder32Little | kCGImageAlphaNoneSkipLast);
    CGContextDrawImage(context, CGRectMake(0, 0, imageWidth, imageHeight), image.CGImage);
    // 遍历像素
    int pixelNum = imageWidth * imageHeight;
    uint32_t* pCurPtr = rgbImageBuf;
    for (int i = 0; i < pixelNum; i++, pCurPtr++){
        if ((*pCurPtr & 0xFFFFFF00) < 0x99999900)    // 将白色变成透明
        {
            // 改成下面的代码，会将图片转成想要的颜色
            uint8_t* ptr = (uint8_t*)pCurPtr;
            ptr[3] = red; //0~255
            ptr[2] = green;
            ptr[1] = blue;
        }
        else
        {
            uint8_t* ptr = (uint8_t*)pCurPtr;
            ptr[0] = 0;
        }
    }
    // 输出图片
    CGDataProviderRef dataProvider = CGDataProviderCreateWithData(NULL, rgbImageBuf, bytesPerRow * imageHeight, ProviderReleaseData);
    CGImageRef imageRef = CGImageCreate(imageWidth, imageHeight, 8, 32, bytesPerRow, colorSpace,
                                        kCGImageAlphaLast | kCGBitmapByteOrder32Little, dataProvider,
                                        NULL, true, kCGRenderingIntentDefault);
    CGDataProviderRelease(dataProvider);
    UIImage* resultUIImage = [UIImage imageWithCGImage:imageRef];
    // 清理空间
    CGImageRelease(imageRef);
    CGContextRelease(context);
    CGColorSpaceRelease(colorSpace);
    return resultUIImage;
}

+ (UIView *)tableViewsFooterView{
    UIView * coverView = [UIView new];
    coverView.backgroundColor = [UIColor clearColor];
    return coverView;
}

//去掉返回按钮上的title文字. 本页面设置,在下一页面生效
+ (UIBarButtonItem *)navigationBackButtonWithNoTitle{
    return [[UIBarButtonItem alloc] initWithTitle:@"" style:UIBarButtonItemStylePlain target:self action:nil];
}

+ (void)showSuccessMessage:(NSString *)message{
    [SVProgressHUD setDefaultStyle:SVProgressHUDStyleCustom];
    [SVProgressHUD setMinimumDismissTimeInterval:ToastShowTimeInterval];
    [SVProgressHUD setBackgroundColor:[UIColor grayColor]];
    [SVProgressHUD setForegroundColor:[UIColor whiteColor]];
    [SVProgressHUD showSuccessWithStatus:message];
}

+ (void)showErrorMessage:(NSString *)message{
    [SVProgressHUD setDefaultStyle:SVProgressHUDStyleCustom];
    [SVProgressHUD setMinimumDismissTimeInterval:ToastShowTimeInterval];
    [SVProgressHUD setBackgroundColor:[UIColor grayColor]];
    [SVProgressHUD setForegroundColor:[UIColor whiteColor]];
    [SVProgressHUD showErrorWithStatus:message];
}

+ (void)showProgressMessage:(NSString *) message{
    [SVProgressHUD setDefaultStyle:SVProgressHUDStyleCustom];
    [SVProgressHUD setBackgroundColor:[UIColor grayColor]];
    [SVProgressHUD setForegroundColor:[UIColor whiteColor]];
    [SVProgressHUD showWithStatus:message];
}

+ (void)showProgressMessageWithNotAllowTouch:(NSString *)message{
    [SVProgressHUD setDefaultStyle:SVProgressHUDStyleCustom];
    [SVProgressHUD setBackgroundColor:[UIColor grayColor]];
    [SVProgressHUD setForegroundColor:[UIColor whiteColor]];
    [SVProgressHUD showWithStatus:message];
}

+ (void)showInfoMessage:(NSString *)message{
    [SVProgressHUD setDefaultStyle:SVProgressHUDStyleCustom];
    [SVProgressHUD setMinimumDismissTimeInterval:2];
    [SVProgressHUD setBackgroundColor:[UIColor grayColor]];
    [SVProgressHUD setForegroundColor:[UIColor whiteColor]];
    [SVProgressHUD showInfoWithStatus:message];
}

+ (void)dismissHUD{
    [SVProgressHUD dismiss];
}

//获取IP地址
+ (NSString *)getIPAddress:(BOOL)preferIPv4{
    NSArray *searchArray = preferIPv4 ?
    @[ IOS_VPN @"/" IP_ADDR_IPv4, IOS_VPN @"/" IP_ADDR_IPv6, IOS_WIFI @"/" IP_ADDR_IPv4, IOS_WIFI @"/" IP_ADDR_IPv6, IOS_CELLULAR @"/" IP_ADDR_IPv4, IOS_CELLULAR @"/" IP_ADDR_IPv6 ] :
    @[ IOS_VPN @"/" IP_ADDR_IPv6, IOS_VPN @"/" IP_ADDR_IPv4, IOS_WIFI @"/" IP_ADDR_IPv6, IOS_WIFI @"/" IP_ADDR_IPv4, IOS_CELLULAR @"/" IP_ADDR_IPv6, IOS_CELLULAR @"/" IP_ADDR_IPv4 ] ;
    
    NSDictionary *addresses = [self getIPAddresses];
    NSLog(@"addresses: %@", addresses);
    
    __block NSString *address;
    [searchArray enumerateObjectsUsingBlock:^(NSString *key, NSUInteger idx, BOOL *stop)
     {
         address = addresses[key];
         if(address) *stop = YES;
     } ];
    return address ? address : @"0.0.0.0";
}

+ (NSDictionary *)getIPAddresses{
    NSMutableDictionary *addresses = [NSMutableDictionary dictionaryWithCapacity:8];
    // retrieve the current interfaces - returns 0 on success
    struct ifaddrs *interfaces;
    if(!getifaddrs(&interfaces)) {
        // Loop through linked list of interfaces
        struct ifaddrs *interface;
        for(interface=interfaces; interface; interface=interface->ifa_next) {
            if(!(interface->ifa_flags & IFF_UP) /* || (interface->ifa_flags & IFF_LOOPBACK) */ ) {
                continue; // deeply nested code harder to read
            }
            const struct sockaddr_in *addr = (const struct sockaddr_in*)interface->ifa_addr;
            char addrBuf[ MAX(INET_ADDRSTRLEN, INET6_ADDRSTRLEN) ];
            if(addr && (addr->sin_family==AF_INET || addr->sin_family==AF_INET6)) {
                NSString *name = [NSString stringWithUTF8String:interface->ifa_name];
                NSString *type;
                if(addr->sin_family == AF_INET) {
                    if(inet_ntop(AF_INET, &addr->sin_addr, addrBuf, INET_ADDRSTRLEN)) {
                        type = IP_ADDR_IPv4;
                    }
                } else {
                    const struct sockaddr_in6 *addr6 = (const struct sockaddr_in6*)interface->ifa_addr;
                    if(inet_ntop(AF_INET6, &addr6->sin6_addr, addrBuf, INET6_ADDRSTRLEN)) {
                        type = IP_ADDR_IPv6;
                    }
                }
                if(type) {
                    NSString *key = [NSString stringWithFormat:@"%@/%@", name, type];
                    addresses[key] = [NSString stringWithUTF8String:addrBuf];
                }
            }
        }
        // Free memory
        freeifaddrs(interfaces);
    }
    return [addresses count] ? addresses : nil;
}

//当前的版本号
+ (NSString *)currentVersion{
    NSString *version = @"";
    version = [[[NSBundle mainBundle] infoDictionary] objectForKey:@"CFBundleShortVersionString"];
    return version;
}

//是否是首次使用当前版本,如果是则显示引导页
+ (BOOL)isFirstLaunchCurrentVersion{
    BOOL isFirst = NO;
    
    NSString *versionKey = (__bridge NSString *)kCFBundleVersionKey;
    versionKey = @"CFBundleShortVersionString";
    //从沙盒中取出上次存储的软件版本号(取出用户上次的使用记录)
    NSString *lastVersion = [[NSUserDefaults standardUserDefaults] objectForKey:versionKey];
    //获得当前打开软件的软件版本号
    NSString *currentVersion = [NSBundle mainBundle].infoDictionary[versionKey];
    
    if ([currentVersion isEqualToString:lastVersion]) {
        //当前版本号 == 上次使用的版本号
        DLog(@"不是首次使用 , 版本号: %@",currentVersion);
        isFirst = NO;
    }else{
        //当前版本号 != 上次使用的版本号
        DLog(@"是首次使用 , 版本号: %@",currentVersion);
        isFirst = YES;
        //存储这次使用的软件版本
        [[NSUserDefaults standardUserDefaults] setObject:currentVersion forKey:versionKey];
        [[NSUserDefaults standardUserDefaults] synchronize];
    }
//    TODO: 上正式环境时,注释该行语句
//    isFirst = YES;
    return isFirst;
}


+ (BOOL)isFirstLoad{
    NSString *currentVersion = [[[NSBundle mainBundle] infoDictionary]
                                objectForKey:@"CFBundleShortVersionString"];
    
    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
    
    NSString *lastRunVersion = [defaults objectForKey:LAST_RUN_VERSION_KEY];
    
    if (!lastRunVersion) {
        [defaults setObject:currentVersion forKey:LAST_RUN_VERSION_KEY];
        return YES;
    }
    else if (![lastRunVersion isEqualToString:currentVersion]) {
        [defaults setObject:currentVersion forKey:LAST_RUN_VERSION_KEY];
        return YES;
    }
    return NO;
}


//是否显示操作指南
+ (BOOL)showUpgradeGuideView:(NSString *)name{
    //TODO: 上正式环境时,注释该行语句
//    [[NSUserDefaults standardUserDefaults] removeObjectForKey:name];
    
    BOOL result = NO;
    //获得当前打开软件的软件版本号
    NSString *currentVersion = [NSBundle mainBundle].infoDictionary[@"CFBundleShortVersionString"];
    NSString *cacheVersion = [[NSUserDefaults standardUserDefaults] objectForKey:name];
    
    if ([currentVersion isEqualToString:cacheVersion]) {
        result = NO;
    }else{
        result = YES;
        [[NSUserDefaults standardUserDefaults] setObject:currentVersion forKey:name];
        [[NSUserDefaults standardUserDefaults] synchronize];
    }
    return result;
}

//将NSDictionary中的Null类型的项目转化成nil
+ (NSDictionary *)nullDic:(NSDictionary *)myDic{
    if (![myDic isKindOfClass:[NSNull class]]) {
        NSArray *keyArr = [myDic allKeys];
        NSMutableDictionary *resDic = [[NSMutableDictionary alloc]init];
        for (int i = 0; i < keyArr.count; i ++){
            id obj = [myDic objectForKey:keyArr[i]];
            
            obj = [self changeType:obj];
            
            [resDic setObject:obj forKey:keyArr[i]];
        }
        return resDic;
    }
    return [NSDictionary dictionary];
}
//将NSDictionary中的Null类型的项目转化成nil
+ (NSArray *)nullArr:(NSArray *)myArr{
    NSMutableArray *resArr = [[NSMutableArray alloc] init];
    if (![myArr isKindOfClass:[NSNull class]]) {
        for (int i = 0; i < myArr.count; i ++)
        {
            id obj = myArr[i];
            
            obj = [self changeType:obj];
            
            [resArr addObject:obj];
        }
    }
    return resArr;
}
//类型识别:将所有的NSNull类型转化成nil
+ (id)changeType:(id)myObj{
    if ([myObj isKindOfClass:[NSDictionary class]])
    {
        return [self nullDic:myObj];
    }
    else if([myObj isKindOfClass:[NSArray class]])
    {
        return [self nullArr:myObj];
    }
    else if([myObj isKindOfClass:[NSString class]])
    {
        return myObj;
    }
    else if([myObj isKindOfClass:[NSNull class]])
    {
        return @"";
    }
    else
    {
        return myObj;
    }
}

//遍历文件夹获得文件夹大小，返回多少M
+ (float ) folderSizeAtPath:(NSString*) folderPath{
    NSFileManager* manager = [NSFileManager defaultManager];
    if (![manager fileExistsAtPath:folderPath]) return 0;
    NSEnumerator *childFilesEnumerator = [[manager subpathsAtPath:folderPath] objectEnumerator];
    NSString* fileName;
    long long folderSize = 0;
    while ((fileName = [childFilesEnumerator nextObject]) != nil){
        NSString* fileAbsolutePath = [folderPath stringByAppendingPathComponent:fileName];
        folderSize += [self fileSizeAtPath:fileAbsolutePath];
    }
    return folderSize/(1024.0*1024.0);
}

//单个文件的大小
+ (long long) fileSizeAtPath:(NSString*) filePath{
    NSFileManager* manager = [NSFileManager defaultManager];
    if ([manager fileExistsAtPath:filePath]){
        return [[manager attributesOfItemAtPath:filePath error:nil] fileSize];
    }
    return 0;
}



+(BOOL)isBlankString:(NSString *)string{
    if (string ==nil || string ==NULL) {
        return YES;
    }
    if ([string isKindOfClass:[NSNull class]]) {
        return YES;
    }
//    if ([[string stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceCharacterSet]]length]==0) {//特殊字符判断
//        return YES;
//    }
    return NO;
}


//判断是否是emoji表情
+(BOOL)isContainsEmoji:(NSString *)string {
    __block BOOL isEmoji = NO;
    [string enumerateSubstringsInRange:NSMakeRange(0,[string length]) options:NSStringEnumerationByComposedCharacterSequences usingBlock:^(NSString *substring, NSRange substringRange, NSRange enclosingRange, BOOL *stop) {
        const unichar hs = [substring characterAtIndex:0];
        // surrogate pair
        if (0xd800 <= hs && hs <= 0xdbff) {
            if (substring.length > 1) {
                const unichar ls = [substring characterAtIndex:1];
                const int uc = ((hs - 0xd800) * 0x400) + (ls - 0xdc00) + 0x10000;
                if (0x1d000 <= uc && uc <= 0x1fa95) {
                    isEmoji = YES;
                }
            }
         }else if (substring.length > 1) {
             const unichar ls = [substring characterAtIndex:1];
             if (ls == 0x20e3 || ls == 0xfe0f || ls == 0xd83c) {
                 isEmoji = YES;
             }
         } else {
             // non surrogate
             if (0x2100 <= hs && hs <= 0x27ff) {
                 isEmoji = YES;
             }else if (0x2B05 <= hs && hs <= 0x2b07) {
                 isEmoji = YES;
             } else if (0x2934 <= hs && hs <= 0x2935) {
                 isEmoji = YES;
             }else if (0x3297 <= hs && hs <= 0x3299) {
                 isEmoji = YES;
             }else if (hs == 0xa9 || hs == 0xae || hs == 0x303d || hs == 0x3030 || hs == 0x2b55 || hs == 0x2b1c || hs == 0x2b1b || hs == 0x2b50) {
                 isEmoji = YES;
             }
         }
         if (isEmoji) {
             *stop = YES;
         }
     }];
    return isEmoji;
}

@end
