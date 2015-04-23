package de.fhconfig.android.binding.viewAttributes.adapterView;

import android.view.View;
import android.widget.ExpandableListView;

import de.fhconfig.android.binding.Binder;
import de.fhconfig.android.binding.listeners.OnChildClickListenerMulticast;
import de.fhconfig.android.binding.viewAttributes.ViewEventAttribute;

/**
 * Fires when Child Item Clicked
 *
 * @author andy
 * @name onChildClick
 * @widget ExpandableListView
 * @type Command
 * @accepts Command
 * @category expandable-list
 * @related
 */
public class ExpandableListView_OnChildClickViewEvent extends ViewEventAttribute<ExpandableListView>
		implements ExpandableListView.OnChildClickListener {

	public ExpandableListView_OnChildClickViewEvent(ExpandableListView view) {
		super(view, "onChildClick");
	}

	@Override
	protected void registerToListener(ExpandableListView view) {
		Binder.getMulticastListenerForView(view, OnChildClickListenerMulticast.class).register(this);
	}

	public boolean onChildClick(ExpandableListView parent, View v,
	                            int groupPosition, int childPosition, long id) {
		this.invokeCommand(parent, v, groupPosition, childPosition, id);
		return true;
	}
}
