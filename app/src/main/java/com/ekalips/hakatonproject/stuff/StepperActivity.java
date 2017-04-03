package com.ekalips.hakatonproject.stuff;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.ekalips.hakatonproject.R;
import com.ekalips.hakatonproject.events.SetButtonTextEvent;
import com.ekalips.hakatonproject.events.SetToolbarTextEvent;
import com.ekalips.hakatonproject.events.StepTryResult;
import com.ekalips.hakatonproject.events.TryToCompleteStep;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Stack;

/**
 * Created by wldev on 4/3/17.
 */

public abstract class StepperActivity extends AppCompatActivity implements Stepper {
    private static final String TAG = StepperActivity.class.getSimpleName();
    protected Stack<Step> stepStack = new Stack<>();

    public final static String EXTRA_STEP = "step_extra";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int[] steps = getIntent().getIntArrayExtra(EXTRA_STEP);
        if (steps == null || steps.length == 0) {
            throw new IllegalArgumentException("Stepper activity must contain at least one step");
        }

        for (int i = steps.length - 1; i >= 0; i--) {
            stepStack.add(StepType.fromInt(steps[i]).getStepFragment());
        }

        showNextStep();
    }


    @Override
    @CallSuper
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    @CallSuper
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @CallSuper
    @Override
    public void sendStepTry() {
        EventBus.getDefault().post(new TryToCompleteStep());
    }

    @CallSuper
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStepComplete(StepTryResult result) {
        if (result.isSuccess() && stepStack.size() == 0)
            onTaskComplete();
        else {
            if (result.isSuccess()) {
                onStepSuccess();
                showNextStep();
            } else
                onStepError();
        }
    }

    public abstract void setButtonNextText(String text);


    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStepButtonChange(SetButtonTextEvent event) {
        setButtonNextText(event.getText());
    }

    @Override
    public abstract void onStepSuccess();

    @Override
    public abstract void onStepError();

    @Override
    public void showNextStep() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentHolder, (Fragment) stepStack.peek(), ((Fragment) stepStack.pop()).getClass().getSimpleName())
                .commit();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSetToolbarTextEvent(SetToolbarTextEvent event){
        setToolbarText(event.getText());
    }

    public void setToolbarText(String text){

    }
}
