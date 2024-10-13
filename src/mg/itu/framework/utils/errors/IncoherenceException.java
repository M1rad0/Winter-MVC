package mg.itu.framework.utils.errors;

public class IncoherenceException extends Exception{
    public IncoherenceException(String verb){
        super("Vous avez annoté 2 méthodes en "+verb+" pour la même URL");
    }
}