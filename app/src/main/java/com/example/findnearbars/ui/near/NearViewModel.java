package com.example.findnearbars.ui.near;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NearViewModel extends ViewModel {
    private MutableLiveData<String> mText;
    public NearViewModel(){
        mText = new MutableLiveData<>();
        mText.setValue("near Fragment");
    }

    public MutableLiveData<String> getmText() {
        return mText;
    }
}
