package com.mypath.tchalam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.CountCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {
    public static final String TAG = "SignUpActivity";

    private EditText etFirstname;
    private EditText etLastname;
    private EditText etEmail;
    private EditText etPassword;
    private Button btSignUp;
    private TextView tvConnect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //add icon back with toolbar
        Toolbar myToolba=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolba);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //block inflate
        etFirstname = findViewById(R.id.etFirstname);
        etLastname = findViewById(R.id.etLastname);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btSignUp = findViewById(R.id.btSignUp);
        tvConnect=findViewById(R.id.tvConnect);

        //Method to get on page Login
        tvConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        btSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String firstname = etFirstname.getText().toString().trim();
                String lastname = etLastname.getText().toString().trim();

                if (email.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Email Obligatoire", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Mot de passe Obligatoire", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (firstname.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Prénom Obligatoire", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (lastname.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Nom Obligatoire", Toast.LENGTH_SHORT).show();
                    return;
                }

                SignUp_User(email, password, lastname, firstname);

            }
        });


    }

    private void SignUp_User(String email, String password, String lastname, String firstname) {
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("email", email);

        query.getFirstInBackground(new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser object, ParseException e) {
                if (e == null) {
                    Toast.makeText(SignUpActivity.this, "Erreur : " + object.getEmail() + " Existe", Toast.LENGTH_SHORT).show();
                    return;
                }

                String username = lastname+" "+firstname;
                Log.i(TAG, "done: " + username);

                ParseUser user = new ParseUser();

                user.setUsername(username);
                user.setPassword(password);
                user.setEmail(email);

                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e != null) {
                            Log.e(TAG, "Échec: ", e);
                            return;
                        }

                        ParseUser user1 = ParseUser.getCurrentUser();
                        user1.put("lastname", lastname);
                        user1.put("firstname", firstname);

                        user1.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e != null) return;
                                goMainActivity();
                            }
                        });

                    }
                });
            }
        });

    }

    private void goMainActivity() {
        Intent i = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}