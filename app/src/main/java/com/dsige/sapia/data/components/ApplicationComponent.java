package com.dsige.sapia.data.components;

import android.app.Application;

import com.dsige.sapia.data.App;
import com.dsige.sapia.data.module.ActivityBindingModule;
import com.dsige.sapia.data.module.DataBaseModule;
import com.dsige.sapia.data.module.RetrofitModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class,
        ActivityBindingModule.class,
        DataBaseModule.class,
        RetrofitModule.class})
public interface ApplicationComponent extends AndroidInjector<App> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        ApplicationComponent.Builder application(Application application);

        ApplicationComponent build();
    }
}