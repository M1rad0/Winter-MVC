package mg.itu.framework.objects;

import java.io.IOException;
import java.util.HashMap;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*Classe retournée par les méthodes associées à des url */
public class ModelView {
    /*Lien vers lequel aller */
    String url;
    /*Data a dispatcher */
    HashMap<String,Object> data;
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    HashMap<String, Object> getData() {
        return data;
    }
    void setData(HashMap<String, Object> data) {
        this.data = data;
    }

    public ModelView(String url) {
        setUrl(url);
        data=new HashMap<String,Object>();
    }

    public void addObject(String key,Object value){
        getData().put(key, value);
    }

    void attributeRequest(HttpServletRequest req){
        for (String key : getData().keySet()) {
            req.setAttribute(key, getData().get(key));
        }
    }

    void dispatchRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        attributeRequest(req);

        req.getRequestDispatcher(url).forward(req, resp);
    }
}
