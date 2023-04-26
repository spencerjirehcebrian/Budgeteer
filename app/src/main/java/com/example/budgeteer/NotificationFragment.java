package com.example.budgeteer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class NotificationFragment extends Fragment {


    private TextInputLayout layputInputEmail;
    private TextInputLayout layputInputPassword;
    private TextInputEditText inputEmail,inputPassword;

    private Button btnLogin;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.notification_fragment, container, false);

//        layputInputEmail = v.findViewById(R.id.layout_login_email);
//        layputInputPassword = v.findViewById(R.id.layout_login_password);
//        inputEmail = v.findViewById(R.id.login_email);
//        inputPassword = v.findViewById(R.id.login_password);
//        btnLogin = v.findViewById(R.id.next);
//
//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                verifyFromSQLite();
//            }
//        });
//
//        databaseHelper = new DatabaseHelper(getActivity());
//        inputValidation = new InputValidation(getActivity());

        return v;
    }

}
