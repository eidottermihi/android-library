package de.fhconfig.android.binding.viewAttributes.compoundButton;


import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import de.fhconfig.android.binding.Binder;
import de.fhconfig.android.binding.ViewAttribute;
import de.fhconfig.android.binding.listeners.OnCheckedChangeListenerMulticast;

/**
 * Checked state of Compound Buttons including Check box
 *
 * @author andy
 * @name checked
 * @widget CompoundButton
 * @type Boolean
 * @accepts Boolean
 * @accepts Integer 0: false other: true
 * @category simple
 * @related http://developer.android.com/reference/android/widget/CompoundButton.html
 */
public class CheckedViewAttribute extends ViewAttribute<CompoundButton, Boolean>
		implements OnCheckedChangeListener {

	public CheckedViewAttribute(CompoundButton view) {
		super(Boolean.class, view, "checked");
		Binder.getMulticastListenerForView(view, OnCheckedChangeListenerMulticast.class)
				.register(this);
	}

	@Override
	protected void doSetAttributeValue(Object newValue) {
		if (getView() == null) return;
		boolean changeTo = getView().isChecked();
		if (newValue == null) {
			changeTo = false;
		}
		if (newValue instanceof Boolean) {
			changeTo = (Boolean) newValue;
		}
		if (newValue instanceof Number) {
			changeTo = !newValue.equals(0);
		}
		if (changeTo != getView().isChecked()) {
			Binder.getMulticastListenerForView(getView(), OnCheckedChangeListenerMulticast.class).nextActionIsNotFromUser();
			getView().setChecked(changeTo);
		}
	}

	@Override
	public Boolean get() {
		if (getView() == null) return null;
		return getView().isChecked();
	}

	public void onCheckedChanged(CompoundButton view, boolean checked) {
		this.notifyChanged();
	}
}