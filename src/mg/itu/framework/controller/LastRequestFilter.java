package mg.itu.framework.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

public class LastRequestFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialisation du filtre si nécessaire
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // Passer la requête au prochain filtre/servlet pour traitement
        chain.doFilter(request, response);

        // Après avoir traité la requête, capturer l'URL et la stocker dans la session
        HttpSession session = httpRequest.getSession();
        session.setAttribute("lastReq", httpRequest.getRequestURI());
    }

    @Override
    public void destroy() {
        // Libération des ressources si nécessaire
    }
}
