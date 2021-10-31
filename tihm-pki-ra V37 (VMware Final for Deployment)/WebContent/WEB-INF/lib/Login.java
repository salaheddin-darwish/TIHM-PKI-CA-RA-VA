package org.tihm.pki.ra.servelts;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.tihm.pki.ra.Config;
import org.tihm.pki.ra.UserRec;
import org.tihm.pki.ra.csrdaos.SecureDAO;

/**
 * Servlet implementation class Login
 * @author Salaheddin Darwish 19/01/2017 ==> Salaheddin.darwish@{rhul.ac.uk or gmail.com} 
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(Login.class.getName());
	private static final String VIEW = "/login.jsp";

	/**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession();
		UserRec connectedUser = (UserRec) session.getAttribute(Config.ATT_SESSION_USER);
		
		if (request.getParameter("disconnect") != null && connectedUser != null)
		{
			logger.info("Successful Logout:"+connectedUser.getEmail()+", "+connectedUser.getFirstname()+" "+connectedUser.getLastname());
			session.setAttribute( Config.ATT_SESSION_USER, null);
			session.invalidate();
			this.getServletContext().getRequestDispatcher( VIEW ).forward( request, response );
			
		}
		else 
		{
			if (Config.ATT_ERRORS != null)session.setAttribute( Config.ATT_ERRORS, null);
			if (connectedUser != null &&  connectedUser.authenticated)
			{
				logger.info("Logout enforced "+connectedUser.getEmail()+", "+connectedUser.getFirstname()+" "+connectedUser.getLastname());
				session.setAttribute( Config.ATT_SESSION_USER, null);
				session.invalidate();
			}
			
			this.getServletContext().getRequestDispatcher(VIEW).forward( request, response );	
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		
		HttpSession session = request.getSession();
		UserRec tryLoginUser = null ;
		UserRec usrrec = (UserRec) session.getAttribute( Config.ATT_SESSION_USER );
		
		try
		{
			SecureDAO DaSo = new SecureDAO();			
			tryLoginUser = DaSo.loginUser(request.getParameter("email"), request.getParameter("password"));

		if (tryLoginUser != null) 			
		{
		 
			if (usrrec != null && usrrec.email.equals(tryLoginUser.email)) 
			{				
				tryLoginUser.setFailAttmptCount(usrrec.getFailAttmptCount());
				
			}
			
			// System.out.println(tryLoginUser.getFailAttmptCount());
			
			if (tryLoginUser.isAuthenticated() && tryLoginUser.getFailAttmptCount()<Config.MAX_LOGIN_FAILURE_ATTEMPTS-1)
			{
				
				request.setAttribute( Config.ATT_SUCCESS, "Successful Login");	
				session.setAttribute( Config.ATT_SESSION_USER, tryLoginUser);
				this.getServletContext().getRequestDispatcher("/index.jsp").forward( request, response );
		
				logger.info("Successful Login:"+tryLoginUser.getEmail()+", "+tryLoginUser.getFirstname()+" "+tryLoginUser.getLastname());
				return;
			}	
			else // Authentication Failure with existing username. 
			{				
				
				    tryLoginUser.setFailAttmptCount(tryLoginUser.getFailAttmptCount()+1) ;
					
				    if (tryLoginUser.getFailAttmptCount() >=	Config.MAX_LOGIN_FAILURE_ATTEMPTS)
					{
						request.setAttribute( Config.ATT_ERRORS, "Multiple Login Failures occurs, your account is current blocked, please wait 20 mins to login again");	
						session.setAttribute( Config.ATT_SESSION_USER, usrrec);
						logger.severe("Multiple Login Failures occurs, your account is current blocked, please wait 20 mins to login again:"
										+tryLoginUser.getEmail()+", "+tryLoginUser.getFirstname()+" "+tryLoginUser.getLastname());
					}
					else
					{
					    request.setAttribute( Config.ATT_ERRORS, "Invalid Password! check your password - Current Attempts( "+tryLoginUser.getFailAttmptCount()+" )");
						session.setAttribute( Config.ATT_SESSION_USER, tryLoginUser);
						logger.severe("Invalid Password! check your password:"
								+tryLoginUser.getEmail()+", "+tryLoginUser.getFirstname()+" "+tryLoginUser.getLastname()+", FA= "+tryLoginUser.getFailAttmptCount());
						
					}				
					
					this.getServletContext().getRequestDispatcher(VIEW ).forward( request, response );		
			}
		
		} else {
			request.setAttribute( Config.ATT_ERRORS, "Invalid credentials, Email or Username is not recognized! check your credentials");
			session.setAttribute( Config.ATT_SESSION_USER, null);
			this.getServletContext().getRequestDispatcher(VIEW ).forward( request, response );
			logger.severe("Invalid credentials, Email or Username is not recognized! check your credentials:"+request.getParameter("email"));
		}
		}
		catch (SQLException sqlexc)
		{
			sqlexc.printStackTrace();
			request.setAttribute( Config.ATT_ERRORS, "DataSource Connection Failure ");
			this.getServletContext().getRequestDispatcher( VIEW ).forward( request, response );	
			logger.severe("DataSource Connection Failure:"+sqlexc.getMessage());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			request.setAttribute( Config.ATT_ERRORS, "Login Error");
			this.getServletContext().getRequestDispatcher( VIEW ).forward( request, response );	
			logger.severe("Login Error: "+e.getMessage());
		}

	}

}
