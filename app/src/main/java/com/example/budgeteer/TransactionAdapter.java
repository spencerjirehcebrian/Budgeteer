package com.example.budgeteer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.List;


public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.UserViewHolder> {
    private String globalEmail;
    private List<Transactions> listTransaction;
    private TransactionDatabaseHelper databaseHelper;


    public TransactionAdapter(List<Transactions> listTransaction, String globalEmail) {
        this.listTransaction = listTransaction;
        this.globalEmail = globalEmail;
    }
    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transaction_item, parent, false);
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {

        Integer amount = listTransaction.get(position).getAmount();
        holder.card_text.setText(listTransaction.get(position).get_Class());
        holder.date_text.setText(listTransaction.get(position).getDate());
        holder.time_text.setText(listTransaction.get(position).getTime());
        holder.amount_text.setText("+" + amount.toString() + ".00");
    }
    @Override
    public int getItemCount() {
        return listTransaction.size();
    }

    /**
     * ViewHolder class
     */
    public class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView card_text, date_text, time_text, amount_text;

        public UserViewHolder(View itemView) {
            super(itemView);

            card_text = (TextView) itemView.findViewById(R.id.card_text);
            date_text = (TextView) itemView.findViewById(R.id.date_text);
            time_text = (TextView) itemView.findViewById(R.id.time_text);
            amount_text = (TextView) itemView.findViewById(R.id.amount_text);
        }

    }
}
