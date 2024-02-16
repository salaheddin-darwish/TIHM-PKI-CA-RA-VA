#!/bin/bash

exec 2>&1

function usage () {
	echo "$0 [Certificate Serial Number]"
	exit 1
}

if [ $# -ne 1 ]
then
	usage
fi

CERT_SN="$1"

SSL_DIR="/etc/ssl"
SSL_CERTS_DIR="$SSL_DIR/ca/newcerts"


# Revoke a particular user's certificate.

openssl ca -config $SSL_DIR/openssl.cnf -revoke $SSL_CERTS_DIR/${CERT_SN}.pem
if [ "$?" != "0" ]; then
    echo  "Revocation Error in ${CERT_SN}" >> $SSL_DIR/ca/rev_errors.txt
    echo "Error Encountered in revoking the certificate"
fi
echo "======================================================================="
echo
echo ">>>>>> ${CERT_SN}.pem is revoked"

# Update the Certificate Revocation list for removing 'user certificates.'

openssl ca -config $SSL_DIR/openssl.cnf -gencrl -out $SSL_DIR/ca/crl/crl.pem
if [ "$?" != "0" ]; then
    echo  "CRL Error in ${CERT_SN}" >> $SSL_DIR/ca/rev_errors.txt 
    echo "Error Encountered in generating CRL"
 
fi
echo ">>>>>> CRL is updated"
echo "======================================================================="
echo " Finishing Certificate Revocation Process" ; 
