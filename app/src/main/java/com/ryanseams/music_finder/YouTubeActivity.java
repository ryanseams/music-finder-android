package com.ryanseams.music_finder;

/**
 * Created by ryan on 6/17/15.
 */


import com.google.android.youtube.player.YouTubeBaseActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.ErrorReason;
import com.google.android.youtube.player.YouTubePlayer.PlaybackEventListener;
import com.google.android.youtube.player.YouTubePlayer.PlayerStateChangeListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

import org.json.JSONException;
import org.json.JSONObject;

public class YouTubeActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    public static final String GOOGLE_API_KEY = "AIzaSyD_7WL8yfcVnmVyDjf2HgCbpdlz4lEr0M0";
    public static final String YOUTUBE_VIDEO_ID = "ZkwLs6vxLik";
    private static final String MIXPANEL_TOKEN = "ryanios";
    private MixpanelAPI mixpanel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_tube);
        YouTubePlayerView youTubePlayView = (YouTubePlayerView) findViewById(R.id.youtube_player);
        youTubePlayView.initialize(GOOGLE_API_KEY, this);
        mixpanel = MixpanelAPI.getInstance(this, MIXPANEL_TOKEN);
    }

    public void onInitializationFailure(Provider provider, YouTubeInitializationResult result) {
        Toast.makeText(this, "Cannot initialize YouTube Player", Toast.LENGTH_LONG).show();
    }

    public void onInitializationSuccess(Provider provider, YouTubePlayer player, boolean wasRestored) {
        player.setPlayerStateChangeListener(playerStateChangeListener);
        player.setPlaybackEventListener(playbackEventListener1);

        if(!wasRestored) {
            player.cueVideo(YOUTUBE_VIDEO_ID);
        }
    }

    private PlaybackEventListener playbackEventListener1 = new PlaybackEventListener() {

        public void onBuffering(boolean arg0) {}

        public void onPaused() {
            JSONObject props = new JSONObject();

            try{
                props.put("Video", "Test");
            } catch (JSONException e) {
                Log.e("Send", "Unable to add properties to JSONObject", e);
            }
            mixpanel.track("Video Paused", props);
        }

        public void onPlaying() {}

        public void onSeekTo(int arg0) {}

        public void onStopped() {}

    };

    private PlayerStateChangeListener playerStateChangeListener = new PlayerStateChangeListener() {

        public void onAdStarted() {}

        public void onError(ErrorReason e) {}

        public void onLoaded(String arg0) {}

        public void onLoading() {}

        public void onVideoStarted() {
            JSONObject props = new JSONObject();

            try{
                props.put("Video", "Test");
            } catch (JSONException e) {
                Log.e("Send", "Unable to add properties to JSONObject", e);
            }
            mixpanel.track("Video Started", props);
        }

        public void onVideoEnded() {
            JSONObject props = new JSONObject();

            try{
                props.put("Video", "Test");
            } catch (JSONException e) {
                Log.e("Send", "Unable to add properties to JSONObject", e);
            }
            mixpanel.track("Video Ended", props);
        }

    };

}




/*
import android.os.Bundle;
import android.widget.Toast;

import java.security.Provider;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.ErrorReason;
import com.google.android.youtube.player.YouTubePlayer.PlaybackEventListener;
import com.google.android.youtube.player.YouTubePlayer.PlayerStateChangeListener;
import com.google.android.youtube.player.YouTubePlayerView;

public class YouTubeActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    public static final String GOOGLE_API_KEY = "AIzaSyCUms1HZrFbYgyNeL87exoVOfKGp45E2BQ";//"AIzaSyD_7WL8yfcVnmVyDjf2HgCbpdlz4lEr0M0";

    public static final String YOUTUBE_VIDEO_ID = "mIJGnsO-FfI";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.youtube);
        //YouTubePlayerView youTubePlayView = (YouTubePlayerView) findViewById(R.id.youtube_player);
        //youTubePlayView.initialize(GOOGLE_API_KEY, this);
    }

    public void onInitializationFailure(Provider provider, YouTubeInitializationResult result) {
        Toast.makeText(this, "Cannot initialize YouTube Player", Toast.LENGTH_LONG).show();
    }

    public void onInitializationSuccess(Provider provider, YouTubePlayer player, boolean wasRestored) {
        player.setPlayerStateChangeListener(playerStateChangeListener);
        player.setPlaybackEventListener(playbackEventListener1);

        if(!wasRestored) {
            player.cueVideo(YOUTUBE_VIDEO_ID);
        }
    }

    private PlaybackEventListener playbackEventListener1 = new PlaybackEventListener() {

        public void onBuffering(boolean arg0) {}

        public void onPaused() {}

        public void onPlaying() {}

        public void onSeekTo(int arg0) {}

        public void onStopped() {}

    };

    private PlayerStateChangeListener playerStateChangeListener = new PlayerStateChangeListener() {

        public void onAdStarted() {}

        public void onError(ErrorReason e) {}

        public void onLoaded(String arg0) {}

        public void onLoading() {}

        public void onVideoStarted() {}

        public void onVideoEnded() {}

    };

}
*/