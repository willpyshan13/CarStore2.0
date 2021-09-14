//
//  MyHeadView.h
//  CarOwnerProject
//
//  Created by apple on 2021/1/26.
//

#import <UIKit/UIKit.h>

NS_ASSUME_NONNULL_BEGIN

@interface MyHeadView : UIView
//头像
@property (weak, nonatomic) IBOutlet UIImageView *headImg;
//手机号码
@property (weak, nonatomic) IBOutlet UILabel *phoneLab;

//我的提问
@property (weak, nonatomic) IBOutlet UILabel *tiwenNumLab;

//我的案例
@property (weak, nonatomic) IBOutlet UILabel *anliNumLab;

//已绑定车辆
@property (weak, nonatomic) IBOutlet UILabel *carNumLab;

@property (nonatomic, copy) void(^selectMsgBlock)(id obj);//个人信息

@property (nonatomic,strong)NSDictionary *dataDict;
@property (weak, nonatomic) IBOutlet UIView *tiwenView;
@property (weak, nonatomic) IBOutlet UIView *anliView;
@property (weak, nonatomic) IBOutlet UIView *cheliangView;


@property (nonatomic, copy) void(^selectAllMsgBlock)(id obj,NSInteger type);//我的提问、我的案例、绑定车辆

@end

NS_ASSUME_NONNULL_END
