//
//  CarTypeCollectionViewCell.m
//  CarOwnerProject
//
//  Created by apple on 2021/1/29.
//

#import "CarTypeCollectionViewCell.h"

@implementation CarTypeCollectionViewCell
-(instancetype)initWithFrame:(CGRect)frame{
    
    self = [super initWithFrame:frame];
    
    if (self) {
        
        [self layout];
    }
    
    return self;
}

-(void)layout{
    
    /*
     *  名字的添加
     */
    int w = (int)self.frame.size.width;
    _areaName = [[UILabel alloc]initWithFrame:CGRectMake(0,0,w, self.frame.size.height)];
    _areaName.backgroundColor = NumberF4F4F4Color;
    _areaName.clipsToBounds = YES;
    _areaName.layer.cornerRadius = 6.0f;
    _areaName.font = [UIFont systemFontOfSize:14.f];
    _areaName.textColor = Number333333Color;
    _areaName.adjustsFontSizeToFitWidth = YES;
    _areaName.textAlignment = NSTextAlignmentCenter;
    [self.contentView addSubview:_areaName];
    
}
@end
