/**
 * 
 */
package org.tihm.pki.ra.utilities;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Vector;

import org.apache.commons.io.FileUtils;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.UserInfo;

import org.apache.commons.lang3.StringUtils;

import org.tihm.pki.ra.Config;
import org.tihm.pki.ra.Model;
import org.tihm.pki.ra.csrdaos.CSRProcessDAO;

/**
 * @author Salaheddin Darwish @ 13/01/2017
 *
 */
public class CaSSHHandler {

	/**
	 * 
	 */
	private JSch jsch;
	
	
	public CaSSHHandler() throws Exception 
	{
		// TODO Auto-generated constructor stub
		
		
		jsch = new JSch();		
    	
		try {
		    // SSH initialisation fo PublicKey Authentication 
    		//jsch.addIdentity(Config.SSH_PRVKEY_SOURCE);
    		jsch.addIdentity(Config.SSH_PRVKEY_SOURCE,Config.SSH_PRVKEY_PASS);
			jsch.setKnownHosts(new FileInputStream(Config.SSH_KNOWNHOSTS_FILE));
			
    	} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} 
    	 catch (IOException e)
    	 {
    		 e.printStackTrace();
    		 throw new Exception(e.getMessage());   		 
    		 
    	 }	
		
	}
	
	

	/**
	 * @param args
	 * @throws Exception 
	 */
//	public static void main(String[] args) throws Exception {
//		// TODO Auto-generated method stub
		
//		ArrayList<String> fnames = new  ArrayList<String>();
//		
//		fnames.add("85eb8cba-870b-4ea9-ab08-5638468980ea");
//		fnames.add("dd05199c-519e-476d-9bf2-174852aea28c");
//		fnames.add("ee2bf051-7d24-4d1f-8bbd-8080bd6de7f6");
		
