/**
 * 
 */
package org.tihm.pki.ra.csrdaos;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.tihm.pki.ra.CertificateRec;
import org.tihm.pki.ra.Config;
import org.tihm.pki.ra.utilities.Database;
import org.tihm.pki.ra.utilities.FileUtilies;

import java.io.IOException;
import java.nio.file.Files;

/**
 * @author Salaheddin Darwisg @ 2017-01-04
 *
 */
public class CSRProcessDAO {
	
	private final DataSource DatSrc ;
//	private HttpServletRequest HttpServletRequestParam;
	
	protected enum RevocationStatus {
		NOT_REVOKED, 
		REVO_IN_PROGRESS , 
		REVOKED
	}

	
	
	/**
	 * Process access to the database to insert and query csr
	 */
	public CSRProcessDAO() {
		// TODO Auto-generated constructor stub
		this.DatSrc = Database.getDataSource();
		
		
	}
	
	public boolean saveCSR (CertificateRec newCertSingReq , int curuserid) 
			throws IOException, SQLException
	{
		boolean boolflag = false; 
		Connection Conn = null ;
		PreparedStatement statement = null;

		try
		{
			//		
			//save CSR file in the server 
			FileUtilies.saveFiletoDirectory(newCertSingReq.getCsrfile(), newCertSingReq.getFileId(),Config.CSR_FILE_EXT);

			// save CSR into the Database
			String SQLStatment = "INSERT INTO certificate_req_tab (common_name,country,stateprovince,locale,organization, "
					+ "organization_unit, date, csr_file, user_id,file_id,cert_type) VALUES (?,?,?,?,?,?,NOW(),?,?,?,?)";    	  
			Conn = DatSrc.getConnection();
			statement = (PreparedStatement) Conn.prepareStatement(SQLStatment);


			byte[] csrf = newCertSingReq.getCsrfile(); // Temp use 

			statement.setString(1, newCertSingReq.getCommonName());
			statement.setString(2, newCertSingReq.getCountry());
			statement.setString(3, newCertSingReq.getStateprovince());
			statement.setString(4, newCertSingReq.getLocale());
			statement.setString(5, newCertSingReq.getOrganization());
			statement.setString(6, newCertSingReq.getOrganization_unit());
			statement.setBytes(7,csrf);
			statement.setInt(8, curuserid);
			statement.setString(9, newCertSingReq.getFileId());
			statement.setString(10, newCertSingReq.getCertType());

			if (statement.executeUpdate() != 0)
			{
				boolflag = true ;	  		  
				statement.close();	
				Conn.close();
			}		  
		}
		catch (SQLException e)
		{
//			 System.err.println("Got Exception");
			 FileUtilies.DelCSRFilefrmDirectory(newCertSingReq.getFileId());
			 throw new SQLException(e.getMessage());			
		}
		finally
		{
	  		 if (statement != null) statement.close();	
	  		 if (Conn != null)  Conn.close();
			
		}

		return boolflag ; 
	}
	
	public ArrayList <CertificateRec>getCurrUserCertificateRecords (int idusr) throws IOException, SQLException, Exception
	{
		
		Connection Conn = null ;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		ArrayList <CertificateRec> cerx = new ArrayList <CertificateRec>() ;		
		
		try {

			String SQLStatment = "Select id, cert_type, common_name,country,stateprovince,locale,organization,organization_unit,date,"
								+ "cer_serial_number,file_id,revoked, user_id from certificate_req_tab where user_id = ?";
			
			Conn = DatSrc.getConnection();
			statement = (PreparedStatement) Conn.prepareStatement(SQLStatment);
			statement.setInt(1, idusr);
			resultSet = statement.executeQuery();
			
			while ( resultSet.next() ) 
            {
            	
            	cerx.add(CertificateRec.mapWithDatabase(resultSet));
            }

			
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new SQLException(e.getMessage());
		}	
		catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		throw new Exception(e.getMessage());
	}	
		finally 
		{
			if (resultSet != null) resultSet.close();	
			if (statement != null) statement.close();	
			if (Conn != null) Conn.close();			
			
		}
		
