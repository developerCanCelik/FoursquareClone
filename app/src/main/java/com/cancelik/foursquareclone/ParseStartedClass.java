package com.cancelik.foursquareclone;

import android.app.Application;

import com.parse.Parse;

public class ParseStartedClass extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //Parse hangi durumlarda logcat'a düşecek onu belirleyebiliriz.
        Parse.setLogLevel(Parse.LOG_LEVEL_ERROR);
        //Firebase deki hazır dosyalara benzer kurduğumuz server bilgilerini yazacağız
        Parse.initialize(new Parse.Configuration.Builder(this)
                    .applicationId("Apı Key")
                    .clientKey("Client Key")
                    .server("https://parseapi.back4app.com/")
                    .build());
    }
}
