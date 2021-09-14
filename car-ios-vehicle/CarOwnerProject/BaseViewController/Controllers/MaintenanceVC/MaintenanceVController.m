//
//  MaintenanceVController.m
//  CarOwnerProject
//
//  Created by apple on 2021/1/25.
//

#import "MaintenanceVController.h"
#import "CarAllTypeView.h"
#import "CarTypeCollectionViewCell.h"
#import "CarTypeAllTableViewCell.h"
#import "CarCollectionReusableView.h"
#import "CarFootCollectionReusableView.h"
#import "CarFlowLayout.h"
//全部分类左边
#import "QueryListByParentModel.h"
//右边分类
#import "QueryTreeListModel.h"
//右边分类名称
#import "QueryTreeListSubListModel.h"



@interface MaintenanceVController ()<UITableViewDelegate,UITableViewDataSource,UIScrollViewDelegate,UICollectionViewDataSource,UICollectionViewDelegate,UICollectionViewDelegateFlowLayout>

{
    CarFlowLayout *layout;
    NSInteger _selectIndex;//记录位置
    BOOL _isScrollDown;//滚动方向
}

//@property(nonatomic,strong) NSMutableArray *tableTittleDataArray;
@property(nonatomic,strong) NSMutableArray *headTittleDataArray;
//@property(nonatomic,strong)NSMutableArray *productIdArray;

@property(nonatomic,strong) UICollectionView *collectionView;

@property(nonatomic,strong)UITableView *tableView;
@property (nonatomic,strong)UIView *lineView;
//@property (nonatomic,strong)CarAllTypeView  *listV;
@property (nonatomic,assign)BOOL isSelect;
@property (nonatomic,assign)NSInteger currentInt;//当前选中行
@property (nonatomic,assign)NSInteger currentSec;//当前选中分区

@end

@implementation MaintenanceVController
static NSString *cellID = @"CarTypeCollectionViewCell";
static NSString *headerID = @"headerID";
static NSString *footerID = @"footerID";


#define backgrCOlor [UIColor colorWithRed:(245)/255.0 green:(245)/255.0 blue:(245)/255.0 alpha:1]
static NSString * const CarTypeAllTableViewCellIdentify = @"CarTypeAllTableViewCell";//骑车分类


- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    self.navigationItem.title = @"全部分类";
    [self.navigationController.navigationBar setTitleTextAttributes:
         @{NSFontAttributeName:[UIFont systemFontOfSize:18.0f],
           NSForegroundColorAttributeName:Number090909Color}];
    [self.view addSubview:self.lineView];
//    [self.view addSubview:self.listV];
    
    self.currentSec = -1;//单圈选中分区
    self.currentInt = -1;//当前选中行
    
    [self creatUI];
    
    [self requestQueryTreeList];
}


-(void)requestQueryTreeList
{
    NSDictionary *pageBoDict=@{
        
    };
    NSDictionary *headerDict =@{
        @"token":[UserInfo getUserInfo].token,
    };
    //查询父节点所有分组，根节点传-1
    NSString *urlStr = [NSString stringWithFormat:@"%@-1",queryTreeListApi];
    [HTTPManager getRequestWithUrl:urlStr parameters:pageBoDict header:headerDict progress:^(NSProgress * _Nonnull uploadProgress) {
        
    } success:^(NSDictionary * _Nonnull responseObject) {
        DLog(@"全部分类1responseObject===%@",responseObject);
        
        NSArray *dataArr = responseObject[@"data"];
                
        NSArray *commendArr = [QueryTreeListModel mj_objectArrayWithKeyValuesArray:dataArr];
        for (int i=0; i<commendArr.count; i++) {
            QueryTreeListModel *caseModel = commendArr[i];
            if (![caseModel.uuid isEqualToString:@"1006"]) {
                [self.headTittleDataArray addObject:caseModel];
            }
        }
        [self.collectionView reloadData];
        [self.tableView reloadData];
        
    } other:^(NSDictionary * _Nonnull responseObject) {
        
    } failure:^(NSError * _Nonnull error) {
        
    }];
}

