package mg.itu.framework.utils;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import mg.itu.framework.annotation.Controller;
import mg.itu.framework.annotation.GET;
import mg.itu.framework.objects.Mapping;
import mg.itu.framework.utils.errors.EmptyPackageException;
import mg.itu.framework.utils.errors.NoControllerException;
import mg.itu.framework.utils.errors.NoPackageException;
import mg.itu.framework.utils.errors.SameURLException;

/*Ensemble de méthodes utiles pour les controllers */
public class ControlUtils {
    //Fonction permettant de recuperer toutes les classes contenues directement dans un package
    public ArrayList<Class<?>> scanPackage(String packageName) throws NoPackageException,EmptyPackageException{
        /*Initialisation de l'ArrayList */
        ArrayList<Class<?>> toReturn=new ArrayList<Class<?>>();
        
        packageName=packageName.replaceAll("\\.","/");
        File baseFile=null;
        try{
            baseFile=new File(getClass().getClassLoader().getResource(packageName).getFile());
        }
        catch(NullPointerException e){
            throw new NoPackageException();
        }
    
        File[] allClasses=baseFile.listFiles();

        for (File file : allClasses) {
            /*Récupération de la partie *1 dans un fichier quelconque de la forme *1.*2 */
            String className=file.getName().split("\\.")[0];

            /*Ajout du nom du package */
            className=packageName+"."+className;

            try{
                /*Recuperation de la classe */
                Class<?> toAdd=Class.forName(className);
                toReturn.add(toAdd);
            }

            /*Dans le cas où un fichier qui n'est pas un .class se trouve dans le package */
            catch(ClassNotFoundException e){
                continue;
            }
        }

        if(toReturn.size()==0){
            throw new EmptyPackageException(packageName);
        }

        /*Return */
        return toReturn;
    }

    public ArrayList<Class<?>> getControllerList(String packageName) throws NoPackageException,EmptyPackageException,NoControllerException{
        /*Recherche de controllers - Sprint1
        Scan et mise en place dans un attribut des valeurs */
        ArrayList<Class<?>> controllers=new ArrayList<Class<?>>();
        ArrayList<Class<?>> toAnalyse=scanPackage(packageName);
        for (Class<?> class1 : toAnalyse) {
            if(class1.isAnnotationPresent(Controller.class)) controllers.add(class1);
        }

        if(controllers.size()==0){
            throw new NoControllerException(packageName);
        }

        return controllers;
    }

    public HashMap<String,Mapping> getRoutes(String packageName) throws NoPackageException,EmptyPackageException,NoControllerException,SameURLException{
        /*Initialisationd de la variable à retourner */
        HashMap<String,Mapping> toReturn=new HashMap<String,Mapping>();

        /*On récupère tous les controllers dont on doit analyser les méthodes */
        ArrayList<Class<?>> toSearchInto= getControllerList(packageName);

        for (Class<?> class1 : toSearchInto) {
            /*On prend toutes les méthodes du controller actuel */
            Method[] lsMethods=class1.getDeclaredMethods();

            for (Method meth : lsMethods) {
                /*Récupérer l'annotation GET sur la méthode */
                GET annot=meth.getAnnotation(GET.class);

                /*Si l'annotation est présente */
                if(annot!=null){
                    Mapping map=new Mapping(meth);
                    String url=annot.urlPattern();

                    if(toReturn.get(url)!=null){
                        Mapping other=toReturn.get(url);
                        throw new SameURLException(map, other);
                    }

                    toReturn.put(url, map);
                }
            }
        }

        return toReturn;
    }
}