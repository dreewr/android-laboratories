Android Testing ► Domain [Biblioteca Kotlin pura | Mockito] 



► Crie uma página de test no mesmo nível de main

► Mockito is used to mock interfaces so that a dummy functionality can be added to a mock interface that can be used in unit testing
    → funções de stub que fazem o que uma implementação da interface faria    
GetProjectsTest → Teste do caso de uso GetProjects

► Usa dependências MockitoAnnotations @Before, @Test e @Test(expected = IllegalArgumentException::class)

► @Before

► Assinatura da classe original:
	open class GetBookmarkedProjects @Inject constructor(
    private val projectsRepository: ProjectsRepository,
    postExecutionThread: PostExecutionThread)
    : ObservableUseCase<List<Project>, Nothing?>(postExecutionThread)

► Se eu tenho que fazer mocks de interfaces, como ProjectsRepository, uso MockitoJUnitRunner? Testei e deu boa usando JUnit
► Classe de teste do caso de uso

    @RunWith(MockitoJUnitRunner::class) ► ver o motivo de usar essa annotation
	class GetBookmarkedProjectsTest {
    private lateinit var getBookmarkedProjects: GetBookmarkedProjects
    → Mock nas dependências do constructor
    → Esses mocks significam que agora a gente pode controlar o retorno 
    → de qualquer chamada para essas classes


    @Mock lateinit var projectsRepository: ProjectsRepository
    @Mock lateinit var postExecutionThread: PostExecutionThread

    @Before → Inicializamos nossos mocks
    		→ Inicializamos nossa classe com os mocks criados
    fun setup() {
        MockitoAnnotations.initMocks(this)
        getBookmarkedProjects = GetBookmarkedProjects(projectsRepository, postExecutionThread)
    }

    → Funções de teste anotadas com @Test
    → Teste para verificar se essa função retorna 
    → Como ela interage com ProjectsRepository é preciso fazer um stub do retorno dela
    → a função de stub cria um projeto na factory e cria um observable, que usa a função 
    do mockito para ser invocada sempre o useCase é "buildado"
    → buildUserCase chama a função do repositório, estamos testando se essa parte do código 
    funciona

    @Test
    fun getBookmarkedProjectsReturnsData() {
        val projects = ProjectDataFactory.makeProjectList(2)
        stubGetProjects(Observable.just(projects))
        val testObserver = getBookmarkedProjects.buildUseCaseObservable().test()
        testObserver.assertValue(projects)
        //poderia ser também testObserver.assertComplete()
    }

    → Teste para ver se completa
    → .test() é uma função da RxJava que retorna um Observer de teste
    @Test
    fun getBookmarkedProjectsCompletes() {
        stubGetProjects(Observable.just(ProjectDataFactory.makeProjectList(2)))
        val testObserver = getBookmarkedProjects.buildUseCaseObservable().test()
        testObserver.assertComplete()
    }

	→ Usa a função *whenever* do Mockito para tratar essa interação 
    → Sempre que a função projectsRepository.getBookmarkedProjects() for 
    invocada a gente vai retornar o observable que é para ela 
    private fun stubGetProjects(observable: Observable<List<Project>>) {
        whenever(projectsRepository.getBookmarkedProjects())
            .thenReturn(observable)
    }}

► Data Factory que gera os dados para as funções de Mock 

► Exemplo de getProjects quando se tem um parâmetro na chamada e um Completable
 	@Test
    fun bookmarkProjectCompletes() {
        stubBookmarkProject(Completable.complete()) //função de teste da interface
        val testObserver = bookmarkProject.buildUseCaseCompletable(
            BookmarkProject.Params.forProject(ProjectDataFactory.randomUuid())
        ).test()
        testObserver.assertComplete()
    }

► Exemplo de teste para ver se ele dá a Exception caso venha com IllegalArgumentException

	@Test(expected = IllegalArgumentException::class)
    fun bookmarkProjectThrowsException() {
        bookmarkProject.buildUseCaseCompletable().test()
    }


► Usando mock<T> em vez de @Mock:
    class BookmarkProjectTest {
         private val projectsRepository = mock<ProjectsRepository>()
         private val postExecutionThread  = mock<PostExecutionThread>()
         private val bookmarkProject = BookmarkProject(projectsRepository, postExecutionThread)

            @Test
            fun bookmarkProjectCompletes() {
                stubBookmarkProject(Completable.complete())
                val testObserver = bookmarkProject.buildUseCaseCompletable(
                    BookmarkProject.Params.forProject(ProjectDataFactory.randomUuid())
                ).test()
                testObserver.assertComplete()
            }

            @Test(expected = IllegalArgumentException::class)
            fun bookmarkProjectThrowsException() {
                bookmarkProject.buildUseCaseCompletable().test()
            }

            private fun stubBookmarkProject(completable: Completable) {
                whenever(projectsRepository.bookmarkProject(any()))
                    .thenReturn(completable)
            }
        }