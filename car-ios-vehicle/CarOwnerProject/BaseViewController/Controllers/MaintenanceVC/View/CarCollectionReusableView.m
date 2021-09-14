//
//  CarCollectionReusableView.m
//  CarOwnerProject
//
//  Created by apple on 2021/1/29.
//

#import "CarCollectionReusableView.h"


@implementation CarCollectionReusableView

-(instancetype)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self)
    {
 
        _headText = [[UILabel alloc]initWithFrame:CGRectMake(12, 0, self.frame.size.width-84, 40)];
        _headText.textAlignment = NSTextAlignmentLeft;
        
        _headText.textColor = Number090909Color;
        _headText.font = [UIFont boldSystemFontOfSize:14];
        [self addSubview:_headText];
        
        
        /*
         *  图片的添加
         */
        UIImage *backImg = kImageNamed(@"my_arrow_right");
        
        _iconImage = [[UIImageView alloc]initWithFrame:CGRectMake(SCREEN_WIDTH-14-backImg.size.width-84, (self.frame.size.height - backImg.size.height)/2, backImg.size.width, backImg.size.height)];
        _iconImage.contentMode = UIViewContentModeScaleAspectFill;
        _iconImage.layer.cornerRadius = 4;
        _iconImage.clipsToBounds = YES;
        _iconImage.alpha = 1;
        _iconImage.image = backImg;
        [self addSubview:_iconImage];
        
    }
    return self;
}

@end
