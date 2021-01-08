package com.tdc.edu.vn.heathcareapp.Adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.tdc.edu.vn.heathcareapp.R;
import com.tdc.edu.vn.heathcareapp.Model.Video;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
    Context context;
    ArrayList<Video> videos = new ArrayList<>();


    public VideoAdapter(Context context, ArrayList<Video> videos) {
        this.context = context;
        this.videos = videos;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_video, parent, false);
        return new VideoViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        Video video  = videos.get(position);
        if (video==null){
            return;
        }
        MediaController mediaController = new MediaController(context);

        try {
           /* holder.vvideo.setMediaController(mediaController);
            mediaController.setAnchorView(holder.vvideo);

            String url = video.getUrlvideo().toString();
            Uri uri = Uri.parse(url);
            holder.vvideo.setVideoURI(uri);
            holder.vvideo.start();*/
            setvideoURL(video,holder);
        }catch (Exception exception){

        }


    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void setvideoURL(Video video, VideoViewHolder holder) {
        //show
        holder.pbvideol.setVisibility(View.VISIBLE);
        //get video ỦL
        String url = video.getUrlvideo().toString();
        //Media controller for play,pause,seebar,time
        MediaController mediaController = new MediaController(context);
        mediaController.setAnchorView(holder.vvideo);

        Uri uri = Uri.parse(url);
        holder.vvideo.setMediaController(mediaController);
        holder.vvideo.setVideoURI(uri);

        holder.vvideo.requestFocus();
        holder.vvideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
               mp.start();

            }
        });
        holder.vvideo.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                    switch (what){
                        case MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:{
                            // rendering started
                            holder.pbvideol.setVisibility(View.VISIBLE);
                            return true;
                        }
                        case MediaPlayer.MEDIA_INFO_BUFFERING_START:{
                            holder.pbvideol.setVisibility(View.VISIBLE);
                            return true;
                        }
                        case MediaPlayer.MEDIA_INFO_BUFFERING_END:{
                            holder.pbvideol.setVisibility(View.GONE);
                            return true;
                        }
                    }

                return false;
            }
        });

        holder.vvideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.start();
            }
        });

    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        VideoView vvideo;
        ProgressBar pbvideol;
        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            vvideo= itemView.findViewById(R.id.vvideo);
            pbvideol= itemView.findViewById(R.id.pbvideo);

        }
    }
}

