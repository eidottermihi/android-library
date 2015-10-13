package de.fhconfig.android.library.ui.injection;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import de.fhconfig.android.library.injection.Injector;
import de.fhconfig.android.library.injection.InjectorProvider;
import de.fhconfig.android.library.injection.annotation.Inject;
import de.fhconfig.android.library.injection.annotation.XmlMenu;
import de.fhconfig.android.library.injection.exceptions.ViewIdNotFoundException;
import de.fhconfig.android.library.injection.xml.FragmentInjector;
import de.fhconfig.android.library.injection.xml.IViewFinder;
import de.fhconfig.android.library.util.Optional;

/**
 * Created by larsgrefer on 24.11.14.
 */
public class InjectionFragment extends Fragment implements InjectorProvider{

	@Inject
	Optional<XmlMenu> menuAnnotation;

	FragmentInjector injector;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Injector parentInjector = null;
		if(getActivity() instanceof InjectorProvider) {
			parentInjector = ((InjectorProvider) getActivity()).getInjector();
		}
		if(injector == null && getActivity().getApplication() instanceof InjectorProvider) {
			parentInjector = ((InjectorProvider) getActivity().getApplication()).getInjector();
		}
		injector = new FragmentInjector(this, parentInjector);
		injector.inject(this);
		if(menuAnnotation.isPresent()){
			setHasOptionsMenu(true);
		}

		injector.injectResources();
		injector.injectAttributes();


	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if (injector.getLayoutId() != 0) {
			final View view = inflater.inflate(injector.getLayoutId(), container, false);
			return view;
		}
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		injector.setViewFinder(new IViewFinder() {
			@Override
			public View findViewById(@IdRes int viewId) {
				return view.findViewById(viewId);
			}
		});
		try {
			injector.injectViews();
		} catch (ViewIdNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		if (menuAnnotation.isPresent()) {
			inflater.inflate(menuAnnotation.get().value(), menu);
		}
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		injector.injectResources();
		injector.injectAttributes();
	}

	@Override
	public Injector getInjector() {
		return null;
	}
}
