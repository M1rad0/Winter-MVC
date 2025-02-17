package mg.itu.framework.utils.validation;

import java.lang.annotation.Annotation;

public abstract class Validator {
    Object obj;
    Annotation annot;

    public Validator(Object obj, Annotation annot){
        this.obj=obj;
        this.annot=annot;
    }

    /* Boolean mais plus pratique de throws Exception directement plutôt que de return false */
    public abstract boolean validate();
}
