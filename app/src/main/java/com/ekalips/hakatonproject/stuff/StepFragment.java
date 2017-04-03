package com.ekalips.hakatonproject.stuff;

import android.support.annotation.CallSuper;
import android.support.v4.app.Fragment;

import com.ekalips.hakatonproject.events.SetToolbarTextEvent;
import com.ekalips.hakatonproject.events.StepTryResult;
import com.ekalips.hakatonproject.events.TryToCompleteStep;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by wldev on 4/3/17.
 */

public abstract class StepFragment extends Fragment implements Step{


    private static final String TAG = StepFragment.class.getSimpleName();

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);

        if (isToolbarEnabled())
            EventBus.getDefault().post(new SetToolbarTextEvent(getToolbarText()));
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @CallSuper
    @Override
    public void onStepResult(boolean isSuccess) {
        EventBus.getDefault().post(new StepTryResult(isSuccess));
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStepTryEvent(TryToCompleteStep step){
        tryCompleteStep();
    }

    public abstract void tryCompleteStep();

    public boolean isToolbarEnabled(){
        return false;
    }

    public String getToolbarText(){
        return "";
    }
}
