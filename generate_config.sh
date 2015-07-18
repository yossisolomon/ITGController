#!/bin/bash

HOST_IP_ADDRESSES="10.0.0.1 10.0.0.2 10.0.0.3 10.0.0.4 10.0.0.5 10.0.0.6 10.0.0.7 10.0.0.8 10.0.0.9 10.0.0.10 10.0.0.11"
#HOST_IP_ADDRESSES="10.0.0.1 10.0.0.2 10.0.0.3"
OUTPUT_CONF="generated-config"
APP_SIM="CSa"


[ -e $OUTPUT_CONF ] && rm $OUTPUT_CONF
for host_addr in $HOST_IP_ADDRESSES ; do
	echo "Host $host_addr {" >> $OUTPUT_CONF
	for dest_addr in $HOST_IP_ADDRESSES ; do
		echo "    -a $dest_addr $APP_SIM" >> $OUTPUT_CONF
	done
	echo "}" >> $OUTPUT_CONF
done