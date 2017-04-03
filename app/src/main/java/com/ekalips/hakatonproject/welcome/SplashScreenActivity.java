package com.ekalips.hakatonproject.welcome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.ekalips.hakatonproject.auth.LoginActivity;
import com.ekalips.hakatonproject.stuff.StepType;
import com.ekalips.hakatonproject.stuff.StepperActivity;
import com.ekalips.hakatonproject.user.User;

public class SplashScreenActivity extends AppCompatActivity {

    public static final String TAG = SplashScreenActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        User user = User.getInstance();
        if (TextUtils.isEmpty(user.getAuthToken())) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra(StepperActivity.EXTRA_STEP,new int[]{StepType.loginTeam.getType(),StepType.loginUser.getType()});
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }
}
