/**
 * @author Ben Pitts
 * Timeline Calendar project from CS495 (Android app development)
 * Won't do much in emulator, as it needs calendar data and multitouch.
 * email me if you do anything cool with this idea: methodermis@gmail.com
 */
package com.ly.cc.custview.timeline.horizontalTimeline;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.net.Uri;
import android.provider.CalendarContract.Calendars;
import android.provider.CalendarContract.Events;
import android.provider.CalendarContract.Instances;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CalStuff {
    private Context context;

    public Map<Long, Clndr> CalendarsMap;
    public ArrayList<Clndr> ourCalendars;
    public Map<Long, Evnt> EventsMap;
    public ArrayList<Evnt> ourEvents;
    public ArrayList<Inst> ourInstances;

    public class Clndr {
        long _id;
        String account_name;
        String display_name;
        String name;
        int color;
        String timezone; // should be parsed

        Paint paint; // bad to have view code in model
    }

    public class Evnt {
        long _id;
        long dtstart;
        long dtend;
        int all_day;
        String title;
        String desc;
        int color;
        long calendar_id;
    }

    public class Inst {
        long _id;
        long event_id;

        /**
         * The beginning time of the instance, in UTC milliseconds. millis since epoch
         */
        long begin_ms;

        /**
         * The ending time of the instance, in UTC milliseconds. millis since epoch
         */
        long end_ms;

        /**
         * The Julian start day of the instance, relative to the local time zone.
         */
        int startday_julian;

        /**
         * The Julian end day of the instance, relative to the local time zone.
         */
        int endday_julian;

        /**
         * The start minute of the instance measured from midnight in the local time zone.
         */
        int startminute;

        /**
         * The end minute of the instance measured from midnight in the local time zone.
         */
        int endminute;
    }

    public CalStuff(Context context) {
        this.context = context;

        CalendarsMap = new HashMap<Long, Clndr>();
        EventsMap = new HashMap<Long, Evnt>();

        ourCalendars = new ArrayList<Clndr>();
        ourEvents = new ArrayList<Evnt>();
    }

    public void LoadEvents() {
        Uri uri = Events.CONTENT_URI;

        String[] projection = new String[]{
                Events._ID,
                Events.DTSTART,
                Events.DTEND,
                Events.ALL_DAY,
                Events.TITLE,
                Events.EVENT_COLOR,
                Events.CALENDAR_ID,
                Events.DESCRIPTION
        };

        int events = 0;

        Cursor c = context.getContentResolver().query(uri, projection, null, null, Events.DTSTART);

        if (c.moveToFirst()) {
            do {
                Evnt e = new Evnt();

                e._id = c.getLong(0);
                e.dtstart = c.getLong(1);
                e.dtend = c.getLong(2);
                e.all_day = c.getInt(3);
                e.title = c.getString(4);
                e.color = c.getInt(5);
                e.calendar_id = c.getLong(6);
                e.desc = c.getString(7);

                ourEvents.add(e);
                EventsMap.put(e._id, e);

            } while (c.moveToNext()/* && events++ < 20*/);
        }
    }

    public void LoadInstances() {
        if (true) return; // TODO broken.

        Uri.Builder builder = Instances.CONTENT_URI.buildUpon();
        ContentUris.appendId(builder, Long.MIN_VALUE);
        ContentUris.appendId(builder, Long.MAX_VALUE);
        Uri uri = builder.build();

        // try http://stackoverflow.com/questions/18158269/android-not-all-instances-of-recurring-event-saved

        String[] projection = new String[]{
                Instances._ID,
                Instances.EVENT_ID,
                Instances.BEGIN,
                Instances.START_DAY,
                Instances.START_MINUTE,
                Instances.END,
                Instances.END_DAY,
                Instances.END_MINUTE
        };

        int instances = 0;

        Cursor c = context.getContentResolver().query(uri, projection, null, null, null);

        if (c.moveToFirst()) {
            do {
                Inst e = new Inst();

                e._id = c.getLong(0);
                e.event_id = c.getLong(1);
                e.begin_ms = c.getLong(2);
                e.startday_julian = c.getInt(3);
                e.startminute = c.getInt(4);
                e.end_ms = c.getLong(5);
                e.endday_julian = c.getInt(6);
                e.endminute = c.getInt(7);

                ourInstances.add(e);

            } while (c.moveToNext() && instances++ < 100);
        }
    }

    public void LoadCalendars() {
        Uri uri = Calendars.CONTENT_URI;

        String[] projection = new String[]{
                Calendars._ID,
                Calendars.ACCOUNT_NAME,
                Calendars.CALENDAR_DISPLAY_NAME,
                Calendars.NAME,
                Calendars.CALENDAR_COLOR,
                Calendars.CALENDAR_TIME_ZONE
        };

        Cursor calCursor = context.getContentResolver().query(uri, projection, Calendars.VISIBLE + " = 1",
                null, Calendars._ID + " ASC");

        if (calCursor.moveToFirst()) {
            do {
                Clndr cal = new Clndr();

                cal._id = calCursor.getLong(0);
                cal.account_name = calCursor.getString(1);
                cal.display_name = calCursor.getString(2);
                cal.name = calCursor.getString(3);
                cal.color = calCursor.getInt(4);
                cal.timezone = calCursor.getString(5);

                cal.paint = new Paint();
                cal.paint.setColor(cal.color);
                cal.paint.setStyle(Style.FILL);

                ourCalendars.add(cal);
                CalendarsMap.put(cal._id, cal);

            } while (calCursor.moveToNext());
        }
    }
}
