#!/bin/sh
# @(#)$Date: 2009/11/20 20:40:27 $
#
# ajml -- script for invoking the JML checker
#
# SYNOPSIS: set CLASSPATH and run AJML

# Change the AspectJMLDIR as necessary
AspectJMLDIR=/usr/local/AspectJML

CLASSPATH=".:$AspectJMLDIR/bin/ajmlc1.7.0.jar:$AspectJMLDIR/aspectjml-lib/mjc.jar:$AspectJMLDIR/aspectjml-lib/antlr.jar:$AspectJMLDIR/aspectjml-lib/GetOpt.jar:$AspectJMLDIR/aspectjml-lib/junit.jar:$AspectJMLDIR/aspectjml-lib/ant.jar:$AspectJMLDIR/aspectjml-lib/qdox-1.12.1.jar:$AspectJMLDIR/aspectjml-lib/commons-lang3-3.1.jar:$AspectJMLDIR/aspectjml-lib/aspectjtools.jar:$AspectJMLDIR/aspectjml-lib/aspectjrt.jar:$AspectJMLDIR/aspectjml-lib/abc-complete.jar:$CLASSPATH"
# for windows with cygwin, uncomment the following:
# CLASSPATH=`cygpath -w -p $CLASSPATH`
export CLASSPATH

# You can use `java' or `jre -cp "$CLASSPATH"' below
# but these have to be in your PATH

java -mx128m org.jmlspecs.checker.Main "$@"

# Copyright (C) 2008-2012 Federal University of Pernambuco and 
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
