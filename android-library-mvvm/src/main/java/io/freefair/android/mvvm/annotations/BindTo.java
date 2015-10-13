package io.freefair.android.mvvm.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface BindTo {
	/**
	 * This defines the BR class of your application.
	 * @return BR class of application
	 */
	Class<?> value() default BindTo.class;
}
