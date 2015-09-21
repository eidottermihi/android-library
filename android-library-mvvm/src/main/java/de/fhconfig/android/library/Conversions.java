package de.fhconfig.android.library;

import android.content.res.TypedArray;
import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;

import de.fhconfig.android.library.adapters.SimpleAdapter;
import de.fhconfig.android.binding.R;
import de.fhconfig.android.library.adapters.SimplePagerAdapter;
import de.fhconfig.android.library.viewmodels.Page;

public class Conversions {
	@BindingAdapter({"bind:items", "bind:layout"})
	public static void listViewAdapter(ListView view, ObservableList list, int layout) {
		Object tag = view.getTag(R.id.adapter_id);
		if(tag == null || !(tag instanceof SimpleAdapter))
		{
			SimpleAdapter adapter = new SimpleAdapter(view, list, layout);
			view.setTag(R.id.adapter_id, adapter);
			view.setAdapter(adapter);
			tag = adapter;
		}
		SimpleAdapter adapter = (SimpleAdapter) tag;
		adapter.updateList(list);
		adapter.updateLayout(layout);
	}



	@BindingAdapter("bind:colorSchemeColors")
	public static void setRefreshColor(SwipeRefreshLayout view, @ColorRes TypedArray colorRes){
		int[] res = new int[colorRes.length()];
		for(int i = 0; i < res.length; i++){
			res[i] = colorRes.peekValue(i).data;
		}
		TypedArray typedArray = view.getContext().getTheme().obtainStyledAttributes(res);
		int[] colors = new int[typedArray.length()];
		for(int i = 0; i < res.length; i++){
			colors[i] = typedArray.getColor(i, -1);
		}
		view.setColorSchemeColors(colors);
		//view.setColorSchemeColors(colorRes);
	}

	@BindingAdapter("bind:drawableLeft")
	public static void setDrawableLeft(TextView view, @DrawableRes int id){
		view.setCompoundDrawablesWithIntrinsicBounds(id, 0, 0, 0);
	}


	@BindingAdapter({"bind:items", "bind:layout"})
	public static void viewPagerAdapter(ViewPager pager, ObservableArrayList<Page> list, int layout){
		Object tag = pager.getTag(R.id.adapter_id);
		if(tag == null || !(tag instanceof SimplePagerAdapter))
		{
			SimplePagerAdapter adapter = new SimplePagerAdapter(pager, list, layout);
			pager.setTag(R.id.adapter_id, adapter);
			pager.setAdapter(adapter);
			tag = adapter;
		}
		SimplePagerAdapter adapter = (SimplePagerAdapter)tag;
		adapter.updateList(list);
		adapter.updateLayout(layout);
	}

	@BindingConversion
	public static String convertDateToString(Date date){
		return DateFormat.getDateTimeInstance().format(date);
	}
}
