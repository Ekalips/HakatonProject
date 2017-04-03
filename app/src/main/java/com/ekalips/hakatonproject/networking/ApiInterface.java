package com.ekalips.hakatonproject.networking;

import com.ekalips.hakatonproject.networking.request_bodies.CreateTeamBody;
import com.ekalips.hakatonproject.networking.request_bodies.CreateUserBody;
import com.ekalips.hakatonproject.networking.request_bodies.LoginRequestBody;
import com.ekalips.hakatonproject.networking.response_bodies.CreateTeamResponse;
import com.ekalips.hakatonproject.user.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by wldev on 4/3/17.
 */

public interface ApiInterface {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://divinumteam.com:3000/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @POST("teams")
    Call<CreateTeamResponse> createTeam(@Body CreateTeamBody body);

    @GET("teams/")
    Call<CreateTeamResponse> checkForTeam(@Query("team") String teamName);

    @POST("auth/login")
    Call<User> login(@Body LoginRequestBody teamName);

    @POST("auth/register")
    Call<User> register(@Body CreateUserBody body);
}
