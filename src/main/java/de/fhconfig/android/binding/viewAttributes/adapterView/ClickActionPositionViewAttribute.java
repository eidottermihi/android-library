package de.fhconfig.android.binding.viewAttributes.adapterView;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import de.fhconfig.android.binding.Binder;
import de.fhconfig.android.binding.ViewAttribute;
import de.fhconfig.android.binding.listeners.OnItemClickListenerMulticast;
import de.fhconfig.android.binding.listeners.OnItemLongClickListenerMulticast;

/**
 * User: =ra=
 * Date: Dec 14, 2011
 * Time: 1:37:15 PM
 */
public class ClickActionPositionViewAttribute extends ViewAttribute<AdapterView<?>, Integer> implements OnItemClickListener,
		AdapterView.OnItemLongClickListener {

	private int mValue;

	public ClickActionPositionViewAttribute(AdapterView<?> view) {
		super(Integer.class, view, "clickActionPosition");
		Binder.getMulticastListenerForView(view, OnItemClickListenerMulticast.class).registerWithHighPriority(this);
		Binder.getMulticastListenerForView(view, OnItemLongClickListenerMulticast.class).registerWithHighPriority(this);
	}

	@Override
	public Integer get() {
		return mValue;
	}

	@Override
	protected void doSetAttributeValue(Object newValue) {
		if (!(newValue instanceof Integer))
			return;
		mValue = (Integer) newValue;
	}

	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		handleEvent(parent, position);
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		handleEvent(parent, position);
		return false;
	}

	private void handleEvent(AdapterView<?> parent, int newValue) {
		if (!getView().equals(parent))
			return;
		mValue = newValue;
		this.notifyChanged();
	}
}
