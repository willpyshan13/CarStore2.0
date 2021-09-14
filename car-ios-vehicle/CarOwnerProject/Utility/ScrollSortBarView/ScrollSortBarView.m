//
//  ScrollSortBarView.m
//  CarOwnerProject
//
//  Created by apple on 2021/3/19.
//

#import "ScrollSortBarView.h"
#import "UIButton+Additions.h"

@interface ScrollSortBarView ()

@property (nonatomic, strong) UIView *flagView;
@property (nonatomic, strong) UIView *lineView;

@property (nonatomic, strong) NSMutableArray *btnArray;
@end
@implementation ScrollSortBarView


- (instancetype)initWithFrame:(CGRect)frame item:(NSArray *)array{
    if (self = [super initWithFrame:frame]) {
        [self initUIView];
        if (array) {
            _itemArray = array;
            [self initItemView];
        }
    }
    return self;
}

- (void)initUIView{
    self.backgroundColor = [UIColor whiteColor];
    _scrollView = [[UIScrollView alloc] initWithFrame:(CGRect){0,0,self.width,self.height}];
    _scrollView.backgroundColor = [UIColor whiteColor];
    _scrollView.showsHorizontalScrollIndicator = NO;
    [self addSubview:_scrollView];
}

- (void)initItemView{
    for (id obj in _scrollView.subviews) {
        if ([obj isKindOfClass:[UIButton class]]) {
            [obj removeFromSuperview];
        }
    }
    [_flagView removeFromSuperview];
    _flagView = nil;
    _btnArray = [NSMutableArray array];
    if (_itemArray.count) {
        CGFloat margin = 0;
        CGFloat width = _scrollView.width*85/375;
        __block CGRect rect = CGRectZero;
        [_itemArray enumerateObjectsUsingBlock:^(id  _Nonnull obj, NSUInteger idx, BOOL * _Nonnull stop) {
            NSString *title = @"";
            if ([obj isKindOfClass:[NSString class]]) {
                title = obj;
            }else{
                if ([obj isKindOfClass:[QueryTreeListModel class]]) {
                    title = ((QueryTreeListModel *)obj).groupName;
                }
            }
            
            UIColor *selectedColor = [UIColor colorWithHexStringAndAlpha:@"#090909"];
            UIButton *btn = [UIButton initTextButtonWithTitle:title font:FontSize(16) titleColor:[UIColor colorWithHexStringAndAlpha:@"#090909"] selectedColor:selectedColor highlightColor:nil frame:(CGRect){rect.origin.x + rect.size.width+margin, 0, width, _scrollView.height} tag:idx];
            [btn addTarget:self action:@selector(itemBtnPressed:) forControlEvents:UIControlEventTouchUpInside];
            [_scrollView addSubview:btn];
            rect = btn.frame;
            [_btnArray addObject:btn];
            if (!_lineView) {
                _lineView =[[UIView alloc]initWithFrame:CGRectMake(0, _scrollView.height-1, SCREEN_WIDTH, 1)];
                
                _lineView.backgroundColor = [UIColor clearColor];
                [_scrollView addSubview:_lineView];
            }
            
            if (!_flagView) {
                _flagView = [[UIView alloc] initWithFrame:(CGRect){btn.x,_scrollView.height-2,btn.width*0.4,4}];
                _flagView.centerX = btn.centerX;
                _flagView.backgroundColor = Number1691E3Color;
                [_scrollView addSubview:_flagView];
            }
            
         
            
        }];
        _scrollView.contentSize = (CGSize){rect.origin.x+rect.size.width+margin,_scrollView.height};
    }
}

- (void)itemBtnPressed:(UIButton *)sender{
    if (sender.selected) {
        return;
    }
    //sender.titleLabel.font = [UIFont boldSystemFontOfSize:16];
    //sender.titleLabel.font = FontSize(14);
    for (UIButton *obj in _btnArray) {
        if (obj.tag != sender.tag) {
            obj.selected = NO;
//
        }else{
            obj.selected = YES;
        }
    }
    [UIView animateWithDuration:0.3 animations:^{
        self.flagView.centerX = sender.centerX;
    } completion:^(BOOL finished) {
    }];
    
    if (self.selectItemBlock) {
        QueryTreeListModel *model = _itemArray[sender.tag];
        self.selectItemBlock(model,sender.tag);
    }
    
    if (_btnArray.count > sender.tag + 1) {
        UIButton *nextBtn = _btnArray[sender.tag + 1];
        if (nextBtn.right > _scrollView.width) {
            [_scrollView setContentOffset:(CGPoint){nextBtn.right-_scrollView.width,0} animated:YES];
        }
    }
    if (sender.tag - 1 >= 0) {
        UIButton *preBtn = _btnArray[sender.tag - 1];
        if (_scrollView.contentOffset.x > preBtn.x) {
            [_scrollView setContentOffset:(CGPoint){preBtn.x,0} animated:YES];
        }
    }
}

- (void)setItemArray:(NSArray *)itemArray{
    if (itemArray) {
        _itemArray = itemArray;
        [self initItemView];
    }
}

- (void)updateSelectedItemWithIndex:(NSInteger)index{
    if (_btnArray.count > index) {
        UIButton *button = _btnArray[index];
        
        [self itemBtnPressed:button];
    }
}

- (void)showIndex:(id)obj{
    NSInteger index = -1;
    NSString *title = nil;
    if ([obj isKindOfClass:[NSString class]]) {
        title = obj;
    }else if ([obj isKindOfClass:[NSNumber class]]){
        index = [(NSNumber *)obj integerValue];
    }
    
    [_btnArray enumerateObjectsUsingBlock:^(id  _Nonnull obj, NSUInteger idx, BOOL * _Nonnull stop) {
        UIButton *btn = obj;
        if (title) {
            if ([btn.titleLabel.text isEqualToString:title]) {
                [self itemBtnPressed:btn];
                return ;
            }
        }else if (index > -1){
            if (btn.tag == index) {
                [self itemBtnPressed:btn];
                return ;
            }
        }
    }];
}

- (void)setShowShadow:(BOOL)showShadow{
    _showShadow = showShadow;
    if (_showShadow) {
        self.backgroundColor = [UIColor whiteColor];
        [self.layer setMasksToBounds:NO];
        //添加阴影
        self.layer.shadowColor = [UIColor lightGrayColor].CGColor;
        //阴影偏移量
        self.layer.shadowOffset = (CGSize){2,2};
        //阴影的透明度
        self.layer.shadowOpacity = 1;
    }else{
        self.backgroundColor = [UIColor colorWithHexStringAndAlpha:@"#ffffff,0.4"];
        [self.layer setMasksToBounds:YES];
    }
}
/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/

@end
