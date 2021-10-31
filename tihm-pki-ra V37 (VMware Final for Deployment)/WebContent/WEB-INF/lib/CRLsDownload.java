package org.tihm.pki.ra.servelts;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.tihm.pki.ra.Config;
import org.tihm.pki.ra.utilities.FileUtilies;

/**
 * Servlet implementation class CRLsDownload
 */
@WebServlet("/crl")
public class CRLsDownload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CRLsDownload() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		byte[] fcont = FileUtilies.getFilefromDirectory("crl", ".pem");	
		 String filename = "crl.pem";	
        
        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition","attachment; filename=\"" + filename + "\"");	            
        OutputStream o = response.getOutputStream();
        o.write(fcont);	            
        o.flush();
        o.close();
	
	
	}

}
