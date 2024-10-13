package mg.itu.framework.utils.errors;

import java.lang.reflect.Method;

public class DoubleVerbException extends Exception{
    public DoubleVerbException(Method method){
        super("La méthode "+method.getName()+" est annotée avec 2 verbes");
    }
}