package com.peerwaya.flutteraccountkit.advanced_ui;

import android.app.Fragment;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.peerwaya.flutteraccountkit.R;

public class AccountKitHeaderFragment extends Fragment {

    private static final String KEY_FIRST_LINE = "KEY_FIRST_LINE";
    private static final String KEY_SECOND_LINE = "KEY_SECOND_LINE";

    TextView text1;
    TextView text2;

    public static AccountKitHeaderFragment newInstance(String firstLine, String secondLine) {
        AccountKitHeaderFragment frag = new AccountKitHeaderFragment();
        Bundle args = new Bundle();
        args.putString(KEY_FIRST_LINE, firstLine);
        args.putString(KEY_SECOND_LINE, secondLine);
        frag.setArguments(args);
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ak_header, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        text1 = view.findViewById(R.id.text1);
        text2 = view.findViewById(R.id.text2);
        String firstLine = getArguments().getString(KEY_FIRST_LINE);
        String secondLine = getArguments().getString(KEY_SECOND_LINE);
        if (!TextUtils.isEmpty(firstLine)) {
            text1.setVisibility(View.VISIBLE);
            text1.setText(firstLine);
        } else {
            text1.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(secondLine)) {
            text2.setVisibility(View.VISIBLE);
            text2.setText(secondLine);
        } else {
            text2.setVisibility(View.GONE);
        }
//        if (showTrial) {
//            text1.setVisibility(View.VISIBLE);
//            text1.setText(R.string.start_trial_please_signup_first);
//            text2.setVisibility(View.VISIBLE);
//            text2.setText(R.string.please_enter_your_phone_number);
//        } else {
//            text1.setVisibility(View.VISIBLE);
//            text1.setText(R.string.please_enter_your_phone_number);
//            text2.setVisibility(View.GONE);
//        }
    }

}
