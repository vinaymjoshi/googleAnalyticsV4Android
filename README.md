Step 1: Add following depandency in your app build.gradle:
    compile 'com.google.android.gms:play-services:7.5.0'
Note: Make sure version is available in your sdk, if not check which version is available and change 7.5.0 to available version.

Step 2: Add AnalyticsApplication.java in your application main package where your default application class exists.

Step 3: Make your top level application class to extend from AnalyticsApplication. for example :
    public class MyApplication extends AnalyticsApplication
    {
        @Override
        public void onCreate() {
        super.onCreate();
        ....
        }
    }

Step 4: Declare the MyApplication as your application class in manifest if noot already done.
    <application
        android:name=".MyApplication"
        ...
        ...
        >
Step 5: Add following permisstions:
    <!-- Get permission for reliable local dispatching on non-Google Play devices. -->
    <uses-permission android:name="android.permission.WAKE_LOCK" />
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

Step 6: Add following declarions in <application> tag:
    <receiver android:name="com.google.android.gms.analytics.AnalyticsReceiver"
              android:enabled="true">
        <intent-filter>
            <action android:name="com.google.android.gms.analytics.ANALYTICS_DISPATCH" />
        </intent-filter>
    </receiver>
    <service android:name="com.google.android.gms.analytics.AnalyticsService"
             android:enabled="true"
             android:exported="false"/>
    <!-- Optionally, register CampaignTrackingReceiver and CampaignTrackingService to enable
        installation campaign reporting -->
    <receiver android:name="com.google.android.gms.analytics.CampaignTrackingReceiver"
              android:exported="true">
        <intent-filter>
            <action android:name="com.android.vending.INSTALL_REFERRER" />
        </intent-filter>
    </receiver>
    <service android:name="com.google.android.gms.analytics.CampaignTrackingService" />
    <meta-data
        android:name="com.google.android.gms.version"
        android:value="@integer/google_play_services_version" />
    <meta-data
        android:name="com.google.android.gms.analytics.globalConfigResource"
        android:resource="@xml/global_tracker" />

Step 7: Add xml folder from this sample into your res/ if not already exists. If already exists, add app_tracker.xml and global_tracker.xml files.

Step 8: Update  <string name="ga_trackingId">UA-66439289-1</string> value (in global_tracker.xml and app_tracker.xml) with your application tracking ID.

Step 9: Add screenName tag for all activitites/fragments in app_tracker.xml which you want to track and give a human readable name for each screen. for example:
<screenName name="com.unikve.googleanalyticssample.MainActivity">MainActivityScreen</screenName>

Step 9: Track page views in any activity as below:
    @Override
    protected void onStart() {
        super.onStart();
        ((MyApplication) getApplication()).reportActivityStart(this);
    }
    @Override
    protected void onStop() {
        super.onStop();
        ((MyApplication) getApplication()).reportActivityStop(this);
    }

Step 10: Track custom events as below (for example button click):
    ((MyApplication) getApplication()).trackEvent(MainActivity.this,"MainActivity","UX","editprofile-button-click");

Step 11: Finish. 

Note: Events and page views take 24-48 hours to update in google analytics dashboard however, you should see real time events in real time dashboard page after few minutes of events.