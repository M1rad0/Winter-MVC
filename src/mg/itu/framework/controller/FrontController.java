package mg.itu.framework.controller;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mg.itu.framework.objects.Mapping;
import mg.itu.framework.utils.ControlUtils;

/*Servlet gérant généralement toutes les requêtes clients entrantes */
public class FrontController extends HttpServlet{
    HashMap<String,Mapping> routeHashMap;
    
    @Override
    public void init() throws ServletException {
        super.init();

        initVariables();
    }

    public void initVariables() throws ServletException{
        ControlUtils instUtils=new ControlUtils();

        /*Recherche de controllers - Sprint2
        Récupération du nom du package qui contiendra tous les controllers */
        String packageName=getInitParameter("controllerspackage");
        try{
            this.routeHashMap=instUtils.getRoutes(packageName);
        }
        catch(Exception e){
            throw new ServletException(e);
        }
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
        /*Résultat de la recherche de la méthode
        On récupère la requête en tant qu'URI pour faciliter la récupération du path */
        URI requestURI=URI.create(req.getRequestURI());
        String key=requestURI.getPath();
        key=key.substring(key.indexOf("/", 1));

        /*Recherche du mapping associé au path
        Si la clé est associée à un mapping */
        if(routeHashMap.containsKey(key)){
            Mapping found=routeHashMap.get(key);
            try{
                found.execute(req, resp);
            }
            catch(Exception e){
                throw new ServletException(e);
            }
        }
        /*Sinon */
        else{
            resp.sendError(404);
            throw new ServletException("Ce lien n'est associé à aucune méthode");
        }
    }
}