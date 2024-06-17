package mg.itu.framework.utils.errors;

import mg.itu.framework.objects.Mapping;

public class SameURLException extends Exception{
    public SameURLException(Mapping a, Mapping b){
        super(
            String.format("La methode %s du controller %s et la methode %s du controller %s ont le meme URL.",
                a.getMethodName(), a.getClassName(), b.getMethodName(), b.getClassName()
            )
        );
    }
}
