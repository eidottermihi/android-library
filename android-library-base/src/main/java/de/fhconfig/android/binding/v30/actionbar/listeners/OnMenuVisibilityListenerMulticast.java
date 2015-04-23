package de.fhconfig.android.binding.v30.actionbar.listeners;

import android.support.v7.app.ActionBar.OnMenuVisibilityListener;
import android.view.View;

import de.fhconfig.android.binding.listeners.ViewMulticastListener;
import de.fhconfig.android.binding.v30.actionbar.BindableActionBar;

public class OnMenuVisibilityListenerMulticast
		extends ViewMulticastListener<OnMenuVisibilityListener>
		implements OnMenuVisibilityListener {

	public void onMenuVisibilityChanged(boolean isVisible) {
		for (OnMenuVisibilityListener l : listeners) {
			l.onMenuVisibilityChanged(isVisible);
		}
	}

	@Override
	public void registerToView(View v) {
		((BindableActionBar) v).getSupportActionBar().addOnMenuVisibilityListener(this);
	}
}
