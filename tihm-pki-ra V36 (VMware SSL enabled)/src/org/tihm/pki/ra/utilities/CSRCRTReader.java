package org.tihm.pki.ra.utilities;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.Security;
import java.security.cert.CRLException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLEntry;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.pkcs.PKCSException;
import org.tihm.pki.ra.CertificateRec;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.PEMWriter;
import org.bouncycastle.operator.DefaultSignatureAlgorithmIdentifierFinder;
import org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder;

/**
 * @author salaheddin 
 *
 */
public class CSRCRTReader {


	 // Constants representing the ASN1ObjectIdentifier's
	 private final String COUNTRY = "2.5.4.6";
	 private final String STATE = "2.5.4.8";
	 private final String LOCALE = "2.5.4.7";
	 private final String ORGANIZATION = "2.5.4.10";
	 private final String ORGANIZATION_UNIT = "2.5.4.11";
	 private final String COMMON_NAME = "2.5.4.3";
	
	 
    public boolean validateCertificateSigningRequest(byte[] f , CertificateRec CerRec) throws Exception, IOException, PKCSException 
	{
	   	
	PKCS10CertificationRequest csr = convertPemToPKCS10CertificationRequest(f);
	
	X500Name x500Name = csr.getSubject();	 
	 
	 if (!(CerRec.getCommonName().equalsIgnoreCase(getX500Field( COMMON_NAME , x500Name))))
	 {
		 throw new Exception("Common Name Input doesn not match the Common Name value in the CSR File! "
		 		+ "("+CerRec.getCommonName()+" != "+getX500Field( COMMON_NAME , x500Name)
		 		+"), please check input value of Field Common Name");
	 }
	 else if (!CerRec.getCountry().equalsIgnoreCase(getX500Field(COUNTRY, x500Name)))
	 {
		 throw new Exception("Country Input doesn not match Country value in the CSR File! "
			 		+ "("+CerRec.getCountry()+" != "+getX500Field( COUNTRY , x500Name)
			 		+"),please check input value of Field Country");
	 }
	 else if (!CerRec.getStateprovince().equalsIgnoreCase(getX500Field(STATE, x500Name)))
	 {
		 throw new Exception("State Province Input doesn not match State/Province value in the CSR File! "
			 		+ "("+CerRec.getStateprovince()+" != "+getX500Field( STATE , x500Name)
			 		+"), please check input value of Field State/Province ");
		 
	 }
	 else if (!CerRec.getLocale().equalsIgnoreCase(getX500Field(LOCALE, x500Name)))
	 {
		 throw new Exception("Locale/City Input doesn not match Locale/City value in the CSR File! "
			 		+ "("+CerRec.getLocale()+" != "+getX500Field(LOCALE , x500Name)
			 		+"), please check input value of Field Locale/City");
		 
	 }
	 else if (!CerRec.getOrganization().equalsIgnoreCase(getX500Field(ORGANIZATION, x500Name)))
	 {
		 throw new Exception("Organization Input doesn not match Organization in the CSR File! "
			 		+ "("+CerRec.getOrganization()+ " != " +getX500Field(ORGANIZATION, x500Name)
			 		+"), please check input value of Field Organization name");
		 
	 }
	 else if (!CerRec.getOrganization_unit().equalsIgnoreCase(getX500Field(ORGANIZATION_UNIT, x500Name)))
	 {
		 throw new Exception("Organization Unit/Department Input doesn not match Organization Unin the CSR File! "
			 		+ "("+CerRec.getOrganization_unit()+"!="+getX500Field(ORGANIZATION_UNIT, x500Name)
			 		+"), please check input value of Field Organization Unit/Dept./Sec.name");		 
	 }

	 return true ;
	 
	 }
	 
	// Method to get specific field from the X500Name Subject
	 
	private String getX500Field(String asn1ObjectIdentifier, X500Name x500Name) 
	{
		
		RDN[] rdnArray = x500Name.getRDNs(new ASN1ObjectIdentifier(asn1ObjectIdentifier));
		
		String retVal = null;
		
		for (RDN item : rdnArray) 
		{
		 retVal = item.getFirst().getValue().toString();
		}
		
		return retVal;		
	 }
	 
	// Method to convert PEM to PKCS10CertificationRequest
	 
