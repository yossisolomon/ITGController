#!/bin/bash
BASE_DIR="/tmp/ITGRecv-Logs"
STATS_DIR="$BASE_DIR/stats"
rm -rf $STATS_DIR
LOGS=`ls -1 $BASE_DIR/*`
mkdir $STATS_DIR
for file in $LOGS; do
	base_file=`basename $file .log`
	ITGDec $file -c 1 $STATS_DIR/$base_file.stats
done

STATS=`ls -1 $STATS_DIR/*`
python plot_from_stats.py $STATS

gpicview /tmp/ITGRecv-Logs/stats/ITGRecv-10.0.0.*.jpg

