package com.example.findnearbars.ui.search;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SearchViewModel extends ViewModel {
    private MutableLiveData<String> mText;
    public SearchViewModel(){
        mText = new MutableLiveData<>();
        mText.setValue("Search Fragment");
    }

    public MutableLiveData<String> getmText() {
        return mText;
    }

}
