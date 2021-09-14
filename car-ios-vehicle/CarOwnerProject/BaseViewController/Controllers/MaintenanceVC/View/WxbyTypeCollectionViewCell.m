//
//  WxbyTypeCollectionViewCell.m
//  CarOwnerProject
//
//  Created by apple on 2021/3/19.
//

#import "WxbyTypeCollectionViewCell.h"

@implementation WxbyTypeCollectionViewCell

- (void)awakeFromNib {
    [super awakeFromNib];
    // Initialization code
}

-(void)setModel:(QueryTreeListModel *)model
{
    if (model)
    {
        
        self.nameLab.text = SafeValue([NSString stringWithFormat:@"%@",model.groupName]);
    }
}
-(void)testString
{
    self.typeImg.image = kImageNamed(@"");
    self.nameLab.text= @"";
}

@end
