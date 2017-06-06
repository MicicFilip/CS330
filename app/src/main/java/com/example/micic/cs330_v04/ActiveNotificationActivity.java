package com.example.micic.cs330_v04;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PersistableBundle;

/**
 * Created by Filip on 06-Jun-17.
 */

public class ActiveNotificationActivity extends NotificationMainActivity {

    private ActiveNotificationFragment mFragment;

    protected static final String ACTION_NOTIFICATION_DELETE
            = "com.example.android.activenotifications.delete";

    private BroadcastReceiver mDeleteReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (mFragment == null) {
                findFragment();
            }
            mFragment.updateNumberOfNotifications();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        findFragment();
        mFragment.updateNumberOfNotifications();
    }

    private void findFragment() {
        mFragment = (ActiveNotificationFragment) getSupportFragmentManager()
                .findFragmentById(R.id.sample_content_fragment);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mDeleteReceiver, new IntentFilter(ACTION_NOTIFICATION_DELETE));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mDeleteReceiver);
    }
}
