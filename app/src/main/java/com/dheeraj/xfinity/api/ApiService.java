package com.dheeraj.xfinity.api;

import android.arch.lifecycle.LiveData;

import com.dheeraj.xfinity.data.Characters;

import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("?q=simpsons+characters&format=json")
    LiveData<ApiResponse<Characters>> getSimpsonsCharacters();

    @GET("?q=the+wire+characters&format=json")
    LiveData<ApiResponse<Characters>> getWireCharacters();

}
