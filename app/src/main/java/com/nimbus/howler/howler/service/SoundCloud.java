package com.nimbus.howler.howler.service;

import com.nimbus.howler.howler.config.Config;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Steve on 2016-09-13.
 */
public class SoundCloud {
    private static final Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(Config.API_URL).build();
    private static final SCService SERVICE = retrofit.create(SCService.class);

    public static SCService getService() {
        return SERVICE;
    }

}
