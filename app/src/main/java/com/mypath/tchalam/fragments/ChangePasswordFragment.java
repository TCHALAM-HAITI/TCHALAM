package com.mypath.tchalam.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mypath.tchalam.LoginActivity;
import com.mypath.tchalam.R;

import com.parse.ParseUser;


/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class ChangePasswordFragment extends Fragment {
    public static final String TAG = "ChangePasswordFragment";

    EditText etPassword1;
    EditText etPassword2;
    Button btmodifier;

    // TODO: Rename and change types of parameters

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tvFirstName = view.findViewById(R.id.tvFirstName);
        TextView tvLastName = view.findViewById(R.id.tvLastName);

        etPassword1 = view.findViewById(R.id.etPassword1);
        etPassword2 = view.findViewById(R.id.etPassword2);
        btmodifier = view.findViewById(R.id.btmodifier);


        ParseUser user = ParseUser.getCurrentUser();

        tvFirstName.setText(user.getString("firstname"));
        tvLastName.setText(user.getString("lastname"));

        btmodifier.setOnClickListener(view1 -> {
            String nouv_pswd1 = etPassword1.getText().toString();
            String nouv_pswd2 = etPassword2.getText().toString();
            if (nouv_pswd1.equals(nouv_pswd2)) {

                if (user != null) {
                    user.setPassword(nouv_pswd1);
                    user.saveInBackground(e -> {
                        if (e != null) {
                            Log.e(TAG, "done: " + e.getMessage(), e);
                            return;
                        }
                        ParseUser.logOutInBackground(e1 -> {
                            if (e1 == null) {
                                Intent i = new Intent(view1.getContext(), LoginActivity.class);
                                view1.getContext().startActivity(i);
                            }
                        });


                    });
                }
            } else {
                Toast.makeText(view1.getContext(), "le mot de passe doit être identique", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_password, container, false);
    }
}