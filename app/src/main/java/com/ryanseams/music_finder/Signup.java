package com.ryanseams.music_finder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Date;

public class Signup extends MainActivity {

    // Create the spinner object for picking a genre of music
    private static Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Set the spinner with genre options
        Spinner spinner = (Spinner) findViewById(R.id.spinner_genre);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.genre_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        // Track that the user viewed the signup screen
        JSONObject props = new JSONObject();
        try {
            props.put("Screen", "Signup");
        } catch (JSONException e) {
            Log.e("Send", "Unable to add properties to JSONObject", e);
        }
        mixpanel.track("Viewed Screen", props);
    }

    /** Called when the user clicks the Signup button */
    public void goToLanding(View view) {

        // Take the user inputs from the screen
        EditText first = (EditText) findViewById(R.id.first_name);
        EditText last = (EditText) findViewById(R.id.last_name);
        EditText email = (EditText) findViewById(R.id.email);
        EditText password = (EditText) findViewById(R.id.password);
        Spinner spinner = (Spinner) findViewById(R.id.spinner_genre);
        String genre = spinner.getSelectedItem().toString();

        // Create super properties for sending with all Mixpanel events regarding user state
        JSONObject superprops = new JSONObject();
        try {
            superprops.put("Email", email.getText().toString());
            superprops.put("Name", first.getText().toString() + last.getText().toString());
            superprops.put("Signup Date", new Date());
            superprops.put("Genre", genre);
            superprops.put("Logged In", true);
        } catch (JSONException e) {
            Log.e("Send", "Unable to add properties to JSONObject", e);
        }
        mixpanel.registerSuperProperties(superprops);

        // Track that the user signed up for the app
        mixpanel.track("Signed Up");

        // Create an alias from our unique identifier to the original distinct id
        mixpanel.alias(email.getText().toString(), mixpanel.getDistinctId());

        // Create people profile updates for the current user
        // Note we can call identify with the alias because the alias call flushes immediately in Android
        mixpanel.getPeople().identify(email.getText().toString());
        mixpanel.getPeople().set(superprops);
        mixpanel.getPeople().increment("Logins", 1);

        // Until you call the below at least once, people updates will not be sent
        mixpanel.identify(email.getText().toString());

        // Go to the Landing Activity and switch screens
        Intent intent = new Intent(this, Landing.class);
        startActivity(intent);
    }

    /** Called when the user clicks the Back button */
    public void goBack(View view) {

        // Track that the user clicked back button from signup screen
        JSONObject props = new JSONObject();
        try {
            props.put("Screen", "Signup");
        } catch (JSONException e) {
            Log.e("Send", "Unable to add properties to JSONObject", e);
        }
        mixpanel.track("Signup Back", props);

        // Go to the MainActivity and switch screens
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    // Below is all system default config for options menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_signup, menu);
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
