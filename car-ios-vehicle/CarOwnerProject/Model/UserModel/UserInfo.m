//
//  UserInfo.m
//  CarStoreProject
//
//  Created by apple on 2021/1/28.
//

#import "UserInfo.h"

@implementation UserInfo

+(void)saveUserInfo:(UserModel*)userModel
{
    NSData *userData = [NSKeyedArchiver archivedDataWithRootObject:userModel];
    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
    [defaults setObject:userData forKey:@"userModel"];
    [defaults synchronize];
}

+(UserModel*)getUserInfo
{
    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
    NSData *myEncodedObject = [defaults objectForKey:@"userModel"];
    UserModel *obj = (UserModel *)[NSKeyedUnarchiver unarchiveObjectWithData:myEncodedObject];
    return obj;
}
@end
