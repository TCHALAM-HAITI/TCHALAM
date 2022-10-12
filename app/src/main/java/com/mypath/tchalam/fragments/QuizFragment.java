package com.mypath.tchalam.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mypath.tchalam.R;
import com.mypath.tchalam.adapters.QuizAdapter;
import com.mypath.tchalam.models.Answer;
import com.mypath.tchalam.models.Quiz;
import com.mypath.tchalam.models.Subject;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuizFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuizFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    public static final String TAG = "QuizFragment";

    // TODO: Rename and change types of parameters
    private String mParam1;

    private TextView tvQuestionID;
    private Button bt_prev;
    private Button bt_next;
    private Button bt_submit;
    private RecyclerView rvQuestion;
    private List<Quiz> allQuiz;
    private QuizAdapter adapter;
    private int quesID;
    private int total_answer;
    private ArrayList<List<String>> myArray;


    public QuizFragment() {
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
    public static QuizFragment newInstance(String param1) {
        QuizFragment fragment = new QuizFragment();
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
        total_answer = 0;

        TextView tvSubjectName = view.findViewById(R.id.tvSubject_Name);
        bt_prev = view.findViewById(R.id.bt_prev);
        bt_next = view.findViewById(R.id.bt_next);
        bt_submit = view.findViewById(R.id.bt_submit);

        bt_prev.setVisibility(View.INVISIBLE);
        bt_next.setVisibility(View.INVISIBLE);
        bt_submit.setVisibility(View.INVISIBLE);

        if (quesID == 0)
            bt_next.setVisibility(View.VISIBLE);

        tvSubjectName.setText(mParam1);

        tvQuestionID = view.findViewById(R.id.tvQuestionID);
        rvQuestion = view.findViewById(R.id.rvQuestion);

        allQuiz = new ArrayList<>();
        myArray = new ArrayList<>();


        adapter = new QuizAdapter(allQuiz);

        rvQuestion.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvQuestion.setLayoutManager(linearLayoutManager);

        queryQuestion();
        setSnapHelper();
        setClickListener();
    }

    private void setClickListener() {
        bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int select_answer = adapter.getOption_select();
                Quiz quiz = allQuiz.get(quesID);

                if (quiz.getAnswer() == select_answer) {
                    Log.i(TAG, "onClick: " + select_answer);
                    total_answer += 1;
                    ParseUser user = ParseUser.getCurrentUser();
                    int score = 1;
                    queryAnswer(quiz, user, score);
                }

                if (quesID == 0) {
                    bt_submit.setVisibility(View.INVISIBLE);
                    bt_next.setVisibility(View.VISIBLE);
                    bt_prev.setVisibility(View.INVISIBLE);
                } else if (quesID == allQuiz.size() - 1) {
                    bt_submit.setVisibility(View.VISIBLE);
                    bt_next.setVisibility(View.INVISIBLE);
                    bt_prev.setVisibility(View.VISIBLE);
                } else {
                    bt_submit.setVisibility(View.INVISIBLE);
                    bt_next.setVisibility(View.VISIBLE);
                    bt_prev.setVisibility(View.VISIBLE);
                }
                rvQuestion.smoothScrollToPosition(quesID + 1);
            }
        });

        bt_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                total_answer -= 1;
                if (quesID == 0) {
                    bt_submit.setVisibility(View.INVISIBLE);
                    bt_next.setVisibility(View.VISIBLE);
                    bt_prev.setVisibility(View.INVISIBLE);
                } else if (quesID == allQuiz.size() - 1) {
                    bt_submit.setVisibility(View.VISIBLE);
                    bt_next.setVisibility(View.INVISIBLE);
                    bt_prev.setVisibility(View.VISIBLE);
                } else {
                    bt_submit.setVisibility(View.INVISIBLE);
                    bt_next.setVisibility(View.VISIBLE);
                    bt_prev.setVisibility(View.VISIBLE);
                }

                if (quesID > 0)
                    rvQuestion.smoothScrollToPosition(quesID - 1);
            }
        });

        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int select_answer = adapter.getOption_select();
                Quiz quiz = allQuiz.get(quesID);

                if (quiz.getAnswer() == select_answer) {
                    Log.i(TAG, "onClick: " + select_answer);
                    total_answer += 1;

                    ParseUser user = ParseUser.getCurrentUser();
                    int score = 1;
                    queryAnswer(quiz, user, score);
                }

                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.flContainer, ScoreFragment.newInstance(String.valueOf(total_answer), String.valueOf(allQuiz.size()))).addToBackStack(null).commit();

            }
        });
    }

    private void queryAnswer(Quiz quiz, ParseUser user, int score) {
        ParseQuery<Answer> queryAnswer = ParseQuery.getQuery(Answer.class);
        queryAnswer.whereEqualTo("quiz", quiz);
        queryAnswer.whereEqualTo("user", user);

        queryAnswer.findInBackground(new FindCallback<Answer>() {
            @Override
            public void done(List<Answer> objects, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Erreur", e);
                    return;
                }

                if (objects.size() == 0) {
                    Answer answer = new Answer();

                    answer.setQuiz(quiz);
                    answer.setUser(user);
                    answer.setScore(score);

                    answer.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e != null) {
                                Log.i(TAG, "Erreur de sauvegarde");
                            }
                            Log.i(TAG, "Answer Sauvegarder");
                        }
                    });
                }
            }
        });


    }

    private void setSnapHelper() {
        SnapHelper snapHelper = new PagerSnapHelper();

        snapHelper.attachToRecyclerView(rvQuestion);
        rvQuestion.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                View view = snapHelper.findSnapView(recyclerView.getLayoutManager());
                assert view != null;

                quesID = Objects.requireNonNull(recyclerView.getLayoutManager()).getPosition(view);
//                Log.i(TAG, "onScrollStateChanged: " + quesID);

                tvQuestionID.setText(quesID + 1 + "/" + allQuiz.size());

                if (quesID == 0) {
                    bt_submit.setVisibility(View.INVISIBLE);
                    bt_next.setVisibility(View.VISIBLE);
                    bt_prev.setVisibility(View.INVISIBLE);
                } else if (quesID == allQuiz.size() - 1) {
                    bt_submit.setVisibility(View.VISIBLE);
                    bt_next.setVisibility(View.INVISIBLE);
                    bt_prev.setVisibility(View.VISIBLE);
                } else {
                    bt_submit.setVisibility(View.INVISIBLE);
                    bt_next.setVisibility(View.VISIBLE);
                    bt_prev.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void queryQuestion() {
        ParseQuery<Subject> subjectQuery = ParseQuery.getQuery(Subject.class);
        subjectQuery.whereEqualTo("Subject", mParam1);

        ParseQuery<Quiz> questionQuery = ParseQuery.getQuery(Quiz.class);

        questionQuery.whereMatchesQuery("subject", subjectQuery);
        questionQuery.include(Quiz.KEY_SUBJECT);

        questionQuery.findInBackground(new FindCallback<Quiz>() {
            @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
            @Override
            public void done(List<Quiz> quizzes, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting Quiz", e);
                    return;
                }
                for (Quiz quiz : quizzes) {
                    Log.i(TAG, "Quiz: " + quiz.getQuestion());
                }
                tvQuestionID.setText(quesID + 1 + "/" + quizzes.size());
                allQuiz.addAll(quizzes);
                adapter.notifyDataSetChanged();
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz, container, false);
    }
}