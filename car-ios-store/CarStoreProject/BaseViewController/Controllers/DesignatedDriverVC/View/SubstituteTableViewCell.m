//
//  SubstituteTableViewCell.m
//  CarStoreProject
//
//  Created by apple on 2021/1/26.
//

#import "SubstituteTableViewCell.h"

@implementation SubstituteTableViewCell

- (void)awakeFromNib {
    [super awakeFromNib];
    // Initialization code
    NSString *moneyStr=[NSString stringWithFormat:@"￥%@",@"88"];
    NSRange range = [moneyStr rangeOfString:@"￥"];
    NSMutableAttributedString *str = [[NSMutableAttributedString alloc]initWithString:moneyStr];
    //设置：在传过来的的内容显示成红色
    [str addAttribute:NSFontAttributeName value:[UIFont systemFontOfSize:16] range:range];
    self.priceLab.attributedText = str;
    
    self.wordSnatchBtn.layer.cornerRadius = 8.0f;
    
    self.bgView.layer.cornerRadius = 3.0f;
    
    self.wordSnatchBtn.userInteractionEnabled = NO;
}


- (void)configSetTableViewCellIndex:(NSIndexPath *)indexPath
{
    switch (indexPath.row) {
        case 0:
        {
            self.priceLab.text = @"￥238.00";
            self.startTimeLab.text = @"及时";
            self.startAddressLab.text = @"豫园";
            self.bournLab.text = @"上海虹桥火车站";
            self.wholeCourseLab.text = @"68.0km";
            self.distanceLab.text = @"4.0km";

            
        }
            break;
        case 1:
        {
            self.priceLab.text = @"￥297.50";
            self.startTimeLab.text = @"及时";
            self.startAddressLab.text = @"南京东路";
            self.bournLab.text = @"东方明珠";
            self.wholeCourseLab.text = @"85.0km";
            self.distanceLab.text = @"5.0km";
        }
            break;
        case 2:
        {
            self.priceLab.text = @"￥357.00";
            self.startTimeLab.text = @"及时";
            self.startAddressLab.text = @"东方明珠";
            self.bournLab.text = @"南京东路";
            self.wholeCourseLab.text = @"102.0km";
            self.distanceLab.text = @"6.0km";
        }
            break;
        case 3:
        {
            self.priceLab.text = @"￥416.50";
            self.startTimeLab.text = @"及时";
            self.startAddressLab.text = @"张家高科";
            self.bournLab.text = @"豫园";
            self.wholeCourseLab.text = @"119.0km";
            self.distanceLab.text = @"7.0km";
        }
            break;
            
        default:
            break;
    }
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
