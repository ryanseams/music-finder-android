package com.ryanseams.music_finder;

import com.google.android.youtube.player.YouTubeBaseActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
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

    // Set Google API credentials and video ID for playing YouTube videos
    public static final String GOOGLE_API_KEY = "AIzaSyD_7WL8yfcVnmVyDjf2HgCbpdlz4lEr0M0";
    public static final String YOUTUBE_VIDEO_ID = "32TNuFrhw5M";

    // Create a MixpanelAPI object for usage in tracking video activity
    private MixpanelAPI mixpanel;

    // Set your Mixpanel project token, this can be found under Mixpanel project settings
    private static final String MIXPANEL_TOKEN = "ryanios";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Find the area for the YouTube object and initialize a YouTube player inside
        setContentView(R.layout.activity_you_tube);
        YouTubePlayerView youTubePlayView = (YouTubePlayerView) findViewById(R.id.youtube_player);
        youTubePlayView.initialize(GOOGLE_API_KEY, this);

        // Initialize an instance of Mixpanel using context and your project token
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

        // Here you can track different events based on the state of the player
        public void onBuffering(boolean arg0) {}

        public void onPaused() {
            // Track that the user paused watching the video
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

        // Here you can track different events based on the state of the player
        public void onAdStarted() {}

        public void onError(ErrorReason e) {}

        public void onLoaded(String arg0) {}

        public void onLoading() {}

        public void onVideoStarted() {
            // Track that the user started watching the video
            JSONObject props = new JSONObject();
            try{
                props.put("Video", "Test");
            } catch (JSONException e) {
                Log.e("Send", "Unable to add properties to JSONObject", e);
            }
            mixpanel.track("Video Started", props);
        }

        public void onVideoEnded() {
            // Track that the user watched the entire video
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
