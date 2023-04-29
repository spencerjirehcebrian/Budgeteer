package com.example.budgeteer;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.budgeteer.TransactionFragment;
import com.example.budgeteer.NotificationFragment;

public class HistoryActivity extends AppCompatActivity{

    private FragmentTransaction fragmentTransaction;

    private Button btn_noti, btn_trans;

    private ImageButton btn_back;
    private ImageView img1, img2, img3;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_activity);

        btn_noti = findViewById(R.id.btn_noti);
        btn_trans = findViewById(R.id.btn_trans);
        btn_back = findViewById(R.id.btn_back);
        img1 = findViewById(R.id.imageview1);
        img2 = findViewById(R.id.imageview_notif);
        img3 = findViewById(R.id.imageview_trans);

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NotificationFragment()).commit();
        fragmentTransaction.replace(R.id.fragment_container, new NotificationFragment()).commit();

        img2.setImageResource(R.drawable.hist_react_active);
        img2.bringToFront();
        img3.setImageResource(R.drawable.hist_rect);

        btn_noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NotificationFragment()).commit();
                fragmentTransaction.replace(R.id.fragment_container, new NotificationFragment()).commit();

                img2.setImageResource(R.drawable.hist_react_active);
                img2.bringToFront();
                img3.setImageResource(R.drawable.hist_rect);
            }
        });

        btn_trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TransactionFragment()).commit();
                fragmentTransaction.replace(R.id.fragment_container, new TransactionFragment()).commit();

                img3.setImageResource(R.drawable.hist_react_active);
                img3.bringToFront();
                img2.setImageResource(R.drawable.hist_rect);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String emailExtra = intent.getStringExtra("EMAIL");
                Intent backIntent = new Intent(HistoryActivity.this, OptionActivity.class);
                backIntent.putExtra("EMAIL", emailExtra);
                startActivity(backIntent);
            }
        });

    }

    public String getMyData() {
        Intent intent = getIntent();
        String emailExtra = intent.getStringExtra("EMAIL");
        return emailExtra;
    }

}
