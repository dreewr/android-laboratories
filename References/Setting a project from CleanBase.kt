Clean Architecture: Setting a new Project from Clean Template


► Repository Setup

	► Create repository on Github
	► Create a matching folder to store the project
	► Create the Android Project in Android Studio
	► Create a new repository on the command line in Android Studio
	► Integrate GitFlow plugin in Android Studio
	► Be sure that that there are no files in default changelist
	► Init Gitflow in AndroidStudio - If has iOS project, set non-default with "android/" suffix 
	► Commit project files in the develop branch


► Module importing
	
	► Copy the plugins used in android-cleand from project build.gradle file
	► Add the dependencies.gradle in the project build.gradle file
		► apply from: 'dependencies.gradle'
	► File > New > Import Module > Import the UI module (it will import the other dependencies)
	► Copy/Paste the dependencies.gradle file in the gradle scripts
	► Add the dependencies.gradle in the project build.gradle file
		► apply from: 'dependencies.gradle'


► Configuration 
	► settings.gladle
		► correct the project name to fit the current app
	► ui module:
		► Set the package name in the manifest
		► Set the package name in the build.gradle defaultConfig
	► Rename module	folder names to reflect
	► Check if classes are named correctly and make adjustments if necessary
	► Run a code cleanup 


► Updating 
	► Update plugins, gradle version and kotlin version
		► Tools -> Kotlin -> Configure Kotlin Plugin Updates -> Check for updates now
	► Check the ui, presentation, domain, data and the data source modules updated dependencies
	► Remove unused and deprecated dependencies
	► 
	► 
	► 
	► 

► Injections 
	► 
	► 
	► 


► Refactoring



---------

Fazer  alguns caso de uso teste
Fazer um módulo para informations do devicce como location etc
Fazer um módulo para camera etc