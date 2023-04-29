package com.example.budgeteer;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LogInFragment extends Fragment {


    private TextInputLayout layputInputEmail;
    private TextInputLayout layputInputPassword;
    private TextInputEditText inputEmail,inputPassword;

    private Switch rememberSwitch;

    private Button btnLogin;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;

    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.login_fragment, container, false);

            layputInputEmail = v.findViewById(R.id.layout_login_email);
            layputInputPassword = v.findViewById(R.id.layout_login_password);
            inputEmail = v.findViewById(R.id.login_email);
            inputPassword = v.findViewById(R.id.login_password);
            btnLogin = v.findViewById(R.id.next);
            rememberSwitch = v.findViewById(R.id.rememberSwitch);

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    verifyFromSQLite();
                }
            });

            databaseHelper = new DatabaseHelper(getActivity());

            loginPreferences = this.getContext().getSharedPreferences("loginPrefs", MODE_PRIVATE);
            loginPrefsEditor = loginPreferences.edit();

            saveLogin = loginPreferences.getBoolean("saveLogin", false);
            if (saveLogin == true) {
                inputEmail.setText(loginPreferences.getString("username", ""));
                inputPassword.setText(loginPreferences.getString("password", ""));
                rememberSwitch.setChecked(true);
            }


            return v;
        }


    private void verifyFromSQLite() {
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();


        if (email.isEmpty()) {
            layputInputEmail.setErrorEnabled(true);
            layputInputEmail.setError("Email field is required!");
            inputEmail.requestFocus();
        } else if (!isEmailValid(email)) {
            layputInputEmail.setErrorEnabled(true);
            layputInputEmail.setError("Invalid email address!");
            inputEmail.requestFocus();
        } else {
            clearError(1);
        }

        if (password.isEmpty()) {
            layputInputPassword.setErrorEnabled(true);
            layputInputPassword.setError("Password field is required!");
            inputPassword.requestFocus();
        } else {
            clearError(2);
        }

        if (withoutErrors()) {
            if (databaseHelper.checkUser(email.trim(), password.trim())) {

                if (rememberSwitch.isChecked()) {
                    loginPrefsEditor.putBoolean("saveLogin", true);
                    loginPrefsEditor.putString("username", email);
                    loginPrefsEditor.putString("password", password);
                    loginPrefsEditor.putBoolean("useNotif", true);
                    loginPrefsEditor.commit();
                } else {
                    loginPrefsEditor.clear();
                    loginPrefsEditor.putBoolean("useNotif", true);
                    loginPrefsEditor.commit();
                }

            Intent accountsIntent = new Intent(getActivity(), OptionActivity.class);
            accountsIntent.putExtra("EMAIL", email);
            emptyInputEditText();
            startActivity(accountsIntent);

                //startActivity(new Intent(getActivity(), OptionActivity.class));

                Toast.makeText(getActivity(), "Login Successful", Toast.LENGTH_SHORT).show();
            } else {
                // Snack Bar to show success message that record is wrong
                Toast.makeText(getActivity(), getString(R.string.error_valid_email_password), Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void emptyInputEditText() {
        inputEmail.setText(null);
        inputPassword.setText(null);
    }

    private void clearError(int field) {
        if (field == 1) {
            layputInputEmail.setError(null);
            layputInputEmail.setErrorEnabled(false);
        } else if (field == 2) {
            layputInputPassword.setError(null);
            layputInputPassword.setErrorEnabled(false);
        }
    }

    private boolean withoutErrors() {
        if (!layputInputEmail.isErrorEnabled() && !layputInputPassword.isErrorEnabled()) {
            return true;
        } else {
            return false;
        }
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                .matches();
    }
}
