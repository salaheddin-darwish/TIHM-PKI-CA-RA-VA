package org.tihm.pki.ra.servelts;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.tihm.pki.ra.Config;
import org.tihm.pki.ra.UserRec;
import org.tihm.pki.ra.csrdaos.CSRProcessDAO;

/**
 * Servlet implementation class CertificateEdit 
 */
@WebServlet("/secure/certificates/edit")
public class CertificateEdit extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/secure/certificates";
	static Logger logger = Logger.getLogger(CertificateEdit.class.getName());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CertificateEdit() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		if (request.getParameter("id") != null && request.getParameter("download") != null) 
		{ 
			// Download
			CSRProcessDAO fileDAO = new CSRProcessDAO();
			byte[] filecontent = null;
			String filename = null;
			
		  try {
	        	if (request.getParameter("download").equals("csr" ))
	        	{	
		        	filecontent = fileDAO.getFileFromSource(Config.CSR_FILE_EXT, request.getParameter("id") );	
		            filename = "FileCSR_"+request.getParameter("id").toString()+Config.CSR_FILE_EXT;
	        	}
	        	else if (request.getParameter("download").equals("crt"))
	        	{	
		        	filecontent = fileDAO.getFileFromSource(Config.CER_FILE_EXT, request.getParameter("id"));	
		            filename = "FileCRT_"+request.getParameter("id").toString()+Config.CER_FILE_EXT;	        		
	        	}
		            
		            
		            response.setContentType("APPLICATION/OCTET-STREAM");
		            response.setHeader("Content-Disposition","attachment; filename=\"" + filename + "\"");	            
		            OutputStream o = response.getOutputStream();
		            o.write(filecontent);	            
		            o.flush();
		            o.close();
	            
	        } 
	        catch (SQLException e ) 
	    	{
	        	request.setAttribute( Config.ATT_ERRORS, "Error while downloading the certificate: "+e.getMessage());
	        	e.printStackTrace();
	            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR , "File Not Found");
				
	    	}
	    	catch (IOException e)
	    	{
	    		request.setAttribute( Config.ATT_ERRORS, "Error while downloading the certificate: "+e.getMessage());
	    		e.printStackTrace();
	    	    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR , "File Not Found");
	    	}
      
	        catch (Exception e) {
	        	request.setAttribute( Config.ATT_ERRORS, "Error while downloading the certificate: "+e.getMessage());
	        	e.printStackTrace();
	            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR , "File Not Found");
	        }
	        
			return;
		}	
		
		if (request.getParameter("id") != null && request.getParameter("revoke") != null) 
		{ 
			
		try 
		{
			String snVal = request.getParameter("id");
			UserRec connectedUser = (UserRec) request.getSession().getAttribute(Config.ATT_SESSION_USER);
			
			if (request.getParameter("revoke").equalsIgnoreCase("true"))
			{
				
			
				CSRProcessDAO DBconn = new CSRProcessDAO();
				
				
								
				// Access Control
				if (DBconn.getAssUserCrtRec(snVal,connectedUser.getId() )|| connectedUser.getRole().equalsIgnoreCase("admin")) 					
				{
				
					if (DBconn.submitRevoCrtReq(snVal))
					{
						
						request.setAttribute( Config.ATT_SUCCESS, "Successful Certificate Revocation Request("+snVal+")!");
						
						logger.info("Successful Certificate Revocation Request("+snVal+
								") by "+connectedUser.getEmail()+","+connectedUser.getFirstname()+" "+connectedUser.getLastname()) ;
						
					}	
					else
						
						{
						logger.info("Error in Certificate("+snVal+") Revocation! by "+connectedUser.getEmail()+","+connectedUser.getFirstname()+" "+connectedUser.getLastname()) ;
						throw new Exception ("Unauthorized Revocation: Error in Certificate("+snVal+") Revocation!");
						}
					
				}
				else 
					{
					logger.info("Unauthorized Certificate("+snVal+") Revocation! by "+connectedUser.getEmail()+","+connectedUser.getFirstname()+" "+connectedUser.getLastname()) ;
					throw new Exception ("Unauthorized Revocation");	
					}
				
				
			}
			else 
			{
				logger.info("Calling Invalid or broken link for Revocation! by "+connectedUser.getEmail()+","+connectedUser.getFirstname()+" "+connectedUser.getLastname()) ;
				throw new Exception("Invalid or broken link encountered");
			}
        } 
        catch (SQLException e ) 
    	{
        	request.setAttribute( Config.ATT_ERRORS, "Error while revoking the certificate: "+e.getMessage());
        	e.printStackTrace();
           			
    	}
    	catch (IOException e)
    	{
    		request.setAttribute( Config.ATT_ERRORS, "Error while revoking the certificate: "+e.getMessage());
    		e.printStackTrace();
    	}
  
        catch (Exception e) {
        	request.setAttribute( Config.ATT_ERRORS, "Error while revoking the certificate: "+e.getMessage());
        	e.printStackTrace();
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED , e.getMessage());
            return;
           }
		
//		response.sendRedirect(request.getContextPath() +VIEW );
		this.getServletContext().getRequestDispatcher(VIEW ).forward( request, response );
//		return;
		}
		

		
	}
}
