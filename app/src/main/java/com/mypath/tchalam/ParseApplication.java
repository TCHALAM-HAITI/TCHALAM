package com.mypath.tchalam;

import android.app.Application;

import com.parse.Parse;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("2vof0vJyL1LraQdME0iL9bzbTF53J5jfTup95DgI")
                .clientKey("yAu8Sen4ajbvfLbRAqLq8tWnk9c7xoxo7Db9V8SJ")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
