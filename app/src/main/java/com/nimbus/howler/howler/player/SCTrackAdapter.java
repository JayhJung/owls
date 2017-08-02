package com.nimbus.howler.howler.player;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nimbus.howler.howler.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Steve on 2016-09-13.
 */
public class SCTrackAdapter extends RecyclerView.Adapter<SCTrackAdapter.TrackViewHolder> {

    private Context mContext;
    private List<Track> mTracks;
    private View.OnClickListener clickListener;
    public SCTrackAdapter(Context mContext, List<Track> mTracks) {
        this.mContext = mContext;
        this.mTracks = mTracks;
    }


    @Override
    public TrackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.track_list_row, parent, false);

        TrackViewHolder viewHolder = new TrackViewHolder(view);
        viewHolder.trackImageView = (ImageView) view.findViewById(R.id.track_image);
        viewHolder.titleTextView = (TextView) view.findViewById(R.id.track_title);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TrackViewHolder holder, int position) {
        holder.titleTextView.setText(mTracks.get(position).getTitle());
        Picasso.with(mContext).load(mTracks.get(position).getArtworkURL())
                .placeholder(R.drawable.ic_empty_music)
                .error(R.drawable.ic_empty_music)
                .into(holder.trackImageView);
        holder.itemView.setTag(position);
    }

    public void setOnItemClickListener(View.OnClickListener clickListener){
        this.clickListener = clickListener;
    };
    @Override
    public int getItemCount() {
        return mTracks.size();
    }

    class TrackViewHolder extends RecyclerView.ViewHolder{
        ImageView trackImageView;
        TextView titleTextView;
        public TrackViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(clickListener);
        }
    }
}
