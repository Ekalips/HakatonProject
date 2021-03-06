package com.ekalips.hakatonproject.user;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.ekalips.hakatonproject
        .BR;
import com.ekalips.hakatonproject.MyApplication;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by wldev on 4/3/17.
 */

public class User extends BaseObservable {

    private static final String PREF_NAME = "user_date";
    private static User instance;
    private static SharedPreferences preferences;

    @SerializedName("name")
    @Expose
    @Bindable
    private
    String userName;
    @SerializedName("email")
    @Expose
    @Bindable
    private
    String userEmail;
    @SerializedName("photo")
    @Expose
    @Bindable
    private
    String userPhoto;
    @SerializedName("teamName")
    @Expose
    @Bindable
    private
    String teamName;
    @SerializedName("id")
    @Expose
    @Bindable
    private
    String userId;
    @SerializedName("token")
    @Expose
    private
    String authToken;
    @SerializedName("teamId")
    @Expose
    private
    String roomToken;
    @SerializedName("role")
    @Expose
    private
    int role;


    private User() {
        this(preferences.getString(Fields.name.toString(), ""),
                preferences.getString(Fields.email.toString(), ""),
                preferences.getString(Fields.photo.toString(), ""),
                preferences.getString(Fields.id.toString(), ""),
                preferences.getString(Fields.authToken.toString(), ""),
                preferences.getString(Fields.roomToken.toString(), ""),
                preferences.getString(Fields.teamName.toString(), ""),
                preferences.getInt(Fields.role.toString(), 0));
    }

    private User(String userName, String userEmail, String userPhoto, String userId, String authToken, String roomToken, String teamName, int role) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhoto = userPhoto;
        this.userId = userId;
        this.authToken = authToken;
        this.roomToken = roomToken;
        this.teamName = teamName;
        this.role = role;
    }

    public static User getInstance() {
        if (preferences == null)
            preferences = MyApplication.get().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        if (instance == null)
            instance = new User();
        return instance;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public String getUserId() {
        return userId;
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getBearerToken(){
        return "Bearer " + authToken;
    }

    public String getRoomToken() {
        return roomToken;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
        notifyPropertyChanged(BR.userName);
        preferences.edit().putString(Fields.name.toString(), userName).apply();
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        notifyPropertyChanged(BR.userEmail);
        preferences.edit().putString(Fields.email.toString(), userEmail).apply();
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
        notifyPropertyChanged(BR.userPhoto);
        preferences.edit().putString(Fields.photo.toString(), userPhoto).apply();
    }

    public void setUserId(String userId) {
        this.userId = userId;
        notifyPropertyChanged(BR.userId);
        preferences.edit().putString(Fields.id.toString(), userId).apply();
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
        preferences.edit().putString(Fields.authToken.toString(), authToken).apply();

    }

    public void setRoomToken(String roomToken) {
        this.roomToken = roomToken;
        preferences.edit().putString(Fields.roomToken.toString(), roomToken).apply();
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
        notifyPropertyChanged(BR.teamName);
        preferences.edit().putString(Fields.teamName.toString(), teamName).apply();
    }

    public void setRole(int role) {
        this.role = role;
        notifyPropertyChanged(BR.role);
        preferences.edit().putInt(Fields.role.toString(),role).apply();
    }

    public void clear() {
        preferences.edit().clear().apply();
    }

    public void applyFrom(User user) {
        setRoomToken(user.getRoomToken());
        setAuthToken(user.getAuthToken());
        setTeamName(user.getTeamName());
        setUserEmail(user.getUserEmail());
        setUserId(user.getUserId());
        setUserName(user.getUserName());
        setUserPhoto(user.getUserPhoto());
        setRole(user.getRole());
    }

    public static boolean canAddProjects() {
        return User.getInstance().getRole()==3;
    }

    public static boolean canAssignUser() {
        return User.getInstance().getRole()>=2;
    }

    public int getRole() {
        return role;
    }

    private enum Fields {
        id, name, email, photo, authToken, roomToken, teamName,role;
    }
}
