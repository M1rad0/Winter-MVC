package mg.itu.framework.objects;

import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import mg.itu.framework.annotation.ParamEquivalent;
import mg.itu.framework.annotation.ParamName;
import mg.itu.framework.annotation.ParamWrapper;
import mg.itu.framework.annotation.RestApi;
import mg.itu.framework.annotation.validation.Validation;
import mg.itu.framework.utils.ParametersUtil;
import mg.itu.framework.utils.Reflect;
import mg.itu.framework.utils.errors.BadReturnTypeException;
import mg.itu.framework.utils.errors.RequiredParameterException;
import mg.itu.framework.utils.errors.ValidationException;
import mg.itu.framework.utils.errors.VerbNotSupportedException;
import mg.itu.framework.utils.validation.Validator;

/*Classe pour sauvegarder le nom d'une classe et d'une méthode  */
public class Mapping {
    Set<VerbMethod> verbmethods;

    public void setVerbmethods(Set<VerbMethod> verbmethods) {
        this.verbmethods = verbmethods;
    }

    public Set<VerbMethod> getVerbmethods() {
        return verbmethods;
    }
    
    public Method getMethod(String verb) throws VerbNotSupportedException{
        for (VerbMethod verbMethod : verbmethods) {
            if(verbMethod.getVerb().equals(verb)){
                return verbMethod.getToExecute();
            }
        }
        throw new VerbNotSupportedException(verb);
    }

    public boolean addMethodForVerb(String verb, Method method){
        return verbmethods.add(new VerbMethod(verb, method));
    }

    /*Constructeur */
    public Mapping(Set<VerbMethod> set) {
        setVerbmethods(set);
    }

    public Object[] buildArgs(HttpServletRequest req) throws Exception {
        // Vérification si la requête est multipart
        boolean isMultipart = req.getContentType() != null && req.getContentType().toLowerCase().contains("multipart/form-data");
        
        // Appel de la fonction appropriée
        if (isMultipart) {
            return buildArgsFromParts(req);
        } else {
            return buildArgsFromParameters(req);
        }
    }
    
    // Fonction pour traiter les paramètres standards
    private Object[] buildArgsFromParameters(HttpServletRequest req) throws Exception {
        Method toExecute = getMethod(req.getMethod());
        Parameter[] parameters = toExecute.getParameters();
        Object[] args = new Object[parameters.length];
        int i = 0;
    
        for (Parameter param : parameters) {
            // Gestion de MySession
            if (param.getType().equals(MySession.class)) {
                args[i++] = new MySession(req.getSession());
                continue;
            }
    
            // Récupération du nom du paramètre
            String name = param.getName();
            if (!param.isAnnotationPresent(ParamWrapper.class) && !param.isAnnotationPresent(ParamName.class)) {
                throw new Exception("ETU2741" + "L'argument " + name + " n'est pas annoté.");
            }
    
            if (param.isAnnotationPresent(ParamWrapper.class)) {
                ParamWrapper annotation = param.getAnnotation(ParamWrapper.class);
                if (annotation != null && !annotation.name().equals("")) {
                    name = annotation.name();
                }
    
                Object result = param.getType().getConstructor().newInstance();
                Field[] classFields = param.getType().getDeclaredFields();
    
                for (Field field : classFields) {
                    String fieldName = field.getName();
                    ParamEquivalent fieldAnnot = field.getAnnotation(ParamEquivalent.class);
                    if (fieldAnnot != null && !fieldAnnot.name().equals("")) {
                        fieldName = fieldAnnot.name();
                    }
                    String fullName = name + "." + fieldName;
                    String strValue = req.getParameter(fullName);
    
                    if (strValue == null) {
                        throw new RequiredParameterException(name + "." + fieldName);
                    }
    
                    Object toSet = ParametersUtil.castString(strValue, field.getType());
                    Reflect.set(result, field.getName(), toSet, field.getType());
                }
                args[i++] = result;
            } else {
                ParamName annotation = param.getAnnotation(ParamName.class);
                if (annotation != null && !annotation.name().equals("")) {
                    name = annotation.name();
                }
    
                String strValue = req.getParameter(name);
                if (strValue == null) {
                    throw new RequiredParameterException(name);
                }
    
                args[i++] = ParametersUtil.castString(strValue, param.getType());
            }
          
            /*Vérification des validations */
            List<Annotation> validations=Reflect.getAllValidationAnnot(param);
            for (Annotation annotation : validations) {
                // Récupérer l'annotation Validation
                Validation validationInfo = annotation.annotationType().getAnnotation(Validation.class);

                if (validationInfo != null) {
                    String className = validationInfo.linkedValidator();

                    // Charger la classe du validateur
                    Class<?> validatorClass = Class.forName(className);

                    // Récupérer le constructeur prenant (Object, Annotation)
                    Constructor<?> constructor = validatorClass.getConstructor(Object.class, String.class ,Annotation.class);

                    // Créer une instance avec des valeurs par défaut
                    Validator validatorInstance = (Validator)constructor.newInstance(args[i], name, annotation);
                    
                    /*Le throws Exception est set dans la fonction Validate pour l'instant*/
                    if(!validatorInstance.validate()){
                        throw new ValidationException(validatorInstance.getErrorMessage());
                    }
                }
            }

            i++;
        }
        return args;
    }
    
