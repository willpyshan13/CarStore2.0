//
//  MyViewController.h
//  CarStoreProject
//
//  Created by apple on 2021/1/27.
//

#import "BaseViewController.h"

NS_ASSUME_NONNULL_BEGIN

@interface MyViewController : BaseViewController

//背景view
@property (weak, nonatomic) IBOutlet UIScrollView *bgScrollView;


//头像
@property (weak, nonatomic) IBOutlet UIImageView *headImg;
//姓名
@property (weak, nonatomic) IBOutlet UILabel *nameLab;

//信息背景
@property (weak, nonatomic) IBOutlet UIView *bgView;

//切换背景
@property (weak, nonatomic) IBOutlet UIView *changeBgView;

//可提现
@property (weak, nonatomic) IBOutlet UILabel *tixianLab;
//总计订单
@property (weak, nonatomic) IBOutlet UILabel *orderTotalLab;

//技师管理、关联店铺logo
@property (weak, nonatomic) IBOutlet UIImageView *toolImg;
//技师管理、关联店铺名称
@property (weak, nonatomic) IBOutlet UILabel *statusLab;

//切换状态1、切换技师身份2、切换店铺
@property (weak, nonatomic) IBOutlet UILabel *changeLab;
@property (weak, nonatomic) IBOutlet UIView *lineV;

//钱包
@property (weak, nonatomic) IBOutlet UIView *moneyBgView;
//订单
@property (weak, nonatomic) IBOutlet UIView *orderBgView;
//账户信息
@property (weak, nonatomic) IBOutlet UIView *accountBgView;
//技师管理
@property (weak, nonatomic) IBOutlet UIView *jishiguanliBgView;
@property (weak, nonatomic) IBOutlet UIButton *quitBtn;
@property (weak, nonatomic) IBOutlet UIView *shareBgView;
@property (weak, nonatomic) IBOutlet UIButton *kefuBtn;
//其他
@property (weak, nonatomic) IBOutlet UIView *otherBgView;
//我的问题
@property (weak, nonatomic) IBOutlet UIView *myAnswerBgView;
//我的案例
@property (weak, nonatomic) IBOutlet UIView *myanliBgView;
//我的dtc查询
@property (weak, nonatomic) IBOutlet UIView *mydtcBgView;
//我的课程
@property (weak, nonatomic) IBOutlet UIView *myKechengBgView;
@property (weak, nonatomic) IBOutlet FSCustomButton *workStatesBtn;//请选择工作状态

@end

NS_ASSUME_NONNULL_END
