package com.cma.pgrssystem.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.cma.pgrssystem.R;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class ComplaintPersonDetailsFragment extends SherlockFragment {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		TextView tx = new TextView(getActivity());
		tx.setText("Person Details");
		return tx;
		
	}
	
	public static ComplaintPersonDetailsFragment newInstance()
	{
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