//		CaSSHHandler x = new CaSSHHandler ();
//		
//		x.updateRevokStat();
//		int i = x.populateCsrFilesToCA(fnames);		
//		System.out.println("The number of files alread uploaded: "+i);
//		
//		int i = x.getCerFileFromCA();	
//		System.out.println("The number of files already downloaded: "+i);
//		
//	    
//	}
	
	public int populateCsrFilesToCA(ArrayList<String>  fileLst) throws Exception	
	{
		
		int csrflenum = 0 ;
		ChannelSftp  sftpChannel = null;
		Session session = null;
		try {
			 
			 if (jsch != null)
				 session = jsch.getSession(Config.SSH_REMOTE_MACHINE_USERNAME, Config.SSH_REMOTE_MACHINE_ADDR, Config.SSH_WORKING_PORT);
			 else throw new Exception("No SSH avaiable ");	

			 System.out.println("Establishing Connection...");
			 session.connect();
			 System.out.println("Connection established.");
			 System.out.println("Creating SFTP Channel.");		
			 sftpChannel = (ChannelSftp) session.openChannel("sftp");
			 sftpChannel.connect();		
			 System.out.println("SFTP Channel created.");					
			 
			 sftpChannel.cd(Config.SSH_SFTPCSRWORKINGDIR);
			 
			 CSRProcessDAO ds = new CSRProcessDAO(); 	
			
			for (String filename: fileLst )
			{	
				
				String filepath = Config.CSR_DIRECTORY+ filename+Config.CSR_FILE_EXT;								
				File f = new File(filepath);	        
				sftpChannel.put(new FileInputStream(f), f.getName());				
				System.out.println("Successful file uploaded to CA: "+ filepath );								
    			ds.CSRSubmitCA(filename);
				System.out.println("Submittion status updated: "+ filepath );
				csrflenum++;
			 } 
			
			
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				if ( sftpChannel != null)  sftpChannel.disconnect();
				if (session != null) session.disconnect();
				throw new Exception(e.getMessage());
				
			} catch (SftpException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				if ( sftpChannel != null) sftpChannel.disconnect();				
				if (session != null) session.disconnect();
				throw new Exception(e.getMessage());
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				if ( sftpChannel != null)  sftpChannel.disconnect();
				if (session != null) session.disconnect();
				throw new Exception(e.getMessage());
			}
		finally 
		{
			if ( sftpChannel != null)  
			{
				sftpChannel.disconnect();
				sftpChannel.exit();
				sftpChannel.quit();
				System.out.println("SFTP Channel is disconnected");
			}

			if (session != null) session.disconnect();

		}
		
		return csrflenum;
		
	}
	
	public int populateCsrFilesToCA() throws Exception	
	{
		
		int csrflenum = 0 ;
		ChannelSftp  sftpChannel = null;
		Session session = null;
		ArrayList<String>  csrfileLst = new ArrayList<String>();
		CSRProcessDAO ds = new CSRProcessDAO(); 		
		
		try {
			 
			csrfileLst = ds.getCSRNotSubmitToCA();
			
			if (csrfileLst.size()== 0) 
				{
				 
				  System.out.println(" No new CSR to submit to CA");
				  return csrflenum ;
				} // not submitted 
			
			 if (jsch != null)
				 session = jsch.getSession(Config.SSH_REMOTE_MACHINE_USERNAME, Config.SSH_REMOTE_MACHINE_ADDR, Config.SSH_WORKING_PORT);
			 else throw new Exception("No SSH avaiable ");	

			 System.out.println("Establishing Connection...");
			 session.connect();
			 System.out.println("Connection established.");
			 System.out.println("Creating SFTP Channel.");		
			 sftpChannel = (ChannelSftp) session.openChannel("sftp");
			 sftpChannel.connect();		
			 System.out.println("SFTP Channel created.");					
			 
			 sftpChannel.cd(Config.SSH_SFTPCSRWORKINGDIR);
			 
			
			for (String filename: csrfileLst )
			{	
				
				String filepath = Config.CSR_DIRECTORY+ filename+Config.CSR_FILE_EXT;								
				File f = new File(filepath);	        
				sftpChannel.put(new FileInputStream(f), f.getName());				
				System.out.println("Successful file uploaded to CA: "+ filepath );								
    			ds.CSRSubmitCA(filename);
				System.out.println("Submittion status updated: "+ filepath );
				csrflenum++;
			 } 
			
			
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				if ( sftpChannel != null)  sftpChannel.disconnect();
				if (session != null) session.disconnect();
				throw new Exception(e.getMessage());
				
			} catch (SftpException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				if ( sftpChannel != null) sftpChannel.disconnect();				
				if (session != null) session.disconnect();
				throw new Exception(e.getMessage());
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				if ( sftpChannel != null)  sftpChannel.disconnect();
				if (session != null) session.disconnect();
				throw new Exception(e.getMessage());
			}
		finally 
		{
			if ( sftpChannel != null)  
			{
				sftpChannel.disconnect();
				sftpChannel.exit();
				sftpChannel.quit();
				System.out.println("SSH Channel is disconnected");
			}

			if (session != null) session.disconnect();

		}
		
		return csrflenum;
		
	}
	
	public int getCerFileFromCA () throws Exception
	{
		
		int cerfilenum = 0 ;
		Session session = null;
		ChannelSftp sftpCh = null;		
		CSRProcessDAO ds = new CSRProcessDAO(); 
		
		try {
			
			if (jsch != null)
				session = jsch.getSession(Config.SSH_REMOTE_MACHINE_USERNAME, Config.SSH_REMOTE_MACHINE_ADDR, Config.SSH_WORKING_PORT);
			else throw new Exception("No SSH avaiable ");	

			System.out.println("Establishing Connection...");
			session.connect();
			System.out.println("Connection established.");
			System.out.println("Creating SFTP Channel.");		
			sftpCh = (ChannelSftp) session.openChannel("sftp");
			sftpCh.connect();		
			System.out.println("SFTP Channel created.");

//			sftpCh = createSftpSession();
			
			sftpCh.cd(Config.SSH_SFTPCRTWORKINGDIR);
			
			//list existing certificate files in the directory at CA.
			Vector<ChannelSftp.LsEntry> list = sftpCh.ls("*"+Config.CER_FILE_EXT); // dir all crt files in the working directory at CA
			
			for(ChannelSftp.LsEntry entry : list) 
			{

				System.out.println(entry.getFilename()); 

				File newFile = new File(Config.CER_DIRECTORY+entry.getFilename());					

				InputStream inpStrm = sftpCh.get(entry.getFilename());

				FileUtils.copyToFile(inpStrm , newFile);

				byte [] cerArry = FileUtils.readFileToByteArray(newFile);
				
				if (cerArry.length== 0 ) 
				{
					newFile.delete();
					throw new Exception ("Failed to obtain CERT from CA, check file "+entry+"at CA") ;
					
				}

				CSRCRTReader certcvalidator = new CSRCRTReader();			    

				String snx = certcvalidator.getSNfromCrt(cerArry);	

				String flid = StringUtils.removeEnd(entry.getFilename(),Config.CER_FILE_EXT);

				if (!(ds.updateCERT(cerArry, flid, snx, null)))
					throw new Exception("Fail to save the Certificate! in the database");
				
				// remove from CA Directory 
				sftpCh.rm(entry.getFilename()); // 	
				cerfilenum++;
			}			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if (sftpCh  != null) sftpCh .disconnect();
			if (session != null)session.disconnect();
			throw new Exception(e.getMessage());
		} catch (SftpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if (sftpCh  != null) sftpCh .disconnect();
			if (session != null)session.disconnect();
			throw new Exception(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if (sftpCh  != null) sftpCh .disconnect();
			if (session != null)session.disconnect();
			throw new Exception(e.getMessage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if (sftpCh  != null) sftpCh .disconnect();
			if (session != null)session.disconnect();
			
			throw new Exception(e.getMessage());
		}
		finally
		{
			if (sftpCh != null)
				{
				 sftpCh .disconnect();
				 sftpCh.exit();
				 sftpCh.quit();
				 System.out.println("SFTP is closed");				 
				}
			
			if (session != null) session.disconnect();				
		}	
		
		return cerfilenum;	
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	/**
	 * @return
	 * @throws Exception
	 */
	public int [] updateRevokStat() throws Exception
	{  
		
		int[] revocrtRec = new int[2]; 
		revocrtRec[0] = 0; // Number of revocation calls 
		revocrtRec[1] = 0;// Number of revocation crl entries  
	
		Session SSHsession = null;
		ChannelSftp sftpCh = null;
		ChannelExec channelExec = null;
		CSRProcessDAO ds = new CSRProcessDAO(); 
		ArrayList<String>  revokcertList = new ArrayList<String>();
		try {

			revokcertList = ds.getRevoInprogresseCert();

			if (jsch != null)
				SSHsession = jsch.getSession(Config.SSH_REMOTE_MACHINE_USERNAME, Config.SSH_REMOTE_MACHINE_ADDR, Config.SSH_WORKING_PORT);
			else throw new Exception("No SSH avaiable ");				

			if (revokcertList.size() != 0) 
			{
				System.out.println("Establishing Connection...");
				SSHsession.connect();
				System.out.println("Connection established.");
				System.out.println("Creating Exec Channel");		


				channelExec = (ChannelExec)SSHsession.openChannel("exec");

				for (String certsernum : revokcertList)
				{	

					// Gets an InputStream for this channel. All data arriving in as messages from the remote side can be read from this stream.
					InputStream in = channelExec.getInputStream();

					//			String crtSN =  "12897903573969758653";
					//String scriptFileName ="sudo /etc/ssl/revoke_cert.sh "+certsernum; 
					String scriptFileName ="/home/ca-admin/ca/revoke_cert.sh "+certsernum; 
					// Set the command that you want to execute
					// In our case its the remote shell script
					channelExec.setCommand(scriptFileName);
					//	         channelExec.setCommand("ls");
					// Execute the command
					channelExec.connect();         

					// Read the output from the input stream we set above
					BufferedReader reader = new BufferedReader(new InputStreamReader(in));

					String line;

					//Read each line from the buffered reader and add it to result list
					// You can also simple print the result here 
					while ((line = reader.readLine()) != null)
					{
						System.out.println(line);
					}

					//	         // Read the output from the input stream we set above
					//	         BufferedReader reader1 = new BufferedReader(new InputStreamReader(channelExec.getErrStream()));
					//	        
					//	         String line1;
					//	         
					//	         //Read each line from the buffered reader and add it to result list
					//	         // You can also simple print the result here 
					//	         while ((line1 = reader1.readLine()) != null)
					//	         {
					//	        	 System.out.println(line1);
					//	         }

					//retrieve the exit status of the remote command corresponding to this channel
					int exitStatus = channelExec.getExitStatus();

					if(exitStatus < 0){
						System.out.println("Done for revoking certificate with serial number:"+certsernum+", but exit status not set!");					
					}
					else if(exitStatus > 0){
						System.out.println("Done for revoking certificate with serial number:"+certsernum+",but with error!:"+exitStatus);
					}
					else{
						System.out.println("Done! for revoking certificate with serial number:"+certsernum);				
					}		

					revocrtRec[0]++;
				}
			
			
			}
			else System.out.println("No outsanding requests of certificate revocation need to be sent to CA");
				 
			
	         //Get from CA the newly updated CRL ;			
			if (!SSHsession.isConnected()) SSHsession.connect();
			System.out.println("Creating SFTP Channel.");		
			sftpCh = (ChannelSftp) SSHsession.openChannel("sftp");
			sftpCh.connect();	
			System.out.println("SFTP Channel created.");

//			sftpCh = createSftpSession();
			
			sftpCh.cd(Config.SSH_SFTPCRLWORKINGDIR);
			
			//list existing certificate files in the directory at CA.
			Vector<ChannelSftp.LsEntry> list = sftpCh.ls("*.pem"); // dir all crt files in the working directory at CA
			
			if (list.size()==0) System.out.println("No CRL avaiable!");
			
			for(ChannelSftp.LsEntry entry : list) 
			{

				System.out.println(entry.getFilename()+" is obtained from CA"); 

				File newFile = new File(Config.CRL_DIRECTORY+entry.getFilename());					

				InputStream inpStrm = sftpCh.get(entry.getFilename());

				FileUtils.copyToFile(inpStrm , newFile);

				byte [] crlArry = FileUtils.readFileToByteArray(newFile);	

				CSRCRTReader crlreader = new CSRCRTReader();
				
				//System.out.println(crlArry.length);

				ArrayList<String> snxList = crlreader.getSNLfromCRL(crlArry);
				
				System.out.println(snxList.toString());
				
				if (snxList.size() ==0) continue;
				
				// update databse records with new revoked certificates obtained from downloaded CRL; 
				if (!ds.RevoCrt (snxList))
				{
					// System.err.println(snxList);
					throw new Exception("Error in Updating the revocation list in the database, No certificate serial numbers are avaliable !"+snxList.toString());
				}
				
				revocrtRec[1]=+snxList.size();
//				for (String snCRlEntry :snxList)
//				{
//					if (!ds.RevoCrt(snCRlEntry))
//						throw new Exception("Error in Updating the revocation list in the database!");					
//				}					
	
			}		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if (channelExec != null) channelExec.disconnect();
			if (sftpCh  != null) sftpCh .disconnect();
			if (SSHsession != null)SSHsession.disconnect();
			throw new Exception(e.getMessage());
			
		} catch (SftpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if (channelExec != null) channelExec.disconnect();
			if (sftpCh  != null) sftpCh .disconnect();
			if (SSHsession != null)SSHsession.disconnect();
			throw new Exception(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if (channelExec != null) channelExec.disconnect();
			if (sftpCh  != null) sftpCh .disconnect();
			if (SSHsession != null)SSHsession.disconnect();
			throw new Exception(e.getMessage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if (channelExec != null) channelExec.disconnect();
			if (sftpCh  != null) sftpCh .disconnect();
			if (SSHsession != null)SSHsession.disconnect();			
			throw new Exception(e.getMessage());
		}
		finally
		{  //Safely disconnect channel and disconnect session. If not done then it may cause resource leak
			if (channelExec != null) { channelExec.disconnect(); System.out.println("Shell is closed");};
			if (sftpCh != null)
				{
				 sftpCh .disconnect();
				 sftpCh.exit();
				 sftpCh.quit();
				 System.out.println("SFTP is closed");				 
				}
			
			if (SSHsession != null) SSHsession.disconnect();
		
		}
		
		return revocrtRec; 
	}
}
	
	
	
