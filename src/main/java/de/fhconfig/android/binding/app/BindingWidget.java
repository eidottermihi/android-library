package de.fhconfig.android.binding.app;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import de.fhconfig.android.binding.AttributeBinder;
import de.fhconfig.android.binding.Binder;
import de.fhconfig.android.binding.Binder.InflateResult;

public class BindingWidget {

	public static Dialog createAndBindDialog(Context context, int layoutId, Object contentViewModel) {
		Dialog dialog = new Dialog(context);
		InflateResult result = Binder.inflateView(context, layoutId, null, false);
		dialog.setContentView(result.rootView);
		for (View v : result.processedViews) {
			AttributeBinder.getInstance().bindView(context, v, contentViewModel);
		}
		return dialog;
	}

}