		return  cerx; 
	}
	
	public ArrayList<CertificateRec> getAllOtherCertificateList (int usrid) throws IOException, SQLException, Exception
	{
		
		
		Connection Conn = null ;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		ArrayList <CertificateRec> cerxList = new ArrayList <CertificateRec>() ;	
		
		try {

			String SQLStatment = "Select id, cert_type, common_name,country,stateprovince,locale,organization,"
									+ "organization_unit,date,cer_serial_number,file_id, revoked , user_id from certificate_req_tab "
									+ "Where cer_serial_number is not null and user_id != ?";
								
			Conn = DatSrc.getConnection();	

			statement = (PreparedStatement) Conn.prepareStatement(SQLStatment);				

			statement.setInt(1, usrid);
			
			resultSet = statement.executeQuery();
			
            while ( resultSet.next()) 
            {
            	
            	cerxList.add(CertificateRec.mapWithDatabase(resultSet));
            }

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new SQLException(e.getMessage());
		}	
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new SQLException(e.getMessage());
		}
		finally 
		{
			if (resultSet != null) resultSet.close();	
			if (statement != null) statement.close();	
			if (Conn != null) Conn.close();			
			
		}
		
		return  cerxList; 
	}
	
	public byte[] getFileFromSource(String filetype, String fileName) throws Exception, SQLException
	{
		Connection Conn = null ;
		PreparedStatement statement = null;	
		ResultSet resultSet = null;
		byte[] fcont = null; 
		
	try {
		
		if(Config.FILE_DIRECTORY_SOURCE)
		{
			// Reading file from Directory
		 	if (filetype.equalsIgnoreCase(Config.CSR_FILE_EXT) 
		 		|| filetype.equalsIgnoreCase(Config.CER_FILE_EXT))
		 	{
		 		fcont = FileUtilies.getFilefromDirectory(fileName, filetype);		 		
		 		 		
		 	} else 
		 			throw new Exception("File extension ("+filetype+") is not recognised");			
			
		}		
		else   
		{  // Reading file from Database 
//          User loggedUser =  (User) request.getSession().getAttribute( Config.ATT_SESSION_USER );
			int tmpuser = 1;			
			String SQLstatement = null ;		
	
			 	if (filetype.equalsIgnoreCase(Config.CSR_FILE_EXT))
			 	{
				 	SQLstatement = "SELECT csr_file FROM certificate_req_tab WHERE file_id = ? AND user_id = ?";

			 	}
			 	else if (filetype.equalsIgnoreCase(Config.CER_FILE_EXT))
			 	{
			 		SQLstatement = "SELECT certificate FROM certificate_req_tab WHERE file_id = ?";
		 		
			 	}
			 	else
			 		throw new Exception("File extension ("+filetype+") is not recognised");
			 	
//			 	System.out.println(SQLstatement);
//			 	System.out.println(fileName);
			 	
				Conn = DatSrc.getConnection();	
	        	statement = (PreparedStatement) Conn.prepareStatement(SQLstatement);
	        	statement.setString(1, fileName);
	
	        	if (filetype.equalsIgnoreCase(Config.CSR_FILE_EXT))
	        	{
//		          	statement.setInt(2, loggedUser.id);
		            statement.setInt(2,tmpuser);
	        	}

	        	
	            
	            resultSet = statement.executeQuery();            
	            
	            System.out.println(getRowNumber(resultSet));
	            
	            resultSet.next();	            
	            
	            Blob blob = resultSet.getBlob("csr_file");	            
	            
	            fcont = blob.getBytes(1, (int) blob.length());           
		  	}
		}
	  	  catch (SQLException e) 
	  	   {
			 // TODO Auto-generated catch block
			 e.printStackTrace();
			 throw new SQLException(e.getMessage());			 
		   }	
		finally 
		{
			if (resultSet != null) resultSet.close();	
			if (statement != null) statement.close();	
			if (Conn != null) Conn.close();			
		}
		
		return fcont ;
	}
	
	public boolean updateCERT (byte[] cert , String fId, String xSN ,HttpServletRequest CurrentRequestParam) 
			throws IOException, SQLException
	{
		boolean boolflag = false; 
		Connection Conn = null ;
		PreparedStatement statement = null;

		try
		{
			//		
			//save CRT file in the server manually uploaded by the servlet 
			if (CurrentRequestParam != null) FileUtilies.saveFiletoDirectory(cert, fId ,Config.CER_FILE_EXT);

			// save to the Database
			String SQLStatment = "UPDATE certificate_req_tab SET certificate = ? , cer_serial_number= ?, revoked =? WHERE file_id= ?";
			
			Conn = DatSrc.getConnection();
			
			statement = (PreparedStatement) Conn.prepareStatement(SQLStatment);

			//    	  User connectedUser = (User) request.getSession().getAttribute(Config.ATT_SESSION_USER); // get the current user id 
			// int usrid = 1;// = connectedUser.id ;
			//		  byte[] csrf = Files.readAllBytes(newCertSingReq.getCsrfile().toPath()); // Temp use 		  byte[] csrf = Files.readAllBytes(newCertSingReq.getCsrfile().toPath()); // Temp use 
						
			statement.setBytes(1,cert);
			statement.setString(2,xSN);
			statement.setString(3, RevocationStatus.NOT_REVOKED.name());
			statement.setString(4, fId);
			

			if (statement.executeUpdate() != 0)
			{
				boolflag = true ;	  		  
				statement.close();	
				Conn.close();
				//	  		 SQLStatment = "Select id from certificate_req_tab Where user_id =? and  ";
				//	  		 statement = (PreparedStatement) Conn.prepareStatement(SQLStatment);	  		  

			}		  
		}
		catch (SQLException e)
		{
//			 System.err.println("Got Exception");
			 FileUtilies.DelCertFilefrmDirectory(fId);
			 throw new SQLException(e.getMessage());			
		}
		finally
		{
	  		 if (statement != null) statement.close();	
	  		 if (Conn != null)  Conn.close();
			
		}

		return boolflag ; 
	}
	
	public boolean submitRevoCrtReq (String sn) throws SQLException
	{
		boolean flg = false;		
		Connection Conn = null ;
		PreparedStatement statement = null;
		String SQLStatment ;
		ResultSet resultSet = null;
		
		String test = null;
		
		try
		{
			Conn = DatSrc.getConnection();
			
			SQLStatment = "SELECT revoked FROM certificate_req_tab WHERE cer_serial_number= ?";
			
			statement = (PreparedStatement) Conn.prepareStatement(SQLStatment);
			statement.setString(1, sn);
			
			resultSet = statement.executeQuery(); 
			
			if (resultSet.next())				
				test = resultSet.getString("revoked");
			
			if (!test.equals(RevocationStatus.NOT_REVOKED.name()))
				return flg ;			
			
			SQLStatment = "UPDATE certificate_req_tab "
					    + "SET revoked = ?  WHERE cer_serial_number= ?";
			
			statement.close();
			
			statement = (PreparedStatement) Conn.prepareStatement(SQLStatment);
			
			statement.setString(1,RevocationStatus.REVO_IN_PROGRESS.name());
			statement.setString(2, sn);
						
			if (statement.executeUpdate() != 0)			
				flg = true ;	
			
		}
		catch (SQLException e)
		{
			 throw new SQLException(e.getMessage());			
		}
		finally
		{
	  		 if (statement != null) statement.close();	
	  		 if (Conn != null)  Conn.close();			
		}	
		
		return flg;
	}
	
	// add revoked status for certificate with a given sn
	public boolean RevoCrt (String sn) throws SQLException
	{
		boolean flg = false;		
		Connection Conn = null ;
		PreparedStatement statement = null;
		
		try
		{
			
			// save to the Database
			// need add user id for allowing certificate revocation 
			String SQLStatment = "UPDATE certificate_req_tab "
								+ "SET revoked = ?  WHERE cer_serial_number =?";
			
			Conn = DatSrc.getConnection();
			statement = (PreparedStatement) Conn.prepareStatement(SQLStatment);
			
			statement.setString(1,RevocationStatus.REVOKED.name());
			statement.setString(2, sn);
			
			if (statement.executeUpdate() != 0)
			{
				flg = true ;	  		  
				statement.close();	
				Conn.close();
			}		
			
		}
		catch (SQLException e)
		{
			 throw new SQLException(e.getMessage());			
		}
		finally
		{
	  		 if (statement != null) statement.close();	
	  		 if (Conn != null)  Conn.close();			
		}	
		
		return flg;
	}
	
	public boolean RevoCrt (ArrayList<String> snLst) throws SQLException
	{
		boolean flg = false;		
		Connection Conn = null ;
		PreparedStatement statement = null;
		ArrayList<String> quesNot = new ArrayList<String>();
		try
		{
			for ( String x : snLst) quesNot.add("?");
			// save to the Database
			// need add user id for allowing certificate revocation 
			String SQLStatment = "UPDATE certificate_req_tab "
								+ "SET revoked = ?  WHERE cer_serial_number in ("+StringUtils.join(quesNot, ',')+")";
			// System.out.println(SQLStatment);
			Conn = DatSrc.getConnection();
			statement = (PreparedStatement) Conn.prepareStatement(SQLStatment);
			
			statement.setString(1,RevocationStatus.REVOKED.name());
			for ( int i =0 ; i < snLst.size(); i++)		
				statement.setString(i+2, snLst.get(i));
			
			if (statement.executeUpdate() != 0)
			{
				flg = true ;	  		  
				statement.close();	
				Conn.close();
			}		
			
		}
		catch (SQLException e)
		{
			 throw new SQLException(e.getMessage());			
		}
		finally
		{
	  		 if (statement != null) statement.close();	
	  		 if (Conn != null)  Conn.close();			
		}	
		
		return flg;
	}
	
	
	
	public ArrayList<String> getRevoInprogresseCert () throws SQLException
	{
			
		Connection Conn = null ;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		ArrayList<String> revcrtList = new ArrayList<String>(); 
		
		try
		{
			
			
			// need add user id for allowing certificate revocation 
			String SQLStatment = "Select cer_serial_number from certificate_req_tab "
								+ "WHERE revoked = ? ";
			
			Conn = DatSrc.getConnection();
			
			statement = (PreparedStatement) Conn.prepareStatement(SQLStatment);
			
			statement.setString(1,RevocationStatus.REVO_IN_PROGRESS.name());

			resultSet = statement.executeQuery() ;
			
			while ( resultSet.next() )
			{
				revcrtList.add(resultSet.getString("cer_serial_number"))	; 

			}		
			
		}
		catch (SQLException e)
		{
			 throw new SQLException(e.getMessage());			
		}
		finally
		{
			 if (resultSet != null)resultSet.close();
	  		 if (statement != null) statement.close();	
	  		 if (Conn != null)  Conn.close();			
		}	
		
		return revcrtList;
	}
	
	public ArrayList<String> getCSRNotSubmitToCA () throws SQLException
	{
		
		Connection Conn = null ;		
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		ArrayList<String> flx =new ArrayList<String> ();
		
		try
		{
			
			// get not submitted CSR from the the Database
			 
			String SQLStatment = "SELECT file_id from certificate_req_tab WHERE csr_submit= ?";
			
			Conn = DatSrc.getConnection();
			
			statement = Conn.prepareStatement(SQLStatment);			
			
			statement.setBoolean(1,false);
	
			resultSet = statement.executeQuery();
			
			while (resultSet.next())

			{
					  		  
				flx.add(resultSet.getString("file_id"));

			}			
		}
		catch (SQLException e)
		{
			 throw new SQLException(e.getMessage());			
		}
		finally
		{
	  		 if (resultSet != null)resultSet.close();
			 if (statement != null) statement.close();	
	  		 if (Conn != null)  Conn.close();	  		 
		}	
		
		return flx;
	}
	
	public boolean CSRSubmitCA (String fId) throws SQLException
	{
		boolean flg = false;		
		Connection Conn = null ;
		PreparedStatement statement = null;
		
		try
		{
			
			// save to the Database
			String SQLStatment = "UPDATE certificate_req_tab "
					+ "SET csr_submit = ?  WHERE file_id= ?";
			
			Conn = DatSrc.getConnection();
			statement = (PreparedStatement) Conn.prepareStatement(SQLStatment);
			
			statement.setBoolean(1,true);
			statement.setString(2, fId);
			
			if (statement.executeUpdate() != 0)
			{
				flg = true ;	  		  
				statement.close();	
				Conn.close();
				System.out.println("CSR Submitted field updated!");
				//	  		 SQLStatment = "Select id from certificate_req_tab Where user_id =? and  ";
				//	  		 statement = (PreparedStatement) Conn.prepareStatement(SQLStatment);	  		  

			}	
			
			
		}
		catch (SQLException e)
		{
			 throw new SQLException(e.getMessage());			
		}
		finally
		{
	  		 if (statement != null) statement.close();	
	  		 if (Conn != null)  Conn.close();			
		}	
		
		return flg;
	}
	
	public boolean getAssUserCrtRec(String sn,int usrid)  throws SQLException, Exception
	{
		
		
		Connection Conn = null ;		
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		boolean flg = false;
		
		try
		{
			
			 
			String SQLStatment = "SELECT user_id from certificate_req_tab WHERE cer_serial_number= ? and user_id= ?";
			
			Conn = DatSrc.getConnection();
			
			statement = (PreparedStatement) Conn.prepareStatement(SQLStatment);			
			
			statement.setString(1,sn);
			statement.setInt(2,usrid);
	
			resultSet = statement.executeQuery();
			
			if (resultSet.next())
					flg = true;			
						
		}
		catch (SQLException e)
		{
			 throw new SQLException(e.getMessage());			
		}
		finally
		{
	  		 if (resultSet != null)resultSet.close();
			 if (statement != null) statement.close();	
	  		 if (Conn != null)  Conn.close();	  		 
		}
		
	return flg;
		
	}
	
	
	protected int getRowNumber(ResultSet ReSet) throws SQLException
	{
		int size = 0;   
		if (ReSet != null)   
		  {  
			ReSet.beforeFirst();  
			ReSet.last();  
		     size = ReSet.getRow();
		  }  	
		ReSet.beforeFirst();		
		return size;				
	}	
}
