package de.fhconfig.android.binding.app;

import android.app.TabActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import de.fhconfig.android.binding.Binder;
import de.fhconfig.android.binding.Binder.InflateResult;
import de.fhconfig.android.binding.menu.OptionsMenuBinder;

public class BindingTabActivity extends TabActivity {

	OptionsMenuBinder menuBinder;
	Object mMenuViewModel;
	private View mRootView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	protected View setAndBindRootView(int layoutId, Object... contentViewModel) {
		if (mRootView != null) {
			throw new IllegalStateException("Root view is already created");
		}
		InflateResult result = Binder.inflateView(this, layoutId, null, false);
		mRootView = result.rootView;
		setContentView(mRootView);
		for (int i = 0; i < contentViewModel.length; i++) {
			Binder.bindView(this, result, contentViewModel[i]);
		}
		return mRootView;
	}

	protected void setAndBindOptionsMenu(int menuId, Object menuViewModel) {
		if (menuBinder != null) {
			throw new IllegalStateException("Options menu can only set once");
		}
		menuBinder = new OptionsMenuBinder(menuId);
		mMenuViewModel = menuViewModel;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// No menu is defined
		if (menuBinder == null)
			return false;
		return menuBinder.onCreateOptionsMenu(this, menu, mMenuViewModel);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		if (menuBinder == null)
			return false;
		return menuBinder.onPrepareOptionsMenu(this, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (menuBinder != null)
			return menuBinder.onOptionsItemSelected(this, item);
		return super.onOptionsItemSelected(item);
	}

	public View getRootView() {
		return mRootView;
	}

	public void setRootView(View rootView) {
		mRootView = rootView;
	}

}
