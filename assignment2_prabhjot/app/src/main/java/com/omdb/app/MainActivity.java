package com.omdb.app;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.omdb.app.adapters.OMDBAdapter;
import com.omdb.app.adapters.OMDBAdapterViewModel;
import com.omdb.app.databinding.ActivityMainBinding;
import com.omdb.app.fragments.Details;
import com.omdb.app.fragments.Search;
import com.omdb.app.omdb.OMDBData;
import com.omdb.app.omdb.OMDBHandler;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    OMDBHandler omdbHandler;
    Fragment[] fragments;
    int current_page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        fragments = new Fragment[]{
                new Search(),
                new Details()
        };


        showSearchFragment();
    }


    void showFragment(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();

        // fragment transaction to add or replace
        // fragments while activity is running
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.ll_container, fragment);

        // making a commit after the transaction
        // to assure that the change is effective
        fragmentTransaction.commit();
    }

    public void showSearchFragment(){
        current_page = 1;
        showFragment(fragments[0]);
    }

    public void showDetailsFragment(OMDBData result){
        OMDBAdapterViewModel posClickModel = new ViewModelProvider(this).get(OMDBAdapterViewModel.class);
        posClickModel.post(result);

        current_page = 2;
        showFragment(fragments[1]);



    }

    @Override
    public void onBackPressed() {
        if(current_page == 2){
            showSearchFragment();
        } else {
            super.onBackPressed();
        }
    }
}