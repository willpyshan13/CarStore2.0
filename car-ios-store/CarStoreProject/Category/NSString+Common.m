//
//  NSString+Common.m
//  Coding_iOS
//
//  Created by 王 原闯 on 14-7-31.
//  Copyright (c) 2014年 Coding. All rights reserved.
//

#import "NSString+Common.h"
#import <CommonCrypto/CommonDigest.h>
#import <CommonCrypto/CommonDigest.h>
#import "sys/utsname.h"
#import <CoreText/CoreText.h>

#define REGULAR_FILE_NAME @"Regular"

// 判断邮件格式正则表达式
#define EMAIL_REGEX_NAME @"\\b([a-zA-Z0-9%_.+\\-]+)@([a-zA-Z0-9.\\-]+?\\.[a-zA-Z]{2,6})\\b"
// 判断数字正则表达式
#define NUMBER_REGEX_NAME @"^[0-9]*$"
// 判断英文正则表达式
#define ENGLISH_REGEX_NAME @"^[A-Za-z]+$"
// 判断中文正则表达式
#define CHINESE_REGEX_NAME @"^[\u4e00-\u9fa5],{0,}$"
// 判断网址正则表达式
#define INTERNET_URL_REGEX_NAME @"^http://([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$ ;^[a-zA-z]+://(w+(-w+)*)(.(w+(-w+)*))*(?S*)?$"

@implementation NSString (Common)

//NSAttributedString 转成 NSString
+ (NSString *)transformAttributeString:(NSAttributedString *)attributedText{
    NSMutableAttributedString * resutlAtt = [[NSMutableAttributedString alloc]initWithAttributedString:attributedText];
    
//    EmoticonsHelper * helper = [EmoticonsHelper new];
    //枚举出所有的附件字符串
    [attributedText enumerateAttributesInRange:NSMakeRange(0, attributedText.length) options:NSAttributedStringEnumerationReverse usingBlock:^(NSDictionary *attrs, NSRange range, BOOL *stop) {
        NSTextAttachment * textAtt = attrs[@"NSAttachment"];//从字典中取得那一个图片
//        if (textAtt)
//        {
//            UIImage * image = textAtt.image;
//            NSString * text = [helper stringFromImage:image];
//            [resutlAtt replaceCharactersInRange:range withString:text];
//        }
    }];
    return resutlAtt.string;
}

- (NSString*)sha1Str{
    const char *cstr = [self cStringUsingEncoding:NSUTF8StringEncoding];
    NSData *data = [NSData dataWithBytes:cstr length:self.length];
    
    uint8_t digest[CC_SHA1_DIGEST_LENGTH];
    
    CC_SHA1(data.bytes, (CC_LONG)data.length, digest);
    
    NSMutableString* output = [NSMutableString stringWithCapacity:CC_SHA1_DIGEST_LENGTH * 2];
    
    for(int i = 0; i < CC_SHA1_DIGEST_LENGTH; i++)
        [output appendFormat:@"%02x", digest[i]];
    
    return output;
}


+ (NSString *)requestJsonStr:(NSString *)sureJsonStr
{
    NSString *symptomStr =sureJsonStr;
    NSString *sureStr = [symptomStr stringByReplacingOccurrencesOfString:@"\\" withString:@" "];
    NSData *jsonData = [sureStr dataUsingEncoding:NSUTF8StringEncoding];
    NSError *err;
    NSDictionary *dic = [NSJSONSerialization JSONObjectWithData:jsonData
                                                        options:NSJSONReadingMutableContainers
                                                          error:&err];
    NSMutableArray *bigAllArray =[[NSMutableArray alloc]init];//症状
    if(err) {
        NSLog(@"json解析失败：%@",err);
    }
    else
    {
        NSMutableArray *allArray =[[NSMutableArray alloc]init];
        [dic enumerateKeysAndObjectsUsingBlock:^(id key, id obj, BOOL *stop) {
            //                NSLog(@"key = %@ and obj = %@", key, obj);            
            /*
                 obj ===字典中所有的value值
                1、如果obj是字符串类型 则转成字典类型
                2、把所有转成数组类型的值存入大数组中
             */
            if ([obj isKindOfClass:[NSString class]]) {//
                NSString *text1 = obj;
                NSArray *array11 = [text1 componentsSeparatedByString:@","];
                [allArray addObject:array11];
            }else
            {
                 [allArray addObject:obj];
            }
        }];
        for (int i=0; i<allArray.count; i++) {
            NSArray *otherArr=allArray[i];
            if (otherArr.count>0) {
                NSString *otherStr = [otherArr componentsJoinedByString:@"、"];
                [bigAllArray addObject:otherStr];
            }
        }
    }
    NSString *zhengStr = [bigAllArray componentsJoinedByString:@"、"];
    return zhengStr;
}

