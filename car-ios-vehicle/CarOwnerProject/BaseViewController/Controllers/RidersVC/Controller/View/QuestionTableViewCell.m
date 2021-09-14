//
//  QuestionTableViewCell.m
//  CarOwnerProject
//
//  Created by apple on 2021/1/26.
//

#import "QuestionTableViewCell.h"

@implementation QuestionTableViewCell

- (void)awakeFromNib {
    [super awakeFromNib];
    // Initialization code
    self.headImg.layer.cornerRadius = 24.0f;
    self.ptBtn.layer.cornerRadius = 2.0f;
    NSString *moneyStr=[NSString stringWithFormat:@"￥%@",@"1"];
    NSRange range = [moneyStr rangeOfString:@"￥"];
    NSMutableAttributedString *str = [[NSMutableAttributedString alloc]initWithString:moneyStr];
    //设置：在传过来的的内容显示成红色
    [str addAttribute:NSFontAttributeName value:[UIFont systemFontOfSize:10] range:range];
    self.priceLab.attributedText = str;
}

-(void)setModel:(DazhuListModel *)model
{
    [self testString];
    if (model) {
        
        [self.headImg sd_setImageWithURL:[NSURL URLWithString:model.technicianImgUrl]placeholderImage:kImageNamed(@"zhanweifu")];
        
        self.titLab.text = [NSString stringWithFormat:@"%@",SafeValue([NSString stringWithFormat:@"%@",model.title])];
        self.questLab.text = [NSString stringWithFormat:@"%@",SafeValue([NSString stringWithFormat:@"%@",model.consultDesc])];
        self.timeLab.text = [NSString stringWithFormat:@"%@",SafeValue([NSString stringWithFormat:@"%@",model.createdTime])];

        self.answerLab.text = [NSString stringWithFormat:@"%@",SafeValue([NSString stringWithFormat:@"%@",model.answerDesc])];
        
        NSString *consultAmt =SafeValue([NSString stringWithFormat:@"%@",model.consultAmt]);
        if (![consultAmt isEqualToString:@"0"])
        {
            NSString *moneyStr=[NSString stringWithFormat:@"￥%@",SafeValue([NSString stringWithFormat:@"%@",model.consultAmt])];
            NSRange range = [moneyStr rangeOfString:@"￥"];
            NSMutableAttributedString *str = [[NSMutableAttributedString alloc]initWithString:moneyStr];
            //设置：在传过来的的内容显示成红色
            [str addAttribute:NSFontAttributeName value:[UIFont systemFontOfSize:10] range:range];
            self.priceLab.attributedText = str;
        }else
        {
            self.priceLab.text= @"";
        }
        
        
        
    }
    
}
- (IBAction)pangtingBtnClick:(id)sender
{
    UIButton *btn = sender;
    if (self.pangtingBlock) {
        self.pangtingBlock(sender);
    }
}
-(void)testString
{
    self.titLab.text=@"";
    self.questLab.text=@"";
    self.timeLab.text=@"";
    self.answerLab.text=@"";
    self.priceLab.text=@"";
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
