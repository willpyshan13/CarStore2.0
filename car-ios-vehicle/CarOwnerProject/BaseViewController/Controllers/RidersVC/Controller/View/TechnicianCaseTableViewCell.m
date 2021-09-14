//
//  TechnicianCaseTableViewCell.m
//  CarOwnerProject
//
//  Created by apple on 2021/1/26.
//

#import "TechnicianCaseTableViewCell.h"

@implementation TechnicianCaseTableViewCell

- (void)awakeFromNib {
    [super awakeFromNib];
    // Initialization code
    
    NSString *moneyStr=[NSString stringWithFormat:@"价值：¥%@",@"299"];
    NSRange colorRange = [moneyStr rangeOfString:[NSString stringWithFormat:@"¥%@",@"299"]];
    NSRange fontRange=[moneyStr rangeOfString:@"299"];
    NSMutableAttributedString *str = [[NSMutableAttributedString alloc]initWithString:moneyStr];
    //设置：在传过来的的内容显示成红色
    [str addAttribute:NSForegroundColorAttributeName value:NumberFF3838Color range:colorRange];
    [str addAttribute:NSFontAttributeName value:[UIFont systemFontOfSize:16] range:fontRange];
    self.priceLab.attributedText = str;
    
    self.buyBtn.layer.cornerRadius = 2.0f;
    
    self.buyBtn.userInteractionEnabled = NO;
}

-(void)setModel:(JishiCaseModel *)model
{
    [self testString];
    if (model) {
        self.nameLab.text = [NSString stringWithFormat:@"%@",SafeValue([NSString stringWithFormat:@"%@",model.technicianName])];
        
        NSString *amtS =SafeValue([NSString stringWithFormat:@"%@",model.amt]);

        if (![amtS isEqualToString:@"0"]) {
            NSString *priceStr=SafeValue([NSString stringWithFormat:@"%@",model.amt]);
            NSString *moneyStr=[NSString stringWithFormat:@"价值：¥%@",priceStr];
            NSRange colorRange = [moneyStr rangeOfString:[NSString stringWithFormat:@"¥%@",priceStr]];
            NSRange fontRange=[moneyStr rangeOfString:priceStr];
            NSMutableAttributedString *str = [[NSMutableAttributedString alloc]initWithString:moneyStr];
            //设置：在传过来的的内容显示成红色
            [str addAttribute:NSForegroundColorAttributeName value:NumberFF3838Color range:colorRange];
            [str addAttribute:NSFontAttributeName value:[UIFont systemFontOfSize:16] range:fontRange];
            self.priceLab.attributedText = str;
            self.buyBtn.hidden = NO;
        }
        else
        {
            self.priceLab.text=@"";
            self.buyBtn.hidden = YES;
        }
        
        self.titLab.text = [NSString stringWithFormat:@"%@",SafeValue([NSString stringWithFormat:@"%@",model.title])];
        
        self.glLab.text = [NSString stringWithFormat:@"工龄：%@",[NSString stringWithFormat:@"%@",SafeValue([NSString stringWithFormat:@"%@",model.workingYear])]];
        
        [self.headImg sd_setImageWithURL:[NSURL URLWithString:model.imgUrl]
                        placeholderImage:kImageNamed(@"zhanweifu")];
//        if ([SafeValue([NSString stringWithFormat:@"%@",model.imgUrl])isEqualToString:@""]) {
//            self.headImg.backgroundColor = NumberF4F4F4Color;
//        }else
//        {
//
//        }
    }
    
}
-(void)testString
{
    self.titLab.text=@"";
    self.nameLab.text=@"";
    self.priceLab.text=@"";
    self.glLab.text=@"";
}



- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
