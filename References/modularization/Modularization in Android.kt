MODULARIZATION -> MAIN IDEAS

× References:
	× [1]
	× [2]
	× [3]
	× [4] Modularization - https://jeroenmols.com/blog/2019/04/02/modularizationexample/
	× [5] Nested Modules in Gradle - http://www.developerphil.com/nested-modules-in-gradle/
	× [6] Dependency management + Kotlin - 
		× https://handstandsam.com/2018/02/11/kotlin-buildsrc-for-better-gradle-dependency-management/
		× https://caster.io/lessons/gradle-dependency-management-using-kotlin-and-buildsrc-for-buildgradle-autocomplete-in-android-studio?autoplay=true
	× [7] Dynamic Feature Navigation - https://medium.com/google-developer-experts/exploring-dynamic-feature-navigation-on-android-c803bdbbca9b
	× [8] More about Dynamic Features - https://medium.com/mindorks/dynamic-feature-modules-the-future-4bee124c0f1
	× [9] Navigation and Dynamic Features - https://www.raywenderlich.com/7023243-navigation-and-dynamic-features
	× [10] Best Practices for on Demand Delivery - https://developer.android.com/guide/app-bundle/ux-guidelines
	× [11] Dynamic Features and Hild caviats - https://developer.android.com/training/dependency-injection/hilt-multi-module#dfm
	× [12] https://stackoverflow.com/questions/62992903/android-dynamic-feature-modules-with-dagger-hilt
	× [13] Buffer - Modularization - https://buffer.com/resources/getting-started-with-feature-modularization-in-android-apps/
	× [14] Test dynamic - Features offline - https://medium.com/@star_zero/an-easy-way-to-test-offline-dynamic-delivery-9b6909b62bcb
	× [15] Testing features offline - https://medium.com/androiddevelopers/local-development-and-testing-with-fakesplitinstallmanager-57083e1840a4



× Dynamic Features 
	× Allows on-demand delivery
	× com.android.dynamic-feature  > com.android.library for feature modules
		× dist:instant="true|false" and dist:onDemand="true|false" to control the installation
	× com.android.application > used in the main module

× Dynamic Feature Navigation [7][9]
	× https://developer.android.com/guide/navigation/navigation-dynamic
	× [:app][:feature-module]
	× Conditional navigation if modules are or are not installed [using the library classes]
	× Dynamic Feature Navigation library extends from the Navigation Component to allow us to perform navigation which involves destinations defined within Dynamic Feature Modules
		× <androidx.fragment.app.FragmentContainerView
		    android:id="@+id/nav_host_fragment"
		    android:name="androidx.navigation.dynamicfeatures 
		        .fragment.DynamicNavHostFragment"  <<< Change the Host Frament
		    app:defaultNavHost="true"
		    app:navGraph="@navigation/main_nav" />

		× <fragment
	        app:moduleName="feature_one" <<< destination in the main_nav
	        android:id="@+id/featureOneFragment"
	        android:name="co.joebirch.feature_one.FeatureOneFragment" />

	    × Navigating between graphs >> use the global action [like we do in the brt app]
	    	<include-dynamic
			    android:id="@+id/featureNav"
			    app:moduleName="secondFeature"
			    app:graphResName="second_feature_nav"
			    app:graphPackage="co.joebirch.navigationsample.feature_two" />

		× The library has ways of dealing with installed|unninstalled features [7]
	× Custom Progress Fragment
		× More on [9], extending the library default
	× Non-Blocking Navigation Flow 

× Organizing Modules: idea using [5]
	ø UI
		ø :APP 'MAIN APP MODULE'
		ø FEATURES
			ø :LOGIN
			ø :ONBOARDING
			ø :PURCHASE 
W
× Dependency management: idea from [6]
	ø Commom dependencies.gradle to use the same version for each single dependency [as used in my android-clean]
	ø 
	ø Using Kotlin + buildSrc 
		× Project -> Create folder buildSrc -> Create file build.gradle.kts -> see full example @android-clean repo


× Dynamic Features and Hilt

× Declare dependencies inside the module [like a small version of the ui module]

× Testing Dynamic Features offline > 
	× Download bundletool: You need bundletool to build a new set of installable APKs from your app bundle