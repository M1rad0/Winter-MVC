package mg.itu.framework.utils.errors;

public class BadReturnTypeException extends Exception{
    public BadReturnTypeException(){
        super("Le type de retour de la methode associée n'est ni un String ni un ModelView.");
    }
}
