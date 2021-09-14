//
//  QueryTreeListModel.m
//  CarOwnerProject
//
//  Created by apple on 2021/1/29.
//

#import "QueryTreeListModel.h"

@implementation QueryTreeListModel

+(NSDictionary *)mj_replacedKeyFromPropertyName
{
    return @{
        @"subListArray":@"subList",
    };
}
+(NSDictionary *)mj_objectClassInArray
{
    return @{
        @"subListArray":[QueryTreeListSubListModel class],
    };
}

@end
