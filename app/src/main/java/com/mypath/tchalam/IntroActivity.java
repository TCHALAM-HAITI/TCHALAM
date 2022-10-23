package com.mypath.tchalam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.parse.ParseUser;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        //if user is already connect
        if (ParseUser.getCurrentUser() != null) {
            goMainActivity();
        }
        //inflate
        Button btnContinue = findViewById(R.id.btnContinue);

        btnContinue.setOnClickListener(view -> GoToLogin());
    }

    private void goMainActivity() {
        Intent i = new Intent(IntroActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    private void GoToLogin() {
        Intent i = new Intent(IntroActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }
}