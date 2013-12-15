package com.cma.pgrssystem;

import org.acra.ACRA;
import org.acra.ACRAConfiguration;
import org.acra.ReportField;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;
import org.holoeverywhere.app.Application;


@ReportsCrashes(socketTimeout = 5000, formKey = "", mailTo = "sudhans@gmail.com",

mode = ReportingInteractionMode.DIALOG, resToastText = R.string.crash_toast_text, resNotifTickerText = R.string.crash_notif_ticker_text, resNotifTitle = R.string.crash_notif_title, resNotifText = R.string.crash_notif_text, resNotifIcon = android.R.drawable.stat_notify_error, resDialogText = R.string.crash_dialog_text, resDialogIcon = android.R.drawable.ic_dialog_alert, resDialogTitle = R.string.crash_dialog_title, resDialogCommentPrompt = R.string.crash_dialog_comment_prompt, customReportContent = {
		ReportField.ANDROID_VERSION, ReportField.APP_VERSION_NAME,
		ReportField.PHONE_MODEL, ReportField.PACKAGE_NAME,
		ReportField.USER_COMMENT, ReportField.STACK_TRACE }, resDialogOkToast = R.string.crash_dialog_ok_toast)
public class PgrsApplication extends Application {

	@Override
	public void onCreate() {

		// Initialize ACRA Library
		ACRAConfiguration config = ACRA.getNewDefaultConfig(this);
		config.deleteOldUnsentReportsOnApplicationStart();
		config.deleteUnapprovedReportsOnApplicationStart();
		ACRA.setConfig(config);
		ACRA.init(this);

		super.onCreate();

	}

}
