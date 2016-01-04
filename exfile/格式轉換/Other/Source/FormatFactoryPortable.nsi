;Copyright 2004-2009 John T. Haller
;Copyright 2008-2009 Vic Saville

;Website: http://portableapps.com

;This software is OSI Certified Open Source Software.
;OSI Certified is a certification mark of the Open Source Initiative.

;This program is free software; you can redistribute it and/or
;modify it under the terms of the GNU General Public License
;as published by the Free Software Foundation; either version 2
;of the License, or (at your option) any later version.

;This program is distributed in the hope that it will be useful,
;but WITHOUT ANY WARRANTY; without even the implied warranty of
;MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
;GNU General Public License for more details.

;You should have received a copy of the GNU General Public License
;along with this program; if not, write to the Free Software
;Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.

!define PORTABLEAPPNAME "FormatFactory Portable"
!define NAME "FormatFactoryPortable"
!define APPNAME "FormatFactory"
!define VER "1.0.0.2"
!define WEBSITE "PortableApps.com"
!define DEFAULTEXE "FormatFactory.exe"
!define DEFAULTAPPDIR "FormatFactory"
!define DEFAULTSETTINGSPATH "settings"

;=== Program Details
Name "${PORTABLEAPPNAME}"
OutFile "..\..\${NAME}.exe"
Caption "${PORTABLEAPPNAME} | PortableApps.com"
VIProductVersion "${VER}"
VIAddVersionKey ProductName "${PORTABLEAPPNAME}"
VIAddVersionKey Comments "Allows ${APPNAME} to be run from a removable drive. For additional details, visit ${WEBSITE}"
VIAddVersionKey CompanyName "PortableApps.com"
VIAddVersionKey LegalCopyright "PortableApps.com & Contributors"
VIAddVersionKey FileDescription "${PORTABLEAPPNAME}"
VIAddVersionKey FileVersion "${VER}"
VIAddVersionKey ProductVersion "${VER}"
VIAddVersionKey InternalName "${PORTABLEAPPNAME}"
VIAddVersionKey LegalTrademarks "PortableApps.com is a Trademark of Rare Ideas, LLC."
VIAddVersionKey OriginalFilename "${NAME}.exe"
;VIAddVersionKey PrivateBuild ""
;VIAddVersionKey SpecialBuild ""

;=== Runtime Switches
CRCCheck On
WindowIcon Off
SilentInstall Silent
AutoCloseWindow True
RequestExecutionLevel user

; Best Compression
SetCompress Auto
SetCompressor /SOLID lzma
SetCompressorDictSize 32
SetDatablockOptimize On

;=== Include
;(Standard NSIS)
!include FileFunc.nsh
!include MUI.nsh

;(NSIS Plugins)
!include Registry.nsh
!include ReplaceInFile.nsh
!include StrRep.nsh
!insertmacro GetParameters ;NSIS 2.4.0 and up ONLY
!insertmacro GetRoot

;=== Program Icon
Icon "..\..\App\AppInfo\appicon.ico"

;=== Icon & Stye ===
!define MUI_ICON "..\..\App\AppInfo\appicon.ico"

;=== Languages
!insertmacro MUI_LANGUAGE "English"

LangString LauncherFileNotFound ${LANG_ENGLISH} "${PORTABLEAPPNAME} cannot be started. You may wish to re-install to fix this issue. (ERROR: $MISSINGFILEORPATH could not be found)"
LangString LauncherAlreadyRunning ${LANG_ENGLISH} "Another instance of ${APPNAME} is already running. Please close other instances of ${APPNAME} before launching ${PORTABLEAPPNAME}."
LangString LauncherAskCopyLocal ${LANG_ENGLISH} "${PORTABLEAPPNAME} appears to be running from a location that is read-only. Would you like to temporarily copy it to the local hard drive and run it from there?$\n$\nPrivacy Note: If you say Yes, your personal data within ${PORTABLEAPPNAME} will be temporarily copied to a local drive. Although this copy of your data will be deleted when you close ${PORTABLEAPPNAME}, it may be possible for someone else to access your data later."
LangString LauncherNoReadOnly ${LANG_ENGLISH} "${PORTABLEAPPNAME} can not run directly from a read-only location and will now close."
LangString LauncherDownloadIE ${LANG_ENGLISH} "${APPNAME} was not found. Say Yes to download it now or No to download it later. If you say Yes, Internet Explorer will open."
LangString LauncherDownloadFFP ${LANG_ENGLISH} "${APPNAME} was not found. Say Yes to download it now or No to download it later. If you say Yes, Firefox Portable will open."

