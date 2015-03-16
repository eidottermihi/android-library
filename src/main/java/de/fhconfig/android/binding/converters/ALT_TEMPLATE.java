package de.fhconfig.android.binding.converters;

import de.fhconfig.android.binding.Binder.InflateResult;
import de.fhconfig.android.binding.Converter;
import de.fhconfig.android.binding.IObservable;
import de.fhconfig.android.binding.viewAttributes.templates.Layout;

/**
 * Alternate Layout Template.  <br/>
 * You can supply more than one templates to it, and it will output them alternatively, according to position <br/>
 * For example, if you provide 3 templates to it, binding:itemTemplate="ALT(A,B,C)" then <br/>
 * the 1,4,7... item in ListView will have A template, and 2,5,8 will have B etc.
 *
 * @author andy
 * @usage layout layout ...
 * @arg layout de.fhconfig.android.binding.viewAttributes.templates.Layout Note layout can be supplied in @package:layout/id format
 * @return de.fhconfig.android.binding.viewAttributes.templates.Layout
 */
public class ALT_TEMPLATE extends Converter<Layout> {
	public ALT_TEMPLATE(IObservable<?>[] dependents) {
		super(Layout.class, dependents);
	}

	@Override
	public Layout calculateValue(Object... args) throws Exception {
		Layout[] ids = new Layout[args.length];
		for (int i = 0; i < args.length; i++) {
			ids[i] = ((Layout) args[i]);
		}
		return new Alt_Layout(ids);
	}

	private static class Alt_Layout extends Layout {
		private Layout[] mLayouts;

		public Alt_Layout(Layout[] layouts) {
			super(layouts[0].getDefaultLayoutId());
			mLayouts = layouts;
		}

		@Override
		public void onAfterInflate(InflateResult result, int pos) {
			int idx = pos % mLayouts.length;
			mLayouts[idx].onAfterInflate(result, pos);
		}

		@Override
		public int getLayoutTypeId(int pos) {
			return pos % mLayouts.length;
		}

		@Override
		public int getLayoutId(int pos) {
			int idx = pos % mLayouts.length;
			return mLayouts[idx].getDefaultLayoutId();
		}

		@Override
		public int getTemplateCount() {
			return mLayouts.length;
		}
	}
}
