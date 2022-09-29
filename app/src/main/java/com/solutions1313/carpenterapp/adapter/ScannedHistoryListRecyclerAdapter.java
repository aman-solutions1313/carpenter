package com.solutions1313.carpenterapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.solutions1313.carpenterapp.R;
import com.solutions1313.carpenterapp.model.DemoModel;

import java.util.ArrayList;

public class ScannedHistoryListRecyclerAdapter extends RecyclerView.Adapter<ScannedHistoryListRecyclerAdapter.RecyclerViewHolder> {

    ArrayList<DemoModel> list;
    Context context;
    public ScannedHistoryListRecyclerAdapter(ArrayList<DemoModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_history, parent, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        DemoModel pojo = list.get(position);
        holder.name.setText(""+pojo.getName());
        if (pojo.getName().equals("Category 1")){
            holder.name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView name;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);

        }
    }


}
