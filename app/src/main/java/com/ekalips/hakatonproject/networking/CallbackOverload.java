package com.ekalips.hakatonproject.networking;

import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;

import com.ekalips.hakatonproject.MyApplication;
import com.ekalips.hakatonproject.R;
import com.ekalips.hakatonproject.networking.excepitons.AuthorizationFailedException;
import com.ekalips.hakatonproject.stuff.Utils;
import com.google.gson.JsonParseException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by wldev on 2/16/17.
 */

public abstract class CallbackOverload<T> implements Callback<T> {
    @Override
    @CallSuper
    final public void onResponse(Call<T> call, Response<T> response) {
        onAnyResponse(call,response);
        if (response.code()==401)
        {
            onAuthenticationFailed(call, new AuthorizationFailedException());
        }
        else if (response.code() >= 500 && response.code() < 600){
            onServerError(call, response);
            onError(call,response);
        }
        else if (response.isSuccessful()){
            onSuccess(call, response);
        }
        else
            onError(call,response);
    }

    public void onAnyResponse(Call<T> call, Response<T> response){

    }

    public void onAuthenticationFailed(Call<T> call, Throwable t){
//        Utils.clearAndRestartOnAuthFailed(MyApplication.get());
    }


    public void onSuccess(Call<T> call, Response<T> response){

    }

    public void onError(Call<T> call,@Nullable Response<T> response){

    }

    private void onServerError(Call<T> call, Response<T> response){
        Utils.showToastMessage(MyApplication.get(), MyApplication.get().getString(R.string.error_server));
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if (t instanceof NumberFormatException || t instanceof JsonParseException){
            Utils.showToastMessage(MyApplication.get(), R.string.error_data_processing);
        }
//        else if (t instanceof IOException){
//            Utils.showToastMessage(MyApplication.get(), R.string.error_no_connection);
//        }
        t.printStackTrace();
    }
}
