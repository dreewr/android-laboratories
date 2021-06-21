Beagle [referencias atualizadas em https://github.com/dreewr/ServerDrivenUI]

Referências
[1] https://www.youtube.com/watch?v=kzkgNdoqPdg&ab_channel=VitorRamos
[2] Server Driven UI - https://www.youtube.com/watch?v=nwwapsDq2uM&ab_channel=EscolaparaDesenvolvedores&t=0s

[3] KotlinConf2019 - Lona Scaling Server Driven UI: https://www.youtube.com/watch?v=Ir8lq4rSyyc&ab_channel=JetBrainsTV

[TODOS]
	- Fazer um exemplo usando o Design System do iti

[1]	//todo: fazer uma poc de backend 
	- SpringInitializer
		- Gera um projeto e adiciona deps do Beagle

	- Configurando um serviço no " backend" do BFF 
		- Exemplo
		
		```kotlin	
			@Service
			class MyService{

				fun createScreen() = 
				Screen(child = Container(
				children = listOf(
				Text("adicione dinheiro").applyStyle(
				 Style(flex = Flex(...))
				)
				))
			}
		```

			//Criar um controller no Spring pra  poder servir essa tela 
			@RestController 
			class MyController(private val service: MyService){

			@GetMapping("/screen") //endpoint screen
			fun getScreen() = service.createScreen()

			}

		- Screen contém um Containers que contém Widgets aos quais podem ser adicionados Styles


	- No Android, criar um projeto simples [6:30 min]
		- build.gradle project
			ext.beagle_version = "1.0.xxx"

		- criar uma classe de config
		@BeagleComponent	
		AppBeagleConfig: BeagleConfig{
			override val isLoggingEnabled = true
			// o emulator do android por padrão nesse IP acessa o localhost da maquina. e como o BFF tá rodando 
			override val baseUrl = "10.0.2.2:8000"
			override val environment = Environment.DEBUG
			override val cache = 

		} 

		- Criar a Activity que vai consumir o Beagle
		AppBeagleActivity

		No XML, usando o layout exemplo do beagle:
		* Hierarquia* 
			Linear
				Toolbar
				Frame
				 	//view que vai renderizar o componente do backend
					Frame
						- id: server_driven_container
					//progress pra indicar o carregamento dos dados
					Progress
		- No .kt da Activity: 

			@BeagleComponent
			class AppBeagleActivity: BeagleActivity(){
				private val toolbar = ...
				private val progressBar = ....

				...

				override fun getServerDrivenContainerId() = R.id.server_driven_container

				override fun getToolbar() = toolbar

				override fun onServerDrivenContainerStateChanged(state: ServerDriveState){
					if(state is ServerDriveState.Loading){
						progressBar.visibility = visivel ou nao	
					} else if ( state is ServerDriveState.Error){
						Toast.makeText(this, "Erro", ...)

					}
				}
			}


		- No styles.xml 
			Mudar o parent do tema do app de DarkActionBar para NoActionBar para orientar o sistema a somente carregar as views do XML 

		---> Depois de compilar/buildar o projeto

		- Criar uma classe de Application

			class MyApplication: Application(){
				override fun onCreate(){
					...
					BeagleSetup().init(this)
				}

			}
		- Adicionar no manifest em <application> com android:name=".MyApplication"


		- Criando o fluxo 
		MainActivity chama a minha BeagleActivity
			- Na MainActivity coloco um botão que chama um intent com a request configurada...

				button.setOnClickListener(){
					startActivity(
						BeagleActivity.newIntent(
							this, 
							ScreenRequest("/screen"))
					)
				}

		De forma meio automágica o app vai plotar na tela o elemento configurado no Beagle

[2] Server Driven UI
	- Modelo tradicional, a configuração do layout é de responsabilidade da plataforma, e os dados são recebidos através do backend e são apresentados nessa tela. E.g.: Tela 1 vai ter um texto e uma imagem, eu chamo um endpoint que me retorna um JSON e eu ploto esses elementos na minha tela 
	- No Server-Driven-UI, o JSON não retorna somente os dados mas sim como eles devem ser exibidos 
		- Spotify, ITI, Airb'n'b, iFood, SoundCloud, Uber 
		- Vantagens: 
			- Se o sistema possui multiplas plataformas, você não precisa construir uma nova tela em multiplas plaformas. Com o Design System acordado entre as tres o teu BFF consegue entregar o que cada plataforma precisa rnde
			- com uma única versão do app é possível ter várias experiências configuráveis para o usuário. 
				- Exemplo do UBER para capitais ou interior
				- Exemplo de uma feature exclusiva para uma cidade que mostra a feature usando a localização do usuário

			- Configurar conteúdos exlusivos para demograficos diferentes ou datas especiais 
			- Adicionar ou remover features sem necessariamente precisar atualizar a versão da loja 
		- Como funciona
			- Servidor monta um JSON de acordo com as especificações do usuário ou da logica
			- Aplicativo recebe esse JSON e renderiza o que é descrito pelo JSON, o processamento dos dados não é mais feito pelo aplicativo
				- Essa renderização preferencialmente é feita tendo em mente que o backend é um BFF [microsserviço que customiza as entregas do backend para cada interface do usuário] com um contrato bem definido de Desi
				- e.g> 
				 	{
				 		"title": "Title"
				 		"boxes": [
				 			{
				 				"type": "FieldComponent",
				 				"title": "Component"
				 			},
							{	
								"type": "TextComponent",
				 				"title": "Component"
				 			},
				 		]

					}
				 - Essa renderização poderia ser diferente para um um usuário 2 por exemplo, baseado em alguma parametrização




