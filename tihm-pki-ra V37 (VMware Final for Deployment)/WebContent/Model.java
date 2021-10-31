package org.tihm.pki.ra;

import java.util.ArrayList;

import org.tihm.pki.ra.utilities.CSRCRTReader;

public class Model {
	/**
	 * Validate mail address
	 */
//	public Boolean validateEmail( String email ) throws Exception {
//	    if ( email != null && email.trim().length() != 0 ) {
//	        if ( !email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) {
//	            throw new Exception( "You must provide a valid email address" );
//	        }
//	    } else {
//	    	throw new Exception( "You must provide a valid email address" );
//	    }
//	    
//	    return true;
//	}

	/**
	 * Validate a password
	 */
	public Boolean validatePassword( String motDePasse, String confirmation ) throws Exception{
	    if (motDePasse != null && motDePasse.trim().length() != 0 && confirmation != null && confirmation.trim().length() != 0) 
	    {
	        if (!motDePasse.equals(confirmation)) {
	            throw new Exception("Passwords does not match, please check your input passwords");
	        } else if (motDePasse.trim().length() < 8) {
	            throw new Exception("Password must be at least of 8 characters");
	        }
	    } else {
	        throw new Exception("You must confirm your password");
	        
	    }
	    
	    return true;
	}

	/**
	 * Validate a name string
	 */
	public Boolean validateName( String nom, String type) throws Exception {
	    if (nom == "" || nom == null) 
	    {
	        throw new Exception( type+" must be filled");
	    }
		if ( nom != null && nom.trim().length() < 3) {
	        throw new Exception( type+" must be at least of 3 characters" );
	    }
	    
	    return true;
	}
	
	/**
	 * Validate a name string in CSR Records
	 */
	public Boolean validateCSRField( String nom, String type) throws Exception {
	    if (nom == "" || nom == null) 
	    {
	        throw new Exception( type+" must be filled");
	    }
	    else if (type.equals("Country"))
	    {
	    	if ( nom.trim().length() < 1) 
		        
	    		throw new Exception( "Country name  must be at least of 2 characters" );	    	
	    }  
	    else 
	    	if (nom.trim().length() < 3) {
	        
	    		throw new Exception( type+" must be at least of 3 characters");
	    }
	    
	    return true;
	}
	
	/**
	 * Validate the content of uploaded CSR file against input data
	 */	
	public boolean validateFile( byte [] filePara,  CertificateRec cerReqRecPara ) throws Exception 
	{
	    
		if(filePara == null || filePara.length==0  ) 
	    {
	        throw new Exception( "You must upload a Certificate Sign Request file (*.csr)" );
	    }
	    
	    // Validate CSR content
	    
	    CSRCRTReader csrvalidator = new CSRCRTReader();
	    
	    csrvalidator.validateCertificateSigningRequest(filePara, cerReqRecPara);
	    
	    return true;
	}
	/**
	 * Validate the content of uploaded certificate file to input data
	 */	
	public String validateFile2GetSN(byte [] certfile) throws Exception 
	{
		String snx = null;
		
	    if( certfile == null || certfile.length==0 ) 
	    {
	        throw new Exception( "You must upload certificate file ( *.crt" );
	    }		
		
	    CSRCRTReader certcvalidator = new CSRCRTReader();
	    
	    snx = certcvalidator.getSNfromCrt(certfile);	
		
		return snx ;
	}
	
	public ArrayList<String> matchingFields (UserRec x1, UserRec x2)
	{
		ArrayList<String> arrylst = new ArrayList<String>();
		
		if (!(x1.getEmail().equals(x2.getEmail())))	arrylst.add("email");	
		if (!(x1.getFirstname().equals(x2.getFirstname())))  arrylst.add("firstname");
		if (!(x1.getLastname().equals(x2.getLastname())))  arrylst.add("lastname");
		if (!(x1.getOrganization().equals(x2.getOrganization())))  arrylst.add("organization");		
		return arrylst;
		
	}
	
}
