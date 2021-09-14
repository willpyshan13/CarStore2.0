//
//  XCXDTableViewCell.m
//  CarStoreProject
//
//  Created by apple on 2021/3/9.
//

#import "XCXDTableViewCell.h"

@implementation XCXDTableViewCell

- (void)awakeFromNib {
    [super awakeFromNib];
    // Initialization code
    self.bgView.layer.cornerRadius=8.0f;
}

-(void)setModel:(FieldServiceDesModel *)model
{
    [self testString];
    if (model) {
        self.pinpaiLab.text = SafeValue([NSString stringWithFormat:@"%@",model.brandName]);
        self.chexingLab.text = SafeValue([NSString stringWithFormat:@"%@",model.carModelName]);
        self.miaoshuLab.text = SafeValue([NSString stringWithFormat:@"%@",model.faultDesc]);
        self.priceLab.text = SafeValue([NSString stringWithFormat:@"%@",model.totalAmount]);
        self.juliLab.text = [NSString stringWithFormat:@"距离%@km",SafeValue([NSString stringWithFormat:@"%@",model.distance])];
    }
}
-(void)testString
{
    self.pinpaiLab.text = @"";
    self.chexingLab.text=@"";
    self.miaoshuLab.text=@"";
    self.priceLab.text=@"";
    self.juliLab.text=@"";
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
