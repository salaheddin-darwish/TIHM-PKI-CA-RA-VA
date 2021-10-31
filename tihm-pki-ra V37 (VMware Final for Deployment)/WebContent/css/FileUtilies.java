/**
 * 
 */
package org.tihm.pki.ra.utilities;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.tihm.pki.ra.Config;

/**
 * @author Salaheddin Darwish @ 06/01/2017
 *
 */
public class FileUtilies {

	/**
	 * 
	 */
	
	private static final Logger LOGGER = Logger.getLogger( FileUtilies.class .getName());
	
	public FileUtilies() {
		// TODO Auto-generated constructor stub
	}
	
	public static boolean saveFiletoDirectory (byte[] fileContent, String fname, String fext) throws IOException
	{
		String FSrName = null ;
		
		if (fext.equalsIgnoreCase(Config.CSR_FILE_EXT))
		{
		
		FSrName =  Config.CSR_DIRECTORY+fname+Config.CSR_FILE_EXT;
		}
		else if (fext.equalsIgnoreCase(Config.CER_FILE_EXT))
		{
			
			FSrName =  Config.CER_DIRECTORY+fname+Config.CER_FILE_EXT;
		
		}			
		else   throw new IOException("File extension ("+fext+") is not recognised");
			
		
		File fvar = new File (FSrName);				
  
		FileUtils.writeByteArrayToFile(fvar, fileContent);			
		
		LOGGER.info(fname+fext+" is already stored" );				
		
		return true;		
	}
	
	public static boolean DelCSRFilefrmDirectory (String fname) throws IOException
	{
		
		String FSrName =  Config.CSR_DIRECTORY+fname+Config.CSR_FILE_EXT;	
		
		File fvar = new File (FSrName);				
  
		FileUtils.deleteQuietly(fvar);
			
		LOGGER.info(fname+Config.CSR_FILE_EXT+" is already deleted" );	
		
		return true;
		
	}
	
	public static boolean DelCertFilefrmDirectory (String fname) throws IOException
	{
		
		String FSrName =  Config.CER_DIRECTORY+fname+Config.CER_FILE_EXT;	
		
		File fvar = new File (FSrName);			
  
		FileUtils.deleteQuietly(fvar);
			
		LOGGER.info(fname+Config.CER_FILE_EXT+" is already deleted" );	
		
		return true;
		
	}
	
	
	
	public static byte [] getFilefromDirectory(String fname, String fext) throws IOException
	{
		
		byte[] filcont = null ;
		
		String FSrName = null ;
		
		if (fext.equalsIgnoreCase(Config.CSR_FILE_EXT))
		{
		
		FSrName =  Config.CSR_DIRECTORY+fname+Config.CSR_FILE_EXT;
		}
		else if (fext.equalsIgnoreCase(Config.CER_FILE_EXT))
		{
			
			FSrName =  Config.CER_DIRECTORY+fname+Config.CER_FILE_EXT;
		
		}
		else if (fext.equalsIgnoreCase(".pem")) // crl download
		{
		
			FSrName = Config.CRL_DIRECTORY+fname+fext;		
		
		}
		else   throw new IOException("File extension ("+fext+") is not recognised");
			
		File fvar = new File (FSrName);				
  
		filcont = FileUtils.readFileToByteArray(fvar);					
		
		LOGGER.info(fname+fext+" is already obtained" );			
		
		return filcont;
		
	}

}
