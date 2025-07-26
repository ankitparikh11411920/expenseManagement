package com.example.expensemanagement.adapters;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanagement.Model.Expense;
import com.example.expensemanagement.R;
import com.example.expensemanagement.Utils.Util;
import com.example.expensemanagement.enums.Constants;

import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

    private Context context;
    private List<Expense> expenseList;
    private ExpenseAdapter.onItemClickListener mlistener;
    private Enum<Constants> value;

    public ExpenseAdapter(Context context, List<Expense> expenseList, Enum<Constants> value) {
        this.context = context;
        this.expenseList = expenseList;
        this.value = value;
    }

    public interface onItemClickListener {
        void onClick(int position);
    }

    public void setOnItemClickListener(ExpenseAdapter.onItemClickListener mlistener) {
        this.mlistener = mlistener;
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.expense_item, parent, false);
        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        Expense expense = expenseList.get(position);

        if(value.equals(Constants.ALLDETAILS)){
            holder.tv_date.setVisibility(View.VISIBLE);
            holder.tv_description.setVisibility(View.VISIBLE);

            holder.tv_date.setText(expense.getDate());
            holder.tv_description.setText(expense.getDescription());
        }
        holder.tv_categoryName.setText(expense.getCategoryName());
        holder.tv_amount.setText(expense.getAmount());

    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    public float getTotal(){
        float total = 0;
        for(Expense expense: expenseList){
            total += Float.parseFloat(expense.getAmount());
        }
        return total;
    }
    public void setExpenseList(List<Expense> expenseList) {

        this.expenseList = expenseList;
        notifyDataSetChanged();
    }

    class ExpenseViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        private TextView tv_categoryName;
        private TextView tv_amount;
        private TextView tv_date;
        private TextView tv_description;

        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_categoryName = itemView.findViewById(R.id.tv_name_expense_item);
            tv_amount = itemView.findViewById(R.id.tv_amount_expense_item);
            tv_date= itemView.findViewById(R.id.tv_date_expense_item);
            tv_description = itemView.findViewById(R.id.tv_description_expense_item);
            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnClickListener(view -> {
                if (mlistener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mlistener.onClick(position);
                    }
                }
            });
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            Util.createUpdateDeleteContextMenu(contextMenu, getAdapterPosition());
        }
    }
}
