package com.aerber.thetest;

import android.content.res.AssetManager;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ExpandedMenuView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

    private TextureView textureView;
    private FloatingActionButton[] buttons;
    private MediaPlayer mediaPlayer;
    private Surface surfaceview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textureView = (TextureView) findViewById(R.id.video);
        textureView.setSurfaceTextureListener(this);

        buttons = new FloatingActionButton[3];
        buttons[0] = (FloatingActionButton) findViewById(R.id.play);
        buttons[1] = (FloatingActionButton) findViewById(R.id.pause);
        buttons[2] = (FloatingActionButton) findViewById(R.id.stop);

        for (FloatingActionButton button : buttons) {
            button.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.pause) {
            mediaPlayer.pause();
        } else if (v.getId() == R.id.stop) {
            mediaPlayer.stop();
        } else if (v.getId() == R.id.play) {
            mediaPlayer.start();
        }
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        surfaceview = new Surface(surface);
        new play().start();
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        mediaPlayer.stop();
        mediaPlayer.release();
        surfaceview = null;
        textureView = null;

        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    private class play extends Thread {
        @Override
        public void run() {
            super.run();
            try {
                File file = new File(Environment.getExternalStorageDirectory() + "/ansen.mp4");
                if (!file.exists()) {
                    copyFile();
                }

                mediaPlayer = new MediaPlayer();

                mediaPlayer.setDataSource(file.getAbsolutePath());
                mediaPlayer.setSurface(surfaceview);
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

    private void copyFile() {
        AssetManager assetManager = this.getAssets();
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            inputStream = assetManager.open("ansen.mp4");
            String newFileName = Environment.getExternalStorageDirectory() + "/ansen.mp4";
            outputStream = new FileOutputStream(newFileName);
            byte[] buffer = new byte[1024];

            int read;
            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }

            inputStream.close();
            inputStream = null;
            outputStream.flush();
            outputStream.close();
            outputStream = null;

        } catch (Exception e) {
            Log.e("Tag", e.getMessage());
        }
    }

}


//
//private MediaPlayer mediaPlayer;
//    private Surface surface;
//    private FloatingActionButton[] buttons = new FloatingActionButton[3];
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        TextureView textureView = (TextureView)findViewById(R.id.video);
//
//        textureView.setSurfaceTextureListener(this);
//
//        buttons[0] = (FloatingActionButton) findViewById(R.id.play);
//        buttons[1] = (FloatingActionButton) findViewById(R.id.pause);
//        buttons[2] = (FloatingActionButton) findViewById(R.id.stop);
//
//        for (FloatingActionButton button:buttons){
//            button.setOnClickListener(this);
//        }
//    }
//
//    @Override
//    public void onClick(View v) {
//        if (surface == null)
//            return;
//
//        if (v.getId() == R.id.pause){
//            mediaPlayer.pause();
//            return;
//        }
//        if (v.getId() == R.id.stop){
//            mediaPlayer.stop();
//        }
//        if (v.getId() == R.id.play){
//            mediaPlayer.start();
//        }
//    }
//
//
//
//
//    @Override
//    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
//        System.out.println("onSurfaceTextureAvailable onSurfaceTextureAvailable");
//        surface = new Surface(surfaceTexture);
//        new PlayVideo().start();
//    }
//
//    @Override
//    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
//        System.out.println("onSurfaceTextureSizeChanged onSurfaceTextureSizeChanged");
//    }
//
//    @Override
//    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
//        System.out.println("onSurfaceTextureDestroyed onSurfaceTextureDestroyed");
//        surfaceTexture = null;
//        surface = null;
//        mediaPlayer.stop();
//        mediaPlayer.release();
//        return true;
//    }
//
//    @Override
//    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
//
//    }
//
//    private class PlayVideo extends Thread{
//        @Override
//        public void run() {
//            try{
//                File file = new File(Environment.getExternalStorageDirectory()+"/ansen.mp4");
//                if (!file.exists()){
//                    copyFile();
//                }
//                mediaPlayer = new MediaPlayer();
//                mediaPlayer.setDataSource(file.getAbsolutePath());
//                mediaPlayer.setSurface(surface);
//                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                    @Override
//                    public void onPrepared(MediaPlayer mp) {
//                        mediaPlayer.start();
//                    }
//                });
//
//                mediaPlayer.prepare();
//
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//    }
//
//    /**
//     * 如果SDCard上没有视频，则将视频copy过去
//     * */
//    private void copyFile(){
//        AssetManager assetManager = this.getAssets();
//        InputStream inputStream = null;
//        OutputStream outputStream = null;
//
//        try{
//            inputStream = assetManager.open("ansen.mp4");
//            String newFileName = Environment.getExternalStorageDirectory() + "/ansen.mp4";
//            outputStream = new FileOutputStream(newFileName);
//            byte[] buffer = new byte[1024];
//
//            int read;
//            while((read = inputStream.read(buffer)) != -1){
//                outputStream.write(buffer, 0, read);
//            }
//
//            inputStream.close();
//            inputStream = null;
//            outputStream.flush();
//            outputStream.close();
//            outputStream = null;
//
//        }catch (Exception e){
//            Log.e("Tag",e.getMessage());
//        }
//    }
//}