+ (NSString *)sizeDisplayWithByte:(CGFloat)sizeOfByte{
    NSString *sizeDisplayStr;
    if (sizeOfByte < 1024) {
        sizeDisplayStr = [NSString stringWithFormat:@"%.2f bytes", sizeOfByte];
    }else{
        CGFloat sizeOfKB = sizeOfByte/1024;
        if (sizeOfKB < 1024) {
            sizeDisplayStr = [NSString stringWithFormat:@"%.2f KB", sizeOfKB];
        }else{
            CGFloat sizeOfM = sizeOfKB/1024;
            if (sizeOfM < 1024) {
                sizeDisplayStr = [NSString stringWithFormat:@"%.2f M", sizeOfM];
            }else{
                CGFloat sizeOfG = sizeOfKB/1024;
                sizeDisplayStr = [NSString stringWithFormat:@"%.2f G", sizeOfG];
            }
        }
    }
    return sizeDisplayStr;
}

- (NSString *)stringByTrimmingLeftCharactersInSet:(NSCharacterSet *)characterSet {
    NSUInteger location = 0;
    NSUInteger length = [self length];
    unichar charBuffer[length];
    [self getCharacters:charBuffer];
    for (location = 0; location < length; location++) {
        if (![characterSet characterIsMember:charBuffer[location]]) {
            break;
        }
    }
    return [self substringWithRange:NSMakeRange(location, length - location)];
}

- (NSString *)stringByTrimmingRightCharactersInSet:(NSCharacterSet *)characterSet {
    NSUInteger location = 0;
    NSUInteger length = [self length];
    unichar charBuffer[length];
    [self getCharacters:charBuffer];
    for (length = [self length]; length > 0; length--) {
        if (![characterSet characterIsMember:charBuffer[length - 1]]) {
            break;
        }
    }
    return [self substringWithRange:NSMakeRange(location, length - location)];
}
//添加 http:// 前缀
- (NSString *)addUrlPrefix
{
    if ([self hasPrefix:@"http"]) {
        return self;
    }else{
        return [NSString stringWithFormat:@"http://%@",self];
    }
}

+ (CGSize)getSizeWithString:(NSString *)string font:(UIFont *)font constrainedSize:(CGSize)size{
    CGSize resultSize = CGSizeZero;
    if (string.length <= 0) {
        return resultSize;
    }
    if (NSFoundationVersionNumber >= NSFoundationVersionNumber_iOS_7_0) {
        //NSStringDrawingUsesFontLeading 计算行高时使用行间距（注：字体大小+行间距=行高)
        //NSStringDrawingUsesLineFragmentOrigin 以每行组成的矩形为单位计算整个文本的尺寸
        //NSStringDrawingTruncatesLastVisibleLine 如果文本内容超出指定的矩形限制时,文本将被截去并在最后一个字符后加上省略号.如果指定了 NSStringDrawingUsesLineFragmentOrigin 选项,则该选项被忽略
        resultSize = [string boundingRectWithSize:size
                                        options:(NSStringDrawingUsesFontLeading | NSStringDrawingUsesLineFragmentOrigin)
                                     attributes:@{NSFontAttributeName: font}
                                        context:nil].size;
    } else {
#if __IPHONE_OS_VERSION_MIN_REQUIRED < __IPHONE_7_0
        resultSize = [self sizeWithFont:font constrainedToSize:size lineBreakMode:NSLineBreakByWordWrapping];
#endif
    }
    resultSize = CGSizeMake(MIN(size.width, ceilf(resultSize.width)), MIN(size.height, ceilf(resultSize.height)));
    if ([string containsEmoji]) {
        resultSize.height += 10;
    }
    return resultSize;
}

