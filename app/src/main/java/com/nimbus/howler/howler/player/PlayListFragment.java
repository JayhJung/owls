package com.nimbus.howler.howler.player;

import android.app.Fragment;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nimbus.howler.howler.MainActivity;
import com.nimbus.howler.howler.R;
import com.nimbus.howler.howler.config.Config;
import com.nimbus.howler.howler.service.SCService;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
 * Created by Steve on 2016-10-02.
 */

public class PlayListFragment extends Fragment {

    private static final String TAG = "PlayListFragment";
    private SCTrackAdapter mAdapter;
    private List<Track> mListItems;
    private LinearLayoutManager layoutManager;

    private MediaPlayer mMediaPlayer;
    private ImageView mPlayerControl;
    private TextView mSelectedTrackTitle;
    private ImageView mSelectedTrackImage;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play_list, container, true);
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mListItems = new ArrayList<>();
        RecyclerView trackRecyclerView = (RecyclerView) view.findViewById(R.id.track_list);
        mAdapter = new SCTrackAdapter(getActivity(), mListItems);
        mAdapter.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getTag() != null) {
                    Track track = mListItems.get(Integer.parseInt(v.getTag().toString()));
                    mSelectedTrackTitle.setText(track.getTitle());
                    Picasso.with(getActivity())
                            .load(track.getArtworkURL())
                            .placeholder(R.drawable.ic_empty_music)
                            .error(R.drawable.ic_empty_music)
                            .into(mSelectedTrackImage);
                    if (mMediaPlayer != null) {
                        if (mMediaPlayer.isPlaying()) {
                            mMediaPlayer.stop();
                            mMediaPlayer.reset();
                        }
                        try {
                            mMediaPlayer.setDataSource(track.getStreamURL() + "?client_id=" + Config.CLIENT_ID);
                            mMediaPlayer.prepareAsync();
                        } catch (IOException e) {
                            Log.e(TAG, track.getStreamURL());
                            e.printStackTrace();
                        } catch (IllegalStateException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        trackRecyclerView.setAdapter(mAdapter);

        layoutManager = new LinearLayoutManager(getActivity());
        trackRecyclerView.setLayoutManager(layoutManager);


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(Config.API_URL)
                .build();
        SCService scService = retrofit.create(SCService.class);
        Call<List<Track>> listCall = scService.getRecentTracks(new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(new Date()));

        listCall.enqueue(new Callback<List<Track>>() {
            @Override
            public void onResponse(Call<List<Track>> call, Response<List<Track>> response) {
                if (response.body() == null || response.body().size() == 0) {
                    Log.d(TAG, "no result");
                    return;
                }
                Log.d(TAG, "First track title: " + response.body().get(0).getTitle());
                loadTracks(response.body());
            }

            @Override
            public void onFailure(Call<List<Track>> call, Throwable t) {
                // Log.d(TAG, "Error: " + t.getMessage());
            }
        });

        /* Player controller */
        mPlayerControl = (ImageView) view.findViewById(R.id.player_control);

        mSelectedTrackTitle = (TextView) view.findViewById(R.id.selected_track_title);
        mSelectedTrackImage = (ImageView) view.findViewById(R.id.selected_track_image);

        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                togglePlayPause();
            }
        });


        mPlayerControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePlayPause();
            }
        });

    }

    private void loadTracks(List<Track> tracks) {
        mListItems.clear();
        mListItems.addAll(tracks);
        mAdapter.notifyDataSetChanged();
    }

    private void togglePlayPause() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            mPlayerControl.setImageResource(R.drawable.ic_play);
        } else {
            mMediaPlayer.start();
            mPlayerControl.setImageResource(R.drawable.ic_pause);
        }
    }


    @Override
    public void onDestroyView() {
        if (mMediaPlayer != null) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        super.onDestroyView();
    }
}
