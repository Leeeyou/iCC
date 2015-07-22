package com.ly.cc.view.framework;

import android.app.Activity;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ly.cc.R;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.schedulers.HandlerScheduler;
import rx.exceptions.OnErrorThrowable;
import rx.functions.Func0;

import static android.os.Process.THREAD_PRIORITY_BACKGROUND;

public class RxAndroidActivity extends Activity {


    private static final String TAG = "RxAndroidSamples";

    private Handler backgroundHandler;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rx_android);

        BackgroundThread backgroundThread = new BackgroundThread();
        backgroundThread.start();
        backgroundHandler = new Handler(backgroundThread.getLooper());

        findViewById(R.id.scheduler_example).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                onRunSchedulerExampleButtonClicked();
            }
        });
    }

    void onRunSchedulerExampleButtonClicked() {
        sampleObservable()
                // Run on a background thread
                .subscribeOn(HandlerScheduler.from(backgroundHandler))
                        // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override public void onCompleted() {
                        Log.d(TAG, "onCompleted()");
                    }

                    @Override public void onError(Throwable e) {
                        Log.e(TAG, "onError()", e);
                    }

                    @Override public void onNext(String string) {
                        if(!TextUtils.isEmpty(string)&&!string.equals("three")){
                            Log.d(TAG, "onNext(" + string + ")");
                        }
                    }
                });
    }

    static Observable<String> sampleObservable() {
        return Observable.defer(new Func0<Observable<String>>() {
            @Override public Observable<String> call() {
                try {
                    // Do some long running operation
                    Thread.sleep(TimeUnit.SECONDS.toMillis(5));
                } catch (InterruptedException e) {
                    throw OnErrorThrowable.from(e);
                }
                return Observable.just("one", "two", "three", "four", "five");
            }
        });
    }

    static class BackgroundThread extends HandlerThread {
        BackgroundThread() {
            super("SchedulerSample-BackgroundThread", THREAD_PRIORITY_BACKGROUND);
        }
    }

}
