package com.example.budgeteer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.budgeteer.LogInFragment;
import com.example.budgeteer.SignUpFragment;

import com.example.budgeteer.R;


public class GetStarted2Activity extends AppCompatActivity {

    private FragmentTransaction fragmentTransaction;

    private Button btnLogin, btnSignup;
    private ImageView img1, img2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.getstarted2_activity);

        btnLogin = findViewById(R.id.lgt_btn);
        btnSignup = findViewById(R.id.snu_btn);
        img1 = findViewById(R.id.imageview1);
        img2 = findViewById(R.id.imageview2);

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LogInFragment()).commit();
        fragmentTransaction.replace(R.id.fragment_container, new LogInFragment()).commit();

        img1.setVisibility(View.VISIBLE);
        img2.setVisibility(View.INVISIBLE);
        btnLogin.setTextSize(20);
        btnSignup.setTextSize(15);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                img1.setVisibility(View.VISIBLE);
                img2.setVisibility(View.INVISIBLE);
                btnLogin.setTextSize(20);
                btnSignup.setTextSize(15);

                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LogInFragment()).commit();
                fragmentTransaction.replace(R.id.fragment_container, new LogInFragment()).commit();
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                img2.setVisibility(View.VISIBLE);
                img1.setVisibility(View.INVISIBLE);
                btnSignup.setTextSize(20);
                btnLogin.setTextSize(15);

                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SignUpFragment()).commit();
                fragmentTransaction.replace(R.id.fragment_container, new SignUpFragment()).commit();
            }
        });
    }
}
