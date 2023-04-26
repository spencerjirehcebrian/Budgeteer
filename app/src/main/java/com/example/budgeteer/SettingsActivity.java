package com.example.budgeteer;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity{
    final Context context = this;

    private ImageButton btn_first, btn_last, btn_email, btn_pass,btn_back, btnSave;

    private TextView btn_first_txt,btn_last_txt,btn_email_txt, btn_pass_txt;

    private DatabaseHelper databaseHelper;
    private User user;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        btn_first = findViewById(R.id.first_name_btn);
        btn_last = findViewById(R.id.last_name_btn);
        btn_email = findViewById(R.id.email_btn);
        btn_pass = findViewById(R.id.pass_btn);
        btn_back = findViewById(R.id.btn_back);
        btnSave = findViewById(R.id.button3);

        btn_first_txt = findViewById(R.id.first_name_txt);
        btn_last_txt = findViewById(R.id.last_name_txt);
        btn_email_txt = findViewById(R.id.email_txt);
        btn_pass_txt = findViewById(R.id.pass_txt);

        databaseHelper = new DatabaseHelper(SettingsActivity.this);
        user = new User();

        btn_first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            // get prompts.xml view
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.prompt_set_first, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.editTextDialogUserInput);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // get user input and set it to result
                                        // edit text
                                        btn_first_txt.setText(userInput.getText());
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            }
        });

        btn_last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            // get prompts.xml view
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.prompt_set_last, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.editTextDialogUserInput);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // get user input and set it to result
                                        // edit text
                                        btn_last_txt.setText(userInput.getText());
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            }
        });

        btn_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

// get prompts.xml view
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.prompt_set_email, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.editTextDialogUserInput);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // get user input and set it to result
                                        // edit text
                                        btn_email_txt.setText(userInput.getText());
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            }
        });

        btn_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

// get prompts.xml view
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.prompt_set_password, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.editTextDialogUserInput);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // get user input and set it to result
                                        // edit text
                                        btn_pass_txt.setText(userInput.getText());
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDatabase();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String emailExtra = intent.getStringExtra("EMAIL");
                Intent backIntent = new Intent(SettingsActivity.this, OptionActivity.class);
                backIntent.putExtra("EMAIL", emailExtra);
                startActivity(backIntent);
            }
        });

    }



    private void updateDatabase() {
        boolean isError = false;
        String firstname = btn_first_txt.getText().toString();
        String lastname = btn_last_txt.getText().toString();
        String email = btn_email_txt.getText().toString();
        String password = btn_pass_txt.getText().toString();

        if (password.isEmpty()) {
            isError = true;
            Toast.makeText(SettingsActivity.this, "Invalid Password", Toast.LENGTH_SHORT).show();
        }

        if (!isEmailValid(email)) {
            isError = true;
            Toast.makeText(SettingsActivity.this, "Invalid Email", Toast.LENGTH_SHORT).show();
        }

        if (lastname.isEmpty()) {
            isError = true;
            Toast.makeText(SettingsActivity.this, "Invalid Email", Toast.LENGTH_SHORT).show();
        }

        if (firstname.isEmpty()) {
            isError = true;
            Toast.makeText(SettingsActivity.this, "Invalid Email", Toast.LENGTH_SHORT).show();
        }

        if (!isError) {
            try{
                user.setFirstName(firstname.trim());
                user.setLastName(firstname.trim());
                user.setEmail(email.trim());
                user.setPassword(password.trim());
                databaseHelper.updateUser(user);
                // Snack Bar to show success message that record saved successfully
                Toast.makeText(SettingsActivity.this, "Account Updated", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                // Snack Bar to show error message that record already exists
                Toast.makeText(SettingsActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }


    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                .matches();
    }
}
