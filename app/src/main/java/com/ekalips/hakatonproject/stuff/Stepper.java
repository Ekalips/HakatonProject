package com.ekalips.hakatonproject.stuff;

import com.ekalips.hakatonproject.events.SetButtonTextEvent;
import com.ekalips.hakatonproject.events.StepTryResult;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by wldev on 4/3/17.
 */

public interface Stepper {


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStepButtonChange(SetButtonTextEvent event);

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStepComplete(StepTryResult result);


    public void sendStepTry();

    public void onTaskComplete();

    public void onStepSuccess();

    public void onStepError();

    public void showNextStep();
}
