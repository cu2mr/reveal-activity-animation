package com.angelocyj.revealactivityanimation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * 类描述：
 * 创建人：angelo
 * 创建时间：10/19/15 3:48 PM
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private int[] mDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public MyAdapter(int[] myDataSet) {
        mDataSet = myDataSet;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_gallery, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.mImageView = (ImageView) view;

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.mImageView.setImageResource(mDataSet[position]);
        //如果设置了回调，则设置点击事件
        if (mOnItemClickListener != null) {
            holder.mImageView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.mImageView, position);
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return mDataSet.length;
    }

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

}
