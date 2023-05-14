package com.art4muslimt.artfooddelegate.ui;

import android.app.Application;

import com.art4muslimt.artfooddelegate.dagger.component.ApplicationComponent;
import com.art4muslimt.artfooddelegate.dagger.component.DaggerApplicationComponent;
import com.art4muslimt.artfooddelegate.dagger.modules.ApplicationModule;


public class ArtFood extends Application {

    private ApplicationComponent applicationComponent;


    @Override
    public void onCreate() {
        super.onCreate();
        initDagger();
    }

    private void initDagger() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule())
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

}
