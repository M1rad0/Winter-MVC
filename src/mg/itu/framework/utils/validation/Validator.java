package mg.itu.framework.utils.validation;

import java.lang.annotation.Annotation;

public abstract class Validator {
    Object obj;
    String inputName;
    Annotation annot;
    String errorMessage;

    public Validator(Object obj, String inputName, Annotation annot){
        this.obj=obj;
        this.inputName=inputName;
        this.annot=annot;
    }

    /* Boolean mais plus pratique de throws Exception directement plutôt que de return false */
    public abstract boolean validate();

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
