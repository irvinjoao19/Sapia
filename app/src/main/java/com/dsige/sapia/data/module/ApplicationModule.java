package com.dsige.sapia.data.module;


import java.util.Map;

import javax.inject.Provider;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {


    /**





     @Provides
     LoginContract.Presenter providerLoginPresenter(AppRepository appRepository) {
     return new LoginPresenter(appRepository);
     }

     @Provides
     PedidosContract.Presenter providerPedidosPresenter(AppRepository appRepository) {
     return new PedidosPresenter(appRepository);
     }

     @Provides
     MainContract.Presenter providerMainPresenter(AppRepository appRepository) {
     return new MainPresenter(appRepository);
     }

     @Provides
     ClienteContract.Presenter providerClientePresenter(AppRepository appRepository) {
     return new ClientePresenter(appRepository);
     }

     @Provides
     FileClienteContract.Presenter providerFileClientePresenter(AppRepository appRepository) {
     return new FileClientePresenter(appRepository);
     }

     @Provides
     RegisterContract.Presenter providerRegisterPresenter(AppRepository appRepository) {
     return new RegisterPresenter(appRepository);
     }

     @Provides
     ViewModelProvider.Factory provideViewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> creators) {
     return new ViewModelFactory(creators);
     }





     */

}