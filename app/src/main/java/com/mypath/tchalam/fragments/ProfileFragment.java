package com.mypath.tchalam.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mypath.tchalam.LoginActivity;
import com.mypath.tchalam.R;
import com.parse.ParseUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //inflate
        TextView tvFirstName = view.findViewById(R.id.tvFirstName);
        TextView tvLastName = view.findViewById(R.id.tvLastName);
        RelativeLayout lscore = view.findViewById(R.id.Lscore);
        RelativeLayout LupdatePass = view.findViewById(R.id.LupdatePass);
        RelativeLayout Llogout = view.findViewById(R.id.Llogout);

        lscore.setOnClickListener(view1 -> {
            AppCompatActivity activity = (AppCompatActivity) view1.getContext();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.flContainer, new UserScoreFragment()).addToBackStack(null).commit();
        });
        LupdatePass.setOnClickListener(view12 -> {
            AppCompatActivity activity = (AppCompatActivity) view12.getContext();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.flContainer, new ChangePasswordFragment()).addToBackStack(null).commit();
        });

        Llogout.setOnClickListener(view13 -> ParseUser.logOutInBackground(e -> {
            if (e == null) {
                Intent i = new Intent(getActivity(), LoginActivity.class);
                startActivity(i);
            }
        }));

        //call firstname and lastname of current user
        ParseUser user = ParseUser.getCurrentUser();

        tvFirstName.setText(user.getString("firstname"));
        tvLastName.setText(user.getString("lastname"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
}