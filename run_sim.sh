#!/bin/bash

echo "Generate config (press enter):"
read
./generate_config.sh

echo "Run config (press enter):"
read
/tmp/generated-config.run

ls -l /tmp/ITGRecv-Logs

echo "Decode results (press enter):"
read
./decode.sh
