#import <UIKit/UIKit.h>

@interface PlaceholderView : UIView

@property (nonatomic, assign) UIEdgeInsets contentInset;
@property (nonatomic, assign) CGFloat intrinsicHeight;
@property (nonatomic, copy) NSString *text;

@end
