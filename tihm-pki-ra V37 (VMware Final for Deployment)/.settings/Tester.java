package org.tihm.pki.ra.utilities;

import java.io.File;
import java.io.IOException;
import java.security.cert.CertificateException;

import org.apache.commons.io.FileUtils;
import org.tihm.pki.ra.CertificateRec;
import org.tihm.pki.ra.Config;

public class Tester {

	public Tester() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws CertificateException, Exception {
		
		// TODO Auto-generated method stub
		
		File newFile = new File(args[0]);
		
		byte [] cerArry = FileUtils.readFileToByteArray(newFile);	

		CSRCRTReader certcvalidator = new CSRCRTReader();			    

		String snx = certcvalidator.getSNfromCrt(cerArry);
		
		System.out.println(snx);
		CertificateRec x = certcvalidator.getDNandSNFromCRT(cerArry);
		System.out.println(x.getCommonName()+"  "+x.getCountry()+" "+x.getOrganization()+" "+x.getOrganization_unit()+
				" "+x.getStateprovince()+" "+x.getLocale()+" "+x.getCer_serial_number());
	}

}
