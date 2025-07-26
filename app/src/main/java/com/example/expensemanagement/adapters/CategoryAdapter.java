package com.example.expensemanagement.adapters;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanagement.Utils.Util;

import java.util.List;

import lombok.Setter;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    public interface OnItemLongClickListener {
        void onItemLongClick(int position);
    }

    @Setter
    private List<String> categoryList;

    private final Context context;

    private final OnItemLongClickListener onItemLongClickListener;

    public CategoryAdapter(Context context, List<String> categoryList, OnItemLongClickListener onItemLongClickListener) {
        this.context = context;
        this.categoryList = categoryList;
        this.onItemLongClickListener = onItemLongClickListener;

    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView textView;

        public CategoryViewHolder(View v) {
            super(v);
            textView = v.findViewById(android.R.id.text1);
            v.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            Util.createUpdateDeleteContextMenu(contextMenu, getAdapterPosition());
        }
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new CategoryViewHolder(v);
    }


    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        holder.textView.setText(categoryList.get(position));
        holder.itemView.setOnLongClickListener(v -> {
            onItemLongClickListener.onItemLongClick(holder.getAdapterPosition());
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}
