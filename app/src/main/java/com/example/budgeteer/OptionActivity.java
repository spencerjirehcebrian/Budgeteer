package com.example.budgeteer;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class OptionActivity extends AppCompatActivity{

    private static final int REQUEST_PERMISSION_PHONE_NOTIFCATION = 8;
    private ImageButton btn_calc, btn_set, btn_hist, btn_logout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option_activity);

        btn_calc = findViewById(R.id.btn_calc);
        btn_set = findViewById(R.id.btn_set);
        btn_hist = findViewById(R.id.btn_hist);
        btn_logout = findViewById(R.id.btn_logout);

        btn_calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ActivityCompat.checkSelfPermission(view.getContext(), android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(OptionActivity.this, new String[]{android.Manifest.permission.POST_NOTIFICATIONS},REQUEST_PERMISSION_PHONE_NOTIFCATION );
                    return;
                }

                Intent intent = getIntent();
                String emailExtra = intent.getStringExtra("EMAIL");
                Intent nextIntent = new Intent(OptionActivity.this, CalculatorActivity.class);
                nextIntent.putExtra("EMAIL",emailExtra);
                startActivity(nextIntent);

                //startActivity(new Intent(OptionActivity.this, CalculatorActivity.class));
            }
        });

        btn_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String emailExtra = intent.getStringExtra("EMAIL");
                Intent nextIntent = new Intent(OptionActivity.this, SettingsActivity.class);
                nextIntent.putExtra("EMAIL",emailExtra);
                startActivity(nextIntent);
            }
        });

        btn_hist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String emailExtra = intent.getStringExtra("EMAIL");
                Intent nextIntent = new Intent(OptionActivity.this, HistoryActivity.class);
                nextIntent.putExtra("EMAIL",emailExtra);
                startActivity(nextIntent);
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String emailExtra = intent.getStringExtra("EMAIL");
                Intent nextIntent = new Intent(OptionActivity.this, GetStartedActivity.class);
                nextIntent.putExtra("EMAIL",emailExtra);
                startActivity(nextIntent);
            }
        });

    }

 }
