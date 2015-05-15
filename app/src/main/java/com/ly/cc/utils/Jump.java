package com.ly.cc.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * 跳转工具类
 */
public class Jump {
	/**
	 * 跳转到activity
	 * 
	 * @param mActivity
	 * @param cls
	 */
	public static void toActivity(Activity mActivity, Class<?> cls) {
		Intent intent = new Intent(mActivity, cls);
		mActivity.startActivity(intent);
	}

	/**
	 * 带参数跳转到activity
	 * 
	 * @param mActivity
	 * @param intent
	 * @param cls
	 */
	public static void toActivity(Activity mActivity, Intent intent, Class<?> cls) {
		intent.setClass(mActivity, cls);
		mActivity.startActivity(intent);
	}

	/**
	 * 跳转到activity并finish activity
	 * 
	 * @param mActivity
	 * @param cls
	 */
	public static void toActivityFinish(Activity mActivity, Class<?> cls) {
		toActivity(mActivity, cls);
		mActivity.finish();
	}

	/**
	 * 跳转到activity并finish activity
	 * 
	 * @param mActivity
	 * @param cls
	 */
	public static void toActivityFinish(Activity mActivity, Intent intent, Class<?> cls) {
		toActivity(mActivity, intent, cls);
		mActivity.finish();
	}

	/**
	 * 跳转到网站
	 * 
	 * @param context
	 * @param url
	 */
	public static void toWebsite(Context context, String url) {
		Uri uriUrl = Uri.parse(url);
		Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
		context.startActivity(launchBrowser);
	}

	private Jump() {
		throw new UnsupportedOperationException("cannot be instantiated");
	}
}