    // Fonction pour traiter les parties multipart
    private Object[] buildArgsFromParts(HttpServletRequest req) throws Exception {
        Method toExecute = getMethod(req.getMethod());
        Parameter[] parameters = toExecute.getParameters();
        Object[] args = new Object[parameters.length];
        int i = 0;
    
        for (Parameter param : parameters) {
            // Gestion de MySession
            if (param.getType().equals(MySession.class)) {
                args[i++] = new MySession(req.getSession());
                continue;
            }
    
            // Gestion de MultiPartFile
            if (param.getType().equals(MultiPartFile.class)) {
                // Récupération de la partie correspondant au nom du paramètre
                String name = param.getName();
                if (param.isAnnotationPresent(ParamName.class)) {
                    ParamName annotation = param.getAnnotation(ParamName.class);
                    if (annotation != null && !annotation.name().equals("")) {
                        name = annotation.name();
                    }
                }
    
                Part part = req.getPart(name);
                if (part == null) {
                    throw new RequiredParameterException(name);
                }
    
                // Création d'une instance MultiPartFile
                args[i++] = new MultiPartFile(part);
                continue;
            }
    
            // Récupération du nom du paramètre
            String name = param.getName();
            if (!param.isAnnotationPresent(ParamWrapper.class) && !param.isAnnotationPresent(ParamName.class)) {
                throw new Exception("ETU2741" + "L'argument " + name + " n'est pas annoté.");
            }
    
            if (param.isAnnotationPresent(ParamWrapper.class)) {
                ParamWrapper annotation = param.getAnnotation(ParamWrapper.class);
                if (annotation != null && !annotation.name().equals("")) {
                    name = annotation.name();
                }
    
                Object result = param.getType().getConstructor().newInstance();
                Field[] classFields = param.getType().getDeclaredFields();
    
                for (Field field : classFields) {
                    String fieldName = field.getName();
                    ParamEquivalent fieldAnnot = field.getAnnotation(ParamEquivalent.class);
                    if (fieldAnnot != null && !fieldAnnot.name().equals("")) {
                        fieldName = fieldAnnot.name();
                    }
                    String fullName = name + "." + fieldName;
    
                    // Extraction depuis les parties
                    Part part = req.getPart(fullName);
                    if (part == null) {
                        throw new RequiredParameterException(name + "." + fieldName);
                    }
    
                    String strValue = new String(part.getInputStream().readAllBytes());
                    Object toSet = ParametersUtil.castString(strValue, field.getType());
                    Reflect.set(result, field.getName(), toSet, field.getType());
                }
                args[i++] = result;
            } else {
                ParamName annotation = param.getAnnotation(ParamName.class);
                if (annotation != null && !annotation.name().equals("")) {
                    name = annotation.name();
                }
    
                // Extraction depuis les parties
                Part part = req.getPart(name);
                if (part == null) {
                    throw new RequiredParameterException(name);
                }
    
                String strValue = new String(part.getInputStream().readAllBytes());
                args[i++] = ParametersUtil.castString(strValue, param.getType());
            }
        }
        return args;
    }    
    
    /*Fonction appelee si quelqu'un entre l'URL */
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws VerbNotSupportedException,Exception{
        PrintWriter out=resp.getWriter();
        Method toExecute=getMethod(req.getMethod());
        Class<?> class1= toExecute.getDeclaringClass();
        Object caller= class1.getConstructor().newInstance();

        for (Field field : class1.getDeclaredFields()) {
            if(field.getType()==MySession.class) Reflect.set(caller, field.getName(), new MySession(req.getSession()), field.getType());
        } 

        Object[] args= buildArgs(req);

        Object result=toExecute.invoke(caller, args);

        /*Cas d'un rest api */
        if(toExecute.isAnnotationPresent(RestApi.class)){
            Gson instG= new Gson();
            String toPrint=null;
            if(result instanceof ModelView){
                toPrint=instG.toJson(((ModelView)result).getData());
            }
            else{
                toPrint=instG.toJson(result);
            }
            resp.setContentType("application/json");
            out.println(toPrint);
            return;
        }

        /*Situation normale de MVC */
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