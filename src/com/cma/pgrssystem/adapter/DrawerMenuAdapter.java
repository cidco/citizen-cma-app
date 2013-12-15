package com.cma.pgrssystem.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cma.pgrssystem.R;
import com.cma.pgrssystem.bean.DrawerMenuBean;

public class DrawerMenuAdapter extends ArrayAdapter<DrawerMenuBean> {

	private LayoutInflater layoutInflater;

	public DrawerMenuAdapter(Activity activity, List<DrawerMenuBean> objects) {
		super(activity, 0, objects);
		layoutInflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	static class DrawerMenuViewHolder {

		int id;
		TextView menuLabel;

	}

	public View getView(final int position, View slidingMenuCell, ViewGroup arg2) {
		DrawerMenuViewHolder slidingMenuViewHolder;
		final DrawerMenuBean menuBean = getItem(position);

		if (slidingMenuCell == null) {
			slidingMenuCell = layoutInflater.inflate(R.layout.slidingmenu_cell,
					null);

			slidingMenuViewHolder = new DrawerMenuViewHolder();
			slidingMenuViewHolder.menuLabel = (TextView) slidingMenuCell
					.findViewById(R.id.text_slidingmenu_cell_label);

			slidingMenuCell.setTag(slidingMenuViewHolder);
		} else {
			slidingMenuViewHolder = (DrawerMenuViewHolder) slidingMenuCell
					.getTag();
		}

//		slidingMenuCell.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
//				if (menuBean.getId() == SocialTangoConstants.SLIDING_MENU_ID_LOGOUT) {
//					
//					System.out.println("Logout Clicked");
//
//				} else {
//					menuClickListener.handleSlidingMenuClick(menuBean);
//				}
//
//			}
//		});

		slidingMenuViewHolder.id = position;
		slidingMenuViewHolder.menuLabel.setText(menuBean.getLabel());

		return slidingMenuCell;
	}

}