Var PROGRAMDIRECTORY
Var SETTINGSDIRECTORY
Var ADDITIONALPARAMETERS
Var EXECSTRING
Var PROGRAMEXECUTABLE
Var INIPATH
Var DISABLESPLASHSCREEN
Var ISDEFAULTDIRECTORY
Var SECONDARYLAUNCH
Var FAILEDTORESTOREKEY
Var MISSINGFILEORPATH
Var USERTYPE

Section "Main"
	;=== Check if already running
	System::Call 'kernel32::CreateMutexA(i 0, i 0, t "${NAME}2") i .r1 ?e'
	Pop $0
	StrCmp $0 0 CheckForINI
		StrCpy $SECONDARYLAUNCH "true"

	CheckForINI:
	;=== Find the INI file, if there is one
		IfFileExists "$EXEDIR\${NAME}.ini" "" NoINI
			StrCpy "$INIPATH" "$EXEDIR"
			Goto ReadINI

	ReadINI:
		;=== Read the parameters from the INI file
		ReadINIStr $0 "$INIPATH\${NAME}.ini" "${NAME}" "${APPNAME}Directory"
		StrCpy "$PROGRAMDIRECTORY" "$EXEDIR\$0"
		ReadINIStr $0 "$INIPATH\${NAME}.ini" "${NAME}" "SettingsDirectory"
		StrCpy "$SETTINGSDIRECTORY" "$EXEDIR\$0"

		;=== Check that the above required parameters are present
		IfErrors NoINI

		ReadINIStr $0 "$INIPATH\${NAME}.ini" "${NAME}" "AdditionalParameters"
		StrCpy "$ADDITIONALPARAMETERS" $0
		ReadINIStr $0 "$INIPATH\${NAME}.ini" "${NAME}" "${APPNAME}Executable"
		StrCpy "$PROGRAMEXECUTABLE" $0
		ReadINIStr $0 "$INIPATH\${NAME}.ini" "${NAME}" "DisableSplashScreen"
		StrCpy "$DISABLESPLASHSCREEN" $0

	;CleanUpAnyErrors:
		;=== Any missing unrequired INI entries will be an empty string, ignore associated errors
		ClearErrors

		;=== Correct PROGRAMEXECUTABLE if blank
		StrCmp $PROGRAMEXECUTABLE "" "" CheckForProgramINI
			StrCpy "$PROGRAMEXECUTABLE" "${DEFAULTEXE}"
			Goto CheckForProgramINI

	CheckForProgramINI:
		IfFileExists "$PROGRAMDIRECTORY\$PROGRAMEXECUTABLE" FoundProgramEXE NoProgramEXE

	NoINI:
		;=== No INI file, so we'll use the defaults
		StrCpy "$ADDITIONALPARAMETERS" ""
		StrCpy "$PROGRAMEXECUTABLE" "${DEFAULTEXE}"
		StrCpy "$DISABLESPLASHSCREEN" "false"

		IfFileExists "$EXEDIR\App\${DEFAULTAPPDIR}\${DEFAULTEXE}" "" NoProgramEXE
			StrCpy "$PROGRAMDIRECTORY" "$EXEDIR\App\${DEFAULTAPPDIR}"
			StrCpy "$SETTINGSDIRECTORY" "$EXEDIR\Data\${DEFAULTSETTINGSPATH}"
			StrCpy "$ISDEFAULTDIRECTORY" "true"
			Goto FoundProgramEXE

	NoProgramEXE:
		;=== Program executable not where expected
		StrCpy $MISSINGFILEORPATH $PROGRAMEXECUTABLE
		IfFileExists "..\FirefoxPortable\*.*" FirefoxFound
		MessageBox MB_OK|MB_ICONEXCLAMATION `$(LauncherFileNotFound)`
		MessageBox MB_YESNO `$(LauncherDownloadIE)` IDYES 0 IDNO +2
		IEFunctions::OpenBrowser /NOUNLOAD
        IEFunctions::SurfTo /NOUNLOAD "http://www.formatoz.com/download.html"
        IEFunctions::ReleaseBrowser
		Abort

		FirefoxFound:
			StrCpy $MISSINGFILEORPATH $PROGRAMEXECUTABLE
			MessageBox MB_OK|MB_ICONEXCLAMATION `$(LauncherFileNotFound)`
			MessageBox MB_YESNO `$(LauncherDownloadFFP)` IDYES 0 IDNO +2
			Exec '"..\FirefoxPortable\FirefoxPortable.exe" http://www.formatoz.com/download.html'
			Abort

	FoundProgramEXE:
		;=== Check if already running
		StrCmp $SECONDARYLAUNCH "true" CheckForSettings
		FindProcDLL::FindProc "$PROGRAMEXECUTABLE"                 
		StrCmp $R0 "1" WarnAnotherInstance CheckForSettings

	WarnAnotherInstance:
		MessageBox MB_OK|MB_ICONINFORMATION `$(LauncherAlreadyRunning)`
		Abort

	CheckForSettings:
		IfFileExists "$SETTINGSDIRECTORY\*.*" SettingsFound
		;=== No settings found
		StrCmp $ISDEFAULTDIRECTORY "true" CopyDefaultSettings
		CreateDirectory $SETTINGSDIRECTORY
		Goto SettingsFound

	CopyDefaultSettings:
		CreateDirectory "$EXEDIR\Data"
		CreateDirectory "$EXEDIR\Data\settings"
		CopyFiles /SILENT $EXEDIR\App\DefaultData\settings\*.* $EXEDIR\Data\settings

	SettingsFound:
		StrCmp $DISABLESPLASHSCREEN "true" GetPassedParameters
			;=== Show the splash screen before processing the files
			InitPluginsDir
			File /oname=$PLUGINSDIR\splash.jpg "${NAME}.jpg"	
			newadvsplash::show /NOUNLOAD 1500 0 0 -1 /L $PLUGINSDIR\splash.jpg

	GetPassedParameters:
		;=== Get any passed parameters
		${GetParameters} $0
		StrCmp "'$0'" "''" "" LaunchProgramParameters

		;=== No parameters
		StrCpy $EXECSTRING `"$PROGRAMDIRECTORY\$PROGRAMEXECUTABLE"`
		Goto AdditionalParameters

	LaunchProgramParameters:
		StrCpy $EXECSTRING `"$PROGRAMDIRECTORY\$PROGRAMEXECUTABLE" $0`

	AdditionalParameters:
		StrCmp $ADDITIONALPARAMETERS "" CheckForBackup

		;=== Additional Parameters
		StrCpy $EXECSTRING `$EXECSTRING $ADDITIONALPARAMETERS`

	CheckForBackup:
		StrCmp $SECONDARYLAUNCH "true" LaunchAndExit
		;=== Check if launcher was shut down correctly
		IfFileExists "$SETTINGSDIRECTORY\.dvdcss-Backup" "" UpdatePaths
		MessageBox MB_YESNO|MB_ICONQUESTION `${PORTABLEAPPNAME} has detected a backup. This means that it was not previously shut down$\r$\ncorrectly and some files were left on the host computer.$\n$\nWould you like to continue?$\n$\nIf you say Yes, your backup will be restored and ${PORTABLEAPPNAME} will continue to load.$\r$\nAny settings you saved during your last session will be lost.$\n$\nIf you say No, ${PORTABLEAPPNAME} will close and you can attempt to manually recover the files.` IDYES RenameBackup
		Goto TheEnd

	RenameBackup:
		;=== Restore the backup
		Rename "$SETTINGSDIRECTORY\.dvdcss-Backup" "$SETTINGSDIRECTORY\.dvdcss"

	UpdatePaths:
		${GetRoot} $EXEDIR $1
		ReadINIStr $2 "$SETTINGSDIRECTORY\FormatFactoryPortableSettings.ini" "FormatFactoryPortableSettings" "LastDrive"
		${ReplaceInFile} "$SETTINGSDIRECTORY\FormatFactory_portable.reg" "$2" "$1"
		${ReplaceInFile} "$SETTINGSDIRECTORY\FormatFactoryPortableSettings.ini" "$2" "$1"
		Delete "$SETTINGSDIRECTORY\FormatFactory_portable.reg.old"
		Delete "$SETTINGSDIRECTORY\FormatFactoryPortableSettings.ini.old"

	;MoveSettings:
		Rename "$DOCUMENTS\FFOutput" "$DOCUMENTS\FFOutput-BackupByFormatFactoryPortable"
		Rename "$PROFILE\.dvdcss" "$PROFILE\.dvdcss-BackupByFormatFactoryPortable"
		CopyFiles /SILENT "$SETTINGSDIRECTORY\.dvdcss" "$PROFILE"
		Rename "$SETTINGSDIRECTORY\.dvdcss" "$SETTINGSDIRECTORY\.dvdcss-Backup"

	;RegistryBackup1:
		;=== Backup the registry
		${registry::KeyExists} "HKEY_CURRENT_USER\Software\FormatFactory-BackupByFormatFactoryPortable" $R0
		StrCmp $R0 "0" RegistryBackup2
		${registry::KeyExists} "HKEY_CURRENT_USER\Software\FormatFactory" $R0
		StrCmp $R0 "-1" RegistryBackup2
		${registry::MoveKey} "HKEY_CURRENT_USER\Software\FormatFactory" "HKEY_CURRENT_USER\Software\FormatFactory-BackupByFormatFactoryPortable" $R0
		Sleep 100

	RegistryBackup2:
		;=== Backup the registry
		${registry::KeyExists} "HKEY_CURRENT_USER\Software\FreeTime\FormatFactory-BackupByFormatFactoryPortable" $R0
		StrCmp $R0 "0" RegistryBackup3
		${registry::KeyExists} "HKEY_CURRENT_USER\Software\FreeTime\FormatFactory" $R0
		StrCmp $R0 "-1" RegistryBackup3
		${registry::MoveKey} "HKEY_CURRENT_USER\Software\FreeTime\FormatFactory" "HKEY_CURRENT_USER\Software\FreeTime\FormatFactory-BackupByFormatFactoryPortable" $R0
		Sleep 100

	RegistryBackup3:
		UserInfo::GetAccountType
		Pop $USERTYPE
		StrCmp $USERTYPE "Guest" RestoreTheKey
		StrCmp $USERTYPE "User" RestoreTheKey
		${registry::KeyExists} "HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft\Windows\CurrentVersion\App Paths\FormatFactory.exe-BackupByFormatFactoryPortable" $R0
		StrCmp $R0 "0" RestoreTheKey
		${registry::KeyExists} "HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft\Windows\CurrentVersion\App Paths\FormatFactory.exe" $R0
		StrCmp $R0 "-1" RestoreTheKey
		${registry::MoveKey} "HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft\Windows\CurrentVersion\App Paths\FormatFactory.exe" "HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft\Windows\CurrentVersion\App Paths\FormatFactory.exe-BackupByFormatFactoryPortable" $R0
		Sleep 100

	RestoreTheKey:
		IfFileExists "$SETTINGSDIRECTORY\FormatFactory_portable.reg" "" LaunchNow

		IfFileExists "$WINDIR\system32\reg.exe" "" RestoreTheKey9x
			nsExec::ExecToStack `"$WINDIR\system32\reg.exe" import "$SETTINGSDIRECTORY\FormatFactory_portable.reg"`
			Pop $R0
			StrCmp $R0 '0' LaunchNow ;successfully restored key

	RestoreTheKey9x:
		${registry::RestoreKey} "$SETTINGSDIRECTORY\FormatFactory_portable.reg" $R0
		StrCmp $R0 '0' LaunchNow ;successfully restored key
		StrCpy $FAILEDTORESTOREKEY "true"

	LaunchNow:	
		Sleep 100
		ExecWait $EXECSTRING

	CheckRunning:
		Sleep 1000
		FindProcDLL::FindProc "${DEFAULTEXE}"                  
		StrCmp $R0 "1" CheckRunning

		;=== Move settings back
		Sleep 500
		Rename "$DOCUMENTS\FFOutput-BackupByFormatFactoryPortable" "$DOCUMENTS\FFOutput"
		CopyFiles /SILENT "$PROFILE\.dvdcss" "$SETTINGSDIRECTORY"
		RMDir /r "$PROFILE\.dvdcss"
		Rename "$PROFILE\.dvdcss-BackupByFormatFactoryPortable" "$PROFILE\.dvdcss"
		RMDir /r "$SETTINGSDIRECTORY\.dvdcss-Backup"

		StrCmp $FAILEDTORESTOREKEY "true" SetOriginalKeyBack1
		${registry::SaveKey} "HKEY_CURRENT_USER\Software\FormatFactory" "$SETTINGSDIRECTORY\FormatFactory_portable.reg" "" $0
		${registry::SaveKey} "HKEY_CURRENT_USER\Software\FreeTime\FormatFactory" "$SETTINGSDIRECTORY\FormatFactory_portable.reg" "/A=1" $0
		UserInfo::GetAccountType
		Pop $USERTYPE
		StrCmp $USERTYPE "Guest" SetOriginalKeyBack1
		StrCmp $USERTYPE "User" SetOriginalKeyBack1
		${registry::SaveKey} "HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft\Windows\CurrentVersion\App Paths\FormatFactory.exe" "$SETTINGSDIRECTORY\FormatFactory_portable.reg" "/A=1" $0
		Sleep 100

	SetOriginalKeyBack1:
		${registry::DeleteKey} "HKEY_CURRENT_USER\Software\FormatFactory" $R0
		Sleep 100
		${registry::KeyExists} "HKEY_CURRENT_USER\Software\FormatFactory-BackupByFormatFactoryPortable" $R0
		StrCmp $R0 "-1" SetOriginalKeyBack2
		${registry::MoveKey} "HKEY_CURRENT_USER\Software\FormatFactory-BackupByFormatFactoryPortable" "HKEY_CURRENT_USER\Software\FormatFactory" $R0
		Sleep 100

	SetOriginalKeyBack2:
		${registry::DeleteKey} "HKEY_CURRENT_USER\Software\FreeTime\FormatFactory" $R0
		Sleep 100
		${registry::KeyExists} "HKEY_CURRENT_USER\Software\FreeTime\FormatFactory-BackupByFormatFactoryPortable" $R0
		StrCmp $R0 "-1" SetOriginalKeyBack3
		${registry::MoveKey} "HKEY_CURRENT_USER\Software\FreeTime\FormatFactory-BackupByFormatFactoryPortable" "HKEY_CURRENT_USER\Software\FreeTime\FormatFactory" $R0
		Sleep 100

	SetOriginalKeyBack3:
		UserInfo::GetAccountType
		Pop $USERTYPE
		StrCmp $USERTYPE "Guest" TheEnd
		StrCmp $USERTYPE "User" TheEnd
		${registry::DeleteKey} "HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft\Windows\CurrentVersion\App Paths\FormatFactory.exe" $R0
		Sleep 100
		${registry::KeyExists} "HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft\Windows\CurrentVersion\App Paths\FormatFactory.exe-BackupByFormatFactoryPortable" $R0
		StrCmp $R0 "-1" TheEnd
		${registry::MoveKey} "HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft\Windows\CurrentVersion\App Paths\FormatFactory.exe-BackupByFormatFactoryPortable" "HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft\Windows\CurrentVersion\App Paths\FormatFactory.exe" $R0
		Sleep 100
		Goto TheEnd

	LaunchAndExit:
		Exec $EXECSTRING

	TheEnd:
		DeleteRegKey /ifempty HKCU "SOFTWARE\FreeTime"
		${registry::Unload}
		newadvsplash::stop /WAIT
SectionEnd