package com.ly.cc.view.activity.custom.android5p0.recyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ly.cc.R;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by kongbei on 2015/1/11.
 */
public class SampleRecyclerAdapter extends
        RecyclerView.Adapter<SampleRecyclerAdapter.ViewHolder> {
    private final ArrayList<SampleModel> sampleData = SampleModelDao.getSampleData(20);

    @Override
    public int getItemCount() {
        return sampleData.size();
    }

    // 用于创建控件
    @Override
    public SampleRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_basic_item, viewGroup, false);
        return new ViewHolder(view);
    }

    // 为控件设置数据
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        //  获取当前item中显示的数据
        final SampleModel rowData = sampleData.get(i);

        //  设置要显示的数据
        viewHolder.textViewSample.setText(rowData.getSampleText());
        viewHolder.itemView.setTag(rowData);
    }

    //  删除指定的Item
    public void removeData(int position) {
        if (sampleData != null && sampleData.size() > 0) {
            sampleData.remove(position);
            //  通知RecyclerView控件某个Item已经被删除
            notifyItemRemoved(position);
        }
    }

    //  在指定位置添加一个新的Item
    public void addItem(int positionToAdd) {
        if (positionToAdd < 0)
            positionToAdd = 0;

        sampleData.add(positionToAdd, new SampleModel("新的列表项" + new Random().nextInt(10000)));
        //  通知RecyclerView控件插入了某个Item
        notifyItemInserted(positionToAdd);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewSample;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewSample = (TextView) itemView.findViewById(R.id.textViewSample);
        }
    }

}
