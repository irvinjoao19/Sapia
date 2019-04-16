package com.dsige.sapia.data.module;

import com.dsige.sapia.data.viewModel.PersonalViewModel;
import com.dsige.sapia.data.viewModel.ViewModelFactory;

import javax.inject.Singleton;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(PersonalViewModel.class)
    abstract ViewModel bindListViewModel(PersonalViewModel personalViewModel);


    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
