package com.ekalips.hakatonproject.adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ekalips.hakatonproject.BR;
import com.ekalips.hakatonproject.R;
import com.ekalips.hakatonproject.data.HeaderItem;
import com.ekalips.hakatonproject.data.Project;
import com.ekalips.hakatonproject.events.CreateProjectEvent;
import com.ekalips.hakatonproject.stuff.BindingViewHolder;
import com.ekalips.hakatonproject.stuff.ClickAdapter;
import com.ekalips.hakatonproject.stuff.DataSetInterface;
import com.ekalips.hakatonproject.user.User;
import com.google.gson.annotations.Expose;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

/**
 * Created by ekalips on 4/4/17.
 */

public class ProjectsNavigatorRecyclerViewAdapter extends RecyclerView.Adapter<BindingViewHolder> implements DataSetInterface {
    public static final Class clazz = ProjectsNavigatorRecyclerViewAdapter.class;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private List<Object> data = new ArrayList<>();
    private boolean canAdd = false;
    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_HEADER: {
                return new BindingViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.rv_item_projects_navigation_header, parent, false));
            }
            case TYPE_ITEM: {
                return new BindingViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.rv_item_projects_navigation_item, parent, false));
            }
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public void onBindViewHolder(BindingViewHolder holder, int position) {
        holder.getBinding().setVariable(BR.item,data.get(holder.getAdapterPosition()));
        holder.getBinding().setVariable(BR.canAdd,canAdd);
        holder.getBinding().setVariable(BR.addClick, new ClickAdapter() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new CreateProjectEvent());
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        if (data.get(position) instanceof HeaderItem)
            return TYPE_HEADER;
        else
            return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void setData(ArrayList data) {

        canAdd = User.canAddProjects();
        this.data.clear();
        this.data.add(new HeaderItem());
        this.data.addAll(data);

        if (data.size() > 0)
            notifyItemRangeChanged(0, data.size() - 1);
        else
            notifyDataSetChanged();
    }
}
