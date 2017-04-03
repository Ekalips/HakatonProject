package com.ekalips.hakatonproject.auth;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ekalips.hakatonproject.R;
import com.ekalips.hakatonproject.databinding.FragmentCreateTeamBinding;
import com.ekalips.hakatonproject.events.SetButtonTextEvent;
import com.ekalips.hakatonproject.networking.ApiInterface;
import com.ekalips.hakatonproject.networking.CallbackOverload;
import com.ekalips.hakatonproject.networking.request_bodies.CreateTeamBody;
import com.ekalips.hakatonproject.networking.response_bodies.CreateTeamResponse;
import com.ekalips.hakatonproject.stuff.StepFragment;
import com.ekalips.hakatonproject.stuff.Utils;
import com.ekalips.hakatonproject.user.User;

import org.greenrobot.eventbus.EventBus;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateTeamFragment extends StepFragment {


    public CreateTeamFragment() {
        // Required empty public constructor
    }

    FragmentCreateTeamBinding binding;

    CreateTeamBody body = new CreateTeamBody();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_create_team, container, false);
        binding.setData(body);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().post(new SetButtonTextEvent(getString(R.string.create_team_fragment_button_text)));
    }

    @Override
    public boolean isToolbarEnabled() {
        return true;
    }

    @Override
    public String getToolbarText() {
        return getString(R.string.create_team_title);
    }

    @Override
    public void tryCompleteStep() {
        ApiInterface.retrofit.create(ApiInterface.class).createTeam(body).enqueue(new CallbackOverload<CreateTeamResponse>() {
            @Override
            public void onSuccess(Call<CreateTeamResponse> call, Response<CreateTeamResponse> response) {
                super.onSuccess(call, response);
                User.getInstance().setRoomToken(response.body().getTeamId());
                onStepResult(true);
                Utils.showToastMessage(R.string.success_create_team);
            }

            @Override
            public void onError(Call<CreateTeamResponse> call, @Nullable Response<CreateTeamResponse> response) {
                super.onError(call, response);
                onStepResult(false);
                Utils.showToastMessage(R.string.error_create_team);
            }

            @Override
            public void onFailure(Call<CreateTeamResponse> call, Throwable t) {
                super.onFailure(call, t);
                onStepResult(false);
                Utils.showToastMessage(R.string.no_connection);
            }
        });
    }
}
