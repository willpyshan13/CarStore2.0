//
//  LoginViewController.m
//  CarOwnerProject
//
//  Created by apple on 2021/1/25.
//

#import "LoginViewController.h"
#import "BaseTabViewController.h"
#import "BaseNavViewController.h"

@interface LoginViewController ()

@end

@implementation LoginViewController
-(void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    self.navigationController.navigationBar.hidden = YES;
}
-(void)viewWillDisappear:(BOOL)animated
{
    [super viewWillAppear:animated];
    self.navigationController.navigationBar.hidden = NO;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    
    self.phoneView.layer.cornerRadius = 25.0f;
    self.codeView.layer.cornerRadius = 25.0f;
    self.loginBtn.layer.cornerRadius = 25.0f;
    self.selNumberBtn.buttonImagePosition = FSCustomButtonImagePositionRight;
    
    self.loginBtn.userInteractionEnabled=NO;
    [self.phoneTF addTarget:self action:@selector(phoneChangedTextField:) forControlEvents:UIControlEventEditingChanged];
    [self.codeTF addTarget:self action:@selector(phoneChangedTextField:) forControlEvents:UIControlEventEditingChanged];
    self.otherView.userInteractionEnabled = YES;
    
    self.technicianRegistBtn.layer.cornerRadius = 2.0f;
    self.storeRegistBtn.layer.cornerRadius = 2.0f;
}

#pragma mark == 改变颜色
-(void)phoneChangedTextField:(UITextField*)textField{
    
    if ([self.phoneTF.text isEqualToString:@""]) {
        self.loginBtn.userInteractionEnabled=NO;
        self.loginBtn.backgroundColor = NumberB9DAF6Color;

        return;
    }
    else if ([self.codeTF.text isEqualToString:@""])
    {
        self.loginBtn.userInteractionEnabled=NO;
        self.loginBtn.backgroundColor = NumberB9DAF6Color;

        return;
    }
    else if ([self.phoneTF.text isEqualToString:@""]&&[self.codeTF.text isEqualToString:@""])
    {
        self.loginBtn.userInteractionEnabled=NO;
        self.loginBtn.backgroundColor = NumberB9DAF6Color;

        return;
    }
    else
    {
        self.loginBtn.userInteractionEnabled = YES;
        self.loginBtn.backgroundColor = Number1684E3Color;
    }
}
- (IBAction)codeBtnClick:(id)sender
{
    NSDictionary *dict= @{
    };
    NSString *urlStr = [NSString stringWithFormat:@"%@%@/merchant",getLoginCodeApi,self.phoneTF.text];
    [HTTPManager getRequestWithUrl:urlStr parameters:dict header:@{} progress:^(NSProgress * _Nonnull uploadProgress) {
    } success:^(NSDictionary * _Nonnull responseObject)
    {
        DLog(@"获取验证码responseObject====%@",responseObject);
        
        [self.codeBtn startCountDownTime:60 withCountDownBlock:^{
            NSLog(@"开始倒计时");
        }];
        
        
    } other:^(NSDictionary * _Nonnull responseObject)
    {
        DLog(@"获取验证码responseObject====%@",responseObject);
        [ZJProgressHUD showStatus:responseObject[@"msg"] andAutoHideAfterTime:2.0];


    } failure:^(NSError * _Nonnull error)
     {
        DLog(@"用户登录error===%@",error);
    }];
}

#pragma mark == 登录

