//
//  UserModel.h
//  CarStoreProject
//
//  Created by apple on 2021/1/28.
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

@interface UserModel : NSObject
+(UserModel*)user;

@property(nonatomic,copy)NSString *token;

@property(nonatomic,copy)NSString *phone;


@property(nonatomic,copy)NSString *expires_in;
//用户类型1车主2技师3店铺
@property(nonatomic,copy)NSString *userType;

@property(nonatomic,copy)NSString *uuid;

//本身定位的经纬度
@property(nonatomic,copy)NSString *latitude;
@property(nonatomic,copy)NSString *longitude;


@end

NS_ASSUME_NONNULL_END
