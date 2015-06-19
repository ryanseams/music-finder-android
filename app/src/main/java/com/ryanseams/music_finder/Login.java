package com.ryanseams.music_finder;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.ryanseams.music_finder.R;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        JSONObject props = new JSONObject();

        try {
            props.put("Screen", "Login");
        } catch (JSONException e) {
            Log.e("Send", "Unable to add properties to JSONObject", e);
        }

        mixpanel.track("Viewed Screen", props);
    }

    /** Called when the user clicks the Login button */
    public void goToLanding(View view) {
        EditText email = (EditText) findViewById(R.id.email);
        EditText password = (EditText) findViewById(R.id.password);

        JSONObject props = new JSONObject();
        JSONObject superprops = new JSONObject();

        try {
            superprops.put("Email", email.getText().toString());
            props.put("Password", password.getText().toString());
            superprops.put("Logged In", true);
        } catch (JSONException e) {
            Log.e("Send", "Unable to add properties to JSONObject", e);
        }

        mixpanel.registerSuperProperties(superprops);
        mixpanel.track("Logged In", props);
        mixpanel.identify(email.getText().toString());
        mixpanel.getPeople().identify(email.getText().toString());
        mixpanel.getPeople().set(superprops);
        mixpanel.getPeople().increment("Logins", 1);

        //SPECIAL: Incremental super properties
        /*JSONObject specialProps = new JSONObject();

        try {
            specialProps.put("Incremental", 0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mixpanel.registerSuperPropertiesOnce(specialProps);
        int current = 0;
        try {
            JSONObject realprops = mixpanel.getSuperProperties().get("Incremental");
            current = realprops("Incremental")
            current = current + 1;
        } catch (Exception e) {
            Log.e("Send", "Unable to add super property", e);
        }
        JSONObject realprops = mixpanel.getSuperProperties().get("Incremental");

        try {
            specialProps.put("Incremental", current);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mixpanel.registerSuperProperties(specialProps);*/

        Intent intent = new Intent(this, Landing.class);
        startActivity(intent);
    }

    /** Called when the user clicks the Back button */
    public void goBack(View view) {

        JSONObject props = new JSONObject();

        try {
            props.put("Screen", "Login");
        } catch (JSONException e) {
            Log.e("Send", "Unable to add properties to JSONObject", e);
        }

        mixpanel.track("Login Back", props);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