- (IBAction)loginBtnClick:(id)sender
{
    if (self.agreeBtn.selected == NO) {
        [ZJProgressHUD showStatus:@"同意以下内容并授权给嘟灵平台完成注册/登录" andAutoHideAfterTime:2.0];
        return;
    }
    
    NSDictionary *dict= @{
        @"accountName":self.phoneTF.text,
        @"code":self.codeTF.text,
        @"terminal":@"merchant",
    };
    NSString *urlStr = [NSString stringWithFormat:@"%@",loginUserLoginApi];
    [HTTPManager postRequestWithUrl:urlStr parameters:dict header:@{} progress:^(NSProgress * _Nonnull uploadProgress) {
    } success:^(NSDictionary * _Nonnull responseObject)
    {
        DLog(@"用户登录responseObject====%@",responseObject);
        
        NSDictionary *dict =responseObject;
        
        NSDictionary *dataDict = dict[@"data"];
        
        DLog(@"dataDict===%@",dataDict);
        
        UserModel *userM =[UserInfo getUserInfo];
        if (userM==nil){
            userM = [UserModel user];
        }
        userM.token =[NSString stringWithFormat:@"%@",SafeValue([NSString stringWithFormat:@"%@",dataDict[@"token"]])];
        userM.checkSts =[NSString stringWithFormat:@"%@",SafeValue([NSString stringWithFormat:@"%@",dataDict[@"checkSts"]])];

        userM.phone = [NSString stringWithFormat:@"%@",self.phoneTF.text];
        userM.expires_in = [NSString stringWithFormat:@"%@",SafeValue([NSString stringWithFormat:@"%@",dataDict[@"expires_in"]])];
        userM.userType = [NSString stringWithFormat:@"%@",SafeValue([NSString stringWithFormat:@"%@",dataDict[@"userType"]])];
        userM.uuid = [NSString stringWithFormat:@"%@",SafeValue([NSString stringWithFormat:@"%@",dataDict[@"uuid"]])];
        [UserInfo saveUserInfo:userM];
       
        NSString *msg = [NSString stringWithFormat:@"%@",dict[@"msg"]];
        [AppUtils showSuccessMessage:msg];
        
        
        NSString *checkSts=dataDict[@"checkSts"];
        NSString *userType=dataDict[@"userType"];
       
        NSInteger checkInt = [checkSts integerValue];
        NSInteger userTypeInt = [userType integerValue];
        if (checkInt==1) {
            BaseTabViewController *tabbarVC = [[BaseTabViewController alloc]init];
            [self.view.window setRootViewController:tabbarVC];
            
        }else
        {
            SummaryH5ViewController *shopCenterVC =[[SummaryH5ViewController alloc]init];
            
            //用户类型1车主2技师3店铺
            if (userTypeInt==2)
            {
                shopCenterVC.url = [NSString stringWithFormat:@"%@%@",HOSTH5_IP,jishiRegistApiH5];;
                
            }else if (userTypeInt==3)
            {
                shopCenterVC.url = [NSString stringWithFormat:@"%@%@",HOSTH5_IP,shopRegisterH5];
                DLog(@"账户信息shopCenterVC===%@",shopCenterVC.url);
            }
         
            shopCenterVC.hidesBottomBarWhenPushed = YES;
            [self.navigationController pushViewController:shopCenterVC animated:YES];
        }
 
    } other:^(NSDictionary * _Nonnull responseObject)
    {
        [ZJProgressHUD showStatus:responseObject[@"msg"] andAutoHideAfterTime:2.0];
    } failure:^(NSError * _Nonnull error)
     {
        DLog(@"用户登录error===%@",error);
    }];
    
}


//单选协议
- (IBAction)xieyiBtnClick:(id)sender
{
    DLog(@"单选");
    UIButton *selBtn = sender;
    selBtn.selected = !selBtn.selected;
}
//服务协议
- (IBAction)fuwuBtnClick:(id)sender
{
    DLog(@"服务协议");
    CommonWebViewController *vc=[[CommonWebViewController alloc]init];
    vc.titleString=@"服务协议";
    vc.url=PathUrl(@"服务协议", @"docx");
    [self.navigationController pushViewController:vc animated:YES];
}
//隐私政策
- (IBAction)yinsiBtnClick:(id)sender
{
    DLog(@"隐私政策");
    CommonWebViewController *vc=[[CommonWebViewController alloc]init];
    vc.titleString=@"隐私政策";
    vc.url=PathUrl(@"隐私政策", @"docx");
    [self.navigationController pushViewController:vc animated:YES];
}
//版权声明
- (IBAction)banquanBtnClick:(id)sender
{
    DLog(@"版权声明");
    CommonWebViewController *vc=[[CommonWebViewController alloc]init];
    vc.titleString=@"版权声明";
    vc.url=PathUrl(@"版权声明", @"docx");
    [self.navigationController pushViewController:vc animated:YES];
}
//技师注册
- (IBAction)jishiBtnClick:(id)sender
{
    DLog(@"技师注册");
    SummaryH5ViewController *summaryH5VC =[[SummaryH5ViewController alloc]init];
    summaryH5VC.url = [NSString stringWithFormat:@"%@%@",HOSTH5_IP,jishiRegistApiH5];
    [self.navigationController pushViewController:summaryH5VC animated:YES];
}

//店铺注册
- (IBAction)storeBtnClick:(id)sender
{
    DLog(@"店铺注册");
    SummaryH5ViewController *summaryH5VC =[[SummaryH5ViewController alloc]init];
    summaryH5VC.url = [NSString stringWithFormat:@"%@%@",HOSTH5_IP,shopRegisterH5];
    [self.navigationController pushViewController:summaryH5VC animated:YES];
}


/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
