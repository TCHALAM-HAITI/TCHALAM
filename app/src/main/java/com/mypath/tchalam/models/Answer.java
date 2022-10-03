package com.mypath.tchalam.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Answer")
public class Answer extends ParseObject {
    public static final String KEY_USER = "user";
    public static final String KEY_QUIZ = "quiz";
    public static final String KEY_SCORE = "score";

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

    public ParseObject getQuiz() {
        return getParseObject(KEY_QUIZ);
    }

    public void setQuiz(ParseObject quiz) {
        put(KEY_QUIZ, quiz);
    }

    public int getScore() {
        return getInt(KEY_SCORE);
    }

    public void setScore(int score) {
        put(KEY_SCORE, score);
    }

}
