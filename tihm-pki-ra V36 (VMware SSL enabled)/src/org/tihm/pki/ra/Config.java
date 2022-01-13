package org.tihm.pki.ra;

public class Config {
	
	public static final String CSR_DIRECTORY = "/home/salah/ra/csrfiles/";
	public static final String CER_DIRECTORY = "/home/salah/ra/certificates/";
	public static final String CRL_DIRECTORY = "/home/salah/ra/crl/";
	public static final String CSR_FILE_EXT = ".csr";
	public static final String CER_FILE_EXT = ".crt";
	
	
	public static final String ATT_ERRORS  = "errors";
	public static final String ATT_SUCCESS  = "success";
	public static final String ATT_SESSION_USER = "userSession";
	
	public static final String ATT_CSRFILE_PARA = "csrfilepara";
	public static final boolean FILE_DIRECTORY_SOURCE = true;
	
//	public static final String SSH_SFTPCSRWORKINGDIR = "/etc/ssl/ca/csrfiles-ca";
//	public static final String SSH_SFTPCRTWORKINGDIR = "/etc/ssl/ca/newcerts-ra";
//	public static final String SSH_SFTPCRLWORKINGDIR = "/etc/ssl/ca/crl";
//	public static final String SSH_PRVKEY_SOURCE =  "/home/salah/.ssh/id_rsa.pem";
//	public static final String SSH_KNOWNHOSTS_FILE= "/home/salah/.ssh/known_hosts";
//	public static final String SSH_REMOTE_MACHINE_ADDR = "192.168.56.103";
//	public static final String SSH_REMOTE_MACHINE_USERNAME = "salah";
	
	public static final String SSH_SFTPCSRWORKINGDIR = "/home/ca-admin/ca/csrfiles-ca";
	public static final String SSH_SFTPCRTWORKINGDIR = "/home/ca-admin/ca/newcerts-ra";
	public static final String SSH_SFTPCRLWORKINGDIR = "/home/ca-admin/ca/crl";
	public static final String SSH_PRVKEY_SOURCE =  "/home/salah/.ssh/id_rsa";
	public static final String SSH_PRVKEY_PASS =  "ssh-ra-ca";
	public static final String SSH_KNOWNHOSTS_FILE= "/home/salah/.ssh/known_hosts";
	public static final String SSH_REMOTE_MACHINE_ADDR = "10.201.2.5";
	public static final String SSH_REMOTE_MACHINE_USERNAME = "ca-admin";
	
	public static final int    SSH_WORKING_PORT = 22;	
	public static final int    MAX_LOGIN_FAILURE_ATTEMPTS = 5 ;
	
}