+ (CGSize)getSizeWithAttributedString:(NSMutableAttributedString *)attri size:(CGSize)size{
    CGFloat height = 0;
    
    CTFramesetterRef framesetter = CTFramesetterCreateWithAttributedString ((CFMutableAttributedStringRef) attri);
    CGRect box = CGRectMake(0,0, size.width, size.height);
    
    CFIndex startIndex = 0;
    
    CGMutablePathRef path = CGPathCreateMutable();
    CGPathAddRect(path, NULL, box);
    
    CTFrameRef frame = CTFramesetterCreateFrame(framesetter, CFRangeMake(startIndex, 0), path, NULL);
    
    CFArrayRef lineArray = CTFrameGetLines(frame);
    CFIndex j = 0, lineCount = CFArrayGetCount(lineArray);
    CGFloat lineHeight, ascent, descent, leading;
    
    for (j=0; j < lineCount; j++) {
        CTLineRef currentLine = (CTLineRef)CFArrayGetValueAtIndex(lineArray, j);
        CTLineGetTypographicBounds(currentLine, &ascent, &descent, &leading);
        lineHeight = ascent + descent + leading+5.0f;
        height += lineHeight;
    }
    
    CFRelease(frame);
    CFRelease(path);
    CFRelease(framesetter);
    
    CGSize result = (CGSize){size.width,height};
    return result;
}

//url参数拼接  确保url中只包含一个?号
- (NSString *)appendUrlParaWith:(NSString *)para{
    NSString *result = self;
    
    if ([self containsString:@"?"]) {
        result = [NSString stringWithFormat:@"%@&%@",self,para];
    }else{
        result = [NSString stringWithFormat:@"%@?%@",self,para];
    }
    
    return result;
}

//把字符串替换成星号
-(NSString *)replaceStringWithAsterisk:(NSString *)originalStr startLocation:(NSInteger)startLocation lenght:(NSInteger)lengh
{
    NSString *newStr = originalStr;
    
    for (int i = 0; i < lengh; i++) {
        
        NSRange range = NSMakeRange(startLocation, 1);
        
        newStr = [newStr stringByReplacingCharactersInRange:range withString:@"*"];
        
        startLocation ++;
        
    }
    
    return newStr;
}
//银行卡号加入* 并四个一个空格
+(NSString *)getNewBankNumWitOldBankNum:(NSString *)bankNum
{
    NSMutableString *mutableStr;
    if (bankNum.length) {
        mutableStr = [NSMutableString stringWithString:bankNum];
        for (int i = 0 ; i < mutableStr.length; i ++) {
            if (i>2&&i<mutableStr.length - 3) {
                [mutableStr replaceCharactersInRange:NSMakeRange(i, 1) withString:@"*"];
            }
        }
        NSString *text = mutableStr;
        NSCharacterSet *characterSet = [NSCharacterSet characterSetWithCharactersInString:@"0123456789\b"];
        text = [text stringByReplacingOccurrencesOfString:@" " withString:@""];
        NSString *newString = @"";
        while (text.length > 0) {
            NSString *subString = [text substringToIndex:MIN(text.length, 4)];
            newString = [newString stringByAppendingString:subString];
            if (subString.length == 4) {
                newString = [newString stringByAppendingString:@" "];
            }
            text = [text substringFromIndex:MIN(text.length, 4)];
        }
        newString = [newString stringByTrimmingCharactersInSet:[characterSet invertedSet]];
        return newString;
    }
    return bankNum;
    
}

+(NSString *)getNewNumberWithOldNum:(NSString *)cardNum
{
    NSMutableString *mutableStr;
       if (cardNum.length) {
           mutableStr = [NSMutableString stringWithString:cardNum];
//           for (int i = 0 ; i < mutableStr.length; i ++) {
//               if (i>2&&i<mutableStr.length - 3) {
//                   [mutableStr replaceCharactersInRange:NSMakeRange(i, 1) withString:@"*"];
//               }
//           }
           NSString *text = mutableStr;
           NSCharacterSet *characterSet = [NSCharacterSet characterSetWithCharactersInString:@"0123456789\b"];
           text = [text stringByReplacingOccurrencesOfString:@" " withString:@""];
           NSString *newString = @"";
           while (text.length > 0) {
               NSString *subString = [text substringToIndex:MIN(text.length, 4)];
               newString = [newString stringByAppendingString:subString];
               if (subString.length == 4) {
                   newString = [newString stringByAppendingString:@" "];
               }
               text = [text substringFromIndex:MIN(text.length, 4)];
           }
           newString = [newString stringByTrimmingCharactersInSet:[characterSet invertedSet]];
           return newString;
       }
       return cardNum;
}

- (NSString *)changSelfByAddOrReduce:(BOOL)isAdd{
    int stringValue = [self intValue];
    NSString *newStr = nil;
    if (isAdd) {
        newStr = [NSString stringWithFormat:@"%d",++stringValue];
    }else{
        newStr = [NSString stringWithFormat:@"%d",--stringValue];
    }
    
    return newStr;
}

