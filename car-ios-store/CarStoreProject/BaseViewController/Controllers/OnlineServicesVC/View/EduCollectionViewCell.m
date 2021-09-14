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

-(void)setModel:(CourseParentModel *)model
{
    [self textString];
    if (model) {
        [self.carImg sd_setImageWithURL:[NSURL URLWithString:model.courseCover]placeholderImage:kImageNamed(@"zhanweifu")];
   
        self.nameLab.text = SafeValue([NSString stringWithFormat:@"%@",model.courseTitle]);

    }
}
-(void)textString
{
    self.nameLab.text =@"";
    self.carImg.image = kImageNamed(@"");
}

@end
