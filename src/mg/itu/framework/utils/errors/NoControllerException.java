package mg.itu.framework.utils.errors;

public class NoControllerException extends Exception{
    public NoControllerException(String packageName){
        super("Le package "+ packageName + " ne contient aucun controller.");
    }
}