- (NSString *)newUrlEncode{
    NSString *url = [self stringByAddingPercentEncodingWithAllowedCharacters:[NSCharacterSet characterSetWithCharactersInString:@"!*'\"();:@&=+$,/?%#[]% "].invertedSet];
    return url;
}

- (NSString *)newUrlDecode{
    return [self stringByRemovingPercentEncoding];
}

- (NSRange)rangeByTrimmingLeftCharactersInSet:(NSCharacterSet *)characterSet{
    NSUInteger location = 0;
    NSUInteger length = [self length];
    unichar charBuffer[length];
    [self getCharacters:charBuffer];
    for (location = 0; location < length; location++) {
        if (![characterSet characterIsMember:charBuffer[location]]) {
            break;
        }
    }
    return NSMakeRange(location, length - location);
}

- (NSRange)rangeByTrimmingRightCharactersInSet:(NSCharacterSet *)characterSet{
    NSUInteger location = 0;
    NSUInteger length = [self length];
    unichar charBuffer[length];
    [self getCharacters:charBuffer];
    for (length = [self length]; length > 0; length--) {
        if (![characterSet characterIsMember:charBuffer[length - 1]]) {
            break;
        }
    }
    return NSMakeRange(location, length - location);
}

//截取字符串长度
+(NSString*)subTextString:(NSString*)str len:(NSInteger)len
{
    if(str.length<=len)return str;
    int count=0;
    NSMutableString *sb = [NSMutableString string];
    
    for (int i=0; i<str.length; i++) {
        NSRange range = NSMakeRange(i, 1) ;
        NSString *aStr = [str substringWithRange:range];
        count += [aStr lengthOfBytesUsingEncoding:NSUTF8StringEncoding]>1?2:1;
        [sb appendString:aStr];
        if(count >= len*2) {
            return (i==str.length-1)?[sb copy]:[NSString stringWithFormat:@"%@...",[sb copy]];
        }
    }
    return str;  
}

//转换拼音
- (NSString *)transformToPinyin {
    if (self.length <= 0) {
        return self;
    }
    NSMutableString *tempString = [NSMutableString stringWithString:self];
    CFStringTransform((CFMutableStringRef)tempString, NULL, kCFStringTransformToLatin, false);
    tempString = (NSMutableString *)[tempString stringByFoldingWithOptions:NSDiacriticInsensitiveSearch locale:[NSLocale currentLocale]];
    return [tempString uppercaseString];
}

//计算字符串的长度转换成类似ASCII编码的长度
+ (int)convertToInt:(NSString*)strtemp{
    int strlength = 0;
    char* p = (char*)[strtemp cStringUsingEncoding:NSUnicodeStringEncoding];
    for (int i=0 ; i<[strtemp lengthOfBytesUsingEncoding:NSUnicodeStringEncoding] ;i++) {
        if (*p) {
            p++;
            strlength++;
        }
        else {
            p++;
        }
    }
    return strlength;
}

//将 &lt 等类似的字符串转化为HTML中的"<"等
+ (NSString *)htmlEntityDecode:(NSString *)string{
    string = [string stringByReplacingOccurrencesOfString:@"&quot;" withString:@"\""];
    string = [string stringByReplacingOccurrencesOfString:@"&apos;" withString:@"'"];
    string = [string stringByReplacingOccurrencesOfString:@"&lt;" withString:@"<"];
    string = [string stringByReplacingOccurrencesOfString:@"&gt;" withString:@">"];
    // Do this last so that, e.g. @"&amp;lt;" goes to @"&lt;" not @"<"
    string = [string stringByReplacingOccurrencesOfString:@"&amp;" withString:@"&"];
    return string;
}

//去除HTML文本标签（正则）
+ (NSString *)colatingHtml:(NSString *)html{
    NSString *_script = @"<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
    NSString *_style = @"<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
    NSString *_html = @"<[^>]+>"; // 定义HTML标签的正则表达式
    NSString *_space = @"\\s*|\t|\r|\n";//定义空格回车换行符
    
    html = [html stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceAndNewlineCharacterSet]];//去除字符串中所有得空格及控制字符
    NSRegularExpression *regEx_script = [NSRegularExpression regularExpressionWithPattern:_script options:0 error:nil];
    NSRegularExpression *regEx_style = [NSRegularExpression regularExpressionWithPattern:_style options:0 error:nil];
    NSRegularExpression *regEx_html = [NSRegularExpression regularExpressionWithPattern:_html options:0 error:nil];
    NSRegularExpression *regEx_space = [NSRegularExpression regularExpressionWithPattern:_space options:0 error:nil];
    
    html  = [regEx_script stringByReplacingMatchesInString:html options:0 range:NSMakeRange(0, html.length) withTemplate:@""];
    html  = [regEx_style stringByReplacingMatchesInString:html options:0 range:NSMakeRange(0, html.length) withTemplate:@""];
    html  = [regEx_html stringByReplacingMatchesInString:html options:0 range:NSMakeRange(0, html.length) withTemplate:@""];
    html  = [regEx_space stringByReplacingMatchesInString:html options:0 range:NSMakeRange(0, html.length) withTemplate:@""];
    
    return html;
}