-(void)creatUI
{
    _selectIndex = 0;
    _isScrollDown = YES;
    

    
    layout = [CarFlowLayout new];
    //layout.itemSize = CGSizeMake((CIO_SCREEN_WIDTH-78)/4.0, 40);
    //layout.sectionInset = UIEdgeInsetsMake(1, 0, 1, 0);
    layout.naviHeight = 0;
    
    /**
     *  设置滑动方向
     *  UICollectionViewScrollDirectionHorizontal 水平方向
     *  UICollectionViewScrollDirectionVertical   垂直方向
     */
    layout.scrollDirection = UICollectionViewScrollDirectionVertical;
    [self.collectionView registerClass:[CarTypeCollectionViewCell class] forCellWithReuseIdentifier:cellID];
    [self.collectionView registerClass:[CarCollectionReusableView class] forSupplementaryViewOfKind:UICollectionElementKindSectionHeader  withReuseIdentifier:headerID];
    [self.collectionView registerClass:[CarFootCollectionReusableView class] forSupplementaryViewOfKind:UICollectionElementKindSectionFooter  withReuseIdentifier:footerID];
   
    // collectionView 的添加
    [self.view addSubview:self.collectionView];
    
    // tableView 的添加
    [self.view addSubview:self.tableView];

}

#pragma mark == 横线
-(UIView *)lineView
{
    if (!_lineView) {
        _lineView =[[UIView alloc]initWithFrame:CGRectMake(0, NavigationBar_Height, SCREEN_WIDTH, 1)];
        _lineView.backgroundColor = NumberF1F1F1Color;
    }
    return _lineView;
}

- (NSInteger)numberOfSectionsInCollectionView:(UICollectionView *)collectionView
{
    return self.headTittleDataArray.count;
}

- (NSInteger)collectionView:(UICollectionView *)collectionView numberOfItemsInSection:(NSInteger)section
{

    QueryTreeListModel *listModel = self.headTittleDataArray[section];
    return listModel.subListArray.count;
    
}

-(UICollectionViewCell *)collectionView:(UICollectionView *)collectionView cellForItemAtIndexPath:(NSIndexPath *)indexPath
{
    CarTypeCollectionViewCell *cell = [collectionView dequeueReusableCellWithReuseIdentifier:cellID forIndexPath:indexPath];

    QueryTreeListModel *listModel = self.headTittleDataArray[indexPath.section];
    QueryTreeListSubListModel *model = listModel.subListArray[indexPath.row];
    cell.areaName.text = SafeValue([NSString stringWithFormat:@"%@",model.groupName]);
    
    if (self.currentInt == indexPath.row && self.currentSec == indexPath.section) {
        cell.areaName.backgroundColor = RGBColor(35, 147, 224);
        cell.areaName.textColor = NumberFFColor;
    }else
    {
        cell.areaName.backgroundColor = NumberF4F4F4Color;
        cell.areaName.textColor = Number333333Color;
    }
    return cell;
}
-(void)collectionView:(UICollectionView *)collectionView didSelectItemAtIndexPath:(NSIndexPath *)indexPath
{
    DLog(@"点击文本框");

    self.currentSec = indexPath.section;
    self.currentInt = indexPath.row;
    [self.collectionView reloadData];
    SummaryH5ViewController *shopCenterVC =[[SummaryH5ViewController alloc]init];
    QueryTreeListModel *listModel = self.headTittleDataArray[indexPath.section];
    QueryTreeListSubListModel *model = listModel.subListArray[indexPath.row];
    //1011
    
    NSString *areaUuid = [[NSUserDefaults standardUserDefaults]objectForKey:changeCityUuid];
    if ([SafeValue(areaUuid)isEqualToString:@""]) {
        areaUuid=@"793";
    }
    DLog(@"areaUuid===%@",areaUuid);
    
    shopCenterVC.url = [NSString stringWithFormat:@"%@%@?areaUuid=%@&parentUuid=%@&currUuid=%@",HOSTH5_IP,storeH5url,areaUuid,model.parentId,model.uuid];
    DLog(@"shopCenterVC====%@",shopCenterVC.url);
    shopCenterVC.hidesBottomBarWhenPushed = YES;
    [self.navigationController pushViewController:shopCenterVC animated:YES];
}


