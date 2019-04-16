package com.dsige.sapia.data.module;

import com.dsige.sapia.ui.activities.PersonalActivity;
import com.dsige.sapia.ui.activities.RegisterPersonalActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {

    @ContributesAndroidInjector
    abstract PersonalActivity bindPersonalActivity();

    @ContributesAndroidInjector
    abstract RegisterPersonalActivity bindRegisterPersonalActivity();


    /**
     *
     @ContributesAndroidInjector(modules = {FragmentBindingModule.class})
     abstract MainActivity bindMainActivity();


     @ContributesAndroidInjector
     abstract RegisterClientActivity bindFileRegisterClientActivity();

     */

}