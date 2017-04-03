package com.ekalips.hakatonproject.auth;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.databinding.library.baseAdapters.BR;
import com.ekalips.hakatonproject.R;
import com.ekalips.hakatonproject.databinding.FragmentLoginTeamBinding;
import com.ekalips.hakatonproject.events.SetButtonTextEvent;
import com.ekalips.hakatonproject.networking.ApiInterface;
import com.ekalips.hakatonproject.networking.CallbackOverload;
import com.ekalips.hakatonproject.networking.request_bodies.EnterTeamRequestBody;
import com.ekalips.hakatonproject.networking.response_bodies.CreateTeamResponse;
import com.ekalips.hakatonproject.stuff.StepFragment;
import com.ekalips.hakatonproject.stuff.Utils;
import com.ekalips.hakatonproject.user.User;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginTeamFragment extends StepFragment {

    public static final String TAG = LoginTeamFragment.class.getSimpleName();

    public LoginTeamFragment() {
        // Required empty public constructor
    }

    FragmentLoginTeamBinding binding;

    EnterTeamRequestBody body = new EnterTeamRequestBody();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login_team, container, false);
        binding.setVariable(BR.data,body);

        binding.createTeamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.startCreateTeamProcess(getContext());
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().post(new SetButtonTextEvent(getString(R.string.login_team_fragment_button_text)));
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void tryCompleteStep() {
        ApiInterface.retrofit.create(ApiInterface.class).checkForTeam(body.getTeam()).enqueue(new CallbackOverload<CreateTeamResponse>() {
            @Override
            public void onSuccess(Call<CreateTeamResponse> call, Response<CreateTeamResponse> response) {
                super.onSuccess(call, response);
                User.getInstance().setRoomToken(response.body().getTeamId());
                onStepResult(true);
            }

            @Override
            public void onError(Call<CreateTeamResponse> call, @Nullable Response<CreateTeamResponse> response) {
                super.onError(call, response);
                onStepResult(false);
                Utils.showToastMessage(R.string.error_check_team);
            }

            @Override
            public void onFailure(Call<CreateTeamResponse> call, Throwable t) {
                super.onFailure(call, t);
                onStepResult(false);
                Utils.showToastMessage(R.string.no_connection);
            }
        });

    }

    public static String getTAG() {
        return TAG;
    }
}
