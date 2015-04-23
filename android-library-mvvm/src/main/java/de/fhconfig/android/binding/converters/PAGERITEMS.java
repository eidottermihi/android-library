package de.fhconfig.android.binding.converters;

import android.view.View;
import android.view.ViewGroup;

import de.fhconfig.android.binding.Binder;
import de.fhconfig.android.binding.Binder.InflateResult;
import de.fhconfig.android.binding.BindingLog;
import de.fhconfig.android.binding.Converter;
import de.fhconfig.android.binding.DynamicObject;
import de.fhconfig.android.binding.IObservable;
import de.fhconfig.android.binding.v30.viewAttributes.adapterView.viewPager.PagerAdapterObservable;
import de.fhconfig.android.binding.viewAttributes.templates.Layout;

/**
 * PAGERADAPTER accepts unlimited number of DynamicObjects
 * It will return a PagerAdapter
 *
 * @author andy
 * @usage params
 * @arg params DynamicObject
 * @item template de.fhconfig.android.binding.viewAttributes.templates.Layout
 * @item item Object Item View Model
 * @item @optional width float
 * @item @optional title String
 * @return android.widget.Adapter
 */
public class PAGERITEMS extends Converter<PagerAdapterObservable> {
	public PAGERITEMS(IObservable<?>[] dependents) {
		super(PagerAdapterObservable.class, dependents);
	}

	@Override
	public PagerAdapterObservable calculateValue(Object... args) throws Exception {
		DynamicObject[] dobjs = new DynamicObject[args.length];
		System.arraycopy(args, 0, dobjs, 0, args.length);

		return new ObsPagerAdapter(dobjs);
	}

	private static class ObsPagerAdapter extends PagerAdapterObservable {
		private final DynamicObject[] items;

		public ObsPagerAdapter(DynamicObject... objs) {
			items = objs;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return items[position].tryGetObservableValue("title", "");
		}

		@Override
		public float getPageWidth(int position) {
			return items[position].tryGetObservableValue("width", 1.0f);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO: cache the view, handle the destroy also
			try {
				Layout layout;
				layout = (Layout) items[position].getObservableByName("template").get();
				InflateResult result =
						Binder.inflateView(container.getContext(), layout.getDefaultLayoutId(), container, false);
				View root =
						Binder.bindView(container.getContext(), result, items[position].getObservableByName("item").get());
				container.addView(root);
				return root;
			} catch (Exception e) {
				BindingLog.exception("PAGERADAPTER", e);
				return null;
			}
		}

		@Override
		public void notifyDataSetChanged() {
			// TODO Auto-generated method stub
			super.notifyDataSetChanged();
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public int getCount() {
			return items.length;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0.equals(arg1);
		}
	}
}
