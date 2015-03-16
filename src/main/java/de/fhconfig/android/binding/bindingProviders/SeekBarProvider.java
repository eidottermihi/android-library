package de.fhconfig.android.binding.bindingProviders;

import android.view.View;
import android.widget.SeekBar;

import de.fhconfig.android.binding.ViewAttribute;
import de.fhconfig.android.binding.viewAttributes.seekBar.OnSeekBarChangeViewEvent;


public class SeekBarProvider extends BindingProvider {
	@SuppressWarnings("unchecked")
	@Override
	public <Tv extends View> ViewAttribute<Tv, ?> createAttributeForView(View view, String attributeId) {
		if (!(view instanceof SeekBar)) return null;
		if (attributeId.equals("onSeekBarChange"))
			return (ViewAttribute<Tv, ?>) new OnSeekBarChangeViewEvent((SeekBar) view);
		return null;
	}
}