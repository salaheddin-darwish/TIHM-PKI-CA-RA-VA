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

SSL_DIR="/home/ca-admin/ca"
SSL_CERTS_DIR="$SSL_DIR/newcerts"


# Revoke a particular user's certificate.

openssl ca -config $SSL_DIR/openssl.cnf -revoke $SSL_CERTS_DIR/${CERT_SN}.pem -passin pass:02-x....x
if [ "$?" != "0" ]; then
    echo  "Revocation Error in ${CERT_SN}" >> $SSL_DIR/rev_errors.txt
    echo "Error Encountered in revoking the certificate"
fi
echo "======================================================================="
echo
echo ">>>>>> ${CERT_SN}.pem is revoked"

# Update the Certificate Revocation list for removing 'user certificates.'

openssl ca -config $SSL_DIR/openssl.cnf -gencrl -out $SSL_DIR/crl/crl.pem -passin pass:02-x....x
if [ "$?" != "0" ]; then
    echo  "CRL Error in ${CERT_SN}" >> $SSL_DIR/rev_errors.txt 
    echo "Error Encountered in generating CRL"
 
fi
echo ">>>>>> CRL is updated"

# update new information to the VA

ssh va-admin@10.201.2.6 '~/va/update_va.sh'
if [ "$?" != "0" ]; then
    echo  "$now: Error in conntecting to VA while calling revocation scripts - ErrNo($?)!? " >> /home/ca-admin/ca/errors.txt
    echo "======================================================================="
fi
echo "======================================================================="
echo " Finishing Certificate Revocation Process" ; 
