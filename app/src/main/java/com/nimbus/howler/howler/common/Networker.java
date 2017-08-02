package com.nimbus.howler.howler.common;

import android.util.Log;

import com.nimbus.howler.howler.config.Config;
import com.nimbus.howler.howler.player.Track;
import com.nimbus.howler.howler.service.SCService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Steve on 2016-09-19.
 */
public class Networker <T>{
    public Networker(Callback<T> callback){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(Config.API_URL)
                .build();
        SCService scService = retrofit.create(SCService.class);
        //Call<T> listCall = scService.getRecentTracks(new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(new Date()));

       // listCall.enqueue(callback);
    }

}
