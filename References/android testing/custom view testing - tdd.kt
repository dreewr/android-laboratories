Referências

[1] Thiago Souto: https://medium.com/android-dev-br/testes-em-android-test-driven-development-com-activities-e-custom-views-24d08dccd11a

[1] Roboletric + Espresso + Android Testing Library + Mockk + Koin*

-	Exemplo: criar uma custom view que irá preencher seus campos utilizando um objeto de entrada e executa uma fun quando a view é clicada

	- app build.gradle

		def allTestsConfigs(dependency){
			dependencies.add('testImplementation', dependency)
			dependencies.add('androidTestImplementation, dependency')
		}

		nas dependências adicionar: 
		//Android Testing Stuff
	    testImplementation "org.robolectric:robolectric:4.2"
	    allTestsConfigs "androidx.test:core:1.1.0"
	    allTestsConfigs 'androidx.test.espresso:espresso-core:3.2.0-alpha02'
	    allTestsConfigs 'androidx.test.ext:junit:1.1.0'

	    testImplementation('io.mockk:mockk:1.9.3')
	    androidTestImplementation('io.mockk:mockk-android:1.9.3') { exclude module: 'objenesis' }
	    androidTestImplementation 'org.objenesis:objenesis:2.6'

   	- Activity de teste
   		- Usa ActivityScenario, uma classe que dispon. diversas APIs para iniciar e manipular o estado de uma activity --> activities que foram criadads no pacote principal* ou declaradas no manifest, o teste precisa de uma activity para ser executada
   		- Na pasta de debug, crie uma activity e um manifesto, adicione essa activity dentro do manifesto de teste e pronto.


   		@RunWith(AndroidJUnit4::class)
			class HeaderViewTest {

			    @Test
			    fun setPerson_shouldUpdatePersonInfo() {
			    	//inicia uma activity de teste vazia 
			        val scenario: ActivityScenario<TestActivity> =
			            ActivityScenario.launch(TestActivity::class.java)
			        scenario.onActivity { activity ->
			            //instancia a headerView
			            val view = HeaderView(activity)
			            //Executado o método utilizando a custom view
			            activity.setContentView(view)
			            //Executado o método responsável por popular a view
			            view.setPerson(Person("Thiago Souto", 28))
			        }

			        //Realizada algumas asserções para verificar se os campos estão com os valores novos
			        onView(withId(R.id.tv_name)).check(matches(withText("Thiago Souto")))
			        onView(withId(R.id.tv_age)).check(matches(withText("28 anos")))
			    }
			}

	- O teste só será executado após criação da custom view e métodos

		class HeaderView @JvmOverloads constructor(
		    context: Context,
		    attrs: AttributeSet? = null,
		    defStyleAttr: Int = 0
		) : LinearLayout(context, attrs, defStyleAttr) {
		    init {
		        LinearLayout.inflate(context, R.layout.header_view, this)
		    }

		    ...
		fun setPerson(person: Person) {
		}

			...
		}

	--> criar uma configuração de "Run/Debug Configurations" https://developer.android.com/studio/run/rundebugconfig
	para executar o teste, ou seja, configs para que o teste possa ser executado usando o modo AndroidJUnit ou o Instrumentado

	-> Criando um teste para disparar uma função sempre que um botão é clicado ->

		@Test
		fun setPerson_shouldCallListenerAfterSubmitClick() {
		    val scenario: ActivityScenario<TestActivity> =
		        ActivityScenario.launch(TestActivity::class.java)
		    val mockedFunction: () -> Unit = mockk(relaxed = true)
		    scenario.onActivity { activity ->
		        val view = HeaderView(activity)
		        activity.setContentView(view)
		        view.setPerson(Person("Thiago Souto", 28))
		        view.setButtonClick(mockedFunction)
		    }

		    onView(withId(R.id.btn_submit)).perform(click())
		    verify { mockedFunction() }
		}
