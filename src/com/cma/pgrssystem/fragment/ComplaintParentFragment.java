package com.cma.pgrssystem.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.cma.pgrssystem.R;
import com.cma.pgrssystem.adapter.ComplaintsFragmentPagerAdapter;
import com.viewpagerindicator.PageIndicator;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class ComplaintParentFragment extends SherlockFragment {

	private View parentFragmentView = null;
	private PageIndicator indicator = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		parentFragmentView = inflater.inflate(
				R.layout.fragment_complaints_parent, container, false);
		return parentFragmentView;

	}

	private List<Fragment> getChildFragments() {
		List<Fragment> childFragments = new ArrayList<Fragment>();
		childFragments.add(ComplaintCategoryFragment.newInstance());
		childFragments.add(ComplaintDetailsFragment.newInstance());

		childFragments.add(ComplaintLocationFragment.newInstance());
		childFragments.add(ComplaintPersonDetailsFragment.newInstance());

		childFragments.add(ComplaintReviewFragment.newInstance());
		return childFragments;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		ViewPager mViewPager = (ViewPager) view.findViewById(R.id.pager);
		List<Fragment> childFragments = getChildFragments();

		mViewPager.setAdapter(new ComplaintsFragmentPagerAdapter(
				getChildFragmentManager(), childFragments));

		indicator = (PageIndicator) view.findViewById(R.id.indicator);
		indicator.setViewPager(mViewPager);

		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageSelected(int position) {
				indicator.setCurrentItem(position);
				switch (position) {

				case 0:
					break;
				default:
					break;
				}
			}

		});

		mViewPager.setCurrentItem(0);
		indicator.setCurrentItem(0);
	}

	public static ComplaintParentFragment newInstance() {
		ComplaintParentFragment fragment = new ComplaintParentFragment();
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
