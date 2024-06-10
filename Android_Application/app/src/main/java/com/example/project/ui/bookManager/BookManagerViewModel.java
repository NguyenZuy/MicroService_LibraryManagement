package com.example.project.ui.bookManager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BookManagerViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public BookManagerViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is book manager");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
