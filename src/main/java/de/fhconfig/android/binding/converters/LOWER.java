package de.fhconfig.android.binding.converters;

import java.util.Locale;

import de.fhconfig.android.binding.Converter;
import de.fhconfig.android.binding.IObservable;

public class LOWER extends Converter<Object> {
	public static Locale locale = null;

	public LOWER(IObservable<?>[] dependents) {
		super(Object.class, dependents);
	}

	@Override
	public Object calculateValue(Object... args) throws Exception {
		if (args.length < 1 || args[0] == null) return null;
		if (locale != null)
			return String.valueOf(args[0]).toLowerCase(locale);
		return String.valueOf(args[0]).toLowerCase(Locale.getDefault());
	}
}
