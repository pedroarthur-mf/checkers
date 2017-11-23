@ECHO OFF
rem @(#)$Date: 2004/01/27 20:40:27 $
rem
rem ajmlc -- script for running the AspectJML runtime assertion checker compiler
rem
rem SYNOPSIS: set CLASSPATH and run JML runtime assertion checker compiler

call ajmlenv.bat

rem save the old CLASSPATH
set OLDCLASSPATH=%CLASSPATH%
set CLASSPATH=.;%AspectJMLDIR%\bin\ajmlc1.7.0.jar;%AspectJMLDIR%\aspectjml-lib\mjc.jar;%AspectJMLDIR%\aspectjml-lib\antlr.jar;%AspectJMLDIR%\aspectjml-lib\GetOpt.jar;%AspectJMLDIR%\aspectjml-lib\junit.jar;%AspectJMLDIR%\aspectjml-lib\ant.jar;%AspectJMLDIR%\aspectjml-lib\qdox-1.12.1.jar;%AspectJMLDIR%\aspectjml-lib\commons-lang3-3.1.jar;%AspectJMLDIR%\aspectjml-lib\aspectjtools.jar;%AspectJMLDIR%\aspectjml-lib\aspectjrt.jar;%AspectJMLDIR%\aspectjml-lib\abc-complete.jar;%CLASSPATH%
rem set J2MELIB = %AspectJMLDIR%\j2me-lib\midpapi21.jar;%AspectJMLDIR%\j2me-lib\cldcapi11.jar;%AspectJMLDIR%\j2me-lib\jsr75.jar;%AspectJMLDIR%\j2me-lib\jsr226.jar;%AspectJMLDIR%\j2me-lib\jsr229.jar;%AspectJMLDIR%\j2me-lib\jsr238.jar;%AspectJMLDIR%\j2me-lib\jsr239.jar;%AspectJMLDIR%\j2me-lib\jsr179.jar

set CMDLINE_ARGS=%1
:getargs
shift
if "%1"=="" goto endgetargs
set CMDLINE_ARGS=%CMDLINE_ARGS% %1
goto getargs
:endgetargs

rem You can use `java' or `jre -cp %CLASSPATH%' below
rem but these have to be in your PATH

java -mx128m org.jmlspecs.ajmlrac.Main %CMDLINE_ARGS%

rem You can use ajmlc with 'JavaME applications'
rem but you need to put the J2MELIB in your PATH
rem as example you can use the following command two
rem commands:

rem java -Xbootclasspath/a:%AspectJMLDIR%\j2me-lib\midpapi21.jar;%AspectJMLDIR%\j2me-lib\cldcapi11.jar;%AspectJMLDIR%\j2me-lib\jsr75.jar;%AspectJMLDIR%\j2me-lib\jsr226.jar;%AspectJMLDIR%\j2me-lib\jsr229.jar;%AspectJMLDIR%\j2me-lib\jsr238.jar;%AspectJMLDIR%\j2me-lib\jsr239.jar;%AspectJMLDIR%\j2me-lib\jsr179.jar org.jmlspecs.ajmlrac.Main %CMDLINE_ARGS%
rem java -Xbootclasspath/a:%J2MELIB% org.jmlspecs.ajmlrac.Main %CMDLINE_ARGS%

rem restore the old CLASSPATH
set CLASSPATH=%OLDCLASSPATH%
set CMDLINE_ARGS=
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
