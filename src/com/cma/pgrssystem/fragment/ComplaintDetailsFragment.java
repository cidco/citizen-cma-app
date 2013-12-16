package com.cma.pgrssystem.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.cma.pgrssystem.HomeActivity;
import com.cma.pgrssystem.R;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class ComplaintDetailsFragment extends SherlockFragment {
	
	private View detailsView = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		detailsView = inflater.inflate(R.layout.fragment_complaint_details, container, false);
		return detailsView;
		
	}
	
	public static ComplaintDetailsFragment newInstance()
	{
		ComplaintDetailsFragment fragment = new ComplaintDetailsFragment();
		return fragment;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.menu_complaints_details, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_about_complaints) {
			Crouton.showText(getActivity(), "About", Style.INFO);
			return true;
		}
		
		if (item.getItemId() == R.id.action_licenses_complaints) {
			Crouton.showText(getActivity(), "Licenses", Style.INFO);
			return true;
		}
		
		if (item.getItemId() == R.id.action_camera_complaints) {
			((HomeActivity)getActivity()).invokeSelectPhotosAction();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
