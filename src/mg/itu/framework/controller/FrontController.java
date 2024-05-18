package mg.itu.framework.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mg.itu.framework.annotation.Controller;
import mg.itu.framework.utils.ControlUtils;

/*Servlet gérant généralement toutes les requêtes clients entrantes */
public class FrontController extends HttpServlet{
    boolean controllerScanned=false;
    ArrayList<Class<?>> controllers;

    public void initVariables(){
        ControlUtils instUtils=new ControlUtils();

        /*Recherche de controllers - Sprint1 */
        /*Récupération du nom du package qui contiendra tous les controllers */
        String packageName=getInitParameter("controllerspackage");

        /*Scan et mise en place dans un attribut des valeurs */
        this.controllers=new ArrayList<Class<?>>();
        ArrayList<Class<?>> toAnalyse=instUtils.scanPackage(packageName);
        for (Class<?> class1 : toAnalyse) {
            if(class1.isAnnotationPresent(Controller.class)) controllers.add(class1);
        }

        /*Confirmation du scan */
        System.out.println("ControllerScan complete");
        controllerScanned=true;
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
        /*ControllerScanned */
        if(!controllerScanned){
            initVariables();
        }

        /*Affichage de l'URL -Sprint 0 */
        PrintWriter out = resp.getWriter();
        out.println("Page : "+req.getRequestURL());

        /*Affichage de la liste des controllers -Sprint 1 */
        out.println("Liste des controllers");
        for (Class<?> class1 : controllers) {
            out.println(class1.getName());
        }
    }
}