package com.ekalips.hakatonproject.auth;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ekalips.hakatonproject.R;
import com.ekalips.hakatonproject.databinding.FragmentLoginEnterBinding;
import com.ekalips.hakatonproject.events.SetButtonTextEvent;
import com.ekalips.hakatonproject.networking.request_bodies.LoginRequestBody;
import com.ekalips.hakatonproject.stuff.StepFragment;
import com.ekalips.hakatonproject.stuff.Utils;

import org.greenrobot.eventbus.EventBus;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginEnterFramgnet extends StepFragment {


    private static String TAG = LoginEnterFramgnet.class.getSimpleName();

    public LoginEnterFramgnet() {
        // Required empty public constructor
    }


    FragmentLoginEnterBinding binding;
    LoginRequestBody body = new LoginRequestBody();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login_enter, container, false);
        binding.setData(body);

        binding.createAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.startCreateUserProcess(getContext());
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().post(new SetButtonTextEvent(getString(R.string.login_enter_fragment_button_text)));
    }

    @Override
    public void tryCompleteStep() {
        if (!body.getEmail().isEmpty() && !body.getPassword().isEmpty()) {
            onStepResult(true);
        } else
            onStepResult(false);
    }

    public static String getTAG() {
        return TAG;
    }

}
