package mg.itu.framework.utils.errors;

public class RequiredParameterException extends Exception{
    public RequiredParameterException(String name){
        super("Le paramètre "+name+" doit être envoyé depuis la view");
    }
}