//
//  OnLineCaseTableViewCell.m
//  CarStoreProject
//
//  Created by apple on 2021/1/27.
//

#import "OnLineCaseTableViewCell.h"

@implementation OnLineCaseTableViewCell

- (void)awakeFromNib {
    [super awakeFromNib];
    // Initialization code
    NSString *moneyStr=[NSString stringWithFormat:@"￥%@",@"88.00"];
    NSRange range = [moneyStr rangeOfString:@"￥"];
    NSMutableAttributedString *str = [[NSMutableAttributedString alloc]initWithString:moneyStr];
    //设置：在传过来的的内容显示成红色
    [str addAttribute:NSFontAttributeName value:[UIFont systemFontOfSize:16] range:range];
    self.priceLab.attributedText = str;
    
    self.bgView.layer.cornerRadius = 8.0f;
}
-(void)setModel:(OnlineCaseModel *)model
{
    [self testString];
    if (model) {
        self.nameLab.text = [NSString stringWithFormat:@"%@",SafeValue([NSString stringWithFormat:@"%@",model.title])];
        
        NSString *amtStr =[NSString stringWithFormat:@"%@",SafeValue([NSString stringWithFormat:@"%@",model.amt])];
        
        DLog(@"amtStr====%@",amtStr);
        
        if ([amtStr isEqualToString:@""]) {
            self.priceLab.text =@"0";
        }else
        {
            self.priceLab.text =amtStr;
        }
        
        NSString *numStr =[NSString stringWithFormat:@"%@",SafeValue([NSString stringWithFormat:@"%@",model.num])];

        if ([numStr isEqualToString:@""]) {
            self.salesLab.text = @"0";
        }
        else
        {
            self.salesLab.text =numStr;
            
        }

    }
    
}
-(void)testString
{
    self.salesLab.text=@"";
    self.nameLab.text=@"";
    self.priceLab.text=@"";
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
