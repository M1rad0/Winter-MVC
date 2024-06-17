package mg.itu.framework.utils.errors;

public class EmptyPackageException extends Exception{
    public EmptyPackageException(String packageName){
        super("Le package "+packageName+" est vide.");
    }
}
