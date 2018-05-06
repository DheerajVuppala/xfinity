package com.dheeraj.xfinity.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dheeraj.xfinity.R;
import com.dheeraj.xfinity.XfinityApplication;
import com.dheeraj.xfinity.binding.FragmentDataBindingComponent;
import com.dheeraj.xfinity.data.Characters;
import com.dheeraj.xfinity.data.RelatedTopic;
import com.dheeraj.xfinity.databinding.CharactersLayoutBinding;
import com.dheeraj.xfinity.utils.ApplicationUtils;
import com.dheeraj.xfinity.utils.AutoClearedValue;
import com.dheeraj.xfinity.viewmodel.ResultsViewModel;
import com.dheeraj.xfinity.vo.Resource;

import java.util.List;

import javax.inject.Inject;

public class CharactersFragment extends Fragment implements CharactersAdapter.CharacterClickListener{

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    AutoClearedValue<CharactersLayoutBinding> binding;
    AutoClearedValue<CharactersAdapter> adapter;

    android.databinding.DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);
    public CharactersFragment() {
        // Required empty public constructor
    }

    public static CharactersFragment newInstance() {
        return new CharactersFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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
        // Inflate the layout for this fragment
        CharactersLayoutBinding charactersLayoutBinding = DataBindingUtil.inflate(inflater, R.layout.characters_layout, container, false,dataBindingComponent);
        this.binding = new AutoClearedValue<>(this,charactersLayoutBinding);

        CharactersAdapter charactersAdapter = new CharactersAdapter(dataBindingComponent,this);
        this.adapter = new AutoClearedValue<>(this,charactersAdapter);

        this.binding.get().charactersRecyclerView.setAdapter(charactersAdapter);
        return binding.get().getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (isAdded()) {

            ViewModelProviders.of(getActivity(), viewModelFactory).get(ResultsViewModel.class).getRelatedTopics().observe(getActivity(), new Observer<Resource<List<RelatedTopic>>>() {
                @Override
                public void onChanged(@Nullable Resource<List<RelatedTopic>> listResource) {
                    if(listResource != null && listResource.data != null && !listResource.data.isEmpty())
                        adapter.get().replace(listResource.data);
                }
            });

            ViewModelProviders.of(getActivity(),viewModelFactory).get(ResultsViewModel.class).getShowGrid().observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(@Nullable Boolean aBoolean) {
                    binding.get().setShowGrid(aBoolean);
                }
            });

            if(!getResources().getBoolean(R.bool.isTablet)){
                ((LandingActivity) getActivity()).updateToolBarTitle(getString(R.string.app_name));
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCharacterSelected(RelatedTopic relatedTopic) {
        ViewModelProviders.of(getActivity(),viewModelFactory).get(ResultsViewModel.class).getSelectedRelatedTopic().setValue(relatedTopic);
        ((LandingActivity)getActivity()).showCharacterDetailFragment();
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnCharactersFragmentInteractionListener {
        void onCharacterSelected(RelatedTopic relatedTopic);
    }
}
