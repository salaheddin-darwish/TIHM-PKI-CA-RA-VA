package org.tihm.pki.ra;

import java.io.File;
import java.security.PublicKey;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

//import pki.utilities.KeypairUtility;

public class CertificateRec {
	
	
	protected enum RevocationStatus {
		NOT_REVOKED, REVO_IN_PROGRESS , REVOKED
	}
	
	
	private int id;
	private String certType;
	private String commonName;
	private String country;
	private String stateprovince;
	private String locale ;
	private String organization;
	private String organization_unit;
	
	
	
	private Date date;	
	private String cer_serial_number;
	
	private  byte[] csrfile;
	private  String fileId  ;
	
	public RevocationStatus revokest;
	
	public int assouser ;
	

	
		
	public String getCertType() {
		return certType;
	}



	public void setCertType(String certType) {
		this.certType = certType;
	}



	public int getAssouser() {
		return assouser;
	}



	public void setAssouser(int assouser) {
		this.assouser = assouser;
	}



	public Date getDate() {
		return date;
	}



	public void setDate(Date date) {
		this.date = date;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getCommonName() {
		return commonName;
	}



	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}



	public String getCountry() {
		return country;
	}



	public void setCountry(String country) {
		this.country = country;
	}



	public String getStateprovince() {
		return stateprovince;
	}



	public void setStateprovince(String stateprovince) {
		this.stateprovince = stateprovince;
	}



	public String getLocale() {
		return locale;
	}



	public void setLocale(String locale) {
		this.locale = locale;
	}



	public String getOrganization() {
		return organization;
	}



	public void setOrganization(String organization) {
		this.organization = organization;
	}



	public String getOrganization_unit() {
		return organization_unit;
	}



	public void setOrganization_unit(String organization_unit) {
		this.organization_unit = organization_unit;
	}



	public String getCer_serial_number() {
		return cer_serial_number;
	}



	public void setCer_serial_number(String cer_serial_number) {
		this.cer_serial_number = cer_serial_number;
	}



	public byte[] getCsrfile() {
		return csrfile;
	}



	public void setCsrfile(byte[] csrfile) {
		this.csrfile = csrfile;
	}


	public String getFileId() {
		return fileId;
	}



	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	
//	public String getRevokest() {
//		return this.revokest.name();
//	}

	public RevocationStatus getRevokest() {
		return this.revokest;
	}

//	public void setRevokest(String revokest) {
//		this.revokest = RevocationStatus.valueOf(revokest);
//	}

	public void setRevokest(RevocationStatus revokest) {
		this.revokest = revokest;
	}

	public CertificateRec(String crtype, String commonName, String country, String stateprovince, String locale, String organization,
			String organization_unit, byte[] csrfbyte) 
	{
		super();
		this.certType=crtype;
		this.commonName = commonName;
		this.country = country;
		this.stateprovince = stateprovince;
		this.locale = locale;
		this.organization = organization;
		this.organization_unit = organization_unit;		
		this.date = Calendar.getInstance().getTime();
		this.fileId =  UUID.randomUUID().toString();		
		this.csrfile = csrfbyte.clone();
//		this.revokest= RevocationStatus.valueOf(revokest)
	}


//	public CertificateRec(HttpServletRequest request)
//	{
//		this.commonName = request.getParameter("commonName");
//		this.country = request.getParameter("country");
//		this.stateprovince = request.getParameter("stateprovince");	
//		this.locale = request.getParameter("locale");		
//		this.organization = request.getParameter("organization");
//		this.organization_unit = request.getParameter("organizationUnit");		
//		this.date = Calendar.getInstance().getTime();
//		this.fileId =  UUID.randomUUID().toString();		
//		this.revokest = RevocationStatus.notRevoked;
////	this.setCSRFileWithBytes(csrfbyte);
//		 
//	}
	


	public CertificateRec()
	{
		this.date = Calendar.getInstance().getTime();
		this.fileId =  UUID.randomUUID().toString();
						
	}


	public boolean validate(Model model) throws Exception 
	{
		return
			model.validateCSRField(this.commonName, "Common Name")
			&&
			model.validateCSRField(this.country, "Country")
			&&
			model.validateCSRField(this.stateprovince, "State or Province")
			&&
			model.validateCSRField(this.locale, "Locale or City")			
			&&
			model.validateCSRField(this.organization, "Organization")
			&&
			model.validateCSRField(this.organization_unit, "Organization Unit/Department/Section")						
			&& 
			model.validateFile (this.csrfile,this);
			
	}
	
   public static CertificateRec mapWithDatabase(ResultSet resultSet) throws SQLException, Exception {
        CertificateRec certiRe = new CertificateRec();
//        certiRe.id 					= resultSet.getInt("id");
        certiRe.certType			= resultSet.getString("cert_type");
        certiRe.commonName 			= resultSet.getString("common_name");
        certiRe.country 			= resultSet.getString("country");
        certiRe.stateprovince 		= resultSet.getString("stateprovince");
        certiRe.locale 				= resultSet.getString("locale")	;
        certiRe.organization 		= resultSet.getString("organization");        
        certiRe.organization_unit 	= resultSet.getString("organization_unit");        	
        certiRe.cer_serial_number 	= resultSet.getString("cer_serial_number");
        certiRe.fileId 				= resultSet.getString("file_id");
        certiRe.date				= resultSet.getDate("date");
        certiRe.assouser			= resultSet.getInt("user_id");
        if (resultSet.getString("revoked") != null)        	
        	certiRe.revokest = RevocationStatus.valueOf(resultSet.getString("revoked"));
        	
        return certiRe;
    }
	
	public void setDataForFieldAndValue(String field, String value)
	{
		if(field.equals("commonName"))
			this.commonName = value;
		else if(field.equals("country"))
			this.country = value;
		else if(field.equals("stateprovince"))
			this.stateprovince = value;
		else if(field.equals("locale"))
			this.locale = value;
		else if(field.equals("organization"))
			this.organization = value;
		else if(field.equals("organization_unit"))
			this.organization_unit = value;
		else if(field.equals("certTyperadio"))
			this.certType = value;
	}
	
	
}