+ (NSString *)filterEmoji:(NSString *)inputStr{
#if 0
    //过滤emoji表情字符
    __block NSString *filterEmojiStr  = [[NSString alloc] initWithString:inputStr];
    [filterEmojiStr enumerateSubstringsInRange:NSMakeRange(0, filterEmojiStr.length) options:NSStringEnumerationByComposedCharacterSequences usingBlock:^(NSString *substring, NSRange substringRange, NSRange enclosingRange, BOOL *stop) {
        //emoji length is 2  replace emoji with emptyString
        if (substring.length == 2) {
            filterEmojiStr = [filterEmojiStr stringByReplacingOccurrencesOfString:substring withString:@""];
        }
        
    }];
    
    return filterEmojiStr;
#endif
    
    return inputStr;
}

- (NSString *)verticalString{
    NSMutableString * str = [[NSMutableString alloc] initWithString:self];
    NSInteger count = str.length;
    for (int i = 1; i < count; i ++) {
        [str insertString:@"\n" atIndex:i*2 - 1];
    }
    return str;
}

//字符串脱敏处理     type类型  1：前部脱敏   2：中部脱敏   3：尾部脱敏
- (NSString *)desensitizationWithCount:(NSInteger)count type:(int)type{
    if (count <= 0) {
        return self;
    }
    int i;
    int sum = 0;
    for(i = 0;i < [self length];i++){
        unichar str = [self characterAtIndex:i];
        if(str < 256){
            sum += 1;
        }else {
            sum += 2;
        }
        if(sum > count){
            NSString *result = @"";
            NSString *str = @"";
            NSMutableString *tmp = [NSMutableString string];
            for (int j = 0; j < count; j++) {
                [tmp appendString:@"*"];
            }
            
            if (type == 1) {
                str = [self substringFromIndex:count];
                result = [NSString stringWithFormat:@"%@%@",tmp,str];
            }else if (type == 2){
                int index = (int)(self.length-count)/2;
                str = [self substringWithRange:NSMakeRange(0, index)];
                
                NSString *trail = [self substringFromIndex:index+count];
                
                result = [NSString stringWithFormat:@"%@%@%@",str,tmp,trail];
            }else if (type == 3){
                str = [self substringToIndex:self.length-count];
                result = [NSString stringWithFormat:@"%@%@",str,tmp];
            }
            
            return result;
        }
    }
    return self;
}

#pragma mark - **************** 一些常用的正则表达式
//去除空格
- (NSString *)trimWhitespace{
    NSMutableString *str = [self mutableCopy];
    CFStringTrimWhitespace((__bridge CFMutableStringRef)str);
    return str;
}
//是否为空字符串
- (BOOL)isEmpty{
    return [[self trimWhitespace] isEqualToString:@""];
}

//是否为空字符串 , (nil,空格,换行,制表符都为空字符串)
- (BOOL)isEmptyString{
    if (!self) {
        return true;
    } else {
        //A character set containing only the whitespace characters space (U+0020) and tab (U+0009) and the newline and nextline characters (U+000A–U+000D, U+0085).
        NSCharacterSet *set = [NSCharacterSet whitespaceAndNewlineCharacterSet];
        
        //Returns a new string made by removing from both ends of the receiver characters contained in a given character set.
        NSString *trimedString = [self stringByTrimmingCharactersInSet:set];
        
        if ([trimedString length] == 0) {
            return true;
        } else {
            return false;
        }
    }
}

// 是否包含数字
- (BOOL)isNumber{
    NSPredicate *predicate = [NSPredicate predicateWithFormat:@"SELF MATCHES %@", NUMBER_REGEX_NAME];
    return [predicate evaluateWithObject:self];
}

// 是否包含英文
- (BOOL)isEnglish{
    NSPredicate *predicate = [NSPredicate predicateWithFormat:@"SELF MATCHES %@", ENGLISH_REGEX_NAME];
    return [predicate evaluateWithObject:self];
}

