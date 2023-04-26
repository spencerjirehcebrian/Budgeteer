package com.example.budgeteer;

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

public class SignUpFragment extends Fragment {

    private TextInputLayout layoutFirstName, layoutLastName, layoutEmail,layoutPassword;
    private TextInputEditText inputFirstName, inputLastName, inputEmail,inputPassword;

    private Button btnSignup;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.signup_fragment, container, false);


        layoutFirstName = v.findViewById(R.id.layout_signup_firstname);
        layoutLastName = v.findViewById(R.id.layout_signup_lastname);
        layoutEmail = v.findViewById(R.id.layout_login_email);
        layoutPassword = v.findViewById(R.id.layout_login_password);
        inputEmail = v.findViewById(R.id.login_email);
        inputPassword = v.findViewById(R.id.login_password);
        inputFirstName = v.findViewById(R.id.signup_firstname);
        inputLastName = v.findViewById(R.id.signup_lastname);
        btnSignup = v.findViewById(R.id.next);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postDataToSQLite();
            }
        });

        databaseHelper = new DatabaseHelper(getActivity());
        user = new User();

        return v;
    }


    private void postDataToSQLite() {

        String firstname = inputFirstName.getText().toString();
        String lastname = inputLastName.getText().toString();
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();

        if (password.isEmpty()) {
            layoutPassword.setErrorEnabled(true);
            layoutPassword.setError("Password field is required!");
            inputPassword.requestFocus();
        } else {
            clearError(4);
        }

        if (!isEmailValid(email)) {
            layoutEmail.setErrorEnabled(true);
            layoutEmail.setError("Invalid email address!");
            inputEmail.requestFocus();
        } else {
            clearError(3);
        }

        if (lastname.isEmpty()) {
            layoutLastName.setErrorEnabled(true);
            layoutLastName.setError("Last name is required!");
            inputLastName.requestFocus();
        }
        else {
            clearError(2);
        }

        if (firstname.isEmpty()) {
            layoutFirstName.setErrorEnabled(true);
            layoutFirstName.setError("First name is required!");
            inputFirstName.requestFocus();
        }
        else {
            clearError(1);
        }


        if (withoutErrors()) {

            try{
                if (!databaseHelper.checkUser(email.trim())) {
                    user.setFirstName(firstname.trim());
                    user.setLastName(firstname.trim());
                    user.setEmail(email.trim());
                    user.setPassword(password.trim());
                    databaseHelper.addUser(user);
                    // Snack Bar to show success message that record saved successfully
                    Toast.makeText(getActivity(), "Account Created", Toast.LENGTH_SHORT).show();
                    emptyInputEditText();
                } else {
                    // Snack Bar to show error message that record already exists
                    Toast.makeText(getActivity(), "Account Already Registered to Another User", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                // Snack Bar to show error message that record already exists
                Toast.makeText(getActivity(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }

        }
    }
    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        inputEmail.setText(null);
        inputPassword.setText(null);
        inputFirstName.setText(null);
        inputLastName.setText(null);
    }

    private void clearError(int field) {
        if (field == 1) {
            layoutFirstName.setError(null);
            layoutFirstName.setErrorEnabled(false);
        } else if (field == 2) {
            layoutLastName.setError(null);
            layoutLastName.setErrorEnabled(false);
        } else if (field == 3) {
            layoutEmail.setError(null);
            layoutEmail.setErrorEnabled(false);
        } else {
            layoutPassword.setError(null);
            layoutPassword.setErrorEnabled(false);
        }
    }

    private boolean withoutErrors() {
        if (!layoutEmail.isErrorEnabled() && !layoutEmail.isErrorEnabled()) {
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