package com.example.budgeteer;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class BudgetAdapter2 extends RecyclerView.Adapter<BudgetAdapter2.UserViewHolder> {
    private String globalEmail;
    private List<Budget> listBudget;

    private Bundle bundle2;

    private Budget budget;

    private Bundle bundle;
    CalculatorActivity cls2 = new CalculatorActivity();
    private CalculatorDatabaseHelper databaseHelper;
    private TransactionDatabaseHelper databaseHelper2;
    private Transactions transactions;


    private final ClickListener listener;
    public BudgetAdapter2(List<Budget> listBudget, ClickListener listener, String globalEmail) {
        this.listBudget = listBudget;
        this.listener = listener;
        this.globalEmail = globalEmail;
    }
    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.budget_item, parent, false);
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        holder.card_text.setText(listBudget.get(position).get_Class());

        Integer budgetID = listBudget.get(position).getId();

        String curr_expenses = Integer.toString(listBudget.get(position).getExpenses());

        holder.hidden_text.setText(budgetID.toString());

        holder.hidden_text2.setText(curr_expenses);

        String classImage = listBudget.get(position).get_Class().toString();

        if (classImage.equals("Savings")) {
            holder.img_budget_class.setImageResource(R.drawable.calc_savings);
        } else if (classImage.equals("Insurance")) {
            holder.img_budget_class.setImageResource(R.drawable.calc_insurance);
        } else if (classImage.equals("Bills")) {
            holder.img_budget_class.setImageResource(R.drawable.calc_bills);
        } else if (classImage.equals("Shopping")) {
            holder.img_budget_class.setImageResource(R.drawable.calc_shopping);
        } else {
            holder.img_budget_class.setImageResource(R.drawable.calc_budget);
        }

        holder.max_text.setText(listBudget.get(position).getMax().toString());

        holder.progress.setMax(listBudget.get(position).getMax());
        holder.progress.setProgress(listBudget.get(position).getExpenses());


    }
    @Override
    public int getItemCount() {
        return listBudget.size();
    }

    /**
     * ViewHolder class
     */
    public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView card_text, edit_text, max_text, zero_text, hidden_text, hidden_text2;
        public ImageView img_card, img_budget_class;
        public ImageButton btn_edit;

        public ProgressBar progress;
        private WeakReference<ClickListener> listenerRef;

        public UserViewHolder(View itemView) {
            super(itemView);


            bundle2 = new Bundle();

            databaseHelper = new CalculatorDatabaseHelper(itemView.getContext());
            budget = new Budget();

            databaseHelper2 = new TransactionDatabaseHelper(itemView.getContext());
            transactions = new Transactions();

            listenerRef = new WeakReference<>(listener);

            progress = (ProgressBar) itemView.findViewById(R.id.progess);

            card_text = (TextView) itemView.findViewById(R.id.card_text);
            edit_text = (TextView) itemView.findViewById(R.id.edit_text);
            max_text = (TextView) itemView.findViewById(R.id.max_text);
            zero_text = (TextView) itemView.findViewById(R.id.zero_text);
            hidden_text = (TextView) itemView.findViewById(R.id.hidden_text);
            hidden_text2 = (TextView) itemView.findViewById(R.id.hidden_text2);

            img_card = (ImageView) itemView.findViewById(R.id.img_card);
            img_budget_class = (ImageView) itemView.findViewById(R.id.img_budget_class);

            btn_edit = (ImageButton) itemView.findViewById(R.id.btn_edit);

            btn_edit.setOnClickListener(this);


        }

        // onClick Listener for view
        @Override
        public void onClick(View v) {

            if (v.getId() == btn_edit.getId()) {

                String budgetID = ((TextView) itemView.findViewById(R.id.hidden_text)).getText().toString();
                int finalBudgetID = Integer.parseInt(budgetID);
                bundle2.putInt("id", finalBudgetID);

                String _class = ((TextView) itemView.findViewById(R.id.card_text)).getText().toString();
                bundle2.putString("class", _class);

                String budgetTemp = ((TextView) itemView.findViewById(R.id.max_text)).getText().toString();
                int budgetTempInt = Integer.parseInt(budgetTemp);
                bundle2.putInt("budget", budgetTempInt);

                String expensesTemp = ((TextView) itemView.findViewById(R.id.hidden_text2)).getText().toString();
                int expensesTempInt = Integer.parseInt(expensesTemp);
                bundle2.putInt("expenses", expensesTempInt);

                final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(),R.style.CustomAlertDialog);

                ViewGroup viewGroup = v.findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.prompt_calc_edit, viewGroup, false);

                Button rdo_class = dialogView.findViewById(R.id.rdo_class);
                Button rdo_expenses = dialogView.findViewById(R.id.rdo_expenses);

                builder.setView(dialogView);
                final AlertDialog editDialog = builder.create();

                rdo_class.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(),R.style.CustomAlertDialog);

                        ViewGroup viewGroup = v.findViewById(android.R.id.content);
                        View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.prompt_calc_budget_edit, viewGroup, false);

                        Button btn_okay_budget = dialogView.findViewById(R.id.btn_okay_budget);
                        EditText new_budget_text = dialogView.findViewById(R.id.new_budget_text);

                        builder.setView(dialogView);
                        final AlertDialog newBudgetDialog = builder.create();

                        btn_okay_budget.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String text2 = new_budget_text.getText().toString();
                                int finalValue=Integer.parseInt(text2);

                                bundle2.putInt("budget", finalValue);
                                updateSQL(view);

                                newBudgetDialog.dismiss();
                                editDialog.dismiss();
                            }
                        });
                        newBudgetDialog.show();

                    }
                });

                rdo_expenses.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(),R.style.CustomAlertDialog);

                        ViewGroup viewGroup = v.findViewById(android.R.id.content);
                        View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.prompt_calc_expense, viewGroup, false);

                        Button btn_okay_expense = dialogView.findViewById(R.id.btn_okay_expense);
                        EditText new_expense_text = dialogView.findViewById(R.id.new_expense_text);

                        builder.setView(dialogView);
                        final AlertDialog newExpenseDialog = builder.create();

                        btn_okay_expense.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                String text1 = new_expense_text.getText().toString();
                                int finalCurrentSum = Integer.parseInt(text1);
                                Integer testlol = bundle2.getInt("expenses");
                                int finalValue3 = finalCurrentSum + testlol;

                                bundle2.putInt("expenses", finalValue3);
                                bundle2.putInt("transaction", finalCurrentSum);

                                String tempemp = Integer.toString(finalValue3);

                                updateSQL(view);
                                updateTransactionSQL(view);

                                newExpenseDialog.dismiss();
                                editDialog.dismiss();

                            }
                        });
                        newExpenseDialog.show();

                    }
                });

                editDialog.show();

            } else {
                Toast.makeText(v.getContext(), "Error", Toast.LENGTH_SHORT).show();
            }

            listenerRef.get().onPositionClicked(getAdapterPosition());
        }

    }

    private void updateSQL(View view) {
        Integer max = bundle2.getInt("budget");
        Integer expenses = bundle2.getInt("expenses");
        String _class = bundle2.getString("class").toString();
        Integer finalBudgetID = bundle2.getInt("id");

        try{
            budget.setExpenses(expenses);
            budget.setMax(max);
            budget.set_Class(_class);
            budget.setUserEmail(globalEmail);
            databaseHelper.updateBudget(budget, finalBudgetID);
            Toast.makeText(view.getContext(), "Budget Updated", Toast.LENGTH_SHORT).show();


            Intent backIntent = new Intent(view.getContext(), CalculatorActivity.class);
            backIntent.putExtra("EMAIL", globalEmail);
            view.getContext().startActivity(backIntent);

        } catch (Exception e) {
            Toast.makeText(view.getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void updateTransactionSQL(View view) {
        String _class = bundle2.getString("class").toString();
        Integer transact = bundle2.getInt("transaction");

        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = df.format(c);

        SimpleDateFormat tf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        String formattedTime = tf.format(c);

        try{
            transactions.setAmount(transact);
            transactions.setDate(formattedDate);
            transactions.setTime(formattedTime);
            transactions.set_Class(_class);
            transactions.setUserEmail(globalEmail);
            databaseHelper2.addTransaction(transactions);
            Toast.makeText(view.getContext(), "Transaction Logged", Toast.LENGTH_SHORT).show();


            Intent backIntent = new Intent(view.getContext(), CalculatorActivity.class);
            backIntent.putExtra("EMAIL", globalEmail);
            view.getContext().startActivity(backIntent);

        } catch (Exception e) {
            Toast.makeText(view.getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }
    public interface ClickListener {
        void onPositionClicked(int position);
    }
}
