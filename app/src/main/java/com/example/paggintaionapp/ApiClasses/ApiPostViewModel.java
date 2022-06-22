package com.example.paggintaionapp.ApiClasses;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.paggintaionapp.Classes.Example;

public class ApiPostViewModel extends AndroidViewModel {

    private final Repository repository;

    public ApiPostViewModel(@NonNull Application application) {
        super(application);
        this.repository = new Repository(application);
    }

    public LiveData<Example> callPostAPIToActivity(int page, int size) {
        return repository.callPostAPI(page, size);
    }
}
