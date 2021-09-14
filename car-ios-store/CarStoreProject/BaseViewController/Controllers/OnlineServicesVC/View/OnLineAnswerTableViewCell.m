//
//  OnLineAnswerTableViewCell.m
//  CarStoreProject
//
//  Created by apple on 2021/1/27.
//

#import "OnLineAnswerTableViewCell.h"

@implementation OnLineAnswerTableViewCell

- (void)awakeFromNib {
    [super awakeFromNib];
    // Initialization code
    
    NSString *moneyStr=[NSString stringWithFormat:@"￥%@",@"88.00"];
    NSRange range = [moneyStr rangeOfString:@"￥"];
    NSMutableAttributedString *str = [[NSMutableAttributedString alloc]initWithString:moneyStr];
    //设置：在传过来的的内容显示成红色
    [str addAttribute:NSFontAttributeName value:[UIFont systemFontOfSize:12] range:range];
    self.priceLab.attributedText = str;
    
    self.bgView.layer.cornerRadius = 8.0f;
    
    self.answerBtn.layer.cornerRadius = 2.0;
}
-(void)setModel:(OnlineAnswerModel *)model
{
    [self testString];
    if (model) {
        self.titleLab.text = SafeValue([NSString stringWithFormat:@"%@",model.title]);
        self.detailLab.text = SafeValue([NSString stringWithFormat:@"%@",model.consultDesc]);

        NSString *moneyStr=[NSString stringWithFormat:@"￥%@",SafeValue([NSString stringWithFormat:@"%@",model.consultAmt])];
        NSRange range = [moneyStr rangeOfString:@"￥"];
        NSMutableAttributedString *str = [[NSMutableAttributedString alloc]initWithString:moneyStr];
        //设置：在传过来的的内容显示成红色
        [str addAttribute:NSFontAttributeName value:[UIFont systemFontOfSize:12] range:range];
        self.priceLab.attributedText = str;
    }
}
-(void)testString
{
    self.titleLab.text = @"";
    self.detailLab.text = @"";
    self.priceLab.text = @"";

}


- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
