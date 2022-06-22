package com.cn.test.viewmodel;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cn.test.R;
import com.cn.test.util.LogFileUtil;

import java.util.List;

public class TemperAdapter extends  RecyclerView.Adapter {

    private static final String TAG = "TemperAdapter";
    private List<TemperInfo> mList;
    public TemperAdapter(List<TemperInfo> list) {
        super();
        mList = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder, viewType: " + viewType);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.showtemper_list, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new TempertureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TempertureViewHolder mTempertureViewHolder = (TempertureViewHolder) holder;
        mTempertureViewHolder.mTimeTextView.setText(mList.get(position).getTime());
        mTempertureViewHolder.mTemperture.setText(mList.get(position).getTemperture());
        mTempertureViewHolder.mTemperture2.setText(mList.get(position).getTemperture2());
        LogFileUtil.saveLog(position+": "+mList.get(position).getTime()+mList.get(position).getTemperture()+mList.get(position).getTemperture2());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    class TempertureViewHolder extends RecyclerView.ViewHolder
    {
        public TextView mTimeTextView;
        public TextView mTemperture;
        public TextView mTemperture2;

        public TempertureViewHolder(View itemView) {
            super(itemView);
            mTimeTextView = (TextView) itemView.findViewById(R.id.id_tempertime);
            mTemperture = (TextView) itemView.findViewById(R.id.id_temper1);
            mTemperture2 = (TextView) itemView.findViewById(R.id.id_temper2);
        }


    }
}
