#!/bin/bash
now=$(date "+%Y.%m.%d-%H:%M:%S")
echo " Start Issuing the certificates for outsanding requests" ; 
shopt -s nullglob
for file in /home/ca-admin/ca/csrfiles-ca/*.csr 	
do
printf 'y\ny\n' | openssl ca -in $file  -out /home/ca-admin/ca/newcerts-ra/$(basename -s .csr $file).crt -extensions v3_OCSP -config /home/ca-admin/ca/openssl.cnf -policy policy_anything -passin pass:02-x....x
if [ "$?" != "0" ]; then
    echo  "$now: $(basename $file)" >> /home/ca-admin/ca/errors.txt
    echo "======================================================================="
    continue
fi
mv $file /home/ca-admin/ca/csrfiles-ra/
echo "$now: $(basename $file).csr is already signed"
echo "======================================================================="
done
ssh va-admin@10.201.2.6 '~/va/update_va.sh'
if [ "$?" != "0" ]; then
    echo  "$now: Error in conntecting to VA - ErrNo($?)!? " >> /home/ca-admin/ca/errors.txt
    echo "======================================================================="
fi
echo
echo " Finishing Issuing the certificates for outsanding requests" ; 
