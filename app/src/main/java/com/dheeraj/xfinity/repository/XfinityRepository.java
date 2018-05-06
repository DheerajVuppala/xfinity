package com.dheeraj.xfinity.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.dheeraj.xfinity.AppExecutors;
import com.dheeraj.xfinity.BuildConfig;
import com.dheeraj.xfinity.api.ApiResponse;
import com.dheeraj.xfinity.api.ApiService;
import com.dheeraj.xfinity.data.Characters;
import com.dheeraj.xfinity.data.RelatedTopic;
import com.dheeraj.xfinity.vo.Resource;

import java.util.List;

import javax.inject.Inject;

public class XfinityRepository {

    private final AppExecutors appExecutors;
    private final ApiService apiService;
    private RelatedTopicDao relatedTopicDao;

    @Inject
    public XfinityRepository(AppExecutors appExecutors, ApiService apiService, RelatedTopicDao relatedTopicDao) {
        this.appExecutors = appExecutors;
        this.apiService = apiService;
        this.relatedTopicDao = relatedTopicDao;
    }

    public LiveData<Resource<List<RelatedTopic>>> getCharacters() {

        return new NetworkBoundResource<List<RelatedTopic>,Characters>(appExecutors){

            @Override
            protected void saveCallResult(@NonNull Characters item) {
                relatedTopicDao.insertRelatedTopics(item.RelatedTopics);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<RelatedTopic> data) {
                // TODO Always fetch for now. Will look into this later
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<RelatedTopic>> loadFromDb() {
                return relatedTopicDao.getRelatedTopics();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Characters>> createCall() {
                if(BuildConfig.FLAVOR.equals("simpsons")){
                    return apiService.getSimpsonsCharacters();
                }else if(BuildConfig.FLAVOR.equals("wire")){
                    return apiService.getWireCharacters();
                }
                return null;

            }
        }.asLiveData();
    }
}
