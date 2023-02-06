# push to git
./bin/helper/push.sh "$1"

# build
./bin/helper/build.sh

# deploy to aws elastic beanstalk
./bin/ebcli-virtual-env/bin/eb deploy