package com.ekalips.hakatonproject.stuff;

import com.ekalips.hakatonproject.auth.CreateTeamFragment;
import com.ekalips.hakatonproject.auth.CreateUserFragment;
import com.ekalips.hakatonproject.auth.LoginEnterFramgnet;
import com.ekalips.hakatonproject.auth.LoginTeamFragment;
import com.ekalips.hakatonproject.auth.SetPhotoFragment;

/**
 * Created by wldev on 4/3/17.
 */

public enum StepType {
    loginTeam(0, new LoginTeamFragment()),
    loginUser(1, new LoginEnterFramgnet()),
    createTeam(2, new CreateTeamFragment()),
    setTeamPhoto(3, SetPhotoFragment.newInstance(SetPhotoFragment.ROLE_TEAM)),
    createUser(4, new CreateUserFragment()),
    setUserPhoto(5, SetPhotoFragment.newInstance(SetPhotoFragment.ROLE_USER));

    Step stepFragment;
    int type;

    StepType(int i, Step stepFragment) {
        this.stepFragment = stepFragment;
        this.type = i;
    }

    public Step getStepFragment() {
        return stepFragment;
    }

    public int getType() {
        return type;
    }

    public static StepType fromInt(int i) {
        for (StepType stepType :
                StepType.values()) {
            if (stepType.getType() == i)
                return stepType;
        }
        throw new IllegalArgumentException("Can't fine this step type");
    }
}
