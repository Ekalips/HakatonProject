package com.ekalips.hakatonproject.events;

/**
 * Created by wldev on 4/3/17.
 */

public class StepTryResult {
    private boolean isSuccess;

    public StepTryResult(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public boolean isSuccess() {
        return isSuccess;
    }
}
