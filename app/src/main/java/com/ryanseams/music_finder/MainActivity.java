package com.ryanseams.music_finder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.mixpanel.android.mpmetrics.Tweak;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends Activity {

    private static Tweak<Double> gameSpeed = MixpanelAPI.doubleTweak("Game speed", 1.0);
    private static Tweak<Boolean> showAds = MixpanelAPI.booleanTweak("Show ads", false);
    private static final String MIXPANEL_TOKEN = "ryanios";
    public MixpanelAPI mixpanel;
    //public final static String EXTRA_MESSAGE = "com.ryanseams.music_finder.MESSAGE";
    public static final String ANDROID_PUSH_SENDER_ID = "530582007670";
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mixpanel = MixpanelAPI.getInstance(this, MIXPANEL_TOKEN);

        JSONObject superprops = new JSONObject();
        JSONObject props = new JSONObject();

        try {
            superprops.put("Test", "True");
            superprops.put("Logged In", false);
            props.put("Screen", "Home");
            props.put("Image", "iPod");
            superprops.put("Distinct Id", mixpanel.getDistinctId());
        } catch (JSONException e) {
            Log.e("Send", "Unable to add properties to JSONObject", e);
        }

        mixpanel.timeEvent("Session");
        mixpanel.registerSuperProperties(superprops);
        mixpanel.track("Viewed Screen", props);
        mixpanel.getPeople().initPushHandling(ANDROID_PUSH_SENDER_ID);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mixpanel.track("Session");
        mixpanel.flush();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mixpanel.getPeople().showNotificationIfAvailable(this);
        mixpanel.getPeople().showSurveyIfAvailable(this);
    }

    /** Called when the user clicks the Login button */
    public void goToLogin(View view) {

        JSONObject props = new JSONObject();

        try {
            props.put("Screen", "Home");
        } catch (JSONException e) {
            Log.e("Send", "Unable to add properties to JSONObject", e);
        }

        mixpanel.track("Login Button Clicked", props);

        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    /** Called when the user clicks the Signup button */
    public void goToSignup(View view) {

        JSONObject props = new JSONObject();

        try {
            props.put("Screen", "Home");
        } catch (JSONException e) {
            Log.e("Send", "Unable to add properties to JSONObject", e);
        }

        mixpanel.track("Sign Up Button Clicked", props);

        Intent intent = new Intent(this, Signup.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
