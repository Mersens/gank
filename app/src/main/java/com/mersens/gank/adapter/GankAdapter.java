package com.mersens.gank.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mersens.gank.R;
import com.mersens.gank.entity.GankBean;

import java.util.List;

/**
 * Created by Mersens on 2016/11/8.
 */

public class GankAdapter extends RecyclerView.Adapter<GankAdapter.MyViewHolder> implements View.OnClickListener{
    private List<GankBean> mList;
    private Context context;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public GankAdapter(List<GankBean> mList, Context context) {
        this.context = context;
        this.mList = mList;

    }

    public void setList(List<GankBean> mList) {
        this.mList = mList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        mView.setOnClickListener(this);
        return new MyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        GankBean bean = mList.get(position);
        String str = bean.getCreatedAt();
        String time = str.split("T")[0];
        holder.tv_content.setText(bean.getDesc());
        holder.tv_name.setText(bean.getWho());
        holder.tv_time.setText(time);
        holder.itemView.setTag(bean.getUrl());
        holder.tv_content.setMovementMethod(LinkMovementMethod.getInstance());
    }


    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_content;
        TextView tv_name;
        TextView tv_time;

        public MyViewHolder(View view) {
            super(view);
            tv_content = (TextView) view.findViewById(R.id.tv_content);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_time = (TextView) view.findViewById(R.id.tv_time);
        }
    }
    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(view, (String)view.getTag());
        }
    }
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    public interface OnRecyclerViewItemClickListener {
         void onItemClick(View view, String url);
    }
}
