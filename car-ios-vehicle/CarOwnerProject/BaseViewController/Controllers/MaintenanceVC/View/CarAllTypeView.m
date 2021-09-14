//
//  CarAllTypeView.m
//  CarOwnerProject
//
//  Created by apple on 2021/1/26.
//

#import "CarAllTypeView.h"
#import "CarTypeAllTableViewCell.h"

@interface CarAllTypeView()<UITableViewDelegate,UITableViewDataSource>
@property (nonatomic,strong)UITableView *listTV;
@property (nonatomic,assign)NSInteger currentRow;
@property (nonatomic,strong)NSMutableArray *typeListArray;//商品分类
@end

@implementation CarAllTypeView
static NSString * const CarTypeAllTableViewCellIdentify = @"CarTypeAllTableViewCell";//骑车分类

- (instancetype)initWithFrame:(CGRect)frame{
    if (self = [super initWithFrame:frame]) {
        [self commonInit];
    }
    return self;
}
-(void)commonInit
{
    [self addSubview:self.listTV];
}

#pragma mark ==UITableViewDataSource

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return 40;
}
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    CarTypeAllTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CarTypeAllTableViewCellIdentify forIndexPath:indexPath];//物流配送
    cell.selectionStyle = UITableViewCellSelectionStyleNone;
    if (self.currentRow == indexPath.row) {
        cell.otherImg.hidden = NO;
        cell.nameLab.textColor = Number1691E3Color;
        cell.backgroundColor = NumberFFColor;

    }else
    {
        cell.otherImg.hidden = YES;
        cell.nameLab.textColor = Number666666Color;
        cell.backgroundColor = NumberF4F4F4Color;

    }
    return cell;
}
-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return 44.0f;
}
-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    [tableView deselectRowAtIndexPath:indexPath animated:YES];

    if (self.delegete && [self.delegete respondsToSelector:@selector(didSelectedIndexPath:)]) {
           [self.delegete didSelectedIndexPath:indexPath];
    }
    self.currentRow = indexPath.row;
    [self.listTV reloadData];
}

#pragma mark == 懒加载
-(UITableView *)listTV
{
    if (!_listTV) {
        _listTV =[[UITableView alloc]initWithFrame:CGRectMake(0, 0, 84, self.frame.size.height) style:UITableViewStylePlain];
        _listTV.delegate = self;
        _listTV.dataSource = self;
        _listTV.separatorStyle = UITableViewCellSeparatorStyleNone;
        [_listTV registerNib:[UINib nibWithNibName:@"CarTypeAllTableViewCell" bundle:nil] forCellReuseIdentifier:CarTypeAllTableViewCellIdentify];//骑车分类
        _listTV.backgroundColor = NumberF4F4F4Color;
        _listTV.showsVerticalScrollIndicator = NO;

    }
    return _listTV;
}

//商品分类
-(NSMutableArray *)typeListArray
{
    if (!_typeListArray) {
        _typeListArray =[[NSMutableArray alloc]init];
    }
    return _typeListArray;
}



/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/

@end
