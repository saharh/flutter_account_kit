//
//  AccountKitViewController.m
//  Runner
//
//  Created by Onyemaechi Okafor on 19/07/2018.
//  Copyright © 2018 The Chromium Authors. All rights reserved.
//

#import "FlutterAccountKitPlugin.h"
#import "advanced_ui/MyUIManager.h"

@implementation FlutterAccountKitViewController
{
    FlutterAccountKitViewController *_instance;
    
    AKFAccountKit *_accountKit;
    UIViewController<AKFViewController> *_pendingLoginViewController;
    NSString *_authorizationCode;
    BOOL *isUserLoggedIn;
    
    FlutterResult _pendingResult;
}

- (instancetype)initWithAccountKit:(AKFAccountKit *)accountKit
{
    self = [super init];
    _accountKit = accountKit;
    
    _pendingLoginViewController = [_accountKit viewControllerForLoginResume];
    _instance = self;
    
    return self;
}

- (void)_prepareLoginViewController:(UIViewController<AKFViewController> *)viewController
{
    viewController.delegate = self;
    if(self.theme != nil) {
        viewController.theme = self.theme;
    }
    if (self.countryWhitelist != nil) {
        viewController.whitelistedCountryCodes = self.countryWhitelist;
    }
    if (self.countryBlacklist != nil) {
        viewController.blacklistedCountryCodes = self.countryBlacklist;
    }
    viewController.defaultCountryCode = self.defaultCountry;
    MyUIManager *uiManager = [[MyUIManager alloc] initWithTheme:self.theme];
    uiManager.confirmButtonType = AKFButtonTypeConfirm;
    uiManager.entryButtonType = [self.buttonType isEqualToString:@"send"] ? AKFButtonTypeSend : AKFButtonTypeLogIn;
    uiManager.firstLine = self.firstLine;
    uiManager.secondLine = self.secondLine;
    viewController.uiManager = uiManager;
    viewController.enableSendToFacebook = true;
    viewController.enableSMS = true;
//    viewController.enableGetACall = true;
    
//    viewController.uiManager = [[AKFSkinManager alloc] initWithSkinType:AKFSkinTypeContemporary primaryColor:[UIColor colorWithRed:0.28 green:0.62 blue:0.33 alpha:1.0]];
}

- (void)loginWithPhone: (FlutterResult)result
{
    if (_pendingResult != nil) {
        [self sendError:@"Request in progress" message:nil details:nil];
    }
    _pendingResult = result;
    NSString *prefillPhone = self.initialPhoneNumber;
    NSString *prefillCountryCode = self.initialPhoneCountryPrefix;
    NSString *inputState = [[NSUUID UUID] UUIDString];
    AKFPhoneNumber * prefillPhoneNumber = [[AKFPhoneNumber alloc] initWithCountryCode:prefillCountryCode phoneNumber:prefillPhone];
    
    dispatch_async(dispatch_get_main_queue(), ^{
        UIViewController<AKFViewController> *viewController = [self->_accountKit viewControllerForPhoneLoginWithPhoneNumber:prefillPhoneNumber state:inputState];
        [self _prepareLoginViewController:viewController];
        UIViewController *rootViewController = [UIApplication sharedApplication].delegate.window.rootViewController;
        [rootViewController presentViewController:viewController animated:YES completion:NULL];
    });
}

- (void)loginWithEmail: (FlutterResult)result;
{
    if (_pendingResult != nil) {
        [self sendError:@"Request in progress" message:nil details:nil];
    }
    NSString *prefillEmail = self.initialEmail;
    NSString *inputState = [[NSUUID UUID] UUIDString];
    
    dispatch_async(dispatch_get_main_queue(), ^{
        UIViewController<AKFViewController> *viewController = [self->_accountKit viewControllerForEmailLoginWithEmail:prefillEmail state:inputState];
        [self _prepareLoginViewController:viewController];
        UIViewController *rootViewController = [UIApplication sharedApplication].delegate.window.rootViewController;
        [rootViewController presentViewController:viewController animated:YES completion:NULL];
    });
}

- (void)viewController:(UIViewController<AKFViewController> *)viewController
didCompleteLoginWithAccessToken:(id<AKFAccessToken>)accessToken
                 state:(NSString *)state
{
    [self sendSuccess:@{
                        @"status" : @"loggedIn",
                        @"accessToken" : [FlutterAccountKitPlugin formatAccessToken: accessToken]
                        }];
}

- (void)viewController:(UIViewController<AKFViewController> *)viewController
didCompleteLoginWithAuthorizationCode:(NSString *)code
                 state:(NSString *)state
{
    [self sendSuccess:@{
                        @"status" : @"loggedIn",
                        @"code" : code,
                        }];
}

- (void)viewWillAppear:(BOOL)animated
{
    // TODO: analyse if this needs to be implemented here or could be handled in React side
}

- (void)viewController:(UIViewController<AKFViewController> *)viewController didFailWithError:(NSError *)error
{
    [self sendSuccess:@{
                        @"status" : @"error",
                        @"errorMessage" : [error description],
                        }];
}

- (void)viewControllerDidCancel:(UIViewController<AKFViewController> *)viewController
{
    [self sendSuccess:@{
                        @"status" : @"cancelledByUser",
                        }];
}

- (void) sendError: (NSString *) code message: (NSString *) message details: (id) details {
    if (_pendingResult != nil) {
        FlutterError *err = [FlutterError errorWithCode:code message:message details:details];
        _pendingResult(err);
    }
    _pendingResult = nil;
}

- (void) sendSuccess: (id) result {
    if (_pendingResult != nil) {
        _pendingResult(result);
    }
    _pendingResult = nil;
}

@end
