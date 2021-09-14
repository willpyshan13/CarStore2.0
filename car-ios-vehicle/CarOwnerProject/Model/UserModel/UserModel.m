//
//  UserModel.m
//  CarStoreProject
//
//  Created by apple on 2021/1/28.
//

#import "UserModel.h"

@implementation UserModel
+(UserModel*)user
{
    UserModel *user=[[UserModel alloc]init];
    return user;
}

-(void)encodeWithCoder:(NSCoder *)aCoder
{
    [aCoder encodeObject:[NSString stringWithFormat:@"%@",self.token] forKey:@"token"];
    [aCoder encodeObject:[NSString stringWithFormat:@"%@",self.phone] forKey:@"phone"];

    [aCoder encodeObject:[NSString stringWithFormat:@"%@",self.expires_in] forKey:@"expires_in"];
    [aCoder encodeObject:[NSString stringWithFormat:@"%@",self.userType] forKey:@"userType"];
    [aCoder encodeObject:[NSString stringWithFormat:@"%@",self.uuid] forKey:@"uuid"];
    [aCoder encodeObject:[NSString stringWithFormat:@"%@",self.latitude] forKey:@"latitude"];
    [aCoder encodeObject:[NSString stringWithFormat:@"%@",self.longitude] forKey:@"longitude"];

    
}

-(id)initWithCoder:(NSCoder *)aDecoder
{
    self=[super init];
    if (self)
    {
        self.token=[aDecoder decodeObjectForKey:@"token"];
        self.phone=[aDecoder decodeObjectForKey:@"phone"];
        self.expires_in=[aDecoder decodeObjectForKey:@"expires_in"];
        self.userType = [aDecoder decodeObjectForKey:@"userType"];
        self.uuid=[aDecoder decodeObjectForKey:@"uuid"];
        self.latitude = [aDecoder decodeObjectForKey:@"latitude"];
        self.longitude=[aDecoder decodeObjectForKey:@"longitude"];
        
       
    }
    return self;
    
}

@end
