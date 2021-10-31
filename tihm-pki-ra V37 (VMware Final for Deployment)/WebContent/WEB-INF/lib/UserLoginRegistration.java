package org.tihm.pki.ra.servelts;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.tihm.pki.ra.Config;
import org.tihm.pki.ra.Model;
import org.tihm.pki.ra.csrdaos.SecureDAO;
import org.tihm.pki.ra.UserRec;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servlet implementation class UserLoginRegistration
 */
@WebServlet("/register")
public class UserLoginRegistration extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/register.jsp";
	static Logger logger = Logger.getLogger(UserLoginRegistration.class.getName());
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserLoginRegistration() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	try {	

	
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}catch (Exception e){
			request.setAttribute( Config.ATT_ERRORS, e.getMessage());
			logger.log(Level.WARNING, e.getMessage(), e);
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
			String email 		= request.getParameter("email" );
			String organization = request.getParameter("organization");
			String firstname 	= request.getParameter( "firstname");
			String lastname 	= request.getParameter( "lastname" );			
			String password1 	= request.getParameter( "password1" );
			String password2 	= request.getParameter( "password2");
			
			String forwardurlink = "email="+email+"&lastname="+lastname+"&organization="+ organization+"&firstname="+firstname+"&password1=&password2=";
			
			SecureDAO sds = new SecureDAO();			
			
	        UserRec newUser = new UserRec(firstname, lastname, organization, email, password1, password2, null, -1); // user_role Instantiated 
	        
	        try {
	        	

	        	// Validate Input data 
	        	newUser.validate(new Model());       	
	        		
	        	if (sds.registerUser(newUser)) 
	    		{
	    			request.setAttribute( Config.ATT_SUCCESS, "You are now registered");
	    			logger.info("Successful Regsitration:"+email+", "+firstname+" "+lastname+"("+organization+").");
	    			
	    			
	    			// Auto login user
	    			UserRec tryLoginUser = sds.loginUser(email,password1); 			
	    			    				    			
	    			if (tryLoginUser != null && tryLoginUser.isAuthenticated()) {
	    				
	    			request.getSession().setAttribute(Config.ATT_SESSION_USER, tryLoginUser);	    		
	    			this.getServletContext().getRequestDispatcher("/index.jsp").forward( request, response );	    			
	    			return;
	    			}
	    		 else
		    		{
	    			 	logger.info("Login Error is encountered after Registration"+email+", "+firstname+" "+lastname+"("+organization+").");		
	    			 	throw new Exception("Login Error is encountered!");
		    		}
	    		}
	        	else
	        	{
	        		logger.info("Unsuccessful Registration:"+email+", "+firstname+" "+lastname+"("+organization+").");
	        		throw new Exception("User Registration Error is encountered!");
	        	
	        	}
			} 
	        catch (SQLException e ) 
	    	
	    	{
				request.setAttribute(Config.ATT_ERRORS, "Problem in the datasource: "+ e.getMessage());
				 e.printStackTrace();
	    	}
	        catch (Exception e) 
	        {
				request.setAttribute( Config.ATT_ERRORS, e.getMessage());
				logger.log(Level.WARNING, e.getMessage(), e);
			}		
		
		this.getServletContext().getRequestDispatcher(VIEW+"?"+forwardurlink).forward( request, response );	
		
	}

}
