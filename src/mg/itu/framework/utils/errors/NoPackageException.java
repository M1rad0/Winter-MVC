package mg.itu.framework.utils.errors;

public class NoPackageException extends Exception{
    public NoPackageException(){
        super("Le package spécifié est introuvable.");
    }
}
