package com.cma.pgrssystem.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.cma.pgrssystem.R;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class ComplaintPersonDetailsFragment extends SherlockFragment {

	private View personalDetailsView = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		personalDetailsView = inflater.inflate(
				R.layout.fragment_complaint_personal_details, container, false);
		return personalDetailsView;

	}

	public static ComplaintPersonDetailsFragment newInstance() {
		ComplaintPersonDetailsFragment fragment = new ComplaintPersonDetailsFragment();
		return fragment;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_about) {
			Crouton.showText(getActivity(), "About", Style.INFO);
			return true;
		}

		if (item.getItemId() == R.id.action_licenses) {
			Crouton.showText(getActivity(), "Licenses", Style.INFO);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
