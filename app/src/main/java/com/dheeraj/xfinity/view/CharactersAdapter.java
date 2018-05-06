package com.dheeraj.xfinity.view;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dheeraj.xfinity.R;
import com.dheeraj.xfinity.common.DataBoundListAdapter;
import com.dheeraj.xfinity.data.RelatedTopic;
import com.dheeraj.xfinity.databinding.ResultsRowBinding;

public class CharactersAdapter extends DataBoundListAdapter<RelatedTopic, ResultsRowBinding> {

    private CharacterClickListener characterClickListener;
    private DataBindingComponent dataBindingComponent;

    public CharactersAdapter(DataBindingComponent dataBindingComponent, CharacterClickListener characterClickListener) {
        this.dataBindingComponent = dataBindingComponent;
        this.characterClickListener = characterClickListener;
    }

    public interface CharacterClickListener {
        void onCharacterSelected(RelatedTopic relatedTopic);
    }

    @Override
    protected ResultsRowBinding createBinding(ViewGroup parent) {
        ResultsRowBinding resultsRowBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.results_row,parent,false,dataBindingComponent);
        return resultsRowBinding;
    }

    @Override
    protected void bind(ResultsRowBinding binding, RelatedTopic item) {
        binding.setRelatedTopic(item);
        if(binding.getRoot().getLayoutParams().getClass().getEnclosingClass().getName().equals(GridLayoutManager.class.getName()))
            binding.setShowText(false);
        else
            binding.setShowText(true);
        binding.getRoot().setOnClickListener(v -> characterClickListener.onCharacterSelected(item));
    }

    @Override
    protected boolean areItemsTheSame(RelatedTopic oldItem, RelatedTopic newItem) {
        return false;
    }

    @Override
    protected boolean areContentsTheSame(RelatedTopic oldItem, RelatedTopic newItem) {
        return false;
    }
}
