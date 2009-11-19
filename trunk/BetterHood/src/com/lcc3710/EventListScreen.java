package com.lcc3710;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.gregbugaj.tabwidget.Tab;
import com.gregbugaj.tabwidget.TabHost;
import com.gregbugaj.tabwidget.TabHostProvider;

public class EventListScreen extends Activity {
	private Intent intent;
	private Bundle extras;
	private String sessionID;
	String delims = "\\^";
	String[] partyTokens;
	String eList;
	private OnCheckedChangeListener BasicCheckListener;
	final Activity eActivity = this;

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// todo
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		intent = getIntent();
		extras = intent.getExtras();
		// extract session id and username
		sessionID = extras.getString(BetterHood.EXTRAS_SESSION_ID);

		TabHostProvider tabProvider = new BHTabProvider(this);
		TabHost tabHost = tabProvider.getTabHost(sessionID);
		tabHost.setCurrentView(R.layout.event_list_screen);
		Tab t = tabHost.getTab("List");
		t.setSelected(true);

		setContentView(tabHost.render());
	}

	public void makeList() {

		Button buttonForward;
		buttonForward = (Button) this.findViewById(R.id.buttonForward);

		final ListView listEventView;

		listEventView = (ListView) this.findViewById(R.id.listEvents);
		listEventView.setChoiceMode(1);

		String[] aszCurrentEvents;
		ArrayAdapter<String> adapter;

		listEventView.getCheckedItemPosition();
		buttonForward.setOnClickListener(new OnClickListener() {

			public void onClick(View view) {
				for (int i = 0; i < listEventView.getCount(); i++) {
					if (listEventView.isItemChecked(i)) {
					}
				}
			}
		});

	}
}
