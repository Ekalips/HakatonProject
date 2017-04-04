package com.ekalips.hakatonproject.adapters;

import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.bumptech.glide.Glide;
import com.ekalips.hakatonproject.MyApplication;
import com.ekalips.hakatonproject.data.Task;
import com.ekalips.hakatonproject.stuff.DataSetInterface;
import com.ekalips.hakatonproject.stuff.Utils;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.gpu.BrightnessFilterTransformation;

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

    @BindingAdapter({"src","name"})
    public static void setImageSrc(ImageView view, String url,String name){
        bindImageToIv(view,url,name,2);
    }

    @BindingAdapter({"blurImage", "name"})
    public static void setBlurredImage(ImageView view, String imagePath, String name) {
        ColorGenerator generator = ColorGenerator.MATERIAL;
        if (name == null)
            name = "U N";
        int color = alterColor(generator.getColor(name), 0.8f);
        view.setBackgroundColor(color);
        Glide.with(view.getContext().getApplicationContext()).load(imagePath).bitmapTransform(new BlurTransformation(view.getContext()), new BrightnessFilterTransformation(view.getContext(), -0.2f)).into(view);
    }

    private static int alterColor(int color, float factor) {
        int a = (color & (0xFF << 24)) >> 24;
        int r = (int) (((color & (0xFF << 16)) >> 16) * factor);
        int g = (int) (((color & (0xFF << 8)) >> 8) * factor);
        int b = (int) ((color & 0xFF) * factor);
        return Color.argb(a, r, g, b);
    }

    public static void bindImageToIv(ImageView view, String url, String name, int borderW) {
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = name == null ? generator.getRandomColor() : generator.getColor(name);

        if (name == null)
            name = "U N";
        String[] strings = name.split(" ");
        String stringToDisplay = "";
        for (String st :
                strings) {
            if (st.length() > 0)
                stringToDisplay += st.substring(0, 1);
        }
        stringToDisplay = stringToDisplay.toUpperCase();

        TextDrawable.IBuilder builder = TextDrawable.builder()
                .beginConfig()
                .withBorder(borderW)
                .width(120)
                .height(120)
                .endConfig()
                .round();

        TextDrawable drawable = builder.build(stringToDisplay, color);

//        view.setImageDrawable(drawable);
        Glide.with(view.getContext().getApplicationContext()).load(url).dontAnimate().placeholder(drawable).into(view);
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

    @BindingAdapter({"adapter","data", "item"})
    public static void setAdapter(RecyclerView recyclerView,Class<? extends RecyclerView.Adapter> clazz, List data, Task task){
        try {
            Constructor<?> ctor = clazz.getConstructor(task.getClass());
            RecyclerView.Adapter adapter = (RecyclerView.Adapter) ctor.newInstance(task);
            recyclerView.setAdapter(adapter);
            setData(recyclerView, data);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @BindingAdapter({"statusPadding"})
    public static void addStatusBarPadding(View view, boolean add){
        if (add){
            view.setPadding(view.getPaddingLeft(),view.getPaddingTop()+ Utils.getStatusBarHeight(),view.getPaddingRight(),view.getPaddingBottom());
        }
    }
}
