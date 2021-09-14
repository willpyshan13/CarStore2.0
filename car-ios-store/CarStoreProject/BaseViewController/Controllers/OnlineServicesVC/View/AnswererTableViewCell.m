//
//  AnswererTableViewCell.m
//  CarOwnerProject
//
//  Created by apple on 2021/1/26.
//

#import "AnswererTableViewCell.h"

@implementation AnswererTableViewCell

- (void)awakeFromNib {
    [super awakeFromNib];
    // Initialization code
    
    self.headImg.layer.cornerRadius = 34.0f;
    
    
    self.consultBtn.layer.cornerRadius = 2.0f;

    
    NSString *moneyStr=[NSString stringWithFormat:@"￥%@",@"68"];
    NSRange range = [moneyStr rangeOfString:@"￥"];
    NSMutableAttributedString *str = [[NSMutableAttributedString alloc]initWithString:moneyStr];
    //设置：在传过来的的内容显示成红色
    [str addAttribute:NSFontAttributeName value:[UIFont systemFontOfSize:10] range:range];
    self.priceLab.attributedText = str;
    
    self.consultBtn.userInteractionEnabled = NO;
    
    
}


-(void)setModel:(DazhuSureModel *)model
{
    [self testString];
    if (model) {
        
        [self.headImg sd_setImageWithURL:[NSURL URLWithString:model.photoImgUrl]placeholderImage:kImageNamed(@"zhanweifu")];
        
        self.nameLab.text = [NSString stringWithFormat:@"%@",SafeValue([NSString stringWithFormat:@"%@",model.name])];
        
        NSString *scoreStr =SafeValue([NSString stringWithFormat:@"%@",model.score]);

        if ([scoreStr isEqualToString:@""]) {
            [self.startNumLab setTitle:@" 0" forState:UIControlStateNormal];
        }else
        {
            [self.startNumLab setTitle:[NSString stringWithFormat:@" %@",SafeValue([NSString stringWithFormat:@"%@",model.score])] forState:UIControlStateNormal];
        }


        NSString *technologyType=[NSString stringWithFormat:@"%@",SafeValue([NSString stringWithFormat:@"%@",model.technologyType])];
        if (![technologyType isEqualToString:@""]) {
            self.zhichengLab.layer.cornerRadius = 2.0f;
            self.zhichengLab.layer.borderWidth = 1.0f;
            self.zhichengLab.layer.borderColor = Number1691E3Color.CGColor;
            self.zhichengLab.text = [NSString stringWithFormat:@"%@  ",technologyType];
            
        }else
        {
            self.zhichengLab.text = @"";
        }
        
        NSString *zixunStr =SafeValue([NSString stringWithFormat:@"%@",model.qaCount]);
        if ([zixunStr isEqualToString:@""]) {
            self.xixunNumLab.text = @"0次咨询";
        }else
        {
            self.xixunNumLab.text = [NSString stringWithFormat:@"%@次咨询",zixunStr];
        }
        

        self.signLab.text = @"";
        
        NSString *answerAmt =SafeValue([NSString stringWithFormat:@"%@",model.answerAmt]);

        if (![answerAmt isEqualToString:@"0"]) {
            NSString *moneyStr=[NSString stringWithFormat:@"￥%@",SafeValue([NSString stringWithFormat:@"%@",model.answerAmt])];
            NSRange range = [moneyStr rangeOfString:@"￥"];
            NSMutableAttributedString *str = [[NSMutableAttributedString alloc]initWithString:moneyStr];
            //设置：在传过来的的内容显示成红色
            [str addAttribute:NSFontAttributeName value:[UIFont systemFontOfSize:10] range:range];
            self.priceLab.attributedText = str;
        }else
        {
            self.priceLab.text =@"";
        }
       
        
        
        NSMutableArray *nameArray =[[NSMutableArray alloc]init];
        NSArray *brandResList = model.brandResList;
        if (brandResList.count >0 && [brandResList isKindOfClass:[NSArray class]]) {
            for (NSDictionary *brandDic in brandResList) {
                NSString *brandName=brandDic[@"brandName"];
                [nameArray addObject:brandName];
            }
            NSString *signStr = [nameArray componentsJoinedByString:@","];
            self.signLab.text =[NSString stringWithFormat:@"擅长：%@",signStr];
        }else
        {
            self.signLab.text=@"";
        }
        
        
        if ([SafeValue([NSString stringWithFormat:@"%@",model.cybAuth])isEqualToString:@"0"]) {
            self.typeLab.text = @"普通技师";
        }else
        {
            self.typeLab.text = @"专家技师";
        }
        
    }
    
}
-(void)testString
{
    self.nameLab.text=@"";
    [self.startNumLab setTitle:@"" forState:UIControlStateNormal];
    self.priceLab.text=@"";
    self.zhichengLab.text=@"";
    self.xixunNumLab.text=@"";
    self.signLab.text=@"";

}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
