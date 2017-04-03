package com.ekalips.hakatonproject.adapters;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ekalips.hakatonproject.BR;
import com.ekalips.hakatonproject.R;
import com.ekalips.hakatonproject.data.ProjectListHeader;
import com.ekalips.hakatonproject.stuff.BindingViewHolder;
import com.ekalips.hakatonproject.stuff.DataSetInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ekalips on 4/3/17.
 */

public class ProjectRecyclerViewAdapter extends RecyclerView.Adapter<BindingViewHolder> implements DataSetInterface{
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    List<Object> data = new ArrayList<>();


    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_HEADER: {
                return new BindingViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.rv_item_project_header, parent, false));
            }
            case TYPE_ITEM: {
                return new BindingViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.rv_item_project_item, parent, false));
            }
        }
        throw new IllegalArgumentException("Unrecognized view type: " + viewType);
    }

    @Override
    public void onBindViewHolder(BindingViewHolder holder, int position) {
        holder.getBinding().setVariable(BR.item,data.get(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (data.get(position) instanceof ProjectListHeader)
            return TYPE_HEADER;
        else
            return TYPE_ITEM;
    }

    @Override
    public void setData(ArrayList data) {
        this.data.clear();
        this.data.addAll(data);

        if (data.size()>0)
            notifyItemChanged(0,data.size()-1);
        else
            notifyDataSetChanged();
    }
}
