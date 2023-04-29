package com.example.budgeteer;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity{
    private static final int SELECT_PICTURE = 3;
    final Context context = this;

    private ImageButton btn_first, btn_last, btn_email, btn_pass,btn_back, btnSave, profile_image_button, pass_eye_btn;

    private TextView btn_first_txt,btn_last_txt,btn_email_txt, btn_pass_txt;

    private DatabaseHelper databaseHelper;
    private User user;

    private List<User> listUser;

    private Uri uri;

    private String uriString, passout, pass;
    private Boolean isEyeOpen;
    private String globalEmail;

    private Switch switch1;

    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;

    private byte[] logoImage;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        globalEmail = intent.getStringExtra("EMAIL");

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        isEyeOpen = false;
        setContentView(R.layout.settings_activity);

//        btn_first = findViewById(R.id.first_name_btn);
//        btn_last = findViewById(R.id.last_name_btn);
//        btn_email = findViewById(R.id.email_btn);
//        btn_pass = findViewById(R.id.pass_btn);

        switch1 = findViewById(R.id.switch1);
        btn_back = findViewById(R.id.btn_back);
        btnSave = findViewById(R.id.button3);
        pass_eye_btn  = findViewById(R.id.pass_eye_btn);
        profile_image_button = findViewById(R.id.profile_image_button);

        btn_first_txt = findViewById(R.id.first_name_txt);
        btn_last_txt = findViewById(R.id.last_name_txt);
        btn_email_txt = findViewById(R.id.email_txt);
        btn_pass_txt = findViewById(R.id.pass_txt);

        databaseHelper = new DatabaseHelper(SettingsActivity.this);
        user = new User();

        listUser = new ArrayList<>();

        Boolean useNotif = loginPreferences.getBoolean("useNotif", false);
        if (useNotif == true) {
            switch1.setChecked(true);
        }

        btn_first_txt.setOnClickListener(new View.OnClickListener() {
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

        btn_last_txt.setOnClickListener(new View.OnClickListener() {
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

        btn_email_txt.setOnClickListener(new View.OnClickListener() {
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

        btn_pass_txt.setOnClickListener(new View.OnClickListener() {
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

        pass_eye_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEyeOpen) {
                    isEyeOpen = true;
                } else {
                    isEyeOpen = false;
                }

                if (isEyeOpen) {
                    btn_pass_txt.setText(pass);
                } else {
                    btn_pass_txt.setText(passout);
                }

            }
        });

        profile_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(
                        Intent.createChooser(intent, "Select Picture"),
                        SELECT_PICTURE);
            }
        });

        getDataFromSQLite();

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

                if (switch1.isChecked()) {
                    loginPrefsEditor.putBoolean("useNotif", true);
                    loginPrefsEditor.commit();
                } else {
                    loginPrefsEditor.putBoolean("useNotif", false);
                    loginPrefsEditor.commit();
                }


                Intent intent = getIntent();
                String emailExtra = intent.getStringExtra("EMAIL");
                Intent nextIntent = new Intent(SettingsActivity.this, SettingsActivity.class);
                nextIntent.putExtra("EMAIL",emailExtra);
                startActivity(nextIntent);

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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                Bitmap selectedImageBitmap =  BitmapFactory.decodeResource(getResources(),
                        R.drawable.getstarted2_profilee);
                try {
                    selectedImageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                profile_image_button.setVisibility(View.VISIBLE);
                profile_image_button.setImageBitmap(selectedImageBitmap);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                selectedImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                logoImage = stream.toByteArray();
//
//                selectedImagePath = selectedImageUri.toString();
//
//                profile_image_button.setImageURI(selectedImageUri);
            }
        }
    }

    private void getDataFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.

        listUser.addAll(databaseHelper.getUser(globalEmail));

        btn_first_txt.setText(listUser.get(0).getFirstName());
        btn_last_txt.setText(listUser.get(0).getLastName());
        btn_email_txt.setText(listUser.get(0).getEmail());

        pass = listUser.get(0).getPassword();
        passout = "";

        for (int i = 0; i < pass.length(); i++) {
            passout += "*";
        }

        if (isEyeOpen) {
            btn_pass_txt.setText(pass);
        } else {
            btn_pass_txt.setText(passout);
        }

        byte[] image = listUser.get(0).getImage();
        profile_image_button.setImageBitmap(getImage(image)); ;

    }

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

}
