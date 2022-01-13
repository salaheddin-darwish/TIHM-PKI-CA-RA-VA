package org.tihm.pki.ra.csrdaos;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;
import java.sql.Connection;

import org.tihm.pki.ra.Config;
import org.tihm.pki.ra.UserRec;
import org.tihm.pki.ra.utilities.Database;
import org.tihm.pki.ra.utilities.Password; 




public class SecureDAO {
	
	static Logger logger = Logger.getLogger(SecureDAO.class.getName());
	
	public Connection conn ;
	private static DataSource datasource;
	private static Password  passlib  ; // Password library
	
	public SecureDAO()
	{
		
		 try
		    {
			 
			 datasource = Database.getDataSource();	// pooling the database connection			 
			 //System.out.println("Instance of RegSrvDAOHandler-@ "+" Time"+System.currentTimeMillis());	
			 passlib = Password.getPasswordInstance();
	
		    }
		 catch (Exception e )
			{
				   System.err.println("Got an exception!");
				   System.err.println(e.getMessage());
				   e.printStackTrace();
				   logger.log(Level.SEVERE, e.getMessage(), e);
			}
	}
	
	
	
	public UserRec loginUser(String email, String password) throws Exception, SQLException
	{
		Connection dbCon  = null; 
		PreparedStatement statement = null;
		ResultSet result = null;		
		
		UserRec tryLoginUser = new UserRec(null, null,null,email, password,null,-1);

		try {
			
			
			dbCon =  datasource.getConnection();						
			String SQLStament = "SELECT id, firstname, lastname, organization, password,role FROM ra_users_tab WHERE email = ?";
			
			statement = dbCon.prepareStatement(SQLStament);
    		statement.setString(1, email);    		    		
			result = statement.executeQuery();
	    		
			if (result.next()) // a given username has a record 
			{
				tryLoginUser.id = result.getInt("id");
				tryLoginUser.firstname = result.getString("firstname");
				tryLoginUser.lastname = result.getString("lastname");
				tryLoginUser.password = result.getString("password");
				tryLoginUser.organization = result.getString("organization");
				tryLoginUser.role = result.getString("role");
				// check the password input			
				if (passlib.check(password, tryLoginUser.password))					
					{
					  tryLoginUser.setAuthenticated(true);							
					}

				return tryLoginUser;	
			}
		} 
		catch (SQLException e) {
			
			e.printStackTrace();
		   logger.log(Level.SEVERE, e.getMessage(), e);		   
		   throw new SQLException(e.getMessage());
		}
		catch (Exception e) {
			
			e.printStackTrace();
		   logger.log(Level.SEVERE, e.getMessage(), e);
		   throw new Exception(e.getMessage());
		}

		finally 
		{
			
			if (result != null) result.close() ;
			if( statement != null) statement.close();			
			if (dbCon  != null) statement.close();	
			System.out.println("Database Connection is closed");
		}

		return null;
	}
	
	public boolean registerUser(UserRec usrrec) throws Exception	
	{
		boolean flg = false ; 
		Connection dbCon  = null; 
		PreparedStatement statement = null;
	  try
		{
			String SQLStatement = "INSERT INTO ra_users_tab (email, password, firstname, lastname, organization, role) "
					+ "VALUES (?, ?, ?, ?,?,?)";
    		
			dbCon = datasource.getConnection() ;  		
    		
			statement = (PreparedStatement) dbCon.prepareStatement(SQLStatement);
    		
    		String passpro = passlib.getSaltedHash(usrrec.getPassword());	    		
    		
    		statement.setString(1, usrrec.getEmail());
    		statement.setString(2, passpro);
    		statement.setString(3, usrrec.getFirstname());
    		statement.setString(4, usrrec.getLastname());
    		statement.setString(5, usrrec.getOrganization());
    		statement.setString(6, usrrec.getRole());
    		
    		if (statement.executeUpdate() != 0) flg = true ;    
		
		}
		catch (SQLException e) {
			
			e.printStackTrace();
		   logger.log(Level.SEVERE, e.getMessage(), e);		   
		   if (e.getMessage().contains("Duplicate entry")) 
			   throw new SQLException("Your Email or Username already exists, please use add new one"); 
		   else throw new SQLException(e.getMessage());
		}	  
	   
	  catch (Exception e)     		
    		{
			  e.printStackTrace();			 	
			  logger.log(Level.SEVERE, e.getMessage(), e);
			  throw new Exception(e.getMessage());	
			  
			}	
		
		return flg;	
		
	}

	public boolean updateUserDetails(UserRec usrrec ) throws Exception
	
	{  
		boolean flg = false ; 
		Connection dbCon  = null; 
		PreparedStatement statement = null;
	  try
		{
			String SQLStatement = "Update ra_users_tab "
								+ "SET email = ?, firstname = ?, lastname = ?, organization =?"
								+ "Where id =?";
    		
			dbCon = datasource.getConnection() ;  		
			dbCon.setAutoCommit(false); // add lock
			statement = (PreparedStatement) dbCon.prepareStatement(SQLStatement);
    		
//    		String passpro = passlib.getSaltedHash(usrrec.getPassword());	    		
    		
    		statement.setString(1, usrrec.getEmail());
    		statement.setString(2, usrrec.getFirstname());
    		statement.setString(3, usrrec.getLastname());
    		statement.setString(4, usrrec.getOrganization());
    		statement.setInt(5, usrrec.getId());
    		
    		if (statement.executeUpdate() != 0) flg = true ; 
    		
    		dbCon.commit(); // release the lock
		
		}
		catch (SQLException e) {			
			e.printStackTrace();
			dbCon.rollback();
		   logger.log(Level.SEVERE, e.getMessage(), e);		   
		   if (e.getMessage().contains("Duplicate entry")) 
			   throw new SQLException("Your Email or Username exists, please add new one"); 
		   else throw new SQLException(e.getMessage());
		}	  
	   
	  catch (Exception e)    		
    		{
		  	  dbCon.rollback();
		  	  e.printStackTrace();			 	
			  logger.log(Level.SEVERE, e.getMessage(), e);
			  throw new Exception(e.getMessage());	
			  
			}	
		
		return flg;	
		
	}
	
	public boolean changeUserPass(UserRec usrrec) throws Exception
	
	{
		boolean flg = false ; 
		Connection dbCon  = null; 
		PreparedStatement statement = null;
	  try
		{
			String SQLStatement = "Update ra_users_tab "
								+ "SET password = ?"
								+ "Where email =?";
    		
			dbCon = datasource.getConnection() ;  		
    		
			dbCon.setAutoCommit(false); // add lock on the record
			
			statement = (PreparedStatement) dbCon.prepareStatement(SQLStatement);
    		
			@SuppressWarnings("static-access")
			String newPWD = passlib.getSaltedHash(usrrec.getPassword());	    		
    		
    		statement.setString(1, newPWD);
    		statement.setString(2, usrrec.getEmail());
    		
    		if (statement.executeUpdate() != 0) 
    		 {
    			 flg = true ; 
    			 usrrec.password = newPWD;
    		 }
    		
    		dbCon.commit();
		
		}
		catch (SQLException e) {
			
			dbCon.rollback();
			e.printStackTrace();
		   logger.log(Level.SEVERE, e.getMessage(), e);		   
		   throw new SQLException(e.getMessage());
		}	  
	   
	  catch (Exception e)    		
    		{
			  e.printStackTrace();			 	
			  logger.log(Level.SEVERE, e.getMessage(), e);
			  throw new Exception(e.getMessage());	
			  
			}	
		
		return flg;	
		
	}
	
}
