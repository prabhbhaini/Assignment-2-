package com.omdb.app.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.omdb.app.R;
import com.omdb.app.adapters.OMDBAdapterViewModel;
import com.omdb.app.databinding.FragmentDetailsBinding;
import com.omdb.app.databinding.FragmentSearchBinding;
import com.omdb.app.omdb.OMDBData;
import com.omdb.app.omdb.OMDBDataViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Details extends Fragment {
    FragmentDetailsBinding binding;
    OMDBAdapterViewModel viewModel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // inflating the layout of the fragment
        // and returning the view component
        binding = FragmentDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getActivity() == null) { return; }
        viewModel  = new ViewModelProvider(getActivity()).get(OMDBAdapterViewModel.class);
        viewModel.movieItem.observe(getViewLifecycleOwner(), this::showSearchResults);
    }

    @Override
    public void onResume() {
        super.onResume();


        Log.d("App", "resume "  + viewModel.getCurrent());
    }

    private void showSearchResults(OMDBData omdbData) {
        Log.d("App", "details observer");
        binding.tvTitle.setText(omdbData.getTitle());
        binding.tvStudio.setText(omdbData.getStudio());
        binding.tvDescription.setText(omdbData.getDescription());
        binding.tvYear.setText(String.valueOf(omdbData.getYear()));


        Picasso.get().load(omdbData.getPoster()).into(binding.imgPoster);
    }

}
