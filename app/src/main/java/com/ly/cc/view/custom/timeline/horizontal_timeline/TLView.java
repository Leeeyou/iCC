/**
 * @author Ben Pitts
 * Timeline Calendar project from CS495 (Android app development)
 * Won't do much in emulator, as it needs calendar data and multitouch.
 * email me if you do anything cool with this idea: methodermis@gmail.com
 */
package com.ly.cc.view.custom.timeline.horizontal_timeline;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class TLView extends View {
    private static final String LogTag = "drgn";
    private static final long SEC_MILLIS = 1000;
    private static final long MIN_MILLIS = 60 * SEC_MILLIS;
    private static final long HOUR_MILLIS = 60 * MIN_MILLIS;
    private static final long DAY_MILLIS = HOUR_MILLIS * 24;

    private Paint blackline, redline, magentaline, redtext20, blacktext30, blacktext20, blacktext15, backgroundgradient;
    private CalStuff calstuff = null;

    public void SetCalStuff(CalStuff c) {
        calstuff = c;
    }

    /**
     * width of view in pixels
     */
    int width;
    /**
     * left and right limit of the ruler in view, in milliseconds
     */
    long left, right;
    /**
     * how many fingers are being used? 0, 1, 2
     */
    int fingers;
    /**
     * holds pointer id of #1/#2 fingers
     */
    int finger1id, finger2id;
    /**
     * holds x/y in pixels of #1/#2 fingers from last frame
     */
    float finger1x, finger1y, finger2x, finger2y;

    private static final int rulery = 300;

    /**
     * width of the view in milliseconds, cached value of (right-left)
     */
    float span;
    /**
     * how many pixels does each millisecond correspond to?
     */
    float pixels_per_milli;
    /**
     * length in pixels of time units, at current zoom scale
     */
    float sec_pixels, min_pixels, hour_pixels, day_pixels;

    /**
     * reusable calendar class object for rounding time to nearest applicable unit in onDraw
     */
    Calendar acalendar;

    public TLView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TLView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TLView(Context context) {
        super(context);
        init();
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        // width/height are set here

        backgroundgradient = new Paint();
        backgroundgradient.setShader(new LinearGradient(0, 0, 0, getHeight(), Color.WHITE, 0xFFAAAAAA, Shader.TileMode.CLAMP));
    }

    private void init() {
        blackline = new Paint();
        blackline.setColor(Color.BLACK);
        blackline.setStrokeWidth(1f);
        blackline.setAntiAlias(true);
        blackline.setStyle(Style.STROKE);

        blacktext20 = new Paint();
        blacktext20.setColor(Color.BLACK);
        blacktext20.setStrokeWidth(1f);
        blacktext20.setAntiAlias(true);
        blacktext20.setStyle(Style.FILL);
        blacktext20.setTextSize(20);

        blacktext15 = new Paint();
        blacktext15.setColor(Color.BLACK);
        blacktext15.setStrokeWidth(1f);
        blacktext15.setAntiAlias(true);
        blacktext15.setStyle(Style.FILL);
        blacktext15.setTextSize(15);

        blacktext30 = new Paint();
        blacktext30.setColor(Color.BLACK);
        blacktext30.setStrokeWidth(1f);
        blacktext30.setAntiAlias(true);
        blacktext30.setStyle(Style.STROKE);
        blacktext30.setTextSize(30);

        redline = new Paint();
        redline.setColor(Color.RED);
        redline.setStrokeWidth(2f);
        redline.setAntiAlias(true);
        redline.setStyle(Style.STROKE);

        redtext20 = new Paint();
        redtext20.setColor(Color.RED);
        redtext20.setStrokeWidth(1f);
        redtext20.setAntiAlias(true);
        redtext20.setStyle(Style.FILL);
        redtext20.setTextSize(20);

        magentaline = new Paint();
        magentaline.setColor(Color.MAGENTA);
        magentaline.setStrokeWidth(1f);
        magentaline.setAntiAlias(true);
        magentaline.setStyle(Style.STROKE);

        acalendar = new GregorianCalendar();

        // start the view off somewhere, +/- some time around now
        left = System.currentTimeMillis() - 1 * DAY_MILLIS;
        right = System.currentTimeMillis() + 2 * DAY_MILLIS;
        span = right - left;
    }

    private String DayShort(int daynumber, boolean shortnotlong) {
        if (shortnotlong) {
            switch (daynumber % 7) {
                case 0:
                    return "s";
                case 1:
                    return "u";
                case 2:
                    return "m";
                case 3:
                    return "t";
                case 4:
                    return "w";
                case 5:
                    return "r";
                case 6:
                    return "f";
                default:
                    return "-";
            }
        } else {
            switch (daynumber % 7) {
                case 0:
                    return "Saturday";
                case 1:
                    return "Sunday";
                case 2:
                    return "Monday";
                case 3:
                    return "Tuesday";
                case 4:
                    return "Wednesday";
                case 5:
                    return "Thursday";
                case 6:
                    return "Friday";
                default:
                    return "derpday";
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        long next, now;

        width = getWidth(); // width of view in pixels
        // calculate span/width
        pixels_per_milli = (float) width / (float) span;
        sec_pixels = (float) SEC_MILLIS * pixels_per_milli;
        min_pixels = (float) MIN_MILLIS * pixels_per_milli;
        hour_pixels = (float) HOUR_MILLIS * pixels_per_milli;
        day_pixels = (float) DAY_MILLIS * pixels_per_milli;

        // draw background
        //canvas.drawARGB(255, 210, 210, 210);
        if (backgroundgradient != null)
            canvas.drawPaint(backgroundgradient);

        // draw 'now' line
        now = System.currentTimeMillis();
        if (left < now && now < right) {
            float dist = (now - left) / span;
            int nowx = (int) (dist * width);
            canvas.drawLine(nowx, 0, nowx, getHeight(), redline);
        }

        // draw finger circles
        if (fingers > 0) {
            canvas.drawCircle(finger1x, finger1y, 60, redline);
            if (fingers > 1) {
                canvas.drawCircle(finger2x, finger2y, 60, magentaline);
            }
        }

        // draw ruler
        canvas.drawLine(0, rulery, width, rulery, blackline);

		/*canvas.drawText("Minutes in view: " + Integer.toString((int) (width / min_pixels)), 0, getHeight()-80, redtext20);
        canvas.drawText("Day width: " + Float.toString(day_pixels), 0, getHeight()-60, redtext20);
		canvas.drawText("day > width: " + Boolean.toString(width < day_pixels), 0, getHeight()-40, redtext20);
		canvas.drawText("Hour width: " + Float.toString(hour_pixels), 0, getHeight()-20, redtext20);*/

        // round calendar down to leftmost hour
        acalendar.setTimeInMillis(left);
        // floor the calendar to various time units to find where (in ms) they start
        acalendar.set(Calendar.MILLISECOND, 0); // second start
        acalendar.set(Calendar.SECOND, 0); // minute start
        acalendar.set(Calendar.MINUTE, 0); // hour start

        // draw hours
        if (hour_pixels > 2) {
            int thehourofday = acalendar.get(Calendar.HOUR_OF_DAY);
            next = acalendar.getTimeInMillis(); // set to start of leftmost hour
            for (long i = next; i < right; i += HOUR_MILLIS) {
                float x = ((float) (i - left) / span * (float) width);
                int h24 = thehourofday % 24;
                int h12 = thehourofday % 12;
                if (h12 == 0) h12 = 12;

                if (hour_pixels < 4) {
                    if (h24 == 12)
                        canvas.drawLine(x, rulery - 10, x, rulery + 10, blackline);
                    if (h12 == 6)
                        canvas.drawLine(x, rulery - 10, x, rulery, blackline);
                } else if (hour_pixels < 20) {
                    if (h24 == 12)
                        canvas.drawLine(x, rulery - 10, x, rulery + 10, blackline);
                    else if (h12 == 6)
                        canvas.drawLine(x, rulery - 10, x, rulery, blackline);
                    else if ((h12 == 3) || (h12 == 9))
                        canvas.drawLine(x, rulery - 5, x, rulery, blackline);
                } else if (hour_pixels < 60) {
                    if (h24 == 12) {
                        canvas.drawLine(x, rulery - 20, x, rulery + 10, blackline);
                        drawhourtext(canvas, x, rulery - 20, h24, h12);
                    } else if (h12 == 6) {
                        canvas.drawLine(x, rulery - 15, x, rulery, blackline);
                        drawhourtext(canvas, x, rulery - 20, h24, h12);
                    } else if ((h12 == 3) || (h12 == 9)) {
                        canvas.drawLine(x, rulery - 10, x, rulery, blackline);
                    } else
                        canvas.drawLine(x, rulery - 5, x, rulery, blackline);
                } else {
                    if (h24 == 12) {
                        canvas.drawLine(x, rulery - 30, x, rulery + 15, blackline);
                        drawstar((int) x, rulery - 30, 20, 20, canvas, blackline);
                    } else if (h12 == 6) {
                        canvas.drawLine(x, rulery - 20, x, rulery + 10, blackline);
                        drawstar((int) x, rulery - 20, 15, 15, canvas, blackline);
                    } else if ((h12 == 3) || (h12 == 9)) {
                        canvas.drawLine(x, rulery - 15, x, rulery + 5, blackline);
                        drawstar((int) x, rulery - 15, 15, 15, canvas, blackline);
                    } else {
                        canvas.drawLine(x, rulery - 10, x, rulery, blackline);
                        drawstar((int) x, rulery - 10, 10, 10, canvas, blackline);
                    }
                    drawhourtext(canvas, x, rulery - 30, h24, h12);
                }

                thehourofday++;
				
				/*for (int j = (int) (x + (float)min_pixels/6f); j < x + min_pixels && j < right; j += (float)min_pixels/6f)
				{
					canvas.drawLine(i, rulery-40, i, rulery+10, blackline);
				}*/
            }
        } // done drawing hours

        // draw months
        // round calendar down to leftmost month
        acalendar.set(Calendar.HOUR_OF_DAY, 0); // day start
        acalendar.set(Calendar.DAY_OF_MONTH, 1); // month start
        next = acalendar.getTimeInMillis(); // set to start of leftmost month
        do {
            // draw each month
            int monthnumber = acalendar.get(Calendar.MONTH);
            int daysthismonth = acalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            String monthnamelong = acalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US);
            String monthnameshort = acalendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US);
            int daynumber = acalendar.get(Calendar.DAY_OF_WEEK);

            long daymsx = next; // first day starts at start of month
            int x = (int) ((daymsx - left) / span * width); // convert to pixels
            canvas.drawLine(x, rulery - 100, x, rulery + 20, blackline);

            if (monthnumber == 0) {
                canvas.drawLine(x, rulery - 130, x, rulery - 110, blackline);
                drawstar(x, rulery - 130, 30, 30, canvas, blackline);
                String year = Integer.toString(acalendar.get(Calendar.YEAR));
                canvas.drawText(year, x + 8, rulery - 167, blacktext30);
            }

            // draw month names
            if (day_pixels < 1) ;
            else if (day_pixels < 2) {
                // sideways month name
                canvas.save();
                canvas.rotate(-90, x, rulery);
                canvas.drawText(monthnameshort, x + 50, rulery + 30, blacktext30);
                canvas.restore();
            } else if (day_pixels < 5) {
                // short month name
                canvas.drawText(monthnameshort, x + 5, rulery - 80, blacktext30);
            } else {
                // long month name
                canvas.drawText(monthnamelong, x + 5, rulery - 80, blacktext30);
            }

            // draw days, weeks
            for (int date = 1; date <= daysthismonth; date++, daynumber++, daymsx += DAY_MILLIS) {
                x = (int) ((daymsx - left) / span * width);

                if (daynumber == 7) daynumber = 0;

                if (day_pixels < 3) ;
                if (day_pixels < 10) {
                    // really tiny days
                    if (daynumber == 1)
                        canvas.drawLine(x, rulery - 10, x, rulery + 10, blackline);
                } else if (day_pixels < 40) {
                    // tiny days
                    if (daynumber == 1)
                        canvas.drawLine(x, rulery - 50, x, rulery + 10, blackline);
                    else
                        canvas.drawLine(x, rulery - 50, x, rulery, blackline);
                } else if (day_pixels > 170) {
                    // big days
                    if (daynumber == 1)
                        canvas.drawLine(x, rulery - 100, x, rulery + 50, blackline);
                    else
                        canvas.drawLine(x, rulery - 100, x, rulery + 10, blackline);

                    drawstar(x, rulery - 100, 20, 20, canvas, blackline);
                    canvas.drawText(Integer.toString(date), x + 10, rulery - 40, blacktext30);
                    canvas.drawText(DayShort(daynumber, false), x + 10, rulery - 10, blacktext30);
                } else {
                    // sideways days
                    if (daynumber == 1)
                        canvas.drawLine(x, rulery - 60, x, rulery + 30, blackline);
                    else
                        canvas.drawLine(x, rulery - 60, x, rulery, blackline);

                    canvas.save();
                    canvas.rotate(-90, x, rulery);
                    canvas.drawText(Integer.toString(date), x + 10, rulery + 30, blacktext30);
                    canvas.drawText(DayShort(daynumber, true), x + 40, rulery + 30, blacktext30);
                    canvas.restore();
                }
            }

            acalendar.add(Calendar.MONTH, 1);
            next = acalendar.getTimeInMillis();
        } while (next < right);
        // done drawing months

        // draw some events maybe
        if (calstuff != null) {
            for (int i = 0; i < calstuff.ourEvents.size(); i++) {
                CalStuff.Evnt e = calstuff.ourEvents.get(i);

                if ((e.dtend < left) || (e.dtstart > right)) continue;

                int x = (int) ((e.dtstart - left) / span * width);
                int x2 = (int) ((e.dtend - left) / span * width);
                int y = rulery + 25;
                float h = 40;
                RectF r = new RectF(x, y, x2, y + h); // left top right bottom

                CalStuff.Clndr cal = calstuff.CalendarsMap.get(e.calendar_id);
                canvas.drawRect(r, cal.paint);
                canvas.drawRect(r, blackline);

                // draw text labels
                if ((e.title == null) || (day_pixels < 35)) ;
                else if (day_pixels < 60) {
                    Paint p = blacktext15;
                    String t = e.title;
                    //float w = p.measureText(t);

                    canvas.save();
                    canvas.rotate(90, x, y);
                    canvas.drawText(e.title, x + h, y, p);
                    canvas.restore();

                    if (e.desc != null) if (e.desc.length() > 0)
                        canvas.drawText("...", x + h / 2, y + 3 * h / 2, blacktext15);
                } else {
                    String t = e.title;

                    canvas.save();
                    canvas.clipRect(r);
                    canvas.drawText(t, x, y + h / 2, blacktext20);
                    canvas.restore();

                    Paint p = blacktext20;
                    //float w = p.measureText(t);

                    canvas.save();
                    canvas.rotate(90, x, y);
                    canvas.drawText(e.title, x + h, y, p);
                    canvas.restore();

                    if (e.desc != null) if (e.desc.length() > 0)
                        canvas.drawText(e.desc, x + h / 2, y + 3 * h / 2, blacktext15);
                }
            }
        }
    }

    private void drawhourtext(Canvas canvas, float x, float y, int h24, int h12) {
        if (h24 < 12)
            canvas.drawText(Integer.toString(h12) + "a", x, y, blacktext30);
        else
            canvas.drawText(Integer.toString(h12) + "p", x, y, blacktext30);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionevent) {
        switch (motionevent.getActionMasked()) {
            // First finger down, start panning
            case MotionEvent.ACTION_DOWN:

                fingers = 1; // panning mode

                // save id and coords
                finger1id = motionevent.getPointerId(motionevent.getActionIndex());
                finger1x = motionevent.getX();
                finger1y = motionevent.getY();

                Log.d(LogTag, "down " + finger1x);
                invalidate(); // redraw
                return true;

            // Second finger down, start scaling
            case MotionEvent.ACTION_POINTER_DOWN:

                if (fingers == 2) // if already tracking 2 fingers
                    break; // ignore 3rd finger
                // else fingers == 1
                fingers = 2; // scaling mode

                // save id and coords
                finger2id = motionevent.getPointerId(motionevent.getActionIndex());
                finger2x = motionevent.getX(finger2id);
                finger2y = motionevent.getY(finger2id);

                Log.d(LogTag, "2down " + finger2x);
                invalidate(); // redraw
                return true;

            case MotionEvent.ACTION_MOVE:

                if (fingers == 0) // if not tracking fingers as down
                    return false; // ignore move events

                float new1x,
                        new1y,
                        new2x,
                        new2y; // Hold new positions of two fingers

                // get finger 1 position
                int pointerindex = motionevent.findPointerIndex(finger1id);
                if (pointerindex == -1) // no change
                {
                    new1x = finger1x; // use values from previous frame
                    new1y = finger1y;
                } else
                // changed
                {
                    // get new values
                    new1x = motionevent.getX(pointerindex);
                    new1y = motionevent.getY(pointerindex);
                }

                // get finger 2 position
                pointerindex = motionevent.findPointerIndex(finger2id);
                if (pointerindex == -1) {
                    new2x = finger2x;
                    new2y = finger2y;
                } else {
                    new2x = motionevent.getX(pointerindex);
                    new2y = motionevent.getY(pointerindex);
                }

                // panning
                if (fingers == 1) {
                    // how far to scroll in milliseconds to match the scroll input in pixels
                    long delta1xinmillis = (long) ((finger1x - new1x) * span / width); // (deltax)*span/width
                    // = delta-x
                    // in
                    // milliseconds
                    // pan the view
                    left += delta1xinmillis;
                    right += delta1xinmillis;
                }
                // scaling
                else if (fingers == 2) {
                    // don't scale if fingers too close, or past each other
                    if (Math.abs(new1x - new2x) < 10) return true;
                    if (finger1x > finger2x) if (new1x < new2x) return true;
                    if (finger1x < finger2x) if (new1x > new2x) return true;

                    // find ruler time in ms under each finger at start of move
                    // y = mx+b, b = left, span = right - left [ms]
                    double m = (double) span / (double) width; // m = span/width
                    double y1 = m * finger1x + left; // ms at finger1
                    double y2 = m * finger2x + left; // ms at finger2
                    // y values are set to the millisecond time shown at the old finger1x and
                    // finger2x, using old left and right span
                    // construct a new line equation through points (new1x,y1),(new2x,y2)
                    // f(x) = y1 + (x - new1x) * (y2 - y1) / (new2x - new1x)
                    left = (long) (y1 + (0 - new1x) * (y2 - y1) / (new2x - new1x));
                    right = (long) (y1 + (width - new1x) * (y2 - y1) / (new2x - new1x));
                    span = right - left; // span of milliseconds in view
                }

                // save
                finger1x = new1x;
                finger1y = new1y;
                finger2x = new2x;
                finger2y = new2y;

                invalidate(); // redraw with new left,right
                return true;

            case MotionEvent.ACTION_POINTER_UP:
                int id = motionevent.getPointerId(motionevent.getActionIndex());

                if (id == finger1id) {
                    // 1st finger went up, make 2nd finger new firstfinger and go back to panning
                    finger1id = finger2id;
                    finger1x = finger2x; // copy coords so view won't jump to other finger
                    finger1y = finger2y;
                    fingers = 1; // panning
                } else if (id == finger2id) {
                    // 2nd finger went up, just go back to panning
                    fingers = 1; // panning
                } else {
                    return false; // ignore 3rd finger ups
                }
                invalidate(); // redraw
                return true;

            case MotionEvent.ACTION_CANCEL:
                Log.d(LogTag, "cancel!"); // herpderp
                return true;

            case MotionEvent.ACTION_UP:
                // last pointer up, no more motionevents
                fingers = 0;
                invalidate(); // redraw
                return true;
        }
        return super.onTouchEvent(motionevent);
    }

    private void drawstar(int x, int y, int halfwidth, int halfheight, Canvas canvas, Paint paint) {
        int midx = x;
        int midy = y - halfheight;
        Path path = new Path();
        path.moveTo(x, y);
        path.quadTo(midx, midy, x - halfwidth, y - halfheight);
        path.quadTo(midx, midy, x, y - halfheight - halfheight);
        path.quadTo(midx, midy, x + halfwidth, y - halfheight);
        path.quadTo(midx, midy, x, y);
        canvas.drawPath(path, paint);
    }
}
