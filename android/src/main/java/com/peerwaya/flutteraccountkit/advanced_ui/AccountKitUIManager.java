/*
 * Copyright (c) 2014-present, Facebook, Inc. All rights reserved.
 *
 * You are hereby granted a non-exclusive, worldwide, royalty-free license to use,
 * copy, modify, and distribute this software in source code or binary form for use
 * in connection with the web services and APIs provided by Facebook.
 *
 * As with any software that integrates with the Facebook platform, your use of
 * this software is subject to the Facebook Developer Principles and Policies
 * [http://developers.facebook.com/policy/]. This copyright notice shall be
 * included in all copies or substantial portions of the software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.peerwaya.flutteraccountkit.advanced_ui;

import android.app.Fragment;
import android.os.Parcel;

import com.facebook.accountkit.ui.BaseUIManager;
import com.facebook.accountkit.ui.ButtonType;
import com.facebook.accountkit.ui.LoginFlowState;
import com.facebook.accountkit.ui.TextPosition;
import com.peerwaya.flutteraccountkit.R;

public class AccountKitUIManager extends BaseUIManager {
    private boolean showTrialNeedRegistration;

    public AccountKitUIManager(boolean showTrialNeedRegistration) {
//        super(R.style.MyAppLoginTheme);
//        super(R.style.LoginTheme);
        super(R.style.AppLoginTheme);
        this.showTrialNeedRegistration = showTrialNeedRegistration;
    }

    private AccountKitUIManager(final Parcel source) {
        super(source);
        showTrialNeedRegistration = source.readInt() == 1;
    }

    @Override
    public Fragment getHeaderFragment(final LoginFlowState state) {
        switch (state) {
            case PHONE_NUMBER_INPUT:
                return AccountKitHeaderFragment.newInstance(showTrialNeedRegistration);
            default:
                return null;
        }
    }

    @Override
    public Fragment getBodyFragment(final LoginFlowState state) {
        return null;
    }

    @Override
    public ButtonType getButtonType(final LoginFlowState state) {
        switch (state) {
            case PHONE_NUMBER_INPUT:
            case EMAIL_INPUT:
                if (showTrialNeedRegistration) {
                    return ButtonType.SEND;
                } else {
                    return ButtonType.LOG_IN;
                }
            case CODE_INPUT:
            case CONFIRM_ACCOUNT_VERIFIED:
                return ButtonType.CONFIRM;
            default:
                return null;
        }
    }

    @Override
    public Fragment getFooterFragment(final LoginFlowState state) {
        return null;
    }


    @Override
    public TextPosition getTextPosition(final LoginFlowState state) {
        return TextPosition.ABOVE_BODY;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(showTrialNeedRegistration ? 1 : 0);
    }

    public static final Creator<AccountKitUIManager> CREATOR
            = new Creator<AccountKitUIManager>() {
        @Override
        public AccountKitUIManager createFromParcel(final Parcel source) {
            return new AccountKitUIManager(source);
        }

        @Override
        public AccountKitUIManager[] newArray(final int size) {
            return new AccountKitUIManager[size];
        }
    };
}
