package mg.itu.framework.objects;

/*Classe pour sauvegarder le nom d'une classe et d'une méthode  */
public class Mapping {
    String className;
    String methodName;

    /*Getters et setters */
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }
    public String getMethodName() {
        return methodName;
    }
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    /*Constructeur */
    public Mapping(String className, String methodName) {
        this.className = className;
        this.methodName = methodName;
    }    
}
