package org.tihm.pki.ra.servelts;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.tihm.pki.ra.CertificateRec;
import org.tihm.pki.ra.Config;
import org.tihm.pki.ra.UserRec;
import org.tihm.pki.ra.csrdaos.CSRProcessDAO;



/**
 * Servlet implementation class CertificateProf @author Salaheddin Darwish @ 08/01/2017
 * 
 */
@WebServlet("/secure/certificates")
public class CertificateProf extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/secure/certificates.jsp";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CertificateProf() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{


		UserRec connectedUser = (UserRec) request.getSession().getAttribute(Config.ATT_SESSION_USER);

		HttpSession session = request.getSession();
		List<CertificateRec> cerUsrReqs = new ArrayList<CertificateRec>();
		List<CertificateRec> certificates = new ArrayList<CertificateRec>();
		List<CertificateRec> combList = null;

		try {

			CSRProcessDAO csrDAO = new CSRProcessDAO();					

			// List of Certificate Signing Requests Records for current user
			cerUsrReqs = csrDAO.getCurrUserCertificateRecords(connectedUser .getId());
	
			// List of all other user Certificates
			certificates = csrDAO.getAllOtherCertificateList (connectedUser.getId());
			
			combList = new ArrayList<CertificateRec>(cerUsrReqs);
			combList.addAll(certificates);	
		
					

		} catch (SQLException e) {
			// TODO Auto-generated catch block			
			request.setAttribute( Config.ATT_ERRORS, e.getMessage());	
		}catch (Exception e) {
			// TODO Auto-generated catch block			
			request.setAttribute( Config.ATT_ERRORS, e.getMessage());	
		}
		
		session.setAttribute( "certsignreqs", combList);

		// System.out.println("Executed =========)");
		this.getServletContext().getRequestDispatcher(VIEW ).forward( request, response );
		// response.getWriter().append("Served at: ").append(request.getContextPath());
	}

}
