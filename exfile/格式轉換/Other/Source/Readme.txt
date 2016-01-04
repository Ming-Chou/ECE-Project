FormatFactory Portable Launcher
===============================
Copyright 2004-2009 John T. Haller
Copyright 2008-2009 Vic Saville

Website: http://PortableApps.com/Development/Test

This software is OSI Certified Open Source Software.
OSI Certified is a certification mark of the Open Source Initiative.

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.

ABOUT FORMATFACTORY PORTABLE
============================
The FormatFactory Portable Launcher allows you to run FormatFactory from a 
removable drive whose letter changes as you move it to another computer. The 
application can be entirely self-contained on the drive and then used on any 
Windows computer.

LICENSE
=======
This code is released under the GPL. The source is included with this package as 
FormatFactoryPortable.nsi.

INSTALLATION / DIRECTORY STRUCTURE
==================================
By default, the program expects the following directory structure:

-\ <--- Directory with FormatFactoryPortable.exe
	+\App\
		+\FormatFactory\
	+\Data\
		+\settings\

It can be used in other directory configurations by including the 
FormatFactoryPortable.ini file in the same directory as 
FormatFactoryPortable.exe and configuring it as details in the INI file section 
below.

FORMATFACTORYPORTABLE.INI CONFIGURATION
=======================================
The FormatFactory Portable Launcher will look for an ini file called 
FormatFactoryPortable.ini (read the previous section for details on placement). 
If you are happy with the default options, it is not necessary, though. The INI 
file is formatted as follows:

[FormatFactoryPortable]
FormatFactoryDirectory=App\FormatFactory
SettingsDirectory=Data\settings
FormatFactoryExecutable=FormatFactory.exe
AdditionalParameters=
DisableSplashScreen=false

The FormatFactoryDirectory and SettingsDirectory entries should be set to the 
*relative* path to the appropriate directories from the current directory. All 
must be a subdirectory (or multiple subdirectories) of the directory containing 
FormatFactoryPortable.exe. The default entries for these are described in the 
installation section above.

The FormatFactoryExecutable entry allows you to set the FormatFactory Portable 
Launcher to use an alternate EXE call to launch FormatFactory. This is helpful 
if you are using a machine that is set to deny FormatFactory.exe from running. 
You'll need to rename the FormatFactory.exe file and then enter the name you 
gave it on the FormatFactoryExecutable= line of the INI.

The AdditionalParameters entry allows you to pass additional commandline 
parameter entries to FormatFactory.exe. Whatever you enter here will be appended 
to the call to FormatFactory.exe.

The DisableSplashScreen entry allows you to run the FormatFactory Portable 
Launcher without the splash screen showing up. The default is false.