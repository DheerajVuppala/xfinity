package com.dheeraj.xfinity.binding;

import android.databinding.BindingAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dheeraj.xfinity.utils.ApplicationUtils;

/**
 * Data Binding adapters specific to the app.
 */
public class BindingAdapters {

    @BindingAdapter("setName")
    public static void setName(TextView view, String text){
        view.setText(ApplicationUtils.getTitle(text));
    }

    @BindingAdapter("setDescription")
    public static void setDescription(TextView view, String text){
        view.setText(ApplicationUtils.getDescription(text));
    }

    @BindingAdapter("setLayout")
    public static void setLayout(RecyclerView recyclerView, boolean showGrid){
        if(showGrid)
            recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(),4));
        else
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
    }

    @BindingAdapter("setVisibility")
    public static void setVisibility(TextView textView,boolean visible){
        textView.setVisibility(visible ? View.VISIBLE: View.GONE);
    }
}
