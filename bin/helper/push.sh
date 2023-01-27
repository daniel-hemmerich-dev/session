# Variables
SSH_KEY_PATH=../../../.ssh/eqno

# commit
ssh-agent bash -c "ssh-add $SSH_KEY_PATH; git commit -a -m \"$1\""

# push
ssh-agent bash -c "ssh-add $SSH_KEY_PATH; git push -u origin master"