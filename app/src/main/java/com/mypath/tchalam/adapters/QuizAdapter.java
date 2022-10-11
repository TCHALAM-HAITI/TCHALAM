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
    private int option_select = -1;

    public int getOption_select() {
        return option_select;
    }

    public void setOption_select(int option_select) {
        this.option_select = option_select;
    }

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

        int questID = position;
        option_select = -1;

        holder.tvQuestion.setText(quiz.getQuestion());
        holder.btOptionA.setText(quiz.getOptionA());
        holder.btOptionB.setText(quiz.getOptionB());
        holder.btOptionC.setText(quiz.getOptionC());
        holder.btOptionD.setText(quiz.getOptionD());

        holder.btOptionA.setBackgroundResource(R.drawable.unselected_btn);
        holder.btOptionB.setBackgroundResource(R.drawable.unselected_btn);
        holder.btOptionC.setBackgroundResource(R.drawable.unselected_btn);
        holder.btOptionD.setBackgroundResource(R.drawable.unselected_btn);

        holder.btOptionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setOption_select(holder.selectOption(holder.btOptionA, 1));


            }
        });
        holder.btOptionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setOption_select(holder.selectOption(holder.btOptionB, 2));
            }
        });
        holder.btOptionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setOption_select(holder.selectOption(holder.btOptionC, 3));
            }
        });

        holder.btOptionD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setOption_select(holder.selectOption(holder.btOptionD, 4));
            }
        });
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
        Button btprev_select;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvQuestion = itemView.findViewById(R.id.tvQuestion);
            btOptionA = itemView.findViewById(R.id.optionA);
            btOptionB = itemView.findViewById(R.id.optionB);
            btOptionC = itemView.findViewById(R.id.optionC);
            btOptionD = itemView.findViewById(R.id.optionD);

            btprev_select = null;
        }

        private int selectOption(Button btn, int op_number) {
            if (btprev_select == null) {
                btn.setBackgroundResource(R.drawable.selected_btn);

                btprev_select = btn;
                return op_number;
            } else if (btprev_select.getId() == btn.getId()) {
                btn.setBackgroundResource(R.drawable.unselected_btn);

                btprev_select = null;
                return -1;
            } else {
                btprev_select.setBackgroundResource(R.drawable.unselected_btn);
                btn.setBackgroundResource(R.drawable.selected_btn);

                btprev_select = btn;
                return op_number;
            }
        }
    }
}
