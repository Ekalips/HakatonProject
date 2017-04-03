package com.ekalips.hakatonproject.auth;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ekalips.hakatonproject.R;
import com.ekalips.hakatonproject.databinding.ActivityLoginBinding;
import com.ekalips.hakatonproject.stuff.StepperActivity;

public class LoginActivity extends StepperActivity {

    public static final String TAG = LoginActivity.class.getSimpleName();

    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        binding.nextClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendStepTry();
            }
        });

    }


//    @Override
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onStepComplete(StepTryResult result) {
//        super.onStepComplete(result);
//        if (result.isSuccess()){
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.fragmentHolder,new LoginEnterFramgnet(),LoginEnterFramgnet.getTAG())
//                    .commit();
//        }
//        else {
//
//        }
//    }


    @Override
    public void onStepSuccess() {
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.fragmentHolder, (Fragment) stepStack.peek(), ((Fragment) stepStack.pop()).getClass().getSimpleName())
//                .commit();
//        showNextStep();
    }

    @Override
    public void onStepError() {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void setButtonNextText(String text) {
        binding.setButtonText(text);
    }

    @Override
    public void onTaskComplete() {
        Log.d(TAG, "onTaskComplete: ");
    }
}
