package com.cma.pgrssystem.fragment;

import java.util.ArrayList;
import java.util.Arrays;

import org.holoeverywhere.widget.AdapterView;
import org.holoeverywhere.widget.AdapterView.OnItemSelectedListener;
import org.holoeverywhere.widget.ArrayAdapter;
import org.holoeverywhere.widget.ListView;
import org.holoeverywhere.widget.RadioButton;
import org.holoeverywhere.widget.Spinner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.cma.pgrssystem.R;
import com.cma.pgrssystem.adapter.CategoryTypeAdapter;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class ComplaintCategoryFragment extends SherlockFragment {

	private View categoryView = null;
	private Spinner spinnerCategory;
	private ListView listviewForCategory;
	private CategoryTypeAdapter adapter;
	private int choice = 0;
	private String[] engArr = { "New Street Light",
			"Non Burining Street Light", "ManHole Covering" };
	private String[] healthArr = { "Dog Menace", "Mosquito Menace",
			"Stray Cattle", "Pubic Toilets", "Flies Menace" };
	private String[] generalArr = { "Voter Id Issue", "Complaints reg schools",
			"Name Error", "Unauth. Ad Boards" };
	private String[] revArr = { "Complaints reg Property Tax",
			"Complaints reg. Voter List", "Complaints related to License" };
	private String[] swArr = { "Garbage Removal", "Garbage Burning",
			"Broken bins", "Garbage Spilling Lorry" };

	private void initComplaintList(int ch) {
		
		switch (ch) {
		case 0:
			adapter = new CategoryTypeAdapter(getActivity(),
					Arrays.asList(generalArr));
			break;
		case 1:
			adapter = new CategoryTypeAdapter(getActivity(),
					Arrays.asList(healthArr));
			break;
		case 2:
			adapter = new CategoryTypeAdapter(getActivity(),
					Arrays.asList(engArr));
			break;
		case 3:
			adapter = new CategoryTypeAdapter(getActivity(),
					Arrays.asList(revArr));
		case 4:
			adapter = new CategoryTypeAdapter(getActivity(),
					Arrays.asList(swArr));
			break;
		default:
			adapter = new CategoryTypeAdapter(getActivity(),
					Arrays.asList(generalArr));
		}
		listviewForCategory.setAdapter(adapter);

	}

	private void initCatSpinner() {
		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				getActivity(), R.array.category_array,
				android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinnerCategory.setAdapter(adapter);
		spinnerCategory.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				initComplaintList(arg2);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		categoryView = inflater.inflate(R.layout.fragment_complaint_category,
				container, false);
		listviewForCategory = (ListView) categoryView
				.findViewById(R.id.list_category_types);
		listviewForCategory.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(android.widget.AdapterView<?> arg0,
					View arg1, int arg2, long arg3) {
				RadioButton radio = (RadioButton) arg1
						.findViewById(R.id.radio_select_category_row);
				radio.setChecked(true);

			}
			

		});
		spinnerCategory = (Spinner) categoryView
				.findViewById(R.id.spinner_category_type);

		initCatSpinner();
		initComplaintList(0);
		return categoryView;

	}

	public static ComplaintCategoryFragment newInstance() {
		ComplaintCategoryFragment fragment = new ComplaintCategoryFragment();
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
