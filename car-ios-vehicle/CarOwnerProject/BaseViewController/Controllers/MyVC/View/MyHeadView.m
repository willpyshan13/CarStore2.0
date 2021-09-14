//
//  MyHeadView.m
//  CarOwnerProject
//
//  Created by apple on 2021/1/26.
//

#import "MyHeadView.h"

@implementation MyHeadView

- (instancetype)initWithFrame:(CGRect)frame {
    
    if (self = [super initWithFrame:frame]) {
        
        self = [[[NSBundle mainBundle] loadNibNamed:@"MyHeadView" owner:self options:0] lastObject];
        [self commit];
    }
    return self;
}
-(void)commit
{
    self.headImg.layer.cornerRadius = 44.0f;
    
    NSString *phoneS =[UserInfo getUserInfo].phone;
    
    self.phoneLab.text = SafeValue([NSString stringWithFormat:@"%@",phoneS]);
    
    self.headImg.userInteractionEnabled = YES;
    self.tiwenView.userInteractionEnabled = YES;
    self.anliView.userInteractionEnabled = YES;
    self.cheliangView.userInteractionEnabled = YES;

    [self.headImg addGestureRecognizer:[[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(tagDidCLick:)]];
    
    [self.tiwenView addGestureRecognizer:[[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(tagDidMsgCLick:)]];
    [self.anliView addGestureRecognizer:[[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(tagDidMsgCLick:)]];

    [self.cheliangView addGestureRecognizer:[[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(tagDidMsgCLick:)]];

}
#pragma mark == 我的提问 我的案例 已绑定车辆
-(void)tagDidMsgCLick:(UITapGestureRecognizer *)sender
{
    UIView *view = [sender view];
    
    if (self.selectAllMsgBlock) {
        self.selectAllMsgBlock(view,view.tag);
    }
}

#pragma mark == 点击头像
-(void)tagDidCLick:(UITapGestureRecognizer *)sender
{
    UIView *view = [sender view];
    
    if (self.selectMsgBlock) {
        self.selectMsgBlock(view);
    }
}

-(void)setDataDict:(NSDictionary *)dataDict
{
    if (dataDict) {
        [self testString];
        
        self.tiwenNumLab.text = SafeValue([NSString stringWithFormat:@"%@",dataDict[@"quizCount"]]);
        self.anliNumLab.text = SafeValue([NSString stringWithFormat:@"%@",dataDict[@"caseCount"]]);
        self.carNumLab.text = SafeValue([NSString stringWithFormat:@"%@",dataDict[@"carCount"]]);

        
    }
}
-(void)testString
{
    self.tiwenNumLab.text= @"";
    self.anliNumLab.text= @"";
    self.carNumLab.text= @"";
}

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/

@end
