package de.larsgrefer.android.library.injection;

/**
 * Created by larsgrefer on 23.11.14.
 */
public class ViewIdNotFoundException extends Exception {

	public ViewIdNotFoundException() {
	}

	private static String formatFieldName(String fieldName) {
		return String.format("The ViewId for the field '%s' could not be found.", fieldName);
	}

	public ViewIdNotFoundException(String fieldName) {
		super(formatFieldName(fieldName));
	}

	public ViewIdNotFoundException(String fieldName, Throwable throwable) {
		super(formatFieldName(fieldName), throwable);
	}

	public ViewIdNotFoundException(Throwable throwable) {
		super(throwable);
	}
}
