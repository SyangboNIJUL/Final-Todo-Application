package com.example.todolist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    private EditText txtEmail;
    private EditText txtPassword;
    private Button btnLogin;

    AlertDialog.Builder mAlterDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        txtEmail=findViewById(R.id.email);
        txtPassword=findViewById(R.id.pass);
        btnLogin=findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = txtEmail.getText().toString();
                String password = txtPassword.getText().toString();

                if (userName.equals("")) {
                    txtEmail.setError("Username required");
                    txtEmail.requestFocus();
                } else if (password.equals("")) {
                    txtPassword.setError("Password Required");
                    txtPassword.requestFocus();
                } else {
                    if (userName.equals("nijul") && password.equals("lama")) {
                        SharedPreferences preferences = getApplicationContext().getSharedPreferences("todo_pref", 0);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean("authentication", true);
                        editor.commit();
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        txtEmail.setError("Invalid username or password");
                    }
                }
            }
        });
    }
}