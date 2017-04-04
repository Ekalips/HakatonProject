package com.ekalips.hakatonproject.networking.request_bodies;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.ekalips.hakatonproject.BR;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ekalips on 4/4/17.
 */

public class CreateNewProjRequestBody extends BaseObservable{
    @SerializedName("title")
    @Expose
    private String title = "";
    @SerializedName("description")
    @Expose
    private String descr = "";
    @SerializedName("deadline")
    @Expose
    @Bindable
    private long time = 0;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
        notifyPropertyChanged(BR.time);
    }

    public boolean isValid() {
        return (time != 0 && !descr.isEmpty() && !title.isEmpty()) ;
    }
}
