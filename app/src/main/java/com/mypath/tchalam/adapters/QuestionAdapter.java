package com.mypath.tchalam.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mypath.tchalam.R;
import com.mypath.tchalam.models.Question;


import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {
    private final List<Question> questions;

    public QuestionAdapter(List<Question> questions) {
        this.questions = questions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Question question = questions.get(position);
        int answer = question.getAnswer();

        holder.tvQuestion.setText(question.getQuestion());
        holder.btOptionA.setText(question.getOptionA());
        holder.btOptionB.setText(question.getOptionB());
        holder.btOptionC.setText(question.getOptionC());
        holder.btOptionD.setText(question.getOptionD());

        holder.btOptionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.btOptionA.setBackgroundResource(R.drawable.selected_btn);
                holder.btOptionB.setBackgroundResource(R.drawable.unselected_btn);
                holder.btOptionC.setBackgroundResource(R.drawable.unselected_btn);
                holder.btOptionD.setBackgroundResource(R.drawable.unselected_btn);
            }
        });
        holder.btOptionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.btOptionA.setBackgroundResource(R.drawable.unselected_btn);
                holder.btOptionB.setBackgroundResource(R.drawable.selected_btn);
                holder.btOptionC.setBackgroundResource(R.drawable.unselected_btn);
                holder.btOptionD.setBackgroundResource(R.drawable.unselected_btn);
            }
        });
        holder.btOptionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.btOptionA.setBackgroundResource(R.drawable.unselected_btn);
                holder.btOptionB.setBackgroundResource(R.drawable.unselected_btn);
                holder.btOptionC.setBackgroundResource(R.drawable.selected_btn);
                holder.btOptionD.setBackgroundResource(R.drawable.unselected_btn);


            }
        });

        holder.btOptionD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.btOptionA.setBackgroundResource(R.drawable.unselected_btn);
                holder.btOptionB.setBackgroundResource(R.drawable.unselected_btn);
                holder.btOptionC.setBackgroundResource(R.drawable.unselected_btn);
                holder.btOptionD.setBackgroundResource(R.drawable.selected_btn);

            }
        });
    }

    @Override
    public int getItemCount() {
        return questions.size();
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
