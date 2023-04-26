package com.example.budgeteer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BudgetAdapter extends RecyclerView.Adapter<BudgetAdapter.ViewHolder> {

    private CalculatorDatabaseHelper databaseHelper;
    private Budget budget;


    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row


        public TextView card_text, edit_text, max_text, zero_text;
        public ImageView img_card, img_budget_class;
        public ImageButton btn_edit;

        public ContentLoadingProgressBar progress;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            card_text = (TextView) itemView.findViewById(R.id.card_text);
            edit_text = (TextView) itemView.findViewById(R.id.edit_text);
            max_text = (TextView) itemView.findViewById(R.id.max_text);
            zero_text = (TextView) itemView.findViewById(R.id.zero_text);

            img_card = (ImageView) itemView.findViewById(R.id.img_card);
            img_budget_class = (ImageView) itemView.findViewById(R.id.img_budget_class);

            btn_edit = (ImageButton) itemView.findViewById(R.id.btn_edit);
        }

    }

    @Override
    public BudgetAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        databaseHelper = new CalculatorDatabaseHelper(context);
        budget = new Budget();

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.budget_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    private List<Budget> mBudget;

    // Pass in the contact array into the constructor
    public BudgetAdapter(List<Budget> budgets) {
        mBudget = budgets;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(BudgetAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        Budget budget = mBudget.get(position);

        // Set item views based on your views and data model
//        TextView textView = holder.nameTextView;
//        textView.setText(contact.getName());
//
//        Button button = holder.messageButton;
//        button.setText(contact.isOnline() ? "Message" : "Offline");
//        button.setEnabled(contact.isOnline());

        TextView card_text = holder.card_text;
        card_text.setText(budget.get_Class());

        TextView edit_text = holder.edit_text;

        TextView max_text = holder.max_text;
        max_text.setText(budget.getMax());

        TextView zero_text = holder.zero_text;

        ImageView img_card = holder.img_card;
        ImageView img_budget_class = holder.img_budget_class;

        ImageButton btn_edit = holder.btn_edit;

    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mBudget.size();
    }
}