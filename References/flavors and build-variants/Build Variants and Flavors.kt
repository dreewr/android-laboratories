Build Variants and Flavors

+ References
	+ commom source set: https://stackoverflow.com/questions/28563632/common-code-for-different-android-flavors
	+ https://www.journaldev.com/21533/android-build-types-product-flavors

× Used to make different builds with small changes
× Default: 
	× debug [run from the IDE in the device]
	× release: signed version

× :app build.gradle, inside android{'...'}

	+ signingConfigs { //:ver tutorial do luis para criação
        release {
            storeFile file("release-key.keystore")
            storePassword 'password'
            keyAlias 'alias'
            keyPassword 'journaldev'
        }
    }

	+ buildTypes {
        release {
            signingConfig signingConfigs.release
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
        debug{
            applicationIdSuffix ".debug"
            versionNameSuffix "-debug"
        }

        beta{ ---> Adding this will add another variant in the build variants 
            signingConfig signingConfigs.release
            applicationIdSuffix ".beta"
            versionNameSuffix "-beta"
        }
    }

× BUILD CONFIG: The BuildConfig.java class is auto-generated when different buildFlavors are created.
We can set Build Config Fields in our build.gradle
	+ They can be acce
	buildTypes {
        release {
            signingConfig signingConfigs.release
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
        debug{
            applicationIdSuffix ".debug"
            versionNameSuffix "-debug"
            buildConfigField "String", "TYPE", '"I AM A DEBUG NINJA"'
        }

        beta{
            signingConfig signingConfigs.release
            applicationIdSuffix ".beta"
            versionNameSuffix "-beta"
            buildConfigField "String", "TYPE", '"I AM A BETA NINJA"'
        }
    }

× FLAVORS :app build.gradle

	× Flavor Dimensions is a way to group flavors by a name. For now, we’re using just a single group
		× flavorDimensions "default"in the defaultConfig block
	× productFlavors{

        free{
            applicationId "com.journaldev.androidproductflavors.free"
        }

        paid{
            applicationId "com.journaldev.androidproductflavors.paid"
        }
    }


    × Writing Flavor specific code
    	src>main is the default code (Project Structure)

    	src> create another folder with the same name such as <paid>
    		override different sources
    		paid> res> values> colors to change the default colors e.g.

    	If you want to have a different version of the same class in the two flavor you'll need to create it in both flavors.

			src/flavor1/java/com/foo/A.java
			src/flavor2/java/com/foo/A.java

		And then your code in src/main/java can do import com.foo.A

		it is important to put the same A class only in source folders that are mutually exclusive. In this case src/flavor1/java and src/flavor2/java are never selected together, but main and flavor1 are.

		If you want to provide a different version of an activity in different flavor do not put it in src/main/java.

		Do note that if you had 3 flavors and only wanted a custom one for flavor1, while flavor2 and flavor3 shared the same activity you could create a common source folders for those two other activities. You have total flexibility in creating new source folders and configuring the source set to use them