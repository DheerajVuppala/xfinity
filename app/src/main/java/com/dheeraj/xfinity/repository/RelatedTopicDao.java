package com.dheeraj.xfinity.repository;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.dheeraj.xfinity.data.RelatedTopic;

import java.util.List;

@Dao
public interface RelatedTopicDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRelatedTopics(List<RelatedTopic> relatedTopics);

    @Query("SELECT * FROM RelatedTopic")
    LiveData<List<RelatedTopic>> getRelatedTopics();
}
