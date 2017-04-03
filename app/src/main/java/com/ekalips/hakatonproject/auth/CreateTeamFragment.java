package com.ekalips.hakatonproject.auth;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ekalips.hakatonproject.R;
import com.ekalips.hakatonproject.databinding.FragmentCreateTeamBinding;
import com.ekalips.hakatonproject.events.SetButtonTextEvent;
import com.ekalips.hakatonproject.networking.request_bodies.CreateTeamBody;
import com.ekalips.hakatonproject.stuff.StepFragment;

import org.greenrobot.eventbus.EventBus;

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
        onStepResult(body.isValid());
    }
}
