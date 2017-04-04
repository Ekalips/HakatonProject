package com.ekalips.hakatonproject.adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ekalips.hakatonproject.BR;
import com.ekalips.hakatonproject.R;
import com.ekalips.hakatonproject.data.HeaderItem;
import com.ekalips.hakatonproject.data.Member;
import com.ekalips.hakatonproject.data.Task;
import com.ekalips.hakatonproject.databinding.ActivitySignUpBinding;
import com.ekalips.hakatonproject.databinding.RvItemTaskMemberBinding;
import com.ekalips.hakatonproject.events.AssignUserToTaskEvent;
import com.ekalips.hakatonproject.stuff.BindingViewHolder;
import com.ekalips.hakatonproject.stuff.ClickAdapter;
import com.ekalips.hakatonproject.stuff.DataSetInterface;
import com.ekalips.hakatonproject.user.User;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ekalips on 4/3/17.
 */

public class TaskMembersRecyclerViewAdapter extends RecyclerView.Adapter<BindingViewHolder> implements DataSetInterface<Member> {

    public static final Class clazz = TaskMembersRecyclerViewAdapter.class;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private List<Object> data = new ArrayList<>();
    private Task task;
    @Override
    public int getItemViewType(int position) {
        if (data.get(position) instanceof Member)
            return TYPE_ITEM;
        else
            return TYPE_HEADER;
    }

    public TaskMembersRecyclerViewAdapter(Task task) {
        this.task = task;
    }

    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_HEADER:{
                return new BindingViewHolder( DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.rv_item_task_member_header, parent, false));
            }
            case TYPE_ITEM:{
                return new BindingViewHolder( DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.rv_item_task_member, parent, false));
            }
            default:throw new IllegalArgumentException();
        }


    }

    @Override
    public void onBindViewHolder(final BindingViewHolder holder, int position) {
        holder.getBinding().setVariable(BR.item, data.get(holder.getAdapterPosition()));
        holder.getBinding().setVariable(BR.onClick, new ClickAdapter() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new AssignUserToTaskEvent(task.getTaskId()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void setData(ArrayList<Member> data) {


        this.data.clear();
        if (User.canAssignUser())
            this.data.add(new HeaderItem());
        this.data.addAll(data);
        if (this.data.size() != 0)
            notifyItemRangeChanged(0, this.data.size() - 1);
        else
            notifyDataSetChanged();
    }
}
