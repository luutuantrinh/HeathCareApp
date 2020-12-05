package com.tdc.edu.vn.heathcareapp;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.tdc.edu.vn.heathcareapp.data_model.Video;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class VideoMain  extends AppCompatActivity {
    VideoView vvVideo;
    TextView txttimesong,txttotaltime;
    SeekBar sbsong;
    ImageButton btnplay, btnimgpre,btnimgnext;
    ArrayList<Video> listvideo;
    int position;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        vvVideo = findViewById(R.id.vv_video);
        txttimesong = findViewById(R.id.txttimesong);
        txttotaltime = findViewById(R.id.txttotaltime);
        sbsong = findViewById(R.id.sb_song);
        btnplay = findViewById(R.id.imgbtnplay);
        btnimgpre = findViewById(R.id.imgbtnpre);
        btnimgnext = findViewById(R.id.imgbtnnext);
        addVideo();
        CreateVideo();

        btnplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(vvVideo.isPlaying()){
                    vvVideo.pause();
                    btnplay.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                }else {
                    vvVideo.start();
                    btnplay.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24);
                }
                setimetotal();
                updatetimesong();

            }
        });

        btnimgnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position++;
                if(position > listvideo.size() -1)
                {
                    position = 0;
                }
                CreateVideo();
                vvVideo.start();
                setimetotal();
                updatetimesong();
            }
        });


        btnimgpre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position--;
                if(position < listvideo.size() -1)
                {
                    position = listvideo.size()- 1;
                }
                CreateVideo();
                vvVideo.start();
                setimetotal();
                updatetimesong();
            }
        });


       sbsong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                vvVideo.seekTo(sbsong.getProgress());
            }
        });

    }

    private void CreateVideo(){
        //mediaPlayer = MediaPlayer.create(MainActivity.this, listvideo.get(position).getFile());
        vvVideo.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + listvideo.get(position).getFile()));
    }

    private void addVideo(){
        listvideo = new ArrayList<>();
        listvideo.add((new Video(R.raw.hello)));
        listvideo.add((new Video(R.raw.nightcorewhereestarted)));
    }

    private  void setimetotal(){
        SimpleDateFormat dingdang = new SimpleDateFormat("mm:ss");
        sbsong.setMax(vvVideo.getDuration());
        txttotaltime.setText(dingdang.format(vvVideo.getDuration())+"");
    }

    private  void updatetimesong(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat dingdang = new SimpleDateFormat("mm:ss");
                txttotaltime.setText(dingdang.format(vvVideo.getDuration())+"");
                txttimesong.setText(dingdang.format(vvVideo.getCurrentPosition()));
                sbsong.setProgress(vvVideo.getCurrentPosition());

                // kiểm tra hết video
                vvVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        position++;
                        if(position > listvideo.size() -1)
                        {
                            position = 0;
                        }
                        CreateVideo();
                        vvVideo.start();
                        setimetotal();
                        updatetimesong();
                    }
                });


                handler.postDelayed(this,500);
            }
        },100);
    }

}
