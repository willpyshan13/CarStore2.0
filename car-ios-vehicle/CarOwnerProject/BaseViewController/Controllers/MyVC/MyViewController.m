//
//  MyViewController.m
//  CarOwnerProject
//
//  Created by apple on 2021/1/25.
//

#import "MyViewController.h"
#import "MyOrderTableViewCell.h"
#import "ShareTableViewCell.h"
#import "MyHeadView.h"
#import "UploadImageTypeViewController.h"
#import "ShareAlertView.h"//点击分享
#import "WXApi.h"


@interface MyViewController ()<UITableViewDelegate,UITableViewDataSource>
@property (nonatomic,strong)UITableView *listTV;
@property (nonatomic,strong)MyHeadView *headView;
@property (nonatomic,strong)UIView *footView;
@property(nonatomic,strong)UploadImageTypeViewController *uploadImage;

@property (nonatomic,copy)NSString *lableValue;
@property (nonatomic,strong)ShareAlertView *shareView;

@end

@implementation MyViewController
//我的订单
static NSString * const MyOrderTableViewCellIdentify = @"MyOrderTableViewCell";
//分享
static NSString * const ShareTableViewCellIdentify = @"ShareTableViewCell";

-(void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    self.navigationController.navigationBar.barTintColor = NumberEDF5FBColor;

}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    self.navigationController.navigationBar.barTintColor = NumberEDF5FBColor;

    [self.view addSubview:self.listTV];
    [self creatNav];
    
    [self  requestQueryQuizCaseCarCountApi];
    [self requestQueryUserPhotoImgUrl];//查询图像
    
    [self requestQueryByCode];
}

-(void)requestQueryQuizCaseCarCountApi
{
    NSDictionary *pageBoDict=@{
    };
    NSDictionary *headerDict =@{
        @"token":[UserInfo getUserInfo].token,
    };
    NSString *urlStr = [NSString stringWithFormat:@"%@",queryQuizCaseCarCountApi];
    [HTTPManager getRequestWithUrl:urlStr parameters:pageBoDict header:headerDict progress:^(NSProgress * _Nonnull uploadProgress) {
        
    } success:^(NSDictionary * _Nonnull responseObject) {

        DLog(@"responseObject===%@",responseObject);
        NSDictionary *dataDict = responseObject[@"data"];
        [self.headView setDataDict:dataDict];

        
    } other:^(NSDictionary * _Nonnull responseObject) {
        [AppUtils showErrorMessage:responseObject[@"msg"]];
        
    } failure:^(NSError * _Nonnull error) {
        
    }];
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
        NSString *lableValue=dataDict[@"lableValue"];
        self.lableValue = lableValue;
        [self.listTV reloadData];
    } other:^(NSDictionary * _Nonnull responseObject) {
        [AppUtils showErrorMessage:responseObject[@"msg"]];
        
    } failure:^(NSError * _Nonnull error) {
        
    }];
}



-(void)creatNav{
    UIView *rightView=[[UIView alloc]initWithFrame:CGRectMake(0, 0, 100, 30)];
    UIImage *setImg=kImageNamed(@"my_set_icon");
    UIButton *setBtn =[UIButton buttonWithType:UIButtonTypeCustom];
    setBtn.tag=20;
    setBtn.frame = CGRectMake(100-setImg.size.width, (30-setImg.size.height)/2, setImg.size.width, setImg.size.height);
    [setBtn setImage:setImg forState:UIControlStateNormal];
    [setBtn addTarget:self action:@selector(setBtnClick) forControlEvents:UIControlEventTouchUpInside];
    [rightView addSubview:setBtn];
    
    
    UIBarButtonItem *rightItem = [[UIBarButtonItem alloc]initWithCustomView:rightView];
    
    UIBarButtonItem *spaceItem = [[UIBarButtonItem alloc]initWithBarButtonSystemItem:UIBarButtonSystemItemFixedSpace target:nil action:nil];
    spaceItem.width = -15;
    self.navigationItem.rightBarButtonItems = @[spaceItem,rightItem];
}

