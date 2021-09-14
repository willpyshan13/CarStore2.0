//
//  TechnicianCaseTableViewCell.h
//  CarOwnerProject
//
//  Created by apple on 2021/1/26.
//

#import <UIKit/UIKit.h>

#import "JishiCaseModel.h"


NS_ASSUME_NONNULL_BEGIN

//技师案例
@interface TechnicianCaseTableViewCell : UITableViewCell
@property (weak, nonatomic) IBOutlet UIImageView *headImg;//图片
@property (weak, nonatomic) IBOutlet UILabel *titLab;//标题
@property (weak, nonatomic) IBOutlet UILabel *priceLab;//价格
@property (weak, nonatomic) IBOutlet UILabel *nameLab;//技师职称姓名
@property (weak, nonatomic) IBOutlet UILabel *glLab;//工龄
@property (weak, nonatomic) IBOutlet UIButton *buyBtn;//立即购买

@property (nonatomic,strong)JishiCaseModel *model;

@end

NS_ASSUME_NONNULL_END
