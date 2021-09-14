//
//  UploadImageTypeViewController.m
//  DaLongInsurance
//
//  Created by 申巧 on 16/7/22.
//  Copyright © 2016年 申巧. All rights reserved.
//

#import "UploadImageTypeViewController.h"
#import "UIView+Frame.h"

@interface UploadImageTypeViewController ()<UIActionSheetDelegate, UINavigationControllerDelegate, UIImagePickerControllerDelegate>
@property (nonatomic, strong) UIActionSheet *actionSheet;
@property (nonatomic, strong) UIAlertController *actionAlert;
@end

@implementation UploadImageTypeViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    
    if (NSFoundationVersionNumber >= NSFoundationVersionNumber_iOS_8_0) {
        _actionAlert = [UIAlertController alertControllerWithTitle:nil message:nil preferredStyle:UIAlertControllerStyleActionSheet];
        UIAlertAction *action1 = [UIAlertAction actionWithTitle:@"拍照" style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
            [self imageSelectedWay:1];
        }];
        UIAlertAction *action2 = [UIAlertAction actionWithTitle:@"从相册选择" style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
            [self imageSelectedWay:2];
        }];
        UIAlertAction *action3 = [UIAlertAction actionWithTitle:@"取消" style:UIAlertActionStyleCancel handler:^(UIAlertAction * _Nonnull action) {
            if (self.view.superview) {
                [self.view removeFromSuperview];
            }
        }];
        if ([UIImagePickerController isSourceTypeAvailable:UIImagePickerControllerSourceTypeCamera]) {
            [_actionAlert addAction:action1];
            [_actionAlert addAction:action2];
            [_actionAlert addAction:action3];
        }else{
            [_actionAlert addAction:action2];
            [_actionAlert addAction:action3];
        }
        
    }else{
#if __IPHONE_OS_VERSION_MIN_REQUIRED < __IPHONE_8_0
        if ([UIImagePickerController isSourceTypeAvailable:UIImagePickerControllerSourceTypeCamera]) {
            _actionSheet = [[UIActionSheet alloc] initWithTitle:nil delegate:self cancelButtonTitle:@"取消" destructiveButtonTitle:nil otherButtonTitles:@"拍照",@"从相册选择", nil];
        }else{
            _actionSheet = [[UIActionSheet alloc] initWithTitle:nil delegate:self cancelButtonTitle:@"取消" destructiveButtonTitle:nil otherButtonTitles:@"从相册选择", nil];
        }
#endif
    }
}

#pragma mark - UIActionSheetDelegate
- (void)actionSheet:(UIActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex{
    if (buttonIndex == actionSheet.numberOfButtons - 1) {
        if (self.view.superview) {
            [self.view removeFromSuperview];
        }
    }
    if ([UIImagePickerController isSourceTypeAvailable:UIImagePickerControllerSourceTypeCamera]) {
        [self imageSelectedWay:buttonIndex+1];
    }else{
        [self imageSelectedWay:buttonIndex+2];
    }
}

#pragma mark - UIImagePickerControllerDelegate
- (void)imagePickerController:(UIImagePickerController *)picker didFinishPickingMediaWithInfo:(NSDictionary<NSString *,id> *)info{
    UIImage *editedImage = [UIImage new];
    editedImage =info[_allowImageEdit?UIImagePickerControllerEditedImage:UIImagePickerControllerOriginalImage];;
//    info[_allowImageEdit?UIImagePickerControllerOriginalImage:UIImagePickerControllerOriginalImage];//UIImagePickerControllerEditedImage裁剪过后的图片:UIImagePickerControllerOriginalImage原始图片
    

    //图片压缩，因为原图都是很大的，不必要传原图
    
    float saleF;
    if (self.isUpLoadHeadImage) {
        saleF = 0.1;
    }else
    {
        saleF = 0.8;
    }
    DLog(@"saleF=====%f",saleF);
    
    UIImage *scaleImage = [self scaleImage:editedImage toScale:saleF];
    NSData *data;
    //以下这两步都是比较耗时的操作，最好开一个HUD提示用户，这样体验会好些，不至于阻塞界面
    if (UIImagePNGRepresentation(scaleImage) == nil) {
        //将图片转换为JPG格式的二进制数据
        data = UIImageJPEGRepresentation(scaleImage, 1);
    } else {
        //将图片转换为PNG格式的二进制数据
        data = UIImagePNGRepresentation(scaleImage);
    }
            //将二进制数据生成UIImage
    
    UIImage *image = [UIImage imageWithData:data];
    
    if (self.selectImageBlock) {
        if (image) {
            self.selectImageBlock(image);
        }
    }
    
    if ([self.imageDelegate respondsToSelector:@selector(selectImageComplete:)]) {
        if (image) {
            [self.imageDelegate performSelector:@selector(selectImageComplete:) withObject:image];
        }
    }
    [picker dismissViewControllerAnimated:YES completion:^{
        if (self.view.superview) {
            [self.view removeFromSuperview];
        }
    }];
}

- (void)imagePickerControllerDidCancel:(UIImagePickerController *)picker{
    [self dismissViewControllerAnimated:YES completion:^{
        if (self.view.superview) {
            [self.view removeFromSuperview];
        }
    }];
}

- (void)imageSelectedWay:(NSInteger)index{
    if (index == 1) {
        UIImagePickerController *imagePicker = [[UIImagePickerController alloc] init];
        imagePicker.sourceType = UIImagePickerControllerSourceTypeCamera;
        imagePicker.delegate = self;
        imagePicker.allowsEditing = _allowImageEdit;
        [self presentViewController:imagePicker animated:YES completion:nil];
    }else if (index == 2){
        UIImagePickerController *imagePicker = [[UIImagePickerController alloc] init];
        imagePicker.sourceType = UIImagePickerControllerSourceTypePhotoLibrary;
        imagePicker.delegate = self;
        imagePicker.allowsEditing = _allowImageEdit;
        [self presentViewController:imagePicker animated:YES completion:nil];
    }
    
}

- (void)showUploadImageView{
    if (self) {
        UIWindow *window = [UIApplication sharedApplication].keyWindow;
        self.view.frame = [UIScreen mainScreen].bounds;
        [window addSubview:self.view];
        if (_actionAlert) {
            [self presentViewController:_actionAlert animated:YES completion:nil];
        }else if (_actionSheet) {
            [_actionSheet showInView:self.view];
        }
        
    }
}


-(UIImage *)scaleImage:(UIImage *)image toScale:(float)scaleSize
{
    UIGraphicsBeginImageContext(CGSizeMake(image.size.width*scaleSize,image.size.height*scaleSize));
    [image drawInRect:CGRectMake(0, 0, image.size.width * scaleSize, image.size.height *scaleSize)];
    UIImage *scaledImage = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    return scaledImage;
}


@end
