package com.ekalips.hakatonproject.stuff;

import com.ekalips.hakatonproject.events.TryToCompleteStep;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by wldev on 4/3/17.
 */

interface Step {
    void onStepResult(boolean isSuccess);


    @Subscribe(threadMode = ThreadMode.MAIN)
    void onStepTryEvent(TryToCompleteStep step);

}
