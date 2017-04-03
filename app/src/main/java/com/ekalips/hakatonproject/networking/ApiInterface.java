package com.ekalips.hakatonproject.networking;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wldev on 4/3/17.
 */

public class ApiInterface {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