// 是否包含汉字
+ (BOOL)isChinese:(NSString *)text{
    for(int i=0; i< [text length];i++){
        int a = [text characterAtIndex:i];
        if( a > 0x4e00 && a < 0x9fff){
            return YES;
        }
    }
    return NO;
}

//判断是否为整形
- (BOOL)isPureInt{
    NSScanner* scan = [NSScanner scannerWithString:self];
    int val;
    return[scan scanInt:&val] && [scan isAtEnd];
}

//判断是否为浮点形
- (BOOL)isPureFloat{
    NSScanner* scan = [NSScanner scannerWithString:self];
    float val;
    return[scan scanFloat:&val] && [scan isAtEnd];
}

// 验证网址正确性
- (BOOL)isInternetUrl{
    NSPredicate *predicate = [NSPredicate predicateWithFormat:@"SELF MATCHES %@", INTERNET_URL_REGEX_NAME];
    return [predicate evaluateWithObject:self];
}

//邮箱
+ (BOOL)validateEmail:(NSString *)email {
    NSString *emailRegex = @"[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";
    NSPredicate *emailTest = [NSPredicate predicateWithFormat:@"SELF MATCHES %@", emailRegex];
    return [emailTest evaluateWithObject:email];
}

//手机号码验证
+ (BOOL)validateMobile:(NSString *)mobile {
    //手机号以13,14,15(除去154),17,18开头，八个 \d 数字字符
//    NSString *phoneRegex = @"^((13[0-9])|(14[0-9])|(15[^4,\\D])|(17[0-9])|(18[0,0-9]))\\d{8}$";
    //以1开头的11为数字
    NSString *phoneRegex = @"^(1)\\d{10}$";
    NSPredicate *phoneTest = [NSPredicate predicateWithFormat:@"SELF MATCHES %@",phoneRegex];
    return [phoneTest evaluateWithObject:mobile];
    
    /*
    NSString * MOBILE = @"^1(3[0-9]|5[0-35-9]|8[0125-9])\\d{8}$";
//    中国移动：China Mobile
//    134[0-8],135,136,137,138,139,150,151,157,158,159,182,183,187,188
    NSString * CM = @"^1(34[0-8]|(3[5-9]|5[017-9]|8[2378])\\d)\\d{7}$";
    
//    中国联通：China Unicom
//    130,131,132,152,155,156,185,186
    NSString * CU = @"^1(3[0-2]|5[256]|8[56])\\d{8}$";
//    中国电信：China Telecom
//    133,1349,153,177,180,189
    NSString * CT = @"^1((33|53|77|8[09])[0-9]|349)\\d{7}$";
    
//    大陆地区固话及小灵通
//    区号：010,020,021,022,023,024,025,027,028,029
//    号码：七位或八位
//     NSString * PHS = @"^0(10|2[0-5789]|\\d{3})\\d{7,8}$";
    
    NSPredicate *regextestmobile = [NSPredicate predicateWithFormat:@"SELF MATCHES %@", MOBILE];
    NSPredicate *regextestcm = [NSPredicate predicateWithFormat:@"SELF MATCHES %@", CM];
    NSPredicate *regextestcu = [NSPredicate predicateWithFormat:@"SELF MATCHES %@", CU];
    NSPredicate *regextestct = [NSPredicate predicateWithFormat:@"SELF MATCHES %@", CT];
    BOOL res1 = [regextestmobile evaluateWithObject:mobile];
    BOOL res2 = [regextestcm evaluateWithObject:mobile];
    BOOL res3 = [regextestcu evaluateWithObject:mobile];
    BOOL res4 = [regextestct evaluateWithObject:mobile];
    
    if (res1 || res2 || res3 || res4){
        return YES;
    }else{
        return NO;
    }
    */
}

//车牌号验证
+ (BOOL)validateCarNo:(NSString *)carNo {
    NSString *carRegex = @"^[\u4e00-\u9fa5]{1}[a-zA-Z]{1}[a-zA-Z_0-9]{4}[a-zA-Z_0-9_\u4e00-\u9fa5]$";
    NSPredicate *carTest = [NSPredicate predicateWithFormat:@"SELF MATCHES %@",carRegex];
    return [carTest evaluateWithObject:carNo];
}

//车型
+ (BOOL)validateCarType:(NSString *)CarType {
    NSString *CarTypeRegex = @"^[\u4E00-\u9FFF]+$";
    NSPredicate *carTest = [NSPredicate predicateWithFormat:@"SELF MATCHES %@",CarTypeRegex];
    return [carTest evaluateWithObject:CarType];
}

