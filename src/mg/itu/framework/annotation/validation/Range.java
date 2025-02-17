package mg.itu.framework.annotation.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import mg.itu.framework.utils.validation.RangeValidator;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Validation(linkedValidator = RangeValidator.class)
public @interface Range {
    int minValue();
    int maxValue();
}
