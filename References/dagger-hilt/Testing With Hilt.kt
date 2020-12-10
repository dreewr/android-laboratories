► References 
	► https://www.youtube.com/watch?v=vVJeOACGSOU
	► OFFICIAL DOCUMENTATION https://dagger.dev/hilt/testing
	► Instrumentation Testing: https://dagger.dev/hilt/instrumentation-testing.html
	► Robolectric Testing: https://dagger.dev/hilt/robolectric-testing.html
	► Github: https://github.com/mitchtabian/Dagger-Hilt-Playerground/tree/hilt-testing

Currently, Hilt only supports Android instrumentation and Robolectric tests
Helps to ACCESS, PROVIDE or REPLACE a bind 


	► Test Setup
		[1]@HiltAndroidTest 
		class FooTest {
		 [2] @get:Rule val rule = HiltAndroidRule(this)
		  ...
		}

		[3] Use HiltTestApplication for your Android Application class\
			► is dependent on whether the test is a Robolectric or instrumentation test


► Basic Testing Example (based on the github references)
	► add the build.gradle dependencies
		hilt-test dependencies, android and fragemnt scenario dependencies
	
	► in the android test directory
		
		► Test Example

		The original AppModule provided a string (example), that we want for the sake of learning to use another

		@UnninstalModules(AppModule::class) USEFUL IF YOU WANT MODULE ALTERNATIVES
		@HiltAndroidTest 
		class MainTest {

			@get:Rule val hiltRule = HiltAndroidRule(this)
			@Inject lateinit var testString 

			@Before //Things put here will execute before every test
			fun init(){
				hiltRule.inject()
			}


			@Test
			fun testSomething(){
				//hiltRule.test() is not needed becausa of the @Before function 
				assertThat(testString, containsString("TEST"))

			}

			//You can build it outside of the class if you want to reuse it 
			@Module
			@InstallIn(ApplicationComponent::class)
			object TestAppModule{
				//Copy/pasting original module changing configurations as needed
				@Singleton 
				@Provides
				fun providesTestString() = "This is a TEST string"

			}
		}
		
		► Create the Runner

			class MyTestRunner: AndroidJUnitTestRunner(){

				override fun newApplication(...):Application{

					return super.newApplication(..., 
						THIS TELLS THE RUNNER WITCH APPLICATION TO RUN:
						Dont use the regular Application, use this one instead
						HiltTestApplication::class.java.name,
						 ...) 
				}

			}

			in the build.gradle you tell in the defaultConfig to use the your runner
			testInstrumentationRunner "com.deco.clean.MyTestRunner"
		

	►
	
	►
	
	►
	
	►

► Activity scenario
	Creates a simulated Activity 

	...Inside your testClass, e.g MainTest
	@Test
	fun mainActivityTest(
		val scenario = launchActivity<MainActivity>()
		)
	}

► Fragment scenario
	Needs extra setup to work with Hilt

	@Test
	fun mainFragmentTest(){	
		val scenario = launchFragmentInContainer<MainFragment>
		//this will not work
	}	