//
//  MyOrderTableViewCell.m
//  CarOwnerProject
//
//  Created by apple on 2021/1/26.
//

#import "MyOrderTableViewCell.h"

@implementation MyOrderTableViewCell

- (void)awakeFromNib {
    [super awakeFromNib];
    // Initialization code

    self.bgView.layer.cornerRadius = 6.0f;
    self.dfkBtn.buttonImagePosition =FSCustomButtonImagePositionTop;
    self.dfw.buttonImagePosition =FSCustomButtonImagePositionTop;
    self.dpj.buttonImagePosition =FSCustomButtonImagePositionTop;
    self.shtkBtn.buttonImagePosition =FSCustomButtonImagePositionTop;

}


- (IBAction)orderBtnClick:(id)sender {
    UIButton *btn = sender;
    if (self.orderListBlock) {
        self.orderListBlock(sender, btn.tag);
    }
}


- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
