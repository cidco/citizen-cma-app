package com.cma.pgrssystem.service;

import java.util.ArrayList;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.cma.pgrssystem.HomeActivity;
import com.cma.pgrssystem.R;
import com.cma.pgrssystem.util.PgrsConstants;

public class ComplaintStatusIntentService extends IntentService {

	private static final String TAG = ComplaintStatusIntentService.class.getName();
	private static ComplaintStatusIntentService sInstance = new ComplaintStatusIntentService();
	private int SP_NOTIFICATION_ID = 21200;
	private String complaintNumber = null;
	private ArrayList<String> complaintNumberList = null;

	public ComplaintStatusIntentService() {
		super(TAG);
	}

	public static ComplaintStatusIntentService getInstance() {
		return sInstance;
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d(TAG, "Inside Get sTatus Intent Service");
		complaintNumber = intent
				.getStringExtra(PgrsConstants.INTENT_PARAM_COMPLAINT_NUMBER);
		complaintNumberList = intent
				.getStringArrayListExtra(PgrsConstants.INTENT_PARAM_COMPLAINT_NUMBER_LIST);

		// Make HTTP Call & invoke notification
		stopSelf();
	}

	private void raiseNotification(String status, String errorMessage) {
		NotificationCompat.Builder b = new NotificationCompat.Builder(this);
		
		final String STATUS_MESSAGE_SUCCESS = "Status of complaint# " + complaintNumber + " is " + status;
		final String STATUS_MESSAGE_FAILED = "Could not retrieve status for complaint# " + complaintNumber + ". Please try again"; 

		b.setAutoCancel(true).setDefaults(Notification.DEFAULT_ALL)
				.setWhen(System.currentTimeMillis());

		if (errorMessage == null) {
			b.setContentTitle(getString(R.string.pgrs_title))
			.setContentText(STATUS_MESSAGE_SUCCESS)
					.setSmallIcon(R.drawable.ic_stat_pgrs)
					.setTicker(STATUS_MESSAGE_SUCCESS);

			Intent outbound = new Intent(this, HomeActivity.class);
			b.setContentIntent(PendingIntent.getActivity(this, 0, outbound, 0));
		} else {
			b.setContentTitle(getString(R.string.pgrs_title))
					.setContentText(errorMessage)
					.setSmallIcon(android.R.drawable.stat_notify_error)
					.setTicker(STATUS_MESSAGE_FAILED);
			Intent outbound = new Intent(this, HomeActivity.class);
			b.setContentIntent(PendingIntent.getActivity(this, 0, outbound, 0));
		}

		NotificationManager mgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		mgr.notify(SP_NOTIFICATION_ID, b.build());
	}

}
