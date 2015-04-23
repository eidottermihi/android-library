package de.fhconfig.android.binding.viewAttributes.adapterView;

import android.widget.Adapter;
import android.widget.AdapterView;

import java.util.Collection;

import de.fhconfig.android.binding.Binder;
import de.fhconfig.android.binding.BindingType;
import de.fhconfig.android.binding.IObservable;
import de.fhconfig.android.binding.Observer;
import de.fhconfig.android.binding.ViewAttribute;
import de.fhconfig.android.binding.exception.AttributeNotDefinedException;
import de.fhconfig.android.binding.viewAttributes.templates.Layout;

/**
 * Item Source of Adapter View
 * Supplying the IObservableCollection to this and Item Template together, adapter will be created automatically
 * Due to the complexity of this API, we recommend to switch to binding:adapter instead of this
 * e.g.  binding:adapter="ADAPTER({source=..., template=...})
 * which will give more fine-grain control on adapter generated
 *
 * @author andy
 * @name itemSource
 * @widget AdapterView
 * @type Object
 * @accepts Object
 * @category list
 * @related
 */
public class ItemSourceViewAttribute extends ViewAttribute<AdapterView<Adapter>, Object> {

	Layout template, spinnerTemplate;
	ViewAttribute<?, Layout> itemTemplateAttr, spinnerTemplateAttr;
	Object mValue;

	private Observer templateObserver = new Observer() {
		public void onPropertyChanged(IObservable<?> prop,
		                              Collection<Object> initiators) {
			template = itemTemplateAttr.get();
			spinnerTemplate = spinnerTemplateAttr.get();
			doSetAttributeValue(mValue);
		}
	};

	@SuppressWarnings("unchecked")
	public ItemSourceViewAttribute(AdapterView<Adapter> view, String attributeName) {
		super(Object.class, view, attributeName);

		try {
			itemTemplateAttr = (ViewAttribute<?, Layout>) Binder.getAttributeForView(getView(), "itemTemplate");
			itemTemplateAttr.subscribe(templateObserver);
			spinnerTemplateAttr = (ViewAttribute<?, Layout>) Binder.getAttributeForView(getView(), "spinnerTemplate");
			spinnerTemplateAttr.subscribe(templateObserver);
			template = itemTemplateAttr.get();
			spinnerTemplate = spinnerTemplateAttr.get();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void doSetAttributeValue(Object newValue) {
		if (getView() == null) return;
		mValue = newValue;
		if (newValue == null)
			return;

		if (newValue instanceof Adapter) {
			try {
				((ViewAttribute<?, Adapter>) Binder.getAttributeForView(getView(), "adapter")).set((Adapter) newValue);
			} catch (AttributeNotDefinedException e) {
				e.printStackTrace();
			}
			return;
		}

		if (template == null) return;

		spinnerTemplate = spinnerTemplate == null ? template : spinnerTemplate;

		try {
			Adapter adapter = de.fhconfig.android.binding.collections.Utility.getSimpleAdapter
					(getView().getContext(), newValue, spinnerTemplate, template, null);
			((ViewAttribute<?, Adapter>) Binder.getAttributeForView(getView(), "adapter")).set(adapter);
			ViewAttribute<?, Object> SelectedObject = (ViewAttribute<?, Object>) Binder.getAttributeForView(getView(), "selectedObject");
			if (SelectedObject != null) {
				Object selObject = SelectedObject.get();
				int c = getView().getAdapter().getCount();
				int pos = -1;
				for (int i = 0; i < c; i++) {
					Object o = getView().getAdapter().getItem(i);
					if (o == null) {
						if (selObject == null) {
							pos = i;
							break;
						}
						continue;
					}
					if (o.equals(selObject)) {
						pos = i;
						break;
					}
				}
				getView().setSelection(pos);
			} else {
				ViewAttribute<?, Integer> SelectedPosition = (ViewAttribute<?, Integer>) Binder.getAttributeForView(getView(), "selectedPosition");
				getView().setSelection(SelectedPosition.get());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Object get() {
		return mValue;
	}

	@Override
	protected BindingType AcceptThisTypeAs(Class<?> type) {
		return BindingType.OneWay;
	}
}
