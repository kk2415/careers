#!/bin/bash

CONTAINER_NAME_PREFIX=job_
ALL_PORTS=(8080 8081)
CURRENT_ACTIVE_PORTS=($(sudo docker ps | awk '/job_*/ {print $(NF-1)}' | sed -n 's/::://p' | sed -n 's/->.*//p' | tr '\n' ' '))
CURRENT_ACTIVE_PORT=$CURRENT_ACTIVE_PORTS
NEXT_ACTIVE_PORT=0

for PORT in "${ALL_PORTS[@]}"; do
        IS_MATCHED=0
        for ACTIVE_PORT in "${CURRENT_ACTIVE_PORTS[@]}"; do
                if [[ $PORT == $ACTIVE_PORT  ]]; then
                        IS_MATCHED=1
                        break
                fi
        done

        if [[ $IS_MATCHED == 0 ]]; then
                NEXT_ACTIVE_PORT=$PORT
                break
        fi
done

if [[ $NEXT_ACTIVE_PORT != 0  ]]; then
        CONTAINER_NAME=${CONTAINER_NAME_PREFIX}${NEXT_ACTIVE_PORT}
        CONTAINER_NAME_TO_BE_TERMINATED=${CONTAINER_NAME_PREFIX}${CURRENT_ACTIVE_PORT}

        #run new job container
        sudo docker run -d --name $CONTAINER_NAME -p $NEXT_ACTIVE_PORT:$NEXT_ACTIVE_PORT -e JAR_FILE=$JOB_APP_JAR_FILE -e PROFILE=$JOB_APP_PROFILE -e JASYPT_PASSWORD=$JASYPT_PASSWORD --net careers_network --net-alias job kyunkim/careers:job-0.0
        echo "The $CONTAINER_NAME container ran on $NEXT_ACTIVE_PORT port"

        #change nginx configure file
        sleep 10
        sed -i 's/'"$CONTAINER_NAME_TO_BE_TERMINATED"'/'"$CONTAINER_NAME"'/' /srv/careers/nginx/conf.d/job.conf
        sudo docker-compose -f /srv/careers/app/docker-compose.yml restart nginx

        #remove old job container
        sudo docker stop $CONTAINER_NAME_TO_BE_TERMINATED
        sleep 2
        sudo docker rm $CONTAINER_NAME_TO_BE_TERMINATED

        echo "The $CONTAINER_NAME_TO_BE_TERMINATED container on $CURRENT_ACTIVE_PORT port has been terminated"
else
        echo "The container cannot run because all ports are in use."
        exit 0
fi
