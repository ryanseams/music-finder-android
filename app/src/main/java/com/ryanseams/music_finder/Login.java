package com.ryanseams.music_finder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Track that the user viewed the login screen
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

        // Take the user inputs from the screen
        EditText email = (EditText) findViewById(R.id.email);
        EditText password = (EditText) findViewById(R.id.password);

        // Create super properties for sending with all Mixpanel events regarding user state
        JSONObject superprops = new JSONObject();
        try {
            superprops.put("Email", email.getText().toString());
            superprops.put("Logged In", true);
        } catch (JSONException e) {
            Log.e("Send", "Unable to add properties to JSONObject", e);
        }
        mixpanel.registerSuperProperties(superprops);

        // Track that the user logged into the app
        mixpanel.track("Logged In");

        // Create people profile updates for the current user
        mixpanel.getPeople().identify(email.getText().toString());
        mixpanel.getPeople().set(superprops);
        mixpanel.getPeople().increment("Logins", 1);

        // Until you call the below at least once, people updates will not be sent
        mixpanel.identify(email.getText().toString());

        // Special: Incremental super properties

        // First create an entry for the given property value
        // The below will only write a 0 if the property key does not exist
        JSONObject specialProps = new JSONObject();
        try {
            specialProps.put("Logins", 0);
        } catch (JSONException e) {
            Log.e("Send", "Unable to add super property", e);
        }
        mixpanel.registerSuperPropertiesOnce(specialProps);
        // Once you have a property key avaialable, grab the super properties and assign the value of the key
        int current = 0;
        JSONObject realprops = new JSONObject();
        try {
            realprops = mixpanel.getSuperProperties();
            Log.e("Super", "Props are" + realprops);
            current = (int) realprops.get("Logins");
            current = current + 1;
        } catch (Exception e) {
            Log.e("Send", "Unable to add super property", e);
        }
        // Finally, assign the new incremented value back into the super properties for the chosen key
        try {
            specialProps.put("Logins", current);
        } catch (JSONException e) {
            Log.e("Send", "Unable to add super property", e);
        }
        mixpanel.registerSuperProperties(specialProps);

        // Go to the Landing Activity and switch screens
        Intent intent = new Intent(this, Landing.class);
        startActivity(intent);
    }

    /** Called when the user clicks the Back button */
    public void goBack(View view) {

        // Track that the user clicked back button from login screen
        JSONObject props = new JSONObject();
        try {
            props.put("Screen", "Login");
        } catch (JSONException e) {
            Log.e("Send", "Unable to add properties to JSONObject", e);
        }
        mixpanel.track("Login Back", props);

        // Go to the MainActivity and switch screens
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    // Below is all system default config for options menu

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
