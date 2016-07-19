package com.example.wsn.myapplication;

import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v4.view.ViewPropertyAnimatorListener;
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
import java.util.stream.Stream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextureView.SurfaceTextureListener {

    private Button[] button = new Button[3];
    private TextureView textureView;
    Surface su;

    MediaPlayer mediaPlayer;

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.play){
            mediaPlayer.start();

        }
        else if(v.getId()==R.id.pause){
            mediaPlayer.pause();
        }
        else if(v.getId()==R.id.stop){
            mediaPlayer.stop();

        }
        else
            return;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textureView = (TextureView)findViewById(R.id.movie_display);
        button[0] = (Button)findViewById(R.id.play);
        button[1] = (Button)findViewById(R.id.pause);
        button[2] = (Button)findViewById(R.id.stop);


        for(Button b:button){
            b.setOnClickListener(this);

        }
        textureView.setSurfaceTextureListener(this);
    }
    public void copyFile() throws IOException{
        InputStream inputStream = null;
        OutputStream outputStream = null;
        inputStream = getAssets().open("ansen.mp4");
        String file = Environment.getExternalStorageDirectory() + "/ansen.mp4";
        outputStream = new FileOutputStream(file);
        byte[] buf=new byte[1024];
        int read;
        while((read = inputStream.read(buf))!=-1){
            outputStream.write(buf,0,read);
        }
        inputStream.close();
        inputStream=null;
        outputStream.flush();
        outputStream.close();
        outputStream=null;
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
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

    }

    private class play extends Thread{
        @Override
        public void run() {
            super.run();
            File file = new File(Environment.getExternalStorageDirectory()+"/ansen.mp4");
            if(!file.exists()){
                try {
                    copyFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(file.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.setSurface(su);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                }
            });
            try {
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
