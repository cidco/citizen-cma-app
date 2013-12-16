package com.cma.pgrssystem.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class PgrsHelper {

	/**
	 * Private Constructor to force singleton
	 */
	private PgrsHelper() {

	}

	private static PgrsHelper sInstance = new PgrsHelper();

	public static PgrsHelper getInstance() {
		return sInstance;
	}

	public String getUniqueImageFilename() {
		return new StringBuffer().append(PgrsConstants.PREFIX_IMAGE)
				.append(System.currentTimeMillis())
				.append(PgrsConstants.SUFFIX_JPG_FILE_EXTENSION).toString();
	}

	public boolean isOnline(Activity activity) {
		ConnectivityManager connectivityManager = (ConnectivityManager) activity
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		} else {
			Crouton.makeText(activity, "No Internet Connection", Style.INFO)
					.show();
		}
		return false;
	}

	public void invokePhoneCall(Context context, String phoneNumber) {
		Intent intent = new Intent(Intent.ACTION_DIAL);
		intent.setData(Uri.parse("tel:" + phoneNumber));
		context.startActivity(intent);
	}

	public void invokeSms(Context context, String phoneNumber) {
		Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
		smsIntent.addCategory(Intent.CATEGORY_DEFAULT);
		smsIntent.setType("vnd.android-dir/mms-sms");
		smsIntent.setData(Uri.parse("sms:" + phoneNumber));
		context.startActivity(smsIntent);
	}

	public void hideSoftKeyboard(Activity activity) {
		InputMethodManager inputMethodManager = (InputMethodManager) activity
				.getSystemService(Activity.INPUT_METHOD_SERVICE);

		// Hide Soft Keyboard only if it is available
		if (activity.getCurrentFocus() != null) {
			inputMethodManager.hideSoftInputFromWindow(activity
					.getCurrentFocus().getWindowToken(), 0);
		}
	}

	public void setupUI(View view, final Activity activity) {

		// Set up touch listener for non-text box views to hide keyboard.
		if (!(view instanceof EditText)) {

			view.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					hideSoftKeyboard(activity);
					return false;
				}

			});
		}

		// If a layout container, iterate over children and seed recursion.
		if (view instanceof ViewGroup) {

			for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

				View innerView = ((ViewGroup) view).getChildAt(i);

				setupUI(innerView, activity);
			}
		}
	}

}
