package com.dheeraj.xfinity.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dheeraj.xfinity.XfinityApplication;
import com.dheeraj.xfinity.R;
import com.dheeraj.xfinity.binding.FragmentDataBindingComponent;
import com.dheeraj.xfinity.data.RelatedTopic;
import com.dheeraj.xfinity.databinding.CharactersDetailLayoutBinding;
import com.dheeraj.xfinity.utils.ApplicationUtils;
import com.dheeraj.xfinity.utils.AutoClearedValue;
import com.dheeraj.xfinity.viewmodel.ResultsViewModel;

import javax.inject.Inject;

public class CharactersDetailFragment extends Fragment{

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private AutoClearedValue<CharactersDetailLayoutBinding> binding;
    DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);

    public CharactersDetailFragment() {
        // Required empty public constructor
    }

    public static CharactersDetailFragment newInstance() {
        return new CharactersDetailFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((XfinityApplication) getActivity().getApplication())
                .getApplicationComponent()
                .inject(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        CharactersDetailLayoutBinding charactersDetailLayoutBinding = DataBindingUtil.inflate(inflater,R.layout.characters_detail_layout,container,false,dataBindingComponent);
        binding = new AutoClearedValue<>(this,charactersDetailLayoutBinding);

        return binding.get().getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(isAdded()){
            ViewModelProviders.of(getActivity(),viewModelFactory).get(ResultsViewModel.class).getSelectedRelatedTopic().observe(getActivity(), new Observer<RelatedTopic>() {
                @Override
                public void onChanged(@Nullable RelatedTopic relatedTopic) {
                    if(isAdded()){
                        if(binding != null && binding.get() != null)
                            binding.get().setRelatedTopic(relatedTopic);
                        if(!getResources().getBoolean(R.bool.isTablet)){
                            ((LandingActivity) getActivity()).updateToolBarTitle(ApplicationUtils.getTitle(relatedTopic.Text));
                        }
                    }
                }
            });
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
