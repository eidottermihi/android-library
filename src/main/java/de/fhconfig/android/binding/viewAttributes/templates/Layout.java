package de.fhconfig.android.binding.viewAttributes.templates;

import de.fhconfig.android.binding.Binder.InflateResult;

public abstract class Layout {
	private int mDefaultId = -1;

	public Layout(int defaultId) {
		setDefaultLayoutId(defaultId);
	}

	public int getDefaultLayoutId() {
		return mDefaultId;
	}

	public void setDefaultLayoutId(int id) {
		mDefaultId = id;
	}

	public abstract int getLayoutTypeId(int pos);

	public abstract int getLayoutId(int pos);

	public abstract int getTemplateCount();

	/**
	 * Provide a hook for injecting additional binding attributes to the view
	 *
	 * @param root
	 */
	public void onAfterInflate(InflateResult result, int pos) {
	}
}