- (UICollectionReusableView *)collectionView:(UICollectionView *)collectionView viewForSupplementaryElementOfKind:(NSString *)kind atIndexPath:(NSIndexPath *)indexPath {
    
    if (kind==UICollectionElementKindSectionFooter) {
        
        CarFootCollectionReusableView *footer = [collectionView  dequeueReusableSupplementaryViewOfKind:kind withReuseIdentifier:footerID forIndexPath:indexPath];
        footer.backgroundColor = backgrCOlor;
        return footer;
    }

     CarCollectionReusableView *header = [collectionView  dequeueReusableSupplementaryViewOfKind:UICollectionElementKindSectionHeader withReuseIdentifier:headerID forIndexPath:indexPath];
       header.backgroundColor = NumberFFColor;
    
    QueryTreeListModel *model = self.headTittleDataArray[indexPath.section];
    header.headText.text = SafeValue([NSString stringWithFormat:@"%@",model.groupName]);
    
      return header;
}

- (CGSize)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout*)collectionViewLayout referenceSizeForHeaderInSection:(NSInteger)section
{
    return CGSizeMake(0, 44);
}

- (CGSize)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout*)collectionViewLayout referenceSizeForFooterInSection:(NSInteger)section
{
//    if (section == 2) {
//
//       return CGSizeMake(0,8);
//    }

    return CGSizeZero;
}

// 导航栏是否消失
-(BOOL)prefersStatusBarHidden
{
    return NO;
}

//列间距
-(CGFloat)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout *)collectionViewLayout minimumInteritemSpacingForSectionAtIndex:(NSInteger)section{
    
    return 10.f;
}

//行间距
-(CGFloat)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout *)collectionViewLayout minimumLineSpacingForSectionAtIndex:(NSInteger)section{
    
    return 10.f;
}
- (UIEdgeInsets)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout*)collectionViewLayout insetForSectionAtIndex:(NSInteger)section{
    
    return UIEdgeInsetsMake(10, 16, 10,10);
}
/*
 格子的宽高设置
 */
-(CGSize)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout *)collectionViewLayout sizeForItemAtIndexPath:(NSIndexPath *)indexPath
{
    return CGSizeMake((SCREEN_WIDTH-84-36)/2.0, 40);
}
-(UICollectionView *)collectionView{
    
    if (!_collectionView) {
        
        _collectionView = [[UICollectionView alloc]initWithFrame:CGRectMake(84,NavigationBar_Height+1, SCREEN_WIDTH-84, SCREEN_HEIGHT-NavigationBar_Height-SafeAreaBottom_Height-BottomTab_Height-1) collectionViewLayout: layout];
        _collectionView.backgroundColor = NumberFFColor;
        _collectionView.showsHorizontalScrollIndicator = NO;
        _collectionView.showsVerticalScrollIndicator = NO;
        _collectionView.delegate = self;
        _collectionView.dataSource = self;
        _collectionView.alwaysBounceVertical = NO;
        _collectionView.alwaysBounceHorizontal = NO;
        _collectionView.pagingEnabled = NO;
    }
    
    return _collectionView;
}


-(NSMutableArray *)headTittleDataArray{
    
    if (!_headTittleDataArray) {
        _headTittleDataArray = [[NSMutableArray alloc]init];
    }
    return _headTittleDataArray;
}



-(UITableView *)tableView{
    
    if (!_tableView) {
        
        _tableView = [[UITableView alloc]initWithFrame:CGRectMake(0,NavigationBar_Height+1, 84, SCREEN_HEIGHT-NavigationBar_Height-SafeAreaBottom_Height-BottomTab_Height-1)];
        _tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
        //self.tableView.bounces = NO;
        _tableView.delegate = self;
        _tableView.dataSource = self;
        _tableView.backgroundColor  = NumberF4F4F4Color;
        _tableView.showsVerticalScrollIndicator = NO;
        [_tableView registerNib:[UINib nibWithNibName:@"CarTypeAllTableViewCell" bundle:nil] forCellReuseIdentifier:CarTypeAllTableViewCellIdentify];//骑车分类
    }
    
    return _tableView;
}

-(NSInteger)numberOfSectionsInTableView:(UITableView *)tableView{
    
    return 1;
}

-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    
    return self.headTittleDataArray.count;
}

