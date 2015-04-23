package de.fhconfig.android.binding.viewAttributes.view;

import android.view.View;

import de.fhconfig.android.binding.BindingType;
import de.fhconfig.android.binding.ViewAttribute;

/**
 * Background Color
 * Unlike "background" attribute, this only accepts Android Color coded Integer
 *
 * @author andy
 * @name backgroundColor
 * @widget View
 * @type Integer
 * @accepts Integer
 * @category simple
 * @related http://developer.android.com/reference/android/widget/View.html
 * @related http://developer.android.com/reference/android/graphics/Color.html
 * @converter ARGB
 */
public class BackgroundColorViewAttribute extends ViewAttribute<View, Integer> {

	public BackgroundColorViewAttribute(View view) {
		super(Integer.class, view, "backgroundColor");
	}

	@Override
	protected void doSetAttributeValue(Object newValue) {
		if (getView() == null) return;
		if (newValue == null) {
			getView().setBackgroundColor(0);
			return;
		}
		if (newValue instanceof Integer) {
			getView().setBackgroundColor((Integer) newValue);
		}
	}

	@Override
	protected BindingType AcceptThisTypeAs(Class<?> type) {
		return BindingType.OneWay;
	}

	@Override
	public Integer get() {
		return null;
	}
}
