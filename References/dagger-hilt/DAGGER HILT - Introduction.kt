DAGGER HILT - Introduction

► References 
	► HiltSeries
		► https://medium.com/android-dev-br/hilt-series-introdu%C3%A7%C3%A3o-ao-dagger-hilt-50faa1b3a194
		► https://medium.com/android-dev-br/hilt-series-architecture-components-com-dagger-hilt-3f10b6345f42	
	► Google Opinionated Guide: d.android.com/dependency-injection [Guide]
	► Using Dagger in your application codelab [codelabs.developers.google/codelabs/android-dagger]
	► Saved State for ViewModel: 
		► https://developer.android.com/topic/libraries/architecture/viewmodel-savedstate
	► Jetpack integrations
		►  https://developer.android.com/training/dependency-injection/hilt-jetpack
	► Maia Grotepass - Comparing DI Frameworks
		►  [ABOUT INTEGRATION TESTS @23:00] https://www.youtube.com/watch?v=-MJm4UGoh5I
	► Android Developers Hilt - Android Dependency Injection
		► [TESTING @15:50] https://www.youtube.com/watch?v=B56oV3IHMxg
	► Migration Guide
		► dagger.dev/hilt/migration-guide
	► Scoping 
		► [Coding with Mitch] https://www.youtube.com/watch?v=ZVv8mh1Kols

► Injecting ViewModels
	
	► The current way of doing things

		@Module
		abstract class ViewModelBindingModule {

		    @Binds
		    @IntoMap
		    @ViewModelKey(SomeViewModel::class)
		    fun provideSomeViewModel(impl: SomeViewModel): ViewModel

		    @Binds
		    @IntoMap
		    @ViewModelKey(AnotherViewModel::class)
		    fun provideAnotherViewModel(impl: AnotherViewModel): ViewModel
		}

		THEN you had to provide a ViewModel factory that would provide you with a viewModel class based on your ViewModelKey
		THEN bind the ViewModelFactory to the ViewModelProvider.Factory interface in the presentation Module
		THEN put the presentation module in you ApplicationComponent

	► Injecting with Hilt
		implementation 'androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha01'
		kapt 'androidx.hilt:hilt-compiler:1.0.0-alpha01'

		class LoggingViewModel @ViewModelInject constructor(
	    private val logger: Logger
	    @Assisted savedStateHandle: SavedStateHandle //OPCIONAL
			) {
			    fun log(message: String){
			        logger.log(message)
			    }
			}
		}

		@AndroidEntryPoint
		class MainActivity: AppCompatActivity() {
		    
		  private val viewModel: LoggingViewModel by viewModels() 

		  ... } 

► Example of RoomDataBaseBuilder

	@InstallIn(ApplicationComponent::class)
	@Module
	abstract class CacheModule {

	    companion object {
	        @Singleton
	        @Provides
	        fun getUserDatabase(@ApplicationContext application: Context): UserDatabase {
	            return UserDatabase.getInstance(application)
	        }
	    }
	    @Binds
	    abstract fun bindCache(cache: CacheImpl): Cache
	}

► Module and Lifecycle
	► You can use scope annotations to limit the lifetime of an object to the lifetime of its component. This means that the same instance of a dependency is used every time that type needs to be provided. 

	►	Application | Activity / ActivityRetained | Fragment | Service | View / ViewWithFragment

	@Module
	@InstallIn(ActivityRetainedComponent::class)
	object RepositoryModule {

	  @Provides
	  @ActivityRetainedScoped
	  fun provideMainRepository(
	    pokedexClient: PokedexClient,
	    pokemonDao: PokemonDao
	  ): MainRepository {
	    return MainRepository(pokedexClient, pokemonDao)
	  }

	  @Provides
	  @ActivityRetainedScoped
	  fun provideDetailRepository(
	    pokedexClient: PokedexClient,
	    pokemonInfoDao: PokemonInfoDao
	  ): DetailRepository {
	    return DetailRepository(pokedexClient, pokemonInfoDao)
	  }
	}

► Scoping 
	► Hilt provides premade scopes, that you can annotate the dependencies with
		@Singleton - lives as long as the app lives 
		@ActivityRetained - lives as long as Activity and Conf. changes, like ViewModels
		@Activity - Lives as long as activity but dies with Config changes
				... so on and so forth ...
	► Examples

		@Singleton
		class SomeClass @Inject constructor(){
			fun doSomething() = "did something"
		}

		
		class AnotherClass @Inject constructor(){
			fun doSomething() = "did something"
		}

		@AndroidEntryPoint
		class MyFragement...{
			@Inject lateinit var someClass: SomeClass

		}

	Cant inject an Activity with a @FragmentScoped annotated dependency 

► Providing Instances of the same type
	► E.g. Two implementations of the same interface
	► Module with two strings, how to tell Hilt to inject one in ActivityA and other in ActivityB
	► How to tell Hilt what to inject and where

	► Add the annotation to the dependency in the module, and when you inject it you use the same annotation 

	► Example 



	class InterfaceImplementation @Inject constructor(): SomeInterface {
		...
	}


	class AnotherInterfaceImplementation @Inject constructor(): SomeInterface {
		...
	}

	Create the annotations

	@Qualifier 
	@Retention(AnnotationRetention.RUNTIME)
	annotation class Implementation

	@Qualifier 
	@Retention(AnnotationRetention.RUNTIME)
	annotation class AnotherImplementation


	@InstallI(ApplicationComponent::class)
	@Module
	class MyModule{	

		@Implemetation //APLLY THE ANNOTATION
		@Singleton
		@Provides
		fun provideInterface(): SomeInterface { return InterfaceImplementation}


		@AnotherImplementation  //APPLY THE ANNOTATION
		@Singleton
		@Provides
		fun provideAnotherInterface(): SomeInterface { return AnotherInterfaceImplementation}
	}

	class SomeClass @Inject constructor (
		@Implementation private val someImplementation : SomeInterface, 
		@AnotherImplementation private val AnotherImplementation: SomeInterface)
//Ver um jeito de criar os módulos retrofit desse jeito 