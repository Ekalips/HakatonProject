package com.ekalips.hakatonproject.auth;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ekalips.hakatonproject.R;
import com.ekalips.hakatonproject.databinding.FragmentCreateUserBinding;
import com.ekalips.hakatonproject.events.SetButtonTextEvent;
import com.ekalips.hakatonproject.networking.ApiInterface;
import com.ekalips.hakatonproject.networking.CallbackOverload;
import com.ekalips.hakatonproject.networking.request_bodies.CreateUserBody;
import com.ekalips.hakatonproject.stuff.StepFragment;
import com.ekalips.hakatonproject.stuff.Utils;
import com.ekalips.hakatonproject.user.User;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateUserFragment extends StepFragment {


    public CreateUserFragment() {
        // Required empty public constructor
    }

    FragmentCreateUserBinding binding;

    CreateUserBody body = new CreateUserBody();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_create_user, container, false);
        binding.setData(body);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().post(new SetButtonTextEvent(getString(R.string.create_user_fragment_button_text)));
    }

    @Override
    public boolean isToolbarEnabled() {
        return true;
    }

    @Override
    public String getToolbarText() {
        return getString(R.string.create_user_title);
    }

    @Override
    public void tryCompleteStep() {
        if (!body.isValid()){
            Utils.showToastMessage(R.string.error_fill_data);
            return;
        }

        body.setFirebaseToken(""); //todo yea
        body.setTeamId(User.getInstance().getRoomToken());

        ApiInterface.retrofit.create(ApiInterface.class).register(body).enqueue(new CallbackOverload<User>() {
            @Override
            public void onSuccess(Call<User> call, Response<User> response) {
                super.onSuccess(call, response);
                User.getInstance().applyFrom(response.body());
                User.getInstance().setUserName(body.getName());
                User.getInstance().setUserEmail(body.getEmail());
                onStepResult(true);
            }

            @Override
            public void onError(Call<User> call, @Nullable Response<User> response) {
                super.onError(call, response);
                onStepResult(false);
                Utils.showToastMessage(R.string.error_sign_up);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                super.onFailure(call, t);
                onStepResult(false);
                Utils.showToastMessage(R.string.no_connection);
            }
        });
    }
}
