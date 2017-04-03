package com.ekalips.hakatonproject.auth;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.ekalips.hakatonproject.R;
import com.ekalips.hakatonproject.databinding.ActivitySignUpBinding;
import com.ekalips.hakatonproject.main.MainActivity;
import com.ekalips.hakatonproject.stuff.StepperActivity;

public class SignUpActivity extends StepperActivity {

    public static final String TAG = SignUpActivity.class.getSimpleName();


    ActivitySignUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);

        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.nextClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendStepTry();
            }
        });
    }

    @Override
    public void setButtonNextText(String text) {
        binding.setButtonText(text);
    }

    @Override
    public void onStepSuccess() {

    }

    @Override
    public void onStepError() {

    }

    @Override
    public void onTaskComplete() {
        Log.d(TAG, "onTaskComplete: ");
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setToolbarText(String text) {
        Log.d(TAG, "setToolbarText: ");
        binding.setTitle(text);
    }
}