-(void)setBtnClick
{
    DLog(@"设置");
    SummaryH5ViewController *shopCenterVC =[[SummaryH5ViewController alloc]init];
    shopCenterVC.url = [NSString stringWithFormat:@"%@%@",HOSTH5_IP,infoH5url];
    shopCenterVC.hidesBottomBarWhenPushed = YES;
    [self.navigationController pushViewController:shopCenterVC animated:YES];
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
        [self.headView.headImg sd_setImageWithURL:[NSURL URLWithString:dataStr] placeholderImage:kImageNamed(@"zhanweifu")];
        [self.listTV reloadData];
        
    } other:^(NSDictionary * _Nonnull responseObject) {
        
        DLog(@"绑定头像responseObject===%@",responseObject);

        [AppUtils showErrorMessage:responseObject[@"msg"]];
    } failure:^(NSError * _Nonnull error) {
        
    }];
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return 2;  
}
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (indexPath.row==0) {
        MyOrderTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:MyOrderTableViewCellIdentify forIndexPath:indexPath];//物流配送
        cell.selectionStyle = UITableViewCellSelectionStyleNone;
        cell.orderListBlock = ^(id  _Nonnull obj, NSInteger type) {
            SummaryH5ViewController *shopCenterVC =[[SummaryH5ViewController alloc]init];
            shopCenterVC.url = [NSString stringWithFormat:@"%@%@",HOSTH5_IP,orderH5url];
            DLog(@"shopCenterVC====%@",shopCenterVC.url);
            shopCenterVC.hidesBottomBarWhenPushed = YES;
            [self.navigationController pushViewController:shopCenterVC animated:YES];
            
        };
        return cell;
    }
    ShareTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:ShareTableViewCellIdentify forIndexPath:indexPath];//分享APP
    cell.shareBlock = ^(id  _Nonnull obj) {
        [self.shareView showShareAlertViewOn:self.view.window];
    };
    cell.selectionStyle = UITableViewCellSelectionStyleNone;
    return cell;
}
-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    
    if (indexPath.row==0) {
        
        DLog(@"lableValue===%@",self.lableValue);
        
        if ([self.lableValue isEqualToString:@"1"]) {
            return 0;
        }else
        {
            return 115.0f;
        }
        
        
    }else
    {
        return 100.0f;
    }
//    return UITableViewAutomaticDimension;
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
        [self.headView.headImg sd_setImageWithURL:[NSURL URLWithString:dataStr] placeholderImage:kImageNamed(@"zhanweifu")];
    } other:^(NSDictionary * _Nonnull responseObject) {
        
        DLog(@"绑定头像responseObject===%@",responseObject);

        [AppUtils showErrorMessage:responseObject[@"msg"]];
    } failure:^(NSError * _Nonnull error) {
        
    }];
}

#pragma mark == 懒加载
-(UITableView *)listTV
{
    if (!_listTV) {
        _listTV =[[UITableView alloc]initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, self.view.frame.size.height-BottomTab_Height-SafeAreaBottom_Height) style:UITableViewStylePlain];
        _listTV.showsVerticalScrollIndicator = NO;
        _listTV.delegate = self;
        _listTV.dataSource = self;
        _listTV.separatorStyle = UITableViewCellSeparatorStyleNone;
        [_listTV registerNib:[UINib nibWithNibName:@"MyOrderTableViewCell" bundle:nil] forCellReuseIdentifier:MyOrderTableViewCellIdentify];//我的订单
        [_listTV registerNib:[UINib nibWithNibName:@"ShareTableViewCell" bundle:nil] forCellReuseIdentifier:ShareTableViewCellIdentify];//分享
        _listTV.backgroundColor = NumberEDF5FBColor;
        _listTV.tableHeaderView = self.headView;
        _listTV.tableFooterView = self.footView;
        _listTV.estimatedRowHeight = 10.0f;
    }
    return _listTV;
}

