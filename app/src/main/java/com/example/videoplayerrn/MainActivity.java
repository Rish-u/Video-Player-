package com.example.videoplayerrn;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoView = findViewById(R.id.videoView);
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.rishabh1);
        videoView.setVideoURI(videoUri);

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        videoView.setOnPreparedListener(mediaPlayer -> {

            mediaPlayer.setOnVideoSizeChangedListener((mp, width, height) -> {

                mediaController.setAnchorView(videoView);
            });
            videoView.start();
        });

        videoView.setOnCompletionListener(mediaPlayer ->
                Toast.makeText(MainActivity.this, "Playback Completed", Toast.LENGTH_SHORT).show()
        );

        videoView.setOnErrorListener((mediaPlayer, what, extra) -> {
            Toast.makeText(MainActivity.this, "Playback Error", Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (videoView.isPlaying()) {
            videoView.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!videoView.isPlaying()) {
            videoView.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (videoView != null) {
            videoView.stopPlayback();
        }
    }
}
