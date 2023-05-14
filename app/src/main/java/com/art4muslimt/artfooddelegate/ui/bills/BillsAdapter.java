package com.art4muslimt.artfooddelegate.ui.bills;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.art4muslimt.artfooddelegate.R;
import com.art4muslimt.artfooddelegate.internet.model.BillsModelResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BillsAdapter extends RecyclerView.Adapter<BillsAdapter.MyHolder> {
    Context context;
    List<BillsModelResponse.DataEntity> list;


    public BillsAdapter(@NonNull Context context, List<BillsModelResponse.DataEntity> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bills, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.tvTransFormationId.setText("# " + list.get(position).getOperation_number());
        holder.tvDate.setText(list.get(position).getOperation_date());
        holder.tvPrice.setText(list.get(position).getPrice() + " " + context.getString(R.string.currency));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvTransFormationId)
        TextView tvTransFormationId;
        @BindView(R.id.tvDate)
        TextView tvDate;
        @BindView(R.id.tvPrice)
        TextView tvPrice;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
