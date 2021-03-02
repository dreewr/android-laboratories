Beagle

Referências
[1] https://www.youtube.com/watch?v=kzkgNdoqPdg&ab_channel=VitorRamos

[1]	//todo: fazer uma poc de backend 
	- SpringInitializer
		- Gera um projeto e adiciona deps do Beagle

	- Configurando um serviço no " backend" do BFF 
		- Exemplo
			
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










