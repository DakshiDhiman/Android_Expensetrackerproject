package com.example.expensetrackerassigment10;

import android.content.Context;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder> {

    Context context;
    ArrayList<Expense> list;
    DatabaseHelper db;

    public ExpenseAdapter(Context context, ArrayList<Expense> list){

        this.context = context;
        this.list = list;

        db = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.expense_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.title.setText(list.get(position).getTitle());
        holder.amount.setText(list.get(position).getAmount());
        holder.date.setText(list.get(position).getDate());

        holder.itemView.setOnLongClickListener(v -> {

            int result = db.deleteExpense(list.get(position).getId());

            if(result > 0){

                list.remove(position);

                notifyItemRemoved(position);
                notifyItemRangeChanged(position, list.size());

                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
            }

            else{
                Toast.makeText(context, "Not Deleted", Toast.LENGTH_SHORT).show();
            }

            return true;
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, amount, date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.tvTitle);
            amount = itemView.findViewById(R.id.tvAmount);
            date = itemView.findViewById(R.id.tvDate);
        }
    }
}