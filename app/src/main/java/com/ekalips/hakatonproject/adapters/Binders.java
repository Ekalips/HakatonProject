package com.ekalips.hakatonproject.adapters;

import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ekalips.hakatonproject.MyApplication;

import java.io.File;

/**
 * Created by wldev on 4/3/17.
 */

public class Binders {
    @BindingAdapter({"android:visibility"})
    public static void setVisibility(View view, boolean isVisible){
        view.setVisibility(isVisible?View.VISIBLE:View.GONE);
    }

    @BindingAdapter({"visibility"})
    public static void setVisibilityApp(View view, boolean isVisible){
        setVisibility(view,isVisible);
    }

    @BindingAdapter({"src"})
    public static void setImageSrc(ImageView view, File file){
        Glide.with(MyApplication.get()).load(file).into(view);
    }
}
