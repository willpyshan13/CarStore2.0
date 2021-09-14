//
//  AnswererTableViewCell.h
//  CarOwnerProject
//
//  Created by apple on 2021/1/26.
//

#import <UIKit/UIKit.h>
#import "DazhuSureModel.h"//答主列表

NS_ASSUME_NONNULL_BEGIN
//答主
@interface AnswererTableViewCell : UITableViewCell
@property (weak, nonatomic) IBOutlet UIImageView *headImg;//头像
@property (weak, nonatomic) IBOutlet UILabel *nameLab;//名称
@property (weak, nonatomic) IBOutlet UIButton *startNumLab;//星级数
@property (weak, nonatomic) IBOutlet UILabel *priceLab;//价格
@property (weak, nonatomic) IBOutlet UILabel *zhichengLab;//职称
@property (weak, nonatomic) IBOutlet UILabel *xixunNumLab;//咨询数
@property (weak, nonatomic) IBOutlet UIButton *consultBtn;//点击咨询
@property (weak, nonatomic) IBOutlet UILabel *signLab;//标签
@property (weak, nonatomic) IBOutlet UILabel *typeLab;//0普通技师 1专家技师

@property (nonatomic,strong)DazhuSureModel *model;

@end

NS_ASSUME_NONNULL_END
