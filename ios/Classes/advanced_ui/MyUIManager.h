//
//  MyUiManager.h
//  flutter_account_kit
//
//  Created by Sahar Haddad on 10/02/2019.
//

#import <Foundation/Foundation.h>
#import <AccountKit/AccountKit.h>

NS_ASSUME_NONNULL_BEGIN

@interface MyUIManager : NSObject <AKFUIManager>

@property (nonatomic, assign) AKFButtonType confirmButtonType;
@property (nonatomic, assign) AKFButtonType entryButtonType;
@property (nonatomic, copy) AKFTheme *theme;
@property (nonatomic, assign) NSString *firstLine;
@property (nonatomic, assign) NSString *secondLine;

- (instancetype)initWithTheme:(AKFTheme *) theme;

@end

NS_ASSUME_NONNULL_END
