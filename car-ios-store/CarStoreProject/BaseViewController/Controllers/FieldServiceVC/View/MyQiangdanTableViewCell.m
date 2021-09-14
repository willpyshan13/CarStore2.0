//
//  MyQiangdanTableViewCell.m
//  CarStoreProject
//
//  Created by apple on 2021/3/9.
//

#import "MyQiangdanTableViewCell.h"

@implementation MyQiangdanTableViewCell

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
        
        //0 待支付 1 已支付 2: 已取消 3:退款中 4:退款成功 5:退款失败 6：已完成 ,
        if ([model.orderSts isEqualToString:@"0"]) {
            self.stateLab.text=@"待支付";
        }
        else if ([model.orderSts isEqualToString:@"1"])
        {
            self.stateLab.text=@"已支付";
        }
        else if ([model.orderSts isEqualToString:@"2"])
        {
            self.stateLab.text=@"已取消";
        }
        else if ([model.orderSts isEqualToString:@"3"])
        {
            self.stateLab.text=@"退款中";
        }
        else if ([model.orderSts isEqualToString:@"4"])
        {
            self.stateLab.text=@"退款成功";
        }
        else if ([model.orderSts isEqualToString:@"5"])
        {
            self.stateLab.text=@"退款失败";
        }
        else if ([model.orderSts isEqualToString:@"6"])
        {
            self.stateLab.text=@"已完成";
        }
    
    }
}
-(void)testString
{
    self.pinpaiLab.text = @"";
    self.chexingLab.text=@"";
    self.miaoshuLab.text=@"";
    self.priceLab.text=@"";
    self.stateLab.text=@"";
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
