package com.dheeraj.xfinity.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.dheeraj.xfinity.data.Characters;
import com.dheeraj.xfinity.data.RelatedTopic;
import com.dheeraj.xfinity.repository.XfinityRepository;
import com.dheeraj.xfinity.vo.Resource;

import java.util.List;

public class ResultsViewModel extends ViewModel {

    private XfinityRepository repository;
    private LiveData<Resource<List<RelatedTopic>>> relatedTopics;
    private MutableLiveData<RelatedTopic> selectedRelatedTopic = new MutableLiveData<>();
    private MutableLiveData<Boolean> showGrid = new MutableLiveData<>();

    public ResultsViewModel(XfinityRepository repository) {
        this.repository = repository;

        //Initially set showGrid to false
        showGrid.setValue(false);

        // Get favorite events
        relatedTopics = repository.getCharacters();

    }

    public MutableLiveData<RelatedTopic> getSelectedRelatedTopic() {
        return selectedRelatedTopic;
    }

    public LiveData<Resource<List<RelatedTopic>>> getRelatedTopics() {
        return relatedTopics;
    }

    public MutableLiveData<Boolean> getShowGrid() {
        return showGrid;
    }
}
