package org.tihm.pki.ra.filters;

import java.io.IOException;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.tihm.pki.ra.Config;
import org.tihm.pki.ra.UserRec;

/**
 * Servlet Filter implementation class AuthnFilter
 * @author Salaheddin Darwish 20/01/2017
 */
@WebFilter(dispatcherTypes = {
				DispatcherType.REQUEST, 
				DispatcherType.FORWARD, 
				DispatcherType.INCLUDE, 
				DispatcherType.ERROR
		}
					, urlPatterns = { "/secure/*" })
public class AuthnFilter implements Filter {

	public static final String LOGIN_PAGE  = "/login";
    /**
     * Default constructor. 
     */
    public AuthnFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here

		// pass the request along the filter chain
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession();        
        UserRec usrrec = (UserRec) session.getAttribute( Config.ATT_SESSION_USER );
        
        if (usrrec  == null || !usrrec.isAuthenticated() ) 
        { 
        	
        	session.setAttribute(Config.ATT_ERRORS, "No Access, You have to login first!");
        	System.out.println("No Access, You have to login first");
        	response.sendRedirect(request.getContextPath() + LOGIN_PAGE );       	
            
        } else {
            chain.doFilter( request, response );
        }

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
