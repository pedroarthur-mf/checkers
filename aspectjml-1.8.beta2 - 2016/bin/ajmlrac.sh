#!/bin/sh
#  @(#)$Date: 2014/4/30 11:58:27 $
#
# ajmlrac --  script for running java programs compiled with
#            the AspectJ JML runtime assertion checker compiler
#
# SYNOPSIS: set CLASSPATH and run java

# Change the AspectJMLDIR as necessary
AspectJMLDIR=/usr/local/AspectJML

CLASSPATH=".:$AspectJMLDIR/bin/ajmlrt1.7.0.jar:$AspectJMLDIR/aspectjml-lib/aspectjrt.jar:$CLASSPATH"
# for windows with cygwin, uncomment the following:
# CLASSPATH=`cygpath -w -p $CLASSPATH`
export CLASSPATH

# Maybe this can be used at some point
# $AspectJMLDIR/aspectjml-lib/ajcrt4ajml.jar:
# $AspectJMLDIR/aspectjml-lib/abcrt4ajml.jar:

# You can use `java' or `jre -cp "$CLASSPATH"' below
# but these have to be in your PATH

java "$@"

# Copyright (C) 2013 Federal University of Pernambuco and 
# University of Central Florida
#
# This file is part of AspectJML
#
# JML is free software; you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation; either version 2, or (at your option)
# any later version.
#
# JML is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with JML; see the file COPYING.  If not, write to
# the Free Software Foundation, 675 Mass Ave, Cambridge, MA 02139, USA.
