package org.tihm.pki.ra.servelts;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.tihm.pki.ra.Config;
import org.tihm.pki.ra.UserRec;
import org.tihm.pki.ra.csrdaos.SecureDAO;
import org.tihm.pki.ra.Model;


/**
 * Servlet implementation class UserAccount
 */
@WebServlet("/secure/account")
public class UserAccount extends HttpServlet implements Servlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/secure/useraccount.jsp";
	
	static Logger logger = Logger.getLogger(UserAccount.class.getName());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserAccount() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try{
			
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
		
		try
		{
			
			SecureDAO ds = new SecureDAO();
			
			HttpSession session = request.getSession();
			
			UserRec connectedUser = (UserRec) session.getAttribute(Config.ATT_SESSION_USER);
			
			Model modl = new Model();
			
		    if (request.getParameter("id") != null)			
			{
        	  if (request.getParameter("id").equals("cud"))
				{	
        		  	String email 		= request.getParameter("email" );
					String organization = request.getParameter("organization");
					String firstname 	= request.getParameter( "firstname");
					String lastname 	= request.getParameter( "lastname");
					String inptpass 	= request.getParameter( "cudpass");
					
					UserRec usrrec = new UserRec(firstname, lastname, organization, email, null, inptpass, null,connectedUser.getId()); // user_role Instantiated
					
					modl.validateName(inptpass, "Your Password");
					modl.validateName(email, "Email");
					modl.validateName(organization, "Organization");
					modl.validateName(firstname, "First Name");
					modl.validateName(firstname, "Last Name");					
										
					List<String> clst = modl.matchingFields(usrrec, connectedUser);				
					
					if(clst.size()!= 0)
					{	
						UserRec tempUsrRec = ds.loginUser(connectedUser.getEmail(), inptpass);
						
						if (tempUsrRec != null && tempUsrRec.authenticated) 
						{
		     				if (ds.updateUserDetails(usrrec)) 
		     				{
		     					request.setAttribute( Config.ATT_SUCCESS, "Successful Upate: the changes have been confirmed");
		     					
		     					// usrrec.password = tempUsrRec.getPassword();
		     					
		     					usrrec.setAuthenticated(tempUsrRec.authenticated);
		     					
		     					session.setAttribute( Config.ATT_SESSION_USER, usrrec);
		     				}	     				
		     				else
							{
		     					logger.info("Unsuccessful update for the new details provided by"+connectedUser.getEmail()+","+connectedUser.getFirstname()+" "+connectedUser.getLastname()) ;
		     					throw new Exception ("Unsuccessful update for the new details provided");	
		     				}
						}
	     				else
	     				{
	     					request.setAttribute( Config.ATT_ERRORS, "Invalid Password! check your password");
	     					this.getServletContext().getRequestDispatcher(VIEW ).forward( request, response );
	     					logger.severe("Invalid password for confirming changes by"+connectedUser.getEmail()+","+connectedUser.getFirstname()+" "+connectedUser.getLastname()) ;
	     					throw new Exception("Fail to submit the Certificate Signing Request! check your request inputs");
	     					
	     				}
					}								
					
				}
				else if (request.getParameter("id").equals("rpwd"))
				{
					
					String oldpassword = request.getParameter("oldpassword" );					
					String newpassword = request.getParameter("newpassword");					
					String confirmnewpasaword	= request.getParameter( "confirmnewpassword");
					
					if (newpassword != null && confirmnewpasaword != null )					
					{
			    		
//						modl.validateName(oldpassword, "Your Password");
//						// validate new password inputs
//						modl.validateName(newpassword, "New Password");						
						
						modl.validatePassword(newpassword, confirmnewpasaword);
						
						// authorise changing the password 
						UserRec tmpUsrRec = ds.loginUser(connectedUser.getEmail(), oldpassword);
																		
						if (tmpUsrRec != null) 
						{
							tmpUsrRec.password = newpassword;
							
		     				if (ds.changeUserPass(tmpUsrRec)) 
		     				{
		     					request.setAttribute( Config.ATT_SUCCESS, "Successful Password Reset");		     					
		     					session.setAttribute( Config.ATT_SESSION_USER, tmpUsrRec);
		     				}	     				
		     				else
								{
		     					logger.severe("Unsuccessful Password Reset by"+connectedUser.getEmail()+","+connectedUser.getFirstname()+" "+connectedUser.getLastname()) ;
		     					throw new Exception ("Unsuccessful Password Reset");	
								}
						}
	     				else
	     				{
	     					request.setAttribute( Config.ATT_ERRORS, "Invalid Password! check your password");
	     					this.getServletContext().getRequestDispatcher(VIEW ).forward( request, response );
	     					logger.severe("Invalid password for confirming changes by"+connectedUser.getEmail()+","+connectedUser.getFirstname()+" "+connectedUser.getLastname());
	     					return;
	     					
	     				}				
						
					}
					else
					{
						throw new Exception ("Emty Field Inputs!");			
						
					}				
					
				}	
				else
				{
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Broken Link");
					logger.severe("Call broken for reset password"+connectedUser.getEmail()+","+connectedUser.getFirstname()+" "+connectedUser.getLastname());
					return;
				} 			
				
			}
		    
		}
        catch (SQLException e ) 
    	{
        	request.setAttribute( Config.ATT_ERRORS, "Error while Conntecting to the database: "+e.getMessage());
        	e.printStackTrace();			
    	} 
        catch (Exception e) {
        	request.setAttribute( Config.ATT_ERRORS, e.getMessage());
        	e.printStackTrace();
           
        }		
		
		this.getServletContext().getRequestDispatcher(VIEW).forward( request, response );
 }

}
