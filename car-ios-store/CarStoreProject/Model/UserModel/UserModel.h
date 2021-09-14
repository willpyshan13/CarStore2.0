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

//审核状态(0:待审核 1:审核通过 2:审核驳回) ,
@property(nonatomic,copy)NSString *checkSts;


@end

NS_ASSUME_NONNULL_END
