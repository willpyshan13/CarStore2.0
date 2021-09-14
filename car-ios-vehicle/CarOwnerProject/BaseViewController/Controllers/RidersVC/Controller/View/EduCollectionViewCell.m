//
//  EduCollectionViewCell.m
//  CarStoreProject
//
//  Created by Qin on 2021/2/15.
//

#import "EduCollectionViewCell.h"

@implementation EduCollectionViewCell

- (void)awakeFromNib {
    [super awakeFromNib];
    // Initialization code
}

-(void)setModel:(MaintainInfoModel *)model
{
    [self textString];
    if (model) {
        [self.carImg sd_setImageWithURL:[NSURL URLWithString:model.maintainCover]placeholderImage:kImageNamed(@"zhanweifu")];
   
        self.nameLab.text = SafeValue([NSString stringWithFormat:@"%@",model.maintainTitle]);

    }
}
-(void)textString
{
    self.nameLab.text =@"";
    self.carImg.image = kImageNamed(@"");
}

@end
