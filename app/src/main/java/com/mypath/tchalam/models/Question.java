package com.mypath.tchalam.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Quiz")
public class Question extends ParseObject {
    public static final String KEY_QUESTION = "question";
    public static final String KEY_A = "A";
    public static final String KEY_B = "B";
    public static final String KEY_C = "C";
    public static final String KEY_D = "D";
    public static final String  KEY_ANSWER = "answer";
    public static final String KEY_SUBJECT = "subject";

    public String getQuestion(){ return getString(KEY_QUESTION);  }
    public String getOptionA(){ return getString(KEY_A);  }
    public String getOptionB(){ return getString(KEY_B);  }
    public String getOptionC(){ return getString(KEY_C);  }
    public String getOptionD(){ return getString(KEY_D);  }
    public int getAnswer(){ return getInt(KEY_ANSWER);  }
    public ParseObject getObject(){return  getParseObject(KEY_SUBJECT);}
}