//用户名 (6-20位 英文字母,数字)
+ (BOOL)validateUserName:(NSString *)name {
    NSString *userNameRegex = @"^[A-Za-z0-9]{6,20}+$";
    NSPredicate *userNamePredicate = [NSPredicate predicateWithFormat:@"SELF MATCHES %@",userNameRegex];
    BOOL B = [userNamePredicate evaluateWithObject:name];
    return B;
}

// 密码
+ (BOOL)validatePassword:(NSString *)passWord {
//    NSString *passWordRegex = @"^[a-zA-Z0-9]{6,16}+$";
    NSString *passWordRegex = @"^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,18}$";
    NSPredicate *passWordPredicate = [NSPredicate predicateWithFormat:@"SELF MATCHES %@",passWordRegex];
    return [passWordPredicate evaluateWithObject:passWord];
}

//验证码
+ (BOOL)validateRandom:(NSString *)random{
    NSString *randomRegex = @"^[0-9]{6}$";
    NSPredicate *randomPredicate = [NSPredicate predicateWithFormat:@"SELF MATCHES %@",randomRegex];
    return [randomPredicate evaluateWithObject:random];
}

//身份证号
+ (BOOL)validateIdentityCard: (NSString *)identityCard {
    BOOL flag;
    if (identityCard.length <= 0) {
            flag = NO;
            return flag;
    }
    NSString *regex2 = @"^(\\d{14}|\\d{17})(\\d|[xX])$";
    NSPredicate *identityCardPredicate = [NSPredicate predicateWithFormat:@"SELF MATCHES %@",regex2];
    return [identityCardPredicate evaluateWithObject:identityCard];
}

//验证车辆识别代码
+ (BOOL)validateCarIdentCode:(NSString *)code{
    //^ 匹配一行的开头位置
//    (?![0-9]+$) 预测该位置后面不全是数字
//    (?![a-zA-Z]+$) 预测该位置后面不全是字母
//    [0-9A-Za-z] {8,16} 由8-16位数字或这字母组成
//    $ 匹配行结尾位置
//    {n}    匹配重复n次
//    {n,}   匹配重复n次或n次以上
//    {n,m} 匹配重复最少n次最多m次
    NSString *passWordRegex = @"^[0-9A-Za-z]{6,17}$";
    
    NSPredicate *passWordPredicate = [NSPredicate predicateWithFormat:@"SELF MATCHES %@",passWordRegex];
    
    return [passWordPredicate evaluateWithObject:code];
}

//验证发动机号码
+ (BOOL)validateEngineNumber:(NSString *)code{
    NSString *passWordRegex = @"^[0-9A-Za-z]{6,14}$";
    NSPredicate *passWordPredicate = [NSPredicate predicateWithFormat:@"SELF MATCHES %@",passWordRegex];
    return [passWordPredicate evaluateWithObject:code];
}

//用户姓名  (2-20位的中/英文姓名)
+ (BOOL)validateName:(NSString *)name {
    NSString *nicknameRegex = @"^[\u4e00-\u9fa5a-zA-Z]{1,20}$";
    NSPredicate *passWordPredicate = [NSPredicate predicateWithFormat:@"SELF MATCHES %@",nicknameRegex];
    return [passWordPredicate evaluateWithObject:name];
}

//验证详细地址 (不超过50个中/英文字符)
+ (BOOL)validateAddress:(NSString *)address{
    NSString *nicknameRegex = @"^[\u4e00-\u9fa5a-zA-Z]{3,50}$";
    NSPredicate *passWordPredicate = [NSPredicate predicateWithFormat:@"SELF MATCHES %@",nicknameRegex];
    return [passWordPredicate evaluateWithObject:address];
}

//验证邮编
+ (BOOL)validatePostcode:(NSString *)postCode{
    NSString *nicknameRegex = @"^[0-9]{6}$";
    NSPredicate *passWordPredicate = [NSPredicate predicateWithFormat:@"SELF MATCHES %@",nicknameRegex];
    return [passWordPredicate evaluateWithObject:postCode];
}

//验证省市名  (2-10位中文字符)
+ (BOOL)validateProvinceCity:(NSString *)city{
    NSString *nicknameRegex = @"^[\u4e00-\u9fa5]{2,10}$";
    NSPredicate *passWordPredicate = [NSPredicate predicateWithFormat:@"SELF MATCHES %@",nicknameRegex];
    return [passWordPredicate evaluateWithObject:city];
}

