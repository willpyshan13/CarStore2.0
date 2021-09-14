//
//  OnlineServicesVController.m
//  CarStoreProject
//
//  Created by apple on 2021/1/25.
//

#import "OnlineServicesVController.h"
#import "CaseViewController.h"
#import "AnswerViewController.h"
#import "DTCViewController.h"
#import "EducationViewController.h"


@interface OnlineServicesVController ()<JohnTopTitleViewDelegate>
@property (nonatomic,strong)CaseViewController *caseVC;//案例
@property (nonatomic,strong)AnswerViewController *answerVC;//回答
@property (nonatomic,strong)DTCViewController *dtcVC;//dtc查询
@property (nonatomic,strong)EducationViewController *eduVC;//培训教育
@property (nonatomic,strong) JohnTopTitleView *topTitleView;

@end

@implementation OnlineServicesVController

-(void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];

    self.navigationController.navigationBar.hidden = YES;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.

    self.view.backgroundColor =NumberEDF5FBColor;
    self.navigationController.navigationBar.barTintColor = NumberEDF5FBColor;
    [self.view addSubview:self.topTitleView];
    

}


#pragma mark - JohnTopTitleViewDelegate

- (void)didSelectedPage:(NSInteger)page{
    self.topTitleView.frame = CGRectMake(0, 52, SCREEN_WIDTH, SCREEN_HEIGHT-52-SafeAreaBottom_Height);
    switch (page) {
        case 0:
        {
            
        }
            break;
        default:
        {
            
        }
            break;
    }
}

#pragma mark - Getter
- (JohnTopTitleView *)topTitleView{
    if (!_topTitleView)
    {
        
        _topTitleView = [[JohnTopTitleView alloc]initWithFrame:CGRectMake(0,52, SCREEN_WIDTH, SCREEN_HEIGHT-52) segWidth:SCREEN_WIDTH];
        _topTitleView.backgroundColor =NumberEDF5FBColor;
        _topTitleView.lineWidth = 12;
        _topTitleView.lineHeight = 3;
        _topTitleView.titles = @[@"技术案例",@"知识问答",@"DTC查询",@"培训教育"];
        [_topTitleView setupViewControllerWithFatherVC:self childVC:@[self.caseVC,self.answerVC,self.dtcVC,self.eduVC]];
        _topTitleView.delegete = self;
        _topTitleView.lineColor =Number1691E3Color;
        _topTitleView.selectedTextColor =Number090909Color;
         _topTitleView.textColor = Number090909Color;
        _topTitleView.textFont = FontSize(16);
        _topTitleView.selectedTextFont =[UIFont boldSystemFontOfSize:18];
//        _topTitleView.canScroll = NO;
        
    }
    return _topTitleView;
}

- (CaseViewController *)caseVC{//案例
    if (!_caseVC) {
        _caseVC = [[CaseViewController alloc]init];
        _caseVC.DidScrollBlock = ^(CGFloat scrollY) {
        };
    }
    return _caseVC;
}

- (AnswerViewController *)answerVC{//回答
    if (!_answerVC) {
        _answerVC = [[AnswerViewController alloc]init];
        _answerVC.DidScrollBlock = ^(CGFloat scrollY) {
        };
    }
    return _answerVC;
}

- (DTCViewController *)dtcVC{//dtc查询
    if (!_dtcVC) {
        _dtcVC = [[DTCViewController alloc]init];
        _dtcVC.DidScrollBlock = ^(CGFloat scrollY) {
        };
    }
    return _dtcVC;
}

- (EducationViewController *)eduVC{//回答
    if (!_eduVC) {
        _eduVC = [[EducationViewController alloc]init];
        _eduVC.DidScrollBlock = ^(CGFloat scrollY) {
        };
    }
    return _eduVC;
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
