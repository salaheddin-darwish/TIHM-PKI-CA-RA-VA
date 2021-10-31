#!/bin/bash

echo " Start Issuing the certificates for outsanding requests" ; 
shopt -s nullglob
for file in /etc/ssl/ca/csrfiles-ca/*.csr 	
do
 
printf 'y\ny\n' | openssl ca -in $file  -out /etc/ssl/ca/newcerts-ra/$(basename -s .csr $file).crt -extensions v3_OCSP -config /etc/ssl/openssl.cnf -policy policy_anything 
if [ "$?" != "0" ]; then
    echo  "$(basename $file)" >> /etc/ssl/ca/errors.txt
    echo "======================================================================="
    continue
fi
mv $file /etc/ssl/ca/csrfiles-ra/
echo "$(basename $file).csr is already signed"
echo "======================================================================="
done
echo
echo " Finishing Issuing the certificates for outsanding requests" ; 
