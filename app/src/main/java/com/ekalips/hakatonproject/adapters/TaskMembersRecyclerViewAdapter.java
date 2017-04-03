package com.ekalips.hakatonproject.adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ekalips.hakatonproject.BR;
import com.ekalips.hakatonproject.R;
import com.ekalips.hakatonproject.data.Member;
import com.ekalips.hakatonproject.databinding.RvItemTaskMemberBinding;
import com.ekalips.hakatonproject.stuff.BindingViewHolder;
import com.ekalips.hakatonproject.stuff.DataSetInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ekalips on 4/3/17.
 */

public class TaskMembersRecyclerViewAdapter extends RecyclerView.Adapter<BindingViewHolder<RvItemTaskMemberBinding>> implements DataSetInterface<Member>{

    public static final Class clazz = TaskMembersRecyclerViewAdapter.class;

    private List<Object> data = new ArrayList<>();

    @Override
    public BindingViewHolder<RvItemTaskMemberBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BindingViewHolder<>((RvItemTaskMemberBinding) DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.rv_item_task_member,parent,false));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<RvItemTaskMemberBinding> holder, int position) {
        holder.getBinding().setVariable(BR.item,data.get(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void setData(ArrayList<Member> data) {
        this.data.clear();
        this.data.addAll(data);
        if (data.size()!=0)
            notifyItemRangeChanged(0,data.size()-1);
        else
            notifyDataSetChanged();
    }
}
