#!/bin/bash
if [[ ( $@ == "--help") ||  $@ == "-h" || $# -lt 1 ]]
then 
        echo "'./script.sh start' to start the domain"
        echo "'./script.sh start new_database' to start the domain and restart the database"
        echo "'./script.sh stop' to stop the domain"
        echo "'./script.sh restart' to restart the domain"
        exit 0
fi 
if [[ "$1" == "start" ]]; 
then
        sudo /opt/glassfish5/glassfish/bin/asadmin start-domain
        sudo /opt/glassfish5/javadb/bin/startNetworkServer&
        sleep 5
        if [[ "$2" == "new_database" ]]; 
        then 
                echo "Creating new database..."
                /opt/glassfish5/javadb/bin/ij /home/ubuntu/runDB.sql
                echo "The database is created, no users are registred. "
        fi
elif [[ "$1" == "stop" ]]; 
then
        sudo /opt/glassfish5/glassfish/bin/asadmin stop-domain
        sudo /opt/glassfish5/javadb/bin/stopNetworkServer&
elif [[ "$1" == "restart" ]]; 
then
        sudo /opt/glassfish5/glassfish/bin/asadmin restart-domain

fi

