package com.mypath.tchalam.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mypath.tchalam.R;
import com.mypath.tchalam.adapters.SubjectAdapter;
import com.mypath.tchalam.models.Subject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubjectFragment extends Fragment {
    public static final String TAG = "SubjectFragment";

    private List<Subject> allSubject;
    private SubjectAdapter adapter;

    public SubjectFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvSubject = view.findViewById(R.id.rvSubject);

        allSubject = new ArrayList<>();

        adapter = new SubjectAdapter(allSubject);

        rvSubject.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvSubject.setLayoutManager(linearLayoutManager);

        querySubject();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_subject, container, false);
    }

    @SuppressLint("NotifyDataSetChanged")
    protected void querySubject() {
        ParseQuery<Subject> query = ParseQuery.getQuery(Subject.class);
        query.setLimit(20);

        query.findInBackground((subjects, e) -> {
            if (e != null) {
                Log.e(TAG, "Issue with getting Subject", e);
                return;
            }
            for (Subject subject : subjects) {
                Log.i(TAG, "Subjects: " + subject.getSubject());
            }

            allSubject.addAll(subjects);
            adapter.notifyDataSetChanged();
        });
    }
}