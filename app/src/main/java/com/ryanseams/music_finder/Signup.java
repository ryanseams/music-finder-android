package com.ryanseams.music_finder;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.ryanseams.music_finder.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class Signup extends MainActivity {

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
        EditText first = (EditText) findViewById(R.id.first_name);
        EditText last = (EditText) findViewById(R.id.last_name);
        EditText email = (EditText) findViewById(R.id.email);
        EditText password = (EditText) findViewById(R.id.password);
        Spinner spinner = (Spinner) findViewById(R.id.spinner_genre);
        String genre = spinner.getSelectedItem().toString();

        JSONObject props = new JSONObject();
        JSONObject superprops = new JSONObject();

        try {
            superprops.put("Email", email.getText().toString());
            superprops.put("Name", first.getText().toString() + last.getText().toString());
            superprops.put("Signup Date", new Date());
            props.put("Genre", genre);
            props.put("Password", password.getText().toString());
            superprops.put("Logged In", true);
        } catch (JSONException e) {
            Log.e("Send", "Unable to add properties to JSONObject", e);
        }

        mixpanel.registerSuperProperties(superprops);
        mixpanel.track("Signed Up", props);
        mixpanel.identify(email.getText().toString());
        mixpanel.getPeople().identify(email.getText().toString());
        mixpanel.getPeople().set(superprops);
        mixpanel.getPeople().increment("Logins", 1);

        Intent intent = new Intent(this, Landing.class);
        startActivity(intent);
    }

    /** Called when the user clicks the Back button */
    public void goBack(View view) {

        JSONObject props = new JSONObject();

        try {
            props.put("Screen", "Signup");
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
