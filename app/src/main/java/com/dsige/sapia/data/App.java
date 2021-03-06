package com.dsige.sapia.data;



import com.dsige.sapia.data.components.DaggerApplicationComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class App extends DaggerApplication {

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerApplicationComponent
                .builder()
                .application(this)
                .build();
    }
}