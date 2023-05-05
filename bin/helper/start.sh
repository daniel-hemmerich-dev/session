#!/bin/bash


# Start docker container
docker-compose rm -f
docker-compose pull
docker-compose up --remove-orphans --force-recreate