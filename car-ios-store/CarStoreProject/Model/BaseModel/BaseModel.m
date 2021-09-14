//
//  BaseModel.m
//  PrayWithPureBody
//
//  Created by 申巧 on 2017/5/9.
//  Copyright © 2017年 申巧. All rights reserved.
//

#import "BaseModel.h"
#import <objc/runtime.h>

@interface BaseModel ()<NSCoding>{
}
@end

@implementation BaseModel

- (id)initWithCoder:(NSCoder *)aDecoder{
    if (self = [super init]) {
        unsigned int outCount;
        //获取类中所有属性名
        objc_property_t *properties = class_copyPropertyList([self class], &outCount);
        for (int i = 0; i < outCount; i++) {
            objc_property_t property = properties[i];
            const char *propertyName = property_getName(property);
            NSString *strName = [NSString stringWithUTF8String:propertyName];
            id value = [aDecoder decodeObjectForKey:strName];
            [self setValue:value forKey:strName];
            
//            const char *propertyAttribute = property_getAttributes(property);
//            NSString *propertyDesc = [NSString stringWithUTF8String:propertyAttribute];
//            NSLog(@" propertyDesc %@",propertyDesc);
            //propertyDesc: T@"NSString",C,N,V_name
            //propertyDesc 总会以一个T开始,@ 代表这个property是一个对象,而不是基础数据类型,"NSString" 代表这个property是NSString类型, C 代表copy, V_name 代表property的名字
        }
        free(properties);
    }
    return self;
}

- (void)encodeWithCoder:(NSCoder *)aCoder{
    unsigned int count;
    objc_property_t *properties = class_copyPropertyList([self class], &count);
    for (int i = 0; i < count; i++) {
        objc_property_t property = properties[i];
        const char *propertyName = property_getName(property);
        NSString *strName = [NSString stringWithUTF8String:propertyName];
        id value = [self valueForKey:strName];
//        DLog(@" 属性名：%@,值：%@",strName,value);
        [aCoder encodeObject:value forKey:strName];
    }
    free(properties);
}

+ (NSArray *)getVariableNameByObject:(id)object{
    unsigned int numIvars = 0;
    //获取类的所有成员变量
    Ivar *ivars = class_copyIvarList([object class], &numIvars);
    //定义一个数组来接收获取的属性名
    NSMutableArray *nameArray = [[NSMutableArray alloc] initWithCapacity:numIvars];
    for (int i = 0; i < numIvars; i++) {
        //得到单个的成员变量
        Ivar thisIvar = ivars[i];
        
        //得到成员变量名
        NSString *variableName = [NSString stringWithUTF8String:ivar_getName(thisIvar)];
        DLog(@" 成员变量的名称: %@",variableName);
        [nameArray addObject:variableName];
        
        //得到这个成员变量的类型
        const char *type = ivar_getTypeEncoding(thisIvar);
        DLog(@" 成员变量的类型:%s ",type);
        NSString *stringType = [NSString stringWithCString:type encoding:NSUTF8StringEncoding];
        
        id value = [object valueForKey:variableName];
        DLog(@" 成员变量的值: %@",value);
        //此处判断非objc类型时跳过
        if ([stringType hasPrefix:@"@"]) {
            //这个函数可以得到成员变量的值. 仅限objc类型的变量
            id objValue = object_getIvar(object, thisIvar);
            DLog(@" objc类型的成员变量的值: %@",objValue);
        }
    }
    free(ivars);
    return nameArray;
}

+ (NSArray *)getPropertyNameByObject:(id)object{
    unsigned int outCount;
    //获取类中所有属性名
    objc_property_t *properties = class_copyPropertyList([self class], &outCount);
    //定义一个数组来接收获取的属性名
    NSMutableArray *nameArray = [[NSMutableArray alloc] initWithCapacity:outCount];
    for (int i = 0; i < outCount; i++) {
        objc_property_t property = properties[i];
        
        const char *propertyName = property_getName(property);
        NSString *strName = [NSString stringWithUTF8String:propertyName];
        DLog(@" 属性的名称: %@",strName);
        [nameArray addObject:strName];
        
        const char *propertyAttribute = property_getAttributes(property);
        NSString *propertyDesc = [NSString stringWithUTF8String:propertyAttribute];
        NSString *propertyType = [[propertyDesc componentsSeparatedByString:@","] firstObject];
        DLog(@" 属性的类型: %@",propertyType);
//        Ti int ; Tq long NSInteger  ; Td CGFloat double ; TQ NSUInteger ; Tf float ;
//        TB BOOL ; Tc char ; Ts short ;
        
        id value = [object valueForKey:strName];
        DLog(@" 属性的值: %@",value);
    }
    free(properties);
    return nameArray;
}

@end
