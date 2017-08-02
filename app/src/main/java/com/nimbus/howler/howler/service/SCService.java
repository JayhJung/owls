package com.nimbus.howler.howler.service;

import com.nimbus.howler.howler.config.Config;
import com.nimbus.howler.howler.player.Track;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Steve on 2016-09-13.
 */
public interface SCService {
    @GET("/tracks?client_id=" + Config.CLIENT_ID)
    Call<List<Track>> getRecentTracks(@Query("created_at[from]") String date);

    @POST("/PodCast/pod.aspx?code=1000671100000100000")
    Call<List<Track>> getPodCastTrack();

    @POST("/sample")
    Call<SampleVO> getSample();
}
