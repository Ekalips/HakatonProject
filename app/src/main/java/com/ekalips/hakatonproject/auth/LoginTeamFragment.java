package com.ekalips.hakatonproject.auth;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.databinding.library.baseAdapters.BR;
import com.ekalips.hakatonproject.R;
import com.ekalips.hakatonproject.databinding.FragmentLoginTeamBinding;
import com.ekalips.hakatonproject.events.SetButtonTextEvent;
import com.ekalips.hakatonproject.networking.request_bodies.EnterTeamRequestBody;
import com.ekalips.hakatonproject.stuff.StepFragment;
import com.ekalips.hakatonproject.stuff.Utils;

import org.greenrobot.eventbus.EventBus;

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
        onStepResult(body.getTeam().equals("gg"));

    }

    public static String getTAG() {
        return TAG;
    }
}
