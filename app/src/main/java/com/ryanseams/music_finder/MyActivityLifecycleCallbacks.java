package com.ryanseams.music_finder;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class MyActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {

    // Create our local instance of the MixpanelAPI
    MixpanelAPI mixpanelCallbacks;

    // Add a list for tracking which activities are currently active
    List<String> status;

    public MyActivityLifecycleCallbacks(MixpanelAPI mpInstance) {
        // Assign our local variables
        mixpanelCallbacks = mpInstance;
        status = new ArrayList<>();
    }

    public List<String> getStatus() {
        return status;
    }

    @Override
    public void onActivityStarted(Activity activity) {
        // Add to the current list of running activities
        status.add(activity.toString());

        // Check to see if there is a current session in progress or not
        // If there is no active session, start a new timer and log to Mixpanel
        if (!mixpanelCallbacks.getSuperProperties().has("Session")) {
            // Start session timer for tracking app session lengths
            mixpanelCallbacks.timeEvent("$app_open");

            // Register a super property to indicate the session is in progress
            JSONObject superprops = new JSONObject();
            try {
                superprops.put("Session", true);
            } catch (JSONException e) {
                Log.e("Send", "Unable to add super properties to JSONObject", e);
            }
            mixpanelCallbacks.registerSuperProperties(superprops);
        }

        Log.d("Current Activities", "onStart() " + status.toString());
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) { }

    @Override
    public void onActivityPaused(Activity activity) { }

    @Override
    public void onActivityDestroyed(Activity activity) { }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) { }

    @Override
    public void onActivityResumed(Activity activity) { }

    @Override
    public void onActivityStopped(Activity activity) {
        // Remove from the current list of running activities
        status.remove(activity.toString());

        // If there are no running activities, the app is backgrounded
        // In this scenario, log the event to Mixpanel
        if(status.isEmpty()) {
            // Send the session tracking information to Mixpanel
            mixpanelCallbacks.track("$app_open");

            // Mark the current session as inactive so we properly start a new one
            mixpanelCallbacks.unregisterSuperProperty("Session");

            // Force all queued Mixpanel data to be sent to Mixpanel
            mixpanelCallbacks.flush();
        }

        Log.d("Current Activities", "onStop() " + status.toString());
    }
}