	@SuppressWarnings("deprecation")
	private PKCS10CertificationRequest convertPemToPKCS10CertificationRequest(byte[] pem) throws PKCSException {
	 
	 Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	 PKCS10CertificationRequest csr = null;
	 ByteArrayInputStream pemStream = null;
	 
	 try 
	 {
		 String teststr = new String(pem, "UTF-8"); // used to check the Unicode of the byte array 
		 pemStream = new ByteArrayInputStream(pem);
		 Reader pemReader = new BufferedReader(new InputStreamReader(pemStream));
		 
		 @SuppressWarnings("resource")		 
		 PEMParser pemParser = new PEMParser(pemReader);

		 Object parsedObj = pemParser.readObject();
		 System.out.println("PemParser returned: " + parsedObj);

		 if (parsedObj instanceof PKCS10CertificationRequest) 
		 {

			 csr = (PKCS10CertificationRequest) parsedObj;	

			 
			 //	 AlgorithmIdentifier sigAlgId = new DefaultSignatureAlgorithmIdentifierFinder().find("SHA1withRSA");
			 //	 if (csr.isSignatureValid(new JcaContentVerifierProviderBuilder().setProvider().build(csr.getSubjectPublicKeyInfo().getPublicKey())))
			 //		System.err.println("Signature is valid");
			 //	else System.err.println("Signature is invalid");

		 } 
	 }catch (UnsupportedEncodingException ex) {

			 System.err.println("UnsupportedEncodingException, convertPemToPublicKey");
			 ex.printStackTrace();

	} catch (IOException ex) {
			 System.err.println("IOException, convertPemToPublicKey");
			 ex.printStackTrace();
		 }
	 
	 
	return csr;
	 }
	
	/**
	 * To get the serial number from certificate 
	 */
    public String getSNfromCrt (byte [] crtf) throws CertificateException, Exception
    {
    	String sn = null;
    	
    	CertificateFactory cf =  CertificateFactory.getInstance("X509"); 
    	
    	InputStream pemInputStream = new ByteArrayInputStream(crtf);
    	
		X509Certificate c = (X509Certificate) cf.generateCertificate(pemInputStream);
						
    	sn = c.getSerialNumber().toString(16);
    	
    	return sn;    	
    }
    
	/**
	 * To get the distinguished name fields and serial number from certificate
	 */
    public CertificateRec getDNandSNFromCRT(byte [] crtf) throws CertificateException {

        
    	String dn = null;
    	String  o = null; 
    	
    	CertificateFactory cf =  CertificateFactory.getInstance("X509");     	
    	InputStream pemInputStream = new ByteArrayInputStream(crtf);   	
		X509Certificate c = (X509Certificate) cf.generateCertificate(pemInputStream);		
		
		CertificateRec tmpVer = new CertificateRec();		
		tmpVer.setCer_serial_number(c.getSerialNumber().toString(16));		
		dn = c.getSubjectDN().toString();	
    	
    	String trimmeddn = dn.trim();
//    	System.out.println(trimmeddn);
        
        StringTokenizer st = new StringTokenizer(trimmeddn, ",=");
        while (st.hasMoreTokens()) {
            o = st.nextToken();
            //System.out.println("Next Token O= "+o);
            if (o.trim().equalsIgnoreCase("CN")) {
            	tmpVer.setCommonName(st.nextToken());
            }
            else if (o.trim().equalsIgnoreCase("C")) {
            	tmpVer.setCountry(st.nextToken());
            }
            else if (o.trim().equalsIgnoreCase("ST")) {
            	tmpVer.setStateprovince(st.nextToken());
            }
            else  if (o.trim().equalsIgnoreCase("L")) {
            	tmpVer.setLocale(st.nextToken());
            }
             else  if (o.trim().equalsIgnoreCase("O")) {
                tmpVer.setOrganization(st.nextToken());
             }
             else  if (o.trim().equalsIgnoreCase("OU")) {
                 tmpVer.setOrganization_unit(st.nextToken());
             }
            //System.out.println("Next Token cn= "+cn);
        }
        
        return tmpVer;
    } //getCNFromDN
    
   public ArrayList<String> getSNLfromCRL (byte[] crlf) throws CertificateException, CRLException
   {
	   ArrayList<String> SNList = new ArrayList<String>(); 
	   
	   
	    CertificateFactory cf = CertificateFactory.getInstance("X.509");	   
	   
	    InputStream pemInputStream = new ByteArrayInputStream(crlf);
	    
	    X509CRL crl = (X509CRL) cf.generateCRL(pemInputStream);
	    
	    // crl.verify(key);
	    
	    Set s = crl.getRevokedCertificates();
	    
	    if (s != null && s.isEmpty() == false) 
	    {
	      Iterator t = s.iterator();
	      while (t.hasNext()) {
	        X509CRLEntry entry = (X509CRLEntry) t.next();	        
//	        System.out.println("serial number = " + entry.getSerialNumber().toString());
//	        System.out.println("revocation date = " + entry.getRevocationDate());
//	        System.out.println("extensions = " + entry.hasExtensions());
	        SNList.add(entry.getSerialNumber().toString(16));
	      }
	    }  
	   
	   return  SNList;
   }
    
    
    

}