//验证个人简介 (不超过50个字符)
+ (BOOL)validateIntrobuction:(NSString *)address length:(int)len{
    if ([address trimWhitespace].length <= 0) {
        return NO;
    }
    if (address.length <= len) {
        return YES;
    }
    return NO;
}

//验证微信号码
+ (BOOL)validateWXUsername:(NSString *)text{
    NSString *wxUsername = @"^[0-9A-Za-z]{1}([0-9A-Za-z]|[-_]){5,19}$";
    NSPredicate *wxPredicate = [NSPredicate predicateWithFormat:@"SELF MATCHES %@",wxUsername];
    return [wxPredicate evaluateWithObject:text];
}

//验证座机号码
+ (BOOL)validateFixedTelephone:(NSString *)text{
    NSString *fixedTelephone = @"^[0-9-]{8,18}$";
    NSPredicate *predicate = [NSPredicate predicateWithFormat:@"SELF MATCHES %@",fixedTelephone];
    return [predicate evaluateWithObject:text];
}


//邮箱
- (BOOL)isValidateEmail{
    NSPredicate *regex = [NSPredicate predicateWithFormat:@"SELF MATCHES %@", EMAIL_REGEX_NAME];
    return [regex evaluateWithObject:self];
}

//验证是否包含表情
- (BOOL)containsEmoji{
    if (!self || self.length <= 0) {
        return NO;
    }
    __block BOOL returnValue = NO;
    [self enumerateSubstringsInRange:NSMakeRange(0, [self length]) options:NSStringEnumerationByComposedCharacterSequences usingBlock:
     ^(NSString *substring, NSRange substringRange, NSRange enclosingRange, BOOL *stop) {
         
         const unichar hs = [substring characterAtIndex:0];
         // surrogate pair
         if (0xd800 <= hs && hs <= 0xdbff) {
             if (substring.length > 1) {
                 const unichar ls = [substring characterAtIndex:1];
                 const int uc = ((hs - 0xd800) * 0x400) + (ls - 0xdc00) + 0x10000;
                 if (0x1d000 <= uc && uc <= 0x1f77f) {
                     returnValue = YES;
                 }
             }
         } else if (substring.length > 1) {
             const unichar ls = [substring characterAtIndex:1];
             if (ls == 0x20e3) {
                 returnValue = YES;
             }
             
         } else {
             // non surrogate
             if (0x2100 <= hs && hs <= 0x27ff) {
                 returnValue = YES;
             } else if (0x2B05 <= hs && hs <= 0x2b07) {
                 returnValue = YES;
             } else if (0x2934 <= hs && hs <= 0x2935) {
                 returnValue = YES;
             } else if (0x3297 <= hs && hs <= 0x3299) {
                 returnValue = YES;
             } else if (hs == 0xa9 || hs == 0xae || hs == 0x303d || hs == 0x3030 || hs == 0x2b55 || hs == 0x2b1c || hs == 0x2b1b || hs == 0x2b50) {
                 returnValue = YES;
             }
         }
     }];
    
    return returnValue;
}

//判断字符串中是否存在汉字
+(BOOL)IsChinese:(NSString *)str {
    for(int i=0; i< [str length];i++){
        int a = [str characterAtIndex:i];
        if( a > 0x4e00 && a < 0x9fff)
        {
            return YES;
        }
    }
    return NO;
}

- (CGSize)czh_sizeWithFont:(UIFont *)font maxH:(CGFloat)maxH
{
    NSMutableDictionary *attrs = [NSMutableDictionary dictionary];
    attrs[NSFontAttributeName] = font;
    CGSize maxSize = CGSizeMake(MAXFLOAT, maxH);
    
    
    return [self boundingRectWithSize:maxSize options:NSStringDrawingUsesLineFragmentOrigin attributes:attrs context:nil].size;
}

- (CGSize)czh_sizeWithFont:(UIFont *)font maxW:(CGFloat)maxW
{
    
    NSMutableDictionary *attrs = [NSMutableDictionary dictionary];
    attrs[NSFontAttributeName] = font;
    CGSize maxSize = CGSizeMake(maxW, MAXFLOAT);
    
    
    return [self boundingRectWithSize:maxSize options:NSStringDrawingUsesLineFragmentOrigin attributes:attrs context:nil].size;
}

- (CGSize)czh_sizeWithFont:(UIFont *)font
{
    return [self czh_sizeWithFont:font maxW:MAXFLOAT];
}



@end
