package com.dheeraj.xfinity.view;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.dheeraj.xfinity.R;
import com.dheeraj.xfinity.XfinityApplication;
import com.dheeraj.xfinity.databinding.ActivityLandingBinding;
import com.dheeraj.xfinity.viewmodel.ResultsViewModel;

import javax.inject.Inject;

public class LandingActivity extends AppCompatActivity{

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    ActivityLandingBinding binding;
    ResultsViewModel resultsViewModel;
    CharactersFragment charactersFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ((XfinityApplication) getApplication())
                .getApplicationComponent()
                .inject(this);
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_landing);
        setSupportActionBar((Toolbar) binding.toolbar);

        resultsViewModel = ViewModelProviders.of(this,viewModelFactory).get(ResultsViewModel.class);

        charactersFragment = (CharactersFragment) getSupportFragmentManager().findFragmentByTag(CharactersFragment.class.getName());
        if(charactersFragment == null)
            charactersFragment = CharactersFragment.newInstance();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, charactersFragment,CharactersFragment.class.getName()).addToBackStack(CharactersFragment.class.getName());
        fragmentTransaction.commit();

        // Show detail view if device is a tablet
        if(getResources().getBoolean(R.bool.isTablet)){
            CharactersDetailFragment detailFragment = CharactersDetailFragment.newInstance();
            FragmentTransaction fragmentTransactions = getSupportFragmentManager().beginTransaction();
            fragmentTransactions.replace(R.id.container_detail,detailFragment,CharactersDetailFragment.class.getName());
            fragmentTransactions.commit();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void showCharacterDetailFragment(){

        if(!getResources().getBoolean(R.bool.isTablet)){

            CharactersDetailFragment charactersDetailFragment = CharactersDetailFragment.newInstance();

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container, charactersDetailFragment,CharactersDetailFragment.class.getName()).addToBackStack(CharactersDetailFragment.class.getName());
            fragmentTransaction.commit();
        }
    }

    public void updateToolBarTitle(String title){
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();
        if(count == 0)
            super.onBackPressed();
        else
            getFragmentManager().popBackStack();
    }
}