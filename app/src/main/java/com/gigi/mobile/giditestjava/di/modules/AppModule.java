package com.gigi.mobile.giditestjava.di.modules;

import android.content.Context;

import com.gigi.mobile.giditestjava.BuildConfig;
import com.gigi.mobile.giditestjava.repo.offline.AppDatabase;
import com.gigi.mobile.giditestjava.repo.offline.CurrentDao;
import com.gigi.mobile.giditestjava.repo.offline.ForecastDao;
import com.gigi.mobile.giditestjava.repo.remote.ApiService;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

    private static OkHttpClient okHttpClient;

    @Singleton
    @Provides
    final OkHttpClient providesClient(HttpLoggingInterceptor loggingInterceptor){
        int REQUEST_TIMEOUT = 15;
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                /*.retryOnConnectionFailure(true)*/
                .build();

        return okHttpClient;
    }

    @Singleton
    @Provides
    final HttpLoggingInterceptor provideHttpLoggingInterceptor(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }


    @Singleton
    @Provides
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Singleton
    @Provides
    AppDatabase providesAppDatabase(Context ctx) {
        return Room.databaseBuilder(ctx, AppDatabase.class, "Gidi-Weather-Java")
                .fallbackToDestructiveMigration()
                .build();
    }

    @Singleton
    @Provides
    ForecastDao providesDataDao(AppDatabase appDatabase) {
        return appDatabase.getDataDao();
    }

    @Singleton
    @Provides
    CurrentDao providesCurrentDao(AppDatabase database) {
        return database.getCurrentDao();
    }

    @Singleton
    @Provides
    ApiService provideRetrofitService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

}
