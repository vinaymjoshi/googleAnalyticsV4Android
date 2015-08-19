package com.unikve.googleanalyticssample;

import android.app.Activity;
import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.analytics.Tracker;

import java.util.HashMap;

/**
 * Created by Vinay on 18-Aug-15.
 */
public class AnalyticsApplication extends Application
{

    private static final String TRACKER_ID = "UA-66439289-1";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static int GENERAL_TRACKER = 0;

    public enum TrackerName {
        APP_TRACKER, // Tracker used only in this app.
        GLOBAL_TRACKER, // Tracker used by all the apps from a company. eg: roll-up tracking.
        ECOMMERCE_TRACKER
    }

    protected HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();

    public synchronized Tracker getTracker(TrackerName trackerId) {
        if (!mTrackers.containsKey(trackerId)) {

            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            analytics.getLogger().setLogLevel(Logger.LogLevel.VERBOSE);
            Tracker t = (trackerId == TrackerName.APP_TRACKER) ? analytics.newTracker(R.xml.app_tracker)/*(TRACKER_ID)*/
                   // : (trackerId == TrackerName.GLOBAL_TRACKER) ? analytics.newTracker(TRACKER_ID)
                   // : analytics.newTracker(R.xml.ecommerce_tracker)
                    :analytics.newTracker(TRACKER_ID) ;
            t.enableAdvertisingIdCollection(true); //if Admob is used

            mTrackers.put(trackerId, t);

        }
        return mTrackers.get(trackerId);
    }
    public void trackEvent(Activity activity,String screenName, String category,String action)
    {

		GoogleAnalytics analytics = GoogleAnalytics.getInstance(activity);
		Tracker tracker=getTracker(TrackerName.APP_TRACKER);
		tracker.setScreenName(screenName);
		tracker.send(new HitBuilders.EventBuilder().setCategory(category) //"UX"
                .setAction(action) //"click"
                .setLabel(screenName + " event")
                        //.set(Fields.SCREEN_NAME, "help popup dialog")
                .build());

    }
    public void trackScreenView(Activity activity,String screenName)
    {

        GoogleAnalytics analytics = GoogleAnalytics.getInstance(activity);
        analytics.getLogger().setLogLevel(Logger.LogLevel.VERBOSE);
        Tracker tracker=getTracker(TrackerName.APP_TRACKER);
        tracker.setScreenName(screenName);
        tracker.enableAutoActivityTracking(true);
        tracker.enableExceptionReporting(true);
        //tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }
    public void reportActivityStart(Activity activity)
    {
        GoogleAnalytics.getInstance(activity).reportActivityStart(activity);
    }
    public void reportActivityStop(Activity activity)
    {
        GoogleAnalytics.getInstance(activity).reportActivityStop(activity);
    }

}
