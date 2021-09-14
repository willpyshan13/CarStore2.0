//
//  WxGoodsCollectionViewCell.m
//  CarOwnerProject
//
//  Created by apple on 2021/3/19.
//

#import "WxGoodsCollectionViewCell.h"

@implementation WxGoodsCollectionViewCell

- (void)awakeFromNib {
    [super awakeFromNib];
    // Initialization code
}
-(void)setModel:(StoreQueryListWXModel *)model
{
    if (model)
    {
        self.storeNameLab.text = SafeValue([NSString stringWithFormat:@"%@",model.storeName]);
        NSString *configNameListStr =[model.configNameList componentsJoinedByString:@","];
        self.brandLab.text =SafeValue([NSString stringWithFormat:@"%@",configNameListStr]);
        
        self.desLab.text =SafeValue([NSString stringWithFormat:@"%@",model.parentTypeName]);
        
        NSArray *imageArr = model.imgList;
        NSDictionary *imgDict = imageArr[0];
        [self.goodImg sd_setImageWithURL:[NSURL URLWithString:imgDict[@"imageUrl"]]placeholderImage:kImageNamed(@"zhanweifu")];        
    }
}

-(void)testString
{
    self.goodImg.image = kImageNamed(@"");
    self.storeNameLab.text = @"";
    self.desLab.text = @"";
    self.brandLab.text = @"";
}
@end
