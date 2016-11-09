package com.mersens.gank.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mersens.gank.R;
import com.mersens.gank.entity.GankBean;

import java.util.List;

/**
 * Created by Mersens on 2016/11/8.
 */

public class WelfareAdapter extends RecyclerView.Adapter<WelfareAdapter.MyViewHolder> implements View.OnClickListener {
    private List<GankBean> mList;
    private Context context;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;


    public WelfareAdapter(List<GankBean> mList, Context context) {
        this.context = context;
        this.mList = mList;

    }

    public void setList(List<GankBean> mList) {
        this.mList = mList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.walfare_item, parent, false);
        mView.setOnClickListener(this);
        return new MyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        GankBean bean = mList.get(position);
        String str = bean.getCreatedAt();
        String time = str.split("T")[0];
        holder.tv_time.setText(time);
        if(!TextUtils.isEmpty(bean.getUrl())){
            Glide.with(context).load(bean.getUrl()).into(holder.imageView);
        }
        holder.itemView.setTag(position);
    }


    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }



    static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tv_time;

        public MyViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.img);
            tv_time = (TextView) view.findViewById(R.id.tv_time);
        }
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(view, (int)view.getTag());
        }
    }
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    public interface OnRecyclerViewItemClickListener {
        <T> void onItemClick(View view, int pos);
    }
}
