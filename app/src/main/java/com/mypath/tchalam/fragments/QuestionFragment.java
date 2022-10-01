package com.mypath.tchalam.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mypath.tchalam.R;
import com.mypath.tchalam.adapters.QuestionAdapter;
import com.mypath.tchalam.adapters.SubjectAdapter;
import com.mypath.tchalam.models.Question;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;


import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    public static final String TAG = "QuestionFragment";

    // TODO: Rename and change types of parameters
    private String mParam1;

    private TextView tvSubjectName;
    private TextView tvQuestionID;
    private RecyclerView rvQuestion;
    private List<Question> allQuestion;
    private QuestionAdapter adapter;
    private int quesID;


    public QuestionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment QuestionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuestionFragment newInstance(String param1) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            Log.i("TAG", "onViewCreated: " + mParam1);
        }

        quesID = 0;
        tvSubjectName = view.findViewById(R.id.tvSubject_Name);
        tvSubjectName.setText(mParam1);

        tvQuestionID = view.findViewById(R.id.tvQuestionID);
        rvQuestion = view.findViewById(R.id.rvQuestion);

        allQuestion = new ArrayList<>();

        adapter = new QuestionAdapter(allQuestion);

        rvQuestion.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvQuestion.setLayoutManager(linearLayoutManager);

        queryQuestion();


        setSnapHelper();
    }

    private void setSnapHelper() {
        SnapHelper snapHelper = new PagerSnapHelper();

        snapHelper.attachToRecyclerView(rvQuestion);
        rvQuestion.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                View view = snapHelper.findSnapView(recyclerView.getLayoutManager());
                quesID = recyclerView.getLayoutManager().getPosition(view);

                tvQuestionID.setText(String.valueOf(quesID+1)+"/"+String.valueOf(allQuestion.size()));
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void queryQuestion() {
        ParseQuery<Question> query = ParseQuery.getQuery(Question.class);
        query.include(Question.KEY_SUBJECT);
        query.setLimit(20);

        query.findInBackground(new FindCallback<Question>() {
            @Override
            public void done(List<Question> questions, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting Question", e);
                    return;
                }
                for (Question question : questions) {
                    Log.i(TAG, "Question: " + question.getQuestion());
                }
                tvQuestionID.setText("1/"+String.valueOf(questions.size()));
                allQuestion.addAll(questions);
                adapter.notifyDataSetChanged();
            }
        });


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_question, container, false);
    }
}