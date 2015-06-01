/**
 * @author Ben Pitts
 * Timeline Calendar project from CS495 (Android app development)
 * Won't do much in emulator, as it needs calendar data and multitouch.
 * email me if you do anything cool with this idea: methodermis@gmail.com
 */
package com.ly.cc.view.custom_controls.timeline;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.ly.cc.R;
import com.ly.cc.custom_controls.timeline.horizontal_timeline.CalStuff;
import com.ly.cc.custom_controls.timeline.horizontal_timeline.TLView;

public class HorizontalTimelineActivity extends ActionBarActivity
{
	private static final String LogTag = "drgn";

	// TODO move timer thing to view itself
	Handler handler = new Handler();
	Runnable runnable = new Runnable()
	{
		public void run()
		{
			contentView.postInvalidate();
			handler.postDelayed(runnable, 250);
		}
	};

	private TLView contentView;
	private CalStuff calstuff;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		calstuff = new CalStuff(this);

		setContentView(R.layout.activity_main_timeline_horizontal);

		contentView = (TLView) findViewById(R.id.fullscreen_content);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState)
	{
		super.onPostCreate(savedInstanceState);

		calstuff.LoadCalendars();
		Log.d(LogTag, "calendars loaded: " + calstuff.ourCalendars.size());
		
		calstuff.LoadEvents();
		Log.d(LogTag, "events loaded: " + calstuff.ourEvents.size());
		
		/*calstuff.LoadInstances();
		Log.d(LogTag, "instances loaded: " + calstuff.ourInstances.size());*/
		
		contentView.SetCalStuff(calstuff);
	}

	@Override
	protected void onPause()
	{
		Log.d(LogTag, "pause");
		super.onPause();

		handler.removeCallbacks(runnable);
	}

	@Override
	protected void onResume()
	{
		Log.d(LogTag, "resume");
		super.onResume();

		runnable.run();
	}
}
