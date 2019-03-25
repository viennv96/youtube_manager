package com.example.youtubermanager.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.youtubermanager.R;
import com.example.youtubermanager.User;
import com.example.youtubermanager.UserModel;
import com.example.youtubermanager.channel.MainActivity;

import java.sql.SQLException;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");

    private TextInputLayout textInputEmail;
    private TextInputLayout textInputUsername;
    private TextInputLayout textInputPassword;
    private TextInputLayout textInputConfirmPassword;
    private Button btnSignup;
    private UserModel userModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        userModel = new UserModel();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        btnSignup = findViewById(R.id.btnSignUp);
        textInputEmail = findViewById(R.id.text_input_email);
        textInputUsername = findViewById(R.id.text_input_username);
        textInputPassword = findViewById(R.id.text_input_password);
        textInputConfirmPassword = findViewById(R.id.text_input_confirm_password);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateEmail() | !validateUsername() | !validatePassword() | !validateConfrimPassword()) {
                    return;
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    String emailInput = textInputEmail.getEditText().getText().toString().trim();
                    String usernameInput = textInputUsername.getEditText().getText().toString().trim();
                    String passwordInput = textInputPassword.getEditText().getText().toString().trim();
                    User user = new User (usernameInput,emailInput,passwordInput,0,0,"");
                    try {
                        userModel.Insert(user);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    intent.putExtra("user",user);
                    startActivity(intent);
                }
            }
        });
    }
    private boolean validateEmail() {
        String emailInput = textInputEmail.getEditText().getText().toString().trim();

        if (emailInput.isEmpty()) {
            textInputEmail.setError("Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            textInputEmail.setError("Please enter a valid email address");
            return false;
        } else {
            textInputEmail.setError(null);
            return true;
        }
    }

    private boolean validateUsername() {
        String usernameInput = textInputUsername.getEditText().getText().toString().trim();

        if (usernameInput.isEmpty()) {
            textInputUsername.setError("Field can't be empty");
            return false;
        } else if (usernameInput.length() > 15) {
            textInputUsername.setError("Username too long");
            return false;
        } else {
            textInputUsername.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String passwordInput = textInputPassword.getEditText().getText().toString().trim();

        if (passwordInput.isEmpty()) {
            textInputPassword.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            textInputPassword.setError("Password too weak");
            return false;
        } else {
            textInputPassword.setError(null);
            return true;
        }
    }
    protected  boolean validateConfrimPassword(){
        String ConfirmPassword = textInputConfirmPassword.getEditText().getText().toString().trim();
        String passwordInput = textInputPassword.getEditText().getText().toString().trim();
        if (ConfirmPassword.isEmpty()) {
            textInputConfirmPassword.setError("Field can't be empty");
            return false;
        } else if (!ConfirmPassword.equals(passwordInput)) {
            textInputConfirmPassword.setError("Re-password doesn't match password");
            return false;
        } else {
            textInputConfirmPassword.setError(null);
            return true;
        }
    }

}
