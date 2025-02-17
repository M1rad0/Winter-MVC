package mg.itu.framework.annotation.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Validation(linkedValidator = "mg.itu.framework.utils.validation.RangeValidator")
public @interface Range {
    int minValue();
    int maxValue();
}
