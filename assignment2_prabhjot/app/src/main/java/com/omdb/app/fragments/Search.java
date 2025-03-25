package com.omdb.app.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.omdb.app.MainActivity;
import com.omdb.app.R;
import com.omdb.app.adapters.OMDBAdapter;
import com.omdb.app.adapters.OMDBAdapterViewModel;
import com.omdb.app.databinding.FragmentSearchBinding;
import com.omdb.app.omdb.OMDBData;
import com.omdb.app.omdb.OMDBDataViewModel;

import java.util.ArrayList;

public class Search extends Fragment  {

    FragmentSearchBinding binding;
    OMDBDataViewModel searchModel;
    OMDBAdapterViewModel posClickModel;
    OMDBAdapter omdbAdapter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // inflating the layout of the fragment
        // and returning the view component
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getActivity() == null) { return; }
        showSearchResults();

        searchModel = new ViewModelProvider(getActivity()).get(OMDBDataViewModel.class);
        searchModel.movieItems.observe(getViewLifecycleOwner(), this::updateSearchResults);

        String apiKey = getActivity().getString(R.string.api_key);

        binding.btnSearch.setOnClickListener(v -> {
            if(binding.edtTerm.getText() == null){ return ;}
            String term = binding.edtTerm.getText().toString();
            if(term.length() == 0) { return; }

            if(omdbAdapter != null){
                omdbAdapter.clear();
            }

            Toast.makeText(getActivity(), "Searching...", Toast.LENGTH_SHORT).show();
            searchModel.search(getContext(), term, apiKey);
        });

    }

    private void updateSearchResults(OMDBData omdbData) {
        if(omdbAdapter == null){return; }
        omdbAdapter.add(omdbData);
        Log.d("App", "Add " + omdbData.getTitle());

    }

    public void showSearchResults(){

        binding.rvOmdb.setLayoutManager(new LinearLayoutManager(getContext()));

        // Set Adapter
        omdbAdapter = new OMDBAdapter(new OMDBAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(OMDBData item, int pos) {
                //switch fragment

                MainActivity activity =  (MainActivity)getActivity();
                if(activity==null){ return ; }
                activity.showDetailsFragment(item);

            }
        });
        binding.rvOmdb.setAdapter(omdbAdapter);

    }
}
