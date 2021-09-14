//
//  ShareTableViewCell.m
//  CarOwnerProject
//
//  Created by apple on 2021/1/26.
//

#import "ShareTableViewCell.h"

@implementation ShareTableViewCell

- (void)awakeFromNib {
    [super awakeFromNib];
    // Initialization code
    self.bgView.layer.cornerRadius = 6.0f;
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}
- (IBAction)shareBtnClick:(id)sender {
    [AppUtils showInfoMessage:@"该功能暂未开通，敬请期待"];
//       if (self.shareBlock) {
//           self.shareBlock(sender);
//       }
}

@end
