package com.project;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TableAdapterEnter extends RecyclerView.Adapter<TableAdapterEnter.ViewHolder> {

    private List<String[]> data;
    private OnItemClickListener onItemClickListener;

    public TableAdapterEnter(List<String[]> data, OnItemClickListener onItemClickListener) {
        this.data = data;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.table_row_enter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String[] rowData = data.get(position);
        holder.column1.setText(rowData[0]);
        holder.column2.setText(rowData[1]);
        holder.column3.setText(rowData[2]);
        if (position == 0) {
            holder.deleteButton.setVisibility(View.INVISIBLE);
        }
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView column1;
        TextView column2;
        TextView column3;
        Button deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            column1 = itemView.findViewById(R.id.column1);
            column2 = itemView.findViewById(R.id.column2);
            column3 = itemView.findViewById(R.id.column3);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }
    }


    public interface OnItemClickListener {

        void onClick(int pos);
    }


}
