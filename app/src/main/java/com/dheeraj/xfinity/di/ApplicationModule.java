package com.dheeraj.xfinity.di;

import android.app.Application;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.persistence.room.Room;

import com.dheeraj.xfinity.AppExecutors;
import com.dheeraj.xfinity.XfinityApplication;
import com.dheeraj.xfinity.api.ApiService;
import com.dheeraj.xfinity.repository.RelatedTopicDao;
import com.dheeraj.xfinity.repository.XfinityDatabase;
import com.dheeraj.xfinity.repository.XfinityRepository;
import com.dheeraj.xfinity.utils.LiveDataCallAdapterFactory;
import com.dheeraj.xfinity.utils.RetrofitApiKeyInterceptor;
import com.dheeraj.xfinity.viewmodel.XfinityModelFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApplicationModule {

    private final XfinityApplication application;

    public ApplicationModule(XfinityApplication xfinityApplication) {
        application = xfinityApplication;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return application;
    }

    @Provides
    @Singleton
    ViewModelProvider.Factory provideViewModelFactory(XfinityRepository repository) {
        return new XfinityModelFactory(repository);
    }

    @Provides
    @Singleton
    AppExecutors providesAppExecutors() {
        return new AppExecutors();
    }


    @Provides
    @Named("dataBase")
    String providesDatabasePath() {
        return "Xfinity.db";
    }

    @Singleton
    @Provides
    XfinityDatabase provideXfinityDatabase(Application application, @Named("dataBase") String databaseName) {
        return Room.databaseBuilder(
                application,
                XfinityDatabase.class,
                databaseName)
                .fallbackToDestructiveMigration()
                .build();
    }


    @Singleton
    @Provides
    RelatedTopicDao provideFavoriteDao(XfinityDatabase xfinityDatabase) {
        return xfinityDatabase.favouriteDao();
    }

    @Provides
    @Singleton
    XfinityRepository provideXfinityRepository(AppExecutors appExecutors, ApiService apiService, RelatedTopicDao relatedTopicDao) {
        return new XfinityRepository(appExecutors, apiService, relatedTopicDao);
    }

    @Provides
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    OkHttpClient.Builder provideOkHttpClientBuilder(Interceptor apiKeyInterceptor) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//        httpClient.addInterceptor(apiKeyInterceptor);
        httpClient.addInterceptor(logging);

        return httpClient;
    }

    @Provides
    @Named("baseUrl")
    String provideBaseUrl() {
        return "http://api.duckduckgo.com/";
    }

    @Provides
    @Singleton
    Retrofit provideRetroFit(OkHttpClient.Builder okHttpClientBuilder, @Named("baseUrl") String baseUrl) {


        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .client(okHttpClientBuilder.build())
                .build();

    }

    @Provides
    @Singleton
    Interceptor provideApiKeyInterceptor() {
        return new RetrofitApiKeyInterceptor();
    }

    @Provides
    @Singleton
    ApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }
}
