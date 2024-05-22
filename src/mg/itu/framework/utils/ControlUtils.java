package mg.itu.framework.utils;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import mg.itu.framework.annotation.Controller;
import mg.itu.framework.annotation.GET;
import mg.itu.framework.objects.Mapping;

/*Ensemble de méthodes utiles pour les controllers */
public class ControlUtils {
    //Fonction permettant de recuperer toutes les classes contenues directement dans un package
    public ArrayList<Class<?>> scanPackage(String packageName){
        /*Initialisation de l'ArrayList */
        ArrayList<Class<?>> toReturn=new ArrayList<Class<?>>();
        
        packageName=packageName.replaceAll("\\.","/");
        File baseFile=new File(getClass().getClassLoader().getResource(packageName).getFile());

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
                System.out.println(className+" n'est pas une classe.");
                continue;
            }
        }

        /*Return */
        return toReturn;
    }

    public ArrayList<Class<?>> getControllerList(String packageName){
        /*Recherche de controllers - Sprint1
        Scan et mise en place dans un attribut des valeurs */
        ArrayList<Class<?>> controllers=new ArrayList<Class<?>>();
        ArrayList<Class<?>> toAnalyse=scanPackage(packageName);
        for (Class<?> class1 : toAnalyse) {
            if(class1.isAnnotationPresent(Controller.class)) controllers.add(class1);
        }

        return controllers;
    }

    public HashMap<String,Mapping> getRoutes(String packageName){
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
                    Mapping map=new Mapping(class1.getName(), meth.getName());
                    toReturn.put(annot.urlPattern(), map);
                }    
            }
        }

        return toReturn;
    }
}