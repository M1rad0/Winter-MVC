package mg.itu.framework.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mg.itu.framework.annotation.Controller;

/*Servlet gérant généralement toutes les requêtes clients entrantes */
public class FrontController extends HttpServlet{
    ArrayList<Class<?>> controllers;

    @Override
    public void init() throws ServletException {
        super.init();

        /*Recherche de controllers - Sprint1 */
        /*Récupération du nom du package qui contiendra tous les controllers */
        String packageName=getInitParameter("controllerspackage");
        /*Scan et mise en place dans un attribut des valeurs */
        this.controllers=scanPackage(packageName);
    }

    private ArrayList<Class<?>> scanPackage(String packageName){
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
                Class<?> toAnalyse=Class.forName(className);
                if(toAnalyse.isAnnotationPresent(Controller.class)) toReturn.add(toAnalyse); /*Ajout selon l'état de la classe (controller ou non) */
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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    protected void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*Affichage de l'URL -Sprint 0 */
        PrintWriter out = resp.getWriter();
        out.println("Page : "+req.getRequestURL());

        /*Affichage de la liste des controllers (Sprint 1) */
        out.println("Liste des controllers");
        for (Class<?> class1 : controllers) {
            out.println(class1.getName());
        }
    }
}