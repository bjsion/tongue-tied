#!/usr/bin/env bash

###############################################################################
# Start script for the tongue tied embedded server.
###############################################################################

# It is advisable to run this script from the command line
# The following environment variables should be set before starting:
# JAVA_HOME
# 

# Uncomment the following line if you are experiencing OutOfMemoryError errors
# tt_opts=-Xms128m -Xmx256m

# check that JAVA_HOME is defined
if [ -z "$JAVA_HOME" ] ; then
  echo JAVA_HOME is not set
fi

# check that TT_HOME is defined
if [ -z "$TT_HOME" ] ; then
  echo Setting TT_HOME to point to the root tonguetied installation
  TT_HOME=`dirname .`
  TT_HOME=`cd "$TT_HOME" && pwd`
fi
# use the fully qualified path name
echo TT_HOME is : $TT_HOME


LIBDIR=$TT_HOME/libs

EXEC="${JAVA_HOME:-/usr}/bin/java $tt_opts -jar $LIBDIR/server.jar"
echo $EXEC
$EXEC &

