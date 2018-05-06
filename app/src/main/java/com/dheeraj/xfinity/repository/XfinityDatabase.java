package com.dheeraj.xfinity.repository;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.dheeraj.xfinity.data.RelatedTopic;

@Database(entities = {RelatedTopic.class},version = 1,exportSchema = false)
public abstract class XfinityDatabase extends RoomDatabase{

    public abstract RelatedTopicDao favouriteDao();
}
