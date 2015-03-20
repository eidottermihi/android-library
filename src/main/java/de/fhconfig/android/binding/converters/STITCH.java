package de.fhconfig.android.binding.converters;

import android.widget.Adapter;

import java.util.ArrayList;

import de.fhconfig.android.binding.Converter;
import de.fhconfig.android.binding.IObservable;
import de.fhconfig.android.binding.collections.CombinedAdapter;

/**
 * STITCH multiple adapters to one adapter for used in List Views. <br/>
 *
 * @usage adapter adapter ...
 * @arg adapter android.widget.Adapter
 * @return android.widget.Adapter
 */
public class STITCH extends Converter<Adapter> {
	public STITCH(IObservable<?>[] dependents) {
		super(Adapter.class, dependents);
	}

	@Override
	public Adapter calculateValue(Object... args) throws Exception {
		CombinedAdapter combine = new CombinedAdapter();
		int length = args.length;
		ArrayList<Adapter> adapters = new ArrayList<Adapter>();
		for (int i = 0; i < length; i++) {
			if (args[i] instanceof Adapter) {
				adapters.add((Adapter) args[i]);
			}
		}
		combine.addAdapter(adapters.toArray(new Adapter[0]));

		return combine;
	}
}