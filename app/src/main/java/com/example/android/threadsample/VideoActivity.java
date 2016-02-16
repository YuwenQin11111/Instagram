package com.example.android.threadsample;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class VideoActivity extends Activity implements
    OnBufferingUpdateListener, OnCompletionListener, OnPreparedListener,
    OnVideoSizeChangedListener, SurfaceHolder.Callback {

  private static final String TAG = "MediaPlayerDemo";
  private int mVideoWidth;
  private int mVideoHeight;
  private MediaPlayer mMediaPlayer;
  private SurfaceView mPreview;
  private SurfaceHolder holder;
  private String path;
  private int mIsLocal;
  private String mVideoPath;
  private static final int LOCAL_VIDEO = 4;
  private static final int STREAM_VIDEO = 5;
  private boolean mIsVideoSizeKnown = false;
  private boolean mIsVideoReadyToBePlayed = false;

  /**
   * 
   * Called when the activity is first created.
   */
  @SuppressWarnings("deprecation")
  @Override
  public void onCreate(Bundle bundle) {
    super.onCreate(bundle);
    setContentView(R.layout.video_fragment);
    mPreview = (SurfaceView) findViewById(R.id.surface);
    holder = mPreview.getHolder();
    holder.addCallback(this);
    holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    mIsLocal = getIntent().getIntExtra(Constants.VIDEO_LOCAL, STREAM_VIDEO);
    mVideoPath = getIntent().getStringExtra(Constants.VIDEO_URL);
    
    prepareVideo();
  }
  
  private void prepareVideo() {
	  // Copy the video to local storage
	  int resourceID = 0;
	  String filePrefix = Environment.getExternalStorageDirectory().getAbsolutePath()+"/";
	  
	  if (mVideoPath.equals("video1")) {
		  resourceID = R.raw.video1;
		  path = filePrefix + "video1.mp4";
	  } else if (mVideoPath.equals("video2")) {
		  resourceID = R.raw.video2;
		  path = filePrefix + "video2.mp4";
	  }
	 
	  InputStream in;
		try {
			in = this.getApplicationContext().getResources().openRawResource(resourceID);
			
		
			FileOutputStream out = new FileOutputStream(path,false);
			byte[] buff = new byte[10240];
			int read = 0;

			try {
			   while ((read = in.read(buff)) > 0) {
			      out.write(buff, 0, read);
			   }
			}
			finally {
			     in.close();
	
			     out.close();
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	  
  }
  
  public static boolean copyFromResources(Context context, int FileResourceID,String CopyPath){
		InputStream in;
		try {
			in = context.getResources().openRawResource(FileResourceID);
			
		
		FileOutputStream out = new FileOutputStream(CopyPath,false);
		byte[] buff = new byte[10240];
		int read = 0;

			try {
			   while ((read = in.read(buff)) > 0) {
			      out.write(buff, 0, read);
			   }
			}
			finally {
			     in.close();
	
			     out.close();
			}
		}catch(IOException e){
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

  private void playVideo(Integer Media) {
    doCleanUp();
    try {

      switch (Media) {
      case LOCAL_VIDEO:
        if (path == "") {
          // Tell the user to provide a media file URL.
          Toast.makeText(
        		  VideoActivity.this,
              "Video does not exist",
              Toast.LENGTH_LONG).show();

        }
        break;
      case STREAM_VIDEO:
        /*
         * TODO: Set path variable to progressive streamable mp4 or 3gpp
         * format URL. Http protocol should be used. Mediaplayer can
         * only play "progressive streamable contents" which basically
         * means: 1. the movie atom has to precede all the media data
         * atoms. 2. The clip has to be reasonably interleaved.
         */
        path = "https://scontent.cdninstagram.com/t50.2886-16/12746917_1124506327590247_1741682255_s.mp4";
        if (path == "") {
          // Tell the user to provide a media file URL.
          Toast.makeText(
        		  VideoActivity.this,
              "Video does not exist",
              Toast.LENGTH_LONG).show();

        }
        break;

      }

      // Create a new media player and set the listeners
      mMediaPlayer = new MediaPlayer();
      mMediaPlayer.setDataSource(path);
      mMediaPlayer.setDisplay(holder);
      mMediaPlayer.prepare();
      mMediaPlayer.setOnBufferingUpdateListener(this);
      mMediaPlayer.setOnCompletionListener(this);
      mMediaPlayer.setOnPreparedListener(this);
      mMediaPlayer.setOnVideoSizeChangedListener(this);
      mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

    } catch (Exception e) {
      Log.e(TAG, "error: " + e.getMessage(), e);
    }
  }

  @Override
  public void onBufferingUpdate(MediaPlayer arg0, int percent) {
    Log.d(TAG, "onBufferingUpdate percent:" + percent);

  }

  @Override
  public void onCompletion(MediaPlayer arg0) {
    Log.d(TAG, "onCompletion called");
    releaseMediaPlayer();
    doCleanUp();
    this.finish();
  }

  @Override
  public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
    Log.v(TAG, "onVideoSizeChanged called");
    if (width == 0 || height == 0) {
      Log.e(TAG, "invalid video width(" + width + ") or height(" + height
          + ")");
      return;
    }
    mIsVideoSizeKnown = true;
    mVideoWidth = width;
    mVideoHeight = height;
    if (mIsVideoReadyToBePlayed && mIsVideoSizeKnown) {
      startVideoPlayback();
    }
  }

  @Override
  public void onPrepared(MediaPlayer mediaplayer) {
    Log.d(TAG, "onPrepared called");
    mIsVideoReadyToBePlayed = true;
    if (mIsVideoReadyToBePlayed && mIsVideoSizeKnown) {
      startVideoPlayback();
    }
  }

  @Override
  public void surfaceChanged(SurfaceHolder surfaceholder, int i, int j, int k) {
    Log.d(TAG, "surfaceChanged called");

  }

  @Override
  public void surfaceDestroyed(SurfaceHolder surfaceholder) {
    Log.d(TAG, "surfaceDestroyed called");
  }

  @Override
  public void surfaceCreated(SurfaceHolder holder) {
    Log.d(TAG, "surfaceCreated called");
    playVideo(mIsLocal);

  }

  @Override
public void onPause() {
    super.onPause();
    releaseMediaPlayer();
    doCleanUp();
  }

  @Override
public void onDestroy() {
    super.onDestroy();
    releaseMediaPlayer();
    doCleanUp();
  }

  private void releaseMediaPlayer() {
    if (mMediaPlayer != null) {
      mMediaPlayer.release();
      mMediaPlayer = null;
    }
  }

  private void doCleanUp() {
    mVideoWidth = 0;
    mVideoHeight = 0;
    mIsVideoReadyToBePlayed = false;
    mIsVideoSizeKnown = false;
  }

  private void startVideoPlayback() {
    Log.v(TAG, "startVideoPlayback");
    holder.setFixedSize(mVideoWidth, mVideoHeight);
    mMediaPlayer.start();
  }
}