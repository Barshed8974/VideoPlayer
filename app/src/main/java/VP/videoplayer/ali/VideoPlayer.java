package VP.videoplayer.ali;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

public class VideoPlayer extends AppCompatActivity {
    private ImageButton back,play,forw;
    private TextView videoNameTv,videoTimeTv;
    private SeekBar videoSeekbar;
    private VideoView videoView;
    private RelativeLayout controlRv, videoRv;
    boolean bool =true;
    private String tittle,path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player2);
        initViews();
        tittle=getIntent().getStringExtra("tittle");
        path=getIntent().getStringExtra("path");
        Log.d("TAG",tittle+" "+path+" ");
        videoNameTv.setText(tittle);
        videoView.setVideoURI(Uri.parse(path));
        Log.d("TAG",Uri.parse(path)+"");
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                videoView.requestFocus();
                videoView.start();
                Log.d("TAG","onPrepared");
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoView.seekTo(videoView.getDuration()-10000);
                Log.d("TAG","BACK");
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("TATA","PLAY");
                if(videoView.isPlaying())
                {
                    Log.d("TAG","isPlaying");
                    videoView.pause();
                    play.setImageDrawable(getResources().getDrawable(R.drawable.play_btn));
                }
                else
                {
                    Log.d("TAG","is not Playing");
                    videoView.start();
                    play.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_pause_circle_outline_24));
                }
            }
        });

        forw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG","Forward");
                videoView.seekTo(videoView.getDuration()+10000);
            }
        });
        videoRv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG","videoRV");
                if(bool)
                {
                    hideControl();
                    bool=false;
                }
                else
                {
                    showControl();
                    bool=false;
                }
            }
        });
        setHandler();
        initialize();
    }

    private void initialize() {
        Log.d("TAG","Initialize");
        videoSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(videoSeekbar.getId()==R.id.progress)
                {
                    if (b)
                    {
                        videoView.seekTo(i);
                        videoView.start();
                        int poss=videoView.getCurrentPosition();
                        videoTimeTv.setText(""+timeToString(videoView.getDuration()-poss));
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void setHandler() {
        Handler handler=new Handler();
        Log.d("TAG","Handler");
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                if(videoView.getDuration()>0)
                {
                    int poss=videoView.getCurrentPosition();
                    videoSeekbar.setProgress(poss);
                    videoTimeTv.setText(""+timeToString(videoView.getDuration()-poss));
                }
                handler.postDelayed(this,0);
            }
        };
        handler.postDelayed(runnable,500);
    }

    private String timeToString(int ms)
    {
        String time;
        int x,sec,min,hrs;
        x=ms/1000;
        sec=x%60;
        x=x/60;
        min=x%60;
        x/=60;
        hrs=x%24;
        if(hrs!=0)
        {
            time=String.format("%02d",hrs)+":"+String.format("%02d",min)+":"+String.format("%02d",sec);
        }
        else
        {
            time=String.format("%02d",min)+":"+String.format("%02d",sec);
        }
        return time;
    }

    private void showControl() {
        controlRv.setVisibility(View.VISIBLE);
    }

    private void hideControl() {
        controlRv.setVisibility(View.GONE);
    }

    private void initViews() {
        videoNameTv=findViewById(R.id.title);
        videoTimeTv=findViewById(R.id.time);
        back=findViewById(R.id.back);
        play=findViewById(R.id.play);
        forw=findViewById(R.id.forward);
        videoSeekbar=findViewById(R.id.progress);
        videoView=findViewById(R.id.videoView);
        controlRv=findViewById(R.id.idIrControl);
        videoRv=findViewById(R.id.idRlVideo);
    }
}