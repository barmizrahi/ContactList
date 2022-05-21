package com.example.contactList;

import android.app.Application;

public class App extends Application {
    public static ViewModel viewModel;
    @Override
    public void onCreate() {
        super.onCreate();
        MSPV3.initHelper(this);
        viewModel = new ViewModel(this);
    }
}