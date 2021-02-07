package com.example.findnearbars.ui.favourite;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FavouriteViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    public FavouriteViewModel(){
        mText = new MutableLiveData<>();
        mText.setValue("Favourite Fragment");
    }

    public MutableLiveData<String> getmText() {
        return mText;
    }
}
