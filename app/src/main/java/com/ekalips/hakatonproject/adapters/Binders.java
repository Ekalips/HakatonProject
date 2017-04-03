package com.ekalips.hakatonproject.adapters;

import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ekalips.hakatonproject.MyApplication;
import com.ekalips.hakatonproject.stuff.DataSetInterface;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

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

    @BindingAdapter({"src"})
    public static void setImageSrc(ImageView view, String url){
        Glide.with(MyApplication.get()).load(url).into(view);
    }

    @BindingAdapter({"data"})
    public static void setData(RecyclerView recyclerView, List data){
        if (data!=null && recyclerView.getAdapter()!=null && recyclerView.getAdapter() instanceof DataSetInterface){
            ((DataSetInterface)recyclerView.getAdapter()).setData(new ArrayList(data));
        }
    }

    @BindingAdapter(("adapter"))
    public static void setAdapter(RecyclerView recyclerView,Class<? extends RecyclerView.Adapter> clazz){
        try {
            Constructor<?> ctor = clazz.getConstructor();
            RecyclerView.Adapter adapter = (RecyclerView.Adapter) ctor.newInstance();
            recyclerView.setAdapter(adapter);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @BindingAdapter({"adapter","data"})
    public static void setAdapter(RecyclerView recyclerView,Class<? extends RecyclerView.Adapter> clazz, List data){
        try {
            Constructor<?> ctor = clazz.getConstructor();
            RecyclerView.Adapter adapter = (RecyclerView.Adapter) ctor.newInstance();
            recyclerView.setAdapter(adapter);
            setData(recyclerView, data);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
