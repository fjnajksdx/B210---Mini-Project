#!/usr/bin/env python

from pexpect import pxssh

a = pxssh.pxssh()
if not a.login ('25.34.188.166', 'bobi', 'b'):
    print "SSH session failed on login."
    print str(a)
else:
    print "SSH session login successful" 
    a.sendline ('roslaunch turtlebot_bringup minimal.launch')
    a.interact()      # Hands control to the user


  
    
    
    


 
    







