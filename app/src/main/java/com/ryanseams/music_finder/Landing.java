package com.ryanseams.music_finder;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.ryanseams.music_finder.R;

import org.json.JSONException;
import org.json.JSONObject;

public class Landing extends MainActivity {

    public static final String GOOGLE_API_KEY = "AIzaSyD_7WL8yfcVnmVyDjf2HgCbpdlz4lEr0M0";
    public static final String YOUTUBE_VIDEO_ID = "mIJGnsO-FfI";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        JSONObject props = new JSONObject();

        try {
            props.put("Screen", "Landing");
        } catch (JSONException e) {
            Log.e("Send", "Unable to add properties to JSONObject", e);
        }

        mixpanel.track("Viewed Screen", props);

        Button test = (Button) findViewById(R.id.button);
        test.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //let's call an intent on the click
                Intent intent = new Intent(Landing.this, YouTubeActivity.class);
                startActivity(intent);
            }
        });

        ImageView image = (ImageView) findViewById(R.id.imageView);
        image.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //let's call an intent on the click
                Intent intent = new Intent(Landing.this, YouTubeActivity.class);
                startActivity(intent);
            }
        });
    }

    /** Called when the user clicks the Back button */
    public void goToHome(View view) {

        JSONObject props = new JSONObject();
        JSONObject superprops = new JSONObject();

        try {
            superprops.put("Logged In", false);
            props.put("Screen", "Landing");
        } catch (JSONException e) {
            Log.e("Send", "Unable to add properties to JSONObject", e);
        }

        mixpanel.registerSuperProperties(superprops);
        mixpanel.track("Signed Out", props);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_landing, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
