//
//  MyUiManager.m
//  flutter_account_kit
//
//  Created by Sahar Haddad on 10/02/2019.
//

#import "MyUIManager.h"
#import "PlaceholderView.h"

@implementation MyUIManager
{
    id<AKFActionController> _actionController;
}

- (instancetype)initWithTheme:(AKFTheme *) theme
{
    self = [super init];
    if (self) {
        _theme = theme;
    }
    return self;
}

- (void)setActionController:(id<AKFAdvancedUIActionController>)actionController
{
    _actionController = actionController;
}

- (AKFButtonType)buttonTypeForState:(AKFLoginFlowState)state
{
    switch (state) {
        case AKFLoginFlowStatePhoneNumberInput:
        case AKFLoginFlowStateEmailInput:
            return self.entryButtonType;
        case AKFLoginFlowStateCodeInput:
        case AKFLoginFlowStateVerified:
            return self.confirmButtonType;
        default:
            return AKFButtonTypeDefault;
    }
}

- (UIView *)headerViewForState:(AKFLoginFlowState)state {
    if (state != AKFLoginFlowStatePhoneNumberInput) {
        return nil;
    }
    NSString *text;
    if (self.secondLine != nil) {
        text = [NSString stringWithFormat:@"%@\n\n%@", self.firstLine, self.secondLine];
    } else {
        text = self.firstLine;
    }
    PlaceholderView *view = [[PlaceholderView alloc] initWithFrame:CGRectZero];
    view.intrinsicHeight = 180;
    view.text = text;
    [view setContentInset:UIEdgeInsetsMake(30, 30, 30, 30)];
    return view;
}

@end
