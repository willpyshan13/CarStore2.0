//
//  LoginViewController.h
//  CarOwnerProject
//
//  Created by apple on 2021/1/25.
//

#import "BaseViewController.h"

NS_ASSUME_NONNULL_BEGIN
//登录
@interface LoginViewController : BaseViewController
//手机号
@property (weak, nonatomic) IBOutlet UIView *phoneView;
//验证码
@property (weak, nonatomic) IBOutlet UIView *codeView;
//登录按钮
@property (weak, nonatomic) IBOutlet UIButton *loginBtn;
//手机号码
@property (weak, nonatomic) IBOutlet UITextField *phoneTF;
//验证码
@property (weak, nonatomic) IBOutlet UITextField *codeTF;
//验证码按钮
@property (weak, nonatomic) IBOutlet UIButton *codeBtn;
//+86
@property (weak, nonatomic) IBOutlet FSCustomButton *selNumberBtn;
@property (weak, nonatomic) IBOutlet UIView *otherView;
@property (weak, nonatomic) IBOutlet UIButton *agreeBtn;

@end

NS_ASSUME_NONNULL_END
