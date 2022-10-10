package com.mypath.tchalam.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mypath.tchalam.R;
import com.mypath.tchalam.models.Quiz;


import java.util.List;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.ViewHolder> {
    private final List<Quiz> quizzes;
    private int option_select=0;

    public QuizAdapter(List<Quiz> quizzes) {
        this.quizzes = quizzes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quiz, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Quiz quiz = quizzes.get(position);
        option_select = 0;

        holder.tvQuestion.setText(quiz.getQuestion());
        holder.btOptionA.setText(quiz.getOptionA());
        holder.btOptionB.setText(quiz.getOptionB());
        holder.btOptionC.setText(quiz.getOptionC());
        holder.btOptionD.setText(quiz.getOptionD());

        holder.btOptionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.btOptionA.setBackgroundResource(R.drawable.selected_btn);
                holder.btOptionB.setBackgroundResource(R.drawable.unselected_btn);
                holder.btOptionC.setBackgroundResource(R.drawable.unselected_btn);
                holder.btOptionD.setBackgroundResource(R.drawable.unselected_btn);

                int option_select = 1;
                setOption_select(option_select);
            }
        });
        holder.btOptionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.btOptionA.setBackgroundResource(R.drawable.unselected_btn);
                holder.btOptionB.setBackgroundResource(R.drawable.selected_btn);
                holder.btOptionC.setBackgroundResource(R.drawable.unselected_btn);
                holder.btOptionD.setBackgroundResource(R.drawable.unselected_btn);

                int option_select = 2;
                setOption_select(option_select);
            }
        });
        holder.btOptionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.btOptionA.setBackgroundResource(R.drawable.unselected_btn);
                holder.btOptionB.setBackgroundResource(R.drawable.unselected_btn);
                holder.btOptionC.setBackgroundResource(R.drawable.selected_btn);
                holder.btOptionD.setBackgroundResource(R.drawable.unselected_btn);

                int option_select = 3;
                setOption_select(option_select);
            }
        });

        holder.btOptionD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.btOptionA.setBackgroundResource(R.drawable.unselected_btn);
                holder.btOptionB.setBackgroundResource(R.drawable.unselected_btn);
                holder.btOptionC.setBackgroundResource(R.drawable.unselected_btn);
                holder.btOptionD.setBackgroundResource(R.drawable.selected_btn);

                int option_select = 4;
                setOption_select(option_select);
            }
        });
    }

    private void setOption_select(int option_select) {
        this.option_select = option_select;
    }

    public int getOption_select()
    {
        return this.option_select;
    }

    @Override
    public int getItemCount() {
        return quizzes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuestion;
        Button btOptionA;
        Button btOptionB;
        Button btOptionC;
        Button btOptionD;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvQuestion = itemView.findViewById(R.id.tvQuestion);
            btOptionA = itemView.findViewById(R.id.optionA);
            btOptionB = itemView.findViewById(R.id.optionB);
            btOptionC = itemView.findViewById(R.id.optionC);
            btOptionD = itemView.findViewById(R.id.optionD);
        }
    }
}
