package com.example.expensemanagement.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanagement.R;

import java.util.List;

import lombok.Setter;

public class SimpleTextViewAdapter extends RecyclerView.Adapter<SimpleTextViewAdapter.SimpleViewHolder> {

    private Context context;
    @Setter
    private List<String> items;

    public SimpleTextViewAdapter(Context context, List<String> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public SimpleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.simple_list_item, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SimpleViewHolder holder, int position) {
        holder.tv_name.setText(items.get(position));

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_name;
        public SimpleViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name_simple_list_item);
        }
    }
}

