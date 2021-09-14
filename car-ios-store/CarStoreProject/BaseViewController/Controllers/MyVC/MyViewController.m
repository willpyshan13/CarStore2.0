//
//  MyViewController.m
//  CarStoreProject
//
//  Created by apple on 2021/1/27.
//

#import "MyViewController.h"
#import "UploadImageTypeViewController.h"

#import "SelectWorkStatesView.h"//选择工作状态
#import "ShareAlertView.h"//点击分享
#import "WXApi.h"


@interface MyViewController ()
@property(nonatomic,strong)UploadImageTypeViewController *uploadImage;
@property(nonatomic,strong)UIImage *headImage;
@property (nonatomic,strong)SelectWorkStatesView *workStateView;
@property (nonatomic,strong)ShareAlertView *shareView;

@end

@implementation MyViewController

-(void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    self.navigationController.navigationBar.barTintColor = NumberF6F7F8Color;
    self.navigationController.navigationBar.hidden = YES;
   

}
-(void)viewWillDisappear:(BOOL)animated
{
//    self.navigationController.navigationBar.hidden = NO;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    self.view.backgroundColor = NumberF6F7F8Color;
//    self.navigationController.navigationBar.barTintColor = NumberF6F7F8Color;

    self.headImg.layer.cornerRadius = 44.0f;
    self.bgView.layer.cornerRadius = 6.0f;
    self.otherBgView.layer.cornerRadius = 6.0f;
    
    self.shareBgView.layer.cornerRadius = 6.0f;
    
    self.changeBgView.layer.cornerRadius = 6.0f;
    
    self.kefuBtn.layer.cornerRadius = 6.0f;
    
    self.quitBtn.layer.cornerRadius = 6.0f;
    
    self.nameLab.text = [UserInfo getUserInfo].phone;
    
    self.bgScrollView.showsVerticalScrollIndicator = NO;
    
    DLog(@"身份类型====%@",[UserInfo getUserInfo].userType);
    self.otherBgView.backgroundColor =[UIColor whiteColor];
    self.bgView.backgroundColor =[UIColor whiteColor];
    self.changeBgView.backgroundColor = [UIColor whiteColor];

    
    //1车主2技师3店铺
    if ([[UserInfo getUserInfo].userType isEqualToString:@"2"])
    {
        self.toolImg.image = kImageNamed(@"my_wodedianpu_icon");
        self.statusLab.text=@"关联店铺";
        self.changeLab.text= @"切换店铺身份";
        
    }else if ([[UserInfo getUserInfo].userType isEqualToString:@"3"])
    {
        self.toolImg.image = kImageNamed(@"my_jsgl_icon");
        self.statusLab.text=@"技师管理";
        self.changeLab.text= @"切换技师身份";
    }
    //账户信息
    [self requestAccountMsg];
    
    self.headImg.userInteractionEnabled = YES;
    [self.headImg addGestureRecognizer:[[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(uploadHeadTagDidCLick:)]];
    
    
    self.nameLab.userInteractionEnabled = YES;
    [self.nameLab addGestureRecognizer:[[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(tagDidCLick:)]];
    
    //钱包
    self.moneyBgView.userInteractionEnabled = YES;
    [self.moneyBgView addGestureRecognizer:[[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(moneyBgDidCLick:)]];
    
    //订单
    self.orderBgView.userInteractionEnabled = YES;
    [self.orderBgView addGestureRecognizer:[[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(orderBgDidCLick:)]];

    
    //账户信息
    self.accountBgView.userInteractionEnabled = YES;
    [self.accountBgView addGestureRecognizer:[[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(accountBgDidCLick:)]];

    //技师管理
    self.jishiguanliBgView.userInteractionEnabled = YES;
    [self.jishiguanliBgView addGestureRecognizer:[[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(jishiguanliBgDidCLick:)]];
    
    //查询头像
    [self requestQueryUserPhotoImgUrl];
    
    
    //我的问题
    self.myAnswerBgView.userInteractionEnabled = YES;
    [self.myAnswerBgView addGestureRecognizer:[[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(myAnswerBgViewDidCLick:)]];
    //我的案例
    self.myanliBgView.userInteractionEnabled = YES;
    [self.myanliBgView addGestureRecognizer:[[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(myanliBgViewDidCLick:)]];
    //我的dtc查询
    self.mydtcBgView.userInteractionEnabled = YES;
    [self.mydtcBgView addGestureRecognizer:[[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(mydtcBgViewDidCLick:)]];
    //我的课程
    self.myKechengBgView.userInteractionEnabled = YES;
    [self.myKechengBgView addGestureRecognizer:[[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(myKechengBgViewDidCLick:)]];
    
    //请选择工作状态
    self.workStatesBtn.buttonImagePosition =FSCustomButtonImagePositionRight;
    
    
    
    self.orderBgView.hidden = NO;
    self.moneyBgView.hidden = NO;
    self.bgView.backgroundColor =[UIColor whiteColor];
    self.lineV.backgroundColor =NumberF1F1F1Color;
    [self requestQueryByCode];
}

-(void)requestQueryByCode
{
    NSDictionary *pageBoDict=@{
    };
    NSDictionary *headerDict =@{
        @"token":[UserInfo getUserInfo].token,
    };
    NSString *urlStr = [NSString stringWithFormat:@"%@",queryByCodeApi];
    [HTTPManager getRequestWithUrl:urlStr parameters:pageBoDict header:headerDict progress:^(NSProgress * _Nonnull uploadProgress) {
        
    } success:^(NSDictionary * _Nonnull responseObject) {
        DLog(@"responseObject121212121=====%@",responseObject);
        NSDictionary *dataDict = responseObject[@"data"];
        NSString *lableValue=SafeValue([NSString stringWithFormat:@"%@",dataDict[@"lableValue"]]);
        
        if ([lableValue isEqualToString:@"1"])
        {
            self.orderBgView.hidden = YES;
            self.moneyBgView.hidden = YES;
            self.bgView.backgroundColor =[UIColor clearColor];
            self.lineV.backgroundColor =NumberF6F7F8Color;
        }else
        {
            self.orderBgView.hidden = NO;
            self.moneyBgView.hidden = NO;
            self.bgView.backgroundColor =[UIColor whiteColor];
            self.lineV.backgroundColor =NumberF1F1F1Color;
        }
        
        
    } other:^(NSDictionary * _Nonnull responseObject)
    {
        DLog(@"responseObject===%@",responseObject);
        [AppUtils showErrorMessage:responseObject[@"msg"]];
        
    } failure:^(NSError * _Nonnull error) {
        
    }];
}



#pragma mark == 请选择我的工作状态

- (IBAction)workStatesBtnClick:(id)sender {
    
    [self.workStateView showSelectWorkStatesViewOn:self.view.window];
    
}

#pragma mark == 我的问题
-(void)myAnswerBgViewDidCLick:(UITapGestureRecognizer *)sender
{
    SummaryH5ViewController *shopCenterVC =[[SummaryH5ViewController alloc]init];
    
    shopCenterVC.url = [NSString stringWithFormat:@"%@%@",HOSTH5_IP,myConsultH5url];
 
    shopCenterVC.hidesBottomBarWhenPushed = YES;
    [self.navigationController pushViewController:shopCenterVC animated:YES];
}
#pragma mark == 我的案例
-(void)myanliBgViewDidCLick:(UITapGestureRecognizer *)sender
{
    SummaryH5ViewController *shopCenterVC =[[SummaryH5ViewController alloc]init];
    
    shopCenterVC.url = [NSString stringWithFormat:@"%@%@",HOSTH5_IP,myCaseH5Url];
 
    shopCenterVC.hidesBottomBarWhenPushed = YES;
    [self.navigationController pushViewController:shopCenterVC animated:YES];
}
#pragma mark == 我的dtc查询
-(void)mydtcBgViewDidCLick:(UITapGestureRecognizer *)sender
{
    DLog(@"我的dtc查询");
    
    SummaryH5ViewController *shopCenterVC =[[SummaryH5ViewController alloc]init];
    
    shopCenterVC.url = [NSString stringWithFormat:@"%@%@",HOSTH5_IP,dtcListH5Api];
 
    shopCenterVC.hidesBottomBarWhenPushed = YES;
    [self.navigationController pushViewController:shopCenterVC animated:YES];
}
#pragma mark == 我的课程
-(void)myKechengBgViewDidCLick:(UITapGestureRecognizer *)sender
{
    DLog(@"我的课程");
    SummaryH5ViewController *shopCenterVC =[[SummaryH5ViewController alloc]init];
    
    shopCenterVC.url = [NSString stringWithFormat:@"%@%@",HOSTH5_IP,myjiaochengH5Api];
 
    shopCenterVC.hidesBottomBarWhenPushed = YES;
    [self.navigationController pushViewController:shopCenterVC animated:YES];
}
#pragma mark == 钱包
-(void)moneyBgDidCLick:(UITapGestureRecognizer *)sender
{
    DLog(@"钱包");
    SummaryH5ViewController *shopCenterVC =[[SummaryH5ViewController alloc]init];
    
    shopCenterVC.url = [NSString stringWithFormat:@"%@%@",HOSTH5_IP,walletH5Api];
    DLog(@"钱包shopCenterVC===%@",shopCenterVC.url);
 
    shopCenterVC.hidesBottomBarWhenPushed = YES;
    [self.navigationController pushViewController:shopCenterVC animated:YES];
}
#pragma mark == 订单
-(void)orderBgDidCLick:(UITapGestureRecognizer *)sender
{
    DLog(@"订单");
    SummaryH5ViewController *shopCenterVC =[[SummaryH5ViewController alloc]init];
    
    shopCenterVC.url = [NSString stringWithFormat:@"%@%@",HOSTH5_IP,myOrderH5Api];
 
    shopCenterVC.hidesBottomBarWhenPushed = YES;
    [self.navigationController pushViewController:shopCenterVC animated:YES];

}
#pragma mark == 账户信息
-(void)accountBgDidCLick:(UITapGestureRecognizer *)sender
{
    DLog(@"账户信息");
    SummaryH5ViewController *shopCenterVC =[[SummaryH5ViewController alloc]init];
    
    //用户类型1车主2技师3店铺
    if ([[UserInfo getUserInfo].userType isEqualToString:@"2"])
    {
        shopCenterVC.url = [NSString stringWithFormat:@"%@%@",HOSTH5_IP,accountInforJishiH5Api];;
        
    }else if ([[UserInfo getUserInfo].userType isEqualToString:@"3"])
    {
        shopCenterVC.url = [NSString stringWithFormat:@"%@%@",HOSTH5_IP,accountInforH5Api];
        DLog(@"账户信息shopCenterVC===%@",shopCenterVC.url);

    }
 
    shopCenterVC.hidesBottomBarWhenPushed = YES;
    [self.navigationController pushViewController:shopCenterVC animated:YES];
}
#pragma mark == 技师管理
-(void)jishiguanliBgDidCLick:(UITapGestureRecognizer *)sender
{
    
    //2技师关联店铺 3店铺技师管理
    SummaryH5ViewController *shopCenterVC =[[SummaryH5ViewController alloc]init];
    
    //用户类型 2技师关联店铺  3店铺技师管理
    if ([[UserInfo getUserInfo].userType isEqualToString:@"2"])
    {
        shopCenterVC.url = [NSString stringWithFormat:@"%@%@",HOSTH5_IP,relevancyShopH5Api];;
        
    }else if ([[UserInfo getUserInfo].userType isEqualToString:@"3"])
    {
        shopCenterVC.url = [NSString stringWithFormat:@"%@%@",HOSTH5_IP,jishiAdminH5Api];
    }
    shopCenterVC.hidesBottomBarWhenPushed = YES;
    [self.navigationController pushViewController:shopCenterVC animated:YES];
    
}
#pragma mark ==
-(void)uploadHeadTagDidCLick:(UITapGestureRecognizer *)sender
{
    DLog(@"上传头像");
    DLog(@"正面");
    WeakSelf(self)
    [self.uploadImage showUploadImageView];
    self.uploadImage.selectImageBlock = ^(UIImage *image)
    {
        StrongSelf(self)
//        [self.headImg setImage:image];
        
        NSString *base64Str =[self imageChangeBase64:image];
        NSDictionary *pageBoDict=@{
            @"base64Img":base64Str,
            @"type":@"other"
            
        };
        NSDictionary *headerDict =@{
            @"token":[UserInfo getUserInfo].token,
        };
        NSString *urlStr = [NSString stringWithFormat:@"%@",uploadBase64Image];
        [HTTPManager postRequestWithUrl:urlStr parameters:pageBoDict header:headerDict progress:^(NSProgress * _Nonnull uploadProgress) {
            
        } success:^(NSDictionary * _Nonnull responseObject) {
            DLog(@"上传头像responseObject===%@",responseObject);
            NSString *dataStr = [NSString stringWithFormat:@"%@",responseObject[@"data"]];
            [self requestUpdateUserPhotoImgWithUserPhotoImg:dataStr];
            
        } other:^(NSDictionary * _Nonnull responseObject) {
            
            DLog(@"账户信息其他responseObject===%@",responseObject);

            [AppUtils showErrorMessage:responseObject[@"msg"]];
        } failure:^(NSError * _Nonnull error) {
            
        }];
                
//        NSDictionary *headerDict =@{
//          @"token":[UserInfo getUserInfo].token,
//        };
//        NSString *urlStr = [NSString stringWithFormat:@"%@?type=other",uploadFileApi];
//
//        [HTTPManager postRequestWithUrl:urlStr parameters:@{} header:headerDict constructingBodyWithBlock:^(id<AFMultipartFormData>  _Nonnull formData) {
//            NSDate *date = [NSDate date];
//
//            NSDateFormatter *formatter = [[NSDateFormatter alloc]init];
//            [formatter setDateFormat:@"yyyyMMddHHmmss"];
//            NSString *dateStr = [formatter stringFromDate:date];
//            NSData *imageData=UIImageJPEGRepresentation(image, 1.0);
//            NSString *fileName = [NSString stringWithFormat:@"%@.png",dateStr];
//            [formData appendPartWithFileData:imageData name:@"file" fileName:fileName mimeType:@"image/png/jpg"];
//
//                } progress:^(NSProgress * _Nonnull uploadProgress) {
//                    NSLog(@"上传进度===%f",uploadProgress.fractionCompleted);
//                } success:^(NSDictionary * _Nonnull responseObject) {
//                    NSLog(@"图片上传成功===%@",responseObject);
//                } other:^(NSDictionary * _Nonnull responseObject) {
//
//                } failure:^(NSError * _Nonnull error) {
//                    NSLog(@"图片上传失败===%@",error);
//                }];
    };
}

-(void)requestUpdateUserPhotoImgWithUserPhotoImg:(NSString *)userPhotoImg
{
    NSDictionary *pageBoDict=@{
        @"userPhotoImg":userPhotoImg,
    };
    NSDictionary *headerDict =@{
        @"token":[UserInfo getUserInfo].token,
    };
    NSString *urlStr = [NSString stringWithFormat:@"%@",updateUserPhotoImageApi];
    [HTTPManager postRequestWithUrl:urlStr parameters:pageBoDict header:headerDict progress:^(NSProgress * _Nonnull uploadProgress) {
        
    } success:^(NSDictionary * _Nonnull responseObject) {
        DLog(@"绑定头像responseObject===%@",responseObject);
        NSString *dataStr = [NSString stringWithFormat:@"%@",userPhotoImg];
        [self.headImg sd_setImageWithURL:[NSURL URLWithString:dataStr] placeholderImage:kImageNamed(@"zhanweifu")];

        
    } other:^(NSDictionary * _Nonnull responseObject) {
        
        DLog(@"绑定头像responseObject===%@",responseObject);

        [AppUtils showErrorMessage:responseObject[@"msg"]];
    } failure:^(NSError * _Nonnull error) {
        
    }];
}
#pragma mark== 查询头像
-(void)requestQueryUserPhotoImgUrl
{
    NSDictionary *pageBoDict=@{
    };
    NSDictionary *headerDict =@{
        @"token":[UserInfo getUserInfo].token,
    };
    NSString *urlStr = [NSString stringWithFormat:@"%@",queryUserPhotoImgUrlApi];
    [HTTPManager postRequestWithUrl:urlStr parameters:pageBoDict header:headerDict progress:^(NSProgress * _Nonnull uploadProgress) {
        
    } success:^(NSDictionary * _Nonnull responseObject) {
        DLog(@"查询头像responseObject===%@",responseObject);
        
        NSString *dataStr = [NSString stringWithFormat:@"%@",responseObject[@"data"]];
        [self.headImg sd_setImageWithURL:[NSURL URLWithString:dataStr] placeholderImage:kImageNamed(@"zhanweifu")];
        
    } other:^(NSDictionary * _Nonnull responseObject) {
        
        DLog(@"绑定头像responseObject===%@",responseObject);

        [AppUtils showErrorMessage:responseObject[@"msg"]];
    } failure:^(NSError * _Nonnull error) {
        DLog(@"头像查询失败");
    }];
}


#pragma mark == 个人信息
-(void)tagDidCLick:(UITapGestureRecognizer *)sender
{
    DLog(@"个人信息");
    SummaryH5ViewController *shopCenterVC =[[SummaryH5ViewController alloc]init];
    
    //用户类型1车主2技师3店铺
    if ([[UserInfo getUserInfo].userType isEqualToString:@"2"])
    {
        shopCenterVC.url = [NSString stringWithFormat:@"%@%@",HOSTH5_IP,gerenxinxiH5Api];;
        
    }else if ([[UserInfo getUserInfo].userType isEqualToString:@"3"])
    {
        shopCenterVC.url = [NSString stringWithFormat:@"%@%@",HOSTH5_IP,shopCenterH5Api];
        DLog(@"个人信息shopCenterVC===%@",shopCenterVC.url);
    }    
 
    shopCenterVC.hidesBottomBarWhenPushed = YES;
    [self.navigationController pushViewController:shopCenterVC animated:YES];
}

#pragma mark == 账户信息
-(void)requestAccountMsg
{
    NSDictionary *pageBoDict=@{
    };
    NSDictionary *headerDict =@{
        @"token":[UserInfo getUserInfo].token,
    };
    NSString *urlStr = [NSString stringWithFormat:@"%@",accountMsgApi];
    [HTTPManager getRequestWithUrl:urlStr parameters:pageBoDict header:headerDict progress:^(NSProgress * _Nonnull uploadProgress) {
        
    } success:^(NSDictionary * _Nonnull responseObject) {
        DLog(@"账户信息responseObject===%@",responseObject);
        NSDictionary *dataDict = responseObject[@"data"];
        self.tixianLab.text = [NSString stringWithFormat:@"可提现%@元",SafeValue([NSString stringWithFormat:@"%@",dataDict[@"aviWithdrawAmt"]])];
        self.orderTotalLab.text = [NSString stringWithFormat:@"总计%@单",SafeValue([NSString stringWithFormat:@"%@",dataDict[@"orderNum"]])];
        
    } other:^(NSDictionary * _Nonnull responseObject) {
        
        DLog(@"账户信息其他responseObject===%@",responseObject);

        [AppUtils showErrorMessage:responseObject[@"msg"]];
    } failure:^(NSError * _Nonnull error) {
        
    }];
}


#pragma mark == 切换身份
-(void)requestLoginSwitchRole
{
    NSDictionary *pageBoDict=@{
    };
    NSDictionary *headerDict =@{
        @"token":[UserInfo getUserInfo].token,
    };
    NSString *urlStr = [NSString stringWithFormat:@"%@",loginSwitchRoleApi];
    [HTTPManager postRequestWithUrl:urlStr parameters:pageBoDict header:headerDict progress:^(NSProgress * _Nonnull uploadProgress) {
        
    } success:^(NSDictionary * _Nonnull responseObject) {
        DLog(@"切换身份responseObject===%@",responseObject);
        

        NSDictionary *dict =responseObject;
        
        NSDictionary *dataDict = dict[@"data"];
        
        UserModel *userM =[UserInfo getUserInfo];
        
        if (userM==nil){
            userM = [UserModel user];
        }
        
        userM.token =[NSString stringWithFormat:@"%@",SafeValue([NSString stringWithFormat:@"%@",dataDict[@"token"]])];
        userM.expires_in = [NSString stringWithFormat:@"%@",SafeValue([NSString stringWithFormat:@"%@",dataDict[@"expires_in"]])];
        userM.userType = [NSString stringWithFormat:@"%@",SafeValue([NSString stringWithFormat:@"%@",dataDict[@"userType"]])];
        userM.uuid = [NSString stringWithFormat:@"%@",SafeValue([NSString stringWithFormat:@"%@",dataDict[@"uuid"]])];
        userM.checkSts = [NSString stringWithFormat:@"%@",SafeValue([NSString stringWithFormat:@"%@",dataDict[@"checkSts"]])];
        [UserInfo saveUserInfo:userM];
        
        
        DLog(@"切换身份userType111===%@",userM.userType);
        
        NSString *checkSts=dataDict[@"checkSts"];
        NSString *userType=dataDict[@"userType"];
       
        NSInteger checkInt = [checkSts integerValue];
        NSInteger userTypeInt = [userType integerValue];
        if (checkInt==1) {
            
            //1车主2技师3店铺
            if ([[UserInfo getUserInfo].userType isEqualToString:@"2"])
            {
                self.toolImg.image = kImageNamed(@"my_wodedianpu_icon");
                self.statusLab.text=@"关联店铺";
                self.changeLab.text= @"切换店铺身份";
                
            }else if ([[UserInfo getUserInfo].userType isEqualToString:@"3"])
            {
                self.toolImg.image = kImageNamed(@"my_jsgl_icon");
                self.statusLab.text=@"技师管理";
                self.changeLab.text= @"切换技师身份";
            }
            
        }else
        {
            SummaryH5ViewController *shopCenterVC =[[SummaryH5ViewController alloc]init];
            
            //用户类型1车主2技师3店铺
            if (userTypeInt==2)
            {
                shopCenterVC.url = [NSString stringWithFormat:@"%@%@",HOSTH5_IP,gerenxinxiH5Api];;
                
            }else if (userTypeInt==3)
            {
                shopCenterVC.url = [NSString stringWithFormat:@"%@%@",HOSTH5_IP,shopCenterH5Api];
                DLog(@"账户信息shopCenterVC===%@",shopCenterVC.url);
            }
         
            shopCenterVC.hidesBottomBarWhenPushed = YES;
            [self.navigationController pushViewController:shopCenterVC animated:YES];
        }
        
        
        [[NSNotificationCenter defaultCenter] postNotificationName:kUpdateUserInfo object:nil];

        
        [AppUtils showSuccessMessage:responseObject[@"msg"]];

    } other:^(NSDictionary * _Nonnull responseObject) {
        
        DLog(@"切换角色其他responseObject===%@",responseObject);
        [AppUtils showErrorMessage:responseObject[@"msg"]];
    } failure:^(NSError * _Nonnull error) {
        
    }];
}

//切换身份
- (IBAction)changeBtnClick:(id)sender {
    //技师管理  切换技师身份
    [self requestLoginSwitchRole];
}

- (IBAction)quiteLoginBtnClick:(id)sender
{
    DLog(@"退出登录");
   UserModel *userM =[UserInfo getUserInfo];
    if (userM==nil){
        userM = [UserModel user];
    }
    
    userM.token =@"";
    userM.phone =@"";
    [UserInfo saveUserInfo:userM];
    LoginViewController *loginVC =[[LoginViewController alloc]init];
    loginVC.hidesBottomBarWhenPushed = YES;
    [self.navigationController pushViewController:loginVC animated:NO];
}

#pragma mark == 点击分享
- (IBAction)shareBtnClick:(id)sender
{
    [self.shareView showShareAlertViewOn:self.view.window];
//    [AppUtils showInfoMessage:@"该功能暂未开通，敬请期待"];
}
- (IBAction)kefuBtnClick:(id)sender {
    [self requestQueryListByTypeApiWithName:@"customer_service_phone"];
}


-(void)requestQueryListByTypeApiWithName:(NSString *)testString
{
    NSDictionary *pageBoDict=@{
    };
    NSDictionary *headerDict =@{
        @"token":[UserInfo getUserInfo].token,
    };
    NSString *urlStr = [NSString stringWithFormat:@"%@/%@",queryListByTypeApi,testString];
    [HTTPManager getRequestWithUrl:urlStr parameters:pageBoDict header:headerDict progress:^(NSProgress * _Nonnull uploadProgress) {
        
    } success:^(NSDictionary * _Nonnull responseObject) {
        DLog(@"responseObject====%@",responseObject);
        

        NSArray *dataArr = responseObject[@"data"];
        for (NSDictionary *dict in dataArr) {
            NSString *phoneStr = dict[@"lableValue"];
            
            [[UIApplication   sharedApplication] openURL:[NSURL URLWithString:[NSString stringWithFormat:@"tel://%@",phoneStr]]options:@{} completionHandler:^(BOOL success) {
            }];
        }
        
        
        
    } other:^(NSDictionary * _Nonnull responseObject) {
        [AppUtils showErrorMessage:responseObject[@"msg"]];
        
    } failure:^(NSError * _Nonnull error) {
        
    }];
}

- (UploadImageTypeViewController *)uploadImage{
    if (!_uploadImage) {
        _uploadImage = [UploadImageTypeViewController new];
        _uploadImage.allowImageEdit = YES;
    }
    return _uploadImage;
}

-(NSString *)imageChangeBase64: (UIImage *)image{
     
    NSData   *imageData = nil;
  //NSString *mimeType  = nil;
    if ([self imageHasAlpha:image]) {
         
        imageData = UIImagePNGRepresentation(image);
        //mimeType = @"image/png";
    }else{
         
        imageData = UIImageJPEGRepresentation(image, 0.3f);
        //mimeType = @"image/jpeg";
    }
    return [NSString stringWithFormat:@"%@",[imageData base64EncodedStringWithOptions: 0]];
}
 
-(BOOL)imageHasAlpha:(UIImage *)image{
 
    CGImageAlphaInfo alpha = CGImageGetAlphaInfo(image.CGImage);
    return (alpha == kCGImageAlphaFirst ||
            alpha == kCGImageAlphaLast ||
            alpha == kCGImageAlphaPremultipliedFirst ||
            alpha == kCGImageAlphaPremultipliedLast);
}


-(SelectWorkStatesView *)workStateView
{
    if (!_workStateView) {
        _workStateView = [SelectWorkStatesView initSelectWorkStatesView];
        [_workStateView configFrame:[UIScreen mainScreen].bounds];
        WeakSelf(self)
        _workStateView.workStatesActionBlock = ^(id  _Nonnull obj, NSString * _Nonnull workStateTitle)
        {
            StrongSelf(self)
            [self.workStatesBtn setTitle:[NSString stringWithFormat:@"工作状态：%@…  ",workStateTitle] forState:UIControlStateNormal];
        };
    }
    return _workStateView;
}
#pragma mark == 分享APP
-(ShareAlertView *)shareView
{
    if (!_shareView) {
        _shareView = [ShareAlertView initShareAlertView];
        [_shareView configFrame:[UIScreen mainScreen].bounds];
        
        /*
         62 微信
         63 朋友圈
         **/
        UIImage *shareImg = kImageNamed(@"share_logo");
//        NSData *imagedata = UIImagePNGRepresentation(shareImg);
        
        WeakSelf(self)
        _shareView.shareBtnBlock = ^(id  _Nonnull obj, NSInteger type)
        {
            StrongSelf(self)
           if (type==62)
            {
                DLog(@"微信");
                NSData *compressData = nil;
                compressData = UIImageJPEGRepresentation(shareImg, 0.25);
                [self weixinShareWiththumbImage:[UIImage imageWithData:compressData] shareType:1];
                
            }
            else if (type==63)
            {
                DLog(@"朋友圈");
                NSData *compressData = nil;
                compressData = UIImageJPEGRepresentation(shareImg, 0.25);
                [self weixinShareWiththumbImage:[UIImage imageWithData:compressData] shareType:2];
            }
            
        };
    }
    return _shareView;
}

-(void)weixinShareWiththumbImage:(UIImage *)thumbImage shareType:(NSInteger)type
{
    /*
     type 1、微信好友
     type 2、朋友圈
     **/
    
    if([WXApi isWXAppInstalled]){//判断当前设备是否安装微信客户端
        //创建多媒体消息结构体
        WXMediaMessage *urlMessage = [WXMediaMessage message];
        urlMessage.title = @"嘟一家服务";//标题
        urlMessage.description = @"分享各地实现上亿车主  信任与成就的技师/店铺之家";//描述
        [urlMessage setThumbImage:thumbImage];
        //创建网页数据对象
        WXWebpageObject *webObj = [WXWebpageObject object];
        webObj.webpageUrl = [NSString stringWithFormat:@"%@%@",HOSTH5_IP,shareDownAppH5];//链接
        urlMessage.mediaObject = webObj;
        SendMessageToWXReq *sendReq = [[SendMessageToWXReq alloc] init];
        sendReq.bText = NO;//不使用文本信息
        sendReq.message = urlMessage;
        if (type==1) {
            sendReq.scene = WXSceneSession;//分享到好友会话
        }else if (type==2)
        {
            sendReq.scene = WXSceneTimeline;//分享到朋友圈
        }
        [WXApi sendReq:sendReq completion:^(BOOL success) {
           NSLog(@"发起分享:%@", success ? @"成功" : @"失败");
        }];
    }else{

        //提示：未安装微信应用或版本过低
    }
    
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
