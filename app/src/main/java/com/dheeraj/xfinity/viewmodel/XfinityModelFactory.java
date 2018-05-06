package com.dheeraj.xfinity.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.dheeraj.xfinity.repository.XfinityRepository;

import javax.inject.Singleton;

@Singleton
public class XfinityModelFactory implements ViewModelProvider.Factory {

    private final XfinityRepository repository;

    public XfinityModelFactory(XfinityRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(ResultsViewModel.class))
            return (T) new ResultsViewModel(repository);
        else
            throw new IllegalArgumentException("ViewModel Not Found");

    }
}
