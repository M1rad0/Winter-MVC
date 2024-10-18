package mg.itu.framework.utils.errors;

public class NotFoundException extends Exception{
    public NotFoundException(){
        super("URL introuvable");
    }
}
