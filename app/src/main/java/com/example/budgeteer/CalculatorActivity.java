package com.example.budgeteer;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class CalculatorActivity extends AppCompatActivity{

    private ImageButton btn_add,btn_back,btn_add2;

    private FrameLayout button1, lyt_btn;
    private ImageView empty_logo;

    private TextView textView1;

    private Bundle bundle;

    private CalculatorDatabaseHelper databaseHelper;

    private NotificationDatabaseHelper notificationDatabaseHelper;
    private Budget budget;

    private Notifications notifications;

    private AppCompatActivity activity = CalculatorActivity.this;
    private RecyclerView recyclerViewUsers;
    private List<Budget> listBudget;
    private BudgetAdapter2 budgetAdapter;

    private TransactionAdapter transactionAdapter;

    private String globalEmail;
    private String globalEmail2;

    private SharedPreferences loginPreferences;

    private Integer budgetID;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator_activity);

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);

        globalEmail2 = loginPreferences.getString("username", "");

        databaseHelper = new CalculatorDatabaseHelper(CalculatorActivity.this);
        budget = new Budget();

        notificationDatabaseHelper = new NotificationDatabaseHelper(CalculatorActivity.this);
        notifications = new Notifications();

        bundle = new Bundle();

        Intent intent = getIntent();
        globalEmail = intent.getStringExtra("EMAIL");

        btn_add = findViewById(R.id.button3);
        btn_add2 = findViewById(R.id.add_btn);
        btn_back = findViewById(R.id.btn_back);
        lyt_btn = findViewById(R.id.lyt_btn);
        textView1 = findViewById(R.id.textView1);

        button1 = findViewById(R.id.button1);
        empty_logo = findViewById(R.id.empty_logo);

        recyclerViewUsers = (RecyclerView) findViewById(R.id.recycler);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(CalculatorActivity.this,R.style.CustomAlertDialog);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.prompt_calc_add, viewGroup, false);

                Button btn_add_class = dialogView.findViewById(R.id.btn_add_class);
                Button btn_add_budget = dialogView.findViewById(R.id.btn_add_budget);

                builder.setView(dialogView);
                final AlertDialog alertDialog = builder.create();

                btn_add_class.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view1) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(CalculatorActivity.this,R.style.CustomAlertDialog);
                        ViewGroup viewGroup = findViewById(android.R.id.content);
                        View dialogView = LayoutInflater.from(view1.getContext()).inflate(R.layout.prompt_calc_class, viewGroup, false);


                        RadioButton btn_rdo_bills = dialogView.findViewById(R.id.rdo_bills);
                        RadioButton btn_rdo_insurance = dialogView.findViewById(R.id.rdo_insurance);
                        RadioButton btn_rdo_others = dialogView.findViewById(R.id.rdo_others);
                        RadioButton btn_rdo_personal = dialogView.findViewById(R.id.rdo_personal);
                        RadioButton btn_rdo_savings = dialogView.findViewById(R.id.rdo_savings);
                        RadioButton btn_rdo_shoping = dialogView.findViewById(R.id.rdo_shoping);


                        builder.setView(dialogView);
                        final AlertDialog alertDialog1 = builder.create();

                        btn_rdo_bills.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                bundle.putString("class", "Bills");
                                alertDialog1.hide();
                            }
                        });

                        btn_rdo_insurance.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                bundle.putString("class", "Insurance");
                                alertDialog1.hide();
                            }
                        });

                        btn_rdo_others.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                final AlertDialog.Builder builder = new AlertDialog.Builder(CalculatorActivity.this,R.style.CustomAlertDialog);
                                ViewGroup viewGroup = findViewById(android.R.id.content);
                                View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.prompt_calc_class_other, viewGroup, false);

                                Button buttonOk = dialogView.findViewById(R.id.btn_okay);
                                EditText otherClassText = dialogView.findViewById(R.id.other_class_text);

                                builder.setView(dialogView);
                                final AlertDialog alertDialog2 = builder.create();

                                buttonOk.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String text = otherClassText.getText().toString();

                                        if (!text.isEmpty()) {

                                            bundle.putString("class", text);

                                            alertDialog2.dismiss();

                                            alertDialog1.hide();
                                        }
                                        else {
                                            Toast.makeText(CalculatorActivity.this, "Please Input a new Classification", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                                alertDialog2.show();


                            }
                        });

                        btn_rdo_personal.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                bundle.putString("class", "Personal");
                                alertDialog1.hide();
                            }
                        });

                        btn_rdo_savings.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                bundle.putString("class", "Savings");
                                alertDialog1.hide();
                            }
                        });

                        btn_rdo_shoping.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                bundle.putString("class", "Shopping");
                                alertDialog1.hide();
                            }
                        });

                        alertDialog1.show();
                    }
                });

                btn_add_budget.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        final AlertDialog.Builder builder = new AlertDialog.Builder(CalculatorActivity.this,R.style.CustomAlertDialog);
                        ViewGroup viewGroup = findViewById(android.R.id.content);
                        View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.prompt_calc_budget_new, viewGroup, false);

                        Button btn_okay_budget = dialogView.findViewById(R.id.btn_okay_budget);
                        EditText new_budget_text = dialogView.findViewById(R.id.new_budget_text);

                        builder.setView(dialogView);
                        final AlertDialog alertDialogBudget = builder.create();

                        btn_okay_budget.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                try {
                                    String text1 = new_budget_text.getText().toString();
                                    int finalValue = Integer.parseInt(text1);

                                    if (!text1.isEmpty()) {
                                        bundle.putInt("budget", finalValue);
                                        alertDialogBudget.hide();
                                        addToSql();
                                        alertDialog.dismiss();

                                    } else {
                                        Toast.makeText(CalculatorActivity.this, "Please Input a Budget", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(CalculatorActivity.this, "Please Select a Classification", Toast.LENGTH_SHORT).show();
                                    alertDialogBudget.hide();
                                }
                            }
                        });

                        alertDialogBudget.show();

                    }
                });
                alertDialog.show();
            }
        });

        btn_add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(CalculatorActivity.this,R.style.CustomAlertDialog);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.prompt_calc_add, viewGroup, false);

                Button btn_add_class = dialogView.findViewById(R.id.btn_add_class);
                Button btn_add_budget = dialogView.findViewById(R.id.btn_add_budget);

                builder.setView(dialogView);
                final AlertDialog alertDialog = builder.create();

                btn_add_class.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view1) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(CalculatorActivity.this,R.style.CustomAlertDialog);
                        ViewGroup viewGroup = findViewById(android.R.id.content);
                        View dialogView = LayoutInflater.from(view1.getContext()).inflate(R.layout.prompt_calc_class, viewGroup, false);


                        RadioButton btn_rdo_bills = dialogView.findViewById(R.id.rdo_bills);
                        RadioButton btn_rdo_insurance = dialogView.findViewById(R.id.rdo_insurance);
                        RadioButton btn_rdo_others = dialogView.findViewById(R.id.rdo_others);
                        RadioButton btn_rdo_personal = dialogView.findViewById(R.id.rdo_personal);
                        RadioButton btn_rdo_savings = dialogView.findViewById(R.id.rdo_savings);
                        RadioButton btn_rdo_shoping = dialogView.findViewById(R.id.rdo_shoping);


                        builder.setView(dialogView);
                        final AlertDialog alertDialog1 = builder.create();

                        btn_rdo_bills.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                bundle.putString("class", "Bills");
                                alertDialog1.hide();
                            }
                        });

                        btn_rdo_insurance.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                bundle.putString("class", "Insurance");
                                alertDialog1.hide();
                            }
                        });

                        btn_rdo_others.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                final AlertDialog.Builder builder = new AlertDialog.Builder(CalculatorActivity.this,R.style.CustomAlertDialog);
                                ViewGroup viewGroup = findViewById(android.R.id.content);
                                View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.prompt_calc_class_other, viewGroup, false);

                                Button buttonOk = dialogView.findViewById(R.id.btn_okay);
                                EditText otherClassText = dialogView.findViewById(R.id.other_class_text);

                                builder.setView(dialogView);
                                final AlertDialog alertDialog2 = builder.create();

                                buttonOk.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String text = otherClassText.getText().toString();

                                        if (!text.isEmpty()) {

                                            bundle.putString("class", text);

                                            alertDialog2.hide();

                                            alertDialog1.hide();
                                        }
                                        else {
                                            Toast.makeText(CalculatorActivity.this, "Please Input a new Classification", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                                alertDialog2.show();


                            }
                        });

                        btn_rdo_personal.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                bundle.putString("class", "Personal");
                                alertDialog1.hide();
                            }
                        });

                        btn_rdo_savings.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                bundle.putString("class", "Savings");
                                alertDialog1.hide();
                            }
                        });

                        btn_rdo_shoping.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                bundle.putString("class", "Shopping");
                                alertDialog1.hide();
                            }
                        });

                        alertDialog1.show();
                    }
                });

                btn_add_budget.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        final AlertDialog.Builder builder = new AlertDialog.Builder(CalculatorActivity.this,R.style.CustomAlertDialog);
                        ViewGroup viewGroup = findViewById(android.R.id.content);
                        View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.prompt_calc_budget_new, viewGroup, false);

                        Button btn_okay_budget = dialogView.findViewById(R.id.btn_okay_budget);
                        EditText new_budget_text = dialogView.findViewById(R.id.new_budget_text);

                        builder.setView(dialogView);
                        final AlertDialog alertDialogBudget = builder.create();

                        btn_okay_budget.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                try {
                                    String text1 = new_budget_text.getText().toString();
                                    int finalValue = Integer.parseInt(text1);

                                    if (!text1.isEmpty()) {
                                        bundle.putInt("budget", finalValue);
                                        alertDialogBudget.hide();
                                        addToSql();
                                        alertDialog.dismiss();

                                    } else {
                                        Toast.makeText(CalculatorActivity.this, "Please Input a Budget", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(CalculatorActivity.this, "Please Select a Classification", Toast.LENGTH_SHORT).show();
                                    alertDialogBudget.hide();
                                }
                            }
                        });

                        alertDialogBudget.show();

                    }
                });
                alertDialog.show();
            }
        });

        listBudget = new ArrayList<>();

        budgetAdapter = new BudgetAdapter2(listBudget, new BudgetAdapter2.ClickListener() {
            @Override public void onPositionClicked(int position) {
                // callback performed on click
                //Toast.makeText(CalculatorActivity.this, "wapapapapapaap", Toast.LENGTH_SHORT).show();
            }
        }, globalEmail);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewUsers.setLayoutManager(mLayoutManager);
        recyclerViewUsers.setItemAnimator(new DefaultItemAnimator());
        recyclerViewUsers.setHasFixedSize(true);
        recyclerViewUsers.setAdapter(budgetAdapter);
        databaseHelper = new CalculatorDatabaseHelper(activity);

        //String emailFromIntent = getIntent().getStringExtra("EMAIL");
        //textViewName.setText(emailFromIntent);

        getDataFromSQLite();

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String emailExtra = intent.getStringExtra("EMAIL");
                Intent backIntent = new Intent(CalculatorActivity.this, OptionActivity.class);
                backIntent.putExtra("EMAIL", emailExtra);
                startActivity(backIntent);
            }
        });

        if (databaseHelper.checkBudgetExist(globalEmail)){
            recyclerViewUsers.setVisibility(recyclerViewUsers.VISIBLE);
            btn_add.setVisibility(recyclerViewUsers.VISIBLE);
            button1.setVisibility(button1.VISIBLE);

            lyt_btn.setVisibility(lyt_btn.INVISIBLE);
            empty_logo.setVisibility(empty_logo.INVISIBLE);
            btn_add2.setVisibility(btn_add2.INVISIBLE);
            textView1.setVisibility(textView1.INVISIBLE);
        }
        else {
            recyclerViewUsers.setVisibility(recyclerViewUsers.INVISIBLE);
            btn_add.setVisibility(btn_add.INVISIBLE);
            button1.setVisibility(button1.INVISIBLE);

            lyt_btn.setVisibility(lyt_btn.VISIBLE);
            empty_logo.setVisibility(empty_logo.VISIBLE);
            btn_add2.setVisibility(btn_add2.VISIBLE);
            textView1.setVisibility(textView1.VISIBLE);
        }

    }


    private void addToSql(){
        Integer max = bundle.getInt("budget");
        String _class = bundle.getString("class").toString();
        Intent intent = getIntent();
        String emailExtra = intent.getStringExtra("EMAIL");
        bundle.putString("email", emailExtra);

        Integer expenses = 0;

        try{
            String emaily = bundle.getString("email");

            //Toast.makeText(CalculatorActivity.this, globalEmail+ " " + emailExtra, Toast.LENGTH_SHORT).show();
            budget.setUserEmail(emaily);
            budget.set_Class(_class);
            budget.setMax(max);
            budget.setExpenses(expenses);


            databaseHelper.addBudget(budget);
            // Snack Bar to show success message that record saved successfully

            addToNotifcationSql();

        } catch (Exception e) {
            // Snack Bar to show error message that record already exists
            Toast.makeText(CalculatorActivity.this, "addtoSQL " + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }

    private void addToNotifcationSql(){

        String _class = bundle.getString("class").toString();
        Intent intent = getIntent();
        String emailExtra = intent.getStringExtra("EMAIL");
        bundle.putString("email", emailExtra);

        Integer percent = 0;
        String date1 = "0", time1 = "0";

        budgetID = databaseHelper.getTopID();

        if (budgetID == 0) {
            budgetID = 1;
        }

        try {
            String emaily = bundle.getString("email");
            notifications.setBudgetID(budgetID);
            notifications.setUserEmail(emaily);
            notifications.set_Class(_class);
            notifications.setPercent(percent);
            notifications.setDate(date1);
            notifications.setTime(time1);

            notificationDatabaseHelper.addNotification(notifications);
            // Snack Bar to show success message that record saved successfully
            Toast.makeText(CalculatorActivity.this, "Budget Added", Toast.LENGTH_SHORT).show();

            Intent intent1 = getIntent();
            String emailExtra1 = intent.getStringExtra("EMAIL");
            Intent backIntent = new Intent(CalculatorActivity.this, CalculatorActivity.class);
            backIntent.putExtra("EMAIL", emailExtra1);
            startActivity(backIntent);

        } catch (Exception e) {
            // Snack Bar to show error message that record already exists
            Toast.makeText(CalculatorActivity.this, "norif " + budgetID + "/" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            Log.d("norif", "norif " + " " + percent + " "+ budgetID + e.getMessage());
            Log.d("norif", "norif " + e.getLocalizedMessage());
        }

    }

    /**
     * This method is to fetch all user records from SQLite
     */
    private void getDataFromSQLite() {
        Intent intent = getIntent();
        String emailExtra = intent.getStringExtra("EMAIL");
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listBudget.clear();
                listBudget.addAll(databaseHelper.getAllBudget(emailExtra));
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                budgetAdapter.notifyDataSetChanged();
            }
        }.execute();
    }


}
