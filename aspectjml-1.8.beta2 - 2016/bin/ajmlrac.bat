@ECHO OFF
rem  @(#)$Date: 2004/01/27 11:58:27 $
rem
rem ajmlrac --  script for running java programs compiled with
rem            the AspectJ JML runtime assertion checker compiler
rem
rem SYNOPSIS: set CLASSPATH and run java

call ajmlenv.bat


rem save the old CLASSPATH
set OLDCLASSPATH=%CLASSPATH%
set CLASSPATH=%CLASSPATH%;%AspectJMLDIR%\bin\ajmlrt1.7.0.jar;%AspectJMLDIR%\aspectjml-lib\aspectjrt.jar;%CLASSPATH%
rem %AspectJMLDIR%\aspectjml-lib\ajcrt4ajml.jar;
rem %AspectJMLDIR%\aspectjml-lib\abcrt4ajml.jar

set CMDLINE_ARGS=%1
:getargs
shift
if "%1"=="" goto endgetargs
set CMDLINE_ARGS=%CMDLINE_ARGS% %1
goto getargs
:endgetargs

rem You can use `java' or `jre -cp %CLASSPATH%' below
rem but these have to be in your PATH

java %CMDLINE_ARGS%

rem restore the old CLASSPATH
set CLASSPATH=%OLDCLASSPATH%
set CMDLINE_ARGS=
set JMLMODELSJAR=
set OLDCLASSPATH=

rem Copyright (C) 2013 Federal University of Pernambuco and 
rem University of Central Florida
rem
rem This file is part of AspectJML
rem
rem JML is free software; you can redistribute it and/or modify
rem it under the terms of the GNU General Public License as published by
rem the Free Software Foundation; either version 2, or (at your option)
rem any later version.
rem
rem JML is distributed in the hope that it will be useful,
rem but WITHOUT ANY WARRANTY; without even the implied warranty of
rem MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
rem GNU General Public License for more details.
rem
rem You should have received a copy of the GNU General Public License
rem along with JML; see the file COPYING.  If not, write to
rem the Free Software Foundation, 675 Mass Ave, Cambridge, MA 02139, USA.
