#!/bin/sh
# @(#)$Date: 2009/11/20 20:40:27 $
#
# ajmlc -- script for running the AspectJML runtime assertion checker compiler
#
# SYNOPSIS: set CLASSPATH and run JML runtime assertion checker compiler

# Change the AspectJMLDIR as necessary
AspectJMLDIR=/usr/local/AspectJML

CLASSPATH=".:$AspectJMLDIR/bin/ajmlc1.7.0.jar:$AspectJMLDIR/aspectjml-lib/mjc.jar:$AspectJMLDIR/aspectjml-lib/antlr.jar:$AspectJMLDIR/aspectjml-lib/GetOpt.jar:$AspectJMLDIR/aspectjml-lib/junit.jar:$AspectJMLDIR/aspectjml-lib/ant.jar:$AspectJMLDIR/aspectjml-lib/qdox-1.12.1.jar:$AspectJMLDIR/aspectjml-lib/commons-lang3-3.1.jar:$AspectJMLDIR/aspectjml-lib/aspectjtools.jar:$AspectJMLDIR/aspectjml-lib/aspectjrt.jar:$AspectJMLDIR/aspectjml-lib/abc-complete.jar:$CLASSPATH"
# for windows with cygwin, uncomment the following:
# CLASSPATH=`cygpath -w -p "$CLASSPATH"`
export CLASSPATH
#: ${J2MELIB = "$AspectJMLDIR/j2me-lib/midpapi21.jar:$AspectJMLDIR/j2me-lib/cldcapi11.jar:$AspectJMLDIR/j2me-lib/jsr75.jar:$AspectJMLDIR/j2me-lib/jsr226.jar:$AspectJMLDIR/j2me-lib/jsr229.jar:$AspectJMLDIR/j2me-lib/jsr238.jar:$AspectJMLDIR/j2me-lib/jsr239.jar:$AspectJMLDIR/j2me-lib/jsr179.jar"}
J2MELIB="$AspectJMLDIR/j2me-lib/midpapi21.jar:$AspectJMLDIR/j2me-lib/cldcapi11.jar:$AspectJMLDIR/j2me-lib/jsr75.jar:$AspectJMLDIR/j2me-lib/jsr226.jar:$AspectJMLDIR/j2me-lib/jsr229.jar:$AspectJMLDIR/j2me-lib/jsr238.jar:$AspectJMLDIR/j2me-lib/jsr239.jar:$AspectJMLDIR/j2me-lib/jsr179.jar"
#J2MELIB=`cygpath -w -p "$J2MELib"`
export J2MELIB

# You can use `java' or `jre -cp "$%CLASSPATH"' below
# but these have to be in your PATH

java -mx128m org.jmlspecs.ajmlrac.Main "$@"

# You can use ajmlc with 'JavaME applications'
# but you need to put the J2MELIB in your PATH
# as example you can use the following command two
# commands:

# java -Xbootclasspath/a:"$AspectJMLDIR\\j2me-lib\\midpapi21.jar;$AspectJMLDIR\\j2me-lib\\cldcapi11.jar;$AspectJMLDIR\\j2me-lib\\jsr75.jar;$AspectJMLDIR\\j2me-lib\\jsr226.jar;$AspectJMLDIR\\j2me-lib\\jsr229.jar;$AspectJMLDIR\\j2me-lib\\jsr238.jar;$AspectJMLDIR\\j2me-lib\\jsr239.jar;$AspectJMLDIR\\j2me-lib\\jsr179.jar org.jmlspecs.ajmlrac.Main" "$@"
# java -Xbootclasspath/a:"$J2MELIB" org.jmlspecs.ajmlrac.Main "$@"

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
