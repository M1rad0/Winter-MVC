package mg.itu.framework.utils.errors;

public class VerbNotSupportedException extends Exception{
    public VerbNotSupportedException(String name){
        super("Aucune association "+name+" pour cet url");
    }
}