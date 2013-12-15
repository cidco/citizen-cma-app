package com.cma.pgrssystem.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ComplaintsFragmentPagerAdapter extends FragmentPagerAdapter {

	private List<Fragment> mFragments;

	public ComplaintsFragmentPagerAdapter(FragmentManager fm,
			List<Fragment> complaintChildFragments) {
		super(fm);
		this.mFragments = complaintChildFragments;
	}

	@Override
	public int getCount() {
		return mFragments.size();
	}

	@Override
	public Fragment getItem(int position) {
		return mFragments.get(position);
	}

}
