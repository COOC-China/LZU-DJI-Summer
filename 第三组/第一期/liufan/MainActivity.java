package com.example.administrator.videoplay;

import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextureView.SurfaceTextureListener {
    private Button[] button = new Button[3];
    MediaPlayer mediaPlayer;
    private TextureView textureView;
    private Surface su;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textureView = (TextureView) findViewById(R.id.movie_display);
        button[0] = (Button) findViewById(R.id.play);
        button[1] = (Button) findViewById(R.id.stop);
        button[2] = (Button) findViewById(R.id.sdsd);
        for (Button b : button) {
            b.setOnClickListener(this);
        }
        textureView.setSurfaceTextureListener(this);
    }

    public void copyFile() throws IOException {
        InputStream inputStream = null;
        OutputStream outputStream = null;

        inputStream = getAssets().open("ansen.mp4");
        String file = Environment.getExternalStorageDirectory() + "/ansen.mp4";

        outputStream = new FileOutputStream(file);
        byte[] buffer = new byte[1024];

        int read;
        while ((read = inputStream.read(buffer)) != 1) {
            outputStream.write(buffer, 0, read);
        }

        inputStream.close();
        inputStream = null;
        outputStream.flush();
        outputStream.close();
        outputStream = null;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.play) {
            mediaPlayer.start();
        } else if (v.getId() == R.id.stop) {
            mediaPlayer.pause();
        } else if (v.getId() == R.id.sdsd) {
            mediaPlayer.stop();
        } else {
            return;
        }
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
        su = new Surface(surfaceTexture);
        new play().start();
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    private class play extends Thread {
        @Override
        public void run() {
            super.run();
            File file = new File(Environment.getExternalStorageDirectory() + "/ansen.mp4");
            try {
                if (!file.exists()) {
                    copyFile();
                }

                mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(file.getAbsolutePath());
                mediaPlayer.setSurface(su);
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                    }
                });
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}