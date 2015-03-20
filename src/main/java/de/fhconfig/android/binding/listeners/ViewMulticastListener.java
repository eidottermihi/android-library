package de.fhconfig.android.binding.listeners;

import android.view.View;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;

import de.fhconfig.android.binding.MulticastListener;


public abstract class ViewMulticastListener<T> extends MulticastListener<View, T> {
	protected ArrayList<T> listeners = new ArrayList<T>(0);

	public abstract void registerToView(View v);

	@Override
	public void registerToHost(View host) {
		registerToView(host);
	}

	public void removeListener(T listener) {
		listeners.remove(listener);
	}

	public void register(T listener) {
		listeners.add(listener);
	}

	public void registerWithHighPriority(T listener) {
		listeners.add(0, listener);
	}

	public static class Factory {
		private static HashMap<Class<?>, Constructor<?>>
				constructors = new HashMap<Class<?>, Constructor<?>>();

		public static void RegisterConstructorE(Class<?> type, Constructor<?> constructor) {
			constructors.put(type, constructor);
		}

		public static void RegisterConstructor(Class<?> type, Class<?> listener) {
			try {
				RegisterConstructorE(type, listener.getConstructor());
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@SuppressWarnings("unchecked")
		public static <T> ViewMulticastListener<T> create(T listenerType, View v) {
			if (constructors.containsKey(listenerType)) {
				try {
					ViewMulticastListener<T> listener = (ViewMulticastListener<T>) constructors.get(listenerType).newInstance();
					listener.registerToView(v);
					return listener;
				} catch (Exception e) {
					return null;
				}
			}
			return null;
		}
	}
}
