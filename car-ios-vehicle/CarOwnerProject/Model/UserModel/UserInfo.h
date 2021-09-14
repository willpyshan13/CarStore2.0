//
//  UserInfo.h
//  CarStoreProject
//
//  Created by apple on 2021/1/28.
//

#import <Foundation/Foundation.h>
#import "UserModel.h"
NS_ASSUME_NONNULL_BEGIN

@interface UserInfo : NSObject

/**
 存储用户信息到本地
 
 @param user userModel
 */
+(void)saveUserInfo:(UserModel*)user;
/**
 获取本地用户信息
 
 @return 返回userModel对象
 */
+(UserModel*)getUserInfo;

@end

NS_ASSUME_NONNULL_END
