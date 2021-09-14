//
//  SelectRidersCarAlertView.m
//  CarOwnerProject
//
//  Created by apple on 2021/2/7.
//

#import "SelectRidersCarAlertView.h"
@interface SelectRidersCarAlertView()<UITableViewDelegate,UITableViewDataSource>

@end
@implementation SelectRidersCarAlertView

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/

+ (instancetype)selectRidersAlertViewCarAlertView{
    static SelectRidersCarAlertView *bind = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        bind = [SelectRidersCarAlertView initSelectRidersCarAlertView];
        [bind configFrame:[UIScreen mainScreen].bounds];
    });
    return bind;
}
- (instancetype)initWithCoder:(NSCoder *)aDecoder{
    if (self = [super initWithCoder:aDecoder]) {
    }
    return self;
}

- (void)configFrame:(CGRect)rect
{
    self.frame = rect;
}


- (void)awakeFromNib{
    [super awakeFromNib];
    _bgView.y = self.frame.size.height;
    self.bgView.backgroundColor = NumberFFColor;
    [self commonInit];
}

- (void)layoutSubviews{
    [super layoutSubviews];
}

- (void)commonInit
{
    
    self.bgView.layer.cornerRadius = 6.0f;
    
   

    self.listTab.delegate = self;
    self.listTab.dataSource = self;
    
}

- (void)touchesEnded:(NSSet<UITouch *> *)touches withEvent:(UIEvent *)event{
    UITouch *touch = [touches anyObject];
    UIView *view = [touch view];
    if (view != _bgView) {
        if (self.superview) {

            [self dismissClick];
        }
    }
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return self.couponArr.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{

    static NSString *introIdentifierCell = @"cell";
     UITableViewCell *cell =[tableView dequeueReusableCellWithIdentifier:introIdentifierCell];
     if (!cell) {
         cell =[[UITableViewCell alloc]initWithStyle:UITableViewCellStyleDefault reuseIdentifier:introIdentifierCell];
     }

    SelectRidersTModel *model = self.couponArr[indexPath.row];

    
    cell.selectionStyle = UITableViewCellSelectionStyleNone;
    cell.textLabel.font = FontSize(14);
    cell.textLabel.text = model.configName;
    cell.textLabel.textAlignment = NSTextAlignmentCenter;
     return cell;
    
    
}
-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return UITableViewAutomaticDimension;
}

-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
   
    SelectRidersTModel *model = self.couponArr[indexPath.row];
    if (self.delegate && [self.delegate respondsToSelector:@selector(getSelectCarType:)]) {
           [self.delegate getSelectCarType:model];
    }
   
    [self dismissSelectRidersCarAlertView];
}



+ (id)initSelectRidersCarAlertView{
    //loadNibNamed 方法调用了initWithCoder 方法。
    NSArray *array = [[NSBundle mainBundle] loadNibNamed:NSStringFromClass([self class]) owner:self options:nil];
    for (id obj in array) {
        Class classss = object_getClass(obj);
        if ([NSStringFromClass(classss) isEqualToString:NSStringFromClass(self)]) {
            return obj;
        }
    }
    return nil;
}

- (void)showSelectRidersCarAlertViewOn:(id)host{
    
    if ([host isKindOfClass:[UIView class]])
    {
        [host addSubview:self];
        
    }else if ([host isKindOfClass:[UIViewController class]])
    {
        [((UIViewController *)host).view addSubview:self];
    }
    else
    {
        UIWindow *window = [UIApplication sharedApplication].keyWindow;
        [window addSubview:self];
    }
}

-(void)dismissClick
{
    [self dismissSelectRidersCarAlertView];
}

- (void)dismissSelectRidersCarAlertView
{
    [self removeFromSuperview];
}

-(NSMutableArray *)couponArr
{
    if (!_couponArr) {
        _couponArr =[[NSMutableArray alloc]init];
        
    }
    return _couponArr;
}


@end
