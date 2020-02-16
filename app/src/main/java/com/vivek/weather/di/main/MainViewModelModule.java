package com.vivek.weather.di.main;

import androidx.lifecycle.ViewModel;

import com.vivek.weather.di.ViewModelKey;
import com.vivek.weather.ui.main.viewmodel.MainViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class MainViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    public abstract ViewModel bindMainViewModel(MainViewModel viewModel);
}
