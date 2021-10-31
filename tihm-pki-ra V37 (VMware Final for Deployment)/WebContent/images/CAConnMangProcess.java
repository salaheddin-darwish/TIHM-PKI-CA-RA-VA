package org.tihm.pki.ra.utilities;

import java.util.Date;
import java.util.logging.Logger;


/**
 * @author Salaheddin Darwish @ 16/01/2017  , Salaheddin.Darwish@gmail.com
 *
 */
public class CAConnMangProcess implements Runnable {
	
	static Logger logger = Logger.getLogger(CAConnMangProcess .class.getName());

    public void run(){
    
       Date x = new Date();
       System.out.println("-----------------------------------------------------");
       System.out.println("CA Process running: "+ x.toString());
      
       try {
  	   
    	   CaSSHHandler sshdl = new CaSSHHandler();
    	   
    	   // Check if there are any new Certificate Signing Request already submitted to RA and they need to pass to the CA . 
		   int ix = sshdl.populateCsrFilesToCA();
		   System.out.println("uploaded CSR records at CA: "+ix);
		   
		   // Check the certificate directory at CA for new issued certificates, if yes, then transfer those certificates to RA 
		   ix = sshdl.getCerFileFromCA();
		   System.out.println("uploaded Certificates from CA: "+ix);
		   
		   // Check and update certificate revocations
		   
		   int[] revrec = sshdl.updateRevokStat();
		   
		   System.out.println("A number of revocation request being sent to CA: "+revrec[0]);
		   System.out.println("A number of CRL Entries being updated: "+revrec[1]);
		   System.out.println("-----------------------------------------------------");
		   
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.warning(e.getMessage());
	} 
       
          
       
       
    }
  }