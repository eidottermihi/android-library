package de.fhconfig.android.binding.kernel;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.fhconfig.android.binding.AttributeBinder;
import de.fhconfig.android.binding.AttributeCollection;
import de.fhconfig.android.binding.Binder.InflateResult;
import de.fhconfig.android.binding.BindingLog;
import de.fhconfig.android.binding.BindingMap;
import de.fhconfig.android.binding.IBindableView;
import de.fhconfig.android.binding.IKernel;
import de.fhconfig.android.binding.ISyntaxResolver;
import de.fhconfig.android.binding.ViewAttribute;
import de.fhconfig.android.binding.ViewFactory;
import de.fhconfig.android.binding.ViewTag;
import de.fhconfig.android.binding.exception.AttributeNotDefinedException;
import de.fhconfig.android.binding.listeners.MulticastListenerCollection;
import de.fhconfig.android.binding.listeners.ViewMulticastListener;

public abstract class KernelBase implements IKernel {

	protected AttributeBinder attributeBinder;
	protected ISyntaxResolver syntaxResolver;

	public KernelBase() {
		super();
	}

	@Override
	public ViewAttribute<?, ?> getAttributeForView(View view, String attributeId)
			throws AttributeNotDefinedException {
		ViewAttribute<?, ?> viewAttribute = null;

		AttributeCollection collection = getAttributeCollectionOfView(view);
		if (collection.containsAttribute(attributeId))
			return collection.getAttribute(attributeId);

		//  Check if it is custom view, if so, try to look for the attribute in custom view first
		if (view instanceof IBindableView) {
			viewAttribute = ((IBindableView<?>) view).createViewAttribute(attributeId);
		}

		if (viewAttribute == null)
			viewAttribute = AttributeBinder.getInstance().createAttributeForView(view, attributeId);

		if (viewAttribute == null)
			throw new AttributeNotDefinedException
					(String.format("The view of type '%s' does not have attribute '%s' defined. ",
							view.getClass().getSimpleName(), attributeId));

		collection.putAttribute(attributeId, viewAttribute);
		return viewAttribute;
	}

	@Override
	public ViewTag getViewTag(View view) {
		Object tag = view.getTag();
		if (tag instanceof ViewTag) {
			return (ViewTag) tag;
		}
		ViewTag vtag = new ViewTag();
		view.setTag(vtag);
		return vtag;
	}

	@Override
	public AttributeCollection getAttributeCollectionOfView(View view) {
		ViewTag vt = getViewTag(view);
		AttributeCollection collection = vt.get(AttributeCollection.class);
		if (collection != null)
			return collection;
		collection = new AttributeCollection();
		vt.put(AttributeCollection.class, collection);
		return collection;
	}

	@Override
	public void putBindingMapToView(View view, BindingMap map) {
		getViewTag(view).put(BindingMap.class, map);
	}

	@Override
	public BindingMap getBindingMapForView(View view) {
		if (!getViewTag(view).containsKey(BindingMap.class)) {
			putBindingMapToView(view, new BindingMap());
		}
		return getViewTag(view).get(BindingMap.class);
	}

	@Override
	public InflateResult inflateView(Context context, int layoutId, ViewGroup parent,
	                                 boolean attachToRoot) {
		LayoutInflater inflater = LayoutInflater.from(context).cloneInContext(context);
		ViewFactory factory = new ViewFactory(inflater);
		inflater.setFactory2(factory);
		InflateResult result = new InflateResult();
		result.rootView = inflater.inflate(layoutId, parent, attachToRoot);
		result.processedViews = factory.getProcessedViews();

		return result;
	}

	@Override
	public View bindView(Context context, InflateResult inflatedView, Object model) {
		for (View v : inflatedView.processedViews) {
			AttributeBinder.getInstance().bindView(context, v, model);
		}
		return inflatedView.rootView;
	}

	@Override
	public <T extends ViewMulticastListener<?>> T getMulticastListenerForView(
			View view, Class<T> listenerType) {
		MulticastListenerCollection collection = getViewTag(view).get(MulticastListenerCollection.class);
		if (collection == null) {
			collection = new MulticastListenerCollection();
			getViewTag(view).put(MulticastListenerCollection.class, collection);
		}

		if (collection.containsKey(listenerType)) {
			return collection.get(listenerType);
		}
		try {
			T listener = listenerType.getConstructor().newInstance();
			listener.registerToView(view);
			collection.put(listenerType, listener);
			return listener;
		} catch (Exception e) {
			BindingLog.exception("BinderV30", e);
			return null;
		}
	}

	@Override
	public void init(Application application) {
		syntaxResolver = createSyntaxResolver(application);
		attributeBinder = createAttributeBinder(application);
		registerProviders(attributeBinder);
	}

	protected abstract AttributeBinder createAttributeBinder(Application application);

	protected abstract void registerProviders(AttributeBinder attrBinder);

	protected abstract ISyntaxResolver createSyntaxResolver(Application application);

	@Override
	public AttributeBinder getAttributeBinder() {
		return attributeBinder;
	}

	@Override
	public ISyntaxResolver getSyntaxResolver() {
		return syntaxResolver;
	}
}