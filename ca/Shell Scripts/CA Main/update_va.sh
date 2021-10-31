#!/bin/bash

now=$(date +"+%Y.%m.%d-%H.%M.%S")
echo " Start updating index.txt, new certificates, new crl" ; 
echo "======================================================================="

echo "Start updating index.txt" ; 
scp ca-admin@10.201.2.5:~/ca/index.txt ~/va/index.txt
if [ "$?" != "0" ]; then
    echo  "$now :index.txt cannot be updated from CA!? CA may not be online err($?)" >> ~/va/va_error_log.txt
    continue
fi

echo " Start sync new crl at CA " ; 
scp ca-admin@10.201.2.5:~/ca/crl/*.* ~/va/crls/
if [ "$?" != "0" ]; then
    echo  "$now :Error in Getting crl from CA!? CA may not be online err($?)" >> ~/va/va_error_log.txt
    continue
fi


echo " Start sync new certs at CA " ; 
rsync -avh -e ssh ca-admin@10.201.2.5:~/ca/newcerts/ ~/va/certs/
if [ "$?" != "0" ]; then
    echo  " $now :Error in Getting new certificates from CA!? CA may not be online err($?)" >> ~/va/va_error_log.txt
    continue
fi


echo "======================================================================="
echo " Finishing Issuing the certificates for outsanding requests" ; 
