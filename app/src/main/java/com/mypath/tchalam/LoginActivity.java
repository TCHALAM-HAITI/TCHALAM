package com.mypath.tchalam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {
    public static final String TAG = "LoginActivity";

    private EditText etEmail;
    private EditText etPassword;
    private Button btLogin;
    private TextView tvSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (ParseUser.getCurrentUser() != null) {
            goMainActivity();
        }

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btLogin = findViewById(R.id.btLogin);
        tvSignUp = findViewById(R.id.tvSignUp);


        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (email.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Email Required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Password Required", Toast.LENGTH_SHORT).show();
                    return;
                }

                LoginUser(email, password);
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUpUser();
            }
        });
    }

    private void SignUpUser() {
        Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(i);
        finish();
    }

    private void LoginUser(String email, String password) {

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("email", email);

        query.getFirstInBackground(new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser object, ParseException e1) {
                if (e1 != null) {
//                    Toast.makeText(LoginActivity.this, "Erreur " + e1.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "done: "+e1.getMessage(),e1);
                    return;
                }
                String username = object.getUsername();

                ParseUser.logInInBackground(username, password, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e2) {
                        if (user != null) {
                            goMainActivity();
                        } else {
                            Toast.makeText(LoginActivity.this, "Échec de la connexion", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "Échec de la connexion: " + e2.getMessage(), e2);
                        }
                    }
                });
            }
        });
    }

    private void goMainActivity() {
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }


}