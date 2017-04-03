package com.ekalips.hakatonproject.auth;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ekalips.hakatonproject.R;
import com.ekalips.hakatonproject.databinding.FragmentLoginEnterBinding;
import com.ekalips.hakatonproject.events.SetButtonTextEvent;
import com.ekalips.hakatonproject.networking.ApiInterface;
import com.ekalips.hakatonproject.networking.CallbackOverload;
import com.ekalips.hakatonproject.networking.request_bodies.LoginRequestBody;
import com.ekalips.hakatonproject.stuff.StepFragment;
import com.ekalips.hakatonproject.stuff.Utils;
import com.ekalips.hakatonproject.user.User;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Response;

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
        if (!body.isValid()){
            Utils.showToastMessage(R.string.error_fill_data);
            return;
        }

        body.setFirebaseToken(""); //// TODO: 4/3/17 Implement firebase :)

        body.setTeamToken(User.getInstance().getRoomToken());

        ApiInterface.retrofit.create(ApiInterface.class).login(body).enqueue(new CallbackOverload<User>() {
            @Override
            public void onSuccess(Call<User> call, Response<User> response) {
                super.onSuccess(call, response);
                User.getInstance().applyFrom(response.body());
                onStepResult(true);
            }

            @Override
            public void onError(Call<User> call, @Nullable Response<User> response) {
                super.onError(call, response);
                Utils.showToastMessage(R.string.error_login);
                onStepResult(false);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                super.onFailure(call, t);
                Utils.showToastMessage(R.string.no_connection);
                onStepResult(false);
            }
        });
    }

    public static String getTAG() {
        return TAG;
    }

}
