package com.cma.pgrssystem.fragment;

import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.EditText;
import org.holoeverywhere.widget.TextView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.cma.pgrssystem.HomeActivity;
import com.cma.pgrssystem.R;
import com.cma.pgrssystem.listener.AsyncTaskCallBack;
import com.cma.pgrssystem.util.PgrsConstants;
import com.cma.pgrssystem.util.PgrsHelper;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class StatusFragment extends SherlockFragment {

	private EditText complaintNumberEdit = null;
	private TextView complaintStatusEdit = null;
	private Button statusButton = null;
	private View statusFragmentView = null;
	private String complaintNumber = null;
	private String complaintStatus = PgrsConstants.STATUS_INPROGRESS;
	private AsyncTaskCallBack statusTaskCallBacks = null;
	private StatusAsyncTask statusTask = null;
	private RelativeLayout complaintStatusLayout = null;

	private class StatusAsyncTask extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			if (statusTaskCallBacks != null) {
				// Disable Get Status button and display progress bar
				statusTaskCallBacks.onPreExecute();
				statusButton.setEnabled(false);
				complaintNumberEdit.setText(PgrsConstants.EMPTY_TEXT);
				complaintStatusLayout.setVisibility(View.GONE);
			}
		}

		@Override
		protected String doInBackground(String... params) {
			String errorMessage = null;

			return errorMessage;
		}

		@Override
		protected void onCancelled() {
			if (statusTaskCallBacks != null) {
				statusTaskCallBacks.onCancelled();
			}
		}

		@Override
		protected void onPostExecute(String pErrorMessage) {

			updateStatusUi();
			if (statusTaskCallBacks != null) {
				statusTaskCallBacks.onPostExecute();
				// Enable Get Status button and hide progress bar
				statusButton.setEnabled(true);
				complaintStatusLayout.setVisibility(View.VISIBLE);

				updateStatusUi();

			}
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		statusTaskCallBacks = (AsyncTaskCallBack) activity;
	}

	@Override
	public void onDetach() {
		super.onDetach();
		statusTaskCallBacks = null;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		setHasOptionsMenu(true);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// Inflate the layout
		statusFragmentView = inflater.inflate(R.layout.fragment_status,
				container, false);

		// Hide soft keyboard when touched outside
		PgrsHelper.getInstance().setupUI(statusFragmentView, getActivity());

		complaintNumberEdit = (EditText) statusFragmentView
				.findViewById(R.id.edittext_complaint_number_text);
		complaintStatusLayout = (RelativeLayout) statusFragmentView
				.findViewById(R.id.layout_complaint_status_text);

		complaintStatusEdit = (TextView) statusFragmentView
				.findViewById(R.id.textview_complaint_status_text);

		Animation anim = new AlphaAnimation(0.0f, 1.0f);
		anim.setDuration(400); // You can manage the time of the blink with this
								// parameter
		anim.setStartOffset(10);
		anim.setRepeatMode(Animation.REVERSE);
		anim.setRepeatCount(Animation.INFINITE);
		complaintStatusEdit.startAnimation(anim);

		statusButton = (Button) statusFragmentView
				.findViewById(R.id.button_get_complaint_status);

		statusButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if (validateInputs()) {

					// Check Internet Connection
					if (PgrsHelper.getInstance().isOnline(getActivity())) {

						// Call Async Task
						if (statusTask == null
								|| statusTask.getStatus().equals(
										Status.FINISHED)) {
							statusTask = new StatusAsyncTask();
							statusTask.execute();
						}

					}
				}
			}
		});

		return statusFragmentView;

	}

	public static StatusFragment newInstance() {
		StatusFragment fragment = new StatusFragment();
		return fragment;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.menu_status, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == R.id.action_register) {
			((HomeActivity) getActivity()).getComplaintParentFragment();
			return true;
		}

		if (item.getItemId() == R.id.action_call) {
			PgrsHelper.getInstance().invokePhoneCall(getActivity(),
					PgrsConstants.PHONE_COMPLAINTS_CALL);
			return true;
		}

		if (item.getItemId() == R.id.action_message) {
			PgrsHelper.getInstance().invokeSms(getActivity(),
					PgrsConstants.PHONE_COMPLAINTS_SMS);
			return true;
		}

		if (item.getItemId() == R.id.action_about) {
			Crouton.showText(getActivity(), "About Coming Soon..", Style.INFO);
			return true;
		}

		if (item.getItemId() == R.id.action_licenses) {
			Crouton.showText(getActivity(), "Licenses Coming Soon..",
					Style.INFO);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private boolean validateInputs() {
		boolean result = true;

		complaintNumber = complaintNumberEdit.getText().toString();

		if (TextUtils.isEmpty(complaintNumber)) {
			complaintNumberEdit.setError(getResources().getString(
					R.string.error_field_required));
			result = false;
		}

		return result;
	}

	@SuppressLint("NewApi")
	private void updateStatusUi() {

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
		{
		if (complaintStatus.equals(PgrsConstants.STATUS_CANCELLED)) {
			complaintStatusLayout.setBackground(getResources().getDrawable(R.drawable.bg_status_result_gray));
		}

		else if (complaintStatus.equals(PgrsConstants.STATUS_INPROGRESS)) {
			complaintStatusLayout.setBackground(getResources().getDrawable(R.drawable.bg_status_result_yellow));		}

		else if (complaintStatus.equals(PgrsConstants.STATUS_PENDING)) {
			complaintStatusLayout.setBackground(getResources().getDrawable(R.drawable.bg_status_result_red));
		}

		else if (complaintStatus.equals(PgrsConstants.STATUS_RESOLVED)) {
			complaintStatusLayout.setBackground(getResources().getDrawable(R.drawable.bg_status_result_green));
		}
		}
		else
		{
			if (complaintStatus.equals(PgrsConstants.STATUS_CANCELLED)) {
				complaintStatusLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_status_result_gray));
			}

			else if (complaintStatus.equals(PgrsConstants.STATUS_INPROGRESS)) {
				complaintStatusLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_status_result_yellow));		}

			else if (complaintStatus.equals(PgrsConstants.STATUS_PENDING)) {
				complaintStatusLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_status_result_red));
			}

			else if (complaintStatus.equals(PgrsConstants.STATUS_RESOLVED)) {
				complaintStatusLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_status_result_green));
			}
		}

	}
}
