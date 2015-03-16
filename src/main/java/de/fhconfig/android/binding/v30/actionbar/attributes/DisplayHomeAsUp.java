package de.fhconfig.android.binding.v30.actionbar.attributes;

import android.support.v7.app.ActionBar;

import de.fhconfig.android.binding.ViewAttribute;
import de.fhconfig.android.binding.v30.actionbar.BindableActionBar;

public class DisplayHomeAsUp extends ViewAttribute<BindableActionBar, Boolean> {

	public DisplayHomeAsUp(BindableActionBar view) {
		super(Boolean.class, view, "displayHomeAsUp");
	}

	@Override
	protected void doSetAttributeValue(Object newValue) {
		getHost().getSupportActionBar().setDisplayHomeAsUpEnabled(newValue.equals(Boolean.TRUE));
	}

	@Override
	public Boolean get() {
		return (getHost().getSupportActionBar().getDisplayOptions() & ActionBar.DISPLAY_HOME_AS_UP)
				== ActionBar.DISPLAY_HOME_AS_UP;
	}
}
