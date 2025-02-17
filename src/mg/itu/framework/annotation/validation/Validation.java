package mg.itu.framework.annotation.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*Annotation à poser sur toutes les annotations de validation */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Validation {
    /*Nom de la classe qui contient la fonction de validation */
    Class<?> linkedValidator();
}
