package com.mypath.tchalam.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mypath.tchalam.R;
import com.mypath.tchalam.models.Answer;
import com.mypath.tchalam.models.Quiz;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class UserScoreFragment extends Fragment {
    public static final String TAG = "UserScoreFragment";

    TextView tvScoreTotal;

    // TODO: Rename and change types of parameters

    public UserScoreFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tvFirstName = view.findViewById(R.id.tvFirstName);
        TextView tvLastName = view.findViewById(R.id.tvLastName);
        tvScoreTotal = view.findViewById(R.id.tvScoreTotal);

        ParseUser user = ParseUser.getCurrentUser();

        tvFirstName.setText(user.getString("firstname"));
        tvLastName.setText(user.getString("lastname"));

        queryQuiz();
    }

    private void queryQuiz() {
        ParseQuery<Quiz> quizQuery = ParseQuery.getQuery(Quiz.class);

        quizQuery.findInBackground((quizzes, e) -> {
            if (e != null) {
                Log.e(TAG, "Issue with getting Quiz", e);
                return;
            }

            int total = 0;
            queryAnswer(total,quizzes.size());
        });
    }

    private void queryAnswer(int total, int totalQuiz) {
        ParseQuery<Answer> answerQuery = ParseQuery.getQuery(Answer.class);
        answerQuery.include(Answer.KEY_USER);
        answerQuery.whereEqualTo(Answer.KEY_USER,ParseUser.getCurrentUser());

        answerQuery.findInBackground((answers, e) -> {
            if (e != null) {
                Log.e(TAG, "Issue with getting Answer", e);
                return;
            }

            int total_answer = total;

            for (Answer answer:answers) {
                total_answer = total_answer + answer.getScore();
            }

            int total_score= (totalQuiz * 100)/totalQuiz;
            int score = (total_answer * total_score) /totalQuiz;

            tvScoreTotal.setText(String.valueOf(score));
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_score, container, false);
    }
}