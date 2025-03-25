package com.omdb.app.adapters;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.omdb.app.omdb.OMDBData;


public class OMDBAdapterViewModel extends ViewModel {
    public MutableLiveData<OMDBData> movieItem;
    private OMDBData current;

    public OMDBAdapterViewModel(){
        movieItem = new MutableLiveData<>();
    }

    public void post(OMDBData item){
        current = item;
        movieItem.postValue(item);
    }

    public OMDBData getCurrent(){
        return current;
    }
}
