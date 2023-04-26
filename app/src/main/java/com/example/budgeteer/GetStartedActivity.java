package com.example.budgeteer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.budgeteer.R;

public class GetStartedActivity extends AppCompatActivity {

    private ImageButton btnGetStarted, btnAboutUs;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.getstarted_activity);

        btnGetStarted = findViewById(R.id.button3);
        btnAboutUs = findViewById(R.id.button4);


        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GetStartedActivity.this, GetStarted2Activity.class));
            }
        });

        btnAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GetStartedActivity.this, GetStartedActivity.class));
            }
        });

    }
}