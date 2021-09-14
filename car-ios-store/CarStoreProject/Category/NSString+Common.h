//
//  NSString+Common.h

#import <Foundation/Foundation.h>

@interface NSString (Common)

//NSAttributedString 转成 NSString
+ (NSString *)transformAttributeString:(NSAttributedString *)attributeString;

- (NSString *) sha1Str;

+ (NSString *)requestJsonStr:(NSString *)sureJsonStr;

+ (NSString *)sizeDisplayWithByte:(CGFloat)sizeOfByte;

- (NSString *)stringByTrimmingLeftCharactersInSet:(NSCharacterSet *)characterSet;
- (NSString *)stringByTrimmingRightCharactersInSet:(NSCharacterSet *)characterSet;

//添加 http:// 前缀
- (NSString *)addUrlPrefix;

//根据字体大小及最大size限制，获取文本大小
+ (CGSize)getSizeWithString:(NSString *)string font:(UIFont *)font constrainedSize:(CGSize)size;
//根据字体大小及最大size限制，获取富文本大小
+ (CGSize)getSizeWithAttributedString:(NSMutableAttributedString *)attri size:(CGSize)size;

//url参数拼接  确保url中只包含一个?号
- (NSString *)appendUrlParaWith:(NSString *)para;

//判断字符串中是否存在汉字
+ (BOOL)IsChinese:(NSString *)str;


//把字符串替换成星号
-(NSString *)replaceStringWithAsterisk:(NSString *)originalStr startLocation:(NSInteger)startLocation lenght:(NSInteger)lengh;
//银行卡号加入* 并四个一个空格
+(NSString *)getNewBankNumWitOldBankNum:(NSString *)bankNum;

//四个一个空格
+(NSString *)getNewNumberWithOldNum:(NSString *)cardNum;


/**
 *  字符串 自加或自减
 *
 *  @isAdd  是否是自加
 *  @return 返回的字符串
 */
- (NSString *)changSelfByAddOrReduce:(BOOL)isAdd;

- (NSString *)newUrlEncode;
- (NSString *)newUrlDecode;

- (NSRange)rangeByTrimmingRightCharactersInSet:(NSCharacterSet *)characterSet;

//截取字符串长度
+(NSString*)subTextString:(NSString*)str len:(NSInteger)len;

//转换拼音
- (NSString *)transformToPinyin;

//计算字符串的长度转换成类似ASCII编码的长度
+ (int)convertToInt:(NSString*)strtemp;

//将 &lt 等类似的字符串转化为HTML中的"<"等
+ (NSString *)htmlEntityDecode:(NSString *)string;

//去除HTML文本标签（正则）
+ (NSString *)colatingHtml:(NSString *)html;

//过滤emoji表情字符
+ (NSString *)filterEmoji:(NSString *)inputStr;

- (NSString *)verticalString;

//字符串脱敏处理     type类型  1：前部脱敏   2：中部脱敏   3：尾部脱敏
- (NSString *)desensitizationWithCount:(NSInteger)count type:(int)type;

#pragma mark - **************** 一些常用的正则表达式
//去除空格
- (NSString *)trimWhitespace;
//是否为空字符串
- (BOOL)isEmpty;
//是否为空字符串 , (nil,空格,换行,制表符都为空字符串)
- (BOOL)isEmptyString;
//判断是否为整形
- (BOOL)isPureInt;
//判断是否为浮点形
- (BOOL)isPureFloat;
// 验证是不是数字
- (BOOL)isNumber;
//  验证是不是英文
- (BOOL)isEnglish;
//  验证是不是汉字
+ (BOOL)isChinese:(NSString *)text;
// 验证是不是网络链接地址
- (BOOL)isInternetUrl;
//邮箱
+ (BOOL)validateEmail:(NSString *)email;
//手机号码验证
+ (BOOL)validateMobile:(NSString *)mobile;
//车牌号验证
+ (BOOL)validateCarNo:(NSString *)carNo;
//车型
+ (BOOL)validateCarType:(NSString *)CarType;
//用户名
+ (BOOL)validateUserName:(NSString *)name;
// 密码
+ (BOOL)validatePassword:(NSString *)passWord;
//验证码
+ (BOOL)validateRandom:(NSString *)random;
//身份证号
+ (BOOL)validateIdentityCard: (NSString *)identityCard;
//验证车辆识别代码
+ (BOOL)validateCarIdentCode:(NSString *)code;
//验证发动机号码
+ (BOOL)validateEngineNumber:(NSString *)code;
//验证用户姓名  (2-20位的中/英文姓名)
+ (BOOL)validateName:(NSString *)name;
//验证详细地址 (不超过50个中/英文字符)
+ (BOOL)validateAddress:(NSString *)address;
//验证邮编
+ (BOOL)validatePostcode:(NSString *)postCode;
//验证省市名  (2-10位中文字符)
+ (BOOL)validateProvinceCity:(NSString *)city;
//验证个人简介 (不超过50个字符)
+ (BOOL) validateIntrobuction:(NSString *)address length:(int)len;
//验证微信号码
+ (BOOL)validateWXUsername:(NSString *)text;
//验证座机号码
+ (BOOL)validateFixedTelephone:(NSString *)text;
// 验证邮箱是不是可用
- (BOOL)isValidateEmail;
//字符串是否包含emoji表情
- (BOOL)containsEmoji;

- (CGSize)czh_sizeWithFont:(UIFont *)font maxH:(CGFloat)maxH;
- (CGSize)czh_sizeWithFont:(UIFont *)font maxW:(CGFloat)maxW;
- (CGSize)czh_sizeWithFont:(UIFont *)font;

@end
