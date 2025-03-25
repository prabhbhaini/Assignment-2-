package com.omdb.app.omdb;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.omdb.app.R;

import java.util.ArrayList;

public class OMDBDataViewModel extends ViewModel {
    public  MutableLiveData<OMDBData> movieItems;

    public OMDBDataViewModel(){
        movieItems = new MutableLiveData<>();
    }

    public void search(Context context, String term, String apiKey){
       // String apiKey =  getString(R.string.api_key);
        //showSearchResults(data);
        Log.d("App", term);
        OMDBHandler omdbHandler = new OMDBHandler(apiKey, new OMDBHandler.IComplete() {
            @Override
            public void onComplete(OMDBData data) {
                //showSearchResults(data);
                movieItems.postValue(data);
            }
        });
        omdbHandler.getSearch(context, term);
    }
}
