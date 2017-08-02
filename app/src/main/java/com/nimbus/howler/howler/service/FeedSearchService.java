package com.nimbus.howler.howler.service;

import com.nimbus.howler.howler.config.Config;
import com.nimbus.howler.howler.player.Track;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Steve on 2016-09-20.
 */

public interface FeedSearchService {

    //https://itunes.apple.com/search?term=이진우&entity=podcast
    @GET("/search?entity=podcast")
    Call<List<Track>> getRecentTracks(@Query("term") String query);
}
