package com.mypath.tchalam.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Subject")
public class Subject extends ParseObject {
    public static final String KEY_SUBJECT = "Subject";

    public String getSubject() {
        return getString(KEY_SUBJECT);
    }
}
