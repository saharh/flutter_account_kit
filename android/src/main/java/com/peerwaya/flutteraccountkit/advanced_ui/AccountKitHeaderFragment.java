package com.applaudsoft.wabi.virtual_number.views.misc;

import android.app.Fragment;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.applaudsoft.wabi.virtual_number.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccountKitHeaderFragment extends Fragment {

    private static final String SHOW_TRIAL_REQUIRES_SIGNUP = "SHOW_TRIAL_REQUIRES_SIGNUP";

    @BindView(R.id.text1)
    TextView text1;

    @BindView(R.id.text2)
    TextView text2;

    public static AccountKitHeaderFragment newInstance(boolean showTrialNeedRegistration) {
        AccountKitHeaderFragment frag = new AccountKitHeaderFragment();
        Bundle args = new Bundle();
        args.putBoolean(SHOW_TRIAL_REQUIRES_SIGNUP, showTrialNeedRegistration);
        frag.setArguments(args);
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ak_header, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        boolean showTrial = getArguments().getBoolean(SHOW_TRIAL_REQUIRES_SIGNUP);

        if (showTrial) {
            text1.setVisibility(View.VISIBLE);
            text1.setText(R.string.start_trial_please_signup_first);
            text2.setVisibility(View.VISIBLE);
            text2.setText(R.string.please_enter_your_phone_number);
        } else {
            text1.setVisibility(View.VISIBLE);
            text1.setText(R.string.please_enter_your_phone_number);
            text2.setVisibility(View.GONE);
        }
    }

}
