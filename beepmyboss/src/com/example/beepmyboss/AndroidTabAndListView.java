package com.example.beepmyboss;

import com.example.androidhive.R;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class AndroidTabAndListView extends TabActivity {
	// TabSpec Names
	private static final String INBOX_SPEC = "";
	private static final String OUTBOX_SPEC = "Outbox";
	private static final String PROFILE_SPEC = "";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        
        TabHost tabHost = getTabHost();
        
        // Inbox Tab
        TabSpec inboxSpec = tabHost.newTabSpec(INBOX_SPEC);
        // Tab Icon
        inboxSpec.setIndicator(INBOX_SPEC, getResources().getDrawable(R.drawable.home));
        Intent inboxIntent = new Intent(AndroidTabAndListView.this, PostActivity.class);
        // Tab Content
        inboxSpec.setContent(inboxIntent);
        
        // Outbox Tab
//        TabSpec outboxSpec = tabHost.newTabSpec(OUTBOX_SPEC);
//        outboxSpec.setIndicator(OUTBOX_SPEC, getResources().getDrawable(R.drawable.icon_outbox));
//        Intent outboxIntent = new Intent(this, OutboxActivity.class);
//        outboxSpec.setContent(outboxIntent);
//        
//        // Profile Tab
        TabSpec profileSpec = tabHost.newTabSpec(PROFILE_SPEC);
        profileSpec.setIndicator(PROFILE_SPEC, getResources().getDrawable(R.drawable.social_person));
        Intent profileIntent = new Intent(AndroidTabAndListView.this, PostActivity.class);
        profileSpec.setContent(profileIntent);
        
//        // Adding all TabSpec to TabHost
        tabHost.addTab(inboxSpec); // Adding Inbox tab
//        tabHost.addTab(outboxSpec); // Adding Outbox tab
        tabHost.addTab(profileSpec); // Adding Profile tab
    }
}