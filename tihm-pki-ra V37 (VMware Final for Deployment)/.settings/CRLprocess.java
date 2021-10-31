package org.tihm.pki.ra.utilities;
import java.io.FileInputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLEntry;
import java.util.Iterator;
import java.util.Set;

public class CRLprocess {
  public static void main(String[] args) throws Exception {
    CertificateFactory cf = CertificateFactory.getInstance("X.509");
    FileInputStream in = new FileInputStream(args[0]);
    X509CRL crl = (X509CRL) cf.generateCRL(in);
    Set s = crl.getRevokedCertificates();
    if (s != null && s.isEmpty() == false) {
      Iterator t = s.iterator();
      while (t.hasNext()) {
        X509CRLEntry entry = (X509CRLEntry) t.next();
        System.out.println("serial number = " + entry.getSerialNumber().toString(16));
        System.out.println("revocation date = " + entry.getRevocationDate());
        System.out.println("extensions = " + entry.hasExtensions());
      }
    }
    in.close();
  }
}