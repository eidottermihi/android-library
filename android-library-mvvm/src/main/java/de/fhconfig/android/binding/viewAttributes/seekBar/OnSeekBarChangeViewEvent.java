package de.fhconfig.android.binding.viewAttributes.seekBar;

import android.widget.SeekBar;

import de.fhconfig.android.binding.Binder;
import de.fhconfig.android.binding.listeners.OnSeekBarChangeListenerMulticast;
import de.fhconfig.android.binding.viewAttributes.ViewEventAttribute;

/**
 * On Seek Bar Changed View Event. Fires when Seek Bar's changed
 *
 * @author andy
 * @name onSeekBarChange
 * @widget SeekBar
 * @type Command
 * @accepts Command
 * @category simple
 * @related http://developer.android.com/reference/android/widget/SeekBar.html
 * @related http://developer.android.com/reference/android/widget/SeekBar.OnSeekBarChangeListener.html
 */
public class OnSeekBarChangeViewEvent extends ViewEventAttribute<SeekBar> implements SeekBar.OnSeekBarChangeListener {

	public OnSeekBarChangeViewEvent(SeekBar view) {
		super(view, "onSeekBarChange");
	}

	public void onProgressChanged(SeekBar seekBar, int progress,
	                              boolean fromUser) {
		if (fromUser) {
			this.invokeCommand(seekBar, progress, fromUser);
		}
	}

	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	public void onStopTrackingTouch(SeekBar seekBar) {
	}

	@Override
	protected void registerToListener(SeekBar view) {
		Binder.getMulticastListenerForView(view, OnSeekBarChangeListenerMulticast.class).register(this);
	}

}
