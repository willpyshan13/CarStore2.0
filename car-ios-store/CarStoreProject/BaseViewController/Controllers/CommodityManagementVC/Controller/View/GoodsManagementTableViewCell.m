//
//  GoodsManagementTableViewCell.m
//  CarStoreProject
//
//  Created by apple on 2021/1/27.
//

#import "GoodsManagementTableViewCell.h"

@implementation GoodsManagementTableViewCell

- (void)awakeFromNib {
    [super awakeFromNib];
    // Initialization code
    
    NSString *moneyStr=[NSString stringWithFormat:@"￥%@",@"88.00"];
    NSRange range = [moneyStr rangeOfString:@"￥"];
    NSMutableAttributedString *str = [[NSMutableAttributedString alloc]initWithString:moneyStr];
    //设置：在传过来的的内容显示成红色
    [str addAttribute:NSFontAttributeName value:[UIFont systemFontOfSize:12] range:range];
    self.priceLab.attributedText = str;
    self.backgroundColor =[UIColor whiteColor];
}

-(void)setModel:(UpGoodsListModel *)model
{
    [self testString];
    if (model) {
        self.storeNameLab.text = SafeValue([NSString stringWithFormat:@"%@",model.goodsName]);
        self.startNumLab.text = SafeValue([NSString stringWithFormat:@"%@",model.score]);
        self.comNumLab.text = [NSString stringWithFormat:@"%@条",SafeValue([NSString stringWithFormat:@"%@",model.commentNum])];
        
        NSString *salesNum=SafeValue([NSString stringWithFormat:@"%@",model.salesNum]);
        NSString *surplusNum=SafeValue([NSString stringWithFormat:@"%@",model.surplusNum]);

        self.saleNumLab.text = [NSString stringWithFormat:@"销量：%@ 库存：%@",salesNum,surplusNum];
        
        
        
        
        NSArray *actAmtArr = model.detailList;
        if (actAmtArr.count>0 &&[actAmtArr isKindOfClass:[NSArray class]])
        {
            for (int i=0; i<actAmtArr.count; i++) {
                NSDictionary *imgDict = [actAmtArr firstObject];
                NSString *imgPath = imgDict[@"actAmt"];
                
                NSString *moneyStr=[NSString stringWithFormat:@"￥%@",SafeValue([NSString stringWithFormat:@"%@",imgPath])];
                NSRange range = [moneyStr rangeOfString:@"￥"];
                NSMutableAttributedString *str = [[NSMutableAttributedString alloc]initWithString:moneyStr];
                //设置：在传过来的的内容显示成红色
                [str addAttribute:NSFontAttributeName value:[UIFont systemFontOfSize:12] range:range];
                self.priceLab.attributedText = str;
            }
        }else
        {
            self.priceLab.text=@"";
        }

        
        
        NSArray *imageArr = model.imgList;
        if (imageArr.count>0 &&[imageArr isKindOfClass:[NSArray class]])
        {
            for (int i=0; i<imageArr.count; i++) {
                NSDictionary *imgDict = [imageArr firstObject];
                NSString *imgPath = imgDict[@"imgPath"];
                
                if (![SafeValue([NSString stringWithFormat:@"%@",imgPath])isEqualToString:@""]) {
                    [self.goodsImg sd_setImageWithURL:[NSURL URLWithString:imgPath]placeholderImage:kImageNamed(@"zhanweifu")];
                }else
                {
                    self.goodsImg.backgroundColor = NumberF4F4F4Color;
                }
            }
        }        
    }
}
-(void)testString
{
    self.storeNameLab.text = @"";//店名称
//    self.stateLab.text = @"";
    self.startNumLab.text = @"";//星级数
    self.comNumLab.text = @"";//评论数
    self.saleNumLab.text = @"";//销量 库存
    self.priceLab.text = @"";//价格

}


- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
