#!/bin/bash
sudo "/var/lib/broker/bin/artemis-service" start
sudo /home/centos/apache-tomcat-9.0.44/bin/./shutdown.sh
sudo /home/centos/apache-tomcat-9.0.44/bin/./startup.sh
