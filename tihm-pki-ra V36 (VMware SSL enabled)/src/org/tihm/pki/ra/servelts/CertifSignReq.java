package org.tihm.pki.ra.servelts;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.tihm.pki.ra.CertificateRec;


import org.tihm.pki.ra.Model;
import org.tihm.pki.ra.UserRec;
import org.tihm.pki.ra.csrdaos.CSRProcessDAO;
import org.tihm.pki.ra.utilities.FileUtilies;
import org.tihm.pki.ra.Config;


/**
 * Servlet implementation class CertifSignReq
 * @author Salaheddin Darwish @ 31/12/2016
 */
@WebServlet("/secure/newcsr")
public class CertifSignReq extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/secure/cersignreq-edit.jsp";
//	private static final String VIEW = "/secure/test.jsp?country=GB";
	static Logger logger = Logger.getLogger(CertificateEdit.class.getName());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CertifSignReq() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub	
		
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		CertificateRec newCerSignReq = new CertificateRec();
		byte[] csrfileContent = null;
		String forwardurlink = "";
		   
		
    	try {
			// Validate
			List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
			
			
			for (FileItem item : items)
			{
			    if (item.isFormField())
			    {
			        // Process regular form field (input type="text|radio|checkbox|etc", select, etc).
			        String fieldname = item.getFieldName();
			        String fieldvalue = item.getString();			        
			        newCerSignReq.setDataForFieldAndValue(fieldname, fieldvalue);
			        
			        // for keeping parameters values in the new CSR form 
			        if (forwardurlink.equals(""))
			        	forwardurlink = fieldname+"="+fieldvalue; 
			        else
			        	forwardurlink = forwardurlink+"&"+fieldname+"="+fieldvalue;
			        
			    } else {
			        // Process form file field (input type="file").
			    	if (item.getSize() > 0) {
			            
			    		InputStream filecontent = item.getInputStream();
			            
			    		csrfileContent = IOUtils.toByteArray(filecontent);
			            
			    		newCerSignReq.setCsrfile(csrfileContent);
			    	}
			    }
			}
			
			newCerSignReq.validate(new Model());
			
			UserRec connectedUser = (UserRec) request.getSession().getAttribute(Config.ATT_SESSION_USER);
			
			
			// Access Control
			if (!newCerSignReq.getOrganization().equalsIgnoreCase(connectedUser.getOrganization())) 
				{
				 if ( !connectedUser.getRole().equalsIgnoreCase("admin"))
				 
				 {
					 logger.info("The current user ("+connectedUser.getEmail()+","+connectedUser.getFirstname()+" "+connectedUser.getLastname()+") is not authorized "
								+ "to submit a CSR for this organization:"+newCerSignReq.getOrganization());
					 
					 throw new Exception( "The current user is not authorized "
						+ "to submit a CSR for this organization :"+newCerSignReq.getOrganization()); // log this event 
				 }
				 }
			
			CSRProcessDAO csrDAO = new CSRProcessDAO();			
			
    		if (csrDAO.saveCSR(newCerSignReq , connectedUser.getId()))
    		{
    			
    			request.setAttribute( Config.ATT_SUCCESS, "Successful Submission of Certificate Signing Request");
    			
//    			response.sendRedirect(request.getContextPath() + "/secure/certificates.jsp" );
//    			return;
    			
    		} 
    		else 
    		{
     			
				logger.info("Fail to submit the Certificate Signing Request by "+connectedUser.getEmail()+","+connectedUser.getFirstname()+" "+connectedUser.getLastname()) ;
				throw new Exception("Fail to submit the Certificate Signing Request! check your request inputs");
    		}				
			
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			request.setAttribute( Config.ATT_ERRORS, e.getMessage());
			
		} catch (SQLException e ) 
    	
    	{
			request.setAttribute( Config.ATT_ERRORS, "Problem in inserting the record in the datasource: "+ e.getMessage());
			e.printStackTrace();
    	}
    	catch (IOException e)
    	{
    		request.setAttribute( Config.ATT_ERRORS, "Problem in save the CSR file in the datasource: "+ e.getMessage());
    		e.printStackTrace();
    	}
    	catch (Exception e) {
			// TODO Auto-generated catch block
			request.setAttribute( Config.ATT_ERRORS, e.getMessage());	
			e.printStackTrace();
		}	
    	
    	this.getServletContext().getRequestDispatcher(VIEW+"?"+forwardurlink).forward(request, response );			
		
	}

}
