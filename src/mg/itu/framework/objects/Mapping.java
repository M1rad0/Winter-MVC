package mg.itu.framework.objects;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mg.itu.framework.annotation.ParamName;
import mg.itu.framework.utils.ParametersUtil;
import mg.itu.framework.utils.errors.BadReturnTypeException;
import mg.itu.framework.utils.errors.RequiredParameterException;

/*Classe pour sauvegarder le nom d'une classe et d'une méthode  */
public class Mapping {
    Method toExecute;

    public Method getToExecute() {
        return toExecute;
    }
    public void setToExecute(Method toExecute) {
        this.toExecute = toExecute;
    }

    public String getClassName() {
        return getToExecute().getDeclaringClass().getSimpleName();
    }
    public String getMethodName(){
        return getToExecute().getName();
    }

    /*Constructeur */
    public Mapping(Method toExecute) {
        this.toExecute=toExecute;
    }

    /*Récupère les arguments contenus dans la requête pour les utiliser avec la fonction */
    public Object[] buildArgs(HttpServletRequest req) throws Exception{
        Parameter[] parameters=toExecute.getParameters();
        Object[] args=new Object[parameters.length];
        int i=0;
        for(Parameter param : parameters) {
            String name=param.getName();
            ParamName annotation=param.getAnnotation(ParamName.class);

            if(annotation!=null){
                name=annotation.name();
            }
            String strValue=req.getParameter(name);
            if(strValue==null){
                throw new RequiredParameterException(name);
            }

            args[i]=ParametersUtil.castString(req.getParameter(name), param.getType());
        }
        return args;
    }

    /*Fonction appelee si quelqu'un entre l'URL */
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception{
        PrintWriter out=resp.getWriter();
        Class<?> class1= toExecute.getDeclaringClass();
        Object caller= class1.getConstructor().newInstance();

        Object[] args= buildArgs(req);

        Object result=toExecute.invoke(caller, args);

        if(result instanceof String){
            out.println(result.toString());
        }
        else if(result instanceof ModelView){
            ModelView mv = (ModelView)result;
            mv.dispatchRequest(req, resp);
        }
        else{
            throw new BadReturnTypeException();
        }   
    
    }

    public static void main(String[] args) {
        Method togetParam=Mapping.class.getDeclaredMethods()[0];

        for (Parameter param : togetParam.getParameters()) {
            System.err.println(param.getName());
        }
    }
}