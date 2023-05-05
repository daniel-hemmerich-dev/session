# A simple session service
A session service written in Java with Spring Boot and MongoDB.

## Setup
1. Create an .env file with the following content in the root directory.
```
APP_PORT=8080
APP_DEBUG=true
API_KEY=<API_KEY>
MONGO_EXPRESS_PORT=8081
DB_PORT=27017
DB_HOST=session-db
DB_NAME=sessions
DB_USER=<USERNAME>
DB_PASSWORD=<PASSWORD>
```
2. Set values for `<API_KEY>`, `<USERNAME>` and `<PASSWORD>`.
3. chmod +x ./bin/helper/install/sh
4. ./bin/helper/start.sh

## Commands
### Install
Installs the application.
```shell
./bin/helper/install.sh
```

### Start
Starts the application.
```shell
./bin/helper/start.sh
```

### Stop
Stops the application.
```shell
./bin/helper/stop.sh
```

### Test
Tests the application.
```shell
./bin/helper/test.sh