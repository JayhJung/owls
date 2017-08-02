package com.nimbus.howler.howler.player;

import android.app.Fragment;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nimbus.howler.howler.R;
import com.nimbus.howler.howler.config.Config;
import com.nimbus.howler.howler.service.SCService;
import com.nimbus.howler.howler.service.SampleVO;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;

import java.text.DateFormat;
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
 * Created by Steve on 2016-10-02.
 */

public class TrackListFragment extends Fragment {

    private static final String TAG = "TrackListFragment";
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
        View view = inflater.inflate(R.layout.fragment_play_list, container, false);
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(Config.LOCALHOST)
                .build();
        SCService scService = retrofit.create(SCService.class);
        Call<SampleVO> call = scService.getSample();
        call.enqueue(new Callback<SampleVO>() {
            @Override
            public void onResponse(Call<SampleVO> call, Response<SampleVO> response) {
                if (response.body() == null) {
                    Log.d(TAG, "no result");
                    return;
                }
                Log.d(TAG, response.body().getSampleData().toString());
                Toast.makeText(getActivity(),response.body().getSampleData().toString(), Toast.LENGTH_LONG).show();

                Log.d(TAG, response.body().getSampleData().toString() + " :: " + response.body().getSampleData().getTime());

                DateTime dt = new DateTime(response.body().getSampleData(), DateTimeZone.forOffsetHours(0));
                DateFormat date = new SimpleDateFormat("Z");
                String localTime = date.format(new Date());
                Log.d(TAG, dt.toString() + " :: " + localTime);

                LocalDateTime lt = new LocalDateTime(response.body().getSampleData());

                Log.d(TAG,  lt.toString()+ " :: " );




            }

            @Override
            public void onFailure(Call<SampleVO> call, Throwable t) {
                Log.d(TAG, t.getMessage().toString());
            }
        });
       /* listCall.enqueue(new Callback<List<Track>>() {
            @Override
            public void onResponse(Call<List<Track>> call, Response<List<Track>> response) {
                if (response.body() == null || response.body().size() == 0) {
                    Log.d(TAG, "no result");
                    return;
                }
                SyndFeedInput input = new SyndFeedInput();
                SyndFeed feed = input.build(new XmlReader(httpcon));
                List entries = feed.getEntries();
                Iterator itEntries = entries.iterator();

                while (itEntries.hasNext()) {
                    SyndEntry entry = itEntries.next();
                    System.out.println("Title: " + entry.getTitle());
                    System.out.println("Link: " + entry.getLink());
                    System.out.println("Author: " + entry.getAuthor());
                    System.out.println("Publish Date: " + entry.getPublishedDate());
                    System.out.println("Description: " + entry.getDescription().getValue());
                    System.out.println();
                }


            }

            @Override
            public void onFailure(Call<List<Track>> call, Throwable t) {
                // Log.d(TAG, "Error: " + t.getMessage());
            }
        });*/

//        SyndFeedInput input = new SyndFeedInput();
//        SyndFeed feed = input.build(new XmlReader(feedUrl));
    }

}
