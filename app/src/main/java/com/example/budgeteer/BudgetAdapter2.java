package com.example.budgeteer;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
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
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class BudgetAdapter2 extends RecyclerView.Adapter<BudgetAdapter2.UserViewHolder> {

    private static final String CHANNEL_ID = "channel1";

    private final int REQUEST_PERMISSION_PHONE_NOTIFCATION=8;

    private String globalEmail;
    private List<Budget> listBudget;

    private Bundle bundle2;

    private Budget budget;

    private Bundle bundle;
    CalculatorActivity cls2 = new CalculatorActivity();
    private CalculatorDatabaseHelper databaseHelper;
    private TransactionDatabaseHelper databaseHelper2;
    private Transactions transactions;

    private NotificationDatabaseHelper notificationDatabaseHelper;

    private Notifications notifications;

    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;

    private Boolean notifOn;


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
        String curr_budget = Integer.toString(listBudget.get(position).getMax());

        holder.hidden_text.setText(budgetID.toString());

        holder.hidden_text2.setText(curr_expenses);

        holder.hidden_text3.setText(curr_budget);

        double  m = listBudget.get(position).getExpenses();

        double  e = listBudget.get(position).getMax();

        double percentFloat = m/e;

        double percentFloat2 = percentFloat * 100;

        String finalFloat = String.format("%.0f",percentFloat2) +"%";

        holder.mid_text.setText(finalFloat);

        float f = (float) percentFloat;

        f += 0.03;

        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) holder.mid_guideline.getLayoutParams();
        params.guidePercent = f; // 45% // range: 0 <-> 1
        holder.mid_guideline.setLayoutParams(params);

        //holder.mid_guideline.setGuidelinePercent(f);

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

        holder.max_text.setText("P " + curr_budget);

        holder.progress.setMax(listBudget.get(position).getMax());
        holder.progress.setProgress(listBudget.get(position).getExpenses());

        Log.d("meowy", finalFloat + ":" + Float.toString(f) +"-"+m+"-"+e);

    }
    @Override
    public int getItemCount() {
        return listBudget.size();
    }

    /**
     * ViewHolder class
     */
    public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView card_text, edit_text, max_text, zero_text, hidden_text, hidden_text2, hidden_text3, mid_text;
        public ImageView img_card, img_budget_class, mid_arrow;
        public ImageButton btn_edit;
        public Guideline mid_guideline;

        public ProgressBar progress;
        private WeakReference<ClickListener> listenerRef;




        public UserViewHolder(View itemView) {
            super(itemView);

            if (ActivityCompat.checkSelfPermission(itemView.getContext(), android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) itemView.getContext(), new String[]{android.Manifest.permission.POST_NOTIFICATIONS},REQUEST_PERMISSION_PHONE_NOTIFCATION );
                Log.d("lolololo", "startNotification: I already have permission?");
                return;
            }

            loginPreferences = itemView.getContext().getSharedPreferences("loginPrefs", MODE_PRIVATE);
            loginPrefsEditor = loginPreferences.edit();

            createNotificationChannel(itemView);
            bundle2 = new Bundle();

            databaseHelper = new CalculatorDatabaseHelper(itemView.getContext());
            budget = new Budget();

            databaseHelper2 = new TransactionDatabaseHelper(itemView.getContext());
            transactions = new Transactions();

            notificationDatabaseHelper = new NotificationDatabaseHelper(itemView.getContext());
            notifications = new Notifications();

            listenerRef = new WeakReference<>(listener);

            mid_guideline = (Guideline) itemView.findViewById(R.id.mid_guideline);

            progress = (ProgressBar) itemView.findViewById(R.id.progess);

            card_text = (TextView) itemView.findViewById(R.id.card_text);
            edit_text = (TextView) itemView.findViewById(R.id.edit_text);
            max_text = (TextView) itemView.findViewById(R.id.max_text);
            zero_text = (TextView) itemView.findViewById(R.id.zero_text);
            hidden_text = (TextView) itemView.findViewById(R.id.hidden_text);
            hidden_text2 = (TextView) itemView.findViewById(R.id.hidden_text2);
            hidden_text3 = (TextView) itemView.findViewById(R.id.hidden_text3);
            mid_text = (TextView) itemView.findViewById(R.id.mid_text);

            img_card = (ImageView) itemView.findViewById(R.id.img_card);
            img_budget_class = (ImageView) itemView.findViewById(R.id.img_budget_class);

            mid_arrow = (ImageView) itemView.findViewById(R.id.mid_arrow);

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

                String budgetTemp = ((TextView) itemView.findViewById(R.id.hidden_text3)).getText().toString();
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
                                try {
                                    String text2 = new_budget_text.getText().toString();
                                    int finalValue = Integer.parseInt(text2);

                                    bundle2.putInt("budget", finalValue);
                                    updateSQL(view);

                                    newBudgetDialog.dismiss();
                                    editDialog.dismiss();
                                } catch (Exception e) {
                                    Toast.makeText(view.getContext(), "Missing Input",Toast.LENGTH_SHORT).show();
                                }
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
                                try {
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
                                } catch (Exception e) {
                                    Toast.makeText(view.getContext(), "Missing Input",Toast.LENGTH_SHORT).show();
                                }
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

        } catch (Exception e) {
            Toast.makeText(view.getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            Log.d("notif_errr3", e.getMessage());
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


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                updateNotificationSQL(view);
            }

        } catch (Exception e) {
            Toast.makeText(view.getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            Log.d("notif_errr2", e.getMessage());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void updateNotificationSQL(View view) {
        String _class = bundle2.getString("class").toString();
        int max = bundle2.getInt("budget");
        int expenses = bundle2.getInt("expenses");
        Integer finalBudgetID = bundle2.getInt("id");

        float percentFloat = (float) expenses / (float) max;

        float percentFloat2 = percentFloat * 100;

        int roundedPercent = Math.round(percentFloat2);

        bundle2.putInt("percent", roundedPercent);

        Log.d("floop", "updateNotificationSQL: " +expenses+"/"+ max +"/"+ roundedPercent+"/"+ finalBudgetID);

        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = df.format(c);

        SimpleDateFormat tf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        String formattedTime = tf.format(c);

        try{
            notifications.setBudgetID(finalBudgetID);
            notifications.setPercent(roundedPercent);
            notifications.setDate(formattedDate);
            notifications.setTime(formattedTime);
            notifications.set_Class(_class);
            notifications.setUserEmail(globalEmail);
            notificationDatabaseHelper.updateNotifications(notifications, finalBudgetID);
            Toast.makeText(view.getContext(), "Changes Saved", Toast.LENGTH_SHORT).show();

            notifOn = loginPreferences.getBoolean("useNotif", false);
            if (notifOn == true) {
                startNotification(view);
                Log.d("wapopo", "updateNotificationSQL: yahooo" );
            }

            Intent backIntent = new Intent(view.getContext(), CalculatorActivity.class);
            backIntent.putExtra("EMAIL", globalEmail);
            view.getContext().startActivity(backIntent);

        } catch (Exception err) {
            Toast.makeText(view.getContext(), err.getMessage().toString(), Toast.LENGTH_SHORT).show();
            Log.d("notif_errr", err.getMessage());
        }
    }

    public interface ClickListener {
        void onPositionClicked(int position);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startNotification(View view) {

        Integer percentage = bundle2.getInt("percent");
        String percentageString = percentage.toString() + "%";
        createNotificationChannel(view);
        String classString = bundle2.getString("class");

        Log.d("meowat", "startNotification: 1 " + percentage);

//        String PkgName = BuildConfig.APPLICATION_ID;
//        RemoteViews notificationLayout = new RemoteViews(PkgName, R.layout.notofication_test);

        if (percentage >= 100) {

            Log.d("meowat", "startNotification: 101");
            String text = classString + " (" + percentageString +")";
            NotificationCompat.Builder builder = new NotificationCompat.Builder(view.getContext(), CHANNEL_ID)
                    .setSmallIcon(R.drawable.notif_warn)
                    .setContentTitle("Limit Exceeded")
                    .setContentText(text)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText("Much longer text that cannot fit one line..."))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(view.getContext());

            notificationManager.notify(8, builder.build());
            Log.d("meowat", "startNotification: 100");


        }else if (percentage >= 50) {

            Log.d("meowat", "startNotification: 101");
            String text = classString + " (" + percentageString +")";
            NotificationCompat.Builder builder = new NotificationCompat.Builder(view.getContext(), CHANNEL_ID)
                    .setSmallIcon(R.drawable.notif_alarm)
                    .setContentTitle("Close to Limit")
                    .setContentText(text)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(text))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(view.getContext());

            notificationManager.notify(8, builder.build());
            Log.d("meowat", "startNotification: 50");

        }

    }

    private void createNotificationChannel(View view) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is not in the Support Library.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "channel1";
            String description = "channel1";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system. You can't change the importance
            // or other notification behaviors after this.
            NotificationManager notificationManager = view.getContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
