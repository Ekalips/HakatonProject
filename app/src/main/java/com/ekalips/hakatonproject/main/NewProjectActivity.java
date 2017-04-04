package com.ekalips.hakatonproject.main;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ekalips.hakatonproject.R;
import com.ekalips.hakatonproject.databinding.ActivityNewProjectBinding;
import com.ekalips.hakatonproject.networking.ApiInterface;
import com.ekalips.hakatonproject.networking.CallbackOverload;
import com.ekalips.hakatonproject.networking.request_bodies.CreateNewProjRequestBody;
import com.ekalips.hakatonproject.stuff.Utils;
import com.ekalips.hakatonproject.user.User;
import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class NewProjectActivity extends AppCompatActivity {

    ActivityNewProjectBinding binding;
    CreateNewProjRequestBody requestBody = new CreateNewProjRequestBody();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_new_project);
        binding.setData(requestBody);

        binding.setDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });

        binding.createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryToCreate();
            }
        });
    }

    private void tryToCreate() {
        if (!requestBody.isValid()){
            Utils.showToastMessage(R.string.error_fill_data);
            return;
        }

        ApiInterface.retrofit.create(ApiInterface.class).createProject(User.getInstance().getBearerToken(),requestBody).enqueue(new CallbackOverload<ResponseBody>() {
            @Override
            public void onSuccess(Call<ResponseBody> call, Response<ResponseBody> response) {
                super.onSuccess(call, response);
                Utils.showToastMessage(R.string.success_create_proj);
                finish();
            }

            @Override
            public void onError(Call<ResponseBody> call, @Nullable Response<ResponseBody> response) {
                super.onError(call, response);
                Utils.showToastMessage(R.string.error_create_proj);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                super.onFailure(call, t);
                Utils.showToastMessage(R.string.no_connection);
            }
        });
    }

    private void showTimePicker() {
        final SwitchDateTimeDialogFragment dateTimeDialogFragment = SwitchDateTimeDialogFragment.newInstance(
                "Select date and time",
                "OK",
                "Cancel"
        );

        dateTimeDialogFragment.startAtCalendarView();
        dateTimeDialogFragment.set24HoursMode(true);
        dateTimeDialogFragment.setMinimumDateTime(new Date(new Date().getTime()-1000));
        dateTimeDialogFragment.setDefaultDateTime(new GregorianCalendar(2018, Calendar.MARCH, 4, 15, 20).getTime());
        dateTimeDialogFragment.setMaximumDateTime(new GregorianCalendar(2066, Calendar.DECEMBER, 31).getTime());
// Define new day and month format
        try {
            dateTimeDialogFragment.setSimpleDateMonthAndDayFormat(new SimpleDateFormat("dd MMMM", Locale.getDefault()));
        } catch (SwitchDateTimeDialogFragment.SimpleDateMonthAndDayFormatException e) {
            Log.e("DATE_TIME", e.getMessage());
        }

        dateTimeDialogFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Date date) {
                // Date is get on positive button click
                // Do something
                requestBody.setTime(date.getTime());
            }

            @Override
            public void onNegativeButtonClick(Date date) {
                // Date is get on negative button click
            }
        });

        dateTimeDialogFragment.show(getSupportFragmentManager(), "dialog_time");
    }
}
