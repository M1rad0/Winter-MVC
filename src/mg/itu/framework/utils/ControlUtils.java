package mg.itu.framework.utils;

import java.io.File;
import java.util.ArrayList;

/**
 * ControlUtils
 */
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
}