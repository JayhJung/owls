package com.nimbus.howler.howler.player;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Steve on 2016-09-13.
 */
public class Track {

    @SerializedName("title")
    private String mTitle;

    @SerializedName("id")
    private int mID;

    @SerializedName("stream_url")
    private String mStreamURL;

    @SerializedName("artwork_url")
    private String mArtworkURL;

    public String getTitle() {
        return mTitle;
    }

    public int getID() {
        return mID;
    }

    public String getStreamURL() {
        return mStreamURL;
    }

    public String getArtworkURL() {
        return mArtworkURL;
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Track{");
        sb.append("mTitle='").append(mTitle).append('\'');
        sb.append(", mID=").append(mID);
        sb.append(", mStreamURL='").append(mStreamURL).append('\'');
        sb.append(", mArtworkURL='").append(mArtworkURL).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
