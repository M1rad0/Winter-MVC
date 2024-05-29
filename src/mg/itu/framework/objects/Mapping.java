package mg.itu.framework.objects;

import mg.itu.framework.utils.Reflect;

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

    public Object execute() throws Exception{
        Class<?> class1= Class.forName(className);
        Object caller= class1.getConstructor().newInstance();
        
        return Reflect.execMeth(caller, methodName, null, null);
    }
}
