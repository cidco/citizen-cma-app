package com.cma.pgrssystem.adapter;

import java.util.List;

import org.holoeverywhere.widget.ArrayAdapter;
import org.holoeverywhere.widget.RadioButton;
import org.holoeverywhere.widget.TextView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cma.pgrssystem.R;

public class CategoryTypeAdapter extends ArrayAdapter<String> {

	private final LayoutInflater layoutInflater;
	Context context = null;

	public CategoryTypeAdapter(Context context, List<String> categoryTypes) {
		super(context, 0, categoryTypes);

		this.context = context;
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	private static class CategoryViewHolder {
		TextView categoryType;
		RadioButton categoryRadio;
	}

	@Override
	public String getItem(int position) {
		return super.getItem(position);
	}

	@Override
	public View getView(final int position, View categoryRow, ViewGroup arg2) {
		String category = getItem(position);
		final CategoryViewHolder categoryViewHolder;

		if (categoryRow == null) {
			categoryRow = layoutInflater.inflate(R.layout.category_row, null);

			categoryViewHolder = new CategoryViewHolder();

			// Category Name
			categoryViewHolder.categoryType = (TextView) categoryRow
					.findViewById(R.id.text_category_type_row);

			// Radio Button
			categoryViewHolder.categoryRadio = (RadioButton) categoryRow
					.findViewById(R.id.radio_select_category_row);

			categoryRow.setTag(categoryViewHolder);
		} else {
			categoryViewHolder = (CategoryViewHolder) categoryRow.getTag();
		}

		// Category Name
		categoryViewHolder.categoryType.setText(category);

		return categoryRow;
	}

}
