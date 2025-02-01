package com.example.expensemanagement.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanagement.Model.ConnectionRequest;
import com.example.expensemanagement.R;

import java.util.List;

public class ConnectionRequestAdapter extends RecyclerView.Adapter<ConnectionRequestAdapter.ConnectionRequestViewHolder> {

    private Context context;
    private List<ConnectionRequest> connectionRequestList;

    private onItemClickListener mlistener;
    public ConnectionRequestAdapter(Context context, List<ConnectionRequest> connectionRequestList) {
        this.context = context;
        this.connectionRequestList = connectionRequestList;
    }


    public interface onItemClickListener{
        void onAcceptClick(int position);
        void onRejectClick(int position);
    }

    public void setOnClickListener(onItemClickListener mlistener){
        this.mlistener = mlistener;
    }

    @NonNull
    @Override
    public ConnectionRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.connection_request_item, parent, false);
        return new ConnectionRequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConnectionRequestViewHolder holder, int position) {

        ConnectionRequest connectionRequest = connectionRequestList.get(position);
        holder.tv_name.setText(connectionRequest.getFromName());
        if(!connectionRequest.isPending()){
            holder.btn_accept.setEnabled(false);
            holder.btn_reject.setEnabled(false);
        }
    }

    @Override
    public int getItemCount() {
        return connectionRequestList.size();
    }


    public void setConnectionRequestList(List<ConnectionRequest> connectionRequestList) {
        this.connectionRequestList = connectionRequestList;
        notifyDataSetChanged();
    }

     class ConnectionRequestViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_name;
        private Button btn_accept;
        private Button btn_reject;
        public ConnectionRequestViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name_request);
            btn_accept = itemView.findViewById(R.id.btn_accept_request);
            btn_reject = itemView.findViewById(R.id.btn_reject_request);

            btn_accept.setOnClickListener(view -> {
                if (mlistener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mlistener.onAcceptClick(position);
                    }
                }
            });

            btn_reject.setOnClickListener(view -> {
                if (mlistener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mlistener.onRejectClick(position);
                    }
                }
            });

        }
    }
}
