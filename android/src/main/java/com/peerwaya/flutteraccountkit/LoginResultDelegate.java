package com.peerwaya.flutteraccountkit;

import android.content.Intent;

import com.facebook.accountkit.AccountKitLoginResult;

import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;

class LoginResultDelegate implements PluginRegistry.ActivityResultListener {
    private static final String ERROR_LOGIN_IN_PROGRESS = "login_in_progress";

    private MethodChannel.Result pendingResult;


    void setPendingResult(String methodName, MethodChannel.Result result) {
        if (pendingResult != null) {
            sendError(
                    ERROR_LOGIN_IN_PROGRESS,
                    methodName + " called while another Facebook " +
                            "login operation was in progress.",
                    null
            );
        }

        pendingResult = result;
    }


    @Override
    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FlutterAccountKitPlugin.APP_REQUEST_CODE) {
            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            if (loginResult.getError() != null) {
                sendSuccess(LoginResults.error(loginResult.getError()));
            } else if (loginResult.wasCancelled()) {
                sendSuccess(LoginResults.cancelledByUser);
            } else {
                sendSuccess(LoginResults.success(loginResult));
            }
            return true;
        }
        return false;
    }

    private void sendSuccess(Object o) {
        if (pendingResult != null) {
            pendingResult.success(o);
        }
        pendingResult = null;
    }

    private void sendError(String s, String s1, Object o) {
        if (pendingResult != null) {
            pendingResult.error(s, s1, o);
        }
        pendingResult = null;
    }
}