-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
      
    CarTypeAllTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CarTypeAllTableViewCellIdentify];
            
    if (!cell) {
                
        cell = [[CarTypeAllTableViewCell alloc]initWithStyle:UITableViewCellStyleDefault reuseIdentifier:cellID];
        cell.selectionStyle = UITableViewCellSelectionStyleNone;
    }
    
    if (_selectIndex == indexPath.row) {
        cell.nameLab.textColor=Number1691E3Color;
        cell.backgroundColor= NumberFFColor;
        cell.otherImg.hidden = NO;
    }else{
        cell.nameLab.textColor = Number666666Color;
        cell.backgroundColor= NumberF4F4F4Color;

        cell.otherImg.hidden = YES;
    }
    
    QueryTreeListModel *model = self.headTittleDataArray[indexPath.row];
    cell.nameLab.text = model.groupName;
    
    return cell;
}

-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{
 
    return 44;
            
}

// CollectionView分区标题即将展示
- (void)collectionView:(UICollectionView *)collectionView willDisplaySupplementaryView:(UICollectionReusableView *)view forElementKind:(NSString *)elementKind atIndexPath:(NSIndexPath *)indexPath {
    //         当前CollectionView滚动的方向向上，CollectionView是用户拖拽而产生滚动的（主要是判断CollectionView是用户拖拽而滚动的，还是点击TableView而滚动的）
    if (!_isScrollDown && (collectionView.dragging || collectionView.decelerating)) {
        [self selectRowAtIndexPath:indexPath.section];
    }
}
// CollectionView分区标题展示结束
- (void)collectionView:(UICollectionView *)collectionView didEndDisplayingSupplementaryView:(nonnull UICollectionReusableView *)view forElementOfKind:(nonnull NSString *)elementKind atIndexPath:(nonnull NSIndexPath *)indexPath {
    //当前CollectionView滚动的方向向下，CollectionView是用户拖拽而产生滚动的（主要是判断CollectionView是用户拖拽而滚动的，还是点击TableView而滚动的）
    if (_isScrollDown && (collectionView.dragging || collectionView.decelerating)) {
        [self selectRowAtIndexPath:indexPath.section + 1];
    }
}
// 当拖动CollectionView的时候，处理TableView
- (void)selectRowAtIndexPath:(NSInteger)index {

    _selectIndex = index;
    [self.tableView selectRowAtIndexPath:[NSIndexPath indexPathForRow:index inSection:0] animated:YES scrollPosition:UITableViewScrollPositionMiddle];
    [self.tableView reloadData];
}

#pragma mark - UIScrollView Delegate
// 标记一下CollectionView的滚动方向，是向上还是向下
- (void)scrollViewDidScroll:(UIScrollView *)scrollView {
    static float lastOffsetY = 0;
    if (self.collectionView == scrollView) {
        _isScrollDown = lastOffsetY < scrollView.contentOffset.y;
        lastOffsetY = scrollView.contentOffset.y;
    }
}

// 选中 处理collectionView
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    _selectIndex = indexPath.row;
    CGRect headerRect = [self frameForHeaderForSection:_selectIndex];
    CGPoint topOfHeader = CGPointMake(0, headerRect.origin.y - _collectionView.contentInset.top);
    [self.collectionView setContentOffset:topOfHeader animated:YES];
    [self.tableView scrollToRowAtIndexPath:[NSIndexPath indexPathForRow:_selectIndex inSection:0] atScrollPosition:UITableViewScrollPositionTop animated:NO];
     [self.tableView reloadData];
}

- (CGRect)frameForHeaderForSection:(NSInteger)section {
    NSIndexPath *indexPath = [NSIndexPath indexPathForItem:0 inSection:section];
    UICollectionViewLayoutAttributes *attributes = [self.collectionView.collectionViewLayout layoutAttributesForSupplementaryViewOfKind:UICollectionElementKindSectionHeader atIndexPath:indexPath];
    return attributes.frame;
}
   


#pragma mark == 懒加载
/*
 
//商品分类列表
-(CarAllTypeView *)listV
{
    if (!_listV) {
        _listV = [[CarAllTypeView alloc]initWithFrame:CGRectMake(0, NavigationBar_Height+1, 84, self.view.frame.size.height-BottomTab_Height-SafeAreaBottom_Height-NavigationBar_Height)];
    }
    
    return _listV;
}**/

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