-(MyHeadView *)headView
{
    if (!_headView) {
        _headView = [[MyHeadView alloc]initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 235)];
    
        WeakSelf(self)
        _headView.selectMsgBlock = ^(id  _Nonnull obj)
        {
            StrongSelf(self)
            DLog(@"上传头像");
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
            };
        };
        
        _headView.selectAllMsgBlock = ^(id  _Nonnull obj, NSInteger type)
        {
            StrongSelf(self)

            if (type==66) {
                SummaryH5ViewController *shopCenterVC =[[SummaryH5ViewController alloc]init];
                shopCenterVC.url = [NSString stringWithFormat:@"%@%@",HOSTH5_IP,consultH5url];
                DLog(@"shopCenterVC====%@",shopCenterVC.url);
                shopCenterVC.hidesBottomBarWhenPushed = YES;
                [self.navigationController pushViewController:shopCenterVC animated:YES];
            }
            else if (type==67)
            {
                SummaryH5ViewController *shopCenterVC =[[SummaryH5ViewController alloc]init];
                shopCenterVC.url = [NSString stringWithFormat:@"%@%@",HOSTH5_IP,myCaseH5url];
                DLog(@"shopCenterVC====%@",shopCenterVC.url);
                shopCenterVC.hidesBottomBarWhenPushed = YES;
                [self.navigationController pushViewController:shopCenterVC animated:YES];
            }
            else if (type==68)
            {
                SummaryH5ViewController *shopCenterVC =[[SummaryH5ViewController alloc]init];
                shopCenterVC.url = [NSString stringWithFormat:@"%@%@",HOSTH5_IP,carH5Url];
                DLog(@"shopCenterVC====%@",shopCenterVC.url);
                shopCenterVC.hidesBottomBarWhenPushed = YES;
                [self.navigationController pushViewController:shopCenterVC animated:YES];
            }
            
        };
    }
    return _headView;
}
-(UIView *)footView
{
    if (!_footView) {
        _footView =[[UIView alloc]initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 100)];
        UIView *bgV =[[UIView alloc]initWithFrame:CGRectMake(10, 0, SCREEN_WIDTH-20, 100)];
        bgV.backgroundColor =NumberEDF5FBColor;
       
        [_footView addSubview:bgV];
        
        UIButton *kefuBtn =[UIButton buttonWithType:UIButtonTypeCustom];
        [kefuBtn setTitle:@"我的客服" forState:UIControlStateNormal];
        [kefuBtn setTitleColor:Number666666Color forState:UIControlStateNormal];
        kefuBtn.frame = CGRectMake(0, 0, bgV.width, 40);
        kefuBtn.titleLabel.font = FontSize(14);
        [kefuBtn addTarget:self action:@selector(kefuBtnClick) forControlEvents:UIControlEventTouchUpInside];
        kefuBtn.backgroundColor=[UIColor whiteColor];
        
        kefuBtn.clipsToBounds = YES;
        kefuBtn.layer.cornerRadius = 6.0f;
        [bgV addSubview:kefuBtn];
        
        
        UIView *spaceV=[[UIView alloc]initWithFrame:CGRectMake(0, 40, SCREEN_WIDTH, 10)];
        spaceV.backgroundColor=NumberEDF5FBColor;
        [bgV addSubview:spaceV];
        
        
        UIButton *quitBt =[UIButton buttonWithType:UIButtonTypeCustom];
        [quitBt setTitle:@"退出登录" forState:UIControlStateNormal];
        [quitBt setTitleColor:Number666666Color forState:UIControlStateNormal];
        quitBt.frame = CGRectMake(0, 50, bgV.width, 40);
        quitBt.titleLabel.font = FontSize(14);
        [quitBt addTarget:self action:@selector(quitBtnClick) forControlEvents:UIControlEventTouchUpInside];
        quitBt.clipsToBounds = YES;
        quitBt.layer.cornerRadius = 6.0f;
        quitBt.backgroundColor=[UIColor whiteColor];
        [bgV addSubview:quitBt];
    }
    return _footView;
}

-(void)quitBtnClick
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

-(void)kefuBtnClick
{
    DLog(@"客服");
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
        urlMessage.title = @"嘟一家";//标题
        urlMessage.description = @"共享全国加盟百万技师  万家品牌4S店/门店的养车服务平台";//描述
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
