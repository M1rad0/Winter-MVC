package mg.itu.framework.objects;

import jakarta.servlet.http.HttpSession;

public class MySession {
    private HttpSession session;

    MySession(HttpSession session){
        this.session=session;
    }

    public void add(String key, Object obj){
        this.session.setAttribute(key, obj);
    }

    public Object get(String key){
        return this.session.getAttribute(key);
    }

    public void delete(String key){
        this.session.removeAttribute(key);
    }
}
