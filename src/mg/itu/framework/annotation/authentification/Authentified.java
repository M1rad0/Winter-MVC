package mg.itu.framework.annotation.authentification;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*Si il y a cette annotation, verification obligatoire pour voir si il y a une variable auth dans la session */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
public @interface Authentified {
    Class<?>[] authorized();
}
