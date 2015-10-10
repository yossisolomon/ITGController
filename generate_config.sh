#!/bin/bash

HOST_IP_ADDRESSES="10.0.0.1 10.0.0.2 10.0.0.3 10.0.0.4 10.0.0.5 10.0.0.6 10.0.0.7 10.0.0.8 10.0.0.9 10.0.0.10 10.0.0.11"
#HOST_IP_ADDRESSES="10.0.0.1 10.0.0.2 10.0.0.3"
OUTPUT_CONF_BASE="/tmp/generated-config"
OUTPUT_CONF_RUN="$OUTPUT_CONF_BASE.run"
APP_SIM="CSa"

rm -f $OUTPUT_CONF_BASE*


for host_addr in $HOST_IP_ADDRESSES ; do
	OUTPUT_CONF="$OUTPUT_CONF_BASE-$host_addr"
	for dest_addr in $HOST_IP_ADDRESSES ; do
		echo "-a $dest_addr $APP_SIM" >> $OUTPUT_CONF
	done
	echo "ssh tutorial1@$host_addr -o StrictHostKeyChecking=false ITGSend $OUTPUT_CONF >/dev/null &" >> $OUTPUT_CONF_RUN
done

echo "wait" >> $OUTPUT_CONF_RUN
chmod +x $OUTPUT_CONF_RUN

