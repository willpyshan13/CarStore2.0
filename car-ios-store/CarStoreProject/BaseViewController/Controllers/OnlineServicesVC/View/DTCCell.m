//
//  DTCCell.m
//  CarStoreProject
//
//  Created by Qin on 2021/2/15.
//

#import "DTCCell.h"

@implementation DTCCell

- (void)awakeFromNib {
    [super awakeFromNib];
    // Initialization code
}

-(void)setModel:(DTCListModel *)model
{
    [self testString];
    if (model)
    {
        self.codeLabel.text = SafeValue([NSString stringWithFormat:@"%@",model.dtcCode]);
        self.desLabel.text = SafeValue([NSString stringWithFormat:@"%@",model.dtcDefinition]);
        self.pinpaiLab.text = SafeValue([NSString stringWithFormat:@"%@",model.configName]);

    }
}

-(void)testString
{
    self.codeLabel.text = @"";
    self.desLabel.text= @"";
    self.pinpaiLab.text = @"";